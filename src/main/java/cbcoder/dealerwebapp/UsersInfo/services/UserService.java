package cbcoder.dealerwebapp.UsersInfo.services;

import cbcoder.dealerwebapp.UsersInfo.Dtos.UserDto;
import cbcoder.dealerwebapp.UsersInfo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

	User updateUser(Long userId, UserDto userDto);

	String deleteUser(Long userId);

	Page<User> getAllUsers(Pageable pageable);

	User getUserById(Long userId);

	User adminUpdateUser(Long userId, UserDto userDto);
}
