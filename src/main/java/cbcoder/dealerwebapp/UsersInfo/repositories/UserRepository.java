package cbcoder.dealerwebapp.UsersInfo.repositories;

import cbcoder.dealerwebapp.UsersInfo.model.Role;
import cbcoder.dealerwebapp.UsersInfo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * <p>Copyright (c) 2024, Cleber Balbinote.</p>
 * <p>All rights reserved.</p>
 * <p>Licensed under the MIT License.</p>
 * <p>For full license text, please see the LICENSE file in the repo root or <a href="https://opensource.org/licenses/MI">...</a>T</p>
 * </br>
 * UserRepository interface extends the JpaRepository interface.
 * It provides the methods to interact with the User entity in the database.
 * The methods are used to query the database for users by email, check if a user exists by email,
 * count the number of super admins in the database, and find a user by first name.
 *
 * @author Cleber Balbinote
 * @version 1.0
 * @see JpaRepository
 * @see User
 * @see Role
 * @since 2024-06-15
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    long countByRolesContaining(Role superAdminRole);

    Optional<User> findUserByFirstNameContainingIgnoreCase(String firstName);
}
