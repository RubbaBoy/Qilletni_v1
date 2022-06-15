package is.yarr.qilletni.components.spotify;

import is.yarr.qilletni.database.converters.SpotifyAlbumIdConverter;
import is.yarr.qilletni.music.AlbumId;

import javax.persistence.Convert;
import javax.persistence.Entity;

/**
 * Stores data for playing from a Spotify album.
 */
@Entity(name = "spotify_album_data")
public class SpotifyAlbumData extends SpotifyCollectionData {

    @Convert(converter = SpotifyAlbumIdConverter.class)
    private AlbumId albumId;

    @Override
    public SpotifyCollectionType getCollectionType() {
        return SpotifyCollectionType.ALBUM;
    }

    @Override
    public boolean isInitialized() {
        return albumId != null;
    }

    @Override
    public boolean isShuffleSupported() {
        return true;
    }

    /**
     * Gets the album ID to play from.
     *
     * @return The album ID to play from
     */
    public AlbumId getAlbumId() {
        return albumId;
    }

    /**
     * Sets the new album ID to play from.
     *
     * @param albumId The new album ID
     */
    public void setAlbumId(AlbumId albumId) {
        this.albumId = albumId;
    }
}
