package is.yarr.qilletni.auth;

import is.yarr.qilletni.database.repositories.TokenRepository;
import is.yarr.qilletni.spotify.SpotifyApiFactory;
import is.yarr.qilletni.user.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class PersistentTokenStore implements TokenStore {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistentTokenStore.class);

    private final TokenRepository tokenRepository;
    private final SpotifyApiFactory spotifyApiFactory;

    public PersistentTokenStore(TokenRepository tokenRepository, SpotifyApiFactory spotifyApiFactory) {
        this.tokenRepository = tokenRepository;
        this.spotifyApiFactory = spotifyApiFactory;
    }

    @Async
    @Override
    public CompletableFuture<Optional<Token>> getToken(UserInfo userInfo) {
        return CompletableFuture.completedFuture(tokenRepository.findById(userInfo.getId())
                .map(Token.class::cast));
    }

    @Override
    public CompletableFuture<Token> refreshToken(Token token) {
        var refreshSpotifyApi = spotifyApiFactory.createRefreshApi(token.getRefreshToken());

        var refreshRequest = refreshSpotifyApi.authorizationCodeRefresh().build();

        return refreshRequest.executeAsync().thenApplyAsync(result -> {
            token.setAccessToken(result.getAccessToken());

            // TODO: QTNB-15: Remove explicit use of TokenRepository
            if (token instanceof OAuthToken) {
                tokenRepository.save((OAuthToken) token);
            } else {
                LOGGER.error("Unable to save token type {} to a repository!", token.getClass().getName());
            }

            return token;
        });
    }

    @Async
    @Override
    public CompletableFuture<Token> createToken(UserInfo userInfo, String accessToken, String refreshToken, int expiresIn) {
        var oauthToken = new OAuthToken(userInfo.getId(), accessToken, refreshToken, Timestamp.from(Instant.now().plusSeconds(expiresIn)));
        tokenRepository.save(oauthToken);
        return CompletableFuture.completedFuture(oauthToken);
    }
}
