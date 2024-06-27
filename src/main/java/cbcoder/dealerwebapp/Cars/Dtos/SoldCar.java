package cbcoder.dealerwebapp.Cars.Dtos;

import cbcoder.dealerwebapp.Cars.enums.CarStatus;
import cbcoder.dealerwebapp.Cars.enums.ValeterStatus;
import cbcoder.dealerwebapp.Cars.enums.WorkshopServiceStatus;
import cbcoder.dealerwebapp.UsersInfo.model.User;

import java.time.LocalDateTime;
import java.util.Set;

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
