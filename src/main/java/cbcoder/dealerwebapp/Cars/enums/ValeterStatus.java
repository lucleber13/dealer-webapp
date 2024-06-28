package cbcoder.dealerwebapp.Cars.enums;

/**
 * <p>Copyright (c) 2024, Cleber Balbinote.</p>
 * <p>All rights reserved.</p>
 * <p>Licensed under the MIT License.</p>
 * <p>For full license text, please see the LICENSE file in the repo root or <a href="https://opensource.org/licenses/MI">...</a>T</p>
 * </br>
 * Enum for the status of the valet.
 * Created by Cleber 0n 27/06/2024.
 */
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
