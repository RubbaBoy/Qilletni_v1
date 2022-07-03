package is.yarr.qilletni.content.playlist;

import is.yarr.qilletni.music.Playlist;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Handles the searching of playlists.
 */
public interface PlaylistSearcher {

    /**
     * Searches Spotify for playlists with the given query.
     *
     * @param query  The query to search
     * @param limit  The maximum amount of playlists to return
     * @param offset The offset of the playlists to return
     * @return The searched playlists
     */
    CompletableFuture<List<Playlist>> searchPlaylists(String query, int limit, int offset);

}
