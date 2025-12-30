package com.projet.Chapi.services;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.projet.Chapi.models.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JWTService {
	
	@Value("${chapi.api.key}")
	private String SECRET_KEY;

	public String generateToken(Users user) {
		return Jwts.builder()
				.subject(String.valueOf(user.getId()))
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String extractUserIdFromHttpRequest(HttpServletRequest request) {
		return extractUserId(extractTokenFromHttpRequest(request));
	}
	
	public String extractTokenFromHttpRequest(HttpServletRequest request) {
		return request.getHeader("Authorization").substring(7);
	}

	public String extractUserId(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResoler) {
		final Claims claims = extractAllClaims(token);
		return claimsResoler.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	private Key getSignInKey() {
		byte[] keyBites = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBites);
	}
	
	public String getKey() {
		return SECRET_KEY;
	}
	
	public boolean isTokenValid(String token, Users user) {
		final String username = extractUserId(token);		
		return (username.equals(String.valueOf(user.getId())) || isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

}

