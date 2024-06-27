package cbcoder.dealerwebapp.UsersInfo.services.impl;

import cbcoder.dealerwebapp.UsersInfo.repositories.UserRepository;
import cbcoder.dealerwebapp.UsersInfo.security.AuthUser;
import cbcoder.dealerwebapp.UsersInfo.services.UserSecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * UserSecurityServiceImpl implements UserSecurityService interface
 * and provides the implementation for the userDetailsService method.
 * This method is used by the Spring Security to load the user details from the database.
 * The method uses the UserRepository to fetch the user details from the database.
 * If the user is not found, it throws a UsernameNotFoundException.
 * The method returns an instance of AuthUser which implements UserDetails interface.
 *
 * @author Cleber Balbinote
 * @version 1.0
 * @see UserSecurityService
 * @since 2024-06-15
 */
@Service
public class UserSecurityServiceImpl implements UserSecurityService {

    private final UserRepository userRepository;

    public UserSecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * This method is used by the Spring Security to load the user details from the database.
     * The method uses the UserRepository to fetch the user details from the database.
     * If the user is not found, it throws a UsernameNotFoundException.
     * The method returns an instance of AuthUser which implements UserDetails interface.
     */
    @Override
    public UserDetailsService userDetailsService() {
        // Lambda expression to implement the loadUserByUsername method of the UserDetailsService interface
        // and return an instance of AuthUser which implements UserDetails interface.
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                return userRepository.findByEmail(email)
                        .map(AuthUser::new)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }
}
