package is.yarr.qilletni.grpc.events.components;

import io.grpc.stub.StreamObserver;
import is.yarr.qilletni.components.Component;
import is.yarr.qilletni.grpc.gen.CreateComponentResponse;
import is.yarr.qilletni.grpc.gen.EmptyResponse;
import is.yarr.qilletni.grpc.gen.ResponseError;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A helper class to wrap a {@link CrudRepository} and remove associated gRPC boilerplate.
 *
 * @param <T> The component type being fetched
 */
public class GRPCRepositoryInterfacer<T extends Component> {

    private final CrudRepository<T, UUID> repository;

    /**
     * Creates a {@link GRPCRepositoryInterfacer} with a component's {@link CrudRepository}.
     *
     * @param repository The repository
     */
    public GRPCRepositoryInterfacer(CrudRepository<T, UUID> repository) {
        this.repository = repository;
    }

    /**
     * Gets a component by its ID from the supplied {@link #repository}, invokes a consumer, and completes the stream
     * with an empty {@link EmptyResponse}. This is intended to be used for simple operations that don't output
     * anything to the response {@link StreamObserver}. If a component is not found, an {@link EmptyResponse} with a
     * relevant error message is sent to the output.
     *
     * @param componentId       The component ID
     * @param responseObserver  The response {@link StreamObserver} provided by gRPC
     * @param componentConsumer The consumer invoked with the found component
     */
    public void getComponent(UUID componentId, StreamObserver<EmptyResponse> responseObserver, Consumer<T> componentConsumer) {
        repository.findById(componentId)
                .ifPresentOrElse(component -> {
                            componentConsumer.accept(component);
                            responseObserver.onNext(EmptyResponse.newBuilder().build());
                        },
                        () -> responseObserver.onNext(EmptyResponse.newBuilder()
                                .setError(ResponseError.newBuilder()
                                        .setMessage("No component found with the ID of: " + componentId)
                                        .build())
                                .build()));
        responseObserver.onCompleted();
    }

    /**
     * Saves a component of type {@link T}, created with a generated UUID, to the supplied {@link #repository}. A
     * {@link CreateComponentResponse} is then added to the response {@link StreamObserver} which is then closed.
     *
     * @param componentCreator The function to create a component with the randomly generated supplied UUID
     * @param responseObserver The response {@link StreamObserver} provided by gRPC
     */
    public void createComponent(Function<UUID, T> componentCreator, StreamObserver<CreateComponentResponse> responseObserver) {
        var componentId = UUID.randomUUID();
        var component = componentCreator.apply(componentId);
        repository.save(component);

        responseObserver.onNext(CreateComponentResponse.newBuilder()
                .setComponentId(componentId.toString())
                .build());

        responseObserver.onCompleted();
    }
}
