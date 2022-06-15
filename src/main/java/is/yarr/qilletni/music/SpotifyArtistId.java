package is.yarr.qilletni.music;

import org.modelmapper.internal.util.Assert;

/**
 * An {@link ArtistId} implementation for the Spotify API.
 * @see <a href="https://developer.spotify.com/documentation/web-api/">Spotify Documentation</a>
 *
 * @param id The Spotify ID string of the artist
 * @param uri The Spotify URI of the artist
 */
public record SpotifyArtistId(String id, String uri) implements ArtistId {

    /**
     * Creates a {@link SpotifyArtistId} with a given ID, implicitly setting the uri as "spotify:album:id-param".
     *
     * @param id The artist ID
     */
    public SpotifyArtistId(String id) {
        this(id, "spotify:artist:" + id);
        Assert.notNull(id, "id must not be null");
    }
}
