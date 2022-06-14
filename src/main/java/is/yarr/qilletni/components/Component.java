package is.yarr.qilletni.components;

import javax.annotation.Nullable;
import java.awt.Color;
import java.util.UUID;

/**
 * The base class of displayable objects that control the flow of the created queue.
 */
public abstract class Component {

    private final UUID instanceId;
    private Color color;

    /**
     * Creates a base {@link Component} with a given instance ID.
     *
     * @param instanceId The instance ID
     */
    protected Component(UUID instanceId) {
        this.instanceId = instanceId;
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
