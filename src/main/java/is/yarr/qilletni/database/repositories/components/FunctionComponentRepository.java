package is.yarr.qilletni.database.repositories.components;

import is.yarr.qilletni.components.FunctionComponent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FunctionComponentRepository extends BoardOwnedRepository<FunctionComponent, UUID> {

    @Query(value = """
SELECT function_component.*, boards.*
FROM boards
         LEFT JOIN function_component
                   ON boards.id = function_component.board_id
WHERE function_component.instance_id = ?1
  AND boards.owner_id = ?2
""", nativeQuery = true)
    @Override
    Optional<FunctionComponent> findComponentOwnedBy(UUID componentId, String ownerId);

    @Override
    List<FunctionComponent> findAllByBoardId(UUID boardId);
}
