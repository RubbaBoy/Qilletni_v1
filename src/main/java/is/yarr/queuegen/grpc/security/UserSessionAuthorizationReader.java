package is.yarr.queuegen.grpc.security;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

/**
 * An authorization reader, taking in a UUID as the Authorization parameter and authorizing with an instance of
 * {@link UserSessionAuthenticationToken}.
 */
@Service
public class UserSessionAuthorizationReader implements GrpcAuthenticationReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserSessionAuthorizationReader.class);

    public static final Metadata.Key<String> AUTHORIZATION_HEADER = Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public Authentication readAuthentication(ServerCall<?, ?> call, Metadata headers) throws AuthenticationException {
        final String authHeader = headers.get(AUTHORIZATION_HEADER);

        var uuidOptional = getOptionalUUID(authHeader);

        if (uuidOptional.isEmpty()) {
            LOGGER.debug("Invalid auth header: {}", authHeader);
            return null;
        }

        return new UserSessionAuthenticationToken(uuidOptional.get());
    }

    private Optional<UUID> getOptionalUUID(@Nullable String uuid) {
        if (uuid == null) {
            return Optional.empty();
        }

        try {
            return Optional.of(UUID.fromString(uuid));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
