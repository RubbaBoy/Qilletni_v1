package is.yarr.qilletni.database.converters;

import is.yarr.qilletni.music.SongId;
import is.yarr.qilletni.music.SpotifySongId;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class SpotifySongIdListConverter implements AttributeConverter<List<SongId>, String> {

    @Override
    public String convertToDatabaseColumn(List<SongId> attribute) {
        if (attribute == null) {
            return "";
        }

        return attribute.stream().map(SongId::id).collect(Collectors.joining(","));
    }

    @Override
    public List<SongId> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            return Collections.emptyList();
        }

        return Arrays.stream(dbData.split(","))
                .map(SpotifySongId::new)
                .map(SongId.class::cast)
                .toList();
    }
}
