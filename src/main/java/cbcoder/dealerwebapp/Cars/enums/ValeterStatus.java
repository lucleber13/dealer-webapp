package cbcoder.dealerwebapp.Cars.enums;

public enum ValeterStatus {
    VALET("Valet"),
    FULL_VALET("Full Valet"),
    POLISH("Polish"),
    SAFEGARD("Safegard"),
    MATS("Mats"),
    BOOT_LINER("Boot Liner"),
    SAFETY_KIT("Safety Kit");

    private final String status;

    ValeterStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
