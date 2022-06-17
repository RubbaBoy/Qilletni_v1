package is.yarr.qilletni.database.repositories.external;

import is.yarr.qilletni.music.SpotifySong;
import org.springframework.data.repository.CrudRepository;

public interface SpotifySongRepository extends CrudRepository<SpotifySong, String> {
}
