/**
 * REST Controller for handling staff-related HTTP requests.
 * Provides endpoints for managing hotel staff members.
 */
package rw.rca.hotelbookingsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rw.rca.hotelbookingsystem.models.Staff;
import rw.rca.hotelbookingsystem.services.StaffService;

import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:5173")
@RequestMapping("/booking")
public class StaffController {
    @Autowired
    private StaffService staffService;

    /**
     * Simple endpoint to test if the server is running
     * @return A hello world message
     */
    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    /**
     * Adds a new staff member to the system
     * @param staff The staff object containing staff member details
     */
    @PostMapping("/person")
    public void saveStaff(@RequestBody Staff staff) {
        staffService.addStaff(staff);
    }

    /**
     * Retrieves all staff members in the system
     * @return List of all staff members
     */
    @GetMapping("/person")
    public List<Staff> getAllStaff() {
        return staffService.findAllStaffs();
    }
}
