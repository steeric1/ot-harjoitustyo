package snake.domain;

import snake.dao.UserDao;

public final class SnakeService {

    /**
     * The DAO this instance uses to manage users.
     */
    private UserDao userDao;

    /**
     * A service object that manages the main logic behind the application.
     *
     * @param dao   The user data access object that manages users for this
     *                  service object.
     */
    public SnakeService(final UserDao dao) {
        this.userDao = dao;
    }

    /**
     * Create a user.
     *
     * @param username The name for the new user.
     * @return An object that tells what the result of the creation was.
     */
    public UserCreationResult createUser(final String username) {
        User user = new User(username);
        UserCreationResult isValid = this.validateUser(user);
        if (isValid != null) {
            return isValid;
        }

        try {
            userDao.add(user);
        } catch (Exception e) { // This branch should ideally never execute.
            return UserCreationResult.INTERNAL_ERROR;
        }

        return UserCreationResult.SUCCESS;
    }

    /**
     * Validate a user.
     *
     * @param user The user to validate.
     * @return A `UserCreationResult` that tells whether the user is valid.
     */
    private UserCreationResult validateUser(final User user) {
        if (user.getUsername().length() < 2) {
            return UserCreationResult.NAME_TOO_SHORT;
        } else if (userDao.getAll().stream()
                .filter(u -> u.getUsername().equals(user.getUsername()))
                .findAny()
                .isPresent()) {
            return UserCreationResult.NAME_TAKEN;
        }

        // Return null if the user is valid. This means that at this point
        // there have appeared no problems with the creation of the user, but
        // a problem may occur later in the process, so it is not considered
        // a success yet. Null signifies that there is no specific info on
        // whether the user creation is going to be successful.
        return null;
    }

}
