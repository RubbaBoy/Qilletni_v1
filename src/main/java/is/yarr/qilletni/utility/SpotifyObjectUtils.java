package is.yarr.qilletni.utility;

import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.model_objects.specification.Track;

public class SpotifyObjectUtils {

    /**
     * Chooses an image's URL from an array of images, provided by the Spotify API.
     *
     * @param albumImages The images to look through
     * @return The largest image's URL
     */
    public static String getPrimaryImage(Image[] albumImages) {
        if (albumImages.length == 0) {
            return "";
        }

        return albumImages[0].getUrl();
    }

    /**
     * Gets the first artist's name from the given {@link Track}, or a placeholder "-" string.
     *
     * @param track The track to get the artist from
     * @return The first artist's name
     */
    public static String getPrimaryArtist(Track track) {
        ArtistSimplified[] artists = track.getArtists();
        if (artists.length == 0) {
            return "-";
        }

        return artists[0].getName();
    }

}
