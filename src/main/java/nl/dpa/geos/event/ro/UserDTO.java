package nl.dpa.geos.event.ro;

import lombok.Getter;
import lombok.Setter;
import nl.dpa.geos.event.model.User;

@Getter
@Setter
public class UserDTO {
	
	User user;
	String token;
	public UserDTO(User user, String token) {
		super();
		this.user = user;
		this.token = token;
	}

}
