package is.yarr.qilletni.content.song;

import is.yarr.qilletni.music.Song;
import is.yarr.qilletni.music.SpotifySong;
import is.yarr.qilletni.utility.SpotifyObjectUtils;
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
        return new SpotifySong(track.getId(),
                track.getName(),
                SpotifyObjectUtils.getPrimaryArtist(track),
                SpotifyObjectUtils.getPrimaryImage(track.getAlbum().getImages()));
    }
}
