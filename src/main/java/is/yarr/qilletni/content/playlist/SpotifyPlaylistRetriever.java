package is.yarr.qilletni.content.playlist;

import is.yarr.qilletni.content.song.SongFactory;
import is.yarr.qilletni.music.Playlist;
import is.yarr.qilletni.music.PlaylistId;
import is.yarr.qilletni.music.SpotifySongId;
import is.yarr.qilletni.spotify.SpotifyApiFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class SpotifyPlaylistRetriever implements PlaylistRetriever {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpotifyPlaylistRetriever.class);

    private final SpotifyApiFactory spotifyApiFactory;

    public SpotifyPlaylistRetriever(SpotifyApiFactory spotifyApiFactory) {
        this.spotifyApiFactory = spotifyApiFactory;
    }

    @Override
    public CompletableFuture<Playlist> fetchPlaylist(PlaylistId playlistId) {
        var spotifyApi = spotifyApiFactory.createClientCredentialsApi();

        GetPlaylistRequest getPlaylistRequest = spotifyApi.getPlaylist(playlistId.id())
                .build();

        LOGGER.debug("Fetching playlist: {}", playlistId.id());
        return getPlaylistRequest.executeAsync()
                .thenApply(PlaylistFactory::createPlaylist);
    }

    @Override
    public CompletableFuture<PagedSongs> fetchPlaylistSongs(PlaylistId playlistId, int offset, int limit) {
        var spotifyApi = spotifyApiFactory.createClientCredentialsApi();

        GetPlaylistsItemsRequest getPlaylistsItemsRequest = spotifyApi.getPlaylistsItems(playlistId.id())
                .offset(offset)
                .limit(limit)
                .build();

        LOGGER.debug("Fetching playlist songs: {} (offset = {}, limit = {})", playlistId.id(), offset, limit);
        return getPlaylistsItemsRequest.executeAsync()
                .thenApply(paging -> {
                    var songs = Arrays.stream(paging.getItems())
                            .map(PlaylistTrack::getTrack)
                            .filter(Track.class::isInstance)
                            .map(Track.class::cast)
                            .map(SongFactory::createSong)
                            .toList();

                    return new SpotifyPagedSongs(limit, offset, songs);
                });
    }
}
