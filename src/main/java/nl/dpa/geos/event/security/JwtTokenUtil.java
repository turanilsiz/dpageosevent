package nl.dpa.geos.event.security;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;



@Component
public class JwtTokenUtil implements Serializable{
	
    private static final long serialVersionUID = 1L;
	
	static final String CLAIM_KEY_USERNAME ="sub";
	static final String CLAIM_KEY_AUDINCE ="audince";
	static final String CLAIM_KEY_CREATED ="created";
    
	@Value("${jwt.scret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expiration;
	
	@Transient
	private Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);
	
	public String getUserNameFromToken(String authToken) {
		String username = null;
		try {
			final Claims claims = getClaimsFromToken(authToken);
			if (claims != null) {
				username = claims.getSubject();
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return username;
	}

	private Claims getClaimsFromToken(String token) {
		Claims claims =null;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
			
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}
	
	public boolean validateToken(String token, UserDetails userDetails) {
		JwtUser user = (JwtUser)userDetails;
		final String username =getUserNameFromToken(token);
		return (username.equals(user.getUsername()) && isTokenExpired(token));
	}
    
	private boolean isTokenExpired(String token) {
		return getExpirationDateFromToken(token).after(new Date());	
	}
	
	private Date getExpirationDateFromToken(String token) {
		Date expirationDate = new Date();
		
		try {
			final Claims claims = getClaimsFromToken(token);
			if(claims !=null) {
				expirationDate = claims.getExpiration();
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return expirationDate;
	}

	public String generateToken(JwtUser userDetails) {
		return generateTokenWithUserName(userDetails.getUsername());
	}
   
	public String generateTokenWithUserName(String userName) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME, userName);
		claims.put(CLAIM_KEY_CREATED, new Date());
		return generateToken(claims);
	}
	private String generateToken(Map<String, Object> claims) {		
		return  Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate()).signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	private Date generateExpirationDate() {
		return  new Date(System.currentTimeMillis()+expiration*1000);
	}

}
