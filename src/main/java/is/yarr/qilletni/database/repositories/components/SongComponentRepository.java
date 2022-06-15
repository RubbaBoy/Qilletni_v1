package is.yarr.qilletni.database.repositories.components;

import is.yarr.qilletni.components.SongComponent;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SongComponentRepository extends CrudRepository<SongComponent, UUID> {
}
