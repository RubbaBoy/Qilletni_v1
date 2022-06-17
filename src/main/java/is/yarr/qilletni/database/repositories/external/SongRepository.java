package is.yarr.qilletni.database.repositories.external;

import is.yarr.qilletni.music.Song;
import is.yarr.qilletni.music.SongId;
import is.yarr.qilletni.music.SpotifySong;
import is.yarr.qilletni.music.SpotifySongId;
import org.springframework.stereotype.Service;

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
        if (!(songId instanceof SpotifySongId)) {
            throw new UnsupportedTypeException(songId);
        }

        return spotifySongRepository.findById(songId.id())
                .map(Song.class::cast);
    }

    /**
     * Saves a {@link Song} to its appropriate repository.
     *
     * @param song The song to save
     * @return The saved song
     * @throws UnsupportedTypeException If a repository for the given type is not found
     */
    public Song save(Song song) {
        if (!(song instanceof SpotifySong)) {
            throw new UnsupportedTypeException(song);
        }

        return spotifySongRepository.save((SpotifySong) song);
    }

    static class UnsupportedTypeException extends RuntimeException {
        UnsupportedTypeException(Object type) {
            super("Operation is unsupported for a class of type: " + type.getClass().getTypeName());
        }
    }
}
