package is.yarr.qilletni.database.converters;

import is.yarr.qilletni.music.PlaylistId;
import is.yarr.qilletni.music.SpotifyPlaylistId;

import javax.annotation.Nonnull;
import javax.persistence.Converter;

@Converter
public class PlaylistIdConverter extends NullAwareAttributeConverter<PlaylistId, String> {

    @Override
    public String convertNonNullToDatabaseColumn(@Nonnull PlaylistId attribute) {
        return attribute.id();
    }

    @Override
    public PlaylistId convertNonNullToEntityAttribute(@Nonnull String id) {
        // TODO: Is the format spotify:playlist:id?
        return new SpotifyPlaylistId(id, "spotify:playlist:" + id);
    }
}
