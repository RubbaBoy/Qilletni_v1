package is.yarr.qilletni.music;

/**
 * A {@link SongId} implementation for the Spotify API.
 * @see <a href="https://developer.spotify.com/documentation/web-api/">Spotify Documentation</a>
 *
 * @param id The Spotify ID string of the song
 * @param uri The Spotify URI of the song
 */
public record SpotifySongId(String id, String uri) implements SongId {
}
