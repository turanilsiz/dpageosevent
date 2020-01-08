package nl.dpa.geos.event.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import nl.dpa.geos.event.ro.DpaEventReturnObject;
import nl.dpa.geos.event.ro.ExceptionRO;
import nl.dpa.geos.event.ro.UserRO;
import nl.dpa.geos.event.service.UserService;
import nl.dpa.geos.event.util.UserUtil;
import nl.dpa.geos.event.vo.UserVO;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping(value = "/registration")
	public ResponseEntity<DpaEventReturnObject> createUser(@RequestBody UserVO userVO) {
		if(userService.isUserExists(userVO.getUserName())) {
			return new ResponseEntity<>(new ExceptionRO("", "Deze gebruiker bestaat al.."), HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>(new UserRO(userService.createUser(UserUtil.convertEntityToVO(userVO)), null), HttpStatus.OK);		
	}

}
