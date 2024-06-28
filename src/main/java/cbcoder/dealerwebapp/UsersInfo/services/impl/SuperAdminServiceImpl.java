package cbcoder.dealerwebapp.UsersInfo.services.impl;

import cbcoder.dealerwebapp.UsersInfo.Dtos.UserDto;
import cbcoder.dealerwebapp.UsersInfo.model.Role;
import cbcoder.dealerwebapp.UsersInfo.model.User;
import cbcoder.dealerwebapp.UsersInfo.model.enums.RoleEnum;
import cbcoder.dealerwebapp.UsersInfo.repositories.RoleRepository;
import cbcoder.dealerwebapp.UsersInfo.repositories.UserRepository;
import cbcoder.dealerwebapp.UsersInfo.services.SuperAdminService;
import cbcoder.dealerwebapp.exceptions.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * <p>Copyright (c) 2024, Cleber Balbinote.</p>
 * <p>All rights reserved.</p>
 * <p>Licensed under the MIT License.</p>
 * <p>For full license text, please see the LICENSE file in the repo root or <a href="https://opensource.org/licenses/MI">...</a>T</p>
 * </br>
 * SuperAdminServiceImpl class implements SuperAdminService interface and provides the implementation of the methods declared in the interface.
 * In this class, we have implemented the methods to add and revoke the admin role to the user.
 * The super admin will have the authority to add and revoke the admin role to the user.
 *
 * @author Cleber Balbinote
 * @version 1.0
 * @implNote This class implements the methods declared in the SuperAdminService interface.
 * @apiNote The super admin will have the authority to add and revoke the admin role to the user.
 * @since 2024-06-15
 */
@Service
public class SuperAdminServiceImpl implements SuperAdminService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public SuperAdminServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * This method only has the accesses to the super admin to add an admin role to any user. The userDto will be passed as a parameter.
     * The userDto will have the user id and the email of the user.
     * The method will check if the user is super admin or not. If the user is not super admin, then it will throw an exception.
     * If the user is super admin, then it will check if the email given in the userDto is matched with the user email.
     * If the email is not matching, then it will throw an exception. Because the email should be matched with the user email to avoid adding the wrong role to the wrong user.
     * If the email is matching, then it will check if the user already has the admin role or not. If the user already has the admin role, then it will throw an exception.
     * If the user does not have the admin role, then it will add the admin role to the user and save the user information.
     * The updated user information will be returned.
     *
     * @param userDto The userDto will have the user id and the email of the user.
     * @return The updated user information.
     */
    @Override
    public User addAdminRole(UserDto userDto) {
        Authentication authentication = getAuthentication();
        if (authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals(RoleEnum.ROLE_SUPERADMIN.name()))) {
            throw new NotAuthorizedAccessException("Only super admin can add admin role to user.");
        }

        User user = getUserById(userDto.getUserId());
        if (!user.getEmail().equals(userDto.getEmail())) {
            throw new EmailNotBindingException("Email given, not matching with user email.");
        }

        Role adminRole = getRoleByName();
        if (user.getRoles().contains(adminRole)) {
            throw new RoleAlreadyAssignedException("User already has admin role.");
        } else {
            user.getRoles().add(adminRole);
            user.setUpdatedAt(LocalDateTime.now());
            return userRepository.save(user);
        }
    }

    /**
     * This method only has the accesses to the super admin to revoke the admin role from the user. The user id will be passed as a parameter.
     * The method will check if the user is super admin or not. If the user is not super admin, then it will throw an exception.
     * If the user is super admin, then it will check if the user has the admin role or not. If the user does not have the admin role, then it will throw an exception.
     * If the user has the admin role, then it will remove the admin role from the user and save the user information.
     * The updated user information will be returned.
     *
     * @param userId The user id to revoke the admin role from the user.
     * @return The updated user information.
     */
    @Override
    public User revokeAdminRole(Long userId) {
        Authentication authentication = getAuthentication();
        if (authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals(RoleEnum.ROLE_SUPERADMIN.name()))) {
            throw new NotAuthorizedAccessException("Only super admin can revoke admin role from user.");
        }
        User user = getUserById(userId);
        Role adminRole = getRoleByName();

        if (!user.getRoles().contains(adminRole)) {
            throw new RoleNotFoundException("User does not have admin role.");
        } else {
            user.getRoles().remove(adminRole);
            user.setUpdatedAt(LocalDateTime.now());
            return userRepository.save(user);
        }
    }

    /**
     * This method only has the accesses to the super admin to create a super admin.
     * The userDto will be passed as a parameter.
     * The userDto will have the email, password, first name, and last name of the user.
     * The method will check if the user is super admin or not.
     * If the user is not super admin, then it will throw an exception.
     * If the user is super admin, then it will check if the user is already in the database or not.
     * If the user is already in the database, then it will get the user information.
     * If the user is not in the database, then it will create a new user and set the user information.
     * The super admin role will be added to the user and save the user information.
     * The created user information will be returned.
     *
     * @param userDto The userDto will have the email, password, first name, and last name of the user.
     * @return The created user information.
     */
    @Override
    public User createSuperAdmin(UserDto userDto) {
        Authentication authentication = getAuthentication();
        if (authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals(RoleEnum.ROLE_SUPERADMIN.name()))) {
            throw new NotAuthorizedAccessException("Only super admin can create super admin.");
        }
        User user;
        if (userRepository.existsByEmail(userDto.getEmail())) {
            user = userRepository.findByEmail(userDto.getEmail())
                    .orElseThrow(() -> new UserNotFoundException("User not found."));
        } else {
            user = new User();
            user.setEmail(userDto.getEmail());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setCreatedAt(LocalDateTime.now());
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setEnabled(true);
        }
        Role superAdminRole = roleRepository.findByRoleName(RoleEnum.ROLE_SUPERADMIN)
                .orElseThrow(() -> new RoleNotFoundException("Role not found."));
        user.getRoles().add(superAdminRole);
        return userRepository.save(user);
    }

    /**
     * This method only has the accesses to the super admin to delete the super admin role from the user.
     * The user id will be passed as a parameter.
     * The method will check if the user is super admin or not.
     * If the user is not super admin, then it will throw an exception.
     * If the user is super admin, then it will check if the user email is matched with the current principal name.
     * If the email is matched, then it will throw an exception.
     * Because the super admin cannot delete their own role.
     * If the email is not matched, then it will check if the user has the super admin role or not.
     * If the user does not have the super admin role, then it will throw an exception.
     * If the user has the super admin role, then it will check the super admin count in the database.
     * If the super admin count is less than 2, then it will throw an exception.
     * Because at least one super admin should be in the database.
     * If the super admin count is greater than 1, then it will delete the user information.
     *
     * @param userId The user id to delete the super admin role from the user.
     */
    @Override
    public void deleteSuperAdminRole(Long userId) {
        Authentication authentication = getAuthentication();
        if (authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals(RoleEnum.ROLE_SUPERADMIN.name()))) {
            throw new NotAuthorizedAccessException("Only super admin can delete super admin role from database.");
        }
        User user = getUserById(userId);
        String currentPrincipalName = authentication.getName();
        if (user.getEmail().equals(currentPrincipalName)) {
            throw new NotAuthorizedAccessException("Super admin cannot delete own role.");
        }
        Role superAdminRole = roleRepository.findByRoleName(RoleEnum.ROLE_SUPERADMIN)
                .orElseThrow(() -> new RoleNotFoundException("Role not found."));
        if (user.getRoles().contains(superAdminRole)) {
            long superAdminCount = userRepository.countByRolesContaining(superAdminRole);
            if (superAdminCount < 2) {
                throw new SuperAdminCountException("At least one super admin should be in the database.");
            }
            userRepository.delete(user);
        } else {
            throw new RoleNotFoundException("User does not have super admin role.");
        }
    }

    /**
     * This method only has the accesses to the super admin to revoke the super admin role from the user.
     * The user id will be passed as a parameter.
     * The method will check if the user is super admin or not.
     * If the user is not super admin, then it will throw an exception.
     * If the user is super admin, then it will check if the user has the super admin role or not.
     * If the user does not have the super admin role, then it will throw an exception.
     * If the user has the super admin role, then it will check if the user has only one role or not.
     * If the user has only one role, then it will throw an exception.
     * If the user has more than one role,
     * then it will remove the super admin role from the user and save the user information.
     * The updated user information will be returned.
     *
     * @param userId The user id to revoke the super admin role from the user.
     * @return The updated user information.
     */
    @Override
    public User revokeSuperAdminRole(Long userId) {
        Authentication authentication = getAuthentication();
        if (authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals(RoleEnum.ROLE_SUPERADMIN.name()))) {
            throw new NotAuthorizedAccessException("Only super admin can revoke super admin role from user.");
        }
        User user = getUserById(userId);
        Role superAdminRole = roleRepository.findByRoleName(RoleEnum.ROLE_SUPERADMIN)
                .orElseThrow(() -> new RoleNotFoundException("Role not found."));

        if (!user.getRoles().contains(superAdminRole) || user.getRoles().size() == 1) {
            throw new RoleNotFoundException("User does not have super admin role or it has only one role.");
        } else {
            user.getRoles().remove(superAdminRole);
            user.setUpdatedAt(LocalDateTime.now());
            return userRepository.save(user);
        }
    }

    /**
     * This method will get the user information based on the user id. The user id will be passed as a parameter.
     * If the user is not found with the given ID, then it will throw an exception.
     * The user information will be returned.
     *
     * @param userId The user id to get the user information.
     * @return The user information.
     */
    private User getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found with the given id " + userId);
        }
        return userOptional.get();
    }

    /**
     * This method will get the role based on the role name. The role name will be passed as a parameter.
     * If the role is not found with the given role name as ROLE_ADMIN, then it will throw an exception.
     * The role information will be returned.
     *
     * @return The role information.
     */
    private Role getRoleByName() {
        return roleRepository.findByRoleName(RoleEnum.ROLE_ADMIN)
                .orElseThrow(() -> new RoleNotFoundException("Role not found."));
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
