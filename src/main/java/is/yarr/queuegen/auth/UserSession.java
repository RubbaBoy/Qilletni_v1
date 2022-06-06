package is.yarr.queuegen.auth;

import is.yarr.queuegen.user.UserInfo;

import java.util.UUID;

/**
 * A data object to associate a session ID with a user.
 *
 * @param userInfo The {@link UserInfo}
 * @param sessionId The session ID
 */
public record UserSession(UserInfo userInfo, UUID sessionId) {

    /**
     * Gets the {@link UserInfo} associated with this session.
     *
     * @return The {@link UserInfo}
     */
    public UserInfo getUserInfo() {
        return userInfo;
    }

    /**
     * Gets the unique ID of the session.
     *
     * @return The ID
     */
    public UUID getSessionId() {
        return sessionId;
    }
}
