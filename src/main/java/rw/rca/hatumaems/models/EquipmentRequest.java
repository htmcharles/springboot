package rw.rca.hatumaems.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity class representing equipment requests in the HATUMA Equipment Management System.
 * Tracks requests from staff members to borrow equipment, including approval status and return details.
 *
 * @author HATUMA EMS Team
 * @version 1.0
 */
@Entity
@Table(name = "equipment_requests")
public class EquipmentRequest {
    /** Unique identifier for the request */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The equipment being requested */
    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    /** The user making the request */
    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    /** Purpose or reason for the equipment request */
    @Column(nullable = false)
    private String purpose;

    /** Duration for which the equipment is needed (in hours) */
    @Column(nullable = false)
    private Integer duration;

    /** Current status of the request (PENDING, APPROVED, REJECTED, RETURNED) */
    @Column(nullable = false)
    private String status;

    /** Date and time when the request was created */
    @Column(nullable = false)
    private LocalDateTime requestDate;

    /** Date and time when the request was approved/rejected */
    private LocalDateTime approvalDate;

    /** Date and time when the equipment was returned */
    private LocalDateTime returnDate;

    /** Condition of the equipment when returned */
    private String returnCondition;

    /**
     * Default constructor required by JPA
     */
    public EquipmentRequest() {}

    /**
     * Gets the request's ID.
     * @return the request's unique identifier
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the request's ID.
     * @param id the unique identifier to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the requested equipment.
     * @return the Equipment object being requested
     */
    public Equipment getEquipment() {
        return equipment;
    }

    /**
     * Sets the requested equipment.
     * @param equipment the Equipment object to set
     */
    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    /**
     * Gets the user who made the request.
     * @return the User object who made the request
     */
    public User getRequester() {
        return requester;
    }

    /**
     * Sets the requesting user.
     * @param requester the User object to set
     */
    public void setRequester(User requester) {
        this.requester = requester;
    }

    /**
     * Gets the purpose of the request.
     * @return the purpose or reason for the request
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * Sets the purpose of the request.
     * @param purpose the purpose to set
     */
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    /**
     * Gets the requested duration.
     * @return the duration in hours
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * Sets the requested duration.
     * @param duration the duration in hours to set
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * Gets the current status of the request.
     * @return the current status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the request.
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the request creation date.
     * @return the LocalDateTime when the request was created
     */
    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    /**
     * Sets the request creation date.
     * @param requestDate the LocalDateTime to set
     */
    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    /**
     * Gets the approval/rejection date.
     * @return the LocalDateTime when the request was approved/rejected
     */
    public LocalDateTime getApprovalDate() {
        return approvalDate;
    }

    /**
     * Sets the approval/rejection date.
     * @param approvalDate the LocalDateTime to set
     */
    public void setApprovalDate(LocalDateTime approvalDate) {
        this.approvalDate = approvalDate;
    }

    /**
     * Gets the return date.
     * @return the LocalDateTime when the equipment was returned
     */
    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    /**
     * Sets the return date.
     * @param returnDate the LocalDateTime to set
     */
    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * Gets the condition of the equipment upon return.
     * @return the condition status
     */
    public String getReturnCondition() {
        return returnCondition;
    }

    /**
     * Sets the condition of the equipment upon return.
     * @param returnCondition the condition to set
     */
    public void setReturnCondition(String returnCondition) {
        this.returnCondition = returnCondition;
    }
}

/**
 * Enum representing the possible states of an equipment request.
 */
enum RequestStatus {
    /** Request is awaiting approval */
    PENDING,
    /** Request has been approved */
    APPROVED,
    /** Request has been rejected */
    REJECTED,
    /** Equipment has been returned */
    RETURNED
}

/**
 * Enum representing the condition of returned equipment.
 */
enum EquipmentCondition {
    /** Equipment is in good condition */
    GOOD,
    /** Equipment is damaged */
    DAMAGED,
    /** Equipment needs maintenance */
    NEEDS_MAINTENANCE
}
