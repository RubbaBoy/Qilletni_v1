package is.yarr.qilletni.content.playlist;

import is.yarr.qilletni.music.Playlist;
import is.yarr.qilletni.music.PlaylistId;

import java.util.concurrent.CompletableFuture;

/**
 * Fetches playlists (and their content) directly from an external source (or with the help of other services), without
 * any cache or restrictions on the playlists themselves.
 */
public interface PlaylistRetriever {

    /**
     * Fetches a {@link Playlist} from an external source.
     *
     * @param playlistId The ID of the playlist to get
     * @return The found playlist
     */
    CompletableFuture<Playlist> fetchPlaylist(PlaylistId playlistId);

    /**
     * Fetches the songs of a playlist with the given ID from an external source, with an offset and limit of the
     * returned songs. The resulting list is immutable.
     *
     * @param playlistId The ID of the playlist to fetch from
     * @param offset     The amount of songs to skip
     * @param limit      The maximum amount of songs to return
     * @return The found songs
     */
    CompletableFuture<PagedSongs> fetchPlaylistSongs(PlaylistId playlistId, int offset, int limit);

}
