package cbcoder.dealerwebapp.UsersInfo.security.config;

import cbcoder.dealerwebapp.UsersInfo.services.JwtService;
import cbcoder.dealerwebapp.UsersInfo.services.UserSecurityService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * <p>Copyright (c) 2024, Cleber Balbinote.</p>
 * <p>All rights reserved.</p>
 * <p>Licensed under the MIT License.</p>
 * <p>For full license text, please see the LICENSE file in the repo root or <a href="https://opensource.org/licenses/MI">...</a>T</p>
 * </br>
 * Filter to intercept requests and validate JWT tokens in the Authorization header.
 * If the token is valid, the user is authenticated and added to the SecurityContext.
 * The filter is added to the Spring Security filter chain in the SecurityConfig class.
 *
 * @author Cleber Balbinote
 * @version 1.0
 * @since 2024-06-15
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserSecurityService userSecurityService;

    public JwtAuthFilter(JwtService jwtService, UserSecurityService userSecurityService) {
        this.jwtService = jwtService;
        this.userSecurityService = userSecurityService;
    }

    /**
     * Method to intercept requests and validate JWT tokens in the Authorization header.
     * If the token is valid, the user is authenticated and added to the SecurityContext.
     *
     * @param request     non-null HttpServletRequest object to get the Authorization header.
     * @param response    non-null HttpServletResponse object to send the response.
     * @param filterChain non-null FilterChain object to continue the filter chain.
     * @throws ServletException if an error occurs during the filter chain.
     * @throws IOException      if user entries are invalid.
     */
    @Override
    protected void doFilterInternal(
            // Non-null parameters to avoid null pointer exceptions.
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String jwt;
        final String userEmail; //
        final String authorizationHeader = request.getHeader("Authorization"); // Get the Authorization header from the request.
        // If the Authorization header is null or does not start with "Bearer ", continue the filter chain.
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return; // Return to avoid null pointer exception.
        }

        jwt = authorizationHeader.substring(7); // Get the token from the Authorization header.
        userEmail = jwtService.getUsernameFromToken(jwt); // Get the user email from the token.
        // If the user email is not null and the user is not authenticated, authenticate the user.
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load the user details from the user email.
            UserDetails userDetails = userSecurityService.userDetailsService().loadUserByUsername(userEmail);
            // If the token is valid, authenticate the user and add it to the SecurityContext.
            if (jwtService.validateToken(jwt, userDetails)) {
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext(); // Create a new SecurityContext.
                // Create a new UsernamePasswordAuthenticationToken with the user details and authorities.
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                securityContext.setAuthentication(authToken); // Set the authentication token to the SecurityContext.
                SecurityContextHolder.setContext(securityContext); // Set the SecurityContext to the SecurityContextHolder.
            }
        }
        filterChain.doFilter(request, response); // Continue the filter chain.
    }
}
