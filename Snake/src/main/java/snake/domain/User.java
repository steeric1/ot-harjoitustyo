package snake.domain;

import java.util.UUID;
import javafx.scene.paint.Color;

/**
 * Represents a user, having an ID, a username and a snake colour.
 */
public class User {
    private UUID uuid; // Username may change, this stays constant.
    private String username;
    private Color snakeColor;

    public User(UUID uuid, String name, Color color) {
        this.uuid = uuid;
        this.username = name;
        this.snakeColor = color;
    }
    
    public User(String name) {
        this(UUID.randomUUID(), name, Color.GREEN);
    }
    
    /**
     * The UUID will always stay the same even if the user's name is changed.
     * 
     * @return The UUID of the user.
     */
    public UUID getId() {
        return this.uuid;
    }
    
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String name) {
        this.username = name;
    }
    
    public Color getColor() {
        return this.snakeColor;
    }
    
    @Override
    public boolean equals(Object other) {
        if (other.getClass() != this.getClass()) {
            return false;
        }
        
        User otherUser = (User) other;
        return this.uuid.equals(otherUser.uuid);
    }
}
