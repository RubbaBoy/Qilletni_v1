package is.yarr.qilletni.database.converters;

import is.yarr.qilletni.music.SongId;
import is.yarr.qilletni.music.SpotifySongId;

import javax.annotation.Nonnull;
import javax.persistence.Converter;

@Converter
public class SongIdConverter extends NullAwareAttributeConverter<SongId, String> {

    @Override
    public String convertNonNullToDatabaseColumn(@Nonnull SongId attribute) {
        return attribute.id();
    }

    @Override
    public SongId convertNonNullToEntityAttribute(@Nonnull String id) {
        return songIdFromIdString(id);
    }

    public static SongId songIdFromIdString(@Nonnull String id) {
        return new SpotifySongId(id, "spotify:track:" + id);
    }
}
