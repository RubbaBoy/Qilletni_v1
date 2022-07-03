package is.yarr.qilletni.content.playlist;

import is.yarr.qilletni.music.Playlist;
import is.yarr.qilletni.music.SpotifyPlaylist;
import is.yarr.qilletni.utility.SpotifyObjectUtils;

import javax.annotation.Nullable;

/**
 * A factory to create {@link is.yarr.qilletni.music.Playlist} objects from any necessary source objects.
 */
public class PlaylistFactory {

    /**
     * Creates a Qilletni {@link Playlist} from a Spotify API {@link se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified}.
     *
     * @param spotifyPlaylist The {@link se.michaelthelin.spotify.model_objects.specification.Playlist} from an API call
     * @return The created {@link Playlist}
     */
    public static Playlist createPlaylist(se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified spotifyPlaylist) {
        return new SpotifyPlaylist(spotifyPlaylist.getId(),
                spotifyPlaylist.getName(),
                spotifyPlaylist.getOwner().getDisplayName(),
                SpotifyObjectUtils.getPrimaryImage(spotifyPlaylist.getImages()),
                spotifyPlaylist.getTracks().getTotal(),
                booleanValueOrDefault(spotifyPlaylist.getIsPublicAccess(), true));
    }

    /**
     * Creates a Qilletni {@link Playlist} from a Spotify API {@link se.michaelthelin.spotify.model_objects.specification.Playlist}.
     *
     * @param spotifyPlaylist The {@link se.michaelthelin.spotify.model_objects.specification.Playlist} from an API call
     * @return The created {@link Playlist}
     */
    public static Playlist createPlaylist(se.michaelthelin.spotify.model_objects.specification.Playlist spotifyPlaylist) {
        return new SpotifyPlaylist(spotifyPlaylist.getId(),
                spotifyPlaylist.getName(),
                spotifyPlaylist.getOwner().getDisplayName(),
                SpotifyObjectUtils.getPrimaryImage(spotifyPlaylist.getImages()),
                spotifyPlaylist.getTracks().getTotal(),
                booleanValueOrDefault(spotifyPlaylist.getIsPublicAccess(), true));
    }

    /**
     * If the given {@link Boolean} isn't null, the value is returned. Otherwise, the provided default is returned.
     *
     * @param bool The nullable {@link Boolean}
     * @param def  The default value
     * @return The boolean value
     */
    private static boolean booleanValueOrDefault(@Nullable Boolean bool, boolean def) {
        if (bool == null) {
            return def;
        }

        return bool;
    }

}
