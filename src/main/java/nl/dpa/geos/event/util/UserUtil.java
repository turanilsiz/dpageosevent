package nl.dpa.geos.event.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserUtil {

	static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	   
	public static String getPasswordHash(String password) {
		return encoder.encode(password);
	}
}
