package is.yarr.qilletni.database.repositories.components;

import is.yarr.qilletni.components.RawCollectionComponent;
import is.yarr.qilletni.components.SongComponent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RawCollectionComponentRepository extends BoardOwnedRepository<RawCollectionComponent, UUID> {

    @Query(value = """
SELECT raw_collection_component.*, boards.*
FROM boards
         LEFT JOIN raw_collection_component
                   ON boards.id = raw_collection_component.board_id
WHERE raw_collection_component.instance_id = ?1
  AND boards.owner_id = ?2
""", nativeQuery = true)
    @Override
    Optional<RawCollectionComponent> findComponentOwnedBy(UUID componentId, String ownerId);

    @Override
    List<RawCollectionComponent> findAllByBoardId(UUID boardId);

}
