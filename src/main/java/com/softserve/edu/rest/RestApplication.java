package com.softserve.edu.rest;

import org.glassfish.jersey.server.ResourceConfig;

public class RestApplication extends ResourceConfig {
    public RestApplication() {
        packages(AchievementRest.class.getPackage().toString());
        packages(AchievementTypeRest.class.getPackage().toString());
        packages(CompetenceRest.class.getPackage().toString());
        packages(GroupRest.class.getPackage().toString());
        packages(RoleRest.class.getPackage().toString());
        packages(UserRest.class.getPackage().toString());
    }
}
