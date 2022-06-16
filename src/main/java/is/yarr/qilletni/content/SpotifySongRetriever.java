package is.yarr.qilletni.content;

import is.yarr.qilletni.music.Song;
import is.yarr.qilletni.music.SongId;
import is.yarr.qilletni.spotify.SpotifyApiFactory;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class SpotifySongRetriever implements SongRetriever {

    private final SpotifyApi spotifyApi;

    public SpotifySongRetriever(SpotifyApiFactory spotifyApiFactory) {
        this.spotifyApi = spotifyApiFactory.createAnonApi();
    }

    @Override
    public CompletableFuture<Song> fetchSong(SongId songId) {
        return spotifyApi.getTrack(songId.id()).build()
                .executeAsync()
                .thenApply(SongFactory::createSong);
    }

    @Override
    public CompletableFuture<List<Song>> fetchSongs(List<SongId> songIds) {
        return spotifyApi.getSeveralTracks(songIds.stream().map(SongId::id).toArray(String[]::new))
                .build()
                .executeAsync()
                .thenApply(tracks -> Arrays.stream(tracks).map(SongFactory::createSong).toList());
    }
}
