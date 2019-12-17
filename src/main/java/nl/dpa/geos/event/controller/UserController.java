package nl.dpa.geos.event.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import nl.dpa.geos.event.model.User;
import nl.dpa.geos.event.service.UserService;
import nl.dpa.geos.event.util.UserUtil;
import nl.dpa.geos.event.vo.UserVO;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping(value = "/registration")
	public User createUser(@RequestBody UserVO userVO) {
		return userService.createUser(UserUtil.convertEntityToVO(userVO));		
	}

}
