package is.yarr.qilletni.music;

import org.modelmapper.internal.util.Assert;

/**
 * An {@link AlbumId} implementation for the Spotify API.
 * @see <a href="https://developer.spotify.com/documentation/web-api/">Spotify Documentation</a>
 *
 * @param id The Spotify ID string of the album
 * @param uri The Spotify URI of the album
 */
public record SpotifyAlbumId(String id, String uri) implements AlbumId {

    /**
     * Creates a {@link SpotifyAlbumId} with a given ID, implicitly setting the uri as "spotify:album:id-param".
     *
     * @param id The album ID
     */
    public SpotifyAlbumId(String id) {
        this(id, "spotify:album:" + id);
        Assert.notNull(id, "id must not be null");
    }
}
