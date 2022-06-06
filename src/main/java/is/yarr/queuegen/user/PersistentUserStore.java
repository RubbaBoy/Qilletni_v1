package is.yarr.queuegen.user;

import is.yarr.queuegen.spotify.SpotifyApiFactory;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class PersistentUserStore implements UserStore {

    private final SpotifyApiFactory spotifyApiFactory;

    public PersistentUserStore(SpotifyApiFactory spotifyApiFactory) {
        this.spotifyApiFactory = spotifyApiFactory;
    }

    @Override
    public CompletableFuture<Boolean> userExists(String spotifyId) {
        throw new UnsupportedOperationException("userExists"); // TODO: userExists
    }

    @Override
    public CompletableFuture<UserInfo> getOrCreateUser(AuthorizationCodeCredentials authorizationCodeCredentials) {
        var profileRequest = spotifyApiFactory.createApi(authorizationCodeCredentials).getCurrentUsersProfile().build();
        return profileRequest.executeAsync()
                .thenApply(user -> {

                    // TODO: Check if user.getId() is contained in the repository. If so, return it!
                    //       otherwise:


                    // TODO: save UserInfo into repository!
                    var images = user.getImages();
                    return new SpotifyUserInfo(user.getId(), user.getDisplayName(), user.getEmail(), images.length > 0 ? images[0].getUrl() : "");
                });
    }

    @Override
    public CompletableFuture<Optional<UserInfo>> getUser(String spotifyId) {
        throw new UnsupportedOperationException("getUser"); // TODO: getUser
    }
}
