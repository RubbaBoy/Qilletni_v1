package is.yarr.queuegen.grpc.security;

import is.yarr.queuegen.auth.SessionHandler;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * An authentication provider using the session ID from {@link UserSessionAuthenticationToken#getCredentials()} and
 * linking it to the {@link SessionHandler}. The resulting {@link Authentication} is still a
 * {@link UserSessionAuthenticationToken}.
 */
@Service
public class UserSessionAuthenticationProvider implements AuthenticationProvider {

    private final SessionHandler sessionHandler;

    public UserSessionAuthenticationProvider(SessionHandler sessionHandler) {
        this.sessionHandler = sessionHandler;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(UserSessionAuthenticationToken.class, authentication, "UserSessionAuthenticationProvider only supports instances of UserSessionAuthenticationToken");
        var sessionAuth = (UserSessionAuthenticationToken) authentication;

        var userInfo = sessionHandler.getUserInfo(sessionAuth.getCredentials()).join()
                .orElseThrow(() -> new BadCredentialsException("Invalid session ID: " + sessionAuth.getCredentials()));

        // TODO: authorities
        return new UserSessionAuthenticationToken(sessionAuth.getCredentials(), userInfo, List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UserSessionAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
