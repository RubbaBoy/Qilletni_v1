package is.yarr.qilletni.auth;

import java.sql.Timestamp;

/**
 * Holds time-specific OAuth token data. This object should be held onto as infrequently as possible, and accessed
 * directly from {@link TokenStore} as they may expire.
 */
public interface Token {

    /**
     * Get the OAuth 2.0 access token.
     *
     * @return The access token
     */
    String getAccessToken();

    /**
     * Sets a new access token, used for refreshing.
     *
     * @param accessToken The new access token
     */
    void setAccessToken(String accessToken);

    /**
     * Gets the OAuth 2.0 refresh token, used to refresh the access token.
     *
     * @return The refresh token
     */
    String getRefreshToken();

    /**
     * Gets the OAuth 2.0 access token expiry date. After this date has passed, this {@link Token} is no longer valid.
     *
     * @return The token expiry time
     */
    Timestamp getAccessTokenExpiry();

    /**
     * If the {@link #getAccessTokenExpiry()} has passed. After this, it should be refreshed.
     *
     * @return If the token is expired
     */
    boolean isExpired();

}
