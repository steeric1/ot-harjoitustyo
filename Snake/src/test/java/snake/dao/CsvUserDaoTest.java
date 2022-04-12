package snake.dao;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import snake.domain.User;

public class CsvUserDaoTest {
    
    CsvUserDao dao;
    
    @Before
    public void setUp() {
        dao = new CsvUserDao("test-users.csv");
    }
    
    @Test
    public void afterAddingUserItAppearsOnList() {
        try {
            dao.add(new User("test"));
        } catch (Exception e) {} // We assume no errors
        
        assertTrue(dao.getAll().stream()
                .filter(u -> u.getUsername().equals("test"))
                .findAny()
                .isPresent());
    }
    
    
    @Test
    public void afterAddingUserItCanBeFound() {
        try {
            dao.add(new User("test"));
        } catch (Exception e) {} // We assume no errors
        
        assertNotNull(dao.getByName("test"));
    }
}
