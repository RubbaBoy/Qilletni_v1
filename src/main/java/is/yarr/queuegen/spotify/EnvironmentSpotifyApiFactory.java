package is.yarr.queuegen.spotify;

import is.yarr.queuegen.user.UserInfo;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

@Service
public class EnvironmentSpotifyApiFactory implements SpotifyApiFactory {

    private static final String CLIENT_ID = System.getenv("SPOTIFY_CLIENT_ID");
    private static final String CLIENT_SECRET = System.getenv("SPOTIFY_CLIENT_SECRET");

    @Override
    public SpotifyApi createAnonApi() {
        return createAnonApi(null);
    }

    @Override
    public SpotifyApi createAnonApi(URI redirectUri) {
        return new SpotifyApi.Builder()
                .setClientId(CLIENT_ID)
                .setClientSecret(CLIENT_SECRET)
                .setRedirectUri(redirectUri)
                .build();
    }

    @Override
    public CompletableFuture<SpotifyApi> createApi(UserInfo userInfo) {
        throw new UnsupportedOperationException("createApi"); // TODO: implement createApi
    }

    @Override
    public SpotifyApi createApi(AuthorizationCodeCredentials authorizationCodeCredentials) {
        return new SpotifyApi.Builder()
                .setClientId(CLIENT_ID)
                .setClientSecret(CLIENT_SECRET)
                .setAccessToken(authorizationCodeCredentials.getAccessToken())
                .setRefreshToken(authorizationCodeCredentials.getRefreshToken())
                .build();
    }
}
