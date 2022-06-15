package is.yarr.qilletni.database.converters;

import javax.annotation.Nonnull;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * A {@link AttributeConverter} but values given are never null.
 *
 * @param <X> The field type
 * @param <Y> The database type
 */
@Converter
public abstract class NullAwareAttributeConverter<X, Y> implements AttributeConverter<X, Y> {

    @Override
    public Y convertToDatabaseColumn(X attribute) {
        if (attribute == null) {
            return null;
        }

        return convertNonNullToDatabaseColumn(attribute);
    }

    @Override
    public X convertToEntityAttribute(Y dbData) {
        if (dbData == null) {
            return null;
        }

        return convertNonNullToEntityAttribute(dbData);
    }

    /**
     * Converts a non-null attribute of type {@link X} to database value of type {@link Y}.
     *
     * @param attribute The attribute to convert
     * @return The converted value
     */
    public abstract Y convertNonNullToDatabaseColumn(@Nonnull X attribute);

    /**
     * Converts a non-null database value of type {@link X} to converted type {@link Y}.
     *
     * @param dbData The database value
     * @return The created type
     */
    public abstract X convertNonNullToEntityAttribute(@Nonnull Y dbData);
}
