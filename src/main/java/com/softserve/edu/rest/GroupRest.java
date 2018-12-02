package com.softserve.edu.rest;

import com.softserve.edu.entity.Group;
import com.softserve.edu.entity.User;
import com.softserve.edu.exception.GroupManagerException;
import com.softserve.edu.manager.GroupManager;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Path("/group")
public class GroupRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupRest.class);

    @Autowired
    private GroupManager groupManager;

    /**
     * find all user by group uuid from database. find by rest/group/findusersbygroupuuid/{groupuuid}.
     * listOfUsers is wrapped by special JaxbList object. Method produces xml file.
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/findusersbygroupuuid/{groupuuid}")
    public Response findUsersByGroupUuid(
            @PathParam("groupuuid") final String groupUuid) {
        List<User> listUsersInGroup = null;
        try {
            listUsersInGroup = groupManager
                    .findUsersByGroupUuid(groupUuid);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return Response.status(500)
                    .entity(e.getMessage()).build();
        }

        JaxbList<User> jaxbList = new JaxbList<User>(listUsersInGroup);

        return Response.ok(jaxbList).build();
    }

    /**
     * Get not yet opened groups
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/findgroupsinfuture")
    public Response findGroupsInFuture() {
        List<Group> listGroupsInFuture = null;
        try {
            listGroupsInFuture = groupManager.inFuture();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return Response.status(500)
                    .entity(e.getMessage()).build();
        }
        JaxbList<Group> jaxbList = new JaxbList<>(listGroupsInFuture);
        return Response.ok(jaxbList).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/findgroupbyuuid/{uuid}")
    public Response findGroupByGroupUuid(@PathParam("uuid") String uuid) {
        Group group = null;

        try {
            group = groupManager.findGroupByGroupUuid(uuid);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return Response.status(500)
                    .entity(e.getMessage()).build();
        }

        if (group == null) {
            LOGGER.error("Group with such uuid does not exist");
            return Response.status(500)
                    .entity("Group with such uuid does not exist").build();
        }
        return Response.ok(group).status(200).build();

    }

    /**
     * createAchievementType group (save in database). createAchievementType by
     * rest/group/creategroup/{competenceuuid} competenceuuid - appropriate uuid of some group's
     * competence. Consumes application/xml file and save in database. return response status 201 if
     * group creation process was successful, otherwise return 500 response status.
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Path("/creategroup")
    public Response createGroup(Group group) {

        try {
            groupManager.create(group);
        } catch (GroupManagerException e) {
            LOGGER.error(e.getMessage());
            return Response.status(500).entity(e.getMessage()).build();
        }

        return Response.status(201).build();
    }

    /**
     * add user with appropriate uuid to group with appropriate uuid. add by
     * addusertogroup/{useruuid}/{groupuuid} useruuid - appropriate uuid of some user. groupuuid -
     * appropriate uuid of some group. return response status 200 if group creation process was
     * successful, otherwise return 500 response status.
     */
    @PUT
    @Path("/addusertogroup/{useruuid}/{groupuuid}")
    public Response addUserToGroup(@PathParam("useruuid") String userUuid,
            @PathParam("groupuuid") String groupUuid) {

        try {
            groupManager.addUser(userUuid, groupUuid);
        } catch (GroupManagerException e) {
            LOGGER.error(e.getMessage());
            return Response.status(500).entity(e.getMessage()).build();
        }

        return Response.status(200).entity("User was added to group").build();
    }

    /**
     * deleteAchievementType user by user's uuid. find by rest/user/deleteuserbyuseruuid/{useruuid}.If
     * some trouble occurred, return response status 500 with appropriate message. If groups was
     * found and removed from database - return response status 200 with appropriate message.
     */
    @DELETE
    @Path("/deletegroupbygroupuuid/{groupuuid}")
    public Response deleteUserByUserUuid(
            @PathParam("groupuuid") String groupUuid) {

        try {
            groupManager.deleteByUuid(groupUuid);
            return Response.status(200).entity("Group was removed").build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return Response.status(500).entity(e.getMessage()).build();
        }

    }

}
