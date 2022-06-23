package is.yarr.qilletni.database.repositories.components;

import is.yarr.qilletni.components.ForComponent;
import is.yarr.qilletni.components.SongComponent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ForComponentRepository extends BoardOwnedRepository<ForComponent, UUID> {

    @Query(value = """
SELECT for_component.*, boards.*
FROM boards
         LEFT JOIN song_component
                   ON boards.id = for_component.board_id
WHERE for_component.instance_id = ?1
  AND boards.owner_id = ?2
""", nativeQuery = true)
    @Override
    Optional<ForComponent> findComponentOwnedBy(UUID componentId, String ownerId);

    @Override
    List<ForComponent> findAllByBoardId(UUID boardId);

}
