package cbcoder.dealerwebapp.UsersInfo.services.impl;

import cbcoder.dealerwebapp.UsersInfo.Dtos.UserDto;
import cbcoder.dealerwebapp.UsersInfo.exceptions.EmailNotBindingException;
import cbcoder.dealerwebapp.UsersInfo.exceptions.OperationNotPermittedException;
import cbcoder.dealerwebapp.UsersInfo.exceptions.PasswordTooShortException;
import cbcoder.dealerwebapp.UsersInfo.exceptions.UserNotFoundException;
import cbcoder.dealerwebapp.UsersInfo.model.Role;
import cbcoder.dealerwebapp.UsersInfo.model.User;
import cbcoder.dealerwebapp.UsersInfo.model.enums.RoleEnum;
import cbcoder.dealerwebapp.UsersInfo.repositories.RoleRepository;
import cbcoder.dealerwebapp.UsersInfo.repositories.UserRepository;
import cbcoder.dealerwebapp.UsersInfo.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * UserServiceImpl class implements UserService interface and provides the implementation of the methods declared in the interface.
 * In this class, we have implemented the methods to update, delete, get all and get user by id.
 * But to access these methods, for example, will be determined by the role of the user.
 * This class will be managed by the admin and the admin will have the authority to update, delete, get all and get user by id.
 * The user will only have the authority to update their first name, last name and password.
 * In the controller class we will determine the role of the user, and based on the role we will provide access to the methods.
 * The class SecurityConfig will set the endpoints and the roles of the users to access the specific endpoints.
 *
 * @author Cleber Balbinote
 * @version 1.0
 * @implNote This class implements the methods declared in the UserService interface.
 * @apiNote This class will be managed by the admin and the admin will have the authority to update, delete, get all and get user by id.
 * The user will only have the authority to update their first name, last name and password.
 * @since 2024-06-15
 */

@Service
public class UserServiceImpl implements UserService {

    private static final String USER_NOT_FOUND = "User not found with the given id ";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * This method only has the accesses to the admin to update any user information. The user id and the userDto will be passed as a parameter.
     * The user information will be updated based on the user id. The userDto will contain the first name, last name, email, password and the roles.
     * If the email is not matched with the user email, then it will throw an exception. Because the email should be matched with the user email to avoid updating the wrong user information.
     * If the user is not found with the given ID, then it will throw an exception.
     * In this method, the admin has the authority to enable or disable the user.
     * And the last the admin can update a user role to another role, in case the user needs to change the department.
     *
     * @param userId  the user id to update the user information.
     * @param userDto the user information to be updated based on the user id and email.
     * @return the updated user information.
     */
    @Override
    public User adminUpdateUser(Long userId, UserDto userDto) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(USER_NOT_FOUND + userId);
        }
        User userToUpdate = userOptional.get();
        if (!userDto.getEmail().equals(userToUpdate.getEmail()) && !Objects.deepEquals(userDto.getUserId(), userToUpdate.getUserId())) {
            throw new EmailNotBindingException("Email not matching with the user email!");
        }
        userToUpdate.setFirstName(userDto.getFirstName());
        userToUpdate.setLastName(userDto.getLastName());
        userToUpdate.setUpdatedAt(LocalDateTime.now());
        userToUpdate.setEnabled(userDto.isEnabled());
        Set<Role> roles = new LinkedHashSet<>();
        userDto.getRoles().forEach(roleDto -> {
            Optional<Role> role = roleRepository.findByRoleName(RoleEnum.valueOf(roleDto.getRoleName().name()));
            role.ifPresent(roles::add);
        });
        userToUpdate.setRoles(roles);
        User updatedUser = modelMapper.map(userToUpdate, User.class);
        return userRepository.save(updatedUser);
    }

    /**
     * This method will update the user information based on the user id. The user id will be passed as a parameter.
     * The admin and the user will have the authority to update the user information.
     * The userDto will be passed as a parameter to update the user information.
     * The userDto will contain the first name, last name, email and password.
     * The user information will be updated based on the user id.
     * The user information will be updated only if the email is matched with the user email.
     * If the email is not matched with the user email, then it will throw an exception.
     * If the user is not found with the given ID, then it will throw an exception.
     * The updated user information will be saved in the database.
     *
     * @param userDto the user information to be updated based on the user id.
     * @param userId  the user id to update the user information.
     * @return the updated user information.
     */
    @Override
    public User updateUser(Long userId, UserDto userDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User userToUpdate = userOptional.get();
            if (!userDto.getEmail().equals(userToUpdate.getEmail())) {
                throw new EmailNotBindingException("User not matching with the user email!");
            }
            if (!currentPrincipalName.equals(userToUpdate.getEmail())) {
                throw new OperationNotPermittedException("User can only update their own information");
            }
            userToUpdate.setFirstName(userDto.getFirstName());
            userToUpdate.setLastName(userDto.getLastName());
            if (userDto.getPassword().length() < 8) {
                throw new PasswordTooShortException("Password must be at least 8 characters long");
            }
            userToUpdate.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userToUpdate.setUpdatedAt(LocalDateTime.now());
            return userRepository.save(userToUpdate);
        } else {
            throw new UserNotFoundException(USER_NOT_FOUND + userId);
        }
    }

    /**
     * This method will delete the user based on the user id. The user id will be passed as a parameter.
     * Only the admin will have the authority to delete the user information.
     * If the admin tries to delete the user with the role ADMIN, then it will throw an exception.
     * The user information will be deleted based on the user id.
     * If the user is not found with the given ID, then it will throw an exception.
     * The user information will be deleted from the database.
     *
     * @param userId the user id to delete the user information.
     * @return the message that the user information is deleted successfully.
     */
    @Override
    public String deleteUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            if (userOptional.get().getRoles().stream().anyMatch(role ->
                    role.getRoleName().equals(RoleEnum.ROLE_ADMIN) || role.getRoleName().equals(RoleEnum.ROLE_SUPERADMIN))) {
                throw new OperationNotPermittedException("Operation not permitted");
            }
            User userToDelete = userOptional.get();
            userRepository.delete(userToDelete);
            return "User deleted successfully!";
        }
        throw new UserNotFoundException(USER_NOT_FOUND + userId);
    }

    /**
     * This method will get all the users from the database.
     * The admin will have the authority to get all the users from the database.
     * The user information will be fetched from the database.
     * If no users are found in the database, then it will throw an exception.
     * The user information will be fetched from the database based on the page number and the page size.
     * The user information will be fetched from the database based on the page number and the page size.
     *
     * @param pageable the page number and the page size to fetch the user information.
     * @return the user information fetched from the database based on the page number and the page size.
     */
    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        if (users.isEmpty()) {
            throw new UserNotFoundException("No users found in the database!");
        }
        return users;
    }

    /**
     * This method will get the user based on the user id. The user id will be passed as a parameter.
     * The admin and the user will have the authority to get the user information based on the user id.
     * The user information will be fetched from the database based on the user id.
     * If the user is not found with the given ID, then it will throw an exception.
     *
     * @param userId the user id to get the user information.
     * @return the user information fetched from the database based on the user id.
     */
    @Override
    public User getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new UserNotFoundException(USER_NOT_FOUND + userId);
    }
}
