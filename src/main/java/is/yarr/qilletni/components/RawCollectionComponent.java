package is.yarr.qilletni.components;

import is.yarr.qilletni.database.converters.SpotifySongIdListConverter;
import is.yarr.qilletni.music.SongId;

import javax.persistence.Convert;
import javax.persistence.Entity;
import java.util.List;
import java.util.UUID;

/**
 * A component to play a set list of songs.
 */
@Entity(name = "raw_collection_component")
public class RawCollectionComponent extends Component {

    private boolean isSequential = true;

    @Convert(converter = SpotifySongIdListConverter.class)
    private List<SongId> songs;

    protected RawCollectionComponent() {}

    /**
     * Creates a base {@link Component} with a given instance ID.
     *
     * @param instanceId The instance ID
     */
    public RawCollectionComponent(UUID instanceId) {
        super(instanceId);
    }

    @Override
    boolean isInitialized() {
        return songs != null;
    }

    /**
     * Gets if the component should play its songs sequentially ({@code true}) or on shuffle ({@code false}).
     *
     * @return If the contents are sequential
     */
    public boolean isSequential() {
        return isSequential;
    }

    /**
     * Sets if the songs in the component should be played sequentially.
     *
     * @param sequential If the songs are sequential
     */
    public void setSequential(boolean sequential) {
        isSequential = sequential;
    }

    /**
     * Gets the songs in the collection.
     *
     * @return The songs in the collection
     */
    public SongId[] getSongs() {
        return songs.toArray(SongId[]::new);
    }

    /**
     * Sets all the songs to be in the collection.
     *
     * @param songs The songs to set
     */
    public void setSongs(SongId[] songs) {
        this.songs = List.of(songs);
    }
}
