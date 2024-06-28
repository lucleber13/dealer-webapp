package cbcoder.dealerwebapp.UsersInfo.services;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

/**
 * <p>Copyright (c) 2024, Cleber Balbinote.</p>
 * <p>All rights reserved.</p>
 * <p>Licensed under the MIT License.</p>
 * <p>For full license text, please see the LICENSE file in the repo root or <a href="https://opensource.org/licenses/MI">...</a>T</p>
 * </br>
 * JwtService interface provides the methods to generate, validate and extract information from JWT tokens.
 * The methods are implemented in the JwtServiceImpl class.
 *
 * @author Cleber Balbinote
 * @version 1.0
 * @since 2024-06-15
 */
public interface JwtService {

    String generateJwtToken(UserDetails userDetails);

    String getUsernameFromToken(String token);

    boolean validateToken(String token, UserDetails userDetails);

    boolean isTokenExpired(String token);

    String generateRefreshJwtToken(Map<String, Object> claims, UserDetails userDetails);
}
