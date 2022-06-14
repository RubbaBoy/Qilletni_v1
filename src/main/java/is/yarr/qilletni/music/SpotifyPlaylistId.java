package is.yarr.qilletni.music;

/**
 * A {@link SpotifyPlaylistId} implementation for the Spotify API.
 * @see <a href="https://developer.spotify.com/documentation/web-api/">Spotify Documentation</a>
 *
 * @param id The Spotify ID string of the playlist
 * @param uri The Spotify URI of the playlist
 */
public record SpotifyPlaylistId(String id, String uri) implements PlaylistId {
}
