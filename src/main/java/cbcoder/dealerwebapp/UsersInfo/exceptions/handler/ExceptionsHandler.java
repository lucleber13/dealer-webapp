package cbcoder.dealerwebapp.UsersInfo.exceptions.handler;

import cbcoder.dealerwebapp.UsersInfo.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionsHandler {

	private static final String MESSAGE = "message";

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult()
				.getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		return errors;
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(UserNotFoundException.class)
	public Map<String, String> handleUserNotFoundException(UserNotFoundException ex) {
		Map<String, String> errors = new HashMap<>();
		errors.put(MESSAGE, ex.getMessage());
		return errors;
	}

	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(UserAlreadyExistsException.class)
	public Map<String, String> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
		Map<String, String> errors = new HashMap<>();
		errors.put(MESSAGE, ex.getMessage());
		return errors;
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(RoleNotFoundException.class)
	public Map<String, String> handleRoleNotFoundException(RoleNotFoundException ex) {
		Map<String, String> errors = new HashMap<>();
		errors.put(MESSAGE, ex.getMessage());
		return errors;
	}

	@ResponseStatus(HttpStatus.LENGTH_REQUIRED)
	@ExceptionHandler(PasswordTooShortException.class)
	public Map<String, String> handlePasswordTooShortException(PasswordTooShortException ex) {
		Map<String, String> errors = new HashMap<>();
		errors.put(MESSAGE, ex.getMessage());
		return errors;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(EmailOrPasswordNotMatchingException.class)
	public Map<String, String> handleEmailOrPasswordNotMatchingException(EmailOrPasswordNotMatchingException ex) {
		Map<String, String> errors = new HashMap<>();
		errors.put(MESSAGE, ex.getMessage());
		return errors;
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UserNotEnabledException.class)
	public Map<String, String> handleUserNotEnabledException(UserNotEnabledException ex) {
		Map<String, String> errors = new HashMap<>();
		errors.put(MESSAGE, ex.getMessage());
		return errors;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(EmailNotBindingException.class)
	public Map<String, String> handleEmailNotBindingException(EmailNotBindingException ex) {
		Map<String, String> errors = new HashMap<>();
		errors.put(MESSAGE, ex.getMessage());
		return errors;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(OperationNotPermittedException.class)
	public Map<String, String> handleOperationNotPermittedException(OperationNotPermittedException ex) {
		Map<String, String> errors = new HashMap<>();
		errors.put(MESSAGE, ex.getMessage());
		return errors;
	}
}
