package rw.rca.hotelbookingsystem.controllers;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rw.rca.hotelbookingsystem.models.Address;
import rw.rca.hotelbookingsystem.models.Review;
import rw.rca.hotelbookingsystem.models.Staff;
import rw.rca.hotelbookingsystem.services.StaffService;

import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:8098")
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

    @Entity
    @Table(name = "rooms")
    public static class Room {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String roomNumber;

        @Column(nullable = false)
        private String type;

        @Column(nullable = false)
        private Double pricePerNight;

        @Column(nullable = false)
        private Integer capacity;

        @Column(nullable = false)
        private String description;

        @Column(nullable = false)
        private Boolean isAvailable;

        @OneToMany(mappedBy = "room")
        private List<Address.Booking> bookings;

        @OneToMany(mappedBy = "room")
        private List<Review> reviews;

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getRoomNumber() {
            return roomNumber;
        }

        public void setRoomNumber(String roomNumber) {
            this.roomNumber = roomNumber;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Double getPricePerNight() {
            return pricePerNight;
        }

        public void setPricePerNight(Double pricePerNight) {
            this.pricePerNight = pricePerNight;
        }

        public Integer getCapacity() {
            return capacity;
        }

        public void setCapacity(Integer capacity) {
            this.capacity = capacity;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Boolean getIsAvailable() {
            return isAvailable;
        }

        public void setIsAvailable(Boolean isAvailable) {
            this.isAvailable = isAvailable;
        }

        public List<Address.Booking> getBookings() {
            return bookings;
        }

        public void setBookings(List<Address.Booking> bookings) {
            this.bookings = bookings;
        }

        public List<Review> getReviews() {
            return reviews;
        }

        public void setReviews(List<Review> reviews) {
            this.reviews = reviews;
        }
    }
}
