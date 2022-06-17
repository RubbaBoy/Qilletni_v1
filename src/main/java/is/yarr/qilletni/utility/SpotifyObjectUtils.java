package is.yarr.qilletni.utility;

import se.michaelthelin.spotify.model_objects.specification.Image;

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

}
