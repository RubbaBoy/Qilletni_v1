package is.yarr.qilletni.grpc.security;

import is.yarr.qilletni.user.UserInfo;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserSessionSecurityContext {

    /**
     * Acquires the currently authenticated user's {@link UserSessionAuthenticationToken}. This should only be used in
     * {@link org.springframework.security.access.annotation.Secured} gRPC service methods.
     *
     * @return The current user's auth token
     */
    public static UserSessionAuthenticationToken getAuthToken() {
        return (UserSessionAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * Acquires the currently authenticated user's {@link UserInfo}. This should only be used in
     * {@link org.springframework.security.access.annotation.Secured} gRPC service methods.
     *
     * @return The current user's info
     */
    public static UserInfo getCurrentUserInfo() {
        return getAuthToken().getPrincipal();
    }

}
