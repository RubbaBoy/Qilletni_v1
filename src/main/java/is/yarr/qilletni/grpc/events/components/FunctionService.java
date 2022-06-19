package is.yarr.qilletni.grpc.events.components;

import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import is.yarr.qilletni.components.FunctionComponent;
import is.yarr.qilletni.database.repositories.BoardRepository;
import is.yarr.qilletni.database.repositories.components.FunctionComponentRepository;
import is.yarr.qilletni.grpc.gen.CreateComponentResponse;
import is.yarr.qilletni.grpc.gen.EmptyResponse;
import is.yarr.qilletni.grpc.gen.events.component.function.FunctionChangeChildrenEvent;
import is.yarr.qilletni.grpc.gen.events.component.function.FunctionCreateEvent;
import is.yarr.qilletni.grpc.gen.events.component.function.FunctionNameChangeEvent;
import is.yarr.qilletni.grpc.gen.events.component.function.FunctionServiceGrpc;
import is.yarr.qilletni.grpc.security.UserSessionSecurityContext;
import is.yarr.qilletni.utility.UUIDUtils;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.security.access.annotation.Secured;

import java.util.UUID;

import static is.yarr.qilletni.grpc.security.Authorities.GENERAL;

@GrpcService
public class FunctionService extends FunctionServiceGrpc.FunctionServiceImplBase {

    private final FunctionComponentRepository functionComponentRepository;
    private final GRPCRepositoryInterfacer<FunctionComponent> repositoryInterfacer;

    public FunctionService(FunctionComponentRepository functionComponentRepository, BoardRepository boardRepository) {
        this.functionComponentRepository = functionComponentRepository;
        this.repositoryInterfacer = new GRPCRepositoryInterfacer<>(functionComponentRepository, boardRepository);
    }

    @Override
    @Secured({GENERAL})
    public void create(FunctionCreateEvent request, StreamObserver<CreateComponentResponse> responseObserver) {
        repositoryInterfacer.createComponent(FunctionComponent::new, request.getCreate(), responseObserver);
    }

    @Override
    @Secured({GENERAL})
    public void changeChildren(FunctionChangeChildrenEvent request, StreamObserver<EmptyResponse> responseObserver) {
        var userInfo = UserSessionSecurityContext.getCurrentUserInfo();
        var componentId = UUID.fromString(request.getModify().getComponentId());

        // TODO: QTNB-12: Validate UUIDs are actual component IDs

        repositoryInterfacer.getComponent(componentId, userInfo.getId(), responseObserver, functionComponent -> {
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
        var userInfo = UserSessionSecurityContext.getCurrentUserInfo();
        var componentId = UUID.fromString(request.getModify().getComponentId());

        repositoryInterfacer.getComponent(componentId, userInfo.getId(), responseObserver, functionComponent -> {
            functionComponent.setName(request.getName());
            functionComponentRepository.save(functionComponent);
        });
    }
}
