package vn.itech.com.quad_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.itech.com.quad_service.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    @Query(value = "SELECT u.id, u.username, u.email, u.address, u.phone, p.name FROM users AS u LEFT JOIN department AS p ON u.dept_name=p.id WHERE 1 = 1", nativeQuery = true)
    List<User> getuser();
}
