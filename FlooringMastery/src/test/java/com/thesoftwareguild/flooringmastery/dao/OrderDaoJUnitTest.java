package com.thesoftwareguild.flooringmastery.dao;

import com.thesoftwareguild.flooringmastery.dto.Order;
import com.thesoftwareguild.flooringmastery.dto.Product;
import com.thesoftwareguild.flooringmastery.dto.TaxInfo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by paulharding on 8/30/16.
 */
public class OrderDaoJUnitTest {

    OrderDao orderDao;

    Order testOrder;
    Product testProduct;
    TaxInfo testTaxInfo;

    public OrderDaoJUnitTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        orderDao = ctx.getBean("orderDao", OrderDao.class);
    }

    @Before
    public void setUp() {

        testOrder = new Order();
        testOrder.setCustomerName("Jones");
        testOrder.setArea(42.2);

        testProduct = new Product();
        testProduct.setProductType("Lumber");
        testProduct.setCostPerSqFt(9.25);
        testProduct.setLaborCostPerSqFt(8.21);

        testOrder.setProduct(testProduct);

        testTaxInfo = new TaxInfo();
        testTaxInfo.setState("Ohio");
        testTaxInfo.setTaxRate(6.25);

        testOrder.setTaxInfo(testTaxInfo);

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAdd() {


        Order addedOrder = orderDao.add(testOrder);

        Assert.assertNotNull(addedOrder);
        Assert.assertNotNull(addedOrder.getOrderNumber());

        Assert.assertEquals("Jones", addedOrder.getCustomerName());

    }

    @Test
    public void testRead() {

        Order addedOrder = orderDao.add(testOrder);

        Order readOrder = orderDao.read(addedOrder.getOrderNumber());

        Assert.assertNotNull(readOrder);
        Assert.assertEquals("Jones", readOrder.getCustomerName());

    }

    @Test
    public void testUpdate() {

        Order addedOrder = orderDao.add(testOrder);

        Order readOrder = orderDao.read(addedOrder.getOrderNumber());

        readOrder.setCustomerName("Martin");

        orderDao.update(readOrder);

        Order updatedOrder = orderDao.read(readOrder.getOrderNumber());

        Assert.assertEquals("Martin", updatedOrder.getCustomerName());

    }

    @Test
    public void testDelete() {

        Order addedOrder = orderDao.add(testOrder);

        orderDao.delete(addedOrder);

        Order readOrder = orderDao.read(addedOrder.getOrderNumber());

        Assert.assertNull(readOrder);

        List<Order> tempOrderList = orderDao.listByDate();

        Assert.assertEquals(0, tempOrderList.size());

    }

}