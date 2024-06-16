package cbcoder.dealerwebapp.UsersInfo.repositories;

import cbcoder.dealerwebapp.UsersInfo.model.Role;
import cbcoder.dealerwebapp.UsersInfo.model.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

	Optional<Role> findByRoleName(RoleEnum roleName);

	Optional<Role> findByRoleId(int roleId);

}
