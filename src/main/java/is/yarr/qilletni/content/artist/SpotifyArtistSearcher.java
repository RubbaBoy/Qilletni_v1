package is.yarr.qilletni.content.artist;

import is.yarr.qilletni.music.Artist;
import is.yarr.qilletni.spotify.SpotifyApiFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class SpotifyArtistSearcher implements ArtistSearcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpotifyArtistSearcher.class);

    private final SpotifyApiFactory spotifyApiFactory;

    public SpotifyArtistSearcher(SpotifyApiFactory spotifyApiFactory) {
        this.spotifyApiFactory = spotifyApiFactory;
    }

    @Override
    public CompletableFuture<List<Artist>> searchArtists(String query, int limit, int offset) {
        var spotifyApi = spotifyApiFactory.createClientCredentialsApi();

        LOGGER.debug("Searching artists for: {}", query);

        return spotifyApi.searchArtists(query)
                .limit(limit)
                .offset(offset)
                .build()
                .executeAsync()
                .thenApply(playlistPaging -> Arrays.stream(playlistPaging.getItems()).map(ArtistFactory::createArtist).toList());
    }
}
