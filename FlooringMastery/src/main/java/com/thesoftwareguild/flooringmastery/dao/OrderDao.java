package com.thesoftwareguild.flooringmastery.dao;

import com.thesoftwareguild.flooringmastery.dto.Order;
import java.util.Date;
import java.util.List;

/**
 * Created by paulharding on 8/30/16.
 */
public interface OrderDao {

    public void setDate(Date date);

    public Order add(Order order);

    public Order read(Integer orderNumber);

    public void update(Order order);

    public void delete(Order order);

    public List<Order> listByDate();

    public List<Order> searchByDate();

}
