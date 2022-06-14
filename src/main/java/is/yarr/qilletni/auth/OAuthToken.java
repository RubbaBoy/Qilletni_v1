package is.yarr.qilletni.auth;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.time.Instant;

@Entity(name = "token")
public final class OAuthToken implements Token {

    @Id
    private String spotifyId;
    private String accessToken;
    private String refreshToken;
    private Timestamp expiry;

    public OAuthToken() {}

    public OAuthToken(String spotifyId, String accessToken, String refreshToken, Timestamp expiry) {
        this.spotifyId = spotifyId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiry = expiry;
    }

    public String getId() {
        return spotifyId;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String getRefreshToken() {
        return refreshToken;
    }

    @Override
    public Timestamp getAccessTokenExpiry() {
        return expiry;
    }

    @Override
    public boolean isExpired() {
        return expiry.before(Timestamp.from(Instant.now()));
    }

    @Override
    public String toString() {
        return "OAuthToken{" +
                "spotifyId='" + spotifyId + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expiry=" + expiry +
                '}';
    }
}
