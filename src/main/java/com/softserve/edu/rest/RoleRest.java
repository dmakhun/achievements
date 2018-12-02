package com.softserve.edu.rest;

import com.softserve.edu.entity.Role;
import com.softserve.edu.entity.User;
import com.softserve.edu.manager.RoleManager;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


@Path("/role")
public class RoleRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleRest.class);

    @Autowired
    RoleManager roleManager;

    /*
     * find users by their roleid. find by rest/role/getusers/{roleid}. users
     * List is wrapped by special JaxbList object.
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getusersbyroleuuid/{roleuuid}")
    public Response getUsersByRoleId(@PathParam("roleuuid") String roleUuid) {
        List<User> users;

        try {
            users = roleManager.findUsersByRoleUuid(roleUuid);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return Response.status(500).entity(e.getMessage()).build();
        }

        JaxbList<User> jaxbList = new JaxbList<User>();
        jaxbList.setList(users);

        return Response.ok(jaxbList).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getAll")
    public Response getAll() {

        List<Role> roles;

        try {
            roles = roleManager.findAll();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return Response.status(500).entity(e.getMessage()).build();
        }

        JaxbList<Role> jaxbList = new JaxbList<Role>();
        jaxbList.setList(roles);

        return Response.ok(jaxbList).build();
    }

    /*
     * find role by rolename. find by rest/role/getrolebyrolename/{rolename}.
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getrolebyrolename/{rolename}")
    public Response getRoleByRolename(@PathParam("rolename") String roleName) {

        Role role;

        try {
            role = roleManager.findRoleByRolename(roleName);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return Response.status(500).entity(e.getMessage()).build();
        }

        return Response.ok(role).build();
    }

    /*
     * find role by roleuuid. find by rest/role/getrolebyuuid/{roleuuid}.
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/getrolebyuuid/{roleuuid}")
    public Response getRoleByRoleUuid(@PathParam("roleuuid") String roleUuid) {

        Role role;

        try {
            role = roleManager.findRoleByUuid(roleUuid);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return Response.status(500).entity(e.getMessage()).build();
        }

        return Response.ok(role).build();
    }

}