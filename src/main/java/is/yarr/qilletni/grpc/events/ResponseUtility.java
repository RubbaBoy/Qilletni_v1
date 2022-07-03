package is.yarr.qilletni.grpc.events;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import io.grpc.stub.StreamObserver;
import is.yarr.qilletni.grpc.gen.EmptyResponse;
import is.yarr.qilletni.grpc.gen.ResponseError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.function.Function;

public class ResponseUtility {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseUtility.class);

    /**
     * Creates a {@link ResponseError} to be set into a gRPC response.
     *
     * @param message The error message
     * @param code    The error code to include
     * @return The created {@link ResponseError}
     */
    public static ResponseError createError(String message, int code) {
        return ResponseError.newBuilder()
                .setMessage(message)
                .setCode(code)
                .build();
    }

    /**
     * Creates a {@link ResponseError} to be set into a gRPC response with a thrown {@link Throwable}.
     *
     * @param throwable The {@link Throwable} to include in the error
     * @return The created {@link ResponseError}
     */
    public static ResponseError createErrorFromThrowable(Throwable throwable) {
        return ResponseError.newBuilder()
                .setMessage(throwable.getMessage())
                .setCode(500)
                .build();
    }

    /**
     * Sends an {@link EmptyResponse} with an {@link ResponseError} populated with the given parameters, used for quick
     * sending of errors. The response remains open after this method.
     *
     * @param responseObserver The gRPC response stream
     * @param message          The error message
     * @param code             The error code to include
     */
    public static void sendError(StreamObserver<EmptyResponse> responseObserver, String message, int code) {
        responseObserver.onNext(EmptyResponse.newBuilder()
                .setError(createError(message, code))
                .build());
    }

    /**
     * Identical to {@link #sendError(StreamObserver, String, int)}, except the response stream is completed afterwards.
     *
     * @param responseObserver The gRPC response stream
     * @param message          The error message
     * @param code             The error code to include
     */
    public static void sendTerminatingError(StreamObserver<EmptyResponse> responseObserver, String message, int code) {
        sendError(responseObserver, message, code);
        responseObserver.onCompleted();
    }

    /**
     * If the given {@link Throwable} is not null, the {@code errorSetter} is invoked with a {@link ResponseError}, and
     * the given response with the applied error is sent to the {@code responseObserver}. No matter the error status
     * (or lack thereof), the {@code responseObserver} is completed.
     *
     * @param throwable        The possible error to report
     * @param responseObserver The gRPC response stream
     * @param errorSetter      The function to apply the error and add to the response stream
     * @param <T>              The type of the response message
     */
    public static <T extends GeneratedMessageV3> void terminallyReportError(@Nullable Throwable throwable, StreamObserver<T> responseObserver, Function<ResponseError, T> errorSetter) {
        terminallyReportError(throwable, responseObserver, "An error occurred during a gRPC request", errorSetter);
    }

    /**
     * If the given {@link Throwable} is not null, the {@code errorSetter} is invoked with a {@link ResponseError}, and
     * the given response with the applied error is sent to the {@code responseObserver}. No matter the error status
     * (or lack thereof), the {@code responseObserver} is completed.
     *
     * @param throwable        The possible error to report
     * @param responseObserver The gRPC response stream
     * @param message          The message to print to console
     * @param errorSetter      The function to apply the error and add to the response stream
     * @param <T>              The type of the response message
     */
    public static <T extends GeneratedMessageV3> void terminallyReportError(@Nullable Throwable throwable, StreamObserver<T> responseObserver, String message, Function<ResponseError, T> errorSetter) {
        if (throwable != null) {
            LOGGER.error(message, throwable);

            responseObserver.onNext(errorSetter.apply(createErrorFromThrowable(throwable)));
        }

        responseObserver.onCompleted();
    }

    /**
     * Sends an empty {@link EmptyResponse} to the given response stream.
     *
     * @param responseObserver The gRPC response stream
     */
    public static void sendEmpty(StreamObserver<EmptyResponse> responseObserver) {
        responseObserver.onNext(EmptyResponse.newBuilder().build());
    }

}
