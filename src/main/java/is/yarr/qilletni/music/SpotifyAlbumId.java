package is.yarr.qilletni.music;

/**
 * An {@link AlbumId} implementation for the Spotify API.
 * @see <a href="https://developer.spotify.com/documentation/web-api/">Spotify Documentation</a>
 *
 * @param id The Spotify ID string of the album
 * @param uri The Spotify URI of the album
 */
public record SpotifyAlbumId(String id, String uri) implements AlbumId {
}
