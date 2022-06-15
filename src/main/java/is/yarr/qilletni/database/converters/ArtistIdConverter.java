package is.yarr.qilletni.database.converters;

import is.yarr.qilletni.music.ArtistId;
import is.yarr.qilletni.music.SpotifyArtistId;

import javax.annotation.Nonnull;
import javax.persistence.Converter;

@Converter
public class ArtistIdConverter extends NullAwareAttributeConverter<ArtistId, String> {

    @Override
    public String convertNonNullToDatabaseColumn(@Nonnull ArtistId attribute) {
        return attribute.id();
    }

    @Override
    public ArtistId convertNonNullToEntityAttribute(@Nonnull String id) {
        return new SpotifyArtistId(id, "spotify:artist:" + id);
    }
}
