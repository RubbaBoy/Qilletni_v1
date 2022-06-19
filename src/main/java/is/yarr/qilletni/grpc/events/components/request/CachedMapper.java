package is.yarr.qilletni.grpc.events.components.request;

import is.yarr.qilletni.components.Component;
import is.yarr.qilletni.components.ForComponent;
import is.yarr.qilletni.components.FunctionComponent;
import is.yarr.qilletni.components.LastFmCollectionComponent;
import is.yarr.qilletni.components.RawCollectionComponent;
import is.yarr.qilletni.components.SongComponent;
import is.yarr.qilletni.components.SpotifyCollectionComponent;
import is.yarr.qilletni.content.song.SongCache;
import is.yarr.qilletni.grpc.gen.request.ComponentBase;
import is.yarr.qilletni.grpc.gen.request.ComponentResponse;
import is.yarr.qilletni.grpc.gen.request.ForLoopComponentResponse;
import is.yarr.qilletni.grpc.gen.request.FunctionComponentResponse;
import is.yarr.qilletni.grpc.gen.request.LastFmCollectionComponentResponse;
import is.yarr.qilletni.grpc.gen.request.LastFmCollectionType;
import is.yarr.qilletni.grpc.gen.request.RawCollectionComponentResponse;
import is.yarr.qilletni.grpc.gen.request.Song;
import is.yarr.qilletni.grpc.gen.request.SongComponentResponse;
import is.yarr.qilletni.grpc.gen.request.SpotifyCollectionComponentResponse;
import is.yarr.qilletni.grpc.gen.request.SpotifyCollectionType;
import is.yarr.qilletni.music.SongId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.awt.Color;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A class to map DAO components to DTO gRPC objects. This class retrieves all necessary entities such as songs, albums,
 * etc. and caches them into an internal map so component creation is faster and synchronous.
 */
public class CachedMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(CachedMapper.class);
    private static final Executor executor = Executors.newCachedThreadPool();

    public static final int COLOR_NOT_FOUND = -1;

    private final Map<UUID, Component> componentMap;
    private final Map<String, is.yarr.qilletni.music.Song> songs;

    private CachedMapper(Map<UUID, Component> componentMap, Map<String, is.yarr.qilletni.music.Song> songs) {
        this.componentMap = componentMap;
        this.songs = songs;
    }

    /**
     * Creates an instance of a {@link CachedMapper}, initializing all necessary caches, so the instance will not need
     * to make any database or external service calls.
     *
     * @param componentMap A map of all {@link Component}s and their instance IDs
     * @param songCache    The {@link SongCache} of the system
     * @return The created {@link CachedMapper}
     */
    public static CompletableFuture<CachedMapper> createMapper(Map<UUID, Component> componentMap, SongCache songCache) {
        return CompletableFuture.supplyAsync(() -> {
            var songIds = componentMap.values().stream()
                    .flatMap(component -> Stream.of(switch (component) {
                        case SongComponent songComponent -> new SongId[]{songComponent.getSongId()};
                        case RawCollectionComponent rawCollectionComponent -> rawCollectionComponent.getSongs();
                        default -> new SongId[0];
                    })).toList();

            var songs = songCache.getSongs(songIds).join()
                    .stream()
                    .collect(Collectors.toMap(is.yarr.qilletni.music.Song::getId, Function.identity()));

            return new CachedMapper(componentMap, songs);
        }, executor);
    }

    /**
     * Creates a list of {@link ComponentResponse}s of all the {@link FunctionComponent}s in the component map.
     *
     * @return The base {@link ComponentResponse}s
     */
    public List<ComponentResponse> createBaseResponses() {
        return componentMap.values().stream()
                .filter(FunctionComponent.class::isInstance)
                .map(this::createComponentResponse)
                .toList();
    }

    /**
     * Creates a {@link ComponentResponse} for an individual {@link Component}. If the component contains any children,
     * it recursively invokes this method until no children remain.
     *
     * @param component The component to convert
     * @return The created {@link ComponentResponse}
     */
    public ComponentResponse createComponentResponse(Component component) {
        var response = ComponentResponse.newBuilder()
                .setBase(createBase(component));

        return (switch (component) {
            case SongComponent songComponent -> createSongResponse(response, songComponent);
            case ForComponent forComponent -> createForLoopResponse(response, forComponent);
            case FunctionComponent functionComponent -> createFunctionResponse(response, functionComponent);
            case RawCollectionComponent rawCollectionComponent ->
                    createRawCollectionResponse(response, rawCollectionComponent);
            case LastFmCollectionComponent lastFmComponent -> createLastFmCollectionResponse(response, lastFmComponent);
            case SpotifyCollectionComponent spotifyComponent ->
                    createSpotifyCollectionResponse(response, spotifyComponent);
            default -> throw new IllegalStateException("Unexpected value: " + component);
        }).build();
    }

    /**
     * Adds a created {@link SongComponentResponse} to a given {@link ComponentResponse.Builder}, returning it
     * afterwards.
     *
     * @param response  The response to
     * @param component The {@link SongComponent} to convert
     * @return The given {@link ComponentResponse.Builder}
     */
    private ComponentResponse.Builder createSongResponse(ComponentResponse.Builder response, SongComponent component) {
        return response.setSong(SongComponentResponse.newBuilder()
                .setSong(createGRPCSong(component.getSongId()))
                .build());
    }

    /**
     * Adds a created {@link ForLoopComponentResponse} to a given {@link ComponentResponse.Builder}, returning it
     * afterwards.
     *
     * @param response  The response to
     * @param component The {@link ForComponent} to convert
     * @return The given {@link ComponentResponse.Builder}
     */
    private ComponentResponse.Builder createForLoopResponse(ComponentResponse.Builder response, ForComponent component) {
        var forComponentBuilder = ForLoopComponentResponse.newBuilder()
                .addAllChildren(createChildren(component.getChildren()));

        switch (component.getLoopStrategy()) {
            case ITERATIONS -> component.getIterations().ifPresent(forComponentBuilder::setIterations);
            case DURATION -> component.getDuration().ifPresent(forComponentBuilder::setDuration);
            case ABS_TIME -> component.getAbsTime().map(Date::getTime).ifPresent(forComponentBuilder::setAbsTime);
        }

        return response.setForLoop(forComponentBuilder.build());
    }

    /**
     * Adds a created {@link FunctionComponentResponse} to a given {@link ComponentResponse.Builder}, returning it
     * afterwards.
     *
     * @param response  The response to
     * @param component The {@link FunctionComponent} to convert
     * @return The given {@link ComponentResponse.Builder}
     */
    private ComponentResponse.Builder createFunctionResponse(ComponentResponse.Builder response, FunctionComponent component) {
        return response.setFunctionComponent(FunctionComponentResponse.newBuilder()
                .setName(component.getName())
                .addAllChildren(createChildren(component.getChildren())));
    }

    /**
     * Adds a created {@link RawCollectionComponentResponse} to a given {@link ComponentResponse.Builder}, returning it
     * afterwards.
     *
     * @param response  The response to
     * @param component The {@link RawCollectionComponent} to convert
     * @return The given {@link ComponentResponse.Builder}
     */
    private ComponentResponse.Builder createRawCollectionResponse(ComponentResponse.Builder response, RawCollectionComponent component) {
        return response.setRawCollection(RawCollectionComponentResponse.newBuilder()
                .setSequential(component.isSequential())
                .addAllSongs(Arrays.stream(component.getSongs()).map(this::createGRPCSong).toList())
                .build());
    }

    /**
     * Adds a created {@link LastFmCollectionComponentResponse} to a given {@link ComponentResponse.Builder}, returning it
     * afterwards.
     *
     * @param response  The response to
     * @param component The {@link LastFmCollectionComponent} to convert
     * @return The given {@link ComponentResponse.Builder}
     */
    private ComponentResponse.Builder createLastFmCollectionResponse(ComponentResponse.Builder response, LastFmCollectionComponent component) {
        var componentBuilder = LastFmCollectionComponentResponse.newBuilder()
                .setSequential(component.isSequential());

        component.getLimit().ifPresent(componentBuilder::setLimit);

        var collectionType = component.getCollectionType();
        componentBuilder.setCollectionType(convertLastFmTypeToGRPC(collectionType));

        return response.setLastFmCollection(componentBuilder.build());
    }

    /**
     * Adds a created {@link SpotifyCollectionComponent} to a given {@link ComponentResponse.Builder}, returning it
     * afterwards.
     *
     * @param response  The response to
     * @param component The {@link SpotifyCollectionComponentResponse} to convert
     * @return The given {@link ComponentResponse.Builder}
     */
    private ComponentResponse.Builder createSpotifyCollectionResponse(ComponentResponse.Builder response, SpotifyCollectionComponent component) {
        var componentBuilder = SpotifyCollectionComponentResponse.newBuilder()
                .setSequential(component.isSequential());

        component.getLimit().ifPresent(componentBuilder::setLimit);

        var collectionType = component.getCollectionType()
                .orElseThrow(UninitializedComponentException::new);
        componentBuilder.setCollectionType(convertSpotifyTypeToGRPC(collectionType));

        return response.setSpotifyCollection(componentBuilder.build());
    }

    /**
     * Creates a {@link ComponentBase} with values found in all {@link Component}s.
     *
     * @param component The base superclass {@link Component}
     * @return The created {@link ComponentBase} to be added to all structural responses
     */
    public ComponentBase createBase(Component component) {
        return ComponentBase.newBuilder()
                .setColor(getRGBFromColor(component.getColor()))
                .setComponentId(component.getInstanceId().toString())
                .build();
    }

    /**
     * Gets the component in the {@link #componentMap} and maps them to {@link ComponentResponse} with
     * {@link #createComponentResponse(Component)}.
     *
     * @param childrenIds The component instance IDs
     * @return The created {@link ComponentResponse}s, in the same order as the input array
     */
    private List<ComponentResponse> createChildren(UUID[] childrenIds) {
        return Arrays.stream(childrenIds)
                .map(componentMap::get)
                .map(this::createComponentResponse).toList();
    }

    /**
     * Creates a gRPC DTO {@link Song} object from a {@link SongId} cached in the {@link #createMapper(Map, SongCache)}
     * method.
     *
     * @param songId The ID of the song to convert
     * @return The {@link Song} with the cached data
     */
    private Song createGRPCSong(SongId songId) {
        var cachedSong = songs.get(songId.id());
        return Song.newBuilder()
                .setId(cachedSong.getId())
                .setName(cachedSong.getName())
                .setArtist(cachedSong.getArtist())
                .setArtworkUrl(cachedSong.getArtworkUrl())
                .build();
    }

    /**
     * Converts a component {@link is.yarr.qilletni.components.spotify.SpotifyCollectionType} to a gRPC DTO
     * {@link SpotifyCollectionType}.
     *
     * @param spotifyCollectionType The collection type
     * @return The gRPC mapped type
     */
    private SpotifyCollectionType convertSpotifyTypeToGRPC(is.yarr.qilletni.components.spotify.SpotifyCollectionType spotifyCollectionType) {
        return SpotifyCollectionType.valueOf(spotifyCollectionType.name());
    }

    /**
     * Converts a component {@link LastFmCollectionComponent.LastFmCollectionType} to a gRPC DTO
     * {@link LastFmCollectionType}.
     *
     * @param lastFmCollectionType The collection type
     * @return The gRPC mapped type
     */
    private LastFmCollectionType convertLastFmTypeToGRPC(LastFmCollectionComponent.LastFmCollectionType lastFmCollectionType) {
        return LastFmCollectionType.valueOf(lastFmCollectionType.name());
    }

    /**
     * Converts a {@link Color} to its RGB value, or if null, {@link #COLOR_NOT_FOUND}.
     *
     * @param color The color to convert
     * @return The RGB value
     */
    private int getRGBFromColor(@Nullable Color color) {
        if (color == null) {
            return COLOR_NOT_FOUND;
        }

        return color.getRGB();
    }

    /**
     * A runtime exception if a {@link Component} is uninitialized with {@link Component#isInitialized()}.
     */
    static class UninitializedComponentException extends RuntimeException {}
}
