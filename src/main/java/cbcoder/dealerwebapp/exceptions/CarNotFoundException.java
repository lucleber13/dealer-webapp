package cbcoder.dealerwebapp.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class CarNotFoundException extends EntityNotFoundException {
    public CarNotFoundException(String s) {
        super(s);
    }
}
