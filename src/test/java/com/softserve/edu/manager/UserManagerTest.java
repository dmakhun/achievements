package com.softserve.edu.manager;

import com.softserve.edu.dao.RoleDao;
import com.softserve.edu.dao.UserDao;
import com.softserve.edu.dao.impl.UserDaoImpl;
import com.softserve.edu.entity.User;
import com.softserve.edu.exception.UserManagerException;
import com.softserve.edu.manager.impl.UserManagerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserManagerTest {

    private User testUser = null;
    @Mock
    private UserDao userDao;
    @Mock
    private RoleDao roleDao;
    @InjectMocks
    private UserManager userManager =
            new UserManagerImpl();

    @BeforeEach
    public void setUp() {
        userDao = new UserDaoImpl();
    }

    @Test
    public void createTestNullStrings() throws Exception {
        Assertions.assertThrows(UserManagerException.class, () -> userManager.createUser(null));
    }
/*
    @Test(expected = UserManagerException.class)
    public void createTestMaxLength() throws Exception {
        userManager
                .createUser("slfkjdslkfjdslkfjsdlkfjsldkjflsdkjflsdkjflsdkjfldskjflsdkjfsldkjflsdk"
                );
    }

    @Test(expected = UserManagerException.class)
    public void createTestEmptyStrings() throws Exception {
        userManager.createUser("");
    }

    @Test(expected = UserManagerException.class)
    public void createTestUsernameMinLength() throws Exception {
        userManager.createUser("Name");
    }

    @Test(expected = UserManagerException.class)
    public void createTestUsername() throws Exception {
        userManager.createUser("Name"
        );
    }

    @Test(expected = UserManagerException.class)
    public void createTestSomeUserName() throws Exception {
        userManager.createUser("Name"
        );
    }

    @Test(expected = UserManagerException.class)
    public void createTestEmail() throws Exception {
        userManager.createUser("Name"
        );
    }

    @Test(expected = UserManagerException.class)
    public void createTestRoleID() throws Exception {
        userManager.createUser("Name"
        );
    }

    @Test(expected = UserManagerException.class)
    public void testModifyValid() throws UserManagerException {
        testUser = userManager.createUser("Name"
        );

        userManager.updateUser(testUser.getId(), "", "", "", "", "", null);
    }

    @Test(expected = UserManagerException.class)
    public void testModifyNameLength() throws UserManagerException {
        testUser = userManager.createUser("Name"
        );
        userManager
                .updateUser(testUser.getId(),
                        "sldfjsdlkfjsdl;fkjsdfdsfsfsdsda;lfkjsd;flkjsd;lfkjds;lfkjsda;lfkjsd;lfkj",
                        "", "", "", "", null);
    }

    @Test(expected = UserManagerException.class)
    public void testModifyNothingChanged() throws UserManagerException {
        testUser = userManager.createUser("Name"
        );
        userManager.updateUser(testUser.getId(), "name", "surname",
                "TestoniniOne", "", "", null);
    }

    @Test(expected = UserManagerException.class)
    public void testModifyDuplicateUsername() throws UserManagerException {
        testUser = userManager.createUser("Name"
        );
        userManager.updateUser(testUser.getId(), "name", "surname",
                "TestoniniTwo", "", "", null);
    }

    @Test(expected = UserManagerException.class)
    public void testModifyDuplicateEmail() throws UserManagerException {
        testUser = userManager.createUser("Name"
        );
        userManager.updateUser(testUser.getId(), "name", "surname", "", "",
                "e2@mail.com", null);
    }*/
}
