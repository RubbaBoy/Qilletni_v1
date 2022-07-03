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
     * Gets the direct artwork URL of the playlist.
     *
     * @return The artwork URL
     */
    String getArtworkUrl();

    /**
     * Gets the amount of songs in the playlist.
     *
     * @return The amount of songs
     */
    int getSize();

    /**
     * Gets if the playlist is visible to the public.
     *
     * @return If the playlist is public
     */
    boolean isPublic();

}
