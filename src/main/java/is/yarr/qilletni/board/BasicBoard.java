package is.yarr.qilletni.board;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity(name = "boards")
public class BasicBoard implements Board {

    @Id
    private UUID id;
    private String name;
    private String ownerId;

    protected BasicBoard() {}

    public BasicBoard(UUID id, String name, String ownerId) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getOwnerId() {
        return ownerId;
    }

}
