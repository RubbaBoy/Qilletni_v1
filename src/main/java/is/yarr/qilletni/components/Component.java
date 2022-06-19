package is.yarr.qilletni.components;

import is.yarr.qilletni.board.Board;
import is.yarr.qilletni.database.converters.ColorConverter;

import javax.annotation.Nullable;
import javax.persistence.Convert;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.awt.Color;
import java.util.UUID;

/**
 * The base class of displayable objects that control the flow of the created queue.
 */
@MappedSuperclass
public abstract class Component {

    @Id
    private UUID instanceId;
    private UUID boardId;

    @Convert(converter = ColorConverter.class)
    private Color color;

    Component() {}

    /**
     * Creates a base {@link Component} with a given instance ID.
     *
     * @param instanceId The instance ID
     */
    protected Component(UUID instanceId, UUID boardId) {
        this.instanceId = instanceId;
        this.boardId = boardId;
    }

    /**
     * Checks if all required fields are non-null and valid. If this returns {@code false}, do not expect anything to
     * work.
     *
     * @return If the component is ready to play
     */
    abstract boolean isInitialized();

    /**
     * Gets the unique identifier of the Component instance.
     *
     * @return The component's UUID
     */
    public UUID getInstanceId() {
        return instanceId;
    }

    /**
     * Gets the {@link Board} ID that this component is a part of.
     *
     * @return The board ID
     */
    public UUID getBoardId() {
        return boardId;
    }

    /**
     * Gets the display color.
     *
     * @return The display color
     */
    public @Nullable Color getColor() {
        return color;
    }

    /**
     * Sets the component's display color.
     *
     * @param color The color of the component
     */
    public void setColor(Color color) {
        this.color = color;
    }
}
