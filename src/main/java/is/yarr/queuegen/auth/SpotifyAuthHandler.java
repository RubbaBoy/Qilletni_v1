package is.yarr.queuegen.auth;

import is.yarr.queuegen.spotify.SpotifyApiFactory;
import is.yarr.queuegen.user.UserStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

@Service
public class SpotifyAuthHandler implements AuthHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpotifyAuthHandler.class);

    private static final URI REDIRECT_URI = URI.create(System.getenv("REDIRECT_URL"));

    private final SpotifyApiFactory spotifyApiFactory;
    private final SessionHandler sessionHandler;
    private final TokenStore tokenStore;
    private final UserStore userStore;

    public SpotifyAuthHandler(SpotifyApiFactory spotifyApiFactory, SessionHandler sessionHandler, TokenStore tokenStore, UserStore userStore) {
        this.spotifyApiFactory = spotifyApiFactory;
        this.sessionHandler = sessionHandler;
        this.tokenStore = tokenStore;
        this.userStore = userStore;
    }

    @Override
    public URI beginAuth() {
        return spotifyApiFactory
                .createAnonApi(REDIRECT_URI)
                .authorizationCodeUri()
                .build()
                .execute();
    }

    @Override
    public CompletableFuture<UserSession> completeAuth(String code) {
        var spotifyApi = spotifyApiFactory.createAnonApi(REDIRECT_URI);
        var authorizationCodeRequest = spotifyApi.authorizationCode(code).build();

        return authorizationCodeRequest.executeAsync()
                .thenCompose(this::createUserSession);
    }

    private CompletableFuture<UserSession> createUserSession(AuthorizationCodeCredentials authorizationCodeCredentials) {
        return userStore.getOrCreateUser(authorizationCodeCredentials)
                .thenCompose(userInfo ->
                        tokenStore.createToken(userInfo, authorizationCodeCredentials.getAccessToken(), authorizationCodeCredentials.getRefreshToken(), authorizationCodeCredentials.getExpiresIn())
                        .thenApply(token -> sessionHandler.createSession(userInfo)));
    }
}
