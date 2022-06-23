package is.yarr.qilletni.database.repositories.components;

import is.yarr.qilletni.components.SongComponent;
import is.yarr.qilletni.components.SpotifyCollectionComponent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The correct order of saving a {@link SpotifyCollectionComponent} with a changed {@link is.yarr.qilletni.components.spotify.SpotifyCollectionData} is:
 * <pre>
 * spotifyDataRepository.save(newCollectionData);
 * spotifyCollectionRepository.save(spotifyCollectionComponent);
 * spotifyDataRepository.delete(oldCollectionData);
 * </pre>
 */
public interface SpotifyComponentRepository extends BoardOwnedRepository<SpotifyCollectionComponent, UUID> {

    @Query(value = """
SELECT spotify_component.*, boards.*
FROM boards
         LEFT JOIN spotify_component
                   ON boards.id = spotify_component.board_id
WHERE spotify_component.instance_id = ?1
  AND boards.owner_id = ?2
""", nativeQuery = true)
    @Override
    Optional<SpotifyCollectionComponent> findComponentOwnedBy(UUID componentId, String ownerId);

    @Override
    List<SpotifyCollectionComponent> findAllByBoardId(UUID boardId);

}
