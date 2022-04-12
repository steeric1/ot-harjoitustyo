package snake.domain;

import java.util.ArrayList;
import java.util.List;
import java.lang.IllegalArgumentException;
import snake.dao.UserDao;

public class FakeUserDao implements UserDao {
    private List<User> users;
    
    public FakeUserDao() {
        this.users = new ArrayList<User>();
    }
    
    @Override
    public void add(User user) throws Exception {
        if (user.getUsername().equals("error"))
            throw new IllegalArgumentException();
        
        users.add(user);
    }

    @Override
    public List<User> getAll() {
        return users;
    }    

    @Override
    public User getByName(String username) {
        for (User u : this.users) {
            if (u.getUsername().equals(username))
                return u;
        }
        
        return null;
    }
}
