package com.thesoftwareguild.flooringmastery.dao;

import com.thesoftwareguild.flooringmastery.dto.TaxInfo;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by paulharding on 8/30/16.
 */
public class TaxInfoDaoJUnitTest {
    private TaxDao taxDao;

    private TaxInfo testTaxInfo;

    public TaxInfoDaoJUnitTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        taxDao = ctx.getBean("taxDao", TaxDao.class);
    }

    @Before
    public void setUp() {

        testTaxInfo = new TaxInfo();
        testTaxInfo.setState("Ohio");
        testTaxInfo.setTaxRate(6.25);

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAdd() {

        TaxInfo addedTaxInfo = taxDao.add(testTaxInfo);

        Assert.assertNotNull(addedTaxInfo);
        Assert.assertNotNull(addedTaxInfo.getId());

    }

    @Test
    public void testRead() {

        TaxInfo addedTaxInfo = taxDao.add(testTaxInfo);

        TaxInfo readTaxInfo = taxDao.read(addedTaxInfo.getId());

        Assert.assertNotNull(readTaxInfo);
        Assert.assertNotSame(addedTaxInfo, readTaxInfo);
        Assert.assertEquals("Ohio", readTaxInfo.getState());

        Double expectedTaxRate = 6.25;

        Assert.assertEquals(expectedTaxRate, readTaxInfo.getTaxRate());

    }

    @Test
    public void update() {

        TaxInfo addedTaxInfo = taxDao.add(testTaxInfo);

        TaxInfo readTaxInfo = taxDao.read(addedTaxInfo.getId());
        readTaxInfo.setTaxRate(5.75);

        taxDao.update(readTaxInfo);

        TaxInfo updatedTaxInfo = taxDao.read(readTaxInfo.getId());

        Assert.assertEquals(6.25, addedTaxInfo.getTaxRate());
        Assert.assertEquals(5.75, updatedTaxInfo.getTaxRate());

    }

    @Test
    public void delete() {

        TaxInfo addedTaxInfo = taxDao.add(testTaxInfo);

        TaxInfo readTaxInfo = taxDao.read(addedTaxInfo.getId());

        taxDao.delete(readTaxInfo);

        TaxInfo deletedTaxInfo = taxDao.read(readTaxInfo.getId());

        Assert.assertNull(deletedTaxInfo);

    }

}