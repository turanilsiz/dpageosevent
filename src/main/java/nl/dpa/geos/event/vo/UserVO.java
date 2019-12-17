package nl.dpa.geos.event.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVO {
	
	private String  userName;
	private String password;
	private String  firstName;
	private String  insertion;
	private String  lastName;
	private Integer active;
	private String  role;
	private String  email;

}
