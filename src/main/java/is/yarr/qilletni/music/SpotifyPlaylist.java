package is.yarr.qilletni.music;

/**
 * An implementation of {@link Playlist} for the Spotify API.
 *
 * @param id The playlist's ID
 * @param name The playlist's name
 * @param owner The owner of the playlist
 * @param artworkUrl The direct artwork URL
 * @param followers The playlist's followers
 * @param description The playlist's description
 * @param isPublic If the playlist is visible to the public
 */
public record SpotifyPlaylist(String id, String name, String owner, String artworkUrl, int followers, String description, boolean isPublic) implements Playlist {
}
