package is.yarr.qilletni.grpc;

import io.grpc.stub.StreamObserver;
import is.yarr.qilletni.grpc.gen.HelloReply;
import is.yarr.qilletni.grpc.gen.HelloRequest;
import is.yarr.qilletni.grpc.gen.SimpleGrpc;
import is.yarr.qilletni.grpc.security.UserSessionSecurityContext;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;

import static is.yarr.qilletni.grpc.security.Authorities.GENERAL;

@GrpcService
public class GrpcServerService extends SimpleGrpc.SimpleImplBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrpcServerService.class);

    @Override
    @Secured({GENERAL})
    public void sayHello(final HelloRequest req, final StreamObserver<HelloReply> responseObserver) {
        var auth = UserSessionSecurityContext.getAuthToken();
        LOGGER.info("auth = {}", auth);

        final HelloReply reply = HelloReply.newBuilder().setMessage("Hello ==> " + req.getName() + " (Hey, " + auth.getPrincipal().getName() + "!)").build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
