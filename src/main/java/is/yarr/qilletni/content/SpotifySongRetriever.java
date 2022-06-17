package is.yarr.qilletni.content;

import is.yarr.qilletni.music.Song;
import is.yarr.qilletni.music.SongId;
import is.yarr.qilletni.spotify.SpotifyApiFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class SpotifySongRetriever implements SongRetriever {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpotifySongRetriever.class);

    private final SpotifyApiFactory spotifyApiFactory;

    public SpotifySongRetriever(SpotifyApiFactory spotifyApiFactory) {
        this.spotifyApiFactory = spotifyApiFactory;
    }

    @Override
    public CompletableFuture<Song> fetchSong(SongId songId) {
        var spotifyApi = spotifyApiFactory.createClientCredentialsApi();

        LOGGER.debug("Fetching song: {}", songId.id());
        return spotifyApi.getTrack(songId.id()).build()
                .executeAsync()
                .thenApply(SongFactory::createSong);
    }

    @Override
    public CompletableFuture<List<Song>> fetchSongs(List<SongId> songIds) {
        var spotifyApi = spotifyApiFactory.createClientCredentialsApi();

        LOGGER.debug("Fetching songs: {}", songIds.stream().map(SongId::id).collect(Collectors.joining(", ")));
        return spotifyApi.getSeveralTracks(songIds.stream().map(SongId::id).toArray(String[]::new))
                .build()
                .executeAsync()
                .thenApply(tracks -> Arrays.stream(tracks).map(SongFactory::createSong).toList());
    }
}
