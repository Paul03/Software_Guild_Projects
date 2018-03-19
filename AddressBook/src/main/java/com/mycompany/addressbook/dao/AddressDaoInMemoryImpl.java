/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.addressbook.dao;

import com.mycompany.addressbook.dto.Address;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author apprentice
 */
public class AddressDaoInMemoryImpl implements AddressDao {

    private List<Address> addressBook = new ArrayList();
    private Integer nextId = 1;

    public AddressDaoInMemoryImpl() {

    }

    @Override
    public Address create(Address address) {

        address.setId(nextId);

        addressBook.add(address);

        nextId++;

        return address;
    }

    @Override
    public Address read(Integer id) {

        for (Address a : addressBook) {

            if (Objects.equals(a.getId(), id)) {

                return a;

            }

        }

        return null;
    }

    @Override
    public void update(Address address) {

        for (Address a : addressBook) {

            if (Objects.equals(a.getId(), address.getId())) {

                a = address;
                break;

            }

        }

    }

    @Override
    public void delete(Address address) {

        for (Address a : addressBook) {

            if (Objects.equals(a.getId(), address.getId())) {

                addressBook.remove(a);
                break;

            }

        }

    }

    @Override
    public List<Address> list() {

        List<Address> result = new ArrayList(addressBook);

        Collections.sort(result);

        return result;
    }

    @Override
    public List<Address> findByLastName(String lastName) {

        List<Address> result = new ArrayList();

        for (Address a : addressBook) {
            if (a.getLastName().equals(lastName)) {
                result.add(a);
            }
        }

        return result;

    }

    @Override
    public List<Address> findByCity(String cityName) {

        List<Address> result = new ArrayList();

        for (Address a : addressBook) {
            if (a.getCity().equals(cityName)) {
                result.add(a);
            }
        }

        return result;
    }

    @Override
    public List<Address> findByState(String stateName) {

        List<Address> result = new ArrayList();

        for (Address a : addressBook) {
            if (a.getState().toLowerCase().contains(stateName.toLowerCase()) || stateName.toLowerCase().contains(a.getState().toLowerCase())) {
                result.add(a);
            }
        }

        Collections.sort(result, new Address());

        return result;
    }

    @Override
    public List<Address> findByZip(String zipCode) {

        List<Address> result = new ArrayList();

        for (Address a : addressBook) {
            if (a.getZipCode() == zipCode) {
                result.add(a);
            }
        }

        return result;
    }

}
