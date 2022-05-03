package snake.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import snake.dao.ScoreDao;
import snake.dao.UserDao;

public class SnakeService {

    private UserDao userDao;
    private ScoreDao scoreDao;
    private User loggedInUser;
    
    public SnakeService(UserDao userDao, ScoreDao scoreDao) {
        this.userDao = userDao;
        this.scoreDao = scoreDao;
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
        
        return this.createUser(new User(user.getId(), newUsername, user.getColor()));
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
        
        return this.createUser(new User(user.getId(), user.getUsername(), color));
    }
    
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
    
    public List<Integer> getUserScores(String username) {
        return this.scoreDao.getUserScores(this.userDao.getByName(username));
    }
    
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
    
    public List<Score> getAllScores() {
        return this.scoreDao.getAll();
    }
    
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


