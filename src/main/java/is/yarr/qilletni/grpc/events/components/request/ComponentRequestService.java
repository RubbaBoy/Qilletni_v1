package is.yarr.qilletni.grpc.events.components.request;

import io.grpc.stub.StreamObserver;
import is.yarr.qilletni.database.repositories.BoardRepository;
import is.yarr.qilletni.database.repositories.components.ComponentAggregatorRepository;
import is.yarr.qilletni.grpc.events.ResponseUtility;
import is.yarr.qilletni.grpc.events.components.request.mapper.ComponentGRPCMapper;
import is.yarr.qilletni.grpc.gen.request.ComponentRequestServiceGrpc;
import is.yarr.qilletni.grpc.gen.request.StructureRequestEvent;
import is.yarr.qilletni.grpc.gen.request.StructureResponse;
import is.yarr.qilletni.grpc.security.UserSessionSecurityContext;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;

import java.util.UUID;

import static is.yarr.qilletni.grpc.security.Authorities.GENERAL;

@GrpcService
public class ComponentRequestService extends ComponentRequestServiceGrpc.ComponentRequestServiceImplBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComponentRequestService.class);

    private final BoardRepository boardRepository;
    private final ComponentAggregatorRepository componentAggregatorRepository;
    private final ComponentGRPCMapper componentGRPCMapper;

    public ComponentRequestService(BoardRepository boardRepository, ComponentAggregatorRepository componentAggregatorRepository, ComponentGRPCMapper componentGRPCMapper) {
        this.boardRepository = boardRepository;
        this.componentAggregatorRepository = componentAggregatorRepository;
        this.componentGRPCMapper = componentGRPCMapper;
    }

    @Override
    @Secured({GENERAL})
    public void requestStructure(StructureRequestEvent request, StreamObserver<StructureResponse> responseObserver) {
        var userInfo = UserSessionSecurityContext.getCurrentUserInfo();
        var boardId = UUID.fromString(request.getBoardId());

        if (boardRepository.getBoard(boardId, userInfo.getId()).isEmpty()) {
            responseObserver.onNext(StructureResponse.newBuilder()
                    .setError(ResponseUtility.createError("Board could not be found", 404)).build());
            responseObserver.onCompleted();
            return;
        }

        componentAggregatorRepository.getComponentsFromBoard(boardId)
                .thenCompose(componentGRPCMapper::createBaseResponses)
                .thenAccept(componentResponses ->
                        responseObserver.onNext(StructureResponse.newBuilder()
                                .addAllComponents(componentResponses)
                                .build()))
                .whenComplete(($, throwable) -> ResponseUtility.terminallyReportError(throwable, responseObserver, "An exception occurred while retrieving components",
                        responseError -> StructureResponse.newBuilder().setError(responseError).build()));
    }
}
