package rw.rca.hotelbookingsystem.models;

import jakarta.persistence.*;
import rw.rca.hotelbookingsystem.controllers.StaffController;

import java.time.LocalDateTime;

@Embeddable
public class Address {
    private String district;
    private String sector;
    private String streetNO;

    public Address(String district, String sector, String streetNO) {
        this.district = district;
        this.sector = sector;
        this.streetNO = streetNO;
    }

    public Address() {

    }


    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getStreetNO() {
        return streetNO;
    }

    public void setStreetNO(String streetNO) {
        this.streetNO = streetNO;
    }

    @Entity
    @Table(name = "payments")
    public static class Payment {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "booking_id", nullable = false)
        private Booking booking;

        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

        @Column(nullable = false)
        private Double amount;

        @Column(nullable = false)
        private String paymentMethod;

        @Column(nullable = false)
        private String transactionId;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private PaymentStatus status;

        @Column(nullable = false)
        private LocalDateTime paymentDate;

        @Column
        private LocalDateTime refundDate;

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Booking getBooking() {
            return booking;
        }

        public void setBooking(Booking booking) {
            this.booking = booking;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public PaymentStatus getStatus() {
            return status;
        }

        public void setStatus(PaymentStatus status) {
            this.status = status;
        }

        public LocalDateTime getPaymentDate() {
            return paymentDate;
        }

        public void setPaymentDate(LocalDateTime paymentDate) {
            this.paymentDate = paymentDate;
        }

        public LocalDateTime getRefundDate() {
            return refundDate;
        }

        public void setRefundDate(LocalDateTime refundDate) {
            this.refundDate = refundDate;
        }
    }

    @Entity
    @Table(name = "bookings")
    public static class Booking {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

        @ManyToOne
        @JoinColumn(name = "room_id", nullable = false)
        private StaffController.Room room;

        @Column(nullable = false)
        private LocalDateTime checkInDate;

        @Column(nullable = false)
        private LocalDateTime checkOutDate;

        @Column(nullable = false)
        private Double totalPrice;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private BookingStatus status;

        @Column(nullable = false)
        private LocalDateTime createdAt;

        @Column
        private LocalDateTime updatedAt;

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public StaffController.Room getRoom() {
            return room;
        }

        public void setRoom(StaffController.Room room) {
            this.room = room;
        }

        public LocalDateTime getCheckInDate() {
            return checkInDate;
        }

        public void setCheckInDate(LocalDateTime checkInDate) {
            this.checkInDate = checkInDate;
        }

        public LocalDateTime getCheckOutDate() {
            return checkOutDate;
        }

        public void setCheckOutDate(LocalDateTime checkOutDate) {
            this.checkOutDate = checkOutDate;
        }

        public Double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(Double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public BookingStatus getStatus() {
            return status;
        }

        public void setStatus(BookingStatus status) {
            this.status = status;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }

        public LocalDateTime getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
        }
    }
}
