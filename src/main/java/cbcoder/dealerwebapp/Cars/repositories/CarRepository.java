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

/**
 * <p>Copyright (c) 2024, Cleber Balbinote.</p>
 * <p>All rights reserved.</p>
 * <p>Licensed under the MIT License.</p>
 * <p>For full license text, please see the LICENSE file in the repo root or <a href="https://opensource.org/licenses/MI">...</a>T</p>
 * </br>
 * CarRepository interface extends JpaRepository for CRUD operations on Car entity in the database.
 * It also contains custom methods for searching cars by regNumber, chassisNumber, model, buyerName, carStatus and user.
 * It also contains methods to check if a car with a given regNumber or chassisNumber exists in the database.
 *
 * @author Cleber Balbinote
 * @version 1.0
 * @since 2024-06-27
 */

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

    Page<Car> findAllByUsersContainingIgnoreCase(Pageable pageable, User user);

}
