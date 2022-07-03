package is.yarr.qilletni.grpc.events.components.request;

import io.grpc.stub.StreamObserver;
import is.yarr.qilletni.database.repositories.components.SpotifyComponentRepository;
import is.yarr.qilletni.grpc.events.ResponseUtility;
import is.yarr.qilletni.grpc.events.components.request.mapper.SpotifyDataGRPCMapper;
import is.yarr.qilletni.grpc.gen.request.SpotifyComponentDataRequestEvent;
import is.yarr.qilletni.grpc.gen.request.SpotifyComponentDataRequestServiceGrpc;
import is.yarr.qilletni.grpc.gen.request.SpotifyComponentDataResponse;
import is.yarr.qilletni.grpc.gen.search.spotify.SpotifySongResponse;
import is.yarr.qilletni.grpc.security.UserSessionSecurityContext;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;

import java.util.UUID;

import static is.yarr.qilletni.grpc.security.Authorities.GENERAL;

@GrpcService
public class SpotifyComponentDataRequestService extends SpotifyComponentDataRequestServiceGrpc.SpotifyComponentDataRequestServiceImplBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpotifyComponentDataRequestService.class);

    private final SpotifyComponentRepository spotifyComponentRepository;
    private final SpotifyDataGRPCMapper spotifyDataGRPCMapper;

    public SpotifyComponentDataRequestService(SpotifyComponentRepository spotifyComponentRepository, SpotifyDataGRPCMapper spotifyDataGRPCMapper) {
        this.spotifyComponentRepository = spotifyComponentRepository;
        this.spotifyDataGRPCMapper = spotifyDataGRPCMapper;
    }

    @Override
    @Secured({GENERAL})
    public void requestSpotifyComponentData(SpotifyComponentDataRequestEvent request, StreamObserver<SpotifyComponentDataResponse> responseObserver) {
        var userInfo = UserSessionSecurityContext.getCurrentUserInfo();

        var spotifyComponentOptional = spotifyComponentRepository.findComponentOwnedBy(UUID.fromString(request.getComponentId()), userInfo.getId());

        if (spotifyComponentOptional.isEmpty()) {
            responseObserver.onNext(SpotifyComponentDataResponse.newBuilder()
                    .setError(ResponseUtility.createError("Component could not be found", 404)).build());
            responseObserver.onCompleted();
            return;
        }

        var spotifyDataOptional = spotifyComponentOptional.get().getCollectionData();

        if (spotifyDataOptional.isEmpty()) {
            responseObserver.onNext(SpotifyComponentDataResponse.newBuilder()
                    .setError(ResponseUtility.createError("Component is uninitialized!", 500)).build());
            responseObserver.onCompleted();
            return;
        }

        spotifyDataGRPCMapper.createResponseFromData(spotifyDataOptional.get())
                .thenAccept(responseObserver::onNext)
                .whenComplete(($, throwable) -> ResponseUtility.terminallyReportError(throwable, responseObserver, "An exception occurred while retrieving Spotify component data",
                        responseError -> SpotifyComponentDataResponse.newBuilder().setError(responseError).build()));
    }
}
