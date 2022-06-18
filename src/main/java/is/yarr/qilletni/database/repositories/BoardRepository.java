package is.yarr.qilletni.database.repositories;

import is.yarr.qilletni.board.BasicBoard;
import is.yarr.qilletni.board.Board;
import is.yarr.qilletni.database.UnsupportedTypeException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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
    public Optional<Board> findById(UUID boardId) {
        return basicBoardRepository.findById(boardId)
                .map(Board.class::cast);
    }

    /**
     * Saves a {@link Board} to its appropriate repository.
     *
     * @param board The board to save
     * @return The saved board
     * @throws UnsupportedTypeException If a repository for the given type is not found
     */
    public Board save(Board board) {
        if (!(board instanceof BasicBoard)) {
            throw new UnsupportedTypeException(board);
        }

        return basicBoardRepository.save((BasicBoard) board);
    }
}
