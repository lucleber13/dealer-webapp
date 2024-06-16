package cbcoder.dealerwebapp.UsersInfo.services;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserSecurityService {

	UserDetailsService userDetailsService();
}
