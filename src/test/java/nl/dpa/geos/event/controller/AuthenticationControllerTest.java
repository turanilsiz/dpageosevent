package nl.dpa.geos.event.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nl.dpa.geos.event.model.User;
import nl.dpa.geos.event.service.UserService;



@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {
	
	@Autowired
    private MockMvc mvc;
	
	@MockBean
	private UserService userService;

	private ObjectMapper om ; 
	private String userName;
	private User usr;
	
	@BeforeEach
	public void setup() {
		 om = new ObjectMapper();
		 userName = "admin1";
		 
		 String password = "12345";
		 usr = new User();
		 usr.setUserName(userName);
		 usr.setPassword(password);
    }

	@Test
	void testLogin_succes() throws Exception{	
	    mvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(usr)))
		   .andExpect(status().isOk())
		   .andExpect(jsonPath("$.user.userName").value(userName))
		   .andExpect(jsonPath("$.user.password", Matchers.nullValue()))		  
		   ;
	}
    
	@Test
	void test_login_failed() throws Exception{
		usr.setPassword("123455");
		 mvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(usr)))
		   .andExpect(status().isUnauthorized())
		   .andReturn();	   	  
	}
	
	@Test
	void test_login_failed_user_wrong_password() throws JsonProcessingException, Exception {
		usr.setPassword("123455");
		when(userService.isUserExists(Mockito.anyString())).thenReturn(true);
	       mvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(usr)))
		   .andExpect(jsonPath("exceptionInfo").value("Onjuist wachtwoord"))
		   ;
	}
	
	@Test
	void test_login_failed_wrong_username() throws JsonProcessingException, Exception {
		usr.setPassword("123455");
		when(userService.isUserExists(Mockito.anyString())).thenReturn(false);
	       mvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(usr)))
		   .andExpect(jsonPath("exceptionInfo").value("Onjuist gebruikernaam"))
		   ;
	}
}
