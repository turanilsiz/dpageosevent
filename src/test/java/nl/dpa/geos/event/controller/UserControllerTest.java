package nl.dpa.geos.event.controller;

import static org.junit.jupiter.api.Assertions.*;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nl.dpa.geos.event.model.User;
import nl.dpa.geos.event.service.UserService;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
	
	@Autowired
    private MockMvc mvc;
	
	@MockBean
	private UserService userService;
	
	private ObjectMapper om ; 
	
	private User testUser;
	
	@BeforeEach
	public void setup() {
		 om = new ObjectMapper();
		 testUser = new User(1, "testUser", null, "testFirstName","testInsertion", "testLastName",1,"ADMIN","test@hotmail.com" );
    }

	@Test
	void test_createUser() throws JsonProcessingException, Exception {
		when(userService.createUser(Mockito.any(User.class))).thenReturn(testUser);
		mvc.perform(post("/registration").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(testUser)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.password", Matchers.nullValue()))
		;
	}

}
