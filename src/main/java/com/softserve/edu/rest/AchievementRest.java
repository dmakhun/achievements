package com.softserve.edu.rest;

import com.softserve.edu.entity.Achievement;
import com.softserve.edu.manager.AchievementManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/achievement")
public class AchievementRest {
    private static final Logger LOGGER = Logger
            .getLogger(AchievementRest.class);

    @Autowired
    private AchievementManager achievementManager;

    /*
     * find all users from database. find by rest/user/allusers. listOfUsers is
     * wrapped by special JaxbList object. Method produces xml file.
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getachievementsbyuseruuid/{useruuid}")
    public Response getAchievementByUuid(@PathParam("useruuid") String userUuid) {

        List<Achievement> achievements;

        try {
            achievements = achievementManager
                    .findAchievementsByUserUuid(userUuid);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return Response.status(500).entity(e.getMessage()).build();
        }

        return Response.ok(achievements).build();
    }

}
