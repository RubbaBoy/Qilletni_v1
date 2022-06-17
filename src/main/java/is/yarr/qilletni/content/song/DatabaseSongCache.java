package is.yarr.qilletni.content.song;

import is.yarr.qilletni.database.repositories.external.SongRepository;
import is.yarr.qilletni.music.Song;
import is.yarr.qilletni.music.SongId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class DatabaseSongCache implements SongCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseSongCache.class);

    private final SongRetriever songRetriever;
    private final SongRepository songRepository;

    public DatabaseSongCache(SongRetriever songRetriever, SongRepository songRepository) {
        this.songRetriever = songRetriever;
        this.songRepository = songRepository;
    }

    @Async
    @Override
    public CompletableFuture<Song> getSong(SongId songId) {
        LOGGER.debug("Getting song {}", songId.id());
        var songOptional = songRepository.findById(songId);

        return songOptional.map(CompletableFuture::completedFuture)
                .orElseGet(() -> songRetriever.fetchSong(songId)
                        .thenApply(songRepository::save));
    }

    @Async
    @Override
    public CompletableFuture<List<Song>> getSongs(List<SongId> songIds) {
        var returningSongs = new ArrayList<Song>();

        var unknownSongs = songIds.parallelStream()
                .filter(songId -> songRepository.findById(songId)
                        .map(returningSongs::add)
                        .isEmpty())
                .toList();

        return songRetriever.fetchSongs(unknownSongs)
                .thenApply(retrievedSongs -> {
                    returningSongs.addAll(retrievedSongs);
                    return Collections.unmodifiableList(returningSongs);
                });
    }
}
