package is.yarr.qilletni.content.song;

import is.yarr.qilletni.music.Song;
import is.yarr.qilletni.spotify.SpotifyApiFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class SpotifySongSearcher implements SongSearcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpotifySongSearcher.class);

    private final SpotifyApiFactory spotifyApiFactory;

    public SpotifySongSearcher(SpotifyApiFactory spotifyApiFactory) {
        this.spotifyApiFactory = spotifyApiFactory;
    }

    @Override
    public CompletableFuture<List<Song>> searchSong(String query, int limit, int offset) {
        var spotifyApi = spotifyApiFactory.createClientCredentialsApi();

        LOGGER.debug("Searching songs for: {}", query);

        return spotifyApi.searchTracks(query)
                .limit(limit)
                .offset(offset)
                .build()
                .executeAsync()
                .thenApply(trackPaging -> Arrays.stream(trackPaging.getItems()).map(SongFactory::createSong).toList());
    }
}
