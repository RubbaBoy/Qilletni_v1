package is.yarr.qilletni.components.spotify;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class SpotifyCollectionData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    /**
     * Gets the {@link SpotifyCollectionType} of the data, identifying what kind of data it is.
     *
     * @return The {@link SpotifyCollectionType}
     */
    public abstract SpotifyCollectionType getCollectionType();

    /**
     * Checks if all required fields are non-null and valid. If this returns {@code false}, do not expect anything to
     * work.
     *
     * @return If the data is ready to be used
     */
    public abstract boolean isInitialized();

    /**
     * Returns if shuffling is supported on the current collection.
     *
     * @return If shuffling is supported
     */
    public abstract boolean isShuffleSupported();

}
