package cbcoder.dealerwebapp.UsersInfo.repositories;

import cbcoder.dealerwebapp.UsersInfo.model.Role;
import cbcoder.dealerwebapp.UsersInfo.model.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * <p>Copyright (c) 2024, Cleber Balbinote.</p>
 * <p>All rights reserved.</p>
 * <p>Licensed under the MIT License.</p>
 * <p>For full license text, please see the LICENSE file in the repo root or <a href="https://opensource.org/licenses/MI">...</a>T</p>
 * </br>
 * RoleRepository interface extends the JpaRepository interface.
 * It provides the methods to interact with the database.
 * The methods are used to query the database for roles.
 *
 * @author Cleber Balbinote
 * @version 1.0
 * @see JpaRepository
 * @see Role
 * @see RoleEnum
 * @since 2024-06-27
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByRoleName(RoleEnum roleName);

    int countRoleByRoleName(RoleEnum roleName);
}
