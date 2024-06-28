package cbcoder.dealerwebapp.UsersInfo.services;

import cbcoder.dealerwebapp.UsersInfo.Dtos.JwtAuthResponse;
import cbcoder.dealerwebapp.UsersInfo.Dtos.RefreshTokenRequest;
import cbcoder.dealerwebapp.UsersInfo.Dtos.SignInRequest;
import cbcoder.dealerwebapp.UsersInfo.Dtos.SignUpRequest;
import cbcoder.dealerwebapp.UsersInfo.model.User;

/**
 * <p>Copyright (c) 2024, Cleber Balbinote.</p>
 * <p>All rights reserved.</p>
 * <p>Licensed under the MIT License.</p>
 * <p>For full license text, please see the LICENSE file in the repo root or <a href="https://opensource.org/licenses/MI">...</a>T</p>
 * </br>
 * UserAuthService interface provides the methods to register a new user, login, and refresh the JWT token.
 * The methods are implemented in the UserAuthServiceImpl class.
 *
 * @author Cleber Balbinote
 * @version 1.0
 * @since 2024-06-15
 */
public interface UserAuthService {

    User register(SignUpRequest signUpRequest);

    JwtAuthResponse login(SignInRequest signInRequest);

    JwtAuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
