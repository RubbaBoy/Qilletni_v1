package is.yarr.qilletni.music;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * An implementation of {@link Playlist} for the Spotify API.
 */
@Entity(name = "spotify_playlist")
public class SpotifyPlaylist implements Playlist {

    @Id
    private String id;
    private String name;
    private String owner;
    private String artworkUrl;
    private int size;
    private boolean isPublic;

    protected SpotifyPlaylist() {}

    /**
     * @param id          The playlist's ID
     * @param name        The playlist's name
     * @param owner       The owner of the playlist
     * @param artworkUrl  The direct artwork URL
     * @param size        The amount of songs in the playlist
     * @param isPublic    If the playlist is visible to the public
     */
    public SpotifyPlaylist(String id, String name, String owner, String artworkUrl, int size, boolean isPublic) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.artworkUrl = artworkUrl;
        this.size = size;
        this.isPublic = isPublic;
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
    public String getOwner() {
        return owner;
    }

    @Override
    public String getArtworkUrl() {
        return artworkUrl;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isPublic() {
        return isPublic;
    }

    @Override
    public String toString() {
        return "SpotifyPlaylist[" +
                "id=" + id + ", " +
                "name=" + name + ", " +
                "owner=" + owner + ", " +
                "artworkUrl=" + artworkUrl + ", " +
                "size=" + size + ", " +
                "isPublic=" + isPublic + ']';
    }
}
