package is.yarr.qilletni.music;

/**
 * An implementation of {@link Song} for the Spotify API.
 *
 * @param id The ID of the song
 * @param name The name of the song
 * @param artist The primary artist of the song
 * @param artworkUrl The artwork URL of the song
 */
public record SpotifySong(String id, String name, String artist, String artworkUrl) implements Song {
}
