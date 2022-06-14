package is.yarr.qilletni.database;

import is.yarr.qilletni.auth.OAuthToken;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<OAuthToken, String> {
}
