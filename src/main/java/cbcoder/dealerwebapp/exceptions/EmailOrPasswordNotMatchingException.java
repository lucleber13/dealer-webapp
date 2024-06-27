package cbcoder.dealerwebapp.exceptions;

public class EmailOrPasswordNotMatchingException extends RuntimeException {
    public EmailOrPasswordNotMatchingException(String message) {
        super(message);
    }
}
