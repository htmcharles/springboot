/**
 * Enum representing the different roles a user can have in the system.
 * These roles determine the user's permissions and access levels.
 */
package rw.rca.hotelbookingsystem.models;

public enum UserRole {
    /**
     * Administrator role with full system access
     */
    ADMIN,

    /**
     * Manager role with hotel management capabilities
     */
    MANAGER,

    /**
     * Staff role for hotel employees
     */
    STAFF,

    /**
     * Customer role for hotel guests
     */
    CUSTOMER
}
