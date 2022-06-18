package is.yarr.qilletni.auth;

import is.yarr.qilletni.database.repositories.UserSessionRepository;
import is.yarr.qilletni.user.SpotifyUserInfo;
import is.yarr.qilletni.user.UserInfo;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class PersistentSessionHandler implements SessionHandler {

    private final UserSessionRepository userSessionRepository;

    public PersistentSessionHandler(UserSessionRepository userSessionRepository) {
        this.userSessionRepository = userSessionRepository;
    }

    @Async
    @Override
    public CompletableFuture<Boolean> isValidSession(UUID sessionId) {
        return CompletableFuture.completedFuture(userSessionRepository.existsById(sessionId));
    }

    @Async
    @Override
    public CompletableFuture<UserSession> createSession(UserInfo userInfo) {
        var sessionId = UUID.randomUUID();

        if (!(userInfo instanceof SpotifyUserInfo)) {
            throw new IllegalArgumentException("PersistentSessionHandler#createSession(UserInfo) only accepts instances of SpotifyUserInfo");
        }

        var userSession = new SpotifyUserSession((SpotifyUserInfo) userInfo, sessionId);
        userSessionRepository.save(userSession);
        return CompletableFuture.completedFuture(userSession);
    }

    @Async
    @Override
    public CompletableFuture<Optional<UserInfo>> getUserInfo(UUID sessionId) {
        return CompletableFuture.completedFuture(userSessionRepository.findById(sessionId)
                        .map(SpotifyUserSession::getUserInfo));
    }

    @Async
    @Override
    public CompletableFuture<Void> invalidateSession(UUID sessionId) {
        userSessionRepository.deleteById(sessionId);
        return CompletableFuture.completedFuture(null);
    }
}
