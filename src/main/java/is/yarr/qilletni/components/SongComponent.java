package is.yarr.qilletni.components;

import is.yarr.qilletni.music.SongId;

import java.util.UUID;

/**
 * A component that allows the queuing of an individual (client-specified) song once.
 */
public class SongComponent extends Component {

    private SongId songId;

    /**
     * Creates a {@link SongComponent} with a given instance ID.
     *
     * @param instanceId The instance ID
     */
    public SongComponent(UUID instanceId) {
        super(instanceId);
    }

    @Override
    boolean isInitialized() {
        return songId != null;
    }

    /**
     * Gets the {@link SongId} the component represents.
     *
     * @return The {@link SongId}
     */
    public SongId getSongId() {
        return songId;
    }

    /**
     * Sets the {@link SongId} for the component.
     *
     * @param songId The {@link SongId}
     */
    public void setSongId(SongId songId) {
        this.songId = songId;
    }
}
