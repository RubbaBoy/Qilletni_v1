package is.yarr.qilletni.grpc.events;

import io.grpc.stub.StreamObserver;
import is.yarr.qilletni.grpc.gen.CreateComponentResponse;
import is.yarr.qilletni.grpc.gen.EmptyResponse;
import is.yarr.qilletni.grpc.gen.events.component.forloop.ForLoopAbsTimeChangeEvent;
import is.yarr.qilletni.grpc.gen.events.component.forloop.ForLoopChildrenChangeEvent;
import is.yarr.qilletni.grpc.gen.events.component.forloop.ForLoopCreateEvent;
import is.yarr.qilletni.grpc.gen.events.component.forloop.ForLoopDurationChangeEvent;
import is.yarr.qilletni.grpc.gen.events.component.forloop.ForLoopIterationsChangeEvent;
import is.yarr.qilletni.grpc.gen.events.component.forloop.ForLoopServiceGrpc;
import org.springframework.security.access.annotation.Secured;

import static is.yarr.qilletni.grpc.security.Authorities.GENERAL;

public class ForLoopService extends ForLoopServiceGrpc.ForLoopServiceImplBase {

    @Override
    @Secured({GENERAL})
    public void create(ForLoopCreateEvent request, StreamObserver<CreateComponentResponse> responseObserver) {
        // TODO: create
    }

    @Override
    @Secured({GENERAL})
    public void changeChildren(ForLoopChildrenChangeEvent request, StreamObserver<EmptyResponse> responseObserver) {
        // TODO: changeChildren
    }

    @Override
    @Secured({GENERAL})
    public void changeIterations(ForLoopIterationsChangeEvent request, StreamObserver<EmptyResponse> responseObserver) {
        // TODO: changeIterations
    }

    @Override
    @Secured({GENERAL})
    public void changeDuration(ForLoopDurationChangeEvent request, StreamObserver<EmptyResponse> responseObserver) {
        // TODO: changeDuration
    }

    @Override
    @Secured({GENERAL})
    public void changeAbsTime(ForLoopAbsTimeChangeEvent request, StreamObserver<EmptyResponse> responseObserver) {
        // TODO: changeAbsTime
    }
}
