package is.yarr.qilletni.database.converters;

import is.yarr.qilletni.music.AlbumId;
import is.yarr.qilletni.music.SpotifyAlbumId;

import javax.annotation.Nonnull;
import javax.persistence.Converter;

@Converter
public class AlbumIdConverter extends NullAwareAttributeConverter<AlbumId, String> {

    @Override
    public String convertNonNullToDatabaseColumn(@Nonnull AlbumId attribute) {
        return attribute.id();
    }

    @Override
    public AlbumId convertNonNullToEntityAttribute(@Nonnull String id) {
        return new SpotifyAlbumId(id, "spotify:album:" + id);
    }
}
