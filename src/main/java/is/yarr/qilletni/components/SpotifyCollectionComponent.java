package is.yarr.qilletni.components;

import is.yarr.qilletni.components.spotify.SpotifySearchOptions;

import java.util.UUID;

public class SpotifyCollectionComponent extends Component {

    private boolean isSequential;
    private Integer limit;
    private SpotifyCollectionType collectionType;
    private SpotifySearchOptions spotifySearchOptions;

    /**
     * Creates a {@link SpotifyCollectionComponent} with a given instance ID.
     *
     * @param instanceId The instance ID
     */
    protected SpotifyCollectionComponent(UUID instanceId) {
        super(instanceId);
        this.spotifySearchOptions = new SpotifySearchOptions();
    }

    @Override
    boolean isInitialized() {
        return false;
    }

    enum SpotifyCollectionType {
        PLAYLIST,
        ARTIST,
        ALBUM,
        SEARCH
    }
}
