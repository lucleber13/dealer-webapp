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
 * Represents a Sold Car object with all its properties and relationships with other entities.
 * This is a record class used to create immutable objects.
 * It is used to create a Sold Car object with all its properties and relationships with other entities.
 * These objects are used to transfer data between the different layers of the application.
 * Hiding the implementation details of the Sold Car object from the other layers.
 *
 * @param carId
 * @param make
 * @param model
 * @param regNumber
 * @param chassisNumber
 * @param color
 * @param keyNumber
 * @param comments
 * @param buyerName
 * @param handoverDate
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
public record SoldCar(
        Long carId,
        String make,
        String model,
        String regNumber,
        String chassisNumber,
        String color,
        int keyNumber,
        String comments,
        String buyerName,
        LocalDateTime handoverDate,
        LocalDateTime dateCreated,
        LocalDateTime dateUpdated,
        CarStatus carStatus,
        Set<WorkshopServiceStatus> workshopServiceStatus,
        Set<ValeterStatus> valeterStatus,
        Set<User> users
) {
}
