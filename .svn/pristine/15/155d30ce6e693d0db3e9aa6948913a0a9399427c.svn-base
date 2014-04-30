package com.softserve.edu.rest;

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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.softserve.edu.entity.Group;
import com.softserve.edu.entity.User;
import com.softserve.edu.exception.UserManagerException;
import com.softserve.edu.manager.UserManager;

@Path("/user")
public class UserRest {
	private static final Logger LOGGER = Logger.getLogger(UserRest.class);

	@Autowired
	private UserManager userManager;

	/**
	 * find all users from database. find by rest/user/allusers. listOfUsers is
	 * wrapped by special JaxbList object. Method produces xml file. Return list
	 * of all users and response status 200.
	 */
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/allusers")
	public Response findListOfAllUsers() {

		try {
			List<User> users = userManager.findAllUsers();
			JaxbList<User> listOfUsers = new JaxbList<User>(users);
			return Response.ok(listOfUsers).status(200).build();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return Response.status(500).entity(e.getMessage()).build();
		}

	}

	/**
	 * find user by user uuid. find by rest/user/finduserbyuuid/{uuid}. Method
	 * produces xml file. If user with appropriate uuid does not exist in
	 * database or some trouble occurred, return response status 500 with
	 * appropriate message. If user was found - return user with response status
	 * 200.
	 */
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/finduserbyuuid/{uuid}")
	public Response findUserByUserUuid(@PathParam("uuid") String uuid) {
		User user = null;

		try {
			user = userManager.findByUuid(uuid);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return Response.status(500).entity(e.getMessage()).build();
		}

		if (user == null) {
			return Response.status(500)
					.entity("User with such uuid does not exist").build();
		}

		return Response.ok(user).status(200).build();
	}

	/**
	 * find user by user email. find by rest/user/finduserbyemail/{email}.
	 * Method produces xml file. If user with appropriate email does not exist
	 * in database or some trouble occurred, return response status 500 with
	 * appropriate message. If user was found - return user with response status
	 * 200.
	 */
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/finduserbyemail/{email}")
	public Response findUserByemail(@PathParam("email") String email) {
		User user = null;
		try {
			user = userManager.findByEmail(email);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return Response.status(500).entity(e.getMessage()).build();
		}

		if (user == null) {
			return Response.status(500)
					.entity("User with such email does not exist").build();
		}

		return Response.ok(user).status(200).build();

	}

	/**
	 * find all user's groups. find by rest/user/findgroups/{username}. Method
	 * produces xml file. If user with appropriate username does not take part
	 * in database or some trouble occurred, return response status 500 with
	 * appropriate message. If groups was found - return response status 200.
	 */
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/findgroups/{username}")
	public Response findGroupsByUserName(@PathParam("username") String username) {
		User user = null;
		List<Group> groups = null;
		
		try {
			user = userManager.findByUsername(username);
			if (user == null) {
				return Response.status(500)
						.entity("User with such name does not exist").build();
			}
			groups = userManager.findGroups(user.getId(), false);
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return Response.status(500)
					.entity(e.getMessage()).build();
		}
		
		JaxbList<Group> groupList = new JaxbList<Group>(groups);
		return Response.ok(groupList).status(200).build();
	}

	/**
	 * find user by user name. find by rest/user/finduserbyusername/{username}.
	 * Method produces xml file. If user with appropriate email does not exist
	 * in database or some trouble occurred, return response status 500 with
	 * appropriate message. If user was found - return user with response status
	 * 200.
	 */
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/finduserbyusername/{username}")
	public Response findUsersByUsername(@PathParam("username") String username) {
		User user = null;
		
		try{
			user = userManager.findByUsername(username);			
		}catch(Exception e){
			LOGGER.error(e.getMessage());
			return Response.status(500)
					.entity(e.getMessage()).build();
		}		

		if (user == null) {
			return Response.status(500)
					.entity("User with such username does not exist").build();
		}
		
		return Response.ok(user).status(200).build();

	}

	/**
	 * delete user by user's uuid. find by
	 * rest/user/deleteuserbyuseruuid/{useruuid}.If some trouble occurred,
	 * return response status 500 with appropriate message. If groups was found
	 * and removed from database - return response status 200 with appropriate
	 * message.
	 */
	@DELETE
	@Path("/deleteuserbyuseruuid/{useruuid}")
	public Response deleteUserByUserUuid(@PathParam("useruuid") String userUuid) {

		try {
			userManager.deleteByUuid(userUuid);
			return Response.status(200).entity("User was removed").build();
		} catch (UserManagerException e) {
			LOGGER.error(e.getMessage());
			return Response.status(500).entity(e.getMessage()).build();
		}

	}

	/**
	 * create user (save in database). Consumes application/xml file, validate
	 * fields and save in database. return response status 201 if user creation
	 * process was successful, otherwise return 500 response status.
	 */
	@POST
	@Path("/createuser")
	@Consumes(MediaType.APPLICATION_XML)
	public Response createUser(User user) {
		try {
			userManager.create(user);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return Response.status(500).entity(e.getMessage()).build();
		}
		return Response.status(201).entity("User was created!").build();
	}

	/**
	 * update user with appropriate uuid. Consumes application/xml file, find
	 * appropriate user by user uuid, validate fields from consumed xml file and
	 * update user in database if validation process was successful. return
	 * response status 200 if update process was successful, otherwise return
	 * 500 response status.
	 */
	@PUT
	@Path("/updateuser/{uuid}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response updateUser(@PathParam("uuid") String UserUuid, User user) {
		try {
			userManager.update(UserUuid, user.getName(), user.getSurname(),
					user.getUsername(), user.getPassword(), user.getEmail(),
					user.getUuid());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return Response.status(500).entity("User was not updated.").build();
		}
		return Response.status(200).entity("User was updated").build();
	}

	/**
	 * attend competence with appropriate competence uuid to user with
	 * appropriate uuid. find user and competence by uuid. return response
	 * status 200 if attending process was successful.
	 */
	@PUT
	@Path("/attendcompetence/{useruuid}/{competenceuuid}")
	public Response attendCompetence(@PathParam("useruuid") String userUuid,
			@PathParam("competenceuuid") String competenceUuid) {
		try {
			userManager.attendCompetence(userUuid, competenceUuid);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return Response.status(500).entity(e.getMessage()).build();
		}
		return Response.status(200).build();
	}

	/**
	 * attend competence with appropriate competence uuid to user with
	 * appropriate uuid. find user and competence by uuid. return response
	 * status 200 if attending process was successful.
	 */
	@PUT
	@Path("/removecompetence/{useruuid}/{competenceuuid}")
	public Response removeCompetence(@PathParam("useruuid") String userUuid,
			@PathParam("competenceuuid") String competenceUuid) {
		try {
			userManager.removeUserToCompetence(userUuid, competenceUuid);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return Response.status(500).entity(e.getMessage()).build();
		}
		return Response.status(200).build();
	}
}
