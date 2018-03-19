/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thesoftwareguild.addressbookweb.tests.dao;

import com.thesoftwareguild.addressbookweb.dao.AddressDao;
import com.thesoftwareguild.addressbookweb.dto.Address;

import java.util.List;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author paulharding
 */
public class AddressBookDaoJUnitTest {

    private AddressDao dao;

    private Address address1;
    private Address address2;
    private Address address3;
    private Address address4;
    private Address address5;

    public AddressBookDaoJUnitTest() {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-persistence.xml");
        this.dao = ctx.getBean("addressDao", AddressDao.class);

    }

    @Before
    public void setUp() {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-persistence.xml");

        JdbcTemplate cleaner = (JdbcTemplate) ctx.getBean("jdbcTemplate");
        cleaner.execute("DELETE FROM Address");

        address1 = new Address();
        address1.setFirstName("Martin");
        address1.setLastName("Stevens");
        address1.setStreetAddress("123 S Main St");
        address1.setCity("Akron");
        address1.setState("OH");
        address1.setZipCode("44308");

        address2 = new Address();
        address2.setFirstName("Karl");
        address2.setLastName("Johnson");
        address2.setStreetAddress("456 E Exchange St");
        address2.setCity("Akron");
        address2.setState("OH");
        address2.setZipCode("44308");

        address3 = new Address();
        address3.setFirstName("Buddy");
        address3.setLastName("Johnson");
        address3.setStreetAddress("918 W Market St");
        address3.setCity("Akron");
        address3.setState("OH");
        address3.setZipCode("44308");

        address4 = new Address();
        address4.setFirstName("Mark");
        address4.setLastName("Carlton");
        address4.setStreetAddress("412 11th St");
        address4.setCity("Youngstown");
        address4.setState("OH");
        address4.setZipCode("n/a");

        address5 = new Address();
        address5.setFirstName("Bob");
        address5.setLastName("Marcus");
        address5.setStreetAddress("823 9th St");
        address5.setCity("Youngstown");
        address5.setState("OH");
        address5.setZipCode("n/a");

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAdd() {

        Address addedAddress = dao.create(address1);

        Integer addedAddressId = addedAddress.getId();

        Assert.assertNotNull(addedAddressId);

    }

    @Test
    public void testRead() {

        Address addedAddress = dao.create(address1);

        Address readAddress = dao.read(addedAddress.getId());

        Assert.assertNotNull(readAddress);
        Assert.assertEquals("Martin", address1.getFirstName());
        Assert.assertEquals("123 S Main St", readAddress.getStreetAddress());

    }

    @Test
    public void testUpdate() {

        Address addedAddress = dao.create(address1);

        Address readAddress = dao.read(addedAddress.getId());

        readAddress.setFirstName("Billy");

        dao.update(readAddress);

        Address readAgainAddress = dao.read(readAddress.getId());

        Assert.assertEquals("Billy", readAgainAddress.getFirstName());

    }

    @Test
    public void testDelete() {

        Address addedAddress = dao.create(address1);

        Address readAddress = dao.read(addedAddress.getId());

        dao.delete(readAddress);

        try {
            Address deletedAddress = dao.read(readAddress.getId());
            
            Assert.assertNull(readAddress);
            
        } catch (EmptyResultDataAccessException ex) {
            
        }

    }

    @Test
    public void testFindByLastName() {

        dao.create(address1);
        dao.create(address2);
        dao.create(address3);

        List<Address> johnsonSearchResultList = dao.findByLastName("Johnson");
        Assert.assertNotNull(johnsonSearchResultList);
        Assert.assertEquals(2, johnsonSearchResultList.size());

        List<Address> stevensSearchResultList = dao.findByLastName("Stevens");
        Assert.assertNotNull(stevensSearchResultList);
        Assert.assertEquals(1, stevensSearchResultList.size());

    }

    @Test
    public void testFindByCity() {

        dao.create(address1);
        dao.create(address2);
        dao.create(address3);
        dao.create(address4);
        dao.create(address5);

        List<Address> akronSearchResultList = dao.findByCity("Akron");
        List<Address> clevelnadSearchResultList = dao.findByCity("Youngstown");
        List<Address> cantonSearchResultList = dao.findByCity("Canton");

        Assert.assertNotNull(akronSearchResultList);
        Assert.assertEquals(3, akronSearchResultList.size());

        Assert.assertNotNull(clevelnadSearchResultList);
        Assert.assertEquals(2, clevelnadSearchResultList.size());

        Assert.assertNotNull(cantonSearchResultList);
        Assert.assertEquals(0, cantonSearchResultList.size());

    }
/*
    @Test
    public void testFindByStateOhio() {

        dao.create(address1);
        dao.create(address2);
        dao.create(address3);
        dao.create(address4);
        dao.create(address5);

        List<Address> stateSortOhio = dao.findByState("Ohio");
        Assert.assertNotNull(stateSortOhio);
        Assert.assertEquals(5, stateSortOhio.size());

    }
*/
    @Test
    public void testFindByStateOH() {

        dao.create(address1);
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

        dao.create(address1);
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

        dao.create(address1);
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

        dao.create(address1);
        dao.create(address2);
        dao.create(address3);
        dao.create(address4);
        dao.create(address5);

        List<Address> zipSort55689 = dao.findByZip("n/a");
        Assert.assertNotNull(zipSort55689);
        Assert.assertEquals(2, zipSort55689.size());

        List<Address> zipSort12345 = dao.findByZip("44308");
        Assert.assertNotNull(zipSort12345);
        Assert.assertEquals(3, zipSort12345.size());

    }

}
