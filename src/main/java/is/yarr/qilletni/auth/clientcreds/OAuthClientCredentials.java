package is.yarr.qilletni.auth.clientcreds;

import java.time.Instant;

public class OAuthClientCredentials implements ClientCredentials {

    private final String accessToken;
    private final Instant expiry;

    public OAuthClientCredentials(String accessToken, int expiresIn) {
        this(accessToken, Instant.now().plusSeconds(expiresIn));
    }

    public OAuthClientCredentials(String accessToken, Instant expiry) {
        this.accessToken = accessToken;
        this.expiry = expiry;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public Instant getExpiry() {
        return expiry;
    }

    @Override
    public boolean isExpired() {
        return Instant.now().isAfter(expiry);
    }
}
