package is.yarr.qilletni.grpc.events;

import io.grpc.stub.StreamObserver;
import is.yarr.qilletni.board.BasicBoard;
import is.yarr.qilletni.board.Board;
import is.yarr.qilletni.database.repositories.BoardRepository;
import is.yarr.qilletni.grpc.gen.EmptyResponse;
import is.yarr.qilletni.grpc.gen.request.BoardCreateEvent;
import is.yarr.qilletni.grpc.gen.request.BoardCreateResponse;
import is.yarr.qilletni.grpc.gen.request.BoardNameChangeEvent;
import is.yarr.qilletni.grpc.gen.request.BoardServiceGrpc;
import is.yarr.qilletni.grpc.gen.request.BoardsRequestEvent;
import is.yarr.qilletni.grpc.gen.request.BoardsRequestResponse;
import is.yarr.qilletni.grpc.security.UserSessionAuthenticationToken;
import is.yarr.qilletni.grpc.security.UserSessionSecurityContext;
import is.yarr.qilletni.user.UserInfo;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import static is.yarr.qilletni.grpc.security.Authorities.GENERAL;

@GrpcService
public class BoardService extends BoardServiceGrpc.BoardServiceImplBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoardService.class);

    private final Pattern NAME_PATTERN = Pattern.compile("^[\\w -_]+$");

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    @Secured({GENERAL})
    public void create(BoardCreateEvent request, StreamObserver<BoardCreateResponse> responseObserver) {
        var userInfo = UserSessionSecurityContext.getCurrentUserInfo();
        var boardId = UUID.randomUUID();
        var boardName = request.getName();

        LOGGER.debug("Making board with ID of {} and name of {}", boardId, boardName);

        if (!isValidName(boardName)) {
            responseObserver.onNext(BoardCreateResponse.newBuilder()
                    .setError(ResponseUtility.createError("Invalid name given", 400)).build());
            return;
        }

        var board = new BasicBoard(boardId, boardName, userInfo.getId());
        boardRepository.saveAsync(board)
                .thenRun(() -> {
                    responseObserver.onNext(BoardCreateResponse.newBuilder()
                            .setBoardId(boardId.toString())
                            .build());
                    responseObserver.onCompleted();
                });

    }

    @Override
    @Secured({GENERAL})
    public void changeName(BoardNameChangeEvent request, StreamObserver<EmptyResponse> responseObserver) {
        var userInfo = UserSessionSecurityContext.getCurrentUserInfo();
        var boardId = UUID.fromString(request.getModify().getBoardId());
        var boardName = request.getName();

        LOGGER.debug("Changing name of board {} to {}", boardId, request.getName());

        if (!isValidName(boardName)) {
            ResponseUtility.sendTerminatingError(responseObserver, "Invalid name given", 400);
            return;
        }

        boardRepository.getBoardAsync(boardId, userInfo.getId())
                .thenAcceptAsync(boardOptional ->
                        boardOptional.ifPresentOrElse(board -> {
                            board.setName(request.getName());

                            LOGGER.debug("Saving board {} with name {}", boardId, request.getName());
                            boardRepository.save(board);
                            ResponseUtility.sendEmpty(responseObserver);
                        }, () -> ResponseUtility.sendError(responseObserver, "No board found with the ID of: " + boardId, 404)))
                .thenRun(responseObserver::onCompleted);
    }

    @Override
    @Secured({GENERAL})
    public void requestBoards(BoardsRequestEvent request, StreamObserver<BoardsRequestResponse> responseObserver) {
        var userInfo = UserSessionSecurityContext.getCurrentUserInfo();

        boardRepository.getBoardsFromOwnerAsync(userInfo.getId())
                .thenAccept(boards -> {
                    LOGGER.debug("Found {} boards for {}", boards.size(), userInfo.getId());

                    responseObserver.onNext(BoardsRequestResponse.newBuilder()
                            .addAllBoards(boards.stream().map(this::convertBoardToGRPC).toList())
                            .build());
                })
                .thenRun(responseObserver::onCompleted);
    }

    private is.yarr.qilletni.grpc.gen.request.Board convertBoardToGRPC(Board board) {
        return is.yarr.qilletni.grpc.gen.request.Board.newBuilder()
                .setId(board.getId().toString())
                .setName(board.getName())
                .setOwnerId(board.getOwnerId())
                .build();
    }

    private boolean isValidName(String name) {
        return NAME_PATTERN.matcher(name).matches();
    }
}
