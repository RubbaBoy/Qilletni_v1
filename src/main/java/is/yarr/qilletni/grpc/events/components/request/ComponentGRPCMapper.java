package is.yarr.qilletni.grpc.events.components.request;

import is.yarr.qilletni.components.Component;
import is.yarr.qilletni.content.song.SongCache;
import is.yarr.qilletni.grpc.gen.request.ComponentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ComponentGRPCMapper {

    private final SongCache songCache;

    public ComponentGRPCMapper(SongCache songCache) {
        this.songCache = songCache;
    }

    /**
     * Creates a list of {@link ComponentResponse}s with the {@link is.yarr.qilletni.components.FunctionComponent}s in
     * the component map. The function's children are populated from the same map.
     *
     * @param componentMap The components mapped to their instance IDs as a key
     * @return The base-level functions
     */
    public CompletableFuture<List<ComponentResponse>> createBaseResponses(Map<UUID, Component> componentMap) {
        return CachedMapper.createMapper(componentMap, songCache)
                .thenApply(CachedMapper::createBaseResponses);
    }
}
