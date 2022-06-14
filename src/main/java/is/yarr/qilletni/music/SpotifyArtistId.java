package is.yarr.qilletni.music;

/**
 * An {@link ArtistId} implementation for the Spotify API.
 * @see <a href="https://developer.spotify.com/documentation/web-api/">Spotify Documentation</a>
 *
 * @param id The Spotify ID string of the artist
 * @param uri The Spotify URI of the artist
 */
public record SpotifyArtistId(String id, String uri) implements ArtistId {
}
