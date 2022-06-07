package is.yarr.queuegen.auth;

import is.yarr.queuegen.user.SpotifyUserInfo;
import is.yarr.queuegen.user.UserInfo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity(name = "session")
public class SpotifyUserSession implements UserSession {

    @Id
    private UUID sessionId;

    @ManyToOne
    @JoinColumn(name = "`user`")
    private SpotifyUserInfo userInfo;

    public SpotifyUserSession() {}

    /**
     * @param userInfo The {@link UserInfo}
     * @param sessionId The session ID
     */
    public SpotifyUserSession(SpotifyUserInfo userInfo, UUID sessionId) {
        this.userInfo = userInfo;
        this.sessionId = sessionId;
    }

    @Override
    public UserInfo getUserInfo() {
        return userInfo;
    }

    @Override
    public UUID getSessionId() {
        return sessionId;
    }

    @Override
    public String toString() {
        return "UserSession[" +
                "userInfo=" + userInfo + ", " +
                "sessionId=" + sessionId + ']';
    }

}
