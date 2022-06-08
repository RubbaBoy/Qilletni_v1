package is.yarr.queuegen.user;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "user_info")
public final class SpotifyUserInfo implements UserInfo {

    @Id
    private String id;
    private String name;
    private String email;
    private String avatarUrl;

    public SpotifyUserInfo() {}

    public SpotifyUserInfo(String id, String name, String email, String avatarUrl) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getAvatarUrl() {
        return avatarUrl;
    }

    @Override
    public String toString() {
        return "SpotifyUserInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}
