package is.yarr.qilletni.music;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * An implementation of {@link Song} for the Spotify API.
 */
@Entity(name = "spotify_songs")
public class SpotifySong implements Song {

    @Id
    private String id;
    private String name;
    private String artist;
    private String artworkUrl;

    protected SpotifySong() {}

    /**
     * @param id The ID of the song
     * @param name The name of the song
     * @param artist The primary artist of the song
     * @param artworkUrl The artwork URL of the song
     */
    public SpotifySong(String id, String name, String artist, String artworkUrl) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.artworkUrl = artworkUrl;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getArtist() {
        return artist;
    }

    @Override
    public String getArtworkUrl() {
        return artworkUrl;
    }

    @Override
    public String toString() {
        return "SpotifySong[" +
                "id=" + id + ", " +
                "name=" + name + ", " +
                "artist=" + artist + ", " +
                "artworkUrl=" + artworkUrl + ']';
    }
}
