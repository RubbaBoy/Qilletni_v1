package is.yarr.qilletni.user;

import is.yarr.qilletni.database.UserInfoRepository;
import is.yarr.qilletni.spotify.SpotifyApiFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.User;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class PersistentUserStore implements UserStore {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistentUserStore.class);

    private final SpotifyApiFactory spotifyApiFactory;
    private final UserInfoRepository userInfoRepository;

    public PersistentUserStore(SpotifyApiFactory spotifyApiFactory, UserInfoRepository userInfoRepository) {
        this.spotifyApiFactory = spotifyApiFactory;
        this.userInfoRepository = userInfoRepository;
    }

    @Async
    @Override
    public CompletableFuture<Boolean> userExists(String spotifyId) {
        return CompletableFuture.completedFuture(userInfoRepository.existsById(spotifyId));
    }

    @Override
    public CompletableFuture<UserInfo> getOrCreateUser(AuthorizationCodeCredentials authorizationCodeCredentials) {
        var profileRequest = spotifyApiFactory.createApi(authorizationCodeCredentials).getCurrentUsersProfile().build();
        return profileRequest.executeAsync()
                .thenApplyAsync(user -> {
                    var spotifyId = user.getId();

                    var userInfoOptional = userInfoRepository.findById(spotifyId);
                    if (userInfoOptional.isPresent()) {
                        LOGGER.info("User with id {} already found!", spotifyId);
                        return userInfoOptional.get();
                    }

                    var userInfo = new SpotifyUserInfo(spotifyId, user.getDisplayName(), user.getEmail(), getAvatarUrl(user));
                    LOGGER.info("Saving user: {}", userInfo);
                    userInfoRepository.save(userInfo);

                    return userInfo;
                });
    }

    @Async
    @Override
    public CompletableFuture<Optional<UserInfo>> getUser(String spotifyId) {
        LOGGER.info("Getting user on thread: {}", Thread.currentThread());
        return CompletableFuture.completedFuture(userInfoRepository.findById(spotifyId)
                .map(UserInfo.class::cast));
    }

    private String getAvatarUrl(User user) {
        var images = user.getImages();
        return images.length > 0 ? images[0].getUrl() : "";
    }
}
