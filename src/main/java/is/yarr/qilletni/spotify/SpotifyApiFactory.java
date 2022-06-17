package is.yarr.qilletni.spotify;

import is.yarr.qilletni.auth.Token;
import is.yarr.qilletni.user.UserInfo;
import org.springframework.lang.Nullable;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;

import java.net.URI;

/**
 * A factory for creating user-specific {@link SpotifyApi}s.
 */
public interface SpotifyApiFactory {

    /**
     * Creates a {@link SpotifyApi} with client credentials.
     * @see <a href="https://developer.spotify.com/documentation/general/guides/authorization/client-credentials/">Client Credentials Flow</a>
     *
     * @return The created {@link SpotifyApi}
     */
    SpotifyApi createClientCredentialsApi();

    /**
     * Returns a {@link SpotifyApi} to be used to refresh access tokens.
     *
     * @param refreshToken The OAuth 2.0 refresh token
     * @return The created {@link SpotifyApi}
     */
    SpotifyApi createRefreshApi(String refreshToken);

    /**
     * Creates a {@link SpotifyApi} without any user tokens tied to it, with the given redirect URI. This should only
     * be used for authenticating a user.
     *
     * @param redirectUri The redirect URI to include, if any
     * @return The created {@link SpotifyApi}
     */
    SpotifyApi createRedirectApi(@Nullable URI redirectUri);

    /**
     * Creates a {@link SpotifyApi} from a {@link UserInfo}. This is meant to be short-lived, and will
     * automatically refresh tokens if necessary.
     *
     * @param userInfo The {@link UserInfo} this is for
     * @return The created {@link SpotifyApi}
     */
    SpotifyApi createApi(Token token);

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
