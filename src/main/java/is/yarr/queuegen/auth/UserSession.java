package is.yarr.queuegen.auth;

import is.yarr.queuegen.user.UserInfo;

import java.util.UUID;

/**
 * A data object to associate a session ID with a user.
 */
public interface UserSession {
    /**
     * Gets the unique ID of the session.
     *
     * @return The ID
     */
    UUID getSessionId();

    /**
     * Gets the {@link UserInfo} associated with this session.
     *
     * @return The {@link UserInfo}
     */
    UserInfo getUserInfo();
}
