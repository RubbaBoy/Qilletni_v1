package is.yarr.qilletni.database;

import is.yarr.qilletni.auth.SpotifyUserSession;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserSessionRepository extends CrudRepository<SpotifyUserSession, UUID> {
}
