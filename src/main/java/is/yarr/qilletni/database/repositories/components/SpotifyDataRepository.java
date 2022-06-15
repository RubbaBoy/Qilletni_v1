package is.yarr.qilletni.database.repositories.components;

import is.yarr.qilletni.components.spotify.SpotifyCollectionData;
import org.springframework.data.repository.CrudRepository;

public interface SpotifyDataRepository extends CrudRepository<SpotifyCollectionData, Integer> {
}
