package is.yarr.qilletni.grpc.events.components.request;

import io.grpc.stub.StreamObserver;
import is.yarr.qilletni.grpc.gen.request.ComponentRequestServiceGrpc;
import is.yarr.qilletni.grpc.gen.request.StructureRequestEvent;
import is.yarr.qilletni.grpc.gen.request.StructureResponse;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.security.access.annotation.Secured;

import static is.yarr.qilletni.grpc.security.Authorities.GENERAL;

@GrpcService
public class ComponentRequestService extends ComponentRequestServiceGrpc.ComponentRequestServiceImplBase {

    @Override
    @Secured({GENERAL})
    public void retrieve(StructureRequestEvent request, StreamObserver<StructureResponse> responseObserver) {

    }
}
