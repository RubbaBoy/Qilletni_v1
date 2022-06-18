package is.yarr.qilletni.grpc.events.components;

import io.grpc.stub.StreamObserver;
import is.yarr.qilletni.grpc.gen.CreateComponentResponse;
import is.yarr.qilletni.grpc.gen.EmptyResponse;
import is.yarr.qilletni.grpc.gen.events.component.rawcollection.RawCollectionCreateEvent;
import is.yarr.qilletni.grpc.gen.events.component.rawcollection.RawCollectionSequentialChangeEvent;
import is.yarr.qilletni.grpc.gen.events.component.rawcollection.RawCollectionServiceGrpc;
import is.yarr.qilletni.grpc.gen.events.component.rawcollection.RawCollectionSongsChangeEvent;
import org.springframework.security.access.annotation.Secured;

import static is.yarr.qilletni.grpc.security.Authorities.GENERAL;

public class RawCollectionService extends RawCollectionServiceGrpc.RawCollectionServiceImplBase {

    @Override
    @Secured({GENERAL})
    public void create(RawCollectionCreateEvent request, StreamObserver<CreateComponentResponse> responseObserver) {
        // TODO: create
    }

    @Override
    @Secured({GENERAL})
    public void changeSequential(RawCollectionSequentialChangeEvent request, StreamObserver<EmptyResponse> responseObserver) {
        // TODO: changeSequential
    }

    @Override
    @Secured({GENERAL})
    public void changeSongs(RawCollectionSongsChangeEvent request, StreamObserver<EmptyResponse> responseObserver) {
        // TODO: changeSongs
    }
}
