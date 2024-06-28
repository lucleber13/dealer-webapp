package cbcoder.dealerwebapp.Cars.enums;

/**
 * <p>Copyright (c) 2024, Cleber Balbinote.</p>
 * <p>All rights reserved.</p>
 * <p>Licensed under the MIT License.</p>
 * <p>For full license text, please see the LICENSE file in the repo root or <a href="https://opensource.org/licenses/MI">...</a>T</p>
 * </br>
 * Enum for Car Status (sold or in stock)
 * Created by Cleber on 27/06/2024.
 */
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
