package is.yarr.qilletni.music;

/**
 * Represents an album fetched from an external API.
 */
public interface Album {

    /**
     * Returns the album's ID string provided by an external API.
     *
     * @return The album's ID
     */
    String getId();

    /**
     * Gets the album's name.
     *
     * @return The album's name
     */
    String getName();

    /**
     * Gets the direct artwork URL of the album.
     *
     * @return The artwork URL
     */
    String getArtworkUrl();

}
