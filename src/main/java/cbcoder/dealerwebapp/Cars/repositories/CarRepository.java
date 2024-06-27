package cbcoder.dealerwebapp.Cars.repositories;

import cbcoder.dealerwebapp.Cars.enums.CarStatus;
import cbcoder.dealerwebapp.Cars.model.Car;
import cbcoder.dealerwebapp.UsersInfo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Optional<List<Car>> findByRegNumberContainingIgnoreCase(String regNumber);

    Optional<List<Car>> findByChassisNumberContainingIgnoreCase(String chassisNumber);

    Optional<Car> findByCarId(Long carId);

    Page<Car> findByModelContainingIgnoreCase(Pageable pageable, String model);

    Optional<List<Car>> findByBuyerNameContainingIgnoreCase(String buyerName);

    Page<Car> findAllByCarStatus(CarStatus carStatus, Pageable pageable);

    boolean existsByRegNumber(String regNumber);

    boolean existsByChassisNumber(String chassisNumber);

//    Optional<List<Car>> findAllByUsersContainingIgnoreCase(User user);

    Page<Car> findAllByUsersContainingIgnoreCase(Pageable pageable, User user);

}
