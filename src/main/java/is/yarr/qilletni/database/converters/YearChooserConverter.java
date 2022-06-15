package is.yarr.qilletni.database.converters;

import is.yarr.qilletni.components.spotify.YearChooser;

import javax.annotation.Nonnull;
import javax.persistence.Converter;

@Converter
public class YearChooserConverter extends NullAwareAttributeConverter<YearChooser, String> {

    @Override
    public String convertNonNullToDatabaseColumn(@Nonnull YearChooser attribute) {
        if (attribute.isRange()) {
            var range = attribute.getRange();
            return range.startYear() + "-" + range.endYear();
        }

        return attribute.getYear().toString();
    }

    @Override
    public YearChooser convertNonNullToEntityAttribute(@Nonnull String data) {
        if (data.contains("-")) {
            var splitColon = data.split(":");
            int startYear = Integer.parseInt(splitColon[0]);
            int endYear = Integer.parseInt(splitColon[1]);

            return YearChooser.createFromRange(startYear, endYear);
        }

        var year = Integer.parseInt(data);
        return YearChooser.createFromYear(year);
    }
}
