/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.addressbook.tests;

import com.mycompany.addressbook.dao.AddressDao;
import com.mycompany.addressbook.dto.Address;
import java.util.List;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author paulharding
 */
public class AddressBookDaoJUnitTest {
    
    AddressDao dao;
    
    public AddressBookDaoJUnitTest() {
        
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
    public void testAdd() {
        
        Address address1 = new Address(); // Create new Address object
        address1.setAddress("123 Main St");
        
        Address addedAddress = dao.create(address1); // Pass address1 to the DAO (now addedAddress should have an id)
        
        Integer addedAddressId = addedAddress.getId();
        
        Assert.assertNotNull(addedAddressId);
        
    }
    
    @Test
    public void testRead() {
        
        Address address1 = new Address(); // Create new Address object
        address1.setFirstName("Martin");
        address1.setLastName("Stevens");
        address1.setAddress("123 Main St");
        
        Address addedAddress = dao.create(address1); // Pass address1 to the DAO (now addedAddress should have an id)
        
        Address readAddress = dao.read(addedAddress.getId()); // Pass the id of addedAddress into the read method
        
        Assert.assertNotNull(readAddress);
        Assert.assertEquals("Martin", address1.getFirstName());
        Assert.assertEquals("123 Main St", readAddress.getAddress());
        
    }
    
    @Test
    public void testUpdate() {
        
        Address address1 = new Address(); // Create new Address object
        address1.setFirstName("Martin");
        address1.setLastName("Stevens");
        address1.setAddress("123 Main St");
        
        Address addedAddress = dao.create(address1); // Pass address1 to the DAO (now addedAddress should have an id)
        
        Address readAddress = dao.read(addedAddress.getId()); // Pass the id of addedAddress into the read method
        
        readAddress.setFirstName("Billy"); // Change the first name of readAddress
        
        //dao.update(readAddress); // Send the change to the DAO
        
        Address readAgainAddress = dao.read(readAddress.getId());
        
        Assert.assertEquals("Billy", readAgainAddress.getFirstName());
        
    }
    
    @Test
    public void testDelete() {
        
        Address address1 = new Address(); // Create new Address object
        address1.setFirstName("Martin");
        address1.setLastName("Stevens");
        address1.setAddress("123 Main St");
        
        Address addedAddress = dao.create(address1); // Pass address1 to the DAO (now addedAddress should have an id)
        
        Address readAddress = dao.read(addedAddress.getId()); // Pass the id of addedAddress into the read method
        
        dao.delete(readAddress); // Delete read Address from the address book
        
        Address readAgainAddress = dao.read(readAddress.getId());
        
        Assert.assertNull(readAgainAddress);
        
    }
    
    @Test
    public void testFindByLastName() {
        
        Address address1 = new Address(); // Create 3 Address objects
        address1.setLastName("Johnson");

        Address address2 = new Address();
        address2.setLastName("Banks");

        Address address3 = new Address();
        address3.setLastName("Johnson");

        dao.create(address1); // Add the addresses to the dao's list
        dao.create(address2);
        dao.create(address3);

        List<Address> lastNameSort = dao.findByLastName("Johnson");
        Assert.assertNotNull(lastNameSort);
        Assert.assertEquals(2, lastNameSort.size());
        
        List<Address> lastNameSort2 = dao.findByLastName("Banks");
        Assert.assertNotNull(lastNameSort2);
        Assert.assertEquals(1, lastNameSort2.size());
        
    }
    
    @Test
    public void testFindByCity() {

        Address address1 = new Address(); // Create 5 Address objects
        address1.setCity("Akron");

        Address address2 = new Address();
        address2.setCity("Akron");

        Address address3 = new Address();
        address3.setCity("Youngstown");

        Address address4 = new Address();
        address4.setCity("Akron");

        Address address5 = new Address();
        address5.setCity("Youngstown");

        dao.create(address1); // Add the addresses to the dao's list
        dao.create(address2);
        dao.create(address3);
        dao.create(address4);
        dao.create(address5);

        List<Address> citySortAkron = dao.findByCity("Akron");
        List<Address> citySortCleveland = dao.findByCity("Youngstown");
        List<Address> citySortCanton = dao.findByCity("Canton");
        
        Assert.assertNotNull(citySortAkron);
        Assert.assertEquals(3, citySortAkron.size());

        Assert.assertNotNull(citySortCleveland);
        Assert.assertEquals(2, citySortCleveland.size());

        
        Assert.assertNotNull(citySortCanton);
        Assert.assertEquals(0, citySortCanton.size());

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
        address1.setZipCode("12345");

        Address address2 = new Address();
        address2.setZipCode("12345");

        Address address3 = new Address();
        address3.setZipCode("55689");

        Address address4 = new Address();
        address4.setZipCode("55689");

        Address address5 = new Address();
        address5.setZipCode("12345");
        
        dao.create(address1);
        dao.create(address2);
        dao.create(address3);
        dao.create(address4);
        dao.create(address5);

        List<Address> zipSort55689 = dao.findByZip("55689");
        Assert.assertNotNull(zipSort55689);
        Assert.assertEquals(2, zipSort55689.size());

        List<Address> zipSort12345 = dao.findByZip("12345");
        Assert.assertNotNull(zipSort12345);
        Assert.assertEquals(3, zipSort12345.size());

    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
