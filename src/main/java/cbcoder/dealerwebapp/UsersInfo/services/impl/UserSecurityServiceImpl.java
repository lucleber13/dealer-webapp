package cbcoder.dealerwebapp.UsersInfo.services.impl;

import cbcoder.dealerwebapp.UsersInfo.repositories.UserRepository;
import cbcoder.dealerwebapp.UsersInfo.security.AuthUser;
import cbcoder.dealerwebapp.UsersInfo.services.UserSecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityServiceImpl implements UserSecurityService {

	private final UserRepository userRepository;

	public UserSecurityServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetailsService userDetailsService() {
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
