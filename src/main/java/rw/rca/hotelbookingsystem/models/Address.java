package rw.rca.hotelbookingsystem.models;

import jakarta.persistence.Embeddable;

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
}
