package is.yarr.qilletni.spotify;

import is.yarr.qilletni.auth.Token;
import is.yarr.qilletni.auth.clientcreds.ClientCredentialsStore;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;

@Service
public class EnvironmentSpotifyApiFactory implements SpotifyApiFactory {

    private static final String CLIENT_ID = System.getenv("SPOTIFY_CLIENT_ID");
    private static final String CLIENT_SECRET = System.getenv("SPOTIFY_CLIENT_SECRET");

    private final ClientCredentialsStore clientCredentialsStore;

    public EnvironmentSpotifyApiFactory(ClientCredentialsStore clientCredentialsStore) {
        this.clientCredentialsStore = clientCredentialsStore;
    }

    @Override
    public SpotifyApi createClientCredentialsApi() {
        var clientCredentials = clientCredentialsStore.getClientCredentials();

        return new SpotifyApi.Builder()
                .setClientId(CLIENT_ID)
                .setClientSecret(CLIENT_SECRET)
                .setAccessToken(clientCredentials.getAccessToken())
                .build();
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
    public SpotifyApi createRedirectApi(URI redirectUri) {
        return new SpotifyApi.Builder()
                .setClientId(CLIENT_ID)
                .setClientSecret(CLIENT_SECRET)
                .setRedirectUri(redirectUri)
                .build();
    }

    @Override
    public SpotifyApi createApi(Token token) {
        // TODO: Clean up!
//        return tokenStore.getToken(userInfo).thenApplyAsync(optionalToken -> {
//            if (optionalToken.isEmpty()) {
//                return Optional.empty();
//            }

//            var token = optionalToken.get();
//
//            if (token.isExpired()) {
//                tokenStore.refreshToken(token).join();
//            }

            return new SpotifyApi.Builder()
                    .setClientId(CLIENT_ID)
                    .setClientSecret(CLIENT_SECRET)
                    .setAccessToken(token.getAccessToken())
                    .setRefreshToken(token.getRefreshToken())
                    .build();
//        });
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
