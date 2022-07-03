package is.yarr.qilletni.content.artist;

import is.yarr.qilletni.music.Artist;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Handles the searching of artists.
 */
public interface ArtistSearcher {

    /**
     * Searches Spotify for artists with the given query.
     *
     * @param query  The query to search
     * @param limit  The maximum amount of artists to return
     * @param offset The offset of the artists to return
     * @return The searched artists
     */
    CompletableFuture<List<Artist>> searchArtists(String query, int limit, int offset);

}
