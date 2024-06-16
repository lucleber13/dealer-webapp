package cbcoder.dealerwebapp.UsersInfo.services.impl;

import cbcoder.dealerwebapp.UsersInfo.Dtos.*;
import cbcoder.dealerwebapp.UsersInfo.exceptions.*;
import cbcoder.dealerwebapp.UsersInfo.model.Role;
import cbcoder.dealerwebapp.UsersInfo.model.User;
import cbcoder.dealerwebapp.UsersInfo.repositories.RoleRepository;
import cbcoder.dealerwebapp.UsersInfo.repositories.UserRepository;
import cbcoder.dealerwebapp.UsersInfo.security.AuthUser;
import cbcoder.dealerwebapp.UsersInfo.services.JwtService;
import cbcoder.dealerwebapp.UsersInfo.services.UserAuthService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * The UserAuthServiceImpl class implements the UserAuthService interface and provides methods to register a new user,
 * log in a user and refresh the JWT token.
 * The class uses the UserRepository, PasswordEncoder, JwtService, AuthenticationManager, RoleRepository and ModelMapper
 * to provide the services.
 * The class provides the register, login and refreshToken methods to register a new user, log in a user and refresh the JWT token.
 * The register method registers a new user using the information provided in the request body and returns the user object.
 * The login method logs in the user using the email and password provided in the request body and returns the JWT token and refresh token.
 * The refreshToken method refreshes the JWT token using the refresh token provided by the user in the request body and returns the new JWT token.
 *
 * @author Cleber Balbinote
 * @version 1.0
 * @apiNote This class is part of the UsersInfo feature.
 * @implNote The class uses the UserRepository, PasswordEncoder, JwtService, AuthenticationManager, RoleRepository and ModelMapper to provide the services.
 * @since 2024-06-15
 */

@Service
public class UserAuthServiceImpl implements UserAuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final RoleRepository roleRepository;
	private final ModelMapper modelMapper;

	public UserAuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
	                           AuthenticationManager authenticationManager, RoleRepository roleRepository, ModelMapper modelMapper) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
		this.roleRepository = roleRepository;
		this.modelMapper = modelMapper;
	}

	/**
	 * Registers a new user using the information provided in the request body and returns the user object.
	 * This method requires the user email to be unique. If the email is not unique, an exception is thrown.
	 * If the email is unique, a new user is created and returned.
	 * Passing the first name, last name, email, password and roles into the body request at client side will return the user object.
	 * The user object will contain the first name, last name, email, password and roles.
	 * The user object will also contain the user id, created at and updated at fields.
	 * The user object will contain the enabled field which is set to true by default. The user can be disabled or enabled back by the admin.
	 * Roles will be set at moment of user register. The role choices are SALES, WORKSHOP, VALETER.
	 * The SUPERADMIN are set at database level. Then the SUPERADMIN can set the ADMIN role to other users.
	 *
	 * @param signUpRequest the sign-up request containing the first name, last name, email, password and roles to be used to create the user.
	 * @return the user object created using the first name, last name, email, password and roles.
	 */
	@Override
	public User register(SignUpRequest signUpRequest) {
		UserDto userDto = new UserDto();
		if (userRepository.existsByEmail(signUpRequest.email())) {
			throw new UserAlreadyExistsException("User with email " + signUpRequest.email() + " already exists");
		}
		userDto.setFirstName(signUpRequest.firstName());
		userDto.setLastName(signUpRequest.lastName());
		userDto.setEmail(signUpRequest.email());
		if (signUpRequest.password().length() < 8) {
			throw new PasswordTooShortException("Password must be at least 8 characters long");
		}
		userDto.setPassword(passwordEncoder.encode(signUpRequest.password()));
		userDto.setEnabled(true);
		Set<Role> roles = new LinkedHashSet<>();
		signUpRequest.roles().forEach(role -> {
			Optional<Role> roleOptional = roleRepository.findByRoleName(role.getRoleName());
			if (roleOptional.isPresent()) {
				roles.add(roleOptional.get());
			} else {
				throw new RoleNotFoundException("Role not found");
			}
		});
		userDto.setRoles(roles);
		User user = modelMapper.map(userDto, User.class);
		return userRepository.save(user);
	}

	/**
	 * Logs in the user using the email and password provided in the request body and returns the JWT token and refresh token.
	 * This method requires the user email and password to be valid. If the email or password is invalid, an exception is thrown.
	 * If the email and password are valid, a new JWT token is generated and returned.
	 * Passing the email and password into the body request at client side will return the JWT token and refresh token.
	 * The Token is valid for 24 hours and the refresh token is valid for 7 days. After 7 days, the user will have to log in again.
	 * The method also have checks for user enabled status. If the user is not enabled, an exception is thrown.
	 * At moment of user register the user is enabled by default. The user can be disabled or enabled back by the admin.
	 *
	 * @param signInRequest the sign in request containing the email and password to be used to generate the JWT token.
	 * @return the JWT token and refresh token generated using the email and password.
	 */
	@Override
	public JwtAuthResponse login(SignInRequest signInRequest) {
		try {
			var loginUser = userRepository.findByEmail(signInRequest.email()).orElseThrow();
			if (!loginUser.isEnabled()) {
				throw new UserNotEnabledException("User is not enabled");
			}

			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(signInRequest.email(), signInRequest.password()));

			AuthUser user = new AuthUser(loginUser);

			var jwt = jwtService.generateJwtToken(user);
			var refreshToken = jwtService.generateRefreshJwtToken(new HashMap<>(), user);

			return new JwtAuthResponse(jwt, refreshToken);
		} catch (AuthenticationException e) {
			throw new EmailOrPasswordNotMatchingException("Invalid email or password");
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email", e);
		} catch (UserNotEnabledException e) {
			throw new UserNotEnabledException(e.getMessage());
		}
	}

	/**
	 * Refreshes the JWT token using the refresh token provided by the user in the request body and returns the new JWT token.
	 * This method requires the user email, the token and the refresh token to be valid. If the refresh token is invalid, an exception is thrown.
	 * If the refresh token is valid, a new JWT token is generated and returned.
	 * Passing the email, token and refresh token into the body request at client side will return the new JWT token.
	 * The Token is valid for 24 hours and the refresh token is valid for 7 days. After 7 days, the user will have to log in again.
	 *
	 * @param refreshTokenRequest the refresh token request containing the refresh token to be used to generate the new JWT token.
	 * @return the new JWT token generated using the refresh token.
	 */
	@Override
	public JwtAuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
		String email = jwtService.getUsernameFromToken(refreshTokenRequest.getRefreshToken());
		var user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Invalid email"));
		AuthUser authUser = new AuthUser(user);
		if (jwtService.validateToken(refreshTokenRequest.getRefreshToken(), authUser)) {
			var jwt = jwtService.generateJwtToken(authUser);
			var refreshToken = jwtService.generateRefreshJwtToken(new HashMap<>(), authUser);
			return new JwtAuthResponse(jwt, refreshToken);
		} else {
			throw new IllegalArgumentException("Invalid refresh token");
		}
	}
}
