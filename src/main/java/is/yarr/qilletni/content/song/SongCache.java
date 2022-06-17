package is.yarr.qilletni.content.song;

import is.yarr.qilletni.music.Song;
import is.yarr.qilletni.music.SongId;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Allows for getting songs from either an internal cache or, if needed, from an external source.
 */
public interface SongCache {

    /**
     * Gets a {@link Song} from the internal cache, or if it's not found, from an external source, caching it
     * afterwards.
     *
     * @param songId The ID of the song to get
     * @return The found song
     */
    CompletableFuture<Song> getSong(SongId songId);

    /**
     * Gets a list of {@link Song}s from the internal cache, or if any not found, from an external source, caching
     * anything necessary afterwards. The resulting list will be immutable, and order is not guaranteed.
     *
     * @param songIds The ID of the songs to get
     * @return An immutable, unordered list of found songs
     */
    CompletableFuture<List<Song>> getSongs(List<SongId> songIds);

    /**
     * Gets a list of {@link Song}s from the internal cache, or if any not found, from an external source, caching
     * anything necessary afterwards. The resulting list will be immutable, with the order being the same as the input
     * ID list.
     *
     * @param songIds The ID of the songs to get
     * @return An immutable, ordered list of found songs
     */
    CompletableFuture<List<Song>> getSongsOrdered(List<SongId> songIds);

}
