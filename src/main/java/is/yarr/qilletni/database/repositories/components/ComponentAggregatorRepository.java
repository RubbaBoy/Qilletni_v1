package is.yarr.qilletni.database.repositories.components;

import is.yarr.qilletni.components.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class ComponentAggregatorRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComponentAggregatorRepository.class);

    private final List<BoardOwnedRepository<? extends Component, UUID>> boardOwnedRepositories;

    public ComponentAggregatorRepository(FunctionComponentRepository functionComponentRepository, SongComponentRepository songComponentRepository) {
        this.boardOwnedRepositories = List.of(
                functionComponentRepository,
                songComponentRepository
        );

        LOGGER.debug("Registered {} board-owned repositories", this.boardOwnedRepositories.size());
    }

    /**
     * Collects all components from the given board.
     *
     * @param boardId The board to collect from
     * @return A map of components and their instance IDs
     */
    @Async
    public CompletableFuture<Map<UUID, Component>> getComponentsFromBoard(UUID boardId) {
        var componentMap = boardOwnedRepositories.parallelStream()
                .map(repository -> repository.findAllByBoardId(boardId))
                .flatMap(List::stream)
                .collect(Collectors.toMap(Component::getInstanceId, Component.class::cast));

        return CompletableFuture.completedFuture(componentMap);
    }

}
