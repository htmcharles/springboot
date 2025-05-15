package rw.rca.hatumaems.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import rw.rca.hatumaems.models.User;
import java.util.Optional;

/**
 * Repository interface for User entity operations.
 * Provides methods for CRUD operations and custom queries related to User entities.
 *
 * @author HATUMA EMS Team
 * @version 1.0
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a user by their email address.
     * @param email the email address to search for
     * @return an Optional containing the user if found, empty otherwise
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks if a user with the given email exists.
     * @param email the email address to check
     * @return true if a user with the email exists, false otherwise
     */
    boolean existsByEmail(String email);
}
