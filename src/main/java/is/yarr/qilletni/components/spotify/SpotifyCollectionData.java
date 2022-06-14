package is.yarr.qilletni.components.spotify;

import is.yarr.qilletni.components.SpotifyCollectionComponent;

/**
 * Stores data relevant to what data is being fetched from a {@link SpotifyCollectionComponent}.
 */
public interface SpotifyCollectionData {

    /**
     * Gets the {@link SpotifyCollectionType} of the data, identifying what kind of data it is.
     *
     * @return The {@link SpotifyCollectionType}
     */
    SpotifyCollectionType getCollectionType();

    /**
     * Checks if all required fields are non-null and valid. If this returns {@code false}, do not expect anything to
     * work.
     *
     * @return If the data is ready to be used
     */
    boolean isInitialized();

    /**
     * Returns if shuffling is supported on the current collection.
     *
     * @return If shuffling is supported
     */
    boolean isShuffleSupported();

}
