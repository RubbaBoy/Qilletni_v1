package is.yarr.qilletni.music;

/**
 * Represents a song fetched from an external API that is playable.
 */
public interface Song {

    /**
     * Gets the song's ID string provided by an external API.
     *
     * @return The song's ID
     */
    String id();

    /**
     * Gets the song's name.
     *
     * @return The song's name
     */
    String name();

    /**
     * Gets the primary artist of the song.
     *
     * @return The artist of the song
     */
    String artist();

    /**
     * Gets the direct artwork URL of the song.
     *
     * @return The artwork URL
     */
    String artworkUrl();

}
