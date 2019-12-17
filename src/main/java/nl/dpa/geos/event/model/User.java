package nl.dpa.geos.event.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "DPA_EVENT_USER")
public class User {
	
	private final static int MAX_VALUE=50;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
	@SequenceGenerator(name="user_generator", sequenceName = "event_user_seq", initialValue = 1, allocationSize = 1)
	private Integer id;
	
	@NotNull
	@Column(name="USER_NAME")
	@Size(max = MAX_VALUE)
	private String userName;
    
	@NotNull
	private  String password;
    
	@NotNull
	@Size(max = MAX_VALUE)
	@Column(name="FIRST_NAME")
	private String firstName;
	
	private String insertion;
	
	@NotNull
	@Size(max = MAX_VALUE)
	@Column(name="LAST_NAME")
	private String lastName;
	
	@NotNull
	@Max(1)
	private Integer active;
	
	@NotNull
	@Size(min = 4, max = 5)
	private String role;
	
	@NotNull
	@Email
	@Column(name = "USER_EMAIL")
	private String email;
	
	
	public User() {
		
	}

	public User(Integer id, String userName, String password, String firstName,
			String insertion, String lastName, Integer active,
			String role, String email
			) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;		
		this.firstName = firstName;
		this.insertion = insertion;
		this.lastName = lastName;
		this.active = active;	
		this.role = role;	
		this.email = email;
	}
	

}
