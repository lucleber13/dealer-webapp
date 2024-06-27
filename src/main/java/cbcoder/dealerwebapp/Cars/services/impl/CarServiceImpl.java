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

    private Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UserNotFoundException("User not found");
        }
        return authentication;
    }

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

    @Override
    public Page<CarDto> getAllStockCars(Pageable pageable) {
        Page<Car> car = carRepository.findAllByCarStatus(CarStatus.STOCK, pageable);
        return getCarDtos(car);
    }

    @Override
    public Page<CarDto> getAllSoldCars(Pageable pageable) {
        Page<Car> car = carRepository.findAllByCarStatus(CarStatus.SOLD, pageable);
        return getCarDtos(car);
    }

    private Page<CarDto> getCarDtos(Page<Car> car) {
        return car.map(car1 -> {
            CarDto carDto = modelMapper.map(car1, CarDto.class);
            User user = car1.getUsers().iterator().next();
            carDto.setUserId(user.getUserId());
            return carDto;
        });
    }


    @Override
    public Page<CarDto> getAllCars(Pageable pageable) {
        Page<Car> car = carRepository.findAll(pageable);
        return getCarDtos(car);
    }

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

    @Override
    public Car getCarById(Long carId) {
        return carRepository.findByCarId(carId)
                .orElseThrow(() -> new CarNotFoundException("Car not found with id: " + carId));
    }

    @Override
    public Page<CarDto> getCarByModel(Pageable pageable, String model) {
        if (model.isBlank()) {
            throw new CarNotFoundException("Please provide a model");
        }
        Page<Car> car = carRepository.findByModelContainingIgnoreCase(pageable, model);
        return getCarDtos(car);
    }

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

    @Override
    public Page<CarDto> getCarByUsers(Pageable pageable, User user) {
        if (user == null) {
            throw new UserNotFoundException("Please provide a user");
        }
        Page<Car> car = carRepository.findAllByUsersContainingIgnoreCase(pageable, user);
        return getCarDtos(car);
    }
}
