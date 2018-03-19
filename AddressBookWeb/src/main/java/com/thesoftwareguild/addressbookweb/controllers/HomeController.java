/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thesoftwareguild.addressbookweb.controllers;

import com.thesoftwareguild.addressbookweb.dao.AddressDao;
import com.thesoftwareguild.addressbookweb.dto.Address;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author paulharding
 */
@Controller
public class HomeController {
    
    private AddressDao addressDao;
    
    @Inject
    public HomeController(AddressDao addressDao) {
        this.addressDao = addressDao;
    }
    
    @RequestMapping(value="/", method=RequestMethod.GET)
    public String home(Map model) {
        
        List<Address> addressList = addressDao.listAll();
        model.put("addressBookList", addressList);
        model.put("address", new Address());
        
        return "home";
        
    }
    
}
