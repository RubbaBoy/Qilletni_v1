package is.yarr.qilletni.grpc.events;

import io.grpc.stub.StreamObserver;
import is.yarr.qilletni.grpc.gen.EmptyResponse;
import is.yarr.qilletni.grpc.gen.ResponseError;

public class ResponseUtility {

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
     * Sends an empty {@link EmptyResponse} to the given response stream.
     *
     * @param responseObserver The gRPC response stream
     */
    public static void sendEmpty(StreamObserver<EmptyResponse> responseObserver) {
        responseObserver.onNext(EmptyResponse.newBuilder().build());
    }

}
