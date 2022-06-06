package is.yarr.queuegen.auth;

import is.yarr.queuegen.database.TokenRepository;
import is.yarr.queuegen.user.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class PersistentTokenStore implements TokenStore {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistentTokenStore.class);
    private final Executor tokenExecutor = Executors.newCachedThreadPool();

    private final TokenRepository tokenRepository;

    public PersistentTokenStore(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Async
    @Override
    public CompletableFuture<Optional<Token>> getToken(UserInfo userInfo) {
        return CompletableFuture.completedFuture(tokenRepository.findById(userInfo.getId())
                .map(Token.class::cast));
    }

    @Override
    public CompletableFuture<Optional<Token>> refreshToken(UserInfo userInfo) {
        throw new UnsupportedOperationException("refreshToken"); // TODO: refreshToken
    }

    @Async
    @Override
    public CompletableFuture<Token> createToken(UserInfo userInfo, String accessToken, String refreshToken, int expiresIn) {
        var oauthToken = new OAuthToken(userInfo.getId(), accessToken, refreshToken, Timestamp.from(Instant.now().plusSeconds(expiresIn)));
        LOGGER.info("Saving token: {}", oauthToken);
        tokenRepository.save(oauthToken);
        LOGGER.info("Saving on thread: {}", Thread.currentThread().getName());
        return CompletableFuture.completedFuture(oauthToken);
    }
}
