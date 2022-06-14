package is.yarr.qilletni.components.spotify;

import is.yarr.qilletni.music.AlbumId;

/**
 * Stores data for playing from a Spotify album.
 */
public class SpotifyAlbumData implements SpotifyCollectionData {

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
