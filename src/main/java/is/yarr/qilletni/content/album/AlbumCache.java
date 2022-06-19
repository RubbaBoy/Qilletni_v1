package is.yarr.qilletni.content.album;

import is.yarr.qilletni.music.Album;
import is.yarr.qilletni.music.AlbumId;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Allows for getting albums from either an internal cache or, if needed, from an external source.
 */
// TODO: QTNB-18: Implement this & related classes
public interface AlbumCache {

    /**
     * Gets a {@link Album} from the internal cache, or if it's not found, from an external source, caching it
     * afterwards.
     *
     * @param albumId The ID of the album to get
     * @return The found album
     */
    CompletableFuture<Album> getAlbum(AlbumId albumId);

    /**
     * Gets a list of {@link Album}s from the internal cache, or if any not found, from an external source, caching
     * anything necessary afterwards. The resulting list will be immutable, and order is not guaranteed.
     *
     * @param albumIds The ID of the albums to get
     * @return An immutable, unordered list of found albums
     */
    CompletableFuture<List<Album>> getAlbums(List<AlbumId> albumIds);

}
