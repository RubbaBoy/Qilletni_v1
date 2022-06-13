package is.yarr.queuegen.grpc;

import io.grpc.stub.StreamObserver;
import is.yarr.queuegen.grpc.gen.HelloReply;
import is.yarr.queuegen.grpc.gen.HelloRequest;
import is.yarr.queuegen.grpc.gen.SimpleGrpc;
import is.yarr.queuegen.grpc.security.UserSessionSecurityContext;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;

@GrpcService
public class GrpcServerService extends SimpleGrpc.SimpleImplBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrpcServerService.class);

    @Override
    @Secured({"ROLE_USER"})
    public void sayHello(final HelloRequest req, final StreamObserver<HelloReply> responseObserver) {
        var auth = UserSessionSecurityContext.getAuthToken();
        LOGGER.info("auth = {}", auth);

        final HelloReply reply = HelloReply.newBuilder().setMessage("Hello ==> " + req.getName() + " (Hey, " + auth.getPrincipal().getName() + "!)").build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
