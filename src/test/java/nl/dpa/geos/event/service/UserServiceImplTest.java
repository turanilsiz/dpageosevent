package nl.dpa.geos.event.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import nl.dpa.geos.event.dao.UserDao;
import nl.dpa.geos.event.model.User;

@SpringBootTest
@AutoConfigureMockMvc
class UserServiceImplTest {
	
	@Autowired
	private UserService userService;
	
	@MockBean
	private UserDao userDao;
	
	private User testUser;
	
	@BeforeEach
	public void setup() {
		 testUser = new User(1, "testUser", "12345", "testFirstName","testInsertion", "testLastName",1,"ADMIN","test@hotmail.com" );
    }

	@Test
	void test_createUser_returned_user_password_has_to_be_null() {	
		when(userDao.save(Mockito.any(User.class))).thenReturn(testUser);
		assertNull(userService.createUser(testUser).getPassword());		
	}
    
	@Test
	void test_isUserExist_true() {
		when(userDao.findByUserName(Mockito.anyString())).thenReturn(testUser);
		assertTrue(userService.isUserExists(Mockito.anyString()));
	}
	
	@Test
	void test_isUserExist_false() {		
		when(userDao.findByUserName(Mockito.anyString())).thenReturn(null);
		assertFalse(userService.isUserExists(Mockito.anyString()));
	}
}
