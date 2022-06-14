package is.yarr.qilletni.components.spotify;

import java.util.function.Supplier;

/**
 * An enum (/factory) to determine what data is being fetched from Spotify.
 */
public enum SpotifyCollectionType {

    /**
     * Songs from a given playlist.
     */
    PLAYLIST(SpotifyPlaylistData::new),

    /**
     * All songs from a given artist
     */
    ARTIST(SpotifyArtistData::new),

    /**
     * Songs from a given album
     */
    ALBUM(SpotifyAlbumData::new),

    /**
     * Songs from searched text
     */
    SEARCH(SpotifySearchData::new);

    private final Supplier<SpotifyCollectionData> collectionDataSupplier;

    /**
     * Creates an {@link SpotifyCollectionType} with a supplier that is invoked to create an appropriate empty
     * {@link SpotifyCollectionData} object when {@link #createInitialData()} is invoked.
     *
     * @param collectionDataSupplier The {@link SpotifyCollectionData} supplier
     */
    SpotifyCollectionType(Supplier<SpotifyCollectionData> collectionDataSupplier) {
        this.collectionDataSupplier = collectionDataSupplier;
    }

    /**
     * Creates the associated {@link SpotifyCollectionData} according to the current collection type. This contains no
     * data, and must be initialized separately.
     *
     * @return An empty {@link SpotifyCollectionData} object
     */
    public SpotifyCollectionData createInitialData() {
        return collectionDataSupplier.get();
    }
}
