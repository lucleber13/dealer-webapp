package cbcoder.dealerwebapp.UsersInfo.Dtos;

/**
 * <p>Copyright (c) 2024, Cleber Balbinote.</p>
 * <p>All rights reserved.</p>
 * <p>Licensed under the MIT License.</p>
 * <p>For full license text, please see the LICENSE file in the repo root or <a href="https://opensource.org/licenses/MI">...</a>T</p>
 * </br>
 * RefreshTokenRequest class is a DTO class that represents the request body for the refresh token endpoint.
 * It contains a single field, refreshToken, which is the refresh token that is used to generate a new access token.
 *
 * @author Cleber Balbinote
 * @version 1.0
 * @since 2024-06-15
 */
public class RefreshTokenRequest {

    private String refreshToken;

    public RefreshTokenRequest() {
    }

    public RefreshTokenRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
