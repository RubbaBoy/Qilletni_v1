package is.yarr.qilletni.database.repositories;

import is.yarr.qilletni.board.BasicBoard;
import is.yarr.qilletni.board.Board;
import is.yarr.qilletni.database.UnsupportedTypeException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class BoardRepository {

    private final BasicBoardRepository basicBoardRepository;

    public BoardRepository(BasicBoardRepository basicBoardRepository) {
        this.basicBoardRepository = basicBoardRepository;
    }

    /**
     * Finds a {@link Board} by its ID.
     *
     * @param boardId The ID of the board to find
     * @return The found board, if any
     */
    public Optional<Board> getBoard(UUID boardId) {
        return basicBoardRepository.findById(boardId)
                .map(Board.class::cast);
    }

    /**
     * Finds a {@link Board} asynchronously by its ID.
     *
     * @param boardId The ID of the board to find
     * @return The found board, if any
     */
    @Async
    public CompletableFuture<Optional<Board>> getBoardAsync(UUID boardId) {
        return CompletableFuture.completedFuture(getBoard(boardId));
    }

    /**
     * Finds a {@link Board} by its ID, owned by the given user ID.
     *
     * @param boardId The ID of the board to find
     * @param ownerId The ID of the owner of the board
     * @return The found board, if any
     */
    public Optional<Board> getBoard(UUID boardId, String ownerId) {
        return basicBoardRepository.findByIdAndOwnerId(boardId, ownerId)
                .map(Board.class::cast);
    }

    /**
     * Finds a {@link Board} asynchronously by its ID, owned by the given user ID.
     *
     * @param boardId The ID of the board to find
     * @param ownerId The ID of the owner of the board
     * @return The found board, if any
     */
    public CompletableFuture<Optional<Board>> getBoardAsync(UUID boardId, String ownerId) {
        return CompletableFuture.completedFuture(getBoard(boardId, ownerId));
    }

    /**
     * Saves a {@link Board} to its appropriate repository.
     *
     * @param board The board to save
     * @return The saved board
     * @throws UnsupportedTypeException If a repository for the given type is not found
     */
    public Board save(Board board) {
        return switch (board) {
            case BasicBoard basicBoard -> basicBoardRepository.save(basicBoard);
            default -> throw new UnsupportedTypeException(board);
        };
    }

    /**
     * Saves a {@link Board} asynchronously to its appropriate repository.
     *
     * @param board The board to save
     * @return The saved board
     */
    @Async
    public CompletableFuture<Board> saveAsync(Board board) {
        return CompletableFuture.completedFuture(save(board));
    }
}
