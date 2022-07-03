package is.yarr.qilletni.grpc.events.components.request.mapper;

import is.yarr.qilletni.components.spotify.SpotifyAlbumData;
import is.yarr.qilletni.components.spotify.SpotifyArtistData;
import is.yarr.qilletni.components.spotify.SpotifyCollectionData;
import is.yarr.qilletni.components.spotify.SpotifyPlaylistData;
import is.yarr.qilletni.content.album.AlbumCache;
import is.yarr.qilletni.content.artist.ArtistCache;
import is.yarr.qilletni.content.playlist.PlaylistCache;
import is.yarr.qilletni.grpc.GRPCEntityFactory;
import is.yarr.qilletni.grpc.gen.request.Album;
import is.yarr.qilletni.grpc.gen.request.Artist;
import is.yarr.qilletni.grpc.gen.request.Playlist;
import is.yarr.qilletni.grpc.gen.request.SpotifyAlbumDataResponse;
import is.yarr.qilletni.grpc.gen.request.SpotifyArtistDataResponse;
import is.yarr.qilletni.grpc.gen.request.SpotifyComponentDataResponse;
import is.yarr.qilletni.grpc.gen.request.SpotifyPlaylistDataResponse;
import is.yarr.qilletni.grpc.gen.request.SpotifySearchData;
import is.yarr.qilletni.grpc.gen.request.SpotifySearchDataResponse;
import is.yarr.qilletni.grpc.gen.request.YearChooser;
import is.yarr.qilletni.grpc.gen.request.YearRange;
import is.yarr.qilletni.music.AlbumId;
import is.yarr.qilletni.music.ArtistId;
import is.yarr.qilletni.music.PlaylistId;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class SpotifyDataGRPCMapper {

    private final AlbumCache albumCache;
    private final ArtistCache artistCache;
    private final PlaylistCache playlistCache;

    public SpotifyDataGRPCMapper(AlbumCache albumCache, ArtistCache artistCache, PlaylistCache playlistCache) {
        this.albumCache = albumCache;
        this.artistCache = artistCache;
        this.playlistCache = playlistCache;
    }

    @Async
    public CompletableFuture<SpotifyComponentDataResponse> createResponseFromData(SpotifyCollectionData collectionData) {
        var response = SpotifyComponentDataResponse.newBuilder();

        switch (collectionData) {
            case SpotifyAlbumData albumData -> response.setAlbumData(createAlbumResponse(albumData));
            case SpotifyArtistData artistData -> response.setArtistData(createArtistResponse(artistData));
            case SpotifyPlaylistData playlistData -> response.setPlaylistData(createPlaylistResponse(playlistData));
            case is.yarr.qilletni.components.spotify.SpotifySearchData searchData -> response.setSearchData(createSearchResponse(searchData));
            default -> throw new IllegalStateException("Unexpected value: " + collectionData);
        }

        return CompletableFuture.completedFuture(response.build());
    }

    /**
     * Creates a {@link SpotifyAlbumDataResponse} from a component's {@link SpotifyAlbumData}.
     *
     * @param albumData The data to add to the response
     * @return The created response to be added to a {@link SpotifyComponentDataResponse}
     */
    private SpotifyAlbumDataResponse createAlbumResponse(SpotifyAlbumData albumData) {
        return SpotifyAlbumDataResponse.newBuilder()
                .setAlbum(createGRPCAlbumFromId(albumData.getAlbumId()))
                .build();
    }

    /**
     * Creates a {@link SpotifyArtistDataResponse} from a component's {@link SpotifyArtistData}.
     *
     * @param artistData The data to add to the response
     * @return The created response to be added to a {@link SpotifyComponentDataResponse}
     */
    private SpotifyArtistDataResponse createArtistResponse(SpotifyArtistData artistData) {
        return SpotifyArtistDataResponse.newBuilder()
                .setArtist(createGRPCArtistFromId(artistData.getArtistId()))
                .build();
    }

    /**
     * Creates a {@link SpotifyPlaylistDataResponse} from a component's {@link SpotifyPlaylistData}.
     *
     * @param playlistData The data to add to the response
     * @return The created response to be added to a {@link SpotifyComponentDataResponse}
     */
    private SpotifyPlaylistDataResponse createPlaylistResponse(SpotifyPlaylistData playlistData) {
        return SpotifyPlaylistDataResponse.newBuilder()
                .setPlaylist(createGRPCPlaylistFromId(playlistData.getPlaylistId()))
                .build();
    }

    /**
     * Creates a {@link SpotifySearchDataResponse} from a component's {@link is.yarr.qilletni.components.spotify.SpotifySearchData}.
     *
     * @param searchData The data to add to the response
     * @return The created response to be added to a {@link SpotifyComponentDataResponse}
     */
    private SpotifySearchDataResponse createSearchResponse(is.yarr.qilletni.components.spotify.SpotifySearchData searchData) {
        return SpotifySearchDataResponse.newBuilder()
                .setSearchData(createGRPCSearchData(searchData))
                .build();
    }

    /**
     * Creates a gRPC {@link Album} DTO from a given album ID. The album is looked up from the {@link AlbumCache}.
     *
     * @param albumId The album ID to look up
     * @return The created {@link Album}
     */
    private Album createGRPCAlbumFromId(AlbumId albumId) {
        var cachedAlbum = albumCache.getAlbum(albumId).join();
        return GRPCEntityFactory.createGRPCAlbum(cachedAlbum);
    }

    /**
     * Creates a gRPC {@link Artist} DTO from a given artist ID. The artist is looked up from the {@link ArtistCache}.
     *
     * @param artistId The artist ID to look up
     * @return The created {@link Artist}
     */
    private Artist createGRPCArtistFromId(ArtistId artistId) {
        var cachedArtist = artistCache.getArtist(artistId).join();
        return GRPCEntityFactory.createGRPCArtist(cachedArtist);
    }

    /**
     * Creates a gRPC {@link Playlist} DTO from a given playlist ID. The playlist is looked up from the
     * {@link PlaylistCache}.
     *
     * @param playlistId The playlist ID to look up
     * @return The created {@link Playlist}
     */
    private Playlist createGRPCPlaylistFromId(PlaylistId playlistId) {
        var cachedPlaylist = playlistCache.getPlaylist(playlistId).join();
        return GRPCEntityFactory.createGRPCPlaylist(cachedPlaylist);
    }

    /**
     * Creates a gRPC {@link SpotifySearchData} DTO from a given component
     * {@link is.yarr.qilletni.components.spotify.SpotifySearchData}.
     *
     * @param searchData The data to convert into the DTO
     * @return The created {@link SpotifySearchData}
     */
    private SpotifySearchData createGRPCSearchData(is.yarr.qilletni.components.spotify.SpotifySearchData searchData) {
        var builder = SpotifySearchData.newBuilder()
                .setSearchData(searchData.getSearchData());

        searchData.getYearChooser()
                .map(this::createGRPCYearChooser)
                .ifPresent(builder::setYearChooser);

        searchData.getGenre().ifPresent(builder::setGenre);

        return builder.build();
    }

    /**
     * Creates a gRPC {@link YearChooser} DTO from a component's
     * {@link is.yarr.qilletni.components.spotify.YearChooser}.
     *
     * @param yearChooser The year chooser to convert
     * @return The created {@link YearChooser}
     */
    private YearChooser createGRPCYearChooser(is.yarr.qilletni.components.spotify.YearChooser yearChooser) {
        var yearChooserBuilder = YearChooser.newBuilder();

        if (yearChooser.isRange()) {
            var range = yearChooser.getRange();
            yearChooserBuilder.setRange(YearRange.newBuilder()
                            .setStartYear(range.startYear())
                            .setEndYear(range.endYear())
                    .build());
        } else {
            yearChooserBuilder.setYear(yearChooser.getYear());
        }

        return yearChooserBuilder.build();
    }

}
