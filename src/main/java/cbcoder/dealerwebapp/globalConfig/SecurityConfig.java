package cbcoder.dealerwebapp.globalConfig;

import cbcoder.dealerwebapp.UsersInfo.security.config.JwtAuthFilter;
import cbcoder.dealerwebapp.UsersInfo.services.UserSecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * <p>Copyright (c) 2024, Cleber Balbinote.</p>
 * <p>All rights reserved.</p>
 * <p>Licensed under the MIT License.</p>
 * <p>For full license text, please see the LICENSE file in the repo root or <a href="https://opensource.org/licenses/MI">...</a>T</p>
 * </br>
 * SecurityConfig class is the configuration class for the security of the application. It is responsible for configuring the security of the application.
 * In the class, SecurityConfig has added an annotation @EnableSpringDataWebSupport to enable Spring Data Web Support.
 * This annotation is used to enable Spring Data Web Support for the application.
 * It is used to configure the page serialization mode for Spring Data Web Support. When using Page serialization mode VIA_DTO,
 * the Pageable argument in the controller methods will be serialized to a DTO object. This way avoiding the response
 * with json well formatted.
 * The class also has a method filterChain(HttpSecurity http) that is responsible for configuring the security of the application.
 *
 * @author Cleber Blabinote
 * @version 1.0
 * @since 2024-06-27
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class SecurityConfig {
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    private final JwtAuthFilter jwtAuthFilter;
    private final UserSecurityService userSecurityService;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, UserSecurityService userSecurityService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userSecurityService = userSecurityService;
    }

    /**
     * This method is responsible for configuring the security of the application.
     * The endpoints will be configured to allow access to specific authorities.
     * The endpoints '/auth/**' and will be accessible by anyone. The endpoints '/users/admin/**' will be accessible only by users with the role 'ADMIN'.
     * The endpoints '/users/**' will be accessible by users with the roles 'SUPERADMIN', 'ADMIN', 'SALES', 'WORKSHOP' and 'VALETER'.
     * The endpoints '/superadmin/**' will be accessible only by users with the role 'SUPERADMIN'.
     * The endpoints '/swagger-ui/**' and '/v3/api-docs/**' will be accessible by anyone.
     * The other endpoints will be accessible only by authenticated users.
     *
     * @param http the HttpSecurity object to be configured.
     * @return a SecurityFilterChain object.
     * @throws Exception if an error occurs while configuring the security.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        logger.info("SecurityConfig.filterChain(HttpSecurity http) called");
        return http
                .csrf(AbstractHttpConfigurer::disable) // Disabled CSRF (Cross-Site Request Forgery).
                .cors(AbstractHttpConfigurer::disable) // Disabled CORS (Cross-Origin Resource Sharing).
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/admin/**").hasAnyRole("ADMIN", "SUPERADMIN")
                                .requestMatchers("/users/**").hasAnyRole("SUPERADMIN", "ADMIN", "SALES", "WORKSHOP", "VALETER")
                                .requestMatchers("/superadmin/**").hasRole("SUPERADMIN")
                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                .requestMatchers("/cars/create/**").hasAnyRole("ADMIN", "SALES")
                                .requestMatchers("/cars/delete/**").hasAnyRole("ADMIN", "SALES")
                                .requestMatchers("/cars/**").hasAnyRole("ADMIN", "SALES", "WORKSHOP", "VALETER")
                                .anyRequest()
                                .authenticated())
                // Session management configuration.
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // Add the JwtAuthFilter before the UsernamePasswordAuthenticationFilter.
                .build(); // Build the SecurityFilterChain.
    }

    /**
     * This method is responsible for creating an AuthenticationProvider object.
     * The AuthenticationProvider object is responsible for authenticating the user.
     * The AuthenticationProvider object will use the UserSecurityService object to retrieve the user information.
     * The AuthenticationProvider object will use the PasswordEncoder object to encode the password.
     * This gets the principal and credentials from the Authentication object
     * and returns a fully authenticated Authentication object.
     *
     * @return an AuthenticationProvider object.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        // Create a DaoAuthenticationProvider object.
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // Set the UserDetailsService object to the UserSecurityService object.
        provider.setUserDetailsService(userSecurityService.userDetailsService());
        // Set the PasswordEncoder object to the PasswordEncoder object.
        provider.setPasswordEncoder(passwordEncoder());
        // Return the AuthenticationProvider object.
        return provider;
    }

    /**
     * This method is responsible for creating a PasswordEncoder object.
     * The PasswordEncoder object is responsible for encoding the password.
     * The PasswordEncoder object will use the BCryptPasswordEncoder to encode the password.
     * The BCryptPasswordEncoder is a PasswordEncoder implementation that uses the BCrypt strong hashing function.
     *
     * @return a PasswordEncoder object.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Return a BCryptPasswordEncoder object.
        return new BCryptPasswordEncoder();
    }

    /**
     * This method is responsible for creating an AuthenticationManager object.
     * The AuthenticationManager object is responsible for authenticating the user.
     * The AuthenticationManager object will use the AuthenticationConfiguration object to get the AuthenticationManager.
     *
     * @param configuration the AuthenticationConfiguration object to be used.
     * @return an AuthenticationManager object.
     * @throws Exception if an error occurs while creating the AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        // Return the AuthenticationManager object.
        return configuration.getAuthenticationManager();
    }

}
