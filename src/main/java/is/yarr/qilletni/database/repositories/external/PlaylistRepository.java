package is.yarr.qilletni.database.repositories.external;

import is.yarr.qilletni.database.UnsupportedTypeException;
import is.yarr.qilletni.music.Playlist;
import is.yarr.qilletni.music.PlaylistId;
import is.yarr.qilletni.music.SpotifyPlaylist;
import is.yarr.qilletni.music.SpotifyPlaylistId;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * An adapter for {@link Playlist} repositories' operations.
 */
@Service
public class PlaylistRepository {

    private final SpotifyPlaylistRepository spotifyPlaylistRepository;

    public PlaylistRepository(SpotifyPlaylistRepository spotifyPlaylistRepository) {
        this.spotifyPlaylistRepository = spotifyPlaylistRepository;
    }

    /**
     * Finds a {@link Playlist} from its appropriate repository by the given ID.
     *
     * @param playlistId The ID of the song to find
     * @return The found playlist, if any
     * @throws UnsupportedTypeException If a repository for the given type is not found
     */
    public Optional<Playlist> findById(PlaylistId playlistId) {
        return switch (playlistId) {
            case SpotifyPlaylistId spotifyPlaylistId -> spotifyPlaylistRepository.findById(playlistId.id())
                    .map(Playlist.class::cast);
            default -> throw new UnsupportedTypeException(playlistId);
        };
    }

    /**
     * Saves a {@link Playlist} to its appropriate repository.
     *
     * @param playlist The playlist to save
     * @return The saved playlist
     * @throws UnsupportedTypeException If a repository for the given type is not found
     */
    public Playlist save(Playlist playlist) {
        return switch (playlist) {
            case SpotifyPlaylist spotifyPlaylist -> spotifyPlaylistRepository.save(spotifyPlaylist);
            default -> throw new UnsupportedTypeException(playlist);
        };
    }
}
