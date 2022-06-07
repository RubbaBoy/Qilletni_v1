package is.yarr.queuegen.database;

import is.yarr.queuegen.auth.SpotifyUserSession;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserSessionRepository extends CrudRepository<SpotifyUserSession, UUID> {
}
