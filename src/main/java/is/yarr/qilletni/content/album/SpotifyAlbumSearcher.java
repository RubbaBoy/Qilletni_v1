package is.yarr.qilletni.content.album;

import is.yarr.qilletni.music.Album;
import is.yarr.qilletni.spotify.SpotifyApiFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class SpotifyAlbumSearcher implements AlbumSearcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpotifyAlbumSearcher.class);

    private final SpotifyApiFactory spotifyApiFactory;

    public SpotifyAlbumSearcher(SpotifyApiFactory spotifyApiFactory) {
        this.spotifyApiFactory = spotifyApiFactory;
    }

    @Override
    public CompletableFuture<List<Album>> searchAlbums(String query, int limit, int offset) {
        var spotifyApi = spotifyApiFactory.createClientCredentialsApi();

        LOGGER.debug("Searching albums for: {}", query);

        return spotifyApi.searchAlbums(query)
                .limit(limit)
                .offset(offset)
                .build()
                .executeAsync()
                .thenApply(playlistPaging -> Arrays.stream(playlistPaging.getItems()).map(AlbumFactory::createAlbum).toList());
    }
}
