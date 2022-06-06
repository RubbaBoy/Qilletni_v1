package is.yarr.queuegen.user;

/**
 * Contains all possible information relating to a user. This may include settings or cached data from Spotify that
 * might be useful.
 */
public interface UserInfo {

    /**
     * Gets the unique ID of the user.
     *
     * @return The unique ID of the user
     */
    String getId();

    /**
     * Get the name of the user.
     *
     * @return The user's name
     */
    String getName();

    /**
     * Gets the user's email.
     *
     * @return The user's email
     */
    String getEmail();

    /**
     * The direct URL to the user's avatar.
     *
     * @return The avatar URL
     */
    String getAvatarUrl();

}
