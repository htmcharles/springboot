package rw.rca.hotelbookingsystem.models;

import jakarta.persistence.*; // Correct import for JPA annotations

@Entity
@Table(name = "staff") // Provide a table name
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate ID if needed
    private Integer code;

    private String firstName;
    private String lastName;

    @Column(name = "email", nullable = false, length = 50, unique = true) // Added unique=true constraint
    private String email;

    // Default constructor
    public Staff() {}

    // Constructor with all fields
    public Staff(Integer code, String firstName, String lastName, String email) {
        this.code = code;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // Alternative constructor
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
}
