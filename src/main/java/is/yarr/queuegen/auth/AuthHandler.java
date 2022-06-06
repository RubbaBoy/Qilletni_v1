package is.yarr.queuegen.auth;

import is.yarr.queuegen.user.UserInfo;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

/**
 * Handles the OAuth 2.0 logging in process.
 */
public interface AuthHandler {

    /**
     * Begins the OAuth authorization process. This returns the initial URL to send the user to link accounts to this
     * service. After logging in through this URL, they are directed to the redirect included in the query parameters
     * of the returned value.
     *
     * @return The initial URI to send the client to
     */
    URI beginAuth();

    /**
     * Completed the authorization process with the code given by the redirect URL, after the user is coming back from
     * the {@link #beginAuth()} URL. If a user is not already present in the system, they are created. Their associated
     * immutable {@link UserInfo} is returned.
     *
     * @param code The OAuth code
     * @return The created (or found) {@link UserInfo}
     */
    CompletableFuture<UserSession> completeAuth(String code);

}
