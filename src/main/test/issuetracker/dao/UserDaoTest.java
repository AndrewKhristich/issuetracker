package issuetracker.dao;

import com.axmor.dao.UserDao;
import com.axmor.dao.impl.UserDaoImpl;
import com.axmor.model.User;
import com.axmor.utils.DataBaseInterface;
import issuetracker.DataBaseTestUtil;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserDaoTest {

    private final String queryPath = "src\\main\\resources\\public\\sql\\create_tables";
    DataBaseInterface dataBaseUtil;
    private UserDao userDao;

    @Before
    public void initDB(){
        dataBaseUtil = new DataBaseTestUtil();
        userDao = new UserDaoImpl(dataBaseUtil);
        dataBaseUtil.createAllTables(queryPath);
    }

    @Test
    public void findUserByNameTest(){
        String name = "First";
        User userByName = userDao.findUserByName(name);
        assertEquals(name, userByName.getName());
    }

    @Test
    public void saveUserTest(){
        String name = "asd";
        userDao.saveUser(name, "asdaaa");
        User testUser = userDao.findUserByName("asd");
        assertEquals(name, testUser.getName());
    }

}
