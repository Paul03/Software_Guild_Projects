package com.thesoftwareguild.addressbookweb.dao;

import com.thesoftwareguild.addressbookweb.dto.Address;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class AddressDaoImpl implements AddressDao {

    private List<Address> addressBook = null;
    private static String FILENAME = "addresses.txt";
    private static String TOKEN = "::";
    private Integer nextId = 1;

    public AddressDaoImpl() {

        addressBook = decode();

        for (Address a : addressBook) {
            if (a.getId() >= nextId) {
                nextId = a.getId() + 1;
            }
        }

    }

    @Override
    public Address create(Address address) {

        address.setId(nextId);

        addressBook.add(address);

        nextId++;
        
        encode();

        return new Address(address);
    }

    @Override
    public Address read(Integer id) {

        for (Address a : addressBook) {

            if (Objects.equals(a.getId(), id)) {

                return new Address(a);

            }

        }

        return null;
    }

    @Override
    public void update(Address address) {

        for (int i = 0; i < addressBook.size(); i++) {
            if (addressBook.get(i).getId().equals(address.getId())) {
                addressBook.set(i, address);
            }
        }
        
        encode();

    }

    @Override
    public void delete(Address address) {

        for (Address a : addressBook) {

            if (Objects.equals(a.getId(), address.getId())) {

                addressBook.remove(a);
                break;

            }

        }
        
        encode();

    }

    @Override
    public List<Address> listAll() {

        ArrayList result = new ArrayList(addressBook);

        Collections.sort(result);

        return result;
    }

    private void encode() {

        PrintWriter out = null;

        try {

            out = new PrintWriter(new FileWriter(FILENAME));

            for (Address a : addressBook) {

                out.print(a.getId());
                out.print(TOKEN);

                out.print(a.getFirstName());
                out.print(TOKEN);

                out.print(a.getLastName());
                out.print(TOKEN);

                out.print(a.getStreetAddress());
                out.print(TOKEN);

                out.print(a.getCity());
                out.print(TOKEN);

                out.print(a.getState());
                out.print(TOKEN);

                out.print(a.getZipCode());
                out.println("");

                out.flush();

            }

        } catch (IOException ex) {

        } finally {
            out.close();
        }

    }

    private List<Address> decode() {

        List<Address> tempAddressBook = new ArrayList<>();

        try {

            Scanner sc = new Scanner(new BufferedReader(new FileReader(FILENAME)));

            while (sc.hasNextLine()) {

                String currentLine = sc.nextLine();

                String[] stringParts = currentLine.split(TOKEN);

                Address newAddress = new Address();

                Integer id;
                try {

                    id = Integer.parseInt(stringParts[0]);

                } catch (NumberFormatException ex) {

                    id = null;

                }

                newAddress.setId(id);
                newAddress.setFirstName(stringParts[1]);
                newAddress.setLastName(stringParts[2]);
                newAddress.setStreetAddress(stringParts[3]);
                newAddress.setCity(stringParts[4]);
                newAddress.setState(stringParts[5]);
                newAddress.setZipCode(stringParts[6]);

                tempAddressBook.add(newAddress);

            }

        } catch (FileNotFoundException ex) {

        }

        return tempAddressBook;

    }

    @Override
    public List<Address> findByLastName(String lastName) {

        List<Address> result = new ArrayList<>();

        for (Address a : addressBook) {
            if (a.getLastName().equals(lastName)) {
                result.add(a);
            }
        }

        return result;

    }

    @Override
    public List<Address> findByCity(String cityName) {

        List<Address> result = new ArrayList<>();

        for (Address a : addressBook) {
            if (a.getCity().equals(cityName)) {
                result.add(a);
            }
        }

        return result;
    }

    @Override
    public List<Address> findByState(String stateName) {

        List<Address> result = new ArrayList<>();

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

        List<Address> result = new ArrayList<>();

        for (Address a : addressBook) {
            if (a.getZipCode().equals(zipCode)) {
                result.add(a);
            }
        }

        return result;
    }

}
