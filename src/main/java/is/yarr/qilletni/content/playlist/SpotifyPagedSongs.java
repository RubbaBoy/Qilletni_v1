package is.yarr.qilletni.content.playlist;

import is.yarr.qilletni.music.Song;
import is.yarr.qilletni.music.SongId;

import java.util.List;

public class SpotifyPagedSongs implements PagedSongs {

    private final int limit;
    private final int offset;
    private final List<Song> songs;

    public SpotifyPagedSongs(int limit, int offset, List<Song> songs) {
        this.limit = limit;
        this.offset = offset;
        this.songs = songs;
    }

    @Override
    public int getLimit() {
        return limit;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public List<Song> getSongs() {
        return songs;
    }
}
