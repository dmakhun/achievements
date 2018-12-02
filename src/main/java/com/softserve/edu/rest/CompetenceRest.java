package com.softserve.edu.rest;

import com.softserve.edu.entity.AchievementType;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.Group;
import com.softserve.edu.exception.CompetenceManagerException;
import com.softserve.edu.manager.CompetenceManager;
import com.softserve.edu.manager.GroupManager;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Path("/competence")
public class CompetenceRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompetenceRest.class);

    @Autowired
    CompetenceManager competenceManager;
    @Autowired
    GroupManager groupManager;

    /**
     * Finds all OPENED groups for specific competence http://localhost:8080/Achievements
     * /rest/competence/findGroupsOpenedByCompetence/UUID
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/findGroupsOpenedByCompetence/{competenceUuid}")
    public Response findOpenedGroupsByCompetenceUuid(
            @PathParam("competenceUuid") final String competenceUuid) {

        List<Group> listOpenedGroupsByCompetence;

        try {
            listOpenedGroupsByCompetence = groupManager.findByCompetenceUuid(
                    competenceUuid, true);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return Response.status(500).entity(e.getMessage()).build();
        }

        JaxbList<Group> jaxbList = new JaxbList<Group>(
                listOpenedGroupsByCompetence);

        return Response.ok(jaxbList).build();
    }

    /**
     * This method returns marshaled list of groups of some specific competence
     * http://localhost:8080/Achievements/rest/competence/ findGroupsByCompetenceUuid/SOME_COMPETENCE_UUID
     *
     * @param id some specific id of the competence
     * @return xml representation of the list of groups
     */
    @Path("findGroupsByCompetenceUuid/{competenceUuid}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response findAllGroupsByCompetenceUuid(
            @PathParam("competenceUuid") final String competenceUuid) {

        List<Group> groups;

        try {
            groups = competenceManager
                    .findGroupsByCompetenceUuid(competenceUuid);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return Response.status(500).entity(e.getMessage()).build();
        }

        return Response.ok(new JaxbList<Group>(groups)).build();
    }

    /**
     * This method returns all competences of the project http://localhost:8080/Achievements/rest/competence/findAllCompetences
     *
     * @return xml representation of all existing competences
     */
    @Path("findAllCompetences")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response findAllCompetences() {

        List<Competence> competences;

        try {
            competences = competenceManager.findAllCompetences();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return Response.status(500).entity(e.getMessage()).build();
        }

        return Response.ok(new JaxbList<Competence>(competences)).build();
    }

    /**
     * This method returns all achievement types of some specific competence
     * http://localhost:8080/Achievements/rest/competence/ findAchievementTypesByComptenceUuid/SOME_COMPETENCE_UUID
     *
     * @param id specific competence id
     * @return xml representation of all achievement types of some competence
     */
    @Path("findAchievementTypesByComptenceUuid/{competenceuuid}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response findAllAchievementTypeByCompetenceUuid(
            @PathParam("competenceuuid") final String competenceUuid) {

        List<AchievementType> achievementTypes;

        try {
            achievementTypes = competenceManager
                    .findAchievementTypesByCompetenceUuid(competenceUuid);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return Response.status(500).entity(e.getMessage()).build();
        }

        return Response.ok(new JaxbList<AchievementType>(achievementTypes))
                .build();
    }

    /**
     * This method returns all competences of some specific user http://localhost
     * :8080/Achievements/rest/competence/findCompetencesByUserUuid /SOME_USER_UUID
     *
     * @param userId specific user id
     * @return xml representation of the competences of some user
     */
    @Path("/findCompetencesByUserUuid/{userUuid}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response findCompetencesByUserId(
            @PathParam("userUuid") final String userUuid) {

        List<Competence> competences;

        try {
            competences = competenceManager.findByUserUuid(userUuid);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return Response.status(500).entity(e.getMessage()).build();
        }

        return Response.ok(new JaxbList<Competence>(competences)).build();
    }

    /**
     * Creates new competence with the name specified in URL
     */
    @Path("createCompetence/{name}")
    @POST
    public Response createCompetence(@PathParam("name") final String name) {

        try {
            if (!competenceManager.validateCompetenceName(name)) {
                return Response.status(500)
                        .entity("Competence with such name already exists")
                        .build();
            } else if (name.length() < 3 || name.length() > 30) {
                return Response.status(500)
                        .entity("Name must be between 3 and 30 symbols")
                        .build();
            }

            competenceManager.create(name);

        } catch (CompetenceManagerException e) {
            LOGGER.error(e.getMessage());
            return Response.status(500).entity(e.getMessage()).build();
        }

        return Response.status(201).build();
    }

    /**
     * Deletes competence by its uuid
     */
    @DELETE
    @Path("/remove/{uuid}")
    public Response deleteCompetencesByUuid(@PathParam("uuid") final String uuid) {

        try {
            competenceManager.deleteByUuid(uuid);
        } catch (CompetenceManagerException e) {
            LOGGER.error(e.getMessage());
            return Response.status(500).entity(e.getMessage()).build();
        }

        return Response.status(200).entity("Competence was removed").build();
    }
}
