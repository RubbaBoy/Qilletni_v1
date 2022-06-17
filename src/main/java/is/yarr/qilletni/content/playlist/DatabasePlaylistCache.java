package is.yarr.qilletni.content.playlist;

import is.yarr.qilletni.database.repositories.external.PlaylistRepository;
import is.yarr.qilletni.music.Playlist;
import is.yarr.qilletni.music.PlaylistId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Override
    public CompletableFuture<Playlist> getPlaylist(PlaylistId playlistId) {
        return null;
    }

    @Override
    public CompletableFuture<PagedSongs> getPlaylistSongs(PlaylistId playlistId, int offset, int limit) {
        return null;
    }
}
