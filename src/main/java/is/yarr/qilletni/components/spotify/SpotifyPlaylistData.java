package is.yarr.qilletni.components.spotify;

import is.yarr.qilletni.music.PlaylistId;

/**
 * Stores data for playing from a Spotify playlist.
 */
public class SpotifyPlaylistData implements SpotifyCollectionData {

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
