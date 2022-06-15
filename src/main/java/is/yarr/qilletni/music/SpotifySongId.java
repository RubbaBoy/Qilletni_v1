package is.yarr.qilletni.music;

import org.modelmapper.internal.util.Assert;

/**
 * A {@link SongId} implementation for the Spotify API.
 * @see <a href="https://developer.spotify.com/documentation/web-api/">Spotify Documentation</a>
 *
 */
public record SpotifySongId(String id, String uri) implements SongId {

    /**
     * Creates a {@link SpotifySongId} with a given ID, implicitly setting the uri as "spotify:track:id-param".
     *
     * @param id The song ID
     */
    public SpotifySongId(String id) {
        this(id, "spotify:track:" + id);
        Assert.notNull(id, "id must not be null");
    }
}
