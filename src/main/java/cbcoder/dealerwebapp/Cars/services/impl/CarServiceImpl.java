package cbcoder.dealerwebapp.Cars.services.impl;

import cbcoder.dealerwebapp.Cars.Dtos.CarDto;
import cbcoder.dealerwebapp.Cars.enums.CarStatus;
import cbcoder.dealerwebapp.Cars.model.Car;
import cbcoder.dealerwebapp.Cars.repositories.CarRepository;
import cbcoder.dealerwebapp.Cars.services.CarService;
import cbcoder.dealerwebapp.UsersInfo.model.User;
import cbcoder.dealerwebapp.UsersInfo.repositories.UserRepository;
import cbcoder.dealerwebapp.exceptions.CarAlreadyExistsException;
import cbcoder.dealerwebapp.exceptions.CarNotFoundException;
import cbcoder.dealerwebapp.exceptions.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * <p>Copyright (c) 2024, Cleber Balbinote.</p>
 * <p>All rights reserved.</p>
 * <p>Licensed under the MIT License.</p>
 * <p>For full license text, please see the LICENSE file in the repo root or <a href="https://opensource.org/licenses/MI">...</a>T</p>
 * </br>
 * CarServiceImpl class implements the CarService interface.
 * It provides the implementation for the methods declared in the CarService interface.
 * Use the CarRepository and UserRepository
 * to interact with the database and the ModelMapper to map the entities to DTOs and vice versa.
 * The methods are annotated with @Transactional to ensure that the operations are atomic.
 *
 * @author Cleber Balbinote
 * @version 1.0
 * @see CarService
 * @see CarRepository
 * @see UserRepository
 * @see ModelMapper
 * @since 2024-06-27
 */
@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public CarServiceImpl(CarRepository carRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Create a car and save it to the database.
     * Get the authenticated user and add it to the car.
     * Save the car and the user to the database.
     * Map the saved car to a CarDto and return it.
     * If the car already exists with the reg number or chassis number, throw an exception.
     * If the user is not found, throw an exception.
     * If the user is not authenticated, throw an exception.
     * If the car is saved successfully, return the CarDto.
     *
     * @param carDto - the car details to be created.
     * @return CarDto - the created car.
     * @throws UserNotFoundException     if the user is not found in the database.
     * @throws CarAlreadyExistsException if the car already exists with the reg number or chassis number.
     */
    @Override
    @Transactional
    public CarDto createCar(cbcoder.dealerwebapp.Cars.Dtos.CarDto carDto) {
        Car car = modelMapper.map(carDto, Car.class);
        var authentication = getAuthentication();
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (carRepository.existsByRegNumber(car.getRegNumber())) {
            throw new CarAlreadyExistsException("Car already exists with reg number: " + car.getRegNumber());
        }
        if (carRepository.existsByChassisNumber(car.getChassisNumber())) {
            throw new CarAlreadyExistsException("Car already exists with chassis number: " + car.getChassisNumber());
        }
        car.getUsers().add(user);
        Car savedCar = carRepository.save(car);
        user.getCars().add(savedCar);
        userRepository.save(user);
        CarDto carDto1 = modelMapper.map(savedCar, CarDto.class);
        if (!car.getUsers().isEmpty()) {
            carDto1.setUserId(car.getUsers().iterator().next().getUserId());
        }
        return carDto1;

    }

    /**
     * This private method gets the authenticated user from the SecurityContextHolder.
     * If the user is not found, it throws a UserNotFoundException.
     * If the user is found, it returns the authenticated user.
     *
     * @return Authentication - the authenticated user.
     * @throws UserNotFoundException if the user is not found in the database.
     */
    private Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UserNotFoundException("User not found");
        }
        return authentication;
    }

    /**
     * Update a car from the stock to sold status.
     * Get the authenticated user and add it to the car.
     * Entry the handover date, buyer name, workshop service status, valeter status, comments, and date updated.
     * Save the car and the user to the database.
     * Map the saved car to a CarDto and return it.
     * If the car is not found, throw an exception.
     * If the user is not found, throw an exception.
     * If the user is not authenticated, throw an exception.
     * If the car is saved successfully, return the CarDto.
     *
     * @param carId  - the car id to be updated.
     * @param carDto - the car details to be updated.
     * @return CarDto - the updated car.
     * @throws UserNotFoundException if the user is not found in the database.
     * @throws CarNotFoundException  if the car is not found in the database.
     */
    @Override
    public CarDto updateCarToSold(Long carId, CarDto carDto) {
        var authentication = getAuthentication();
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Optional<Car> optionalCar = carRepository.findByCarId(carId);
        if (optionalCar.isEmpty()) {
            throw new CarNotFoundException("Car not found with id: " + carId);
        } else {
            Car car = optionalCar.get();
            car.setHandoverDate(carDto.getHandoverDate());
            car.setCarStatus(CarStatus.SOLD);
            car.setBuyerName(carDto.getBuyerName());
            car.setDateUpdated(LocalDateTime.now());
            car.setWorkshopServiceStatus(carDto.getWorkshopServiceStatus());
            car.setValeterStatus(carDto.getValeterStatus());
            car.setComments(carDto.getComments());
            car.getUsers().clear();
            car.getUsers().add(user);
            Car savedCar = carRepository.save(car);
            user.getCars().add(savedCar);
            userRepository.save(user);
            CarDto updatedCarDto = modelMapper.map(savedCar, CarDto.class);
            updatedCarDto.setUserId(user.getUserId());
            return updatedCarDto;
        }
    }

    /**
     * Get all the cars from the database with the stock status.
     * Map the cars to CarDto and return them.
     * If the cars are not found, return an empty list.
     * In this method, we use the CarDto to return the cars with the userId of the user who created the car.
     * Avoiding showing the user's details in the response.
     *
     * @param pageable - the pagination information for the cars.
     * @return Page<CarDto> - the cars with the stock status.
     */
    @Override
    public Page<CarDto> getAllStockCars(Pageable pageable) {
        Page<Car> car = carRepository.findAllByCarStatus(CarStatus.STOCK, pageable);
        return getCarDtos(car);
    }

    /**
     * Get all the cars from the database with the sold status.
     * Map the cars to CarDto and return them.
     * If the cars are not found, return an empty list.
     * In this method, we use the CarDto to return the cars with the userId of the user who created the car.
     * Avoiding showing the user's details in the response.
     *
     * @param pageable - the pagination information for the cars.
     * @return Page<CarDto> - the cars with the sold status.
     */
    @Override
    public Page<CarDto> getAllSoldCars(Pageable pageable) {
        Page<Car> car = carRepository.findAllByCarStatus(CarStatus.SOLD, pageable);
        return getCarDtos(car);
    }

    /**
     * This private method maps the cars to CarDto and returns them.
     * In this method, we're avoiding duplication of code by mapping the cars to CarDto.
     *
     * @return Page<CarDto> - the cars with the workshop service status.
     */
    private Page<CarDto> getCarDtos(Page<Car> car) {
        return car.map(car1 -> {
            CarDto carDto = modelMapper.map(car1, CarDto.class);
            User user = car1.getUsers().iterator().next();
            carDto.setUserId(user.getUserId());
            return carDto;
        });
    }

    /**
     * Get all the cars from the database.
     * Map the cars to CarDto and return them.
     *
     * @param pageable - the pagination information for the cars.
     * @return Page<CarDto> - the cars.
     */
    @Override
    public Page<CarDto> getAllCars(Pageable pageable) {
        Page<Car> car = carRepository.findAll(pageable);
        return getCarDtos(car);
    }

    /**
     * Delete a car from the database.
     * If the car is not found, throw an exception.
     * If the car is found, remove the user from the car and delete the car.
     * It deletes the car assigned to the user's relationship table.
     *
     * @param carId - the car id to be deleted.
     * @throws CarNotFoundException if the car is not found in the database.
     */
    @Override
    @Transactional
    public void deleteCar(Long carId) {
        Optional<Car> optionalCar = carRepository.findByCarId(carId);
        if (optionalCar.isEmpty()) {
            throw new CarNotFoundException("Car not found with id: " + carId);
        } else {
            Car car = optionalCar.get();
            car.getUsers().clear();
            carRepository.delete(car);
        }
    }

    /**
     * Get a car from the database by the reg number.
     * Map the car to CarDto and return it.
     * If the car is not found, throw an exception.
     *
     * @param regNumber - the reg number of the car. (e.g., ABC123)
     * @return List<CarDto> - the car with the reg number.
     * @throws CarNotFoundException if the car is not found in the database.
     */
    @Override
    public List<CarDto> getCarByRegNumber(String regNumber) {
        if (regNumber.isBlank()) {
            throw new CarNotFoundException("Please provide a reg number");
        }
        return carRepository.findByRegNumberContainingIgnoreCase(regNumber)
                .map(cars -> {
                    return cars.stream().map(car -> {
                        CarDto carDto = modelMapper.map(car, CarDto.class);
                        if (!car.getUsers().isEmpty()) {
                            carDto.setUserId(car.getUsers().iterator().next().getUserId());
                        }
                        return carDto;
                    }).toList();
                }).orElseThrow(() -> new CarNotFoundException("Car not found with reg number: " + regNumber));
    }

    /**
     * Get a car from the database by the chassis number.
     * Map the car to CarDto and return it.
     * If the car is not found, throw an exception.
     *
     * @param chassisNumber - the chassis number of the car. (e.g., 12PHV345OPH6789)
     * @return List<CarDto> - the car with the chassis number.
     * @throws CarNotFoundException if the car is not found in the database.
     */
    @Override
    public List<CarDto> getCarByChassisNumber(String chassisNumber) {
        if (chassisNumber.isBlank()) {
            throw new CarNotFoundException("Please provide a chassis number");
        }
        return carRepository.findByChassisNumberContainingIgnoreCase(chassisNumber)
                .map(cars -> {
                    return cars.stream().map(car -> {
                        CarDto carDto = modelMapper.map(car, CarDto.class);
                        if (!car.getUsers().isEmpty()) {
                            carDto.setUserId(car.getUsers().iterator().next().getUserId());
                        }
                        return carDto;
                    }).toList();
                }).orElseThrow(() -> new CarNotFoundException("Car not found with chassis number: " + chassisNumber));
    }

    /**
     * Get a car from the database by the model.
     * Map the car to CarDto and return it.
     * If the car is not found, throw an exception.
     *
     * @param carId - the car id to be retrieved.
     * @return Car - the car with the id.
     * @throws CarNotFoundException if the car is not found in the database.
     */
    @Override
    public Car getCarById(Long carId) {
        return carRepository.findByCarId(carId)
                .orElseThrow(() -> new CarNotFoundException("Car not found with id: " + carId));
    }

    /**
     * Get a car from the database by the model.
     * Map the car to CarDto and return it.
     * If the car is not found, throw an exception.
     *
     * @param model - the model of the car. (e.g., Yaris)
     * @return List<CarDto> - the car with the model.
     * @throws CarNotFoundException if the car is not found in the database.
     */
    @Override
    public Page<CarDto> getCarByModel(Pageable pageable, String model) {
        if (model.isBlank()) {
            throw new CarNotFoundException("Please provide a model");
        }
        Page<Car> car = carRepository.findByModelContainingIgnoreCase(pageable, model);
        return getCarDtos(car);
    }

    /**
     * Get the cars or a car from the database by the buyer name.
     * Map the car to CarDto and return it.
     * If the car is not found, throw an exception.
     *
     * @param buyerName - the make of the car. (e.g., Toyota)
     * @return List<CarDto> - the car with the make.
     * @throws CarNotFoundException if the car is not found in the database.
     */
    @Override
    public List<CarDto> getCarByBuyerName(String buyerName) {
        if (buyerName.isBlank()) {
            throw new CarNotFoundException("Please provide a buyer name");
        }
        return carRepository.findByBuyerNameContainingIgnoreCase(buyerName)
                .map(cars -> {
                    return cars.stream().map(car -> {
                        CarDto carDto = modelMapper.map(car, CarDto.class);
                        if (!car.getUsers().isEmpty()) {
                            carDto.setUserId(car.getUsers().iterator().next().getUserId());
                        }
                        return carDto;
                    }).toList();
                }).orElseThrow(() -> new CarNotFoundException("Car not found with buyer name: " + buyerName));
    }

    /**
     * Get the car's assigned from the database by the user.
     * Map the car to CarDto and return it.
     * If the car is not found, throw an exception.
     *
     * @param pageable - the pagination information for the cars.
     * @param user     - the user to be retrieved.
     * @return Page<CarDto> - a list of cars with assigned user.
     * @throws UserNotFoundException if the user is not found in the database.
     */
    @Override
    public Page<CarDto> getCarByUsers(Pageable pageable, User user) {
        if (user == null) {
            throw new UserNotFoundException("Please provide a user");
        }
        Page<Car> car = carRepository.findAllByUsersContainingIgnoreCase(pageable, user);
        return getCarDtos(car);
    }
}
