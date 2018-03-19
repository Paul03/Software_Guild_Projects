/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.addressbook.tests;

import com.mycompany.addressbook.dao.AddressDao;
import com.mycompany.addressbook.dto.Address;
import java.util.List;
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
public class AddressDaoLambdaImplJUnitTest {

    AddressDao dao;

    public AddressDaoLambdaImplJUnitTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        dao = ctx.getBean("addressDaoLambda", AddressDao.class);
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testFindByLastName() {

        Address address1 = new Address(); // Create 3 Address objects that all have the same last name
        address1.setLastName("Test");

        Address address2 = new Address();
        address2.setLastName("notTest");

        Address address3 = new Address();
        address3.setLastName("Test");

        dao.create(address1); // Add the addresses to the dao's list
        dao.create(address2);
        dao.create(address3);

        List<Address> lastNameSort = dao.findByLastName("Test");

        Assert.assertNotNull(lastNameSort);
        Assert.assertEquals(2, lastNameSort.size());

    }

    @Test
    public void testFindByCity() {

        Address address1 = new Address(); // Create 5 Address objects
        address1.setCity("Akron");

        Address address2 = new Address();
        address2.setCity("Akron");

        Address address3 = new Address();
        address3.setCity("Canton");

        Address address4 = new Address();
        address4.setCity("Akron");

        Address address5 = new Address();
        address5.setCity("Cleveland");

        dao.create(address1); // Add the addresses to the dao's list
        dao.create(address2);
        dao.create(address3);
        dao.create(address4);
        dao.create(address5);

        List<Address> citySortAkron = dao.findByCity("Akron");
        Assert.assertNotNull(citySortAkron);
        Assert.assertEquals(3, citySortAkron.size());

        List<Address> citySortCleveland = dao.findByCity("Cleveland");
        Assert.assertNotNull(citySortCleveland);
        Assert.assertEquals(1, citySortCleveland.size());

        List<Address> citySortCanton = dao.findByCity("Canton");
        Assert.assertNotNull(citySortCanton);
        Assert.assertEquals(1, citySortCanton.size());

    }

    @Test
    public void testFindByStateOhio() {

        Address address1 = new Address(); // Create 5 Address objects, varying types of Ohio
        address1.setState("Oh");

        Address address2 = new Address();
        address2.setState("OH");

        Address address3 = new Address();
        address3.setState("Ohio");

        Address address4 = new Address();
        address4.setState("oh");

        Address address5 = new Address();
        address5.setState("ohio");

        dao.create(address1); // Add the addresses to the dao's list
        dao.create(address2);
        dao.create(address3);
        dao.create(address4);
        dao.create(address5);

        List<Address> stateSortOhio = dao.findByState("Ohio");
        Assert.assertNotNull(stateSortOhio);
        Assert.assertEquals(5, stateSortOhio.size());

    }

    @Test
    public void testFindByStateOH() {

        Address address1 = new Address(); // Create 5 Address objects, varying types of Ohio
        address1.setState("Oh");

        Address address2 = new Address();
        address2.setState("OH");

        Address address3 = new Address();
        address3.setState("Ohio");

        Address address4 = new Address();
        address4.setState("oh");

        Address address5 = new Address();
        address5.setState("ohio");

        dao.create(address1); // Add the addresses to the dao's list
        dao.create(address2);
        dao.create(address3);
        dao.create(address4);
        dao.create(address5);

        List<Address> stateSortOH = dao.findByState("OH");
        Assert.assertNotNull(stateSortOH);
        Assert.assertEquals(5, stateSortOH.size());

    }

    @Test
    public void testFindByStateOh() {

        Address address1 = new Address(); // Create 5 Address objects, varying types of Ohio
        address1.setState("Oh");

        Address address2 = new Address();
        address2.setState("OH");

        Address address3 = new Address();
        address3.setState("Ohio");

        Address address4 = new Address();
        address4.setState("oh");

        Address address5 = new Address();
        address5.setState("ohio");

        dao.create(address1); // Add the addresses to the dao's list
        dao.create(address2);
        dao.create(address3);
        dao.create(address4);
        dao.create(address5);

        List<Address> stateSortOh = dao.findByState("Oh");
        Assert.assertNotNull(stateSortOh);
        Assert.assertEquals(5, stateSortOh.size());

    }

    @Test
    public void testFindByStateoh() {

        Address address1 = new Address(); // Create 5 Address objects, varying types of Ohio
        address1.setState("Oh");

        Address address2 = new Address();
        address2.setState("OH");

        Address address3 = new Address();
        address3.setState("Ohio");

        Address address4 = new Address();
        address4.setState("oh");

        Address address5 = new Address();
        address5.setState("ohio");

        dao.create(address1); // Add the addresses to the dao's list
        dao.create(address2);
        dao.create(address3);
        dao.create(address4);
        dao.create(address5);

        List<Address> stateSortoh = dao.findByState("oh");
        Assert.assertNotNull(stateSortoh);
        Assert.assertEquals(5, stateSortoh.size());

    }

    @Test
    public void testFindByZip() {

        Address address1 = new Address(); // Create 5 Address objects
        address1.setZipCode("00000");

        Address address2 = new Address();
        address2.setZipCode("99999");

        Address address3 = new Address();
        address3.setZipCode("99999");

        Address address4 = new Address();
        address4.setZipCode("00000");

        Address address5 = new Address();
        address5.setZipCode("00000");

//        Address address6 = new Address(); // what if there is an address object w/no zipCode set
        // We got problems!
        
        dao.create(address1); // Add the addresses to the dao's list
        dao.create(address2);
        dao.create(address3);
        dao.create(address4);
        dao.create(address5);
//        dao.create(address6);

        List<Address> zipSort00000 = dao.findByZip("00000");
        Assert.assertNotNull(zipSort00000);
        Assert.assertEquals(3, zipSort00000.size());

        List<Address> zipSort99999 = dao.findByZip("99999");
        Assert.assertNotNull(zipSort99999);
        Assert.assertEquals(2, zipSort99999.size());

    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
