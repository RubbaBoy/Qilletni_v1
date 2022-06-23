package is.yarr.qilletni.database;

/**
 * An exception thrown when an operation is performed on an object with an unsupported type, typically in repositories.
 */
public class UnsupportedTypeException extends RuntimeException {

    /**
     * Creates an exception with the offending object, to print out its type.
     *
     * @param type The offending object
     */
    public UnsupportedTypeException(Object type) {
        super("Operation is unsupported for a class of type: " + type.getClass().getSimpleName());
    }
}
