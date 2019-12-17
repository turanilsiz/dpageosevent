package nl.dpa.geos.event.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import nl.dpa.geos.event.model.User;
import nl.dpa.geos.event.ro.UserDTO;
import nl.dpa.geos.event.security.JwtUser;
import nl.dpa.geos.event.service.UserService;
import nl.dpa.geos.event.dao.UserDao;
import nl.dpa.geos.event.security.JwtTokenUtil;

@RestController
public class AuthenticationController {
	
	@Value("${jwt.header}")
	private String tokenHeader;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserDao userDao;
	
	
	@PostMapping(value="/login")
	public ResponseEntity<UserDTO> login(@RequestBody User user, HttpServletResponse response) throws Exception{
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
			final JwtUser userDetails = (JwtUser)authentication.getPrincipal();
			SecurityContextHolder.getContext().setAuthentication(authentication);
			final String token = jwtTokenUtil.generateToken(userDetails);
			User usr = userDetails.getUser();
			userDao.save(usr);
			usr.setPassword(null);
			response.setHeader("Token", token);
			return new ResponseEntity<UserDTO>(new UserDTO(usr, token), HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("Error => "+e.getMessage());
			
		}
		return null;
	}
   
	
	
}
