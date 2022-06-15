package is.yarr.qilletni.database.converters;

import javax.annotation.Nonnull;
import javax.persistence.Converter;
import java.awt.Color;

@Converter
public class ColorConverter extends NullAwareAttributeConverter<Color, Integer> {

    /**
     * Convert Color object to an integer via {@link Color#getRGB()}.
     *
     * @param color The {@link Color} being converted
     * @return The RGB of the color
     */
    @Override
    public Integer convertNonNullToDatabaseColumn(@Nonnull Color color) {
        return color.getRGB();
    }

    /**
     * Convert an integer created by {@link Color#getRGB()} into a {@link Color}.
     *
     * @param colorInt The color RGB integer
     * @return The created color
     */
    @Override
    public Color convertNonNullToEntityAttribute(@Nonnull Integer colorInt) {
        return new Color(colorInt);
    }

}
