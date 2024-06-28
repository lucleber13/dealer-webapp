package cbcoder.dealerwebapp.Cars.services;

import cbcoder.dealerwebapp.Cars.Dtos.CarDto;
import cbcoder.dealerwebapp.Cars.model.Car;
import cbcoder.dealerwebapp.UsersInfo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * <p>Copyright (c) 2024, Cleber Balbinote.</p>
 * <p>All rights reserved.</p>
 * <p>Licensed under the MIT License.</p>
 * <p>For full license text, please see the LICENSE file in the repo root or <a href="https://opensource.org/licenses/MI">...</a>T</p>
 * </br>
 * CarService interface for CarService implementation class
 * to implement the methods for CarService operations on the Car entity.
 * The methods are used to create, update, delete, get, and search for cars.
 * Also, the methods are used to get cars by model, buyer name, and user.
 *
 * @author Cleber Balbinote
 * @version 1.0
 * @see CarDto
 * @since 2024-06-27
 */
public interface CarService {
    CarDto createCar(CarDto carDto);

    CarDto updateCarToSold(Long carId, CarDto carDto);

    Page<CarDto> getAllStockCars(Pageable pageable);

    Page<CarDto> getAllSoldCars(Pageable pageable);

    Page<CarDto> getAllCars(Pageable pageable);

    void deleteCar(Long carId);

    List<CarDto> getCarByRegNumber(String regNumber);

    List<CarDto> getCarByChassisNumber(String chassisNumber);

    Car getCarById(Long carId);

    Page<CarDto> getCarByModel(Pageable pageable, String model);

    List<CarDto> getCarByBuyerName(String buyerName);

    Page<CarDto> getCarByUsers(Pageable pageable, User user);
}
