package is.yarr.qilletni.components;

import is.yarr.qilletni.components.spotify.SpotifyCollectionData;
import is.yarr.qilletni.components.spotify.SpotifyCollectionType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Optional;
import java.util.UUID;

/**
 * A component to play from different Spotify sources.
 */
@Entity(name = "spotify_component")
public class SpotifyCollectionComponent extends Component {

    private boolean isSequential = true;

    @Column(name = "iteration_limit")
    private Integer limit;

    @OneToOne
    private SpotifyCollectionData collectionData;

    protected SpotifyCollectionComponent() {}

    /**
     * Creates a {@link SpotifyCollectionComponent} with a given instance ID.
     *
     * @param instanceId The instance ID
     * @param boardId The ID of the {@link is.yarr.qilletni.board.Board} this component is a part of
     */
    public SpotifyCollectionComponent(UUID instanceId, UUID boardId) {
        super(instanceId, boardId);
    }

    @Override
    boolean isInitialized() {
        return collectionData != null && collectionData.isInitialized();
    }

    /**
     * Checks if the component should play the songs sequentially, or on a pseudo-shuffle.
     * The term "pseud-shuffle" comes from the fact that it may not be in a perfect shuffle, as fetching all tracks
     * from a large collection and shuffling from them may be intensive.
     *
     * @return If the collection is played through sequentially
     */
    public boolean isSequential() {
        return isSequential;
    }

    /**
     * Sets if the songs should be played sequentially, or on a pseudo-shuffle.
     * The term "pseud-shuffle" comes from the fact that it may not be in a perfect shuffle, as fetching all tracks
     * from a large collection and shuffling from them may be intensive.
     *
     * @param isSequential If the songs should be played sequentially
     */
    public void setSequential(boolean isSequential) {
        this.isSequential = isSequential;
    }

    /**
     * Checks if the {@link SpotifyCollectionType} allows for shuffling. This might be unavailable for things like
     * radios in the future. If this component isn't initialized (checked via {@link #isInitialized()}), this method
     * will always return {@code false}.
     *
     * @return If shuffling is supported
     */
    public boolean isShuffleSupported() {
        return isInitialized() && collectionData.isShuffleSupported();
    }

    /**
     * Gets the maximum amount of songs to play from the collection. If this returns an empty optional, no limit is in
     * place, it will go on forever (or until the collection ends).
     *
     * @return The song limit (if any)
     */
    public Optional<Integer> getLimit() {
        return Optional.ofNullable(limit);
    }

    /**
     * Sets the maximum amount of songs to play from the collection.
     *
     * @param limit The song limit
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * Clears the song limit, making {@link #getLimit()} return an empty optional.
     */
    public void clearLimit() {
        this.limit = null;
    }

    /**
     * Gets the {@link SpotifyCollectionType} of this component, which determines what songs are fetched. If one has not
     * been set yet, an empty optional will be returned.
     *
     * @return The {@link SpotifyCollectionType}
     */
    public Optional<SpotifyCollectionType> getCollectionType() {
        return getCollectionData().map(SpotifyCollectionData::getCollectionType);
    }

    /**
     * Sets the {@link SpotifyCollectionType} of the component, which determines what songs are fetched.
     *
     * @param collectionType The {@link SpotifyCollectionType}
     */
    public void setCollectionType(SpotifyCollectionType collectionType) {
        this.collectionData = collectionType.createInitialData();
    }

    /**
     * Gets the {@link SpotifyCollectionData} specific to the collection type set via
     * {@link #setCollectionType(SpotifyCollectionType)}. If that has not been invoked yet, this will return an empty
     * optional.
     *
     * @return The {@link SpotifyCollectionData}, if a collection type has been set
     */
    public Optional<SpotifyCollectionData> getCollectionData() {
        return Optional.ofNullable(collectionData);
    }
}
