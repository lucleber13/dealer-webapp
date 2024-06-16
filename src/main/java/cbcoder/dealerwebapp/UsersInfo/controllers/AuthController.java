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

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final UserAuthServiceImpl userAuthService;

	public AuthController(UserAuthServiceImpl userAuthService) {
		this.userAuthService = userAuthService;
	}

	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody @Valid SignUpRequest signUpRequest) {
		URI uri = URI.create(
				ServletUriComponentsBuilder
						.fromCurrentContextPath()
						.path("/api/v1/auth/register")
						.toUriString());
		return ResponseEntity.created(uri).body(userAuthService.register(signUpRequest));
	}

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> login(@RequestBody SignInRequest signInRequest) {
		return ResponseEntity.ok(userAuthService.login(signInRequest));
	}

	@PostMapping("/refresh/token")
	public ResponseEntity<JwtAuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
		return ResponseEntity.ok(userAuthService.refreshToken(refreshTokenRequest));
	}
}
