package is.yarr.qilletni.components.spotify;

import is.yarr.qilletni.grpc.gen.events.spotify.SpotifyTag;

public class SpotifySearchOptions {
    private String lookupData;
    private YearChooser yearChooser;
    private String genre;
    private SpotifyTag[] tags;
}