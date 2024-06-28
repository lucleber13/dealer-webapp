package cbcoder.dealerwebapp.UsersInfo.Dtos;

/**
 * <p>Copyright (c) 2024, Cleber Balbinote.</p>
 * <p>All rights reserved.</p>
 * <p>Licensed under the MIT License.</p>
 * <p>For full license text, please see the LICENSE file in the repo root or <a href="https://opensource.org/licenses/MI">...</a>T</p>
 * </br>
 * JwtAuthResponse class is a DTO class that represents the response of the authentication process.
 *
 * @param token
 * @param refreshToken
 * @author Cleber Balbinote
 * @version 1.0
 * @since 2024-06-15
 */
public record JwtAuthResponse(String token, String refreshToken) {
}
