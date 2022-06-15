package is.yarr.qilletni.database.repositories.components;

import is.yarr.qilletni.components.SpotifyCollectionComponent;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SpotifyComponentRepository extends CrudRepository<SpotifyCollectionComponent, UUID> {
}
