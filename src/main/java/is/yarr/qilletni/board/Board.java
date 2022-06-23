package is.yarr.qilletni.board;

import is.yarr.qilletni.user.UserInfo;

import java.util.UUID;

/**
 * The owning object for all components in a "queue flow".
 */
public interface Board {

    /**
     * Gets the ID of the board.
     *
     * @return The board's ID
     */
    UUID getId();

    /**
     * Gets the name of the board.
     *
     * @return The board's name
     */
    String getName();

    /**
     * Sets the name of the board.
     *
     * @param name The board's new name
     */
    void setName(String name);

    /**
     * Gets the owning {@link UserInfo}'s ID of the board.
     *
     * @return The board owner's ID
     */
    String getOwnerId();
}
