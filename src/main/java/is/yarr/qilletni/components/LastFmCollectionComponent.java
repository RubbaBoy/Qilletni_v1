package is.yarr.qilletni.components;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Optional;
import java.util.UUID;

/**
 * A component to play from different LastFm sources.
 */
@Entity(name = "lastfm_component")
public class LastFmCollectionComponent extends Component {

    private boolean isSequential = true;
    private LastFmCollectionType collectionType;

    @Column(name = "iteration_limit")
    private Integer limit;

    protected LastFmCollectionComponent() {}

    /**
     * Creates a {@link LastFmCollectionComponent} with a given instance ID.
     *
     * @param instanceId The instance ID
     * @param boardId The ID of the {@link is.yarr.qilletni.board.Board} this component is a part of
     */
    public LastFmCollectionComponent(UUID instanceId, UUID boardId) {
        super(instanceId, boardId);
    }

    @Override
    public boolean isInitialized() {
        return true;
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
     * Gets the {@link LastFmCollectionType} of this component, which determines what songs are fetched.
     *
     * @return The {@link LastFmCollectionType}
     */
    public LastFmCollectionType getCollectionType() {
        return collectionType;
    }

    /**
     * Sets the {@link LastFmCollectionType} of the component, which determines what songs are fetched.
     *
     * @param collectionType The {@link LastFmCollectionType}
     */
    public void setCollectionType(LastFmCollectionType collectionType) {
        this.collectionType = collectionType;
    }

    /**
     * An enum to determine what data is being fetched from LastFm.
     */
    public enum LastFmCollectionType {

        /**
         * The user's top tracks.
         */
        TOP_TRACKS,

        /**
         * The user's loved tracks.
         */
        LOVED_TRACKS,

        /**
         * The user's top artists.
         */
        TOP_ARTIST,

        /**
         * The user's top albums.
         */
        TOP_ALBUM
    }
}
