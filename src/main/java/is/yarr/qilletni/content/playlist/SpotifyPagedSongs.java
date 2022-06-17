package is.yarr.qilletni.content.playlist;

import is.yarr.qilletni.music.Song;

import java.util.List;

public class SpotifyPagedSongs implements PagedSongs {

    private final int limit;
    private final int offset;
    private final int total;
    private final boolean hasNext;
    private final List<Song> songs;

    public SpotifyPagedSongs(int limit, int offset, int total, boolean hasNext, List<Song> songs) {
        this.limit = limit;
        this.offset = offset;
        this.total = total;
        this.hasNext = hasNext;
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
    public boolean getHasNext() {
        return hasNext;
    }

    @Override
    public int getTotal() {
        return total;
    }

    @Override
    public List<Song> getSongs() {
        return songs;
    }
}
