package cbcoder.dealerwebapp.UsersInfo.repositories;

import cbcoder.dealerwebapp.UsersInfo.model.Role;
import cbcoder.dealerwebapp.UsersInfo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    long countByRolesContaining(Role superAdminRole);

    Optional<User> findUserByFirstNameContainingIgnoreCase(String firstName);
}
