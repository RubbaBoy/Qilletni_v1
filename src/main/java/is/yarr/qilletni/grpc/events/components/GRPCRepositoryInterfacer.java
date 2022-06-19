package is.yarr.qilletni.grpc.events.components;

import io.grpc.stub.StreamObserver;
import is.yarr.qilletni.components.Component;
import is.yarr.qilletni.database.repositories.BoardRepository;
import is.yarr.qilletni.database.repositories.components.BoardOwnedRepository;
import is.yarr.qilletni.grpc.events.ResponseUtility;
import is.yarr.qilletni.grpc.gen.CreateComponentResponse;
import is.yarr.qilletni.grpc.gen.CreateEvent;
import is.yarr.qilletni.grpc.gen.EmptyResponse;
import is.yarr.qilletni.grpc.gen.ResponseError;
import is.yarr.qilletni.grpc.security.UserSessionAuthenticationToken;
import is.yarr.qilletni.grpc.security.UserSessionSecurityContext;
import is.yarr.qilletni.user.UserInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * A helper class to wrap a {@link CrudRepository} and remove associated gRPC boilerplate.
 *
 * @param <T> The component type being fetched
 */
public class GRPCRepositoryInterfacer<T extends Component> {

    private final BoardOwnedRepository<T, UUID> repository;
    private final BoardRepository boardRepository;

    /**
     * Creates a {@link GRPCRepositoryInterfacer} with a component's {@link CrudRepository}.
     *
     * @param repository      The repository
     * @param boardRepository The board repository
     */
    public GRPCRepositoryInterfacer(BoardOwnedRepository<T, UUID> repository, BoardRepository boardRepository) {
        this.repository = repository;
        this.boardRepository = boardRepository;
    }

    /**
     * Gets a component by its ID from the supplied {@link #repository}, invokes a consumer, and completes the stream
     * with an empty {@link EmptyResponse}. This is intended to be used for simple operations that don't output
     * anything to the response {@link StreamObserver}. If a component is not found, an {@link EmptyResponse} with a
     * relevant error message is sent to the output.
     *
     * @param componentId       The component ID
     * @param ownerId
     * @param responseObserver  The response {@link StreamObserver} provided by gRPC
     * @param componentConsumer The consumer invoked with the found component
     */
    public void getComponent(UUID componentId, String ownerId, StreamObserver<EmptyResponse> responseObserver, Consumer<T> componentConsumer) {
        repository.findComponentOwnedBy(componentId, ownerId)
                .ifPresentOrElse(component -> {
                            componentConsumer.accept(component);
                            responseObserver.onNext(EmptyResponse.newBuilder().build());
                        },
                        () -> responseObserver.onNext(EmptyResponse.newBuilder()
                                .setError(ResponseError.newBuilder()
                                        .setMessage("No component found with the ID of: " + componentId)
                                        .setCode(404)
                                        .build())
                                .build()));
        responseObserver.onCompleted();
    }

    /**
     * Saves a component of type {@link T}, created with a generated UUID, to the supplied {@link #repository}. A
     * {@link CreateComponentResponse} is then added to the response {@link StreamObserver} which is then closed.
     * It should be noted that this relies on getting the current
     * {@link org.springframework.security.core.context.SecurityContext} via
     * {@link UserSessionSecurityContext#getAuthToken()}.
     *
     * @param componentCreator The function to create a component with the randomly generated supplied UUID
     * @param createEvent      The {@link CreateEvent} that holds the board ID and any other data used for creation
     * @param responseObserver The response {@link StreamObserver} provided by gRPC
     */
    public void createComponent(BiFunction<UUID, UUID, T> componentCreator, CreateEvent createEvent, StreamObserver<CreateComponentResponse> responseObserver) {
        UserSessionAuthenticationToken auth = UserSessionSecurityContext.getAuthToken();
        UserInfo userInfo = auth.getPrincipal();

        var componentId = UUID.randomUUID();
        var boardId = UUID.fromString(createEvent.getBoardId());
        var componentResponseBuilder = CreateComponentResponse.newBuilder();

        if (boardRepository.getBoard(boardId, userInfo.getId()).isEmpty()) {
            componentResponseBuilder
                    .setError(ResponseUtility.createError("The board could not be found", 404));
        } else {
            T component = componentCreator.apply(componentId, boardId);
            repository.save(component);

            componentResponseBuilder.setComponentId(componentId.toString());
        }

        responseObserver.onNext(componentResponseBuilder.build());
        responseObserver.onCompleted();
    }
}
