package is.yarr.qilletni.grpc.events;

import io.grpc.stub.StreamObserver;
import is.yarr.qilletni.grpc.gen.CreateComponentResponse;
import is.yarr.qilletni.grpc.gen.EmptyResponse;
import is.yarr.qilletni.grpc.gen.events.component.song.SongChangeEvent;
import is.yarr.qilletni.grpc.gen.events.component.song.SongCreateEvent;
import is.yarr.qilletni.grpc.gen.events.component.song.SongServiceGrpc;
import org.springframework.security.access.annotation.Secured;

import static is.yarr.qilletni.grpc.security.Authorities.GENERAL;

public class SongService extends SongServiceGrpc.SongServiceImplBase {

    @Override
    @Secured({GENERAL})
    public void create(SongCreateEvent request, StreamObserver<CreateComponentResponse> responseObserver) {
        // TODO: create
    }

    @Override
    @Secured({GENERAL})
    public void changeSong(SongChangeEvent request, StreamObserver<EmptyResponse> responseObserver) {
        // TODO: changeSong
    }
}
