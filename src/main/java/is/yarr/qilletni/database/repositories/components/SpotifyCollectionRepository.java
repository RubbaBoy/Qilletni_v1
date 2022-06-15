package is.yarr.qilletni.database.repositories.components;

import is.yarr.qilletni.components.SpotifyCollectionComponent;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

/**
 * The correct order of saving a {@link SpotifyCollectionComponent} with a changed {@link is.yarr.qilletni.components.spotify.SpotifyCollectionData} is:
 * <pre>
 * spotifyDataRepository.save(newCollectionData);
 * spotifyCollectionRepository.save(spotifyCollectionComponent);
 * spotifyDataRepository.delete(oldCollectionData);
 * </pre>
 */
public interface SpotifyCollectionRepository extends CrudRepository<SpotifyCollectionComponent, UUID> {
}
