package snake.domain;

import java.util.UUID;
import javafx.scene.paint.Color;

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
