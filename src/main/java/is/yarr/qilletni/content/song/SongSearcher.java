package is.yarr.qilletni.content.song;

import is.yarr.qilletni.music.Song;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Handles the searching of songs.
 */
public interface SongSearcher {

    /**
     * Searches Spotify for songs with the given query.
     *
     * @param query  The query to search
     * @param limit  The maximum amount of songs
     * @param offset The offset of the songs to return
     * @return The searched songs
     */
    CompletableFuture<List<Song>> searchSong(String query, int limit, int offset);

}
