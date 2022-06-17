package is.yarr.qilletni.content.song;

import is.yarr.qilletni.music.Song;
import is.yarr.qilletni.music.SpotifySong;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.model_objects.specification.Track;

/**
 * A factory to create {@link Song} objects from any necessary source objects.
 */
public class SongFactory {

    /**
     * Creates a {@link Song} from a given Spotify {@link Track}.
     *
     * @param track The {@link Track} to create the {@link Song} from.
     * @return The created {@link Song}
     */
    public static Song createSong(Track track) {
        return new SpotifySong(track.getId(), track.getName(), getPrimaryArtist(track), getPrimaryImage(track));
    }

    /**
     * Gets the first artist's name from the given {@link Track}, or a placeholder "-" string.
     *
     * @param track The track to get the artist from
     * @return The first artist's name
     */
    private static String getPrimaryArtist(Track track) {
        ArtistSimplified[] artists = track.getArtists();
        if (artists.length == 0) {
            return "-";
        }

        return artists[0].getName();
    }

    /**
     * Gets the largest album image from the given {@link Track}, or an empty string.
     *
     * @param track The track to get the album art from
     * @return The largest image's URL
     */
    private static String getPrimaryImage(Track track) {
        Image[] albumImages = track.getAlbum().getImages();
        if (albumImages.length == 0) {
            return "";
        }

        return albumImages[0].getUrl();
    }

}
