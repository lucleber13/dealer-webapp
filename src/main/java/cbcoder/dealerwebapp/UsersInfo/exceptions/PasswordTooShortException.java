package cbcoder.dealerwebapp.UsersInfo.exceptions;

public class PasswordTooShortException extends RuntimeException {
	public PasswordTooShortException(String message) {
		super(message);
	}
}
