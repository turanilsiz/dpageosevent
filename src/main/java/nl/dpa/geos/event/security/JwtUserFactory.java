package nl.dpa.geos.event.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import nl.dpa.geos.event.model.User;


public class JwtUserFactory {
	
	public static JwtUser create(User user) {
		return new JwtUser(user.getId(), user.getUserName()
				         , user.getPassword()
				         , user
				         , maptoGrantedAuthorities(new ArrayList(Arrays.asList("ROLE_"+user.getRole())))
				         , user.getActive() == 1 ? true:false
				);
	}

	private static List<GrantedAuthority> maptoGrantedAuthorities(List<String> authorities){
		
		return authorities.stream().map(Authority -> new SimpleGrantedAuthority(Authority)).collect(Collectors.toList());
		
	}

}
