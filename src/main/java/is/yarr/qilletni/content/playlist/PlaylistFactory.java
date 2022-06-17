package is.yarr.qilletni.content.playlist;

import is.yarr.qilletni.music.Playlist;
import is.yarr.qilletni.music.SpotifyPlaylist;
import is.yarr.qilletni.utility.SpotifyObjectUtils;

/**
 * A factory to create {@link is.yarr.qilletni.music.Playlist} objects from any necessary source objects.
 */
public class PlaylistFactory {

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
                spotifyPlaylist.getFollowers().getTotal(),
                spotifyPlaylist.getDescription(),
                spotifyPlaylist.getIsPublicAccess());
    }

}
