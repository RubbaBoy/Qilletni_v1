package is.yarr.queuegen.auth;

import is.yarr.queuegen.user.UserInfo;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Handles {@link Token} access and updating.
 */
public interface TokenStore {

    /**
     * Gets the {@link Token} for the given user.
     *
     * @param userInfo The user to get the token of
     * @return The user's associated token, if one is found
     */
    CompletableFuture<Optional<Token>> getToken(UserInfo userInfo);

    /**
     * Refreshes the access token for the given {@link Token}, modifying the given object. It is returned again for
     * easy chaining of calls.
     *
     * @param token The {@link Token} to refresh
     * @return The resulting token
     */
    CompletableFuture<Token> refreshToken(Token token);

    /**
     * Creates a {@link Token} for a given {@link UserInfo} with supplied access/refresh token data.
     *
     * @param userInfo The {@link UserInfo} to create the token for
     * @param accessToken The OAuth access token
     * @param refreshToken The OAuth refresh token
     * @param expiresIn The time (in seconds, from now) the token expires
     * @return The created {@link Token}
     */
    CompletableFuture<Token> createToken(UserInfo userInfo, String accessToken, String refreshToken, int expiresIn);

}
