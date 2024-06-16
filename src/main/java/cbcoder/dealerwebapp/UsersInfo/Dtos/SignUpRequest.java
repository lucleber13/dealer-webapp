package cbcoder.dealerwebapp.UsersInfo.Dtos;

import cbcoder.dealerwebapp.UsersInfo.model.Role;

import java.util.Set;

public record SignUpRequest(String firstName, String lastName, String email, String password, Set<Role> roles) {
}
