package is.yarr.qilletni.database.repositories.external;

class UnsupportedTypeException extends RuntimeException {
    UnsupportedTypeException(Object type) {
        super("Operation is unsupported for a class of type: " + type.getClass().getTypeName());
    }
}
