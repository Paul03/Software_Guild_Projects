/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.addressbook.dao;

import com.mycompany.addressbook.dto.Address;
import java.util.List;

/**
 *
 * @author paulharding
 */
public interface AddressDao {
    
    public Address create(Address adddress);
    
    public Address read(Integer id);
    
    public void update(Address address);
    
    public void delete(Address address);
    
    public List<Address> list();
    
    public List<Address> findByLastName(String lastName);
    
    public List<Address> findByCity(String cityName);
    
    public List<Address> findByState(String stateName);
    
    public List<Address> findByZip(String zipCode);
    
}
