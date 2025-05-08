/**
 * Address entity representing a physical address in the system.
 * This is an embeddable class that can be used within other entities
 * to store address information.
 */
package rw.rca.hotelbookingsystem.models;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    /**
     * District or city of the address
     */
    private String district;

    /**
     * Sector or neighborhood of the address
     */
    private String sector;

    /**
     * Street number or identifier
     */
    private String streetNO;

    /**
     * Constructor for creating a new address with all fields
     * @param district District or city
     * @param sector Sector or neighborhood
     * @param streetNO Street number or identifier
     */
    public Address(String district, String sector, String streetNO) {
        this.district = district;
        this.sector = sector;
        this.streetNO = streetNO;
    }

    /**
     * Default constructor
     */
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
