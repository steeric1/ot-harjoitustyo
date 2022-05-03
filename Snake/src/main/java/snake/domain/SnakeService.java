package snake.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import snake.dao.ScoreDao;
import snake.dao.UserDao;

/**
 * This class is responsible for the main application logic. It handles actions
 * such as user creation, user information retrieval, adding new scores.
 */
public class SnakeService {

    private UserDao userDao;
    private ScoreDao scoreDao;
    private User loggedInUser;
    
    public SnakeService(UserDao userDao, ScoreDao scoreDao) {
        this.userDao = userDao;
        this.scoreDao = scoreDao;
    }

    /**
     * Create a new user with a username.
     * 
     * @param username The username for the new user.
     * @return An object that contains the outcome of the operation.
     */
    public UserOperationResult createUser(String username) {
        return this.createUser(new User(username));
    }
    
    /**
     * Create a new user with a `User` object. This object will then be registered
     * and serialised on the disk.
     * 
     * @param user The user to be registered and serialised.
     * @return An object that contains the outcome of the operation.
     */
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
    
    /**
     * Rename a user.
     * 
     * @param username The old username.
     * @param newUsername The new username.
     * 
     * @return An object that contains the outcome of the operation.
     */
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
        
        return this.createUser(new User(user.getId(), newUsername, user.getColor()));
    }
    
    /**
     * Set a user's snake colour.
     * 
     * @param username The name of the user.
     * @param color The new colour.
     * 
     * @return An object that contains the outcome of the operation.
     */
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
        
        return this.createUser(new User(user.getId(), user.getUsername(), color));
    }
    
    /**
     * Log in a user.
     * 
     * @param username The name of the user to log in.
     * @return <code>true</code>, if the user was found and logged in successfully,
     * <code>false</code> otherwise.
     */
    public boolean login(String username) {
        User u = this.userDao.getByName(username);
        if (u == null) {
            System.out.println("User by name \"" + username + "\" was not found.");
            return false;
        }
        
        System.out.println("Logged in as: " + username + " (" + u.getId() + ")");
        this.loggedInUser = u;
        return true;
    }
    
    public User getLoggedInUser() {
        return this.loggedInUser;
    }
    
    /**
     * Log off from the currently logged in user.
     * 
     * @return <code>true</code> if a user was logged in, <code>false</code> otherwise.
     */
    public boolean logoff() {
        if (this.loggedInUser != null) {
            this.loggedInUser = null;
            return true;
        }
        
        return false;
    }
    
    /**
     * @return A list of all the registered users' names.
     */
    public List<String> getAllUsernames() {
        return this.userDao.getAll().stream()
                .map(u -> u.getUsername())
                .collect(Collectors.toList());
    }
    
    /**
     * @param username The name of the user whose scores are to be retrieved.
     * @return A list of the user's scores.
     */
    public List<Integer> getUserScores(String username) {
        return this.scoreDao.getUserScores(this.userDao.getByName(username));
    }
    
    /**
     * @param n The number of top scores to be retrieved.
     * @return A list of pairs with each pair representing a score: the key
     * is the user who owns the score, and the value is the score.
     */
    public List<Pair<User, Integer>> getTopScores(int n) {
        List<Score> scores = this.scoreDao.getAll();
        List<User> users = this.userDao.getAll();
        List<Pair<User, Integer>> result = new ArrayList<>();
        
        int i = 0;
        ListIterator<Score> it = scores.listIterator();
        while (it.hasNext() && i++ < n) {
            Score next = it.next();
            User user = users.stream()
                    .filter(u -> u.getId().equals(next.getOwnerId()))
                    .findAny()
                    .orElse(null);
            
            result.add(new Pair<>(user, next.getValue()));
        }
        
        return result;
    }
    
    /**
     * @return All scores.
     */
    public List<Score> getAllScores() {
        return this.scoreDao.getAll();
    }
    
    /**
     * Add a score.
     * 
     * @param username The name of the owner of the score.
     * @param value The score.
     * @return <code>true</code> if the score was added successfully, <code>false</code> otherwise.
     */
    public boolean addScore(String username, int value) {
        try {
            this.scoreDao.add(new Score(value, this.userDao.getByName(username).getId()));
            return true;
        } catch (Exception e) {
            return false;
        }
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


