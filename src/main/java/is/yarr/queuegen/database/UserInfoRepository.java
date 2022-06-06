package is.yarr.queuegen.database;

import is.yarr.queuegen.user.SpotifyUserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserInfoRepository extends CrudRepository<SpotifyUserInfo, String> {
}
