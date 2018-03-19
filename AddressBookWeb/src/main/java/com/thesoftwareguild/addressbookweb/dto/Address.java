package com.thesoftwareguild.addressbookweb.dto;

import java.util.Comparator;
import org.hibernate.validator.constraints.NotEmpty;

public class Address implements Comparable, Comparator {

    private Integer id;
    
    @NotEmpty(message="Please enter a first name")
    private String firstName;
    
    @NotEmpty(message="Please enter a last name")
    private String lastName;
    
    @NotEmpty(message="Please enter a street address")
    private String streetAddress;
    
    @NotEmpty(message="Please enter a city")
    private String city;
    
    @NotEmpty(message="Please enter a state")
    private String state;
    
    @NotEmpty(message="Please enter a zip code")
    private String zipCode;

    public Address() {

    }

    public Address(Address address) {
        this.id = address.getId();
        this.firstName = address.getFirstName();
        this.lastName = address.getLastName();
        this.streetAddress = address.getStreetAddress();
        this.city = address.getCity();
        this.state = address.getState();
        this.zipCode = address.getZipCode();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public int compareTo(Object o) {

        Address compareAddress = (Address) o;

        if (this.lastName == null && compareAddress.getLastName() == null) {
            return 0;
        } else if (this.lastName == null) {
            return 1;
        } else if (compareAddress.getLastName() == null) {
            return -1;
        } else {
            return this.lastName.compareToIgnoreCase(compareAddress.getLastName());
        }

    }

    @Override
    public int compare(Object o1, Object o2) {

        Address address1 = (Address) o1;
        Address address2 = (Address) o2;

        String address1City = address1.getCity();
        String address2City = address2.getCity();

        if (address1City == null && address2City == null) {
            return 0;
        } else if (address1City == null) {
            return 1;
        } else if (address2City == null) {
            return -1;
        } else {
            return address1.getCity().compareToIgnoreCase(address2.getCity());
        }

    }

}
