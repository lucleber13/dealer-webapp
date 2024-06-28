package cbcoder.dealerwebapp.UsersInfo.services;

import cbcoder.dealerwebapp.UsersInfo.Dtos.UserDto;
import cbcoder.dealerwebapp.UsersInfo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * UserService interface provides the methods to interact with the User entity.
 * The methods are implemented in the UserServiceImpl class.
 *
 * @author Cleber Balbinote
 * @version 1.0
 * @see User
 * @see UserDto
 * @see Page
 * @see Pageable
 * @since 2024-06-15
 */
public interface UserService {

    User updateUser(Long userId, UserDto userDto);

    String deleteUser(Long userId);

    Page<User> getAllUsers(Pageable pageable);

    User getUserById(Long userId);

    User adminUpdateUser(Long userId, UserDto userDto);
}
