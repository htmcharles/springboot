package rw.rca.hatumaems.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.rca.hatumaems.models.Equipment;
import java.util.List;

/**
 * Repository interface for Equipment entity operations.
 * Provides methods for CRUD operations and custom queries related to Equipment entities.
 *
 * @author HATUMA EMS Team
 * @version 1.0
 */
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    /**
     * Finds all equipment items with a specific status.
     * @param status the status to search for (e.g., AVAILABLE, IN_USE, MAINTENANCE)
     * @return list of equipment items with the specified status
     */
    List<Equipment> findByStatus(String status);

    /**
     * Finds all equipment items of a specific type.
     * @param type the type/category to search for
     * @return list of equipment items of the specified type
     */
    List<Equipment> findByType(String type);
}
