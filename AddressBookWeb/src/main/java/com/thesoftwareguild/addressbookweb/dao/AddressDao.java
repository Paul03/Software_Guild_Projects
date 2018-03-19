package com.thesoftwareguild.addressbookweb.dao;

import com.thesoftwareguild.addressbookweb.dto.Address;
import java.util.List;

public interface AddressDao {
    
    public Address create(Address address);
    
    public Address read(Integer id);
    
    public void update(Address address);
    
    public void delete(Address address);
    
    public List<Address> listAll();
    
    public List<Address> findByLastName(String lastName);
    
    public List<Address> findByCity(String cityName);
    
    public List<Address> findByState(String stateName);
    
    public List<Address> findByZip(String zipCode);
    
}
