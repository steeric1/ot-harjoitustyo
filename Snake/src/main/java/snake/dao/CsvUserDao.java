package snake.dao;

import java.util.ArrayList;
import java.util.List;
import snake.domain.User;

public class CsvUserDao implements UserDao {

    // TODO: Read from a CSV file.
    
    private List<User> users;
    
    public CsvUserDao() {
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
