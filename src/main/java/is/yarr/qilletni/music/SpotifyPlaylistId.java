package is.yarr.qilletni.music;

import org.modelmapper.internal.util.Assert;

/**
 * A {@link PlaylistId} implementation for the Spotify API.
 * @see <a href="https://developer.spotify.com/documentation/web-api/">Spotify Documentation</a>
 *
 * @param id The Spotify ID string of the playlist
 * @param uri The Spotify URI of the playlist
 */
public record SpotifyPlaylistId(String id, String uri) implements PlaylistId {

    /**
     * Creates a {@link SpotifyPlaylistId} with a given ID, implicitly setting the uri as "spotify:playlist:id-param".
     *
     * @param id The playlist ID
     */
    public SpotifyPlaylistId(String id) {
        // TODO: Is the format spotify:playlist:id?
        this(id, "spotify:playlist:" + id);
        Assert.notNull(id, "id must not be null");
    }
}
