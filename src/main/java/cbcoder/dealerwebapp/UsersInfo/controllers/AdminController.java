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

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    /**
     * This method is responsible for getting all users from the database.
     * This method is only accessible by users with the role 'ROLE_ADMIN'.
     *
     * @param pageNo   determines the page number to be returned from the database.
     * @param pageSize determines the number of users to be returned per page.
     * @param sortBy   determines the sorting order of the users to be returned.
     * @return a ResponseEntity containing a Page of Users.
     */
    @GetMapping(value = "/all", produces = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Page<User>> getAllUsers(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "userId") String sortBy) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    /**
     * This method is responsible for deleting a user by its id.
     * This method is only accessible by users with the role 'ROLE_ADMIN'.
     *
     * @param userId determines the id of the user to be deleted.
     * @return a ResponseEntity containing a message indicating the success of the operation.
     */
    @PutMapping(value = "/update/{userId}", produces = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<User> adminUpdateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.adminUpdateUser(userId, userDto));
    }

    /**
     * This method is responsible for deleting a user by its id.
     * This method is only accessible by users with the role 'ROLE_ADMIN'.
     *
     * @param userId determines the id of the user to be deleted.
     * @return a ResponseEntity containing a message indicating the success of the operation.
     */
    @DeleteMapping(value="/delete/{userId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.deleteUser(userId));
    }

    /**
     * This method is responsible for getting a user by its id.
     * This method allows access to users with the roles 'ROLE_ADMIN', 'ROLE_SALES', 'ROLE_WORKSHOP' and 'ROLE_VALETER'.
     * The user with the role 'ROLE_ADMIN' can access any user, while the other roles can only access their own user.
     *
     * @param userId determines the id of the user to be returned.
     * @return a ResponseEntity containing the User.
     */
    @GetMapping(value = "/get/{userId}",  produces = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }
}
