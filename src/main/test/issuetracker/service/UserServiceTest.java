package issuetracker.service;

import com.axmor.dao.UserDao;
import com.axmor.dao.impl.UserDaoImpl;
import com.axmor.exception.UserAlreadyExistException;
import com.axmor.model.User;
import com.axmor.service.UserService;
import com.axmor.service.impl.UserServiceImpl;
import com.axmor.utils.DataSource;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;


public class UserServiceTest {

    private final String queryPath = "src\\main\\resources\\public\\sql\\create_tables";
    private UserDao userDao;
    private UserService service;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void initDB(){
        userDao = new UserDaoImpl();
        DataSource.createAllTables(queryPath);
        service = new UserServiceImpl(userDao);
    }

    @Test
    public void findUserTest(){
        User first = service.findUser("First");
        assertEquals("First", first.getName());
    }

    @Test
    public void saveUserTest(){
        thrown.expect(UserAlreadyExistException.class);
        service.saveUser("First", "aaa");
        service.saveUser("First1", "aaa");
        User first1 = service.findUser("First1");
        assertEquals("First1", first1.getName());
    }

}
