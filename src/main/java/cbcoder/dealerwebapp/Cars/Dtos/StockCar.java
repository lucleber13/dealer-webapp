package cbcoder.dealerwebapp.Cars.Dtos;

import cbcoder.dealerwebapp.Cars.enums.CarStatus;
import cbcoder.dealerwebapp.Cars.enums.ValeterStatus;
import cbcoder.dealerwebapp.Cars.enums.WorkshopServiceStatus;
import cbcoder.dealerwebapp.UsersInfo.model.User;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * <p>Copyright (c) 2024, Cleber Balbinote.</p>
 * <p>All rights reserved.</p>
 * <p>Licensed under the MIT License.</p>
 * <p>For full license text, please see the LICENSE file in the repo root or <a href="https://opensource.org/licenses/MI">...</a>T</p>
 * </br>
 * StockCar DTO class to represent the StockCar entity in the database with all the necessary fields and their types.
 * This class is used to transfer the data between the service and the controller.
 * It is also used to represent the data in the database.
 *
 * @param carId
 * @param make
 * @param model
 * @param regNumber
 * @param chassisNumber
 * @param color
 * @param keyNumber
 * @param comments
 * @param dateCreated
 * @param dateUpdated
 * @param carStatus
 * @param workshopServiceStatus
 * @param valeterStatus
 * @param users
 * @author Cleber Balbinote
 * @version 1.0
 * @since 2024-06-27
 */
public record StockCar(
        Long carId,
        String make,
        String model,
        String regNumber,
        String chassisNumber,
        String color,
        int keyNumber,
        String comments,
        LocalDateTime dateCreated,
        LocalDateTime dateUpdated,
        CarStatus carStatus,
        Set<WorkshopServiceStatus> workshopServiceStatus,
        Set<ValeterStatus> valeterStatus,
        Set<User> users
) {
}
