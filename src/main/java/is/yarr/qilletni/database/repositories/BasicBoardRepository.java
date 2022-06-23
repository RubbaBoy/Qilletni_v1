package is.yarr.qilletni.database.repositories;

import is.yarr.qilletni.board.BasicBoard;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

interface BasicBoardRepository extends CrudRepository<BasicBoard, UUID> {

    Optional<BasicBoard> findByIdAndOwnerId(UUID boardId, String ownerId);

}
