package com.thesoftwareguild.addressbookweb.dao;

import com.thesoftwareguild.addressbookweb.dto.Address;
import java.util.List;

public interface AddressDao {

    Address create(Address address);

    Address read(Integer id);

    void update(Address address);

    void delete(Address address);

    List<Address> listAll();

    List<Address> findByLastName(String lastName);

    List<Address> findByCity(String cityName);

    List<Address> findByState(String stateName);

    List<Address> findByZip(String zipCode);

}
