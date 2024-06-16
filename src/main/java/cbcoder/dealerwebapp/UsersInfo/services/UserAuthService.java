package cbcoder.dealerwebapp.UsersInfo.services;

import cbcoder.dealerwebapp.UsersInfo.Dtos.JwtAuthResponse;
import cbcoder.dealerwebapp.UsersInfo.Dtos.RefreshTokenRequest;
import cbcoder.dealerwebapp.UsersInfo.Dtos.SignInRequest;
import cbcoder.dealerwebapp.UsersInfo.Dtos.SignUpRequest;
import cbcoder.dealerwebapp.UsersInfo.model.User;

public interface UserAuthService {

	User register(SignUpRequest signUpRequest);

	JwtAuthResponse login(SignInRequest signInRequest);

	JwtAuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
