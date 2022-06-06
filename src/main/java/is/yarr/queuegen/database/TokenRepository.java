package is.yarr.queuegen.database;

import is.yarr.queuegen.auth.OAuthToken;
import is.yarr.queuegen.auth.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface TokenRepository extends CrudRepository<OAuthToken, String> {
}
