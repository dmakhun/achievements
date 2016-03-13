package com.softserve.edu.manager;

import com.softserve.edu.dao.RoleDao;
import com.softserve.edu.dao.UserDao;
import com.softserve.edu.dao.UserDaoImplementation;
import com.softserve.edu.entity.User;
import com.softserve.edu.exception.InvalidValueException;
import com.softserve.edu.exception.UserManagerException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author dmakhtc
 */
@RunWith(MockitoJUnitRunner.class)
public class UserManagerImplTest {

    User testUser = null;
    @Mock
    private UserDao userDao;
    @Mock
    private RoleDao roleDao;
    ;
    @InjectMocks
    private UserManager userManager =
            new UserManagerImplementation();

    /**
     * Lots of versions that method of creating user can take.
     *
     * @throws UserManagerException
     */

    @Before
    public void setUp() throws Exception {
        userDao = new UserDaoImplementation();
    }

    @Test(expected = UserManagerException.class)
    public void createTestNullStrings() throws Exception {
        userManager.create(null, null, null, null, null, (Long) null);
    }

    @Test(expected = UserManagerException.class)
    public void createTestMaxLength50() throws Exception {
        userManager
                .create("slfkjdslkfjdslkfjsdlkfjsldkjflsdkjflsdkjflsdkjfldskjflsdkjfsldkjflsdk",
                        "", "", "", "", (Long) null);
    }

    @Test(expected = UserManagerException.class)
    public void createTestEmptyStrings() throws Exception {
        userManager.create("", "", "", "", "", (Long) null);
    }

    @Test(expected = UserManagerException.class)
    public void createTestUsernameMinLength() throws Exception {
        userManager.create("Name", "Surname", "u", "password", "email", (Long) null);
    }

    @Test(expected = UserManagerException.class)
    public void createTestUsername() throws Exception {
        userManager.create("Name", "Surname", "u$$ern3m#", "password", "email",
                (Long) null);
    }

    @Test(expected = UserManagerException.class)
    public void createTestSomeUserName() throws Exception {
        userManager.create("Name", "Surname", "someUserName", "password",
                "email", (Long) null);
    }

    @Test(expected = UserManagerException.class)
    public void createTestEmail() throws Exception {
        userManager.create("Name", "Surname", "someUserName", "password",
                "email@email", (Long) null);
    }

    @Test(expected = UserManagerException.class)
    public void createTestRoleID() throws Exception {
        userManager.create("Name", "Surname", "someUserName", "password",
                "email@email.email", 2l);
    }

    /**
     * Lots of versions that method of user modifying can take.
     *
     * @throws UserManagerException
     */

    @Test(expected = UserManagerException.class)
    public void testModifyValid() throws UserManagerException, InvalidValueException {
        testUser = userManager.create("Name", "Surname", "testUserOne",
                "pass", "e@mail.com", (Long) null);

        userManager.update(testUser.getId(), "", "", "", "", "", null);
    }

    @Test(expected = UserManagerException.class)
    public void testModifyNameLength() throws UserManagerException, InvalidValueException {
        testUser = userManager.create("Name", "Surname", "testUserOne",
                "pass", "e@mail.com", (Long) null);
        userManager
                .update(testUser.getId(),
                        "sldfjsdlkfjsdl;fkjsdfdsfsfsdsda;lfkjsd;flkjsd;lfkjds;lfkjsda;lfkjsd;lfkj",
                        "", "", "", "", null);
    }

    @Test(expected = UserManagerException.class)
    public void testModifyNothingChanged() throws InvalidValueException, UserManagerException {
        testUser = userManager.create("Name", "Surname", "testUserOne",
                "pass", "e@mail.com", (Long) null);
        userManager.update(testUser.getId(), "name", "surname",
                "TestoniniOne", "", "", null);
    }

    @Test(expected = UserManagerException.class)
    public void testModifyDuplicateUsername() throws InvalidValueException, UserManagerException {
        testUser = userManager.create("Name", "Surname", "testUserOne",
                "pass", "e@mail.com", (Long) null);
        userManager.update(testUser.getId(), "name", "surname",
                "TestoniniTwo", "", "", null);
    }

    @Test(expected = UserManagerException.class)
    public void testModifyDuplicateEmail() throws InvalidValueException, UserManagerException {
        testUser = userManager.create("Name", "Surname", "testUserOne",
                "pass", "e@mail.com", (Long) null);
        userManager.update(testUser.getId(), "name", "surname", "", "",
                "e2@mail.com", null);
    }
}
