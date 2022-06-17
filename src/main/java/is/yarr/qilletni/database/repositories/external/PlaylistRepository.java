package is.yarr.qilletni.database.repositories.external;

import is.yarr.qilletni.music.Playlist;
import is.yarr.qilletni.music.PlaylistId;
import is.yarr.qilletni.music.Song;
import is.yarr.qilletni.music.SongId;
import is.yarr.qilletni.music.SpotifyPlaylist;
import is.yarr.qilletni.music.SpotifyPlaylistId;
import is.yarr.qilletni.music.SpotifySong;
import is.yarr.qilletni.music.SpotifySongId;
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
    public Optional<Song> findById(PlaylistId playlistId) {
        if (!(playlistId instanceof SpotifyPlaylistId)) {
            throw new UnsupportedTypeException(playlistId);
        }

        return spotifyPlaylistRepository.findById(playlistId.id())
                .map(Song.class::cast);
    }

    /**
     * Saves a {@link Playlist} to its appropriate repository.
     *
     * @param playlist The playlist to save
     * @return The saved playlist
     * @throws UnsupportedTypeException If a repository for the given type is not found
     */
    public Playlist save(Playlist playlist) {
        if (!(playlist instanceof SpotifyPlaylist)) {
            throw new UnsupportedTypeException(playlist);
        }

        return spotifyPlaylistRepository.save((SpotifyPlaylist) playlist);
    }
}
