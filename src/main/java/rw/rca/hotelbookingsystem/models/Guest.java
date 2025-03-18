package rw.rca.hotelbookingsystem.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "guest")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer guestID;

    private String name;
    private String phone;
    private String email;

    @Embedded
    private Address address;

    public Guest(Integer guestID, String name, String phone, String email, Address address) {
        this.guestID = guestID;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public Guest() {

    }

    public Integer getGuestID() {
        return guestID;
    }

    public void setGuestID(Integer guestID) {
        this.guestID = guestID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}