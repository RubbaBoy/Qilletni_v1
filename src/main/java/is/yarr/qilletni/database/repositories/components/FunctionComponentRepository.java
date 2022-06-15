package is.yarr.qilletni.database.repositories.components;

import is.yarr.qilletni.components.FunctionComponent;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface FunctionComponentRepository extends CrudRepository<FunctionComponent, UUID> {
}
