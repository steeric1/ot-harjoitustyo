package snake.domain;

import java.util.UUID;

/**
 * A score that consists of a value and the owner's ID.
 */
public class Score {
    private int value;
    private UUID ownerId;
    
    public Score(int value, UUID id) {
        this.value = value;
        this.ownerId = id;
    }
    
    public int getValue() {
        return this.value;
    }
    
    public UUID getOwnerId() {
        return this.ownerId;
    }
}
