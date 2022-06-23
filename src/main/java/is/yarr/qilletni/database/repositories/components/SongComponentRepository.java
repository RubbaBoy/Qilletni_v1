package is.yarr.qilletni.database.repositories.components;

import is.yarr.qilletni.components.SongComponent;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SongComponentRepository extends BoardOwnedRepository<SongComponent, UUID> {
    @Query(value = """
SELECT song_component.*, boards.*
FROM boards
         LEFT JOIN song_component
                   ON boards.id = song_component.board_id
WHERE song_component.instance_id = ?1
  AND boards.owner_id = ?2
""", nativeQuery = true)
    @Override
    Optional<SongComponent> findComponentOwnedBy(UUID componentId, String ownerId);

    @Override
    List<SongComponent> findAllByBoardId(UUID boardId);
}
