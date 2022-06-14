package is.yarr.qilletni.rest;

import is.yarr.qilletni.auth.AuthHandler;
import is.yarr.qilletni.auth.SessionHandler;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@RestController
public class TestEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestEndpoint.class);

    private final AuthHandler authHandler;
    private final SessionHandler sessionHandler;

    public TestEndpoint(AuthHandler authHandler, SessionHandler sessionHandler) {
        this.authHandler = authHandler;
        this.sessionHandler = sessionHandler;
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
