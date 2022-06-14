package is.yarr.qilletni.grpc.events;

import io.grpc.stub.StreamObserver;
import is.yarr.qilletni.grpc.gen.CreateComponentResponse;
import is.yarr.qilletni.grpc.gen.EmptyResponse;
import is.yarr.qilletni.grpc.gen.events.function.FunctionChangeChildrenEvent;
import is.yarr.qilletni.grpc.gen.events.function.FunctionCreateEvent;
import is.yarr.qilletni.grpc.gen.events.function.FunctionGrpc;
import is.yarr.qilletni.grpc.gen.events.function.FunctionNameChangeEvent;
import org.springframework.security.access.annotation.Secured;

import static is.yarr.qilletni.grpc.security.Authorities.GENERAL;

public class FunctionService extends FunctionGrpc.FunctionImplBase {

    @Override
    @Secured({GENERAL})
    public void create(FunctionCreateEvent request, StreamObserver<CreateComponentResponse> responseObserver) {
        // TODO: create
    }

    @Override
    @Secured({GENERAL})
    public void changeChildren(FunctionChangeChildrenEvent request, StreamObserver<EmptyResponse> responseObserver) {
        // TODO: changeChildren
    }

    @Override
    @Secured({GENERAL})
    public void changeName(FunctionNameChangeEvent request, StreamObserver<EmptyResponse> responseObserver) {
        // TODO: changeName
    }
}
