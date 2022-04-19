package snake.domain;

import javafx.scene.paint.Color;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import snake.domain.SnakeService;

public class SnakeServiceTest {
    
    SnakeService service;
    FakeUserDao userDao;
    
    @Before
    public void setUp() {
        this.userDao = new FakeUserDao();
        this.service = new SnakeService(this.userDao);
    }
    
    @Test
    public void canCreateSingleUser() {
        assertEquals(UserOperationResult.SUCCESS, service.createUser("test"));
        assertEquals(1, this.userDao.getAll().size());
    }
    
    @Test
    public void cannotCreateUserWithTooShortName() {
        assertEquals(UserOperationResult.NAME_TOO_SHORT, service.createUser("t"));
    }
    
    @Test
    public void cannotCreateUserWithSameNameTwice() {
        service.createUser("test");
        
        assertEquals(UserOperationResult.NAME_TAKEN, service.createUser("test"));
    }
    
    @Test
    public void userCreationReturnsInternalErrorUponException() {
        assertEquals(UserOperationResult.INTERNAL_ERROR, service.createUser("error"));
    }
    
    @Test
    public void canLogInToCreatedUser() {
        service.createUser("user");
        
        assertTrue(service.login("user"));
    }
    
    @Test
    public void afterLoggingInLoggedInUserChanges() {
        service.createUser("user");
        service.login("user");
        
        assertEquals("user", service.getLoggedInUser().getUsername());
    }
    
    @Test
    public void canRenameUser() {
        service.createUser("test");
        service.renameUser("test", "newname");
        
        assertEquals("newname", service.getAllUsernames().get(0));
    }
    
    @Test
    public void canSetUserColor() {
        service.createUser("test");
        service.setUserColor("test", Color.RED);
        service.login("test");
        
        assertEquals(Color.RED, service.getLoggedInUser().getColor());
    }

}
