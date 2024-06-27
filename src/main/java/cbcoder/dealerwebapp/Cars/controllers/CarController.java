package cbcoder.dealerwebapp.Cars.controllers;

import cbcoder.dealerwebapp.Cars.Dtos.CarDto;
import cbcoder.dealerwebapp.Cars.model.Car;
import cbcoder.dealerwebapp.Cars.services.CarService;
import cbcoder.dealerwebapp.UsersInfo.model.User;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/create")
    public ResponseEntity<CarDto> createCar(@RequestBody @Valid CarDto carDto) {
        return ResponseEntity.ok(carService.createCar(carDto));
    }

    @PutMapping("/update-to-sold/{carId}")
    public ResponseEntity<CarDto> updateCarToSold(@PathVariable Long carId, @RequestBody @Valid CarDto carDto) {
        return ResponseEntity.ok(carService.updateCarToSold(carId, carDto));
    }

    @GetMapping(value = "/all-stock-cars", produces = "application/json")
    ResponseEntity<Page<CarDto>> getAllStockCars(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "carId") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return ResponseEntity.ok(carService.getAllStockCars(pageable));
    }

    @GetMapping(value = "/all-sold-cars", produces = "application/json")
    ResponseEntity<Page<CarDto>> getAllSoldCars(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "carId") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return ResponseEntity.ok(carService.getAllSoldCars(pageable));
    }

    @GetMapping(value = "/all-cars", produces = "application/json")
    ResponseEntity<Page<CarDto>> getAllCars(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "carId") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return ResponseEntity.ok(carService.getAllCars(pageable));
    }

    @DeleteMapping("/delete/{carId}")
    ResponseEntity<?> deleteCar(@PathVariable Long carId) {
        carService.deleteCar(carId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reg-number/{regNumber}")
    public ResponseEntity<List<CarDto>> getCarByRegNumber(@PathVariable @Valid String regNumber) {
        return ResponseEntity.ok(carService.getCarByRegNumber(regNumber));
    }

    @GetMapping("/chassis-number/{chassisNumber}")
    public ResponseEntity<List<CarDto>> getCarByChassisNumber(@PathVariable @Valid String chassisNumber) {
        return ResponseEntity.ok(carService.getCarByChassisNumber(chassisNumber));
    }

    @GetMapping("/car-by-id/{carId}")
    public ResponseEntity<Car> getCarById(@PathVariable Long carId) {
        return ResponseEntity.ok(carService.getCarById(carId));
    }

    @GetMapping("/car-by-model/{model}")
    public ResponseEntity<Page<CarDto>> getCarByModel(@PathVariable @Valid String model,
                                                      @RequestParam(defaultValue = "0") Integer pageNo,
                                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                                      @RequestParam(defaultValue = "carId") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return ResponseEntity.ok(carService.getCarByModel(pageable, model));
    }

    @GetMapping("/car-by-buyer/{buyerName}")
    public ResponseEntity<List<CarDto>> getCarByBuyerName(@PathVariable @Valid String buyerName) {
        return ResponseEntity.ok(carService.getCarByBuyerName(buyerName));
    }

    @GetMapping("/get-cars/{user}")
    public ResponseEntity<Page<CarDto>> getCarByUser(@PathVariable User user,
                                                     @RequestParam(defaultValue = "0") Integer pageNo,
                                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                                     @RequestParam(defaultValue = "carId") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return ResponseEntity.ok(carService.getCarByUsers(pageable, user));
    }
}
