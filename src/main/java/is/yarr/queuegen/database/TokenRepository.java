package is.yarr.queuegen.database;

import is.yarr.queuegen.auth.OAuthToken;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<OAuthToken, String> {
}
