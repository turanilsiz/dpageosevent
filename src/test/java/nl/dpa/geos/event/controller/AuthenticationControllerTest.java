package nl.dpa.geos.event.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import nl.dpa.geos.event.model.User;



@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {
	
	@Autowired
    private MockMvc mvc;
	
//	@MockBean
//	private AuthenticationManager authenticationManager;
//	
//	@MockBean
//	private Authentication authentication;
	
	
	private ObjectMapper om ; 
	private String userName;
	
	@BeforeEach
	public void setup() {
		 om = new ObjectMapper();
		 userName = "admin1";
    }

	@Test
	void testLogin_succes() throws Exception{	
	    String password = "12345";
		User usr = new User();
		usr.setUserName(userName);
		usr.setPassword(password);

	    mvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(usr)))
		  // .andDo(print())
		   .andExpect(status().isOk())
		   .andExpect(jsonPath("$.user.userName").value(userName))
		   .andExpect(jsonPath("$.user.password", Matchers.nullValue()))		  
		   ;
	}
    
	@Test
	void testLogin_failed() throws Exception{
		String password = "123455";
		User usr = new User();
		usr.setUserName(userName);
		usr.setPassword(password);

		MvcResult result = mvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(usr)))
		  // .andDo(print())
		   .andExpect(status().isOk())
		   .andReturn();
		assertEquals(0, result.getResponse().getContentLength());
		   	  
	}
}
