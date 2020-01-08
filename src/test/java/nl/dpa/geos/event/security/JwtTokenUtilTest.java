package nl.dpa.geos.event.security;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import io.jsonwebtoken.Claims;


@SpringBootTest
@AutoConfigureMockMvc
class JwtTokenUtilTest {
	
	
	@MockBean
	private JwtTokenUtil jwtTokenUtilMock;
	
	@MockBean
	private Claims claim;
	
	private JwtTokenUtil jwtTokenUtil;
	
	@BeforeEach
	public void setup() {
		jwtTokenUtil = new JwtTokenUtil();
    }

	@Test
	void test_getUserNameFromToken_claims_null() {
		when(jwtTokenUtilMock.getClaimsFromToken(Mockito.anyString())).thenReturn(null);
		assertNull(jwtTokenUtil.getUserNameFromToken("test"));
		
	}


}
