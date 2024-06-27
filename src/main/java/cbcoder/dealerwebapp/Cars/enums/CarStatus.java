package cbcoder.dealerwebapp.Cars.enums;

public enum CarStatus {
    SOLD("sold"),
    STOCK("stock");

    private final String value;

    CarStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
