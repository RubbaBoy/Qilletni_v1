package is.yarr.qilletni.auth.clientcreds;

import java.time.Instant;
import java.util.Date;

public interface ClientCredentials {

    /**
     * Gets the access token of the credentials.
     *
     * @return The access token
     */
    String getAccessToken();

    /**
     * Gets the time of the expiration of the token.
     *
     * @return The expiration time
     */
    Instant getExpiry();

    /**
     * Checks if the token is expired or not.
     *
     * @return If the token is expired
     */
    boolean isExpired();

}
