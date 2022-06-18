package is.yarr.qilletni.database.repositories.external;

import is.yarr.qilletni.music.SpotifyPlaylist;
import org.springframework.data.repository.CrudRepository;

interface SpotifyPlaylistRepository extends CrudRepository<SpotifyPlaylist, String> {
}
