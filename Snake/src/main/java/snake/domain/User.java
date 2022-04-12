package snake.domain;

public class User {
    private String username;

    public User(String name) {
        this.username = name;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String name) {
        this.username = name;
    }
}
