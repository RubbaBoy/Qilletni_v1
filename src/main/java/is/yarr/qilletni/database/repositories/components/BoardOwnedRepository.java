package is.yarr.qilletni.database.repositories.components;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface BoardOwnedRepository<T, ID> extends CrudRepository<T, ID> {

    /**
     * Acquires the component {@link T} with the given component instance ID, owned by the supplied owner ID. This is
     * to ensure the user has access to the component. The board ID is not checked in this, as UUIDs don't really have
     * a need for clash checking or anything.
     *
     * @param componentId The instance ID of the component to fetch
     * @param ownerId     The owner of the component
     * @return The found component, if any
     */
    Optional<T> findComponentOwnedBy(UUID componentId, String ownerId);

    /**
     * Finds all components in the repository owned by the supplied board.
     *
     * @param boardId The board the components should be a part of
     * @return All components owned by the board
     */
    List<T> findAllByBoardId(UUID boardId);
}
