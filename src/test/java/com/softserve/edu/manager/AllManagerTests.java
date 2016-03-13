package com.softserve.edu.manager;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({AchievementManagerImplTest.class,
        AchievementTypeManagerImplTest.class, CompetenceManagerImplTest.class,
        GroupManagerImplementationTest.class,
        RoleManagerImplementationTest.class, UserManagerImplTest.class})
public class AllManagerTests {

}
