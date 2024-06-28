package cbcoder.dealerwebapp.UsersInfo.controllers;

import cbcoder.dealerwebapp.UsersInfo.Dtos.UserDto;
import cbcoder.dealerwebapp.UsersInfo.model.User;
import cbcoder.dealerwebapp.UsersInfo.services.SuperAdminService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>Copyright (c) 2024, Cleber Balbinote.</p>
 * <p>All rights reserved.</p>
 * <p>Licensed under the MIT License.</p>
 * <p>For full license text, please see the LICENSE file in the repo root or <a href="https://opensource.org/licenses/MI">...</a>T</p>
 * </br>
 * SuperAdminController class provides the REST API endpoints for the super admin operations.
 * The class is annotated with @RestController to indicate that it is a controller class.
 * The class is annotated with @RequestMapping to map the endpoints to the /superadmin path.
 * The class is annotated with @PreAuthorize to ensure that only super admins can access the endpoints.
 * This class is to manage the super admin operations like adding admin role,
 * revoking admin role, deleting super admin role, creating super admin, and revoking super admin role.
 * The class uses the SuperAdminService to perform the operations.
 *
 * @author Cleber Balbinote
 * @version 1.0
 * @see SuperAdminService
 * @since 2024-06-15
 */
@RestController
@RequestMapping("/superadmin")
@PreAuthorize("hasAuthority('ROLE_SUPERADMIN')")
public class SuperAdminController {

    private final SuperAdminService superAdminService;

    public SuperAdminController(SuperAdminService superAdminService) {
        this.superAdminService = superAdminService;
    }

    /**
     * Add an admin role to a user. The user must be a super admin to perform this operation.
     *
     * @param userDto The user DTO containing the user id and the role to be added.
     * @return The user entity with the admin role added.
     */
    @PostMapping("/addAdminRole")
    public ResponseEntity<User> addAdminRole(@RequestBody @Valid UserDto userDto) {
        return ResponseEntity.ok(superAdminService.addAdminRole(userDto));
    }

    /**
     * Revoke the admin role from a user. The user must be a super admin to perform this operation.
     *
     * @param userId The user id of the user whose admin role is to be revoked.
     * @return The user entity with the admin role revoked.
     */
    @PutMapping("/revokeAdminRole/{userId}")
    public ResponseEntity<User> revokeAdminRole(@PathVariable Long userId) {
        return ResponseEntity.ok(superAdminService.revokeAdminRole(userId));
    }

    /**
     * Delete the super admin user. The user must be a super admin to perform this operation.
     *
     * @param userId The user id of the user whose super admin role is to be deleted.
     * @return A response entity with no content.
     */
    @DeleteMapping("/deleteSuperAdminRole/{userId}")
    public ResponseEntity<Void> deleteSuperAdminRole(@PathVariable Long userId) {
        superAdminService.deleteSuperAdminRole(userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Create a super admin user. The user must be a super admin to perform this operation.
     *
     * @param userDto The user DTO containing the user details.
     * @return The user entity with the super admin role added.
     */
    @PostMapping("/createSuperAdmin")
    public ResponseEntity<User> createSuperAdmin(@RequestBody @Valid UserDto userDto) {
        return ResponseEntity.ok(superAdminService.createSuperAdmin(userDto));
    }

    /**
     * Revoke the super admin role from a user. The user must be a super admin to perform this operation.
     *
     * @param userId The user id of the user whose super admin role is to be revoked.
     * @return The user entity with the super admin role revoked.
     */
    @PutMapping("/revokeSuperAdminRole/{userId}")
    public ResponseEntity<User> revokeSuperAdminRole(@PathVariable Long userId) {
        return ResponseEntity.ok(superAdminService.revokeSuperAdminRole(userId));
    }
}
