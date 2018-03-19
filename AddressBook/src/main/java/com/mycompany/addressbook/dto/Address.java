/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.addressbook.dto;

import java.util.Comparator;


/**
 *
 * @author apprentice
 */
public class Address implements Comparable, Comparator {
    
    private Integer id;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private String zipCode;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

        return this.lastName.compareToIgnoreCase( ( (Address) o).getLastName() );
        
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
            return address1.getCity().compareToIgnoreCase( address2.getCity() );
        }
        
    }
    
}
