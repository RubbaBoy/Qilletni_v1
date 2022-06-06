package is.yarr.queuegen.auth;

import is.yarr.queuegen.user.UserInfo;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemorySessionHandler implements SessionHandler {

    private final Map<UUID, UserInfo> sessions = new ConcurrentHashMap<>();

    @Override
    public boolean isValidSession(UUID sessionId) {
        return sessions.containsKey(sessionId);
    }

    @Override
    public UserSession createSession(UserInfo userInfo) {
        var sessionId = UUID.randomUUID();
        sessions.put(sessionId, userInfo);
        return new UserSession(userInfo, sessionId);
    }

    @Override
    public Optional<UserInfo> getUserInfo(UUID sessionId) {
        return Optional.ofNullable(sessions.get(sessionId));
    }

    @Override
    public void invalidateSession(UUID sessionId) {
        sessions.remove(sessionId);
    }
}
