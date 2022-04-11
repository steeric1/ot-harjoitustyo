package snake.dao;

import java.util.ArrayList;
import java.util.List;
import snake.domain.User;

public final class CsvUserDao implements UserDao {

    /**
     * A list that contains all the users managed by this DAO.
     */
    private List<User> users;

    /**
     * A user data access object that reads its data from a CSV file.
     */
    public CsvUserDao() {
        this.users = new ArrayList<User>();
    }

    @Override
    public void add(final User user) throws Exception {
        users.add(user);
    }

    @Override
    public List<User> getAll() {
        return users;
    }
}
