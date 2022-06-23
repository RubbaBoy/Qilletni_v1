package is.yarr.qilletni.grpc.events.components;

import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import is.yarr.qilletni.components.ForComponent;
import is.yarr.qilletni.database.repositories.BoardRepository;
import is.yarr.qilletni.database.repositories.components.ForComponentRepository;
import is.yarr.qilletni.grpc.gen.CreateComponentResponse;
import is.yarr.qilletni.grpc.gen.EmptyResponse;
import is.yarr.qilletni.grpc.gen.events.component.forloop.ForLoopAbsTimeChangeEvent;
import is.yarr.qilletni.grpc.gen.events.component.forloop.ForLoopChildrenChangeEvent;
import is.yarr.qilletni.grpc.gen.events.component.forloop.ForLoopCreateEvent;
import is.yarr.qilletni.grpc.gen.events.component.forloop.ForLoopDurationChangeEvent;
import is.yarr.qilletni.grpc.gen.events.component.forloop.ForLoopIterationsChangeEvent;
import is.yarr.qilletni.grpc.gen.events.component.forloop.ForLoopServiceGrpc;
import is.yarr.qilletni.grpc.security.UserSessionSecurityContext;
import is.yarr.qilletni.utility.UUIDUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.parameters.P;

import java.util.Date;
import java.util.UUID;

import static is.yarr.qilletni.grpc.security.Authorities.GENERAL;

public class ForLoopService extends ForLoopServiceGrpc.ForLoopServiceImplBase {

    private final ForComponentRepository forComponentRepository;
    private final GRPCRepositoryInterfacer<ForComponent> repositoryInterfacer;

    public ForLoopService(ForComponentRepository forComponentRepository, BoardRepository boardRepository) {
        this.forComponentRepository = forComponentRepository;
        this.repositoryInterfacer = new GRPCRepositoryInterfacer<>(forComponentRepository, boardRepository);
    }

    @Override
    @Secured({GENERAL})
    public void create(ForLoopCreateEvent request, StreamObserver<CreateComponentResponse> responseObserver) {
        repositoryInterfacer.createComponent(ForComponent::new, request.getCreate(), responseObserver);
    }

    @Override
    @Secured({GENERAL})
    public void changeChildren(ForLoopChildrenChangeEvent request, StreamObserver<EmptyResponse> responseObserver) {
        var userInfo = UserSessionSecurityContext.getCurrentUserInfo();
        var componentId = UUID.fromString(request.getModify().getComponentId());

        repositoryInterfacer.getComponent(componentId, userInfo.getId(), responseObserver, forComponent -> {
            var childrenIdArray = request.getChildrenList()
                    .stream()
                    .map(ByteString::toByteArray)
                    .map(UUIDUtils::convertBytesToUUID)
                    .toArray(UUID[]::new);

            forComponent.setChildren(childrenIdArray);
            forComponentRepository.save(forComponent);
        });
    }


    @Override
    @Secured({GENERAL})
    public void changeIterations(ForLoopIterationsChangeEvent request, StreamObserver<EmptyResponse> responseObserver) {
        var userInfo = UserSessionSecurityContext.getCurrentUserInfo();
        var componentId = UUID.fromString(request.getModify().getComponentId());

        repositoryInterfacer.getComponent(componentId, userInfo.getId(), responseObserver, forComponent -> {
            forComponent.setIterations(request.getIterations());
            forComponentRepository.save(forComponent);
        });
    }

    @Override
    @Secured({GENERAL})
    public void changeDuration(ForLoopDurationChangeEvent request, StreamObserver<EmptyResponse> responseObserver) {
        var userInfo = UserSessionSecurityContext.getCurrentUserInfo();
        var componentId = UUID.fromString(request.getModify().getComponentId());

        repositoryInterfacer.getComponent(componentId, userInfo.getId(), responseObserver, forComponent -> {
            forComponent.setDuration(request.getDuration());
            forComponentRepository.save(forComponent);
        });
    }

    @Override
    @Secured({GENERAL})
    public void changeAbsTime(ForLoopAbsTimeChangeEvent request, StreamObserver<EmptyResponse> responseObserver) {
        var userInfo = UserSessionSecurityContext.getCurrentUserInfo();
        var componentId = UUID.fromString(request.getModify().getComponentId());

        repositoryInterfacer.getComponent(componentId, userInfo.getId(), responseObserver, forComponent -> {
            forComponent.setAbsTime(new Date(request.getAbsTime()));
            forComponentRepository.save(forComponent);
        });
    }
}
