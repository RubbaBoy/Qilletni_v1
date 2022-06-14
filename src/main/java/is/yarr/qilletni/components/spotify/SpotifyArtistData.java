package is.yarr.qilletni.components.spotify;

import is.yarr.qilletni.music.ArtistId;

/**
 * Stores data for playing from a Spotify album.
 */
public class SpotifyArtistData implements SpotifyCollectionData {

    private ArtistId artistId;

    @Override
    public SpotifyCollectionType getCollectionType() {
        return SpotifyCollectionType.ARTIST;
    }

    @Override
    public boolean isInitialized() {
        return artistId != null;
    }

    @Override
    public boolean isShuffleSupported() {
        return true;
    }

    /**
     * Gets the artist ID to play from.
     *
     * @return The artist ID to play from
     */
    public ArtistId getArtistId() {
        return artistId;
    }

    /**
     * Sets the new artist ID to play from.
     *
     * @param artistId The new artist ID
     */
    public void setArtistId(ArtistId artistId) {
        this.artistId = artistId;
    }
}
