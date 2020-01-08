package nl.dpa.geos.event.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import nl.dpa.geos.event.dao.UserDao;
import nl.dpa.geos.event.model.User;


@SpringBootTest
@AutoConfigureMockMvc
class JwtUserDetailsServiceImplTest {
	
	@MockBean
	private UserDao userDao;
	
	private User testUser;
	
	@Autowired
	private JwtUserDetailsServiceImpl serviceImpl;
	
	@BeforeEach
	public void setup() {
		testUser = new User(1, "testUser", "12345", "testFirstName","testInsertion", "testLastName",1,"ADMIN","test@hotmail.com" );
    }

	@Test
	void test_loadUserByUsername_user_exists() {
		when(userDao.findByUserName(Mockito.anyString())).thenReturn(testUser);
		assertNotNull(serviceImpl.loadUserByUsername(testUser.getUserName()));	
	}
	
	@Test
	void test_loadUserByUsername_user_not_exists() {
		when(userDao.findByUserName(Mockito.anyString())).thenReturn(null);
		assertThrows(UsernameNotFoundException.class, () -> serviceImpl.loadUserByUsername(testUser.getUserName()));
	}

}
