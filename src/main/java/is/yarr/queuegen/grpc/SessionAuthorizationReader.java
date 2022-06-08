package is.yarr.queuegen.grpc;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class SessionAuthorizationReader implements GrpcAuthenticationReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionAuthorizationReader.class);

    public static final Metadata.Key<String> AUTHORIZATION_HEADER = Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);

    @Nullable
    @Override
    public Authentication readAuthentication(ServerCall<?, ?> call, Metadata headers) throws AuthenticationException {
        final String authHeader = headers.get(AUTHORIZATION_HEADER);

        LOGGER.info("header = {}", authHeader);

        if (authHeader == null) {
            LOGGER.info("No auth header found!");
            return null;
        }

        var uuidOptional = getOptionalUUID(authHeader);

        if (uuidOptional.isEmpty()) {
            LOGGER.info("Invalid auth header: {}", authHeader);
            return null;
        }

        LOGGER.info("Session ID: {}", uuidOptional.get());

        return new SessionAuth(uuidOptional.get());
    }

    private Optional<UUID> getOptionalUUID(String uuid) {
        try {
            return Optional.of(UUID.fromString(uuid));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
