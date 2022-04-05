package snake.domain;

import snake.dao.UserDao;

public class SnakeService {
    private UserDao userDao;
    
    public SnakeService(UserDao userDao) {
        this.userDao = userDao;
    }
    
    public UserCreationResult createUser(String username) {
        User user = new User(username);
        UserCreationResult isValid = this.validateUser(user);
        if (isValid != null)
            return isValid;
        
        try {
            userDao.add(user);
        } catch (Exception e) { // This branch should ideally never execute.
            return UserCreationResult.INTERNAL_ERROR;
        }
        
        System.out.println("Here");
        return UserCreationResult.SUCCESS;
    }
    
    private UserCreationResult validateUser(User user) {
        if (user.getUsername().length() < 2)
            return UserCreationResult.NAME_TOO_SHORT;
        else if (userDao.getAll().stream()
                .filter(u -> u.getUsername().equals(user.getUsername()))
                .findAny()
                .isPresent())
            return UserCreationResult.NAME_TAKEN;
        
        // Return null if the user is valid. This means that at this point
        // there have appeared no problems with the creation of the user, but
        // a problem may occur later in the process, so it is not considered
        // a success yet. Null signifies that there is no specific info on
        // whether the user creation is going to be successful.
        return null;
    }
    
}
