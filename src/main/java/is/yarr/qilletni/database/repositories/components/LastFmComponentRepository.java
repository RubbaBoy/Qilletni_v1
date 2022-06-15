package is.yarr.qilletni.database.repositories.components;

import is.yarr.qilletni.components.LastFmCollectionComponent;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface LastFmComponentRepository extends CrudRepository<LastFmCollectionComponent, UUID> {
}
