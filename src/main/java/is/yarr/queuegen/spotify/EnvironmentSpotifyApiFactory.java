package is.yarr.queuegen.spotify;

import is.yarr.queuegen.auth.Token;
import is.yarr.queuegen.auth.TokenStore;
import is.yarr.queuegen.user.UserInfo;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;

import java.net.URI;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class EnvironmentSpotifyApiFactory implements SpotifyApiFactory {

    private static final String CLIENT_ID = System.getenv("SPOTIFY_CLIENT_ID");
    private static final String CLIENT_SECRET = System.getenv("SPOTIFY_CLIENT_SECRET");

    private final TokenStore tokenStore;

    public EnvironmentSpotifyApiFactory(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Override
    public SpotifyApi createAnonApi() {
        return createAnonApi(null);
    }

    @Override
    public SpotifyApi createRefreshApi(String refreshToken) {
        return new SpotifyApi.Builder()
                .setClientId(CLIENT_ID)
                .setClientSecret(CLIENT_SECRET)
                .setRefreshToken(refreshToken)
                .build();
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
    public CompletableFuture<Optional<SpotifyApi>> createApi(UserInfo userInfo) {
        return tokenStore.getToken(userInfo).thenApplyAsync(optionalToken -> {
            if (optionalToken.isEmpty()) {
                return Optional.empty();
            }

            var token = optionalToken.get();

            if (token.isExpired()) {
                tokenStore.refreshToken(token).join();
            }

            return Optional.of(new SpotifyApi.Builder()
                    .setClientId(CLIENT_ID)
                    .setClientSecret(CLIENT_SECRET)
                    .setAccessToken(token.getAccessToken())
                    .setRefreshToken(token.getRefreshToken())
                    .build());
        });
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
