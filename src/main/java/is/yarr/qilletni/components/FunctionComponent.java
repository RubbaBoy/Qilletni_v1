package is.yarr.qilletni.components;

import java.util.UUID;

/**
 * A component that contains a collection of children that will be iterated through and invoked upon generation.
 */
public class FunctionComponent extends Component {

    private String name;
    private UUID[] children;

    /**
     * Creates a base {@link Component} with a given instance ID.
     *
     * @param instanceId The instance ID
     */
    protected FunctionComponent(UUID instanceId) {
        super(instanceId);
    }

    @Override
    boolean isInitialized() {
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
        return children;
    }

    /**
     * Sets the new childrens' instance IDs.
     *
     * @param children The childrens' IDs
     */
    public void setChildren(UUID[] children) {
        this.children = children;
    }
}
