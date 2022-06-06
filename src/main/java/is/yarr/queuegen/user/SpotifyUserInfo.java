package is.yarr.queuegen.user;

public record SpotifyUserInfo(String id, String name, String email, String avatarUrl) implements UserInfo {

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
}
