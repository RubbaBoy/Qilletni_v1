package is.yarr.qilletni.components.spotify;

import is.yarr.qilletni.database.converters.SpotifyPlaylistIdConverter;
import is.yarr.qilletni.music.PlaylistId;

import javax.persistence.Convert;
import javax.persistence.Entity;

/**
 * Stores data for playing from a Spotify playlist.
 */
@Entity(name = "spotify_playlist_data")
public class SpotifyPlaylistData extends SpotifyCollectionData {

    @Convert(converter = SpotifyPlaylistIdConverter.class)
    private PlaylistId playlistId;

    @Override
    public SpotifyCollectionType getCollectionType() {
        return SpotifyCollectionType.PLAYLIST;
    }

    @Override
    public boolean isInitialized() {
        return playlistId != null;
    }

    @Override
    public boolean isShuffleSupported() {
        return true;
    }

    /**
     * Gets the playlist ID to play from.
     *
     * @return The playlist ID to play from
     */
    public PlaylistId getPlaylistId() {
        return playlistId;
    }

    /**
     * Sets the new playlist ID to play from.
     *
     * @param playlistId The new playlist ID
     */
    public void setPlaylistId(PlaylistId playlistId) {
        this.playlistId = playlistId;
    }
}
