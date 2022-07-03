package is.yarr.qilletni.content.playlist;

import is.yarr.qilletni.content.song.SongFactory;
import is.yarr.qilletni.music.Playlist;
import is.yarr.qilletni.spotify.SpotifyApiFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class SpotifyPlaylistSearcher implements PlaylistSearcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpotifyPlaylistSearcher.class);

    private final SpotifyApiFactory spotifyApiFactory;

    public SpotifyPlaylistSearcher(SpotifyApiFactory spotifyApiFactory) {
        this.spotifyApiFactory = spotifyApiFactory;
    }

    @Override
    public CompletableFuture<List<Playlist>> searchPlaylists(String query, int limit, int offset) {
        var spotifyApi = spotifyApiFactory.createClientCredentialsApi();

        LOGGER.debug("Searching playlists for: {}", query);

        return spotifyApi.searchPlaylists(query)
                .limit(limit)
                .offset(offset)
                .build()
                .executeAsync()
                .thenApply(playlistPaging -> Arrays.stream(playlistPaging.getItems()).map(PlaylistFactory::createPlaylist).toList());    }
}
