package com.thesoftwareguild.addressbookweb.controllers;

import com.thesoftwareguild.addressbookweb.dao.AddressDao;
import com.thesoftwareguild.addressbookweb.dto.Address;
import com.thesoftwareguild.addressbookweb.dto.SearchCommand;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value="/address")
public class AddressController {
    
    private AddressDao addressDao;
    
    public AddressController(AddressDao addressDao) {
        this.addressDao = addressDao;
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    @ResponseBody
    public Address show(@PathVariable("id") Integer addressId) {
        return addressDao.read(addressId);
    }
    
    @RequestMapping(value="", method=RequestMethod.POST)
    @ResponseBody
    public Address add(@Valid @RequestBody Address address) {
        return addressDao.create(address);
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    @ResponseBody
    public Address edit(@Valid @RequestBody Address address) {
        addressDao.update(address);
        return address;
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    @ResponseBody
    public Address delete(@RequestBody Address address) {
        addressDao.delete(address);
        return address;
    }

    @RequestMapping(value="/search", method=RequestMethod.GET)
    public String search() {
        return "Search";
    }

    @RequestMapping(value="/search", method=RequestMethod.POST)
    @ResponseBody
    public List<Address> searchByLastName(@RequestBody SearchCommand searchCommand) {

        List<Address> searchResultList = new ArrayList<>();

        if (searchCommand.getFieldToSearch().equals("lastName")) {

            searchResultList = addressDao.findByLastName(searchCommand.getValueToSearchFor());

        }

        if (searchCommand.getFieldToSearch().equals("city")) {

            searchResultList = addressDao.findByCity(searchCommand.getValueToSearchFor());

        }

        if (searchCommand.getFieldToSearch().equals("state")) {

            searchResultList = addressDao.findByState(searchCommand.getValueToSearchFor());

        }

        if (searchCommand.getFieldToSearch().equals("zipCode")) {

            searchResultList = addressDao.findByZip(searchCommand.getValueToSearchFor());

        }

        return searchResultList;
    }
    
}