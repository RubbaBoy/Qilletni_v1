package is.yarr.qilletni.music;

/**
 * Represents an artist fetched from an external API.
 */
public interface Artist {

    /**
     * Returns the playlist's ID string provided by an external API.
     *
     * @return The playlist's ID
     */
    String getId();

    /**
     * Gets the artist's name.
     *
     * @return The artist's name
     */
    String getName();

    /**
     * Gets the direct artwork URL of the artist.
     *
     * @return The artwork URL
     */
    String getArtworkUrl();

}
