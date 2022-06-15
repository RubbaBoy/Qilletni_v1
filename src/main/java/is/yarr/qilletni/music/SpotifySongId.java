package is.yarr.qilletni.music;

import javax.persistence.DiscriminatorValue;
import java.util.Objects;

/**
 * A {@link SongId} implementation for the Spotify API.
 * @see <a href="https://developer.spotify.com/documentation/web-api/">Spotify Documentation</a>
 *
 */
//@DiscriminatorValue("spotify")
//@Entity
public final class SpotifySongId extends SongId {
    private String uri;

    public SpotifySongId() {}

    /**
     * @param id The Spotify ID string of the song
     * @param uri The Spotify URI of the song
     */
    public SpotifySongId(String id, String uri) {
        this.id = id;
        this.uri = uri;
    }

    public String uri() {return uri;}

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (SpotifySongId) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.uri, that.uri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uri);
    }

    @Override
    public String toString() {
        return "SpotifySongId[" +
                "id=" + id + ", " +
                "uri=" + uri + ']';
    }

}
