package is.yarr.qilletni.grpc.events.components;

import io.grpc.stub.StreamObserver;
import is.yarr.qilletni.components.SongComponent;
import is.yarr.qilletni.database.repositories.BoardRepository;
import is.yarr.qilletni.database.repositories.components.SongComponentRepository;
import is.yarr.qilletni.grpc.gen.CreateComponentResponse;
import is.yarr.qilletni.grpc.gen.EmptyResponse;
import is.yarr.qilletni.grpc.gen.events.component.song.SongChangeEvent;
import is.yarr.qilletni.grpc.gen.events.component.song.SongCreateEvent;
import is.yarr.qilletni.grpc.gen.events.component.song.SongServiceGrpc;
import is.yarr.qilletni.grpc.security.UserSessionSecurityContext;
import is.yarr.qilletni.music.SpotifySongId;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.security.access.annotation.Secured;

import java.util.UUID;

import static is.yarr.qilletni.grpc.security.Authorities.GENERAL;

@GrpcService
public class SongService extends SongServiceGrpc.SongServiceImplBase {

    private final SongComponentRepository songComponentRepository;
    private final GRPCRepositoryInterfacer<SongComponent> repositoryInterfacer;

    public SongService(SongComponentRepository songComponentRepository, BoardRepository boardRepository) {
        this.songComponentRepository = songComponentRepository;
        this.repositoryInterfacer = new GRPCRepositoryInterfacer<>(songComponentRepository, boardRepository);
    }


    @Override
    @Secured({GENERAL})
    public void create(SongCreateEvent request, StreamObserver<CreateComponentResponse> responseObserver) {
        repositoryInterfacer.createComponent(SongComponent::new, request.getCreate(), responseObserver);
    }

    @Override
    @Secured({GENERAL})
    public void changeSong(SongChangeEvent request, StreamObserver<EmptyResponse> responseObserver) {
        var userInfo = UserSessionSecurityContext.getCurrentUserInfo();
        var componentId = UUID.fromString(request.getModify().getComponentId());

        repositoryInterfacer.getComponent(componentId, userInfo.getId(), responseObserver, songComponent -> {
            songComponent.setSongId(new SpotifySongId(request.getSongId()));
            songComponentRepository.save(songComponent);
        });
    }
}
