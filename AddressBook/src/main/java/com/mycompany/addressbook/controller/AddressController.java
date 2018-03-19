/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.addressbook.controller;

import com.mycompany.addressbook.dao.AddressDao;
import com.mycompany.addressbook.dto.Address;
import com.mycompany.ui.ConsoleIO;
import java.util.List;

/**
 *
 * @author apprentice
 */
public class AddressController {

    private ConsoleIO io = new ConsoleIO();
    private AddressDao addressDao;

    public AddressController(AddressDao dao) {
        this.addressDao = dao;
    }

    public void run() {

        boolean playAgain = true;

        while (playAgain) {

            io.println("");
            io.println("                        =========");
            io.println("                        MAIN MENU");
            io.println("                        =========");
            io.println("");

            io.println("\"1\"  Add an address               \"4\"  List the addresses in the address book");
            io.println("");
            io.println("\"2\"  Delete an address            \"5\"  Search");
            io.println("");
            io.println("\"3\"  Edit an address              \"6\"  View Current address count");
            io.println("");
            io.println("                   \"7\"  Exit Address Book");
            io.println("");

            int userChoice = io.getUserInt("Input the number that matches what you would like to do ", 1, 7);

            switch (userChoice) {

                case 1:
                    addAddress();
                    break;

                case 2:
                    deleteAddress();
                    break;

                case 3:
                    editAddress();
                    break;

                case 4:
                    listAddresses();
                    break;

                case 5:
                    searchAddress();
                    break;

                case 6:
                    countAddresses();
                    break;

                case 7:
                    playAgain = false;

            }

        }

    }

    private void addAddress() {

        io.println("");
        io.println("===========");
        io.println("ADD ADDRESS");
        io.println("===========");
        io.println("");

        boolean addAgain = true;

        while (addAgain) {

            String lastName = io.getNonEmptyUserString("Enter the last name of the person you are adding: ");
            String firstName = io.getNonEmptyUserString("Enter the first name of the person you are adding: ");
            String address = io.getNonEmptyUserString("What is " + firstName + " " + lastName + "'s street address? (Do not include city or zip) ");

            addAddress(lastName, firstName, address);

            io.println("");
            io.println(firstName + " " + lastName + " has been added!");
            io.println("If you would like to add more information for " + firstName + " " + lastName + ", to your addess book,\nthen you can do so by selecting \"Edit an address\" from the Main Menu");
            io.println("");

            int userChoice = io.getUserInt("Input \"0\" when you are ready to return to the Main Menu, or\nInput \"1\" to add another address ", 0, 1);

            if (userChoice == 0) {
                addAgain = false;
            } else {
                io.println("");
            }

        }

    }

    public Address addAddress(String lastName, String firstName, String address) {

        Address newAddress = new Address();

        newAddress.setLastName(lastName);
        newAddress.setFirstName(firstName);
        newAddress.setAddress(address);
        newAddress.setCity("**No City Saved**");
        newAddress.setState("**No State Saved**");
        newAddress.setZipCode("#####");

        Address addedAddress = addressDao.create(newAddress);

        return addedAddress;
    }

    private void deleteAddress() {

        io.println("");
        io.println("==============");
        io.println("DELETE ADDRESS");
        io.println("==============");
        io.println("");

        boolean removeAgain = true;

        while (removeAgain) {

            int idKnowledge = io.getUserInt("Do you know the ID Number of the address that you would like to delete?\n(Input \"1\" for yes, or \"2\" for no) ", 1, 2);

            if (idKnowledge == 2) {
                getId();
            }

            io.println("");
            int addressId = io.getUserInt("What is the ID Number of the address that you would like to remove?\n(You can enter \"0\" if you do not want to delete an entry) ");

            Address address = addressDao.read(addressId);

            try {

                io.println("");
                io.println("You have chosen to delete this entry:");
                io.println("    ---------------------------------");
                
                printFullAddress(address);
                
                io.println("    ---------------------------------");
                io.println("");

                int reallyDelete = io.getUserInt("Do you really want to delete this address?\n(Input \"1\" for yes, or \"2\" for no) ", 1, 2);

                if (reallyDelete == 1) {

                    addressDao.delete(address);

                    io.println("");
                    io.println("Address successfully deleted!");
                    io.println("");

                } else {

                    io.println("");
                    io.println("OK, the address has not been deleted.");
                    io.println("");

                }

            } catch (NullPointerException ex) {

                io.println("There is not an address stored in your address book with that ID Number.");

            }

            io.println("");
            int userChoice = io.getUserInt("Input \"0\" when you are ready to return to the Main Menu, or\nInput \"1\" to remove another address ", 0, 1);

            if (userChoice == 0) {
                removeAgain = false;
            } else {
                io.println("");
            }

        }

    }

    private void editAddress() {

        io.println("");
        io.println("============");
        io.println("EDIT ADDRESS");
        io.println("============");
        io.println("");

        boolean editAgain = true;

        while (editAgain) {

            int knowledge = io.getUserInt("Do you know the ID Number of the address that you want to edit?\n(Input \"1\" for yes, or \"2\" for no) ", 1, 2);
            if (knowledge == 2) {
                getId();
            }

            io.println("");
            int addressId = io.getUserInt("What is the ID Number of the address that you would like to edit?\n(You can enter \"0\" if you do not want to edit any entry.) ");

            Address editAddress = addressDao.read(addressId);

            try {

                io.println("");
                io.println("You have chosen to edit this entry:");
                io.println("    -------------------------------");
                
                printFullAddress(editAddress);
                
                io.println("    -------------------------------");
                io.println("");

                boolean editSameEntry = true;

                while (editSameEntry) {

                    io.println("");
                    io.println("What would you like to edit?");
                    io.println("");
                    io.println("\"1\" Edit first name           \"4\" Edit city");
                    io.println("");
                    io.println("\"2\" Edit last name            \"5\" Edit state");
                    io.println("");
                    io.println("\"3\" Edit street address       \"6\" Edit zip code");
                    io.println("");
                    io.println("You can input \"0\" if you do not want to edit anything.");

                    io.println("");
                    int editAttribute = io.getUserInt("Input the number that matches what you would like to edit ", 0, 6);

                    switch (editAttribute) {

                        case 0:
                            io.println("");
                            io.println("You have chosen to edit nothing.");

                            editSameEntry = false;

                            break;

                        case 1:

                            String firstName = io.getNonEmptyUserString("Enter the first name: ");
                            editAddress.setFirstName(firstName);

                            addressDao.update(editAddress);

                            io.println("");
                            io.println("First name for " + editAddress.getFirstName() + " " + editAddress.getLastName() + " successfully updated!");
                            io.println("");

                            break;

                        case 2:

                            String lastName = io.getNonEmptyUserString("Enter the last name: ");
                            editAddress.setLastName(lastName);

                            addressDao.update(editAddress);

                            io.println("");
                            io.println("Last name for " + editAddress.getFirstName() + " " + editAddress.getLastName() + " successfully updated!");
                            io.println("");

                            break;

                        case 3:

                            String streetAddress = io.getNonEmptyUserString("Enter the street address: ");
                            editAddress.setAddress(streetAddress);

                            addressDao.update(editAddress);

                            io.println("");
                            io.println("Street address for " + editAddress.getFirstName() + " " + editAddress.getLastName() + " successfully updated!");
                            io.println("");

                            break;

                        case 4:

                            String city = io.getNonEmptyUserString("Enter the city: ");
                            editAddress.setCity(city);

                            addressDao.update(editAddress);

                            io.println("");
                            io.println("City for " + editAddress.getFirstName() + " " + editAddress.getLastName() + " successfully updated!");
                            io.println("");

                            break;

                        case 5:

                            String state = io.getNonEmptyUserString("Enter the state: ");
                            editAddress.setState(state);

                            addressDao.update(editAddress);

                            io.println("");
                            io.println("State for " + editAddress.getFirstName() + " " + editAddress.getLastName() + " successfully updated!");
                            io.println("");

                            break;

                        case 6:

                            String zipCode = io.getNonEmptyUserString("Enter the zip code: ");
                            editAddress.setZipCode(zipCode);

                            addressDao.update(editAddress);

                            io.println("");
                            io.println("Zip code for " + editAddress.getFirstName() + " " + editAddress.getLastName() + " successfully updated!");
                            io.println("");

                            break;

                    }

                    if (editSameEntry) {

                        int keepEditingSameEntry = io.getUserInt("Would you like to edit any other information about " + editAddress.getFirstName() + " " + editAddress.getLastName() + "?" + "\nInput \"1\" for yes, \"2\" for no ", 1, 2);

                        if (keepEditingSameEntry == 2) {
                            editSameEntry = false;
                        }

                    }

                }

            } catch (NullPointerException ex) {
                io.println("There is not an address stored in your address book with that ID Number.");
            }

            io.println("");
            int userChoice = io.getUserInt("Input \"0\" when you are ready to return to the Main Menu, or\nInput \"1\" to edit another address ", 0, 1);

            if (userChoice == 0) {
                editAgain = false;
            }

        }

    }

    private void listAddresses() {

        io.println("");
        io.println("=================");
        io.println("LIST OF ADDRESSES");
        io.println("=================");

        List<Address> tempAddressBook = addressDao.list();
        printListOfStreetAddresses(tempAddressBook);

        io.getUserInt("Input \"0\" when you are ready to return to the Main Menu ", 0, 0);

    }

    private void searchAddress() {

        io.println("");
        io.println("======");
        io.println("SEARCH");
        io.println("======");
        io.println("");

        boolean searchAgain = true;

        while (searchAgain) {

            io.println("You can search the entries in The Address Book by last name, city, state, or zip code.");
            io.println("");
            io.println("\"1\" Search by Last Name       \"3\" Search by State");
            io.println("");
            io.println("\"2\" Search by City            \"4\" Search by zipcode");
            io.println("");
            io.println("                  \"5\" Search by id");
            int searchBy = io.getUserInt("Input the number that corresponds to your choice ", 1, 4);

            List<Address> searchResults;

            switch (searchBy) {

                case 1:
                    String lastName = io.getNonEmptyUserString("What last name would you like to search for? ");
                    searchResults = addressDao.findByLastName(lastName);

                    io.println("");
                    io.println("Search Results for " + lastName + ": ");

                    printListOfStreetAddresses(searchResults);
                    break;

                case 2:
                    String city = io.getNonEmptyUserString("What city would you like to search for? ");
                    searchResults = addressDao.findByCity(city);

                    io.println("");
                    io.println("Search Results for " + city + ": ");

                    printListOfStreetAddresses(searchResults);
                    break;

                case 3:
                    String state = io.getNonEmptyUserString("What state would you like to search for? ");
                    searchResults = addressDao.findByState(state);

                    io.println("");
                    io.println("Search results for " + state + ":");

                    printListOfStreetAddresses(searchResults);
                    break;

                case 4:
                    String zipCode = io.getNonEmptyUserString("What last name would you like to search for? ");
                    searchResults = addressDao.findByZip(zipCode);

                    io.println("");
                    io.println("Search Results for " + zipCode + ": ");

                    printListOfStreetAddresses(searchResults);
                    break;

                case 5:

                    Integer id = io.getUserInt("What id would you like to search for? ");

                    try {
                        Address address = addressDao.read(id);

                        io.println("");
                        printFullAddress(address);

                    } catch (NullPointerException ex) {
                        io.println("There does not appear to be an address with that id in your Address Book.");
                    }

                    break;

            }

            io.println("");
            int userChoice = io.getUserInt("Input \"0\" when you are ready to return to the Main Menu, or\nInput \"1\" to search again ", 0, 1);

            if (userChoice == 0) {
                searchAgain = false;
            } else {
                io.println("");
            }

        }

    }

    private void getId() {

        io.println("");
        String lastName = io.getUserString("Enter the last name of the person whose address you would like to delete: ");
        io.println("");

        List<Address> lastNameResults = addressDao.findByLastName(lastName);

        io.println("Your address book contains the following entries for the last name " + lastName + ":");
        printListOfStreetAddresses(lastNameResults);

    }

    private void printListOfFullAddresses(List<Address> addressList) {

        for (Address a : addressList) {
            io.println("----------------------------------------------------");
            printFullAddress(a);
        }

        io.println("----------------------------------------------------");
    }

    private void printListOfStreetAddresses(List<Address> addressList) {

        io.println("");

        for (Address a : addressList) {
            io.println("    " + a.getId() + " | " + a.getLastName() + ", " + a.getFirstName() + " | " + a.getAddress());
        }

        io.println("");
    }

    private void printFullAddress(Address address) {

        io.println("    Address ID Number: " + address.getId());
        io.println("    " + address.getFirstName() + " " + address.getLastName());
        io.println("    " + address.getAddress());
        io.println("    " + address.getCity() + ", " + address.getState() + " " + address.getZipCode());

    }

    private void countAddresses() {

        io.println("");
        io.println("=============");
        io.println("ADDRESS COUNT");
        io.println("=============");
        io.println("");

        List<Address> tempAddressBook = addressDao.list();

        if (tempAddressBook.size() == 1) {
            io.println("There is currently " + tempAddressBook.size() + " address saved in the address book");
        } else {
            io.println("There are currently " + tempAddressBook.size() + " addresses saved in the address book");
        }

        io.println("");

        io.getUserInt("Input \"0\" when you are ready to return to the Main Menu ", 0, 0);

    }

}
