package is.yarr.queuegen.spotify;

import is.yarr.queuegen.user.UserInfo;
import org.springframework.lang.Nullable;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;

import java.net.URI;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * A factory for creating user-specific {@link SpotifyApi}s.
 */
public interface SpotifyApiFactory {

    /**
     * Creates a {@link SpotifyApi} without any user tokens tied to it.
     *
     * @return The created {@link SpotifyApi}
     */
    SpotifyApi createAnonApi();

    /**
     * Returns a {@link SpotifyApi} to be used to refresh access tokens.
     *
     * @param refreshToken The OAuth 2.0 refresh token
     * @return The created {@link SpotifyApi}
     */
    SpotifyApi createRefreshApi(String refreshToken);

    /**
     * Creates a {@link SpotifyApi} without any user tokens tied to it, with the (optional) given redirect URI.
     *
     * @param redirectUri The redirect URI to include, if any
     * @return The created {@link SpotifyApi}
     */
    SpotifyApi createAnonApi(@Nullable URI redirectUri);

    /**
     * Creates a {@link SpotifyApi} from a {@link UserInfo}. This is meant to be short-lived, and will
     * automatically refresh tokens if necessary.
     *
     * @param userInfo The {@link UserInfo} this is for
     * @return The created {@link SpotifyApi}
     */
    CompletableFuture<Optional<SpotifyApi>> createApi(UserInfo userInfo);

    /**
     * Creates a {@link SpotifyApi} from the given {@link AuthorizationCodeCredentials}. This is meant to be
     * short-lived, and will do nothing to refresh tokens. This is intended to be used to get user info immediately
     * after authorization.
     *
     * @param authorizationCodeCredentials The {@link AuthorizationCodeCredentials} from the initial authorization
     *                                     through the OAuth flow
     * @return The created {@link SpotifyApi}
     */
    SpotifyApi createApi(AuthorizationCodeCredentials authorizationCodeCredentials);

}
