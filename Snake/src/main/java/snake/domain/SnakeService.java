package snake.domain;

import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.paint.Color;
import snake.dao.UserDao;

public class SnakeService {

    private UserDao userDao;
    private User loggedInUser;
    
    public SnakeService(UserDao dao) {
        this.userDao = dao;
    }

    public UserOperationResult createUser(String username) {
        return this.createUser(new User(username));
    }
    
    public UserOperationResult createUser(User user) {
        UserOperationResult isValid = this.validateUser(user);
        if (isValid != null) {
            return isValid;
        }

        try {
            userDao.add(user);
        } catch (Exception e) { // This branch should ideally never execute.
            return UserOperationResult.INTERNAL_ERROR;
        }

        return UserOperationResult.SUCCESS;
    }
    
    public UserOperationResult renameUser(String username, String newUsername) {
        User user = this.userDao.getByName(username);
        if (user == null) {
            return UserOperationResult.USER_NOT_FOUND;
        }
            
        try {
            userDao.remove(user);
        } catch (Exception e) {
            return UserOperationResult.INTERNAL_ERROR;
        }
        
        return this.createUser(new User(user.getUUID(), newUsername, user.getColor()));
    }
    
    public UserOperationResult setUserColor(String username, Color color) {
        User user = this.userDao.getByName(username);
        if (user == null) {
            return UserOperationResult.USER_NOT_FOUND;
        }
        
        try {
            userDao.remove(user);
        } catch (Exception e) {
            return UserOperationResult.INTERNAL_ERROR;
        }
        
        return this.createUser(new User(user.getUUID(), user.getUsername(), color));
    }
    
    public boolean login(String username) {
        User u = this.userDao.getByName(username);
        if (u == null) {
            System.out.println("User by name \"" + username + "\" was not found.");
            return false;
        }
        
        System.out.println("Logged in as: " + username + " (" + u.getUUID() + ")");
        this.loggedInUser = u;
        return true;
    }
    
    public User getLoggedInUser() {
        return this.loggedInUser;
    }
    
    public boolean logoff() {
        if (this.loggedInUser != null) {
            this.loggedInUser = null;
            return true;
        }
        
        return false;
    }
    
    public List<String> getAllUsernames() {
        return this.userDao.getAll().stream()
                .map(u -> u.getUsername())
                .collect(Collectors.toList());
    }

    private UserOperationResult validateUser(User user) {
        if (user.getUsername().length() < 2) {
            return UserOperationResult.NAME_TOO_SHORT;
        } else if (userDao.getAll().stream()
                .filter(u -> u.getUsername().equals(user.getUsername()))
                .findAny()
                .isPresent()) {
            return UserOperationResult.NAME_TAKEN;
        }

        // Return null if the user is valid. This means that at this point
        // there have appeared no problems with the creation of the user, but
        // a problem may occur later in the process, so it is not considered
        // a success yet. Null signifies that there is no specific info on
        // whether the user creation is going to be successful.
        return null;
    }

}


