package cbcoder.dealerwebapp.UsersInfo.services;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {

	String generateJwtToken(UserDetails userDetails);

	String getUsernameFromToken(String token);

	boolean validateToken(String token, UserDetails userDetails);

	boolean isTokenExpired(String token);

	String generateRefreshJwtToken(Map<String, Object> claims, UserDetails userDetails);
}
