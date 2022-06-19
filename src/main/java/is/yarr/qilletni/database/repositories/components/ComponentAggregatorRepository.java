package is.yarr.qilletni.database.repositories.components;

import is.yarr.qilletni.components.Component;
import is.yarr.qilletni.components.FunctionComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ComponentAggregatorRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComponentAggregatorRepository.class);

    private final FunctionComponentRepository functionComponentRepository;
    private final List<BoardOwnedRepository<? extends Component, UUID>> boardOwnedRepositories;

    public ComponentAggregatorRepository(FunctionComponentRepository functionComponentRepository) {
        this.functionComponentRepository = functionComponentRepository;
        this.boardOwnedRepositories = List.of(
                // TODO: Put other component repositories here, when they are more implemented
        );

        LOGGER.debug("Registered {} board-owned repositories", this.boardOwnedRepositories.size());
    }

    /**
     * Collects all components from the given board.
     *
     * @param boardId The board to collect from
     * @return The functions and other components separated
     */
    @Async
    public CompletableFuture<ComponentAggregatorResult> getComponentsFromBoard(UUID boardId) {
        var functionComponentMap = transformToComponentMap(functionComponentRepository.findAllByBoardId(boardId).stream(), Function.identity());

        var componentStream = boardOwnedRepositories.parallelStream()
                .map(repository -> repository.findAllByBoardId(boardId))
                .flatMap(List::stream);

        var componentMap = transformToComponentMap(componentStream, Component.class::cast);

        return CompletableFuture.completedFuture(new ComponentAggregatorResult(functionComponentMap, componentMap));
    }

    private <T extends Component, U extends Component> Map<UUID, T> transformToComponentMap(Stream<U> components, Function<U, T> mapper) {
        return components.collect(Collectors.toMap(Component::getInstanceId, mapper));
    }

    /**
     * A data class to hold function components and everything else, for easy filtering. Each UUID key is the
     * {@link Component#getInstanceId()} of the component.
     *
     * @param functionComponents The functions of the board
     * @param components         All the other non-functions of the board
     */
    record ComponentAggregatorResult(Map<UUID, FunctionComponent> functionComponents, Map<UUID, Component> components) {}

}
