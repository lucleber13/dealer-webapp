package cbcoder.dealerwebapp.exceptions;

public class NotAuthorizedAccessException extends RuntimeException {
    public NotAuthorizedAccessException(String s) {
        super(s);
    }
}
