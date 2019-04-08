package com.enterprise.usermgmt;

import com.enterprise.usermgmt.users.model.Status;
import com.enterprise.usermgmt.users.model.User;
import com.enterprise.usermgmt.services.users.UsersServer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UsersServer.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersServerTests {

	private static final String LOCALHOST = "http://localhost:";
	private static final String BASE_PATH = "/enterprise/users";
	private static final String USER_CREATION_PATH = "/addUser";
	private static final String USER_GET_ALL_PATH = "/listUsers";
	private static final String USER_GET_PATH = "/getUser/0";
	private static final String USER_UPDATE_PATH = "/updateUser/0";
	private static final String USER_DELETE_PATH = "/deleteUser/0";

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getLocalUrl() {
		return LOCALHOST + this.port + BASE_PATH;
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testGetAllUsers() {
		ResponseEntity<List<User>> response = this.restTemplate.exchange(
				this.getLocalUrl() + USER_GET_ALL_PATH,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<User>>() {});

		Assert.assertTrue(response.getBody().size() > 0);
		Assert.assertFalse(response.getBody().stream().
				anyMatch(user -> user.getStatus().equalsIgnoreCase(Status.INACTIVE.getStatus())));
	}

	@Test
	public void testGetUserById() {
		User user = this.getUser(USER_GET_PATH);

		Assert.assertNotNull(user);
	}

	@Test
	public void testUpdatePost() {
		String updatedFirstName = "Name";
		String updatedLastName = "Last";
		User user = this.getUser(USER_GET_PATH);
		user.setFirstName(updatedFirstName);
		user.setLastName(updatedLastName);
		this.restTemplate.put(this.getLocalUrl() + USER_UPDATE_PATH, user);
		User updatedUser = this.getUser(USER_GET_PATH);

		Assert.assertNotNull(updatedUser);
		Assert.assertEquals(updatedUser.getFirstName(), updatedFirstName);
		Assert.assertEquals(updatedUser.getLastName(), updatedLastName);
	}

	@Test
	public void testCreateUser() {
		User user = new User();
		user.setFirstName("Saul");
		user.setLastName("Hernandez");
		user.setDateOfBirth("1964-11-20");

		ResponseEntity<User> postResponse = this.restTemplate.postForEntity(this.getLocalUrl() + USER_CREATION_PATH, user, User.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
		Assert.assertEquals(postResponse.getBody().getFirstName(), "Saul");
		Assert.assertEquals(postResponse.getBody().getLastName(), "Hernandez");
		Assert.assertEquals(postResponse.getBody().getDateOfBirth(), "1964-11-20");
	}

	@Test
	public void testDeletePost() {
		User user = this.getUser(USER_GET_PATH);
		Assert.assertNotNull(user);
		this.restTemplate.delete(this.getLocalUrl() + USER_DELETE_PATH);
		user = this.getUser(USER_GET_PATH);

		Assert.assertTrue(user.getStatus().equalsIgnoreCase(Status.INACTIVE.getStatus()));

	}

	private User getUser(String basePath) {
		return this.restTemplate.getForObject(this.getLocalUrl() + basePath, User.class);
	}
}