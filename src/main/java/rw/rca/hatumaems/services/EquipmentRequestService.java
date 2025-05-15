package rw.rca.hatumaems.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.rca.hatumaems.models.Equipment;
import rw.rca.hatumaems.models.EquipmentRequest;
import rw.rca.hatumaems.models.User;
import rw.rca.hatumaems.repositories.EquipmentRequestRepository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for managing equipment request operations.
 * Handles business logic for creating, updating, and processing equipment requests.
 *
 * @author HATUMA EMS Team
 * @version 1.0
 */
@Service
public class EquipmentRequestService {
    @Autowired
    private EquipmentRequestRepository requestRepository;

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuditLogService auditLogService;

    /**
     * Creates a new equipment request and updates equipment quantity.
     * @param equipment the equipment being requested
     * @param requester the user making the request
     * @param purpose purpose of the request
     * @param duration duration in hours
     * @return created request object
     */
    @Transactional
    public EquipmentRequest createRequest(Equipment equipment, User requester, String purpose, Integer duration) {
        // Check if equipment is available
        if (!equipmentService.hasAvailableQuantity(equipment.getId(), 1)) {
            throw new RuntimeException("Equipment not available");
        }

        // Create request
        EquipmentRequest request = new EquipmentRequest();
        request.setEquipment(equipment);
        request.setRequester(requester);
        request.setPurpose(purpose);
        request.setDuration(duration);
        request.setStatus("PENDING");
        request.setRequestDate(LocalDateTime.now());

        // Save request
        request = requestRepository.save(request);

        // Create audit log
        auditLogService.logAllocation(
            equipment,
            requester,
            "Equipment Requested",
            String.format("New request for %s - %s", equipment.getName(), purpose)
        );

        return request;
    }

    /**
     * Updates request status and manages equipment quantity accordingly.
     * @param requestId ID of the request
     * @param newStatus new status to set
     * @param returnCondition condition of equipment if being returned
     * @return updated request object
     */
    @Transactional
    public EquipmentRequest updateRequestStatus(Long requestId, String newStatus, String returnCondition) {
        EquipmentRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        String oldStatus = request.getStatus();

        // Check if the status transition is valid
        boolean isValidTransition = switch (newStatus) {
            case "APPROVED" -> "PENDING".equals(oldStatus);
            case "REJECTED" -> "PENDING".equals(oldStatus);
            case "RETURNED" -> "APPROVED".equals(oldStatus);
            default -> false;
        };

        // If transition is not valid, return the request without changes
        if (!isValidTransition) {
            String message = switch (newStatus) {
                case "APPROVED" -> "Can only approve pending requests. Current status: " + oldStatus;
                case "REJECTED" -> "Can only reject pending requests. Current status: " + oldStatus;
                case "RETURNED" -> "Can only return approved requests. Current status: " + oldStatus;
                default -> "Invalid status: " + newStatus;
            };

            // Log the invalid transition attempt
            auditLogService.logAllocation(
                request.getEquipment(),
                request.getRequester(),
                "Status Update Failed",
                String.format("Failed to update status to %s - %s", newStatus, message)
            );

            return request; // Return unchanged request
        }

        request.setStatus(newStatus);

        switch (newStatus) {
            case "APPROVED":
                request.setApprovalDate(LocalDateTime.now());
                equipmentService.decreaseQuantity(request.getEquipment().getId(), 1);

                // Log approval
                auditLogService.logAllocation(
                    request.getEquipment(),
                    request.getRequester(),
                    "Request Approved",
                    String.format("Approved %s request for %s",
                        request.getEquipment().getName(),
                        request.getPurpose())
                );
                break;

            case "RETURNED":
                request.setReturnDate(LocalDateTime.now());
                request.setReturnCondition(returnCondition);
                equipmentService.increaseQuantity(request.getEquipment().getId(), 1, returnCondition);

                // Log return
                auditLogService.logAllocation(
                    request.getEquipment(),
                    request.getRequester(),
                    "Equipment Returned",
                    String.format("Returned %s - Condition: %s",
                        request.getEquipment().getName(),
                        returnCondition)
                );
                break;

            case "REJECTED":
                request.setApprovalDate(LocalDateTime.now());

                // Log rejection
                auditLogService.logAllocation(
                    request.getEquipment(),
                    request.getRequester(),
                    "Request Rejected",
                    String.format("Rejected %s request - Equipment unavailable",
                        request.getEquipment().getName())
                );
                break;
        }

        return requestRepository.save(request);
    }

    /**
     * Gets all requests for a specific user.
     * @param requester the user whose requests to find
     * @return list of user's requests
     */
    public List<EquipmentRequest> getRequestsByUser(User requester) {
        return requestRepository.findByRequester(requester);
    }

    /**
     * Gets all requests with a specific status.
     * @param status the status to filter by
     * @return list of requests with the specified status
     */
    public List<EquipmentRequest> getRequestsByStatus(String status) {
        return requestRepository.findByStatus(status);
    }

    /**
     * Gets a specific request by ID.
     * @param id the request ID
     * @return the request object
     * @throws RuntimeException if request not found
     */
    public EquipmentRequest getRequestById(Long id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
    }

    public List<EquipmentRequest> getAllRequests() {
        return requestRepository.findAll();
    }

    public List<EquipmentRequest> getPendingRequests() {
        return requestRepository.findByStatus("PENDING");
    }
}
