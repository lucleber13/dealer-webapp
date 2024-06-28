package cbcoder.dealerwebapp.UsersInfo.controllers;

import cbcoder.dealerwebapp.UsersInfo.Dtos.JwtAuthResponse;
import cbcoder.dealerwebapp.UsersInfo.Dtos.RefreshTokenRequest;
import cbcoder.dealerwebapp.UsersInfo.Dtos.SignInRequest;
import cbcoder.dealerwebapp.UsersInfo.Dtos.SignUpRequest;
import cbcoder.dealerwebapp.UsersInfo.model.User;
import cbcoder.dealerwebapp.UsersInfo.services.impl.UserAuthServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * <p>Copyright (c) 2024, Cleber Balbinote.</p>
 * <p>All rights reserved.</p>
 * <p>Licensed under the MIT License.</p>
 * <p>For full license text, please see the LICENSE file in the repo root or <a href="https://opensource.org/licenses/MI">...</a>T</p>
 * </br>
 * AuthController class provides the endpoints for the authentication process.
 * It provides the endpoints for registering a new user, logging in, and refreshing the token.
 * The class is annotated with @RestController and @RequestMapping to define the base path for the endpoints.
 * The class has a constructor that receives an instance of the UserAuthServiceImpl class.
 * The register method receives a SignUpRequest object, validates it, and returns the created user.
 * The login method receives a SignInRequest object and returns the JWT token.
 * The refreshToken method receives a RefreshTokenRequest object and returns a new JWT token.
 *
 * @author Cleber Balbinote
 * @version 1.0
 * @see UserAuthServiceImpl
 * @see SignUpRequest
 * @see SignInRequest
 * @see RefreshTokenRequest
 * @since 2024-06-15
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserAuthServiceImpl userAuthService;

    public AuthController(UserAuthServiceImpl userAuthService) {
        this.userAuthService = userAuthService;
    }

    /**
     * Register a new user and return the created user.
     * The method receives a SignUpRequest object, validates it, and returns the created user.
     * The URI of the created user is returned in the response header with http status code 201 (CREATED).
     *
     * @param signUpRequest the SignUpRequest object containing the user details to be registered.
     * @return the ResponseEntity containing the created user and the URI of the created user.
     */
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid SignUpRequest signUpRequest) {
        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/api/v1/auth/register")
                        .toUriString());
        return ResponseEntity.created(uri).body(userAuthService.register(signUpRequest));
    }

    /**
     * Login the user and return the JWT token.
     * The method receives a SignInRequest object and returns the JWT token.
     * The JWT token is returned in the response body with http status code 200 (OK).
     *
     * @param signInRequest the SignInRequest object containing the user details to be logged in.
     * @return the ResponseEntity containing the JWT token.
     */
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(userAuthService.login(signInRequest));
    }

    /**
     * Refresh the JWT token and return the new JWT token.
     * The method receives a RefreshTokenRequest object and returns a new JWT token.
     * The new JWT token is returned in the response body with http status code 200 (OK).
     *
     * @param refreshTokenRequest the RefreshTokenRequest object containing the refresh token.
     * @return the ResponseEntity containing the new JWT token.
     */
    @PostMapping("/refresh/token")
    public ResponseEntity<JwtAuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(userAuthService.refreshToken(refreshTokenRequest));
    }
}
