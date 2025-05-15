package rw.rca.hatumaems.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.rca.hatumaems.models.EquipmentRequest;
import rw.rca.hatumaems.models.User;
import java.util.List;

/**
 * Repository interface for EquipmentRequest entity operations.
 * Provides methods for CRUD operations and custom queries related to equipment requests.
 *
 * @author HATUMA EMS Team
 * @version 1.0
 */
public interface EquipmentRequestRepository extends JpaRepository<EquipmentRequest, Long> {
    /**
     * Finds all equipment requests made by a specific user.
     * @param requester the user whose requests to find
     * @return list of equipment requests made by the user
     */
    List<EquipmentRequest> findByRequester(User requester);

    /**
     * Finds all equipment requests with a specific status.
     * @param status the status to search for (e.g., PENDING, APPROVED, REJECTED, RETURNED)
     * @return list of equipment requests with the specified status
     */
    List<EquipmentRequest> findByStatus(String status);
}
