package is.yarr.qilletni.grpc.events;

import io.grpc.stub.StreamObserver;
import is.yarr.qilletni.grpc.gen.CreateComponentResponse;
import is.yarr.qilletni.grpc.gen.EmptyResponse;
import is.yarr.qilletni.grpc.gen.events.spotify.SpotifyCreateEvent;
import is.yarr.qilletni.grpc.gen.events.spotify.SpotifyDataChangeEvent;
import is.yarr.qilletni.grpc.gen.events.spotify.SpotifyGenreChangeEvent;
import is.yarr.qilletni.grpc.gen.events.spotify.SpotifyGrpc;
import is.yarr.qilletni.grpc.gen.events.spotify.SpotifyLimitChangeEvent;
import is.yarr.qilletni.grpc.gen.events.spotify.SpotifySequentialChangeEvent;
import is.yarr.qilletni.grpc.gen.events.spotify.SpotifyTagsChangeEvent;
import is.yarr.qilletni.grpc.gen.events.spotify.SpotifyTypeChangeEvent;
import is.yarr.qilletni.grpc.gen.events.spotify.SpotifyYearChangeEvent;
import is.yarr.qilletni.grpc.gen.events.spotify.SpotifyYearRangeChangeEvent;
import org.springframework.security.access.annotation.Secured;

import static is.yarr.qilletni.grpc.security.Authorities.GENERAL;

public class SpotifyService extends SpotifyGrpc.SpotifyImplBase {

    @Override
    @Secured({GENERAL})
    public void create(SpotifyCreateEvent request, StreamObserver<CreateComponentResponse> responseObserver) {
        // TODO: create
    }

    @Override
    @Secured({GENERAL})
    public void changeSequential(SpotifySequentialChangeEvent request, StreamObserver<EmptyResponse> responseObserver) {
        // TODO: changeSequential
    }

    @Override
    @Secured({GENERAL})
    public void changeLimit(SpotifyLimitChangeEvent request, StreamObserver<EmptyResponse> responseObserver) {
        // TODO: changeLimit
    }

    @Override
    @Secured({GENERAL})
    public void changeType(SpotifyTypeChangeEvent request, StreamObserver<EmptyResponse> responseObserver) {
        // TODO: changeType
    }

    @Override
    @Secured({GENERAL})
    public void changeData(SpotifyDataChangeEvent request, StreamObserver<EmptyResponse> responseObserver) {
        // TODO: changeData
    }

    @Override
    @Secured({GENERAL})
    public void changeYearRange(SpotifyYearRangeChangeEvent request, StreamObserver<EmptyResponse> responseObserver) {
        // TODO: changeYearRange
    }

    @Override
    @Secured({GENERAL})
    public void changeYear(SpotifyYearChangeEvent request, StreamObserver<EmptyResponse> responseObserver) {
        // TODO: changeYear
    }

    @Override
    @Secured({GENERAL})
    public void changeGenre(SpotifyGenreChangeEvent request, StreamObserver<EmptyResponse> responseObserver) {
        // TODO: changeGenre
    }

    @Override
    @Secured({GENERAL})
    public void changeTags(SpotifyTagsChangeEvent request, StreamObserver<EmptyResponse> responseObserver) {
        // TODO: changeTags
    }
}
