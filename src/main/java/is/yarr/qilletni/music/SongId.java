package is.yarr.qilletni.music;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * The identifier of a song, provided by an external API.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "type_stuff")
public abstract class SongId {

    @Id
    String id;

    /**
     * Gets the ID string of the song.
     *
     * @return The song's ID
     */
    public String id() {
        return id;
    }

}
