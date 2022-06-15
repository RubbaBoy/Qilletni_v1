package is.yarr.qilletni.database.repositories.components;

import is.yarr.qilletni.components.RawCollectionComponent;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RawCollectionComponentRepository extends CrudRepository<RawCollectionComponent, UUID> {
}
