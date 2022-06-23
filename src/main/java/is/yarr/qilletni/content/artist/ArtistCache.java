package is.yarr.qilletni.content.artist;

import is.yarr.qilletni.music.Artist;
import is.yarr.qilletni.music.ArtistId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Allows for getting artists from either an internal cache or, if needed, from an external source.
 */
// TODO: QTNB-18: Implement this & related classes
public interface ArtistCache {

    /**
     * Gets a {@link Artist} from the internal cache, or if it's not found, from an external source, caching it
     * afterwards.
     *
     * @param artistId The ID of the artist to get
     * @return The found artist
     */
    CompletableFuture<Artist> getArtist(ArtistId artistId);

    /**
     * Gets a list of {@link Artist}s from the internal cache, or if any not found, from an external source, caching
     * anything necessary afterwards. The resulting list will be immutable, and order is not guaranteed.
     *
     * @param artistIds The ID of the artists to get
     * @return An immutable, unordered list of found artists
     */
    CompletableFuture<List<Artist>> getArtists(List<ArtistId> artistIds);

    @Service
    class Stub implements ArtistCache {

        @Override
        public CompletableFuture<Artist> getArtist(ArtistId artistId) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override
        public CompletableFuture<List<Artist>> getArtists(List<ArtistId> artistIds) {
            throw new UnsupportedOperationException("Not implemented");
        }
    }
}
