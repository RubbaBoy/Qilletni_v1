package is.yarr.queuegen.grpc;

import is.yarr.queuegen.auth.SessionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class SessionAuthenticationProvider implements AuthenticationProvider {

    private final SessionHandler sessionHandler;

    public SessionAuthenticationProvider(SessionHandler sessionHandler) {
        this.sessionHandler = sessionHandler;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(SessionAuth.class, authentication, "SessionAuthenticationProvider only supports instances of SessionAuth");
        var sessionAuth = (SessionAuth) authentication;

        var userInfo = sessionHandler.getUserInfo(sessionAuth.getCredentials()).join()
                .orElseThrow(() -> new BadCredentialsException("Invalid session ID: " + sessionAuth.getCredentials()));

        // TODO: authorities
        return new SessionAuth(sessionAuth.getCredentials(), userInfo, List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SessionAuth.class.isAssignableFrom(authentication);
    }
}
