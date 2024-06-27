package cbcoder.dealerwebapp.Cars.services;

import cbcoder.dealerwebapp.Cars.Dtos.CarDto;
import cbcoder.dealerwebapp.Cars.model.Car;
import cbcoder.dealerwebapp.UsersInfo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

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
