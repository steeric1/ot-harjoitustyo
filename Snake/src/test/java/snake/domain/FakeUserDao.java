package snake.domain;

import java.util.ArrayList;
import java.util.List;
import snake.dao.UserDao;

public class FakeUserDao implements UserDao {
    private List<User> users;
    
    public FakeUserDao() {
        this.users = new ArrayList<User>();
    }
    
    @Override
    public void add(User user) throws Exception {
        users.add(user);
    }

    @Override
    public List<User> getAll() {
        return users;
    }    
}
