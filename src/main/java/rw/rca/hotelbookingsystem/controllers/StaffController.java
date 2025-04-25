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

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @PostMapping("/person")
    public void saveStaff(@RequestBody Staff staff) {
        staffService.addStaff(staff);
    }

    @GetMapping("/person")
    public List<Staff> getAllStaff() {
        return staffService.findAllStaffs();
    }
}
