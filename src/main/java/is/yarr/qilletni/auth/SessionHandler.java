package is.yarr.qilletni.auth;

import is.yarr.qilletni.user.UserInfo;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Handles user login sessions.
 */
public interface SessionHandler {

    /**
     * Checks if the given UUID is a valid session ID.
     *
     * @param sessionId The unique session ID
     * @return if the session is valid
     */
    CompletableFuture<Boolean> isValidSession(UUID sessionId);

    /**
     * Creates a {@link SpotifyUserSession} with the given {@link UserInfo}. This does not matter if the {@link UserInfo} has
     * already had sessions created from it.
     *
     * @param userInfo The {@link UserInfo}
     * @return The created {@link SpotifyUserSession}
     */
    CompletableFuture<UserSession> createSession(UserInfo userInfo);

    /**
     * Gets the associated {@link UserInfo} by the given session ID, if one exists. If {@link #isValidSession(UUID)}
     * returns {@code true}, this will always return a non-empty Optional.
     *
     * @param sessionId The unique session ID
     * @return The {@link UserInfo}, if one exists
     */
    CompletableFuture<Optional<UserInfo>> getUserInfo(UUID sessionId);

    /**
     * Invalidates/removes a user's session based on the given session ID. Subsequent {@link #isValidSession(UUID)} will
     * return {@code false}.
     *
     * @param sessionId The unique session ID
     * @return The async task of invalidating the session
     */
    CompletableFuture<Void> invalidateSession(UUID sessionId);

}
