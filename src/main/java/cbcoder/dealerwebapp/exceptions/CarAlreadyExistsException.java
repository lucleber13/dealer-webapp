package cbcoder.dealerwebapp.exceptions;

import jakarta.persistence.EntityExistsException;

public class CarAlreadyExistsException extends EntityExistsException {
    public CarAlreadyExistsException(String s) {
        super(s);
    }
}
