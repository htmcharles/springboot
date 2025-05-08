/**
 * Staff entity representing hotel staff members in the system.
 * This class manages staff information and their room assignments.
 */
package rw.rca.hotelbookingsystem.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*; // Correct import for JPA annotations
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "staff") // Provide a table name
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "code"
)
public class Staff {

    /**
     * Unique identifier for the staff member
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate ID if needed
    private Integer code;

    /**
     * Staff member's first name
     */
    private String firstName;

    /**
     * Staff member's last name
     */
    private String lastName;

    /**
     * Staff member's email address (unique)
     */
    @Column(name = "email", nullable = false, length = 50, unique = true) // Added unique=true constraint
    private String email;

    /**
     * Set of rooms assigned to this staff member for maintenance/cleaning
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "staff_room_assignment",
        joinColumns = @JoinColumn(name = "staff_id"),
        inverseJoinColumns = @JoinColumn(name = "room_id")
    )
    @JsonIgnore
    private Set<Room> assignedRooms = new HashSet<>();

    /**
     * Default constructor
     */
    public Staff() {}

    /**
     * Constructor for creating a new staff member with all fields
     * @param code Unique identifier for the staff member
     * @param firstName Staff member's first name
     * @param lastName Staff member's last name
     * @param email Staff member's email address
     */
    public Staff(Integer code, String firstName, String lastName, String email) {
        this.code = code;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /**
     * Alternative constructor for creating a new staff member
     * @param email Staff member's email address
     * @param lastName Staff member's last name
     * @param firstName Staff member's first name
     */
    public Staff(String email, String lastName, String firstName) {
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    // Getters and Setters
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Room> getAssignedRooms() {
        return assignedRooms;
    }

    public void setAssignedRooms(Set<Room> assignedRooms) {
        this.assignedRooms = assignedRooms;
    }

    /**
     * Assigns a room to this staff member
     * @param room The room to be assigned
     */
    public void assignRoom(Room room) {
        this.assignedRooms.add(room);
    }

    /**
     * Removes a room assignment from this staff member
     * @param room The room to be unassigned
     */
    public void unassignRoom(Room room) {
        this.assignedRooms.remove(room);
    }
}
