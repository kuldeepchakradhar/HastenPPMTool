package io.hastentech.ppmtool.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.hastentech.ppmtool.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

	public String generateToken(Authentication authentication) {
		User user = (User)authentication.getPrincipal();
		
		Date now = new Date(System.currentTimeMillis());
		
		Date expiretyDate = new Date(now.getTime()+SecurityConstants.EXPIRATION_TIME);
		
		String userId = Long.toString(user.getId());
		
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", (Long.toString(user.getId())));
		claims.put("username", user.getUsername());
		claims.put("fullname", user.getFullname());
		
		return Jwts.builder()
				.setSubject(userId)
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(expiretyDate)
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
				.compact();
			
				
	}
	
	// validate the token
	public boolean validateToken(String token) {
		
		try {
			Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token);
			return true;
		}catch (SignatureException ex) {
			// TODO: handle exception
			System.out.println("Invalid Jwt Signature");
		}catch (MalformedJwtException exception) {
			// TODO: handle exception
			System.out.println("Invalid Jwt token");
		}catch (ExpiredJwtException e) {
			// TODO: handle exception
			System.out.println("Expired Jwt Token");
		}catch (UnsupportedJwtException e) {
			// TODO: handle exception
			System.out.println("Unsupported Jwt Token");
		}catch (IllegalArgumentException e) {
			// TODO: handle exception
			System.out.println("Jwt claims String is empty");
		}
	
		return false;
	}
	
	// Get userId from jwt
	
	public Long getUserIdFromJwt(String token) {
		
		Claims claims = Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token).getBody();
        String id = (String)claims.get("id");
//        String id = "8";
        return Long.valueOf(id);
	}
}

