package com.thesoftwareguild.flooringmastery.controller;

import com.thesoftwareguild.flooringmastery.dto.Order;
import com.thesoftwareguild.flooringmastery.dto.Product;
import com.thesoftwareguild.flooringmastery.dto.TaxInfo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by paulharding on 9/2/16.
 */
public class OrderControllerJUnitTest {

    OrderController oc;

    Order order1;
    Order order2;

    Product product1;
    Product product2;

    TaxInfo taxInfo1;
    TaxInfo taxInfo2;

    public OrderControllerJUnitTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        oc = ctx.getBean("orderController", OrderController.class);
    }

    @Before
    public void setUp() {

        order1 = new Order();
        order2 = new Order();

        product1 = new Product();
        product2 = new Product();

        taxInfo1 = new TaxInfo();
        taxInfo2 = new TaxInfo();

        product1.setProductType("Carpet");
        product1.setCostPerSqFt(2.25);
        product1.setLaborCostPerSqFt(2.10);

        product2.setProductType("Laminate");
        product2.setCostPerSqFt(1.75);
        product2.setLaborCostPerSqFt(2.10);

        taxInfo1.setState("PA");
        taxInfo1.setTaxRate(6.75);

        taxInfo2.setState("MI");
        taxInfo2.setTaxRate(5.75);

        order1.setCustomerName("Steven");
        order1.setArea(250.00);
        order1.setProduct(product1);
        order1.setTaxInfo(taxInfo1);

        order2.setCustomerName("Bill");
        order2.setArea(500.00);
        order2.setProduct(product2);
        order2.setTaxInfo(taxInfo2);

    }

    @After
    public void tearDown() {

    }

    @Test
    public void testSetOrderTotalsOrder1() {

        order1 = oc.setOrderTotals(order1);

        Double expectedMaterialCost = 562.50;
        Double expectedLaborCost = 525.00;
        Double expectedTax = 73.41;
        Double expectedTotal = 1160.91;

        Assert.assertEquals(expectedMaterialCost, order1.getMaterialCost());
        Assert.assertEquals(expectedLaborCost, order1.getLaborCost());
        Assert.assertEquals(expectedTax, order1.getTax());
        Assert.assertEquals(expectedTotal, order1.getTotal());

    }

    @Test
    public void testSetOrderTotalsOrder2() {

        order2 = oc.setOrderTotals(order2);

        Double expectedMaterialCost = 875.00;
        Double expectedLaborCost = 1050.00;
        Double expectedTax = 110.69;
        Double expectedTotal = 2035.69;

        Assert.assertEquals(expectedMaterialCost, order2.getMaterialCost());
        Assert.assertEquals(expectedLaborCost, order2.getLaborCost());
        Assert.assertEquals(expectedTax, order2.getTax());
        Assert.assertEquals(expectedTotal, order2.getTotal());

    }

}