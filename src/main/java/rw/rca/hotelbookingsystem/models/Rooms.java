package rw.rca.hotelbookingsystem.models;

import jakarta.persistence.*;

@Entity
@Table(name = "rooms")
public class Rooms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roomID;

    private String type;
    private Double price;
    private String status;

    public Rooms() {}

    public Rooms(Integer roomID, String type, Double price, String status) {
        this.roomID = roomID;
        this.type = type;
        this.price = price;
        this.status = status;
    }

    public Integer getRoomID() {
        return roomID;
    }

    public void setRoomID(Integer roomID) {
        this.roomID = roomID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
