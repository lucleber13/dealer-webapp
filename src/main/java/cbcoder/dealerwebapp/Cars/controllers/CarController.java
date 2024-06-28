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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Copyright (c) 2024, Cleber Balbinote.</p>
 * <p>All rights reserved.</p>
 * <p>Licensed under the MIT License.</p>
 * <p>For full license text, please see the LICENSE file in the repo root or <a href="https://opensource.org/licenses/MI">...</a>T</p>
 * </br>
 * CarController class is a RestController class that handles all the requests related to the Car entity.
 * It has methods to create, update, delete, and get cars. It also has methods to get cars by different parameters.
 * It uses CarService to perform the operations.
 * The methods are secured using the @PreAuthorize annotation to allow only specific roles to access the methods.
 * The methods are also annotated with @CrossOrigin to allow requests from the frontend.
 *
 * @author Cleber Balbinote
 * @version 1.0
 * @since 2024-06-27
 */
@RestController
@RequestMapping("/cars")
@CrossOrigin(origins = "http://localhost:4200")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    /**
     * This method creates a car. It receives a CarDto object and returns a ResponseEntity with the created CarDto object.
     * The method is secured to allow only ADMIN and SALES roles to access it.
     * The method is annotated with @Valid to validate the CarDto object.
     *
     * @param carDto CarDto object
     * @return ResponseEntity with the created CarDto object created.
     */
    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALES')")
    public ResponseEntity<CarDto> createCar(@RequestBody @Valid CarDto carDto) {
        return ResponseEntity.ok(carService.createCar(carDto));
    }

    /**
     * This method updates a car.
     * It receives a carId and a CarDto object and returns a ResponseEntity with the updated CarDto object.
     * The method is secured to allow only ADMIN and SALES roles to access it.
     * The method is annotated with @Valid to validate the CarDto object.
     *
     * @param carId  Long carId
     * @param carDto CarDto object
     * @return ResponseEntity with the updated CarDto object.
     */
    @PutMapping("/update-to-sold/{carId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALES')")
    public ResponseEntity<CarDto> updateCarToSold(@PathVariable Long carId, @RequestBody @Valid CarDto carDto) {
        return ResponseEntity.ok(carService.updateCarToSold(carId, carDto));
    }

    /**
     * This method to get all the stock cars in the database.
     * The pageable object is used to set the page number, page size, and sort by parameters.
     * The method is secured to allow only ADMIN, SALES, VALETER, and WORKSHOP roles to access it.
     *
     * @return ResponseEntity with the updated CarDto object.
     */
    @GetMapping(value = "/all-stock-cars", produces = "application/json")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALES', 'VALETER', 'WORKSHOP')")
    ResponseEntity<Page<CarDto>> getAllStockCars(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "carId") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return ResponseEntity.ok(carService.getAllStockCars(pageable));
    }

    /**
     * This method to get all the sold cars in the database.
     * The pageable object is used to set the page number, page size, and sort by parameters.
     * The method is secured to allow only ADMIN, SALES, VALETER, and WORKSHOP roles to access it.
     *
     * @return ResponseEntity with the updated CarDto object.
     */
    @GetMapping(value = "/all-sold-cars", produces = "application/json")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALES', 'VALETER', 'WORKSHOP')")
    ResponseEntity<Page<CarDto>> getAllSoldCars(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "carId") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return ResponseEntity.ok(carService.getAllSoldCars(pageable));
    }

    /**
     * This method to get all the cars in the database.
     * The pageable object is used to set the page number, page size, and sort by parameters.
     * The method is secured to allow only ADMIN, SALES, VALETER, and WORKSHOP roles to access it.
     * This method will be the main method to show the table with all the cars in the frontend.
     *
     * @return ResponseEntity with the updated CarDto object.
     */
    @GetMapping(value = "/all-cars", produces = "application/json")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALES', 'VALETER', 'WORKSHOP')")
    ResponseEntity<Page<CarDto>> getAllCars(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "carId") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return ResponseEntity.ok(carService.getAllCars(pageable));
    }

    /**
     * This method deletes a car from the database.
     * It receives a carId and returns a ResponseEntity with no content.
     * The method is secured to allow only ADMIN and SALES roles to access it.
     * When the car cycle is completed, the car is deleted from the database.
     *
     * @param carId Long carId
     * @return ResponseEntity with no content.
     */
    @DeleteMapping("/delete/{carId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALES')")
    ResponseEntity<Car> deleteCar(@PathVariable Long carId) {
        carService.deleteCar(carId);
        return ResponseEntity.noContent().build();
    }

    /**
     * This method gets a car by the registration number.
     * It receives a regNumber and returns a ResponseEntity with a list of CarDto objects.
     * The method is secured to allow only ADMIN, SALES, VALETER, and WORKSHOP roles to access it.
     * It returns a list of CarDto objects because any letter or number can search the registration number.
     * For example, if the registration number is "ABC123", the search "ABC" will return the car.
     *
     * @param regNumber String regNumber (search parameter for registration number - can be any part of the registration number)
     * @return ResponseEntity with a list of CarDto objects or a unique registration number.
     */
    @GetMapping("/reg-number/{regNumber}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALES', 'VALETER', 'WORKSHOP')")
    public ResponseEntity<List<CarDto>> getCarByRegNumber(@PathVariable @Valid String regNumber) {
        return ResponseEntity.ok(carService.getCarByRegNumber(regNumber));
    }

    /**
     * This method gets a car by the chassis number.
     * It receives a chassisNumber and returns a ResponseEntity with a list of CarDto objects.
     * The method is secured to allow only ADMIN, SALES, VALETER, and WORKSHOP roles to access it.
     * It returns a list of CarDto objects because any letter or number can search the chassis number.
     * For example, if the chassis number is "ABC123", the search "ABC" will return the car.
     *
     * @param chassisNumber String chassisNumber (search parameter for chassis number - can be any part of the chassis number)
     * @return ResponseEntity with a list of CarDto objects or a unique chassis number.
     */
    @GetMapping("/chassis-number/{chassisNumber}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALES', 'VALETER', 'WORKSHOP')")
    public ResponseEntity<List<CarDto>> getCarByChassisNumber(@PathVariable @Valid String chassisNumber) {
        return ResponseEntity.ok(carService.getCarByChassisNumber(chassisNumber));
    }

    /**
     * This method gets a car by the carId.
     * It receives a carId and returns a ResponseEntity with a Car object.
     * The method is secured to allow only ADMIN, SALES, VALETER, and WORKSHOP roles to access it.
     *
     * @param carId Long carId (search parameter for carId)
     * @return ResponseEntity with a Car object.
     */
    @GetMapping("/car-by-id/{carId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALES', 'VALETER', 'WORKSHOP')")
    public ResponseEntity<Car> getCarById(@PathVariable Long carId) {
        return ResponseEntity.ok(carService.getCarById(carId));
    }

    /**
     * This method gets a car by the model.
     * It receives a model and returns a ResponseEntity with a list of CarDto objects.
     * The method is secured to allow only ADMIN, SALES, VALETER, and WORKSHOP roles to access it.
     * It returns a list of CarDto objects because any letter can be used to search the model.
     * For example, if the model is "Yaris", the search "Ya" will return the car.
     * The pageable object is used to set the page number, page size, and sort by parameters.
     *
     * @return ResponseEntity with a list of CarDto objects.
     */
    @GetMapping("/car-by-model/{model}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALES', 'VALETER', 'WORKSHOP')")
    public ResponseEntity<Page<CarDto>> getCarByModel(@PathVariable @Valid String model,
                                                      @RequestParam(defaultValue = "0") Integer pageNo,
                                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                                      @RequestParam(defaultValue = "carId") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return ResponseEntity.ok(carService.getCarByModel(pageable, model));
    }

    /**
     * This method gets a car by the buyer name.
     * It receives a buyerName and returns a ResponseEntity with a list of CarDto objects.
     * The method is secured to allow only ADMIN, SALES, VALETER, and WORKSHOP roles to access it.
     * It returns a list of CarDto objects because any letter can be used to search the buyer's name.
     * For example, if the buyer's name is "John Doe", the search "John" will return the car.
     *
     * @param buyerName String buyerName (search parameter for buyer's name - can be any part of the buyer's name)
     * @return ResponseEntity with a list of CarDto objects.
     */
    @GetMapping("/car-by-buyer/{buyerName}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALES', 'VALETER', 'WORKSHOP')")
    public ResponseEntity<List<CarDto>> getCarByBuyerName(@PathVariable @Valid String buyerName) {
        return ResponseEntity.ok(carService.getCarByBuyerName(buyerName));
    }

    /**
     * This method gets a car by the user.
     * Searching from the user ID, it will return all the cars that the user has.
     * It receives a user and returns a ResponseEntity with a list of CarDto objects.
     * The method is secured to allow only ADMIN, SALES, VALETER, and WORKSHOP roles to access it.
     * The pageable object is used to set the page number, page size, and sort by parameters.
     *
     * @param userId User userId (search parameter for userId)
     * @return ResponseEntity with a list of CarDto objects.
     */
    @GetMapping("/get-cars/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALES', 'VALETER', 'WORKSHOP')")
    public ResponseEntity<Page<CarDto>> getCarByUser(@PathVariable User userId,
                                                     @RequestParam(defaultValue = "0") Integer pageNo,
                                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                                     @RequestParam(defaultValue = "carId") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return ResponseEntity.ok(carService.getCarByUsers(pageable, userId));
    }
}
