package is.yarr.queuegen.grpc;

import is.yarr.queuegen.user.UserInfo;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.UUID;

public class SessionAuth extends AbstractAuthenticationToken {

    private UUID sessionId;
    private final UserInfo userInfo;

    /**
     * Creates an initial {@link SessionAuth} that wants to be authenticated.
     *
     * @param sessionId The session ID
     */
    public SessionAuth(UUID sessionId) {
        super(null);
        this.sessionId = sessionId;
        this.userInfo = null;
        setAuthenticated(false);
    }

    /**
     * Creates an authenticated {@link SessionAuth} with the {@link UserInfo} who the {@code sessionId} belongs to.
     *
     * @param sessionId The session ID of the user
     * @param userInfo The information regarding the user
     * @param authorities The authorities of the user
     */
    SessionAuth(UUID sessionId, UserInfo userInfo, Collection<GrantedAuthority> authorities) {
        super(authorities);
        this.sessionId = sessionId;
        this.userInfo = userInfo;
        super.setAuthenticated(true);
    }

    @Override
    public UUID getCredentials() {
        return sessionId;
    }

    @Override
    public UserInfo getPrincipal() {
        return userInfo;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated,
                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.sessionId = null;
    }
}
