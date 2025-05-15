package rw.rca.hatumaems.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * Entity class representing a user in the HATUMA Equipment Management System.
 * Users can be either administrators or staff members with different access levels.
 *
 * @author HATUMA EMS Team
 * @version 1.0
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
    /** Unique identifier for the user */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** User's email address, must be unique */
    @Column(unique = true, nullable = false)
    private String email;

    /** User's hashed password */
    @Column(nullable = false)
    @JsonIgnoreProperties
    private String password;

    /** User's full name (firstName + lastName) */
    @Column(nullable = false)
    private String fullName;

    /** User's role/type (ADMIN or STAFF) */
    @Column(nullable = false)
    private String userType;

    /** User's first name */
    @Column(nullable = false)
    private String firstName;

    /** User's last name */
    @Column(nullable = false)
    private String lastName;

    /** Department where the user works */
    @Column(nullable = false)
    private String department;

    /** User's contact phone number */
    private String phoneNumber;

    /** List of equipment requests made by this user */
    @OneToMany(mappedBy = "requester")
    @JsonIgnoreProperties("requester")
    private List<EquipmentRequest> requests;

    /** Flag indicating if the user account is active */
    private boolean isActive = true;

    /**
     * Gets the user's ID.
     * @return the user's unique identifier
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the user's ID.
     * @param id the unique identifier to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the user's email address.
     * @return the user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the user's password.
     * @return the hashed password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     * @param password the password to set (will be hashed)
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the user's full name.
     * @return the user's full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets the user's full name.
     * @param fullName the full name to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Gets the user's type/role.
     * @return the user's type (ADMIN or STAFF)
     */
    public String getUserType() {
        return userType;
    }

    /**
     * Sets the user's type/role.
     * @param userType the user type to set
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }
}
