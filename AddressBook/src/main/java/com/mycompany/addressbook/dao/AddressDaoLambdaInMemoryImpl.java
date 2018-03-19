/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.addressbook.dao;

import com.mycompany.addressbook.dto.Address;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author apprentice
 */
public class AddressDaoLambdaInMemoryImpl implements AddressDao {

    private List<Address> addressBook = new ArrayList();
    private Integer nextId = 1;

    public AddressDaoLambdaInMemoryImpl() {

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

        List<Address> result = addressBook
                .stream()
                .filter(a -> a.getLastName().equals(lastName))
                .collect(Collectors.toList());

        return result;

    }

    @Override
    public List<Address> findByCity(String cityName) {

        List<Address> result = addressBook
                .stream()
                .filter(a -> a.getCity().equals(cityName))
                .collect(Collectors.toList());

        return result;

    }

    @Override
    public List<Address> findByState(String stateName) {

        // Create a list of Address objects that match the state name
        List<Address> result = addressBook
                .stream()
                .filter(a -> a.getState().toLowerCase().contains(stateName.toLowerCase()) || stateName.toLowerCase().contains(a.getState().toLowerCase()))
                .collect(Collectors.toList());

        Collections.sort(result, new Address());

        return result;
    }

    @Override
    public List<Address> findByZip(String zipCode) {

        List<Address> result = addressBook
                .stream()
                .filter(a -> a.getZipCode().equals(zipCode))
                .collect(Collectors.toList());

        return result;
    }

}
