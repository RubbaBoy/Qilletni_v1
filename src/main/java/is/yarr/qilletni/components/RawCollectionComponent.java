package is.yarr.qilletni.components;

import is.yarr.qilletni.music.SongId;

import java.util.UUID;

public class RawCollectionComponent extends Component {

    private boolean isSequential;
    private SongId[] songs;

    /**
     * Creates a base {@link Component} with a given instance ID.
     *
     * @param instanceId The instance ID
     */
    protected RawCollectionComponent(UUID instanceId) {
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
        return songs;
    }

    /**
     * Sets all the songs to be in the collection.
     *
     * @param songs The songs to set
     */
    public void setSongs(SongId[] songs) {
        this.songs = songs;
    }
}
