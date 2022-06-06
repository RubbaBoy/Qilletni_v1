package is.yarr.queuegen.rest;

import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
public class TestEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestEndpoint.class);

    private static final String clientId = System.getenv("SPOTIFY_CLIENT_ID");
    private static final String clientSecret = System.getenv("SPOTIFY_CLIENT_SECRET");
    private static final URI redirectUri = URI.create("http://localhost:8000/redirect");

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();

    /*
     * Step 1: User goes to /login and gets redirected to the Spotify OAuth page
     */

    record UserData(String token, String accessToken) {}


    @GetMapping("/login")
    public ResponseEntity<?> login(HttpServletRequest request) {
        // codeVerifier -> a cryptographically random string between 43 and 128 characters in length. It can contain letters, digits, underscores, periods, hyphens, or tildes and is generated
        // codeChallenge -> base64url encoded sha256-hash of the code verifier
        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri().state("STATE").build();
//        return new ResponseEntity<>(authorizationCodeUriRequest.execute().toString(), HttpStatus.PERMANENT_REDIRECT);
//        return authorizationCodeUriRequest.execute().toString();
        return new ResponseEntity<>(new MultiValueMapAdapter<>(Map.of("Location", List.of(authorizationCodeUriRequest.execute().toString()))), HttpStatus.PERMANENT_REDIRECT);
    }

    @GetMapping("/redirect")
    public ResponseEntity<?> test(HttpServletRequest request) throws IOException, ParseException, SpotifyWebApiException {
        System.out.println(request.getQueryString());
        // Get `code` from query

        var query = UriComponentsBuilder.newInstance().query(request.getQueryString()).build().getQueryParams()
                .toSingleValueMap();

        System.out.println("query = " + query);

        var authorizationCodeRequest = spotifyApi.authorizationCode(query.get("code")).build();
        var authorizationCodeCredentials = authorizationCodeRequest.execute();
//
////        // Set access and refresh token for further "spotifyApi" object usage
        spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
        spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
//
//        System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
