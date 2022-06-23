package is.yarr.qilletni.database.repositories.components;

import is.yarr.qilletni.components.LastFmCollectionComponent;
import is.yarr.qilletni.components.SongComponent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LastFmComponentRepository extends BoardOwnedRepository<LastFmCollectionComponent, UUID> {

    @Query(value = """
SELECT lastfm_component.*, boards.*
FROM boards
         LEFT JOIN lastfm_component
                   ON boards.id = lastfm_component.board_id
WHERE lastfm_component.instance_id = ?1
  AND boards.owner_id = ?2
""", nativeQuery = true)
    @Override
    Optional<LastFmCollectionComponent> findComponentOwnedBy(UUID componentId, String ownerId);

    @Override
    List<LastFmCollectionComponent> findAllByBoardId(UUID boardId);

}
