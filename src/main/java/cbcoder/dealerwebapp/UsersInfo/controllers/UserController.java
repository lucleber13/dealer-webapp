package cbcoder.dealerwebapp.UsersInfo.controllers;

import cbcoder.dealerwebapp.UsersInfo.Dtos.UserDto;
import cbcoder.dealerwebapp.UsersInfo.model.User;
import cbcoder.dealerwebapp.UsersInfo.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * UserController class is the controller class for the User entity. It is responsible for handling the HTTP requests for the User entity.
 * In the class SecurityConfig has added an annotation @EnableSpringDataWebSupport to enable Spring Data Web Support.
 * This annotation is used to enable Spring Data Web Support for the application.
 * It is used to configure the page serialization mode for Spring Data Web Support. When using Page serialization mode VIA_DTO,
 * the Pageable argument in the controller methods will be serialized to a DTO object. This way avoiding the response
 * with json well formatted.
 *
 * @author : Cleber Blabinote
 * @version 1.0
 * @since : 15/06/2024
 */

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	/**
	 * This method is responsible for getting a user by its id.
	 * This method allows access to users with the roles 'ROLE_ADMIN', 'ROLE_SALES', 'ROLE_WORKSHOP' and 'ROLE_VALETER'.
	 * The user with the role 'ROLE_ADMIN' can access any user, while the other roles can only access their own user.
	 *
	 * @param userId determines the id of the user to be returned.
	 * @return a ResponseEntity containing the User.
	 */
	@PutMapping(value = "/{userId}", produces = "application/json")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_SALES', 'ROLE_WORKSHOP', 'ROLE_VALETER')")
	public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
		return ResponseEntity.ok(userService.updateUser(userId, userDto));
	}
}
