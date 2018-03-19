package com.thesoftwareguild.flooringmastery.dao;

import com.thesoftwareguild.flooringmastery.dto.Product;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 * Created by paulharding on 8/30/16.
 */
public class ProductDaoJUnitTest {

    private ProductDao productDao;

    private Product testProduct;

    public ProductDaoJUnitTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        productDao = ctx.getBean("productDao", ProductDao.class);
    }

    @Before
    public void setUp() {

        testProduct = new Product();
        testProduct.setProductType("Lumber");
        testProduct.setCostPerSqFt(9.25);
        testProduct.setLaborCostPerSqFt(8.21);

    }

    @After
    public void tearDown() {

    }

    @Test
    public void testAdd() {

        Product addedProduct = productDao.add(testProduct);

        Assert.assertNotNull(addedProduct);
        Assert.assertNotNull(addedProduct.getId());

    }

    @Test
    public void testRead() {

        Product addedProduct = productDao.add(testProduct);

        Product readProduct = productDao.read(addedProduct.getId());

        Assert.assertNotNull(readProduct);

        Double expectedCostPerSqFt = 9.25;

        Assert.assertEquals(expectedCostPerSqFt, readProduct.getCostPerSqFt());

    }

    @Test
    public void testUpdate() {

        Product addedProduct = productDao.add(testProduct);

        Product readProduct = productDao.read(addedProduct.getId());
        readProduct.setProductType("Wood");

        productDao.update(readProduct);

        Product updatedProduct = productDao.read(readProduct.getId());

        Assert.assertNotSame(addedProduct, updatedProduct);
        Assert.assertEquals("Wood", updatedProduct.getProductType());

    }

    @Test
    public void testDelete() {

        Product addedProduct = productDao.add(testProduct);

        productDao.delete(addedProduct);

        Product deletedProduct = productDao.read(addedProduct.getId());

        Assert.assertNull(deletedProduct);

    }

}