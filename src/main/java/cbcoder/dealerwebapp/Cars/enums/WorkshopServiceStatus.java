package cbcoder.dealerwebapp.Cars.enums;

public enum WorkshopServiceStatus {
    SERVICE("Service"),
    REPAIR("Repair"),
    MOT("MOT");

    private final String status;

    WorkshopServiceStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
