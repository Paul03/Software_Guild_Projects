package com.thesoftwareguild.flooringmastery;

import com.thesoftwareguild.flooringmastery.controller.OrderController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by paulharding on 8/30/16.
 */
public class App {

    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        OrderController oc = ctx.getBean("orderController", OrderController.class);
        oc.run();

    }
}
