package is.yarr.qilletni.grpc.events;

import io.grpc.stub.StreamObserver;
import is.yarr.qilletni.grpc.gen.CreateComponentResponse;
import is.yarr.qilletni.grpc.gen.EmptyResponse;
import is.yarr.qilletni.grpc.gen.events.lastfm.LastFmCreateEvent;
import is.yarr.qilletni.grpc.gen.events.lastfm.LastFmGrpc;
import is.yarr.qilletni.grpc.gen.events.lastfm.LastFmLimitChangeEvent;
import is.yarr.qilletni.grpc.gen.events.lastfm.LastFmSequentialChangeEvent;
import is.yarr.qilletni.grpc.gen.events.lastfm.LastFmTypeChangeEvent;
import org.springframework.security.access.annotation.Secured;

import static is.yarr.qilletni.grpc.security.Authorities.GENERAL;

public class LastFmService extends LastFmGrpc.LastFmImplBase {

    @Override
    @Secured({GENERAL})
    public void create(LastFmCreateEvent request, StreamObserver<CreateComponentResponse> responseObserver) {
        // TODO: create
    }

    @Override
    @Secured({GENERAL})
    public void changeSequential(LastFmSequentialChangeEvent request, StreamObserver<EmptyResponse> responseObserver) {
        // TODO: changeSequential
    }

    @Override
    @Secured({GENERAL})
    public void changeLimit(LastFmLimitChangeEvent request, StreamObserver<EmptyResponse> responseObserver) {
        // TODO: changeLimit
    }

    @Override
    @Secured({GENERAL})
    public void changeType(LastFmTypeChangeEvent request, StreamObserver<EmptyResponse> responseObserver) {
        // TODO: changeType
    }
}
