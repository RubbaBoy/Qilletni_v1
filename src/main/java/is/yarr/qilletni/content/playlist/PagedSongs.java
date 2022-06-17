package is.yarr.qilletni.content.playlist;

import is.yarr.qilletni.music.Song;

import java.util.List;

/**
 * A collection of songs that came from a paged source.
 */
public interface PagedSongs {

    /**
     * Gets the maximum amount of songs this objects may contain.
     *
     * @return The page limit
     */
    int getLimit();

    /**
     * Gets the song offset from the original request this could have come from.
     *
     * @return The amount of songs that came before this
     */
    int getOffset();

    /**
     * Gets a list of songs in the paged list.
     *
     * @return The songs
     */
    List<Song> getSongs();

}
