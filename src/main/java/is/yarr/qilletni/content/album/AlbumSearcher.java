package is.yarr.qilletni.content.album;

import is.yarr.qilletni.music.Album;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Handles the searching of albums.
 */
public interface AlbumSearcher {

    /**
     * Searches Spotify for albums with the given query.
     *
     * @param query  The query to search
     * @param limit  The maximum amount of albums to return
     * @param offset The offset of the albums to return
     * @return The searched albums
     */
    CompletableFuture<List<Album>> searchAlbums(String query, int limit, int offset);

}
