package is.yarr.queuegen.components;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class ForLoopComponent extends Component {

    private UUID[] children;

    @Nullable
    private Integer iterations;

    /**
     * Creates a base {@link Component} with a given instance ID.
     *
     * @param instanceId The instance ID
     */
    protected ForLoopComponent(UUID instanceId) {
        super(instanceId);
    }

    @Override
    boolean isInitialized() {
        return children != null;
    }

    /**
     * Gets the children to iterate through, represented by component instance IDs.
     *
     * @return The childrens' IDs
     */
    public UUID[] getChildren() {
        return children;
    }

    /**
     * Sets the children of the loop.
     *
     * @param children The childrens' IDs
     */
    public void setChildren(UUID[] children) {
        this.children = children;
    }

    /**
     * Gets the amount of times to loop through this
     *
     * @return
     */
    public Optional<Integer> getIterations() {
        return Optional.ofNullable(iterations);
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public void clearIterations() {
        this.iterations = null;
    }
}
