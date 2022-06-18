package is.yarr.qilletni.grpc.events;

import io.grpc.stub.StreamObserver;
import is.yarr.qilletni.grpc.gen.EmptyResponse;
import is.yarr.qilletni.grpc.gen.events.component.general.DeleteEvent;
import is.yarr.qilletni.grpc.gen.events.component.general.GeneralServiceGrpc;
import is.yarr.qilletni.grpc.gen.events.component.general.RecolorEvent;
import org.springframework.security.access.annotation.Secured;

import static is.yarr.qilletni.grpc.security.Authorities.GENERAL;

public class GeneralEventService extends GeneralServiceGrpc.GeneralServiceImplBase {

    @Override
    @Secured({GENERAL})
    public void delete(DeleteEvent request, StreamObserver<EmptyResponse> responseObserver) {
        // TODO: delete
    }

    @Override
    @Secured({GENERAL})
    public void recolor(RecolorEvent request, StreamObserver<EmptyResponse> responseObserver) {
        // TODO: recolor
    }
}
