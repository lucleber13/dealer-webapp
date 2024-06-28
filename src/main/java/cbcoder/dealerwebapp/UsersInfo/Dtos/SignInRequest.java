package cbcoder.dealerwebapp.UsersInfo.Dtos;

/**
 * <p>Copyright (c) 2024, Cleber Balbinote.</p>
 * <p>All rights reserved.</p>
 * <p>Licensed under the MIT License.</p>
 * <p>For full license text, please see the LICENSE file in the repo root or <a href="https://opensource.org/licenses/MI">...</a>T</p>
 * </br>
 * SignInRequest class is a DTO that represents the request body for the sign-in endpoint.
 * It contains the email and password fields.
 *
 * @param email
 * @param password
 * @author Cleber Balbinote
 * @version 1.0
 * @since 2024-06-27
 */
public record SignInRequest(String email, String password) {
}
