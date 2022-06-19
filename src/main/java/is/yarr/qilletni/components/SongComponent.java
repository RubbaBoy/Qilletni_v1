package is.yarr.qilletni.components;

import is.yarr.qilletni.database.converters.SpotifySongIdConverter;
import is.yarr.qilletni.music.SongId;

import javax.persistence.Convert;
import javax.persistence.Entity;
import java.util.UUID;

/**
 * A component that allows the queuing of an individual (client-specified) song once.
 */
@Entity(name = "song_component")
public class SongComponent extends Component {

    @Convert(converter = SpotifySongIdConverter.class)
    private SongId songId;

    protected SongComponent() {}

    /**
     * Creates a {@link SongComponent} with a given instance ID.
     *
     * @param instanceId The instance ID
     * @param boardId The ID of the {@link is.yarr.qilletni.board.Board} this component is a part of
     */
    public SongComponent(UUID instanceId, UUID boardId) {
        super(instanceId, boardId);
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
