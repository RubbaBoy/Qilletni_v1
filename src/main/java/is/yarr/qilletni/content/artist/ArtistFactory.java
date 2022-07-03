package is.yarr.qilletni.content.artist;

import is.yarr.qilletni.database.UnsupportedTypeException;
import is.yarr.qilletni.music.Artist;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;

/**
 * A factory to create {@link Artist} objects from any necessary source objects.
 */
public class ArtistFactory {

    /**
     * Creates a Qilletni {@link Artist} from a Spotify API {@link ArtistSimplified}.
     *
     * @param spotifyArtist The {@link ArtistSimplified} from an API call
     * @return The created {@link Artist}
     */
    public static Artist createArtist(ArtistSimplified spotifyArtist) {
        // TODO: QTNB-18
        throw new UnsupportedTypeException("createArtist has not yet been implemented!");
    }

    /**
     * Creates a Qilletni {@link Artist} from a Spotify API {@link se.michaelthelin.spotify.model_objects.specification.Artist}.
     *
     * @param spotifyArtist The {@link se.michaelthelin.spotify.model_objects.specification.Artist} from an API call
     * @return The created {@link Artist}
     */
    public static Artist createArtist(se.michaelthelin.spotify.model_objects.specification.Artist spotifyArtist) {
        // TODO: QTNB-18
        throw new UnsupportedTypeException("createArtist has not yet been implemented!");
    }

}
