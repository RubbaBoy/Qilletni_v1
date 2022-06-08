package is.yarr.queuegen.grpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.examples.lib.HelloReply;
import net.devh.boot.grpc.examples.lib.HelloRequest;
import net.devh.boot.grpc.examples.lib.SimpleGrpc;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;

@GrpcService
public class GrpcServerService extends SimpleGrpc.SimpleImplBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrpcServerService.class);

    @Override
    @Secured({"ROLE_USER"})
    public void sayHello(final HelloRequest req, final StreamObserver<HelloReply> responseObserver) {
        var auth = getAuth();
        LOGGER.info("auth = {}", auth);

        final HelloReply reply = HelloReply.newBuilder().setMessage("Hello ==> " + req.getName() + " (Hey, " + auth.getPrincipal().getName() + "!)").build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    private SessionAuth getAuth() {
        return (SessionAuth) SecurityContextHolder.getContext().getAuthentication();
    }

}
