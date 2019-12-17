package nl.dpa.geos.event.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import nl.dpa.geos.event.dao.UserDao;
import nl.dpa.geos.event.model.User;
import nl.dpa.geos.event.ro.UserDTO;
import nl.dpa.geos.event.security.JwtTokenUtil;
import nl.dpa.geos.event.security.JwtUser;
import nl.dpa.geos.event.util.UserUtil;
import nl.dpa.geos.event.vo.UserVO;

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
	
	private Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
	
	@PostMapping(value="/login")
	public ResponseEntity<UserDTO> login(@RequestBody UserVO userVO, HttpServletResponse response) {
		try {
			User user = UserUtil.convertEntityToVO(userVO);
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
			final JwtUser userDetails = (JwtUser)authentication.getPrincipal();
			SecurityContextHolder.getContext().setAuthentication(authentication);
			final String token = jwtTokenUtil.generateToken(userDetails);
			User usr = userDetails.getUser();
			userDao.save(usr);
			usr.setPassword(null);
			response.setHeader("Token", token);
			return new ResponseEntity<>(new UserDTO(usr, token), HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			
		}
		return null;
	}
   
	
	
}
