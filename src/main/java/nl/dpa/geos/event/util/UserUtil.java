package nl.dpa.geos.event.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import nl.dpa.geos.event.model.User;
import nl.dpa.geos.event.vo.UserVO;

public class UserUtil {

	static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	   
	public static String getPasswordHash(String password) {
		return encoder.encode(password);
	}
	
	private UserUtil() {
		throw new IllegalStateException("Utility class");
	}
	
	public static User convertEntityToVO(UserVO userVO) {
	  User usr = new User();
	  usr.setUserName(userVO.getUserName());
	  usr.setPassword(userVO.getPassword());
	  usr.setFirstName(userVO.getFirstName());
	  usr.setInsertion(userVO.getInsertion());
	  usr.setLastName(userVO.getLastName());
	  usr.setActive(userVO.getActive());
	  usr.setEmail(userVO.getEmail());
      usr.setRole(userVO.getRole());
	  return usr;
		
	}
}
