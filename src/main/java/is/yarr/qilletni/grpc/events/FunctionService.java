package is.yarr.qilletni.grpc.events;

import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import is.yarr.qilletni.components.FunctionComponent;
import is.yarr.qilletni.database.repositories.components.FunctionComponentRepository;
import is.yarr.qilletni.grpc.gen.CreateComponentResponse;
import is.yarr.qilletni.grpc.gen.EmptyResponse;
import is.yarr.qilletni.grpc.gen.events.function.FunctionChangeChildrenEvent;
import is.yarr.qilletni.grpc.gen.events.function.FunctionCreateEvent;
import is.yarr.qilletni.grpc.gen.events.function.FunctionGrpc;
import is.yarr.qilletni.grpc.gen.events.function.FunctionNameChangeEvent;
import is.yarr.qilletni.utility.UUIDUtils;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.security.access.annotation.Secured;

import java.util.UUID;

import static is.yarr.qilletni.grpc.security.Authorities.GENERAL;

@GrpcService
public class FunctionService extends FunctionGrpc.FunctionImplBase {

    private final FunctionComponentRepository functionComponentRepository;
    private final GRPCRepositoryInterfacer<FunctionComponent> repositoryInterfacer;

    public FunctionService(FunctionComponentRepository functionComponentRepository) {
        this.functionComponentRepository = functionComponentRepository;
        this.repositoryInterfacer = new GRPCRepositoryInterfacer<>(functionComponentRepository);
    }

    @Override
    @Secured({GENERAL})
    public void create(FunctionCreateEvent request, StreamObserver<CreateComponentResponse> responseObserver) {
        repositoryInterfacer.createComponent(FunctionComponent::new, responseObserver);
    }

    @Override
    @Secured({GENERAL})
    public void changeChildren(FunctionChangeChildrenEvent request, StreamObserver<EmptyResponse> responseObserver) {
        var componentId = UUID.fromString(request.getModify().getComponentId());

        // TODO: QTNB-12: Validate UUIDs are actual component IDs

        repositoryInterfacer.getComponent(componentId, responseObserver, functionComponent -> {
            var childrenIdArray = request.getChildrenList()
                    .stream()
                    .map(ByteString::toByteArray)
                    .map(UUIDUtils::convertBytesToUUID)
                    .toArray(UUID[]::new);

            functionComponent.setChildren(childrenIdArray);
            functionComponentRepository.save(functionComponent);
        });
    }

    @Override
    @Secured({GENERAL})
    public void changeName(FunctionNameChangeEvent request, StreamObserver<EmptyResponse> responseObserver) {
        var componentId = UUID.fromString(request.getModify().getComponentId());

        repositoryInterfacer.getComponent(componentId, responseObserver, functionComponent -> {
            functionComponent.setName(request.getName());
            functionComponentRepository.save(functionComponent);
        });
    }
}
