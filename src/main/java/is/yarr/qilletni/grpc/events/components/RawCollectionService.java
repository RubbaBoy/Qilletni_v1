package is.yarr.qilletni.grpc.events.components;

import io.grpc.stub.StreamObserver;
import is.yarr.qilletni.components.RawCollectionComponent;
import is.yarr.qilletni.database.repositories.BoardRepository;
import is.yarr.qilletni.database.repositories.components.RawCollectionComponentRepository;
import is.yarr.qilletni.grpc.gen.CreateComponentResponse;
import is.yarr.qilletni.grpc.gen.EmptyResponse;
import is.yarr.qilletni.grpc.gen.events.component.rawcollection.RawCollectionCreateEvent;
import is.yarr.qilletni.grpc.gen.events.component.rawcollection.RawCollectionSequentialChangeEvent;
import is.yarr.qilletni.grpc.gen.events.component.rawcollection.RawCollectionServiceGrpc;
import is.yarr.qilletni.grpc.gen.events.component.rawcollection.RawCollectionSongsChangeEvent;
import is.yarr.qilletni.grpc.security.UserSessionSecurityContext;
import is.yarr.qilletni.music.SongId;
import is.yarr.qilletni.music.SpotifySongId;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.security.access.annotation.Secured;

import java.util.UUID;

import static is.yarr.qilletni.grpc.security.Authorities.GENERAL;

@GrpcService
public class RawCollectionService extends RawCollectionServiceGrpc.RawCollectionServiceImplBase {

    private final RawCollectionComponentRepository rawCollectionComponentRepository;
    private final GRPCRepositoryInterfacer<RawCollectionComponent> repositoryInterfacer;

    public RawCollectionService(RawCollectionComponentRepository rawCollectionComponentRepository, BoardRepository boardRepository) {
        this.rawCollectionComponentRepository = rawCollectionComponentRepository;
        this.repositoryInterfacer = new GRPCRepositoryInterfacer<>(rawCollectionComponentRepository, boardRepository);
    }

    @Override
    @Secured({GENERAL})
    public void create(RawCollectionCreateEvent request, StreamObserver<CreateComponentResponse> responseObserver) {
        repositoryInterfacer.createComponent(RawCollectionComponent::new, request.getCreate(), responseObserver);
    }

    @Override
    @Secured({GENERAL})
    public void changeSequential(RawCollectionSequentialChangeEvent request, StreamObserver<EmptyResponse> responseObserver) {
        var userInfo = UserSessionSecurityContext.getCurrentUserInfo();
        var componentId = UUID.fromString(request.getModify().getComponentId());

        repositoryInterfacer.getComponent(componentId, userInfo.getId(), responseObserver, rawCollectionComponent -> {
            rawCollectionComponent.setSequential(request.getSequential());
            rawCollectionComponentRepository.save(rawCollectionComponent);
        });
    }

    @Override
    @Secured({GENERAL})
    public void changeSongs(RawCollectionSongsChangeEvent request, StreamObserver<EmptyResponse> responseObserver) {
        var userInfo = UserSessionSecurityContext.getCurrentUserInfo();
        var componentId = UUID.fromString(request.getModify().getComponentId());

        repositoryInterfacer.getComponent(componentId, userInfo.getId(), responseObserver, rawCollectionComponent -> {
            rawCollectionComponent.setSongs(request.getSongIdsList()
                    .stream()
                    .map(SpotifySongId::new)
                    .toArray(SongId[]::new)
            );
            rawCollectionComponentRepository.save(rawCollectionComponent);
        });
    }
}
