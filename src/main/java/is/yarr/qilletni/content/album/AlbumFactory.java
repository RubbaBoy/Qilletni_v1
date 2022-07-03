package is.yarr.qilletni.content.album;

import is.yarr.qilletni.components.spotify.SpotifyAlbumData;
import is.yarr.qilletni.database.UnsupportedTypeException;
import is.yarr.qilletni.music.Album;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;

/**
 * A factory to create {@link Album} objects from any necessary source objects.
 */
public class AlbumFactory {

    /**
     * Creates a Qilletni {@link Album} from a Spotify API {@link AlbumSimplified}.
     *
     * @param spotifyAlbum The {@link AlbumSimplified} from an API call
     * @return The created {@link Album}
     */
    public static Album createAlbum(AlbumSimplified spotifyAlbum) {
        // TODO: QTNB-18
        throw new UnsupportedTypeException("createAlbum has not yet been implemented!");
    }

    /**
     * Creates a Qilletni {@link Album} from a Spotify API {@link se.michaelthelin.spotify.model_objects.specification.Album}.
     *
     * @param spotifyAlbum The {@link se.michaelthelin.spotify.model_objects.specification.Album} from an API call
     * @return The created {@link Album}
     */
    public static Album createAlbum(se.michaelthelin.spotify.model_objects.specification.Album spotifyAlbum) {
        // TODO: QTNB-18
        throw new UnsupportedTypeException("createAlbum has not yet been implemented!");
    }

}
