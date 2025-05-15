package rw.rca.hatumaems.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.rca.hatumaems.models.Equipment;
import rw.rca.hatumaems.models.EquipmentRequest;
import rw.rca.hatumaems.models.User;
import rw.rca.hatumaems.services.EquipmentRequestService;
import rw.rca.hatumaems.services.EquipmentService;
import rw.rca.hatumaems.services.UserService;
import java.util.List;

@RestController
@RequestMapping("/api/requests")
@CrossOrigin(origins = "*")
public class EquipmentRequestController {

    @Autowired
    private EquipmentRequestService requestService;

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<EquipmentRequest> createRequest(@Valid @RequestBody CreateRequestDTO request) {
        try {
            // First get the equipment and user objects
            Equipment equipment = equipmentService.getEquipmentById(request.getEquipmentId());
            User requester = userService.getUserById(request.getRequesterId());

            // Then create the request
            EquipmentRequest newRequest = requestService.createRequest(
                equipment,
                requester,
                request.getPurpose(),
                request.getDuration()
            );

            return ResponseEntity.ok(newRequest);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to create request: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<EquipmentRequest>> getAllRequests() {
        return ResponseEntity.ok(requestService.getAllRequests());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<EquipmentRequest>> getUserRequests(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(requestService.getRequestsByUser(user));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<EquipmentRequest> updateRequestStatus(
            @PathVariable Long id,
            @RequestBody StatusUpdateDTO statusUpdate) {
        return ResponseEntity.ok(
            requestService.updateRequestStatus(
                id,
                statusUpdate.getStatus(),
                statusUpdate.getReturnCondition()
            )
        );
    }

    @GetMapping("/pending")
    public ResponseEntity<List<EquipmentRequest>> getPendingRequests() {
        return ResponseEntity.ok(requestService.getPendingRequests());
    }
}

class CreateRequestDTO {
    @NotNull(message = "Equipment ID is required")
    private Long equipmentId;

    @NotNull(message = "Requester ID is required")
    private Long requesterId;

    @NotBlank(message = "Purpose is required")
    private String purpose;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 hour")
    private Integer duration;

    // Getters and setters
    public Long getEquipmentId() { return equipmentId; }
    public void setEquipmentId(Long equipmentId) { this.equipmentId = equipmentId; }
    public Long getRequesterId() { return requesterId; }
    public void setRequesterId(Long requesterId) { this.requesterId = requesterId; }
    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }
}

class StatusUpdateDTO {
    @NotBlank(message = "Status is required")
    private String status;

    private String returnCondition;

    // Getters and setters
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getReturnCondition() { return returnCondition; }
    public void setReturnCondition(String returnCondition) { this.returnCondition = returnCondition; }
}
