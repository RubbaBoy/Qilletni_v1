package is.yarr.qilletni.content.playlist;

import is.yarr.qilletni.database.repositories.external.PlaylistRepository;
import is.yarr.qilletni.music.Playlist;
import is.yarr.qilletni.music.PlaylistId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class DatabasePlaylistCache implements PlaylistCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabasePlaylistCache.class);

    private final PlaylistRetriever playlistRetriever;
    private final PlaylistRepository playlistRepository;

    public DatabasePlaylistCache(PlaylistRetriever playlistRetriever, PlaylistRepository playlistRepository) {
        this.playlistRetriever = playlistRetriever;
        this.playlistRepository = playlistRepository;
    }

    @Async
    @Override
    public CompletableFuture<Playlist> getPlaylist(PlaylistId playlistId) {
        LOGGER.debug("Getting playlist: {}", playlistId.id());
        var playlistOptional = playlistRepository.findById(playlistId);

        return playlistOptional.map(CompletableFuture::completedFuture)
                .orElseGet(() -> playlistRetriever.fetchPlaylist(playlistId)
                        .thenApply(playlistRepository::save));
    }

    /**
     * This implementation doesn't actually cache anything, as it wasn't really deemed necessary to the extreme
     * complications of playlists updating, and existing songs in the cache.
     *
     * @param playlistId The ID of the playlist to fetch from
     * @param offset     The amount of songs to skip
     * @param limit      The maximum amount of songs to return
     * @return The fetched playlist songs
     */
    @Override
    public CompletableFuture<PagedSongs> getPlaylistSongs(PlaylistId playlistId, int offset, int limit) {
        LOGGER.debug("Fetching playlist songs: {} (offset = {}, limit = {})", playlistId.id(), offset, limit);

        return playlistRetriever.fetchPlaylistSongs(playlistId, offset, limit);
    }
}
