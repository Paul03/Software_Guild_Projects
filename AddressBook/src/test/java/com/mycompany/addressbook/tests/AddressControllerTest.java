/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.addressbook.tests;

import com.mycompany.addressbook.controller.AddressController;
import com.mycompany.addressbook.dto.Address;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author paulharding
 */
public class AddressControllerTest {
    
    
    
    AddressController ac;
    
    public AddressControllerTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        ac = ctx.getBean("addressController", AddressController.class);
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testCreateAddress() {
        
        String firstName = "Paul";
        String lastName = "Harding";
        String streetAddress = "123 Main St";
        
        Address address = ac.addAddress(lastName, firstName, streetAddress);
        
        Assert.assertEquals("Paul", address.getFirstName());
        Assert.assertEquals("Harding", address.getLastName());
        Assert.assertEquals("123 Main St", address.getAddress());
        Assert.assertEquals("**No City Saved**", address.getCity());
        Assert.assertEquals("**No State Saved**", address.getState());
        Assert.assertEquals("#####", address.getZipCode());
        
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
