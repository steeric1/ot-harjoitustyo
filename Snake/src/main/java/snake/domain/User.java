package snake.domain;

public class User {
    /**
     * The name of this user.
     */
    private String username;

    /**
     * Create a new user object.
     *
     * @param name The name of this user.
     */
    public User(final String name) {
        this.username = name;
    }

    /**
     * @return The name of the user.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Set the name of the user.
     *
     * @param name The new username.
     */
    public void setUsername(final String name) {
        this.username = name;
    }
}
