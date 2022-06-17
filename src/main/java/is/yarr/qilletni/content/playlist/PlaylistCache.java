package is.yarr.qilletni.content.playlist;

import is.yarr.qilletni.music.Playlist;
import is.yarr.qilletni.music.PlaylistId;

import java.util.concurrent.CompletableFuture;

/**
 * Allows for getting playlists from either an internal cache or, if needed, from an external source.
 */
public interface PlaylistCache {

    /**
     * Gets a {@link Playlist} from the internal cache, or if it's not found, from an external source, caching it
     * afterwards.
     *
     * @param playlistId The ID of the playlist to get
     * @return The found playlist
     */
    CompletableFuture<Playlist> getPlaylist(PlaylistId playlistId);

    /**
     * Gets the songs of a playlist with the given ID, with an offset and limit of the returned songs.
     *
     * @param playlistId The ID of the playlist to fetch from
     * @param offset     The amount of songs to skip
     * @param limit      The maximum amount of songs to return
     * @return The found songs
     */
    CompletableFuture<PagedSongs> getPlaylistSongs(PlaylistId playlistId, int offset, int limit);

}
