/**
 * Repository interface for managing User entities in the database.
 * Provides methods for basic CRUD operations and custom queries.
 */
package rw.rca.hotelbookingsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.rca.hotelbookingsystem.models.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a user by their email address
     * @param email The email address to search for
     * @return Optional containing the user if found, empty otherwise
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks if a user exists with the given email address
     * @param email The email address to check
     * @return true if a user exists with the email, false otherwise
     */
    boolean existsByEmail(String email);
}
