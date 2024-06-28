package cbcoder.dealerwebapp.Cars.enums;

/**
 * <p>Copyright (c) 2024, Cleber Balbinote.</p>
 * <p>All rights reserved.</p>
 * <p>Licensed under the MIT License.</p>
 * <p>For full license text, please see the LICENSE file in the repo root or <a href="https://opensource.org/licenses/MI">...</a>T</p>
 * </br>
 * Enum for the status of a workshop service
 * Created by Cleber on 27/06/2024.
 */
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
