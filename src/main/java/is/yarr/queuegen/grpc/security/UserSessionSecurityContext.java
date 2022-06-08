package is.yarr.queuegen.grpc.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class UserSessionSecurityContext {

    public static UserSessionAuthenticationToken getAuthToken() {
        return (UserSessionAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    }

}
