package is.yarr.qilletni.content.playlist;

import is.yarr.qilletni.music.Playlist;

/**
 * A {@link PagedSongs} object with a {@link Playlist} source.
 */
public interface PlaylistPagesSongs extends PagedSongs {

    /**
     * Gets the {@link Playlist} this {@link PagedSongs} object is for.
     *
     * @return The {@link Playlist}
     */
    Playlist getPlaylist();

}
