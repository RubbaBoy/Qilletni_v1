package is.yarr.qilletni.components;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A component that contains a collection of children that will be iterated through and invoked upon generation.
 */
@Entity(name = "function_component")
public class FunctionComponent extends Component {

    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<UUID> children;

    protected FunctionComponent() {}

    /**
     * Creates a base {@link Component} with a given instance ID.
     *
     * @param instanceId The instance ID
     * @param boardId The ID of the {@link is.yarr.qilletni.board.Board} this component is a part of
     */
    public FunctionComponent(UUID instanceId, UUID boardId) {
        super(instanceId, boardId);
    }

    @Override
    public boolean isInitialized() {
        return name != null && children != null;
    }

    /**
     * Gets the name of the function.
     *
     * @return The name of the function
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the function.
     *
     * @param name The name of the function
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the children's instance IDs to be iterated through.
     *
     * @return The childrens' IDs
     */
    public UUID[] getChildren() {
        return children.toArray(UUID[]::new);
    }

    /**
     * Sets the new childrens' instance IDs.
     *
     * @param children The childrens' IDs
     */
    public void setChildren(UUID[] children) {
        this.children = new ArrayList<>(List.of(children));
    }
}
