package cbcoder.dealerwebapp.exceptions.handler;

import cbcoder.dealerwebapp.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Copyright (c) 2024, Cleber Balbinote.</p>
 * <p>All rights reserved.</p>
 * <p>Licensed under the MIT License.</p>
 * <p>For full license text, please see the LICENSE file in the repo root or <a href="https://opensource.org/licenses/MI">...</a>T</p>
 * </br>
 * Exception handler class to handle exceptions thrown by the application.
 * It is responsible for returning the appropriate HTTP status code and message to the client.
 *
 * @author Cleber Balbinote
 * @version 1.0
 * @see MethodArgumentNotValidException
 * @see UserNotFoundException
 * @see UserAlreadyExistsException
 * @see RoleNotFoundException
 * @see PasswordTooShortException
 * @see EmailOrPasswordNotMatchingException
 * @see UserNotEnabledException
 * @see EmailNotBindingException
 * @see OperationNotPermittedException
 * @see NotAuthorizedAccessException
 * @see RoleAlreadyAssignedException
 * @see SuperAdminCountException
 * @see CarAlreadyExistsException
 * @see CarNotFoundException
 * @since 2024-06-15
 */
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

    /**
     * Handle UserNotFoundException exception and return a message to the client.
     * The message is the exception message.
     * The HTTP status code is 404 (NOT FOUND).
     *
     * @param ex UserNotFoundException exception.
     * @return a map with the message of the exception.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public Map<String, String> handleUserNotFoundException(UserNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(MESSAGE, ex.getMessage());
        return errors;
    }

    /**
     * Handle UserAlreadyExistsException exception and return a message to the client.
     * The message is the exception message.
     * The HTTP status code is 409 (CONFLICT).
     *
     * @param ex UserAlreadyExistsException exception.
     * @return a map with the message of the exception.
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public Map<String, String> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(MESSAGE, ex.getMessage());
        return errors;
    }

    /**
     * Handle RoleNotFoundException exception and return a message to the client.
     * The message is the exception message.
     * The HTTP status code is 404 (NOT FOUND).
     *
     * @param ex RoleNotFoundException exception.
     * @return a map with the message of the exception.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RoleNotFoundException.class)
    public Map<String, String> handleRoleNotFoundException(RoleNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(MESSAGE, ex.getMessage());
        return errors;
    }

    /**
     * Handle PasswordTooShortException exception and return a message to the client.
     * The message is the exception message.
     * The HTTP status code is 411 (LENGTH REQUIRED).
     *
     * @param ex PasswordTooShortException exception.
     * @return a map with the message of the exception.
     */
    @ResponseStatus(HttpStatus.LENGTH_REQUIRED)
    @ExceptionHandler(PasswordTooShortException.class)
    public Map<String, String> handlePasswordTooShortException(PasswordTooShortException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(MESSAGE, ex.getMessage());
        return errors;
    }

    /**
     * Handle EmailOrPasswordNotMatchingException exception and return a message to the client.
     * The message is the exception message.
     * The HTTP status code is 400 (BAD REQUEST).
     *
     * @param ex EmailOrPasswordNotMatchingException exception.
     * @return a map with the message of the exception.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailOrPasswordNotMatchingException.class)
    public Map<String, String> handleEmailOrPasswordNotMatchingException(EmailOrPasswordNotMatchingException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(MESSAGE, ex.getMessage());
        return errors;
    }

    /**
     * Handle UserNotEnabledException exception and return a message to the client.
     * The message is the exception message.
     * The HTTP status code is 401 (UNAUTHORIZED).
     *
     * @param ex UserNotEnabledException exception.
     * @return a map with the message of the exception.
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UserNotEnabledException.class)
    public Map<String, String> handleUserNotEnabledException(UserNotEnabledException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(MESSAGE, ex.getMessage());
        return errors;
    }

    /**
     * Handle EmailNotBindingException exception and return a message to the client.
     * The message is the exception message.
     * The HTTP status code is 400 (BAD REQUEST).
     *
     * @param ex EmailNotBindingException exception.
     * @return a map with the message of the exception.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailNotBindingException.class)
    public Map<String, String> handleEmailNotBindingException(EmailNotBindingException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(MESSAGE, ex.getMessage());
        return errors;
    }

    /**
     * Handle OperationNotPermittedException exception and return a message to the client.
     * The message is the exception message.
     * The HTTP status code is 405 (METHOD NOT ALLOWED).
     *
     * @param ex OperationNotPermittedException exception.
     * @return a map with the message of the exception.
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(OperationNotPermittedException.class)
    public Map<String, String> handleOperationNotPermittedException(OperationNotPermittedException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(MESSAGE, ex.getMessage());
        return errors;
    }

    /**
     * Handle NotAuthorizedAccessException exception and return a message to the client.
     * The message is the exception message.
     * The HTTP status code is 401 (UNAUTHORIZED).
     *
     * @param ex NotAuthorizedAccessException exception.
     * @return a map with the message of the exception.
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotAuthorizedAccessException.class)
    public Map<String, String> handleNotAuthorizedAccessException(NotAuthorizedAccessException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(MESSAGE, ex.getMessage());
        return errors;
    }

    /**
     * Handle RoleAlreadyAssignedException exception and return a message to the client.
     * The message is the exception message.
     * The HTTP status code is 409 (CONFLICT).
     *
     * @param ex RoleAlreadyAssignedException exception.
     * @return a map with the message of the exception.
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(RoleAlreadyAssignedException.class)
    public Map<String, String> handleRoleAlreadyAssignedException(RoleAlreadyAssignedException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(MESSAGE, ex.getMessage());
        return errors;
    }

    /**
     * Handle SuperAdminCountException exception and return a message to the client.
     * The message is the exception message.
     * The HTTP status code is 400 (BAD REQUEST).
     *
     * @param ex SuperAdminCountException exception.
     * @return a map with the message of the exception.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SuperAdminCountException.class)
    public Map<String, String> handleSuperAdminCountException(SuperAdminCountException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(MESSAGE, ex.getMessage());
        return errors;
    }

    /**
     * Handle the CarAlreadyExistsException exception and return a message to the client.
     * The message is the exception message.
     * The HTTP status code is 409 (CONFLICT).
     *
     * @param ex CarAlreadyExistsException exception.
     * @return a map with the message of the exception.
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CarAlreadyExistsException.class)
    public Map<String, String> handleCarAlreadyExistsException(CarAlreadyExistsException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(MESSAGE, ex.getMessage());
        return errors;
    }

    /**
     * Handle the CarNotFoundException exception and return a message to the client.
     * The message is the exception message.
     * The HTTP status code is 404 (NOT FOUND).
     *
     * @param ex CarNotFoundException exception.
     * @return a map with the message of the exception.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CarNotFoundException.class)
    public Map<String, String> handleCarNotFoundException(CarNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(MESSAGE, ex.getMessage());
        return errors;
    }
}
