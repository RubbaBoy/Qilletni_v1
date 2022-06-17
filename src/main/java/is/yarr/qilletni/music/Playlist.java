package is.yarr.qilletni.music;

/**
 * Represents a playlist fetched from an external API.
 */
public interface Playlist {

    /**
     * Returns the playlist's ID string provided by an external API.
     *
     * @return The playlist's ID
     */
    String getId();

    /**
     * Gets the playlist's name.
     *
     * @return The playlist's name
     */
    String getName();

    /**
     * Gets the owner of the playlist.
     *
     * @return The owner of the playlist
     */
    String getOwner();

    /**
     * Gets the direct artwork URL of the song.
     *
     * @return The artwork URL
     */
    String getArtworkUrl();

    /**
     * Gets the followers of the playlist.
     *
     * @return The playlist's followers
     */
    int getFollowers();

    /**
     * Gets the description of the playlist.
     *
     * @return The playlist's description
     */
    String getDescription();

    /**
     * Gets if the playlist is visible to the public.
     *
     * @return If the playlist is public
     */
    boolean isPublic();

}
