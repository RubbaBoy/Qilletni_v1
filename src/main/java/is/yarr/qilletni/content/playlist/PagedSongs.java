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
     * Gets if the paging source has a next available page.
     *
     * @return If another page after this is available
     */
    boolean getHasNext();

    /**
     * Gets the total amount of songs in the source this object came from.
     *
     * @return The total songs in the source
     */
    int getTotal();

    /**
     * Gets a list of songs in the paged list.
     *
     * @return The songs
     */
    List<Song> getSongs();

}
