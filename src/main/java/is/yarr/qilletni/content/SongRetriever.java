package is.yarr.qilletni.content;

import is.yarr.qilletni.music.Song;
import is.yarr.qilletni.music.SongId;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Fetches songs directly from an external source, without any cache or restrictions.
 */
public interface SongRetriever {

    /**
     * Fetches a {@link Song} from an external source with the given ID.
     *
     * @param songId The ID of the song to fetch
     * @return The fetched song
     */
    CompletableFuture<Song> fetchSong(SongId songId);

    /**
     * Fetches a list of {@link Song}s from an external source with a given list of IDs. The resulting list will be
     * immutable, and order is not guaranteed.
     *
     * @param songIds The song IDs to fetch
     * @return An immutable, list of fetched songs
     */
    CompletableFuture<List<Song>> fetchSongs(List<SongId> songIds);

}
