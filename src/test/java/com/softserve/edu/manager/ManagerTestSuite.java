package com.softserve.edu.manager;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({AchievementManagerTest.class,
        AchievementTypeManagerTest.class, CompetenceManagerTest.class,
        ClassManagerTest.class,
        AccessRoleManagerTest.class, UserManagerTest.class})
public class ManagerTestSuite {

}
