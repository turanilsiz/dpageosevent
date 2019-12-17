package nl.dpa.geos.event.security;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
	
	public String getUserNameFromToken(String authToken) {
		String username = null;
		try {
			final Claims claims = getClaimsFromToken(authToken);
			username = claims.getSubject();
		}catch(Exception ex) {
			username = null;
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
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.after(new Date());	
	}
	
	private Date getExpirationDateFromToken(String token) {
		Date expiration = null;
		
		try {
			final Claims claims = getClaimsFromToken(token);
			if(claims !=null) {
				expiration = claims.getExpiration();
			}
		} catch (Exception ex) {
			expiration = null;
		}
		return expiration;
	}

	public String generateToken(JwtUser userDetails) {
		return generateTokenWithUserName(userDetails.getUsername());
		/*Map<String, Object> claims = new HashMap<String, Object>();
		claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
		claims.put(CLAIM_KEY_CREATED, new Date());
		return generateToken(claims);*/
	}
   
	public String generateTokenWithUserName(String userName) {
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put(CLAIM_KEY_USERNAME, userName);
		claims.put(CLAIM_KEY_CREATED, new Date());
		return generateToken(claims);
	}
	private String generateToken(Map<String, Object> claims) {		
		String generatedToken=  Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate()).signWith(SignatureAlgorithm.HS512, secret).compact();
		return generatedToken;
	}

	private Date generateExpirationDate() {
		Date d1 =  new Date(System.currentTimeMillis()+expiration*1000);
		return d1;
	}

}
