package rw.rca.hatumaems.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

/**
 * Entity class representing equipment items in the HATUMA Equipment Management System.
 * Each equipment item has properties such as name, type, status, and location.
 *
 * @author HATUMA EMS Team
 * @version 1.0
 */
@Entity
@Table(name = "equipment")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Equipment {
    /** Unique identifier for the equipment */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Name of the equipment */
    @Column(nullable = false)
    private String name;

    /** Type/category of the equipment */
    @Column(nullable = false)
    private String type;

    /** Detailed description of the equipment */
    @Column(nullable = false)
    private String description;

    /** Current status of the equipment (AVAILABLE, IN_USE, MAINTENANCE) */
    @Column(nullable = false)
    private String status;

    /** Available quantity of this equipment */
    @Column(nullable = false)
    private Integer quantity;

    /** Physical location where the equipment is stored */
    @Column(nullable = false)
    private String location;

    /**
     * Default constructor required by JPA
     */
    public Equipment() {}

    /**
     * Gets the equipment's ID.
     * @return the equipment's unique identifier
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the equipment's ID.
     * @param id the unique identifier to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the equipment's name.
     * @return the name of the equipment
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the equipment's name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the equipment's type.
     * @return the type/category of the equipment
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the equipment's type.
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the equipment's description.
     * @return the detailed description of the equipment
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the equipment's description.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the equipment's current status.
     * @return the current status (AVAILABLE, IN_USE, MAINTENANCE)
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the equipment's status.
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the available quantity.
     * @return the quantity of this equipment
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Sets the available quantity.
     * @param quantity the quantity to set
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the equipment's location.
     * @return the physical location where the equipment is stored
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the equipment's location.
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }
}

/**
 * Enum representing the possible states of equipment.
 */
enum EquipmentStatus {
    /** Equipment is available for use */
    AVAILABLE,
    /** Equipment is currently in use */
    IN_USE,
    /** Equipment is under maintenance */
    MAINTENANCE
}
