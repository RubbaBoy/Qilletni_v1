package is.yarr.qilletni.components;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Entity(name = "for_component")
public class ForComponent extends Component {

    @ElementCollection
    private List<UUID> children;
    private LoopStrategy loopStrategy = LoopStrategy.NONE;
    private Integer iterations;
    private Integer duration;
    private Date absTime;

    protected ForComponent() {}

    /**
     * Creates a base {@link Component} with a given instance ID.
     *
     * @param instanceId The instance ID
     * @param boardId The ID of the {@link is.yarr.qilletni.board.Board} this component is a part of
     */
    public ForComponent(UUID instanceId, UUID boardId) {
        super(instanceId, boardId);
    }

    @Override
    boolean isInitialized() {
        return children != null && loopStrategy.isLoopValid(this);
    }

    /**
     * Gets the children to iterate through, represented by component instance IDs.
     *
     * @return The childrens' IDs
     */
    public UUID[] getChildren() {
        return children.toArray(UUID[]::new);
    }

    /**
     * Sets the children of the loop.
     *
     * @param children The childrens' IDs
     */
    public void setChildren(UUID[] children) {
        this.children = List.of(children);
    }

    /**
     * Gets the strategy of the stopping point calculation for the loop. If this is {@link LoopStrategy#NONE}, this
     * will go on forever.
     *
     * @return The {@link LoopStrategy}
     */
    public LoopStrategy getLoopStrategy() {
        return loopStrategy;
    }

    /**
     * Sets the stopping strategy for the loop. This should be accompanied by the setting of another property, unless
     * the value is {@link LoopStrategy#NONE}.
     *
     * @param loopStrategy The {@link LoopStrategy}
     */
    public void setLoopStrategy(LoopStrategy loopStrategy) {
        this.loopStrategy = loopStrategy;
    }

    /**
     * Gets the amount of times to loop through this loop should iterate through. This property is only valid if a
     * non-empty optional is returned, and the loop strategy is {@link LoopStrategy#ITERATIONS}.
     *
     * @return The iteration count
     */
    public Optional<Integer> getIterations() {
        return Optional.ofNullable(iterations);
    }

    /**
     * Sets the amount of iterations this loop should go through before stopping. This property only has an effect if
     * the loop strategy is {@link LoopStrategy#ITERATIONS}.
     *
     * @param iterations The iteration count
     */
    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    /**
     * Clears the iteration count, making {@link #getIterations()} return an empty optional.
     */
    public void clearIterations() {
        this.iterations = null;
    }

    /**
     * Gets the amount of seconds the loop should iterate for. This property is only valid if a
     * non-empty optional is returned, and the loop strategy is {@link LoopStrategy#DURATION}.
     *
     * @return The duration of the loop in seconds
     */
    public Optional<Integer> getDuration() {
        return Optional.ofNullable(duration);
    }

    /**
     * Sets the amount of seconds the loop should iterate for. This property only has an effect if
     * the loop strategy is {@link LoopStrategy#DURATION}.
     *
     * @param duration The duration in seconds
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Clears the duration, making {@link #getDuration()} return an empty optional.
     */
    public void clearDuration() {
        this.duration = null;
    }

    /**
     * Gets the absolute time to stop the loop at. This property is only valid if a
     * non-empty optional is returned, and the loop strategy is {@link LoopStrategy#ABS_TIME}.
     *
     * @return The time to stop the loop
     */
    public Optional<Date> getAbsTime() {
        return Optional.ofNullable(absTime);
    }

    /**
     * Sets the absolute time the loop should stop. This property only has an effect if
     * the loop strategy is {@link LoopStrategy#ABS_TIME}.
     *
     * @param absTime The time the loop should stop
     */
    public void setAbsTime(Date absTime) {
        this.absTime = absTime;
    }

    /**
     * Clears the absolute time, making {@link #getAbsTime()} return an empty optional.
     */
    public void clearAbsTime() {
        this.absTime = null;
    }

    /**
     * The strategy to stop the loop.
     */
    enum LoopStrategy {

        /**
         * The loop will never stop.
         */
        NONE($ -> true),

        /**
         * The loop will stop after a certain amount of iterations, set by {@link #setIterations(int)}.
         */
        ITERATIONS(loop -> loop.getIterations().isPresent()),

        /**
         * The loop will stop after a set number of seconds have passed, set by {@link #setDuration(int)}.
         */
        DURATION(loop -> loop.getDuration().isPresent()),

        /**
         * The loop will stop at a certain exact time of day, set by {@link #setAbsTime(Date).}
         */
        ABS_TIME(loop -> loop.getAbsTime().isPresent());

        private final Function<ForComponent, Boolean> validityChecker;

        /**
         * Creates a {@link LoopStrategy} with a function to check if the associated properties are present in a given
         * {@link ForComponent}.
         *
         * @param validityChecker The checker for the strategy
         */
        LoopStrategy(Function<ForComponent, Boolean> validityChecker) {
            this.validityChecker = validityChecker;
        }

        /**
         * Checks if the given {@link ForComponent} has all necessary fields present according to the current
         * {@link LoopStrategy}, used for a validity check.
         *
         * @param component The {@link ForComponent} to check
         * @return If the component is valid
         */
        public boolean isLoopValid(ForComponent component) {
            return validityChecker.apply(component);
        }
    }
}
