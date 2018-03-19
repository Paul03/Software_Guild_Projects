package com.thesoftwareguild.flooringmastery.dao;

import com.thesoftwareguild.flooringmastery.dto.Order;
import com.thesoftwareguild.flooringmastery.utility.FlooringMasteryUtility;

import java.util.*;

/**
 * Created by paulharding on 8/30/16.
 */
public class OrderDaoInMemoryImpl implements OrderDao {

    private Integer nextOrderNumber = 1;
    private Date date = FlooringMasteryUtility.getCurrentDate();

    public Map<String, List<Order>> orderMap = new HashMap();

    ProductDao productDao;
    TaxDao taxDao;

    public OrderDaoInMemoryImpl(ProductDao productDao, TaxDao taxDao) {
        this.productDao = productDao;
        this.taxDao = taxDao;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public Order add(Order order) {

        order.setOrderNumber(nextOrderNumber);

        nextOrderNumber++;

        List<Order> todaysOrders;

        todaysOrders = orderMap.get(FlooringMasteryUtility.formatDateForFiles(date));

        if (todaysOrders == null) {
            orderMap.put(FlooringMasteryUtility.formatDateForFiles(date), new ArrayList());
            todaysOrders = orderMap.get(FlooringMasteryUtility.formatDateForFiles(date));
        }

        todaysOrders.add(order);

        return new Order(order);
    }

    @Override
    public Order read(Integer orderNumber) {

        List<Order> todaysOrders = orderMap.get(FlooringMasteryUtility.formatDateForFiles(date));

        for (Order o : todaysOrders) {
            if (o.getOrderNumber().equals(orderNumber)) {
                return new Order(o);
            }
        }

        return null;
    }

    @Override
    public void update(Order order) {

        List<Order> todaysOrders = orderMap.get(FlooringMasteryUtility.formatDateForFiles(date));

        for (int i = 0; i < todaysOrders.size(); i++) {
            if (todaysOrders.get(i).getOrderNumber().equals(order.getOrderNumber())) {
                todaysOrders.set(i, order);
            }
        }

    }

    @Override
    public void delete(Order order) {

        List<Order> todaysOrders = orderMap.get(FlooringMasteryUtility.formatDateForFiles(date));

        for (Order o : todaysOrders) {
            if (o.getOrderNumber().equals(order.getOrderNumber())) {
                todaysOrders.remove(o);
                break;
            }
        }

    }

    @Override
    public List<Order> listByDate() {

        List<Order> daysOrders;

        daysOrders = orderMap.get(FlooringMasteryUtility.formatDateForFiles(date));

        if (daysOrders == null) {
            return null;
        } else {
            orderMap.put(FlooringMasteryUtility.formatDateForFiles(date), daysOrders);
        }

        return new ArrayList(daysOrders);
    }

    @Override
    public List<Order> searchByDate() {
        return null;
    }

}
