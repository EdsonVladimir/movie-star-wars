package org.esosa.spring.repository;
import org.esosa.spring.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Edson Sosa
 * @version 1.0
 * @since 2024
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
