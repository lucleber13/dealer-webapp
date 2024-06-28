package cbcoder.dealerwebapp.UsersInfo.Dtos;

import cbcoder.dealerwebapp.UsersInfo.model.Role;

import java.util.Set;

/**
 * <p>Copyright (c) 2024, Cleber Balbinote.</p>
 * <p>All rights reserved.</p>
 * <p>Licensed under the MIT License.</p>
 * <p>For full license text, please see the LICENSE file in the repo root or <a href="https://opensource.org/licenses/MI">...</a>T</p>
 * </br>
 * SignUpRequest class is a DTO that represents the request body for the sign-up endpoint.
 * It contains the user's first name, last name, email, password, and roles.
 * The roles are a set of Role enums.
 *
 * @param firstName
 * @param lastName
 * @param email
 * @param password
 * @param roles
 * @author Cleber Balbinote
 * @version 1.0
 * @since 2024-06-15
 */
public record SignUpRequest(String firstName, String lastName, String email, String password, Set<Role> roles) {
}
