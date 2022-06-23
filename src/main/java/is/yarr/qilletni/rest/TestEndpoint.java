package is.yarr.qilletni.rest;

import com.google.common.collect.ImmutableList;
import is.yarr.qilletni.auth.AuthHandler;
import is.yarr.qilletni.auth.SessionHandler;
import is.yarr.qilletni.components.SpotifyCollectionComponent;
import is.yarr.qilletni.components.spotify.SpotifyCollectionType;
import is.yarr.qilletni.components.spotify.SpotifyPlaylistData;
import is.yarr.qilletni.content.playlist.PlaylistCache;
import is.yarr.qilletni.content.song.SongCache;
import is.yarr.qilletni.database.repositories.components.FunctionComponentRepository;
import is.yarr.qilletni.database.repositories.components.RawCollectionComponentRepository;
import is.yarr.qilletni.database.repositories.components.SongComponentRepository;
import is.yarr.qilletni.database.repositories.components.SpotifyComponentRepository;
import is.yarr.qilletni.database.repositories.components.SpotifyDataRepository;
import is.yarr.qilletni.music.SpotifyPlaylistId;
import is.yarr.qilletni.music.SpotifySongId;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * This class will be removed when this starts getting more production-ready, do not let the code quality or
 * functionality of this class reflect on the quality the rest of the project.
 */
@RestController
public class TestEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestEndpoint.class);

    private final AuthHandler authHandler;
    private final SessionHandler sessionHandler;
    private final SongCache songCache;
    private final PlaylistCache playlistCache;
    private final FunctionComponentRepository functionComponentRepository;
    private final SongComponentRepository songComponentRepository;
    private final RawCollectionComponentRepository rawCollectionComponentRepository;
    private final SpotifyComponentRepository spotifyComponentRepository;
    private final SpotifyDataRepository spotifyDataRepository;

    public TestEndpoint(AuthHandler authHandler, SessionHandler sessionHandler, SongCache songCache, PlaylistCache playlistCache, FunctionComponentRepository functionComponentRepository, SongComponentRepository songComponentRepository, RawCollectionComponentRepository rawCollectionComponentRepository, SpotifyComponentRepository spotifyComponentRepository, SpotifyDataRepository spotifyDataRepository) {
        this.authHandler = authHandler;
        this.sessionHandler = sessionHandler;
        this.songCache = songCache;
        this.playlistCache = playlistCache;
        this.functionComponentRepository = functionComponentRepository;
        this.songComponentRepository = songComponentRepository;
        this.rawCollectionComponentRepository = rawCollectionComponentRepository;
        this.spotifyComponentRepository = spotifyComponentRepository;
        this.spotifyDataRepository = spotifyDataRepository;
    }

    @GetMapping("/foo")
    public ResponseEntity<?> foo() {
        var myBoard = UUID.fromString("e7144b81-1244-4fd8-9956-5eedeed8246d");
        var myFunction = UUID.fromString("33cb2a83-a4fe-43eb-ae63-189383af307b");

//        LOGGER.debug("name = {}", name);
//        LOGGER.debug("componentId = {}", componentId);

//        var songId = UUID.fromString("b593db01-d689-4e05-bac5-6fe69e00a80a");

//        var children = Stream.of("3idDCx8VXTkqPL6UQTK4bl", "7EllQktMuwLTLxDulLn0OY", "3EuZ0a81ka2N2EUbUeVeF5", "0HrGzCf2C0C75beCx6aUqu").map(songId -> {
//            var component = new SongComponent(UUID.randomUUID(), myBoard);
//            component.setSongId(new SpotifySongId(songId));
//            var id = songComponentRepository.save(component).getInstanceId();
//
//            LOGGER.info("Created component ID: {}", id);
//            return id;
//        }).toArray(UUID[]::new);
//
        var function = functionComponentRepository.findComponentOwnedBy(myFunction, "rubbaboy")
                .orElseThrow();
//
//        var createdFunction = new FunctionComponent(UUID.randomUUID(), myBoard);
//        createdFunction.setChildren(children);
//
//        functionComponentRepository.save(createdFunction);

//        var component = new RawCollectionComponent(UUID.randomUUID(), myBoard);
//        var songs = Stream.of(
//        "0mFaWgcWph8oldTKMnWeQW", "2hXwOk0PnRVceFA5O3EcNv", "5lruZuUNMmcBfVybmEjxvM")
//                .map(SpotifySongId::new)
//                        .toArray(SpotifySongId[]::new);
//        component.setSongs(songs);
//        component.setSequential(true);

        var comp = new SpotifyCollectionComponent(UUID.randomUUID(), myBoard);
        comp.setCollectionType(SpotifyCollectionType.PLAYLIST);
        var data = (SpotifyPlaylistData) comp.getCollectionData().orElseThrow();

        data.setPlaylistId(new SpotifyPlaylistId("4JbnWtTQ1yn947oMTGdwNE"));
//        spotifyComponentRepository.save()

        spotifyDataRepository.save(data);
        spotifyComponentRepository.save(comp);
//        spotifyDataRepository.delete(oldCollectionData);

        function.setChildren(new UUID[]{comp.getInstanceId()});

        functionComponentRepository.save(function);

        return new ResponseEntity<>(comp, HttpStatus.OK);
    }

    @GetMapping("/test_ownership")
    public ResponseEntity<?> testOwnership(@Param("name") String name, @Param("componentId") String componentId) {
        LOGGER.debug("name = {}", name);
        LOGGER.debug("componentId = {}", componentId);

        var component = functionComponentRepository.findComponentOwnedBy(UUID.fromString(componentId), name);

        LOGGER.debug("{}", component);
        return new ResponseEntity<>(component, HttpStatus.OK);
    }

    @GetMapping("/demo")
    public ResponseEntity<?> demo(@Param("id") String id) {
        var song = songCache.getSong(new SpotifySongId(id)).join();
        return new ResponseEntity<>(song, HttpStatus.OK);
    }

    @GetMapping("/playlist")
    public ResponseEntity<?> playlist(@Param("id") String id) {
        var song = playlistCache.getPlaylist(new SpotifyPlaylistId(id)).join();
        return new ResponseEntity<>(song, HttpStatus.OK);
    }

    @GetMapping("/playlist_tracks")
    public ResponseEntity<?> playlist(@Param("id") String id, @Param("offset") int offset, @Param("limit") int limit) {
        var song = playlistCache.getPlaylistSongs(new SpotifyPlaylistId(id), offset, limit).join();
        return new ResponseEntity<>(song, HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<?> login() {
        return new ResponseEntity<>(new MultiValueMapAdapter<>(Map.of("Location", List.of(authHandler.beginAuth().toString()))), HttpStatus.PERMANENT_REDIRECT);
    }

    @GetMapping("/redirect")
    public CompletableFuture<ResponseEntity<?>> test(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, SpotifyWebApiException {
        System.out.println(request.getQueryString());
        // Get `code` from query

        var query = UriComponentsBuilder.newInstance().query(request.getQueryString()).build().getQueryParams()
                .toSingleValueMap();

        return authHandler.completeAuth(query.get("code"))
                .thenApply(userSession -> {

                    response.addCookie(new Cookie("session", userSession.getSessionId().toString()));

//                    userSession.getSessionId()
//                            return new ResponseEntity<>(new MultiValueMapAdapter<>(Map.of("Set-Cookie", List.of("session="))), HttpStatus.OK);
                    return new ResponseEntity<>("Hello " + userSession.getUserInfo().getName(), HttpStatus.OK);
                });
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        var sessionIdOptional = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equalsIgnoreCase("session"))
                .findFirst();

        if (sessionIdOptional.isPresent()) {
            var sessionId = UUID.fromString(sessionIdOptional.get().getValue());
            sessionHandler.invalidateSession(sessionId);
            return new ResponseEntity<>("Logged out of session: " + sessionId, HttpStatus.OK);
        }

        LOGGER.info("No session found!");

        return new ResponseEntity<>("No session found!", HttpStatus.OK);
    }

    // A client would initially request this to verify its login stuff
    @GetMapping("/verify")
    public ResponseEntity<?> verify(HttpServletRequest request, HttpServletResponse response) {
        var sessionIdOptional = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equalsIgnoreCase("session"))
                .findFirst();

        if (sessionIdOptional.isPresent()) {
            var sessionId = UUID.fromString(sessionIdOptional.get().getValue());
            var userInfoOptional = sessionHandler.getUserInfo(sessionId).join();

            if (userInfoOptional.isEmpty()) {
                LOGGER.info("Invalid session! id: {}", sessionId);
                var cookie = new Cookie("session", null);
                cookie.setMaxAge(0);
                response.addCookie(cookie);

                return new ResponseEntity<>("Invalid session", HttpStatus.OK);
            }

            var userInfo = userInfoOptional.get();

            LOGGER.info("Session is for: {}", userInfo.getName());

            return new ResponseEntity<>(userInfo, HttpStatus.OK);
        }

        LOGGER.info("No session found!");

        return new ResponseEntity<>("No session found!", HttpStatus.OK);
    }

}
