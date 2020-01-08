package nl.dpa.geos.event.ro;

import lombok.Getter;
import lombok.Setter;
import nl.dpa.geos.event.model.User;

@Getter
@Setter
public class UserRO implements DpaEventReturnObject{
	
	User user;
	String token;

	public UserRO(User user, String token) {
		super();
		this.user = user;
		this.token = token;
	}


}
