package cbcoder.dealerwebapp.UsersInfo.services.impl;

import cbcoder.dealerwebapp.UsersInfo.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * JwtServiceImpl class implements JwtService interface and provides methods to generate and validate JWT tokens.
 * It uses JJWT library to generate and validate JWT tokens.
 *
 * @author Cleber Balbinote
 * @version 1.0
 * @since 2024-06-15
 */
@Service
public class JwtServiceImpl implements JwtService {

    @Value("${dealer-webapp.security.jwt.secret}")
    private String jwtSecret;

    @Value("${dealer-webapp.security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${dealer-webapp.security.jwt.expiration-refresh}")
    private long jwtRefreshExpiration;

    /**
     * Generates a JWT token for a given user.
     * The token is signed with the secret key and has an expiration time.
     *
     * @param userDetails User details to generate the token.
     * @return JWT token.
     */
    @Override
    public String generateJwtToken(UserDetails userDetails) {
        // Generate a JWT token for the user, signed with the secret key and with an expiration time.
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey())
                .compact();
    }

    /**
     * Generates a secret key to sign the JWT token.
     * This method complies with the JJWT library requirements version 12.5.0.
     *
     * @return Secret key.
     * @see SecretKey
     * @see Keys
     */
    private SecretKey getSignInKey() {
        // Convert the secret key to a byte array and generate a SecretKey object from it.
        byte[] keyBytes = jwtSecret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extracts the username from the JWT token.
     * Claims are the payload of the JWT token.
     *
     * @param token JWT token.
     * @return Username.
     */
    @Override
    public String getUsernameFromToken(String token) {
        // Extract the username from the JWT token.
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a claim from the JWT token.
     * Claims are the payload of the JWT token.
     *
     * @param token          JWT token.
     * @param claimsResolver Function to extract the claim.
     * @param <T>            Type of the claim.
     * @return Claim.
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        // Extract all claims from the JWT token and apply the claimsResolver function to extract a specific claim.
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    /**
     * Extracts all claims from the JWT token.
     * Claims are the payload of the JWT token.
     *
     * @param token JWT token.
     * @return All claims from the JWT token.
     */
    private Claims extractAllClaims(String token) {
        // Verify the JWT token with the secret key and extract all claims from it.
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Validates the JWT token.
     * The token is valid if the username in the token matches the username in the UserDetails object
     * and the token is not expired.
     *
     * @param token       JWT token.
     * @param userDetails User details to validate the token.
     * @return True if the token is valid, false otherwise.
     */
    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        /* Validate the JWT token
        by checking if the username in the token matches the username in the UserDetails object
        and if the token is not expired. */
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Checks if the JWT token is expired.
     *
     * @param token JWT token.
     * @return True if the token is expired, false otherwise.
     */
    @Override
    public boolean isTokenExpired(String token) {
        // Check if the expiration date of the token is before the current date.
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token JWT token.
     * @return Expiration date.
     */
    private Date extractExpiration(String token) {
        // Extract the expiration date from the JWT token.
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Generates a JWT token for a given user with the claims provided.
     * The token is signed with the secret key and has an expiration time.
     *
     * @param claims      Claims to add to the token.
     * @param userDetails User details to generate the token.
     * @return JWT token.
     */
    @Override
    public String generateRefreshJwtToken(Map<String, Object> claims, UserDetails userDetails) {
        /* Generate a JWT token for the user with the provided claims,
         signed with the secret key and with an expiration time.*/
        return Jwts
                .builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtRefreshExpiration))
                .signWith(getSignInKey())
                .compact();
    }
}
