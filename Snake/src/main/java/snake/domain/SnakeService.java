package snake.domain;

import java.util.List;
import java.util.stream.Collectors;
import snake.dao.UserDao;

public class SnakeService {

    private UserDao userDao;
    private User loggedInUser;
    
    public SnakeService(UserDao dao) {
        this.userDao = dao;
    }

    public UserCreationResult createUser(String username) {
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
    
    public boolean login(String username) {
        User u = this.userDao.getByName(username);
        if (u == null) {
            System.out.println("User by name \"" + username + "\" was not found.");
            return false;
        }
        
        System.out.println("Logged in as: " + username);
        this.loggedInUser = u;
        return true;
    }
    
    public User getLoggedInUser() {
        return this.loggedInUser;
    }
    
    public List<String> getAllUsernames() {
        return this.userDao.getAll().stream()
                .map(u -> u.getUsername())
                .collect(Collectors.toList());
    }

    private UserCreationResult validateUser(User user) {
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


