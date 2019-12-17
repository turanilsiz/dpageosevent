package nl.dpa.geos.event.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import nl.dpa.geos.event.model.User;
import nl.dpa.geos.event.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping(value = "/registration")
	public User createUser(@RequestBody User user) {
		User createdUser = userService.createUser(user);
		createdUser.setPassword(null);
		return createdUser;
	}

}
