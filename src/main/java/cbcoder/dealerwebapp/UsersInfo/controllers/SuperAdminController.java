package cbcoder.dealerwebapp.UsersInfo.controllers;

import cbcoder.dealerwebapp.UsersInfo.Dtos.UserDto;
import cbcoder.dealerwebapp.UsersInfo.model.User;
import cbcoder.dealerwebapp.UsersInfo.services.SuperAdminService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/superadmin")
@PreAuthorize("hasAuthority('ROLE_SUPERADMIN')")
public class SuperAdminController {

    private final SuperAdminService superAdminService;

    public SuperAdminController(SuperAdminService superAdminService) {
        this.superAdminService = superAdminService;
    }

    @PostMapping("/addAdminRole")
    public ResponseEntity<User> addAdminRole(@RequestBody @Valid UserDto userDto) {
        return ResponseEntity.ok(superAdminService.addAdminRole(userDto));
    }

    @PutMapping("/revokeAdminRole/{userId}")
    public ResponseEntity<User> revokeAdminRole(@PathVariable Long userId) {
        return ResponseEntity.ok(superAdminService.revokeAdminRole(userId));
    }

    @DeleteMapping("/deleteSuperAdminRole/{userId}")
    public ResponseEntity<Void> deleteSuperAdminRole(@PathVariable Long userId) {
        superAdminService.deleteSuperAdminRole(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/createSuperAdmin")
    public ResponseEntity<User> createSuperAdmin(@RequestBody @Valid UserDto userDto) {
        return ResponseEntity.ok(superAdminService.createSuperAdmin(userDto));
    }

    @PutMapping("/revokeSuperAdminRole/{userId}")
    public ResponseEntity<User> revokeSuperAdminRole(@PathVariable Long userId) {
        return ResponseEntity.ok(superAdminService.revokeSuperAdminRole(userId));
    }
}
