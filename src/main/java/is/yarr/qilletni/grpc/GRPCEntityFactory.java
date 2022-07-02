package is.yarr.qilletni.grpc;

import is.yarr.qilletni.grpc.gen.request.Album;
import is.yarr.qilletni.grpc.gen.request.Artist;
import is.yarr.qilletni.grpc.gen.request.Playlist;
import is.yarr.qilletni.grpc.gen.request.Song;

/**
 * A factory to convert DAOs to GRPC objects.
 */
public class GRPCEntityFactory {

    /**
     * Creates a gRPC DTO {@link Song} object from a given song.
     *
     * @param song The song to convert
     * @return The created {@link Song}
     */
    public static Song createGRPCSong(is.yarr.qilletni.music.Song song) {
        return Song.newBuilder()
                .setId(song.getId())
                .setName(song.getName())
                .setArtist(song.getArtist())
                .setArtworkUrl(song.getArtworkUrl())
                .build();
    }

    /**
     * Creates a gRPC {@link Album} DTO from a given album.
     *
     * @param album The album to convert
     * @return The created {@link Album}
     */
    public static Album createGRPCAlbum(is.yarr.qilletni.music.Album album) {
        return Album.newBuilder()
                .setId(album.getId())
                .setName(album.getName())
                .setArtworkUrl(album.getArtworkUrl())
                .build();
    }

    /**
     * Creates a gRPC {@link Artist} DTO from a given artist.
     *
     * @param artist The artist to convert
     * @return The created {@link Artist}
     */
    public static Artist createGRPCArtist(is.yarr.qilletni.music.Artist artist) {
        return Artist.newBuilder()
                .setId(artist.getId())
                .setName(artist.getName())
                .setArtworkUrl(artist.getArtworkUrl())
                .build();
    }

    /**
     * Creates a gRPC {@link Playlist} DTO from a given playlist.
     *
     * @param playlist The playlist to convert
     * @return The created {@link Playlist}
     */
    public static Playlist createGRPCPlaylist(is.yarr.qilletni.music.Playlist playlist) {
        return Playlist.newBuilder()
                .setId(playlist.getId())
                .setName(playlist.getName())
                .setOwner(playlist.getOwner())
                .setArtworkUrl(playlist.getArtworkUrl())
                .setFollowers(playlist.getFollowers())
                .setDescription(playlist.getDescription())
                .build();
    }

}
