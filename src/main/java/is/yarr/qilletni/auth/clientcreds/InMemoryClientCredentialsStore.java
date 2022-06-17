package is.yarr.qilletni.auth.clientcreds;

import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;

/**
 * Stores a single {@link ClientCredentials} object in memory, lazily performing a synchronous update when it is
 * expired.
 */
@Service
public class InMemoryClientCredentialsStore implements ClientCredentialsStore {

    private static final String CLIENT_ID = System.getenv("SPOTIFY_CLIENT_ID");
    private static final String CLIENT_SECRET = System.getenv("SPOTIFY_CLIENT_SECRET");

    private ClientCredentials clientCredentials;

    @Override
    public ClientCredentials getClientCredentials() {
        if (clientCredentials == null || clientCredentials.isExpired()) {
            return requestNewCredentials();
        }

        return clientCredentials;
    }

    private synchronized ClientCredentials requestNewCredentials() {
        if (clientCredentials != null && !clientCredentials.isExpired()) {
            return clientCredentials;
        }

        try {
            var spotifyApi = new SpotifyApi.Builder()
                    .setClientId(CLIENT_ID)
                    .setClientSecret(CLIENT_SECRET)
                    .build();

            se.michaelthelin.spotify.model_objects.credentials.ClientCredentials spotifyClientCredentials = spotifyApi.clientCredentials()
                    .build()
                    .execute();

            return clientCredentials = new OAuthClientCredentials(spotifyClientCredentials.getAccessToken(), spotifyClientCredentials.getExpiresIn());
        } catch (IOException | ParseException | SpotifyWebApiException e) {
            throw new RuntimeException(e);
        }
    }
}
