package is.yarr.qilletni.grpc.search;

import io.grpc.stub.StreamObserver;
import is.yarr.qilletni.content.playlist.PlaylistSearcher;
import is.yarr.qilletni.content.song.SongSearcher;
import is.yarr.qilletni.grpc.GRPCEntityFactory;
import is.yarr.qilletni.grpc.events.ResponseUtility;
import is.yarr.qilletni.grpc.gen.search.spotify.SpotifyAlbumResponse;
import is.yarr.qilletni.grpc.gen.search.spotify.SpotifyArtistResponse;
import is.yarr.qilletni.grpc.gen.search.spotify.SpotifyPlaylistResponse;
import is.yarr.qilletni.grpc.gen.search.spotify.SpotifySearchQuery;
import is.yarr.qilletni.grpc.gen.search.spotify.SpotifySearchServiceGrpc;
import is.yarr.qilletni.grpc.gen.search.spotify.SpotifySongResponse;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;

import static is.yarr.qilletni.grpc.security.Authorities.GENERAL;

@GrpcService
public class SpotifySearchService extends SpotifySearchServiceGrpc.SpotifySearchServiceImplBase {

    private static final int DEFAULT_LIMIT = 10;

    private static final Logger LOGGER = LoggerFactory.getLogger(SpotifySearchService.class);

    private final SongSearcher songSearcher;
    private final PlaylistSearcher playlistSearcher;

    public SpotifySearchService(SongSearcher songSearcher, PlaylistSearcher playlistSearcher) {
        this.songSearcher = songSearcher;
        this.playlistSearcher = playlistSearcher;
    }

    @Override
    @Secured({GENERAL})
    public void searchSongs(SpotifySearchQuery request, StreamObserver<SpotifySongResponse> responseObserver) {
        var query = request.getQuery();
        var limit = request.hasLimit() ? request.getLimit() : DEFAULT_LIMIT;
        var offset = request.hasOffset() ? request.getOffset() : 0;

        if (query.isBlank()) {
            responseObserver.onNext(SpotifySongResponse.newBuilder()
                    .setError(ResponseUtility.createError("Invalid query", 400)).build());
            responseObserver.onCompleted();
            return;
        }

        songSearcher.searchSongs(query, limit, offset)
                .thenApply(songs -> songs.stream().map(GRPCEntityFactory::createGRPCSong).toList())
                .thenAccept(grpcSongs -> responseObserver.onNext(SpotifySongResponse.newBuilder()
                        .addAllSongs(grpcSongs)
                        .build()))
                .whenComplete(($, throwable) ->
                        ResponseUtility.terminallyReportError(throwable, responseObserver, "An exception occurred while searching for songs",
                                responseError -> SpotifySongResponse.newBuilder().setError(responseError).build()));
    }

    @Override
    @Secured({GENERAL})
    public void searchPlaylists(SpotifySearchQuery request, StreamObserver<SpotifyPlaylistResponse> responseObserver) {
        var query = request.getQuery();
        var limit = request.hasLimit() ? request.getLimit() : DEFAULT_LIMIT;
        var offset = request.hasOffset() ? request.getOffset() : 0;

        if (query.isBlank()) {
            responseObserver.onNext(SpotifyPlaylistResponse.newBuilder()
                    .setError(ResponseUtility.createError("Invalid query", 400)).build());
            responseObserver.onCompleted();
            return;
        }

        playlistSearcher.searchPlaylists(query, limit, offset)
                .thenApply(songs -> songs.stream().map(GRPCEntityFactory::createGRPCPlaylist).toList())
                .thenAccept(grpcSongs -> responseObserver.onNext(SpotifyPlaylistResponse.newBuilder()
                        .addAllPlaylists(grpcSongs)
                        .build()))
                .whenComplete(($, throwable) ->
                        ResponseUtility.terminallyReportError(throwable, responseObserver, "An exception occurred while searching for playlists",
                                responseError -> SpotifyPlaylistResponse.newBuilder().setError(responseError).build()));
    }

    @Override
    @Secured({GENERAL})
    public void searchAlbums(SpotifySearchQuery request, StreamObserver<SpotifyAlbumResponse> responseObserver) {
        // TODO: searchAlbums
    }

    @Override
    @Secured({GENERAL})
    public void searchArtists(SpotifySearchQuery request, StreamObserver<SpotifyArtistResponse> responseObserver) {
        // TODO: searchArtists
    }
}
