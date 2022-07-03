package is.yarr.qilletni.grpc.search;

import io.grpc.stub.StreamObserver;
import is.yarr.qilletni.content.album.AlbumSearcher;
import is.yarr.qilletni.content.artist.ArtistSearcher;
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
import org.springframework.security.access.annotation.Secured;

import static is.yarr.qilletni.grpc.security.Authorities.GENERAL;

@GrpcService
public class SpotifySearchService extends SpotifySearchServiceGrpc.SpotifySearchServiceImplBase {

    private static final int DEFAULT_LIMIT = 10;

    private final SongSearcher songSearcher;
    private final PlaylistSearcher playlistSearcher;
    private final AlbumSearcher albumSearcher;
    private final ArtistSearcher artistSearcher;

    public SpotifySearchService(SongSearcher songSearcher, PlaylistSearcher playlistSearcher, AlbumSearcher albumSearcher, ArtistSearcher artistSearcher) {
        this.songSearcher = songSearcher;
        this.playlistSearcher = playlistSearcher;
        this.albumSearcher = albumSearcher;
        this.artistSearcher = artistSearcher;
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
                .thenApply(playlists -> playlists.stream().map(GRPCEntityFactory::createGRPCPlaylist).toList())
                .thenAccept(grpcPlaylists -> responseObserver.onNext(SpotifyPlaylistResponse.newBuilder()
                        .addAllPlaylists(grpcPlaylists)
                        .build()))
                .whenComplete(($, throwable) ->
                        ResponseUtility.terminallyReportError(throwable, responseObserver, "An exception occurred while searching for playlists",
                                responseError -> SpotifyPlaylistResponse.newBuilder().setError(responseError).build()));
    }

    @Override
    @Secured({GENERAL})
    public void searchAlbums(SpotifySearchQuery request, StreamObserver<SpotifyAlbumResponse> responseObserver) {
        var query = request.getQuery();
        var limit = request.hasLimit() ? request.getLimit() : DEFAULT_LIMIT;
        var offset = request.hasOffset() ? request.getOffset() : 0;

        if (query.isBlank()) {
            responseObserver.onNext(SpotifyAlbumResponse.newBuilder()
                    .setError(ResponseUtility.createError("Invalid query", 400)).build());
            responseObserver.onCompleted();
            return;
        }

        albumSearcher.searchAlbums(query, limit, offset)
                .thenApply(albums -> albums.stream().map(GRPCEntityFactory::createGRPCAlbum).toList())
                .thenAccept(grpcAlbums -> responseObserver.onNext(SpotifyAlbumResponse.newBuilder()
                        .addAllAlbums(grpcAlbums)
                        .build()))
                .whenComplete(($, throwable) ->
                        ResponseUtility.terminallyReportError(throwable, responseObserver, "An exception occurred while searching for albums",
                                responseError -> SpotifyAlbumResponse.newBuilder().setError(responseError).build()));
    }

    @Override
    @Secured({GENERAL})
    public void searchArtists(SpotifySearchQuery request, StreamObserver<SpotifyArtistResponse> responseObserver) {
        var query = request.getQuery();
        var limit = request.hasLimit() ? request.getLimit() : DEFAULT_LIMIT;
        var offset = request.hasOffset() ? request.getOffset() : 0;

        if (query.isBlank()) {
            responseObserver.onNext(SpotifyArtistResponse.newBuilder()
                    .setError(ResponseUtility.createError("Invalid query", 400)).build());
            responseObserver.onCompleted();
            return;
        }

        artistSearcher.searchArtists(query, limit, offset)
                .thenApply(artists -> artists.stream().map(GRPCEntityFactory::createGRPCArtist).toList())
                .thenAccept(grpcArtists -> responseObserver.onNext(SpotifyArtistResponse.newBuilder()
                        .addAllArtists(grpcArtists)
                        .build()))
                .whenComplete(($, throwable) ->
                        ResponseUtility.terminallyReportError(throwable, responseObserver, "An exception occurred while searching for artists",
                                responseError -> SpotifyArtistResponse.newBuilder().setError(responseError).build()));
    }
}
