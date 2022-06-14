package is.yarr.qilletni.user;

import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Stores and manages users in the system.
 */
public interface UserStore {

    /**
     * Checks if a user exists with the given Spotify ID.
     *
     * @param spotifyId The Spotify ID of the user
     * @return If the user exists
     */
    CompletableFuture<Boolean> userExists(String spotifyId);

    /**
     * Gets (if available) or creates a {@link UserInfo} for a given Spotify ID. The user is populated with data from
     * the Spotify API from their ID.
     *
     * @param authorizationCodeCredentials The {@link AuthorizationCodeCredentials} retrieved from the OAuth flow
     * @return The created {@link UserInfo}
     */
    CompletableFuture<UserInfo> getOrCreateUser(AuthorizationCodeCredentials authorizationCodeCredentials);

    /**
     * Gets a {@link UserInfo} from their Spotify ID, if they exist.
     *
     * @param spotifyId The Spotify ID
     * @return The {@link UserInfo}, if it exists
     */
    CompletableFuture<Optional<UserInfo>> getUser(String spotifyId);

}
