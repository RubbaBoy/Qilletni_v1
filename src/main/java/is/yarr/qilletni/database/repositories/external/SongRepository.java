package is.yarr.qilletni.database.repositories.external;

import is.yarr.qilletni.database.UnsupportedTypeException;
import is.yarr.qilletni.music.Song;
import is.yarr.qilletni.music.SongId;
import is.yarr.qilletni.music.SpotifySong;
import is.yarr.qilletni.music.SpotifySongId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * An adapter for {@link Song} repositories' operations.
 */
@Service
public class SongRepository {

    private final SpotifySongRepository spotifySongRepository;

    public SongRepository(SpotifySongRepository spotifySongRepository) {
        this.spotifySongRepository = spotifySongRepository;
    }

    /**
     * Finds a {@link Song} from its appropriate repository by the given ID.
     *
     * @param songId The ID of the song to find
     * @return The found song, if any
     * @throws UnsupportedTypeException If a repository for the given type is not found
     */
    public Optional<Song> findById(SongId songId) {
        return switch (songId) {
            case SpotifySongId spotifySongId -> spotifySongRepository.findById(songId.id())
                    .map(Song.class::cast);
            default -> throw new UnsupportedTypeException(songId);
        };
    }

    /**
     * Saves a {@link Song} to its appropriate repository.
     *
     * @param song The song to save
     * @return The saved song
     * @throws UnsupportedTypeException If a repository for the given type is not found
     */
    public Song save(Song song) {
        return switch (song) {
            case SpotifySong spotifySong -> spotifySongRepository.save(spotifySong);
            default -> throw new UnsupportedTypeException(song);
        };
    }

    /**
     * Saves a list of {@link Song}s to their appropriate repository. They must all be the same type.
     *
     * @param songs The songs to save
     * @return The saved songs
     * @throws UnsupportedTypeException If a repository for the given type is not found
     */
    public List<Song> saveAll(List<Song> songs) {
        if (songs.isEmpty()) {
            return songs;
        }

        if (!(songs.get(0) instanceof SpotifySong)) {
            throw new UnsupportedTypeException(songs.get(0));
        }

        spotifySongRepository.saveAll(songs.stream()
                .map(SpotifySong.class::cast).toList());

        return songs;
    }
}
