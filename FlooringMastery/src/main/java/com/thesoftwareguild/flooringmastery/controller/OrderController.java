package com.thesoftwareguild.flooringmastery.controller;

import com.mycompany.ui.ConsoleIO;
import com.thesoftwareguild.flooringmastery.dao.*;
import com.thesoftwareguild.flooringmastery.dto.Audit;
import com.thesoftwareguild.flooringmastery.dto.Order;
import com.thesoftwareguild.flooringmastery.dto.Product;
import com.thesoftwareguild.flooringmastery.dto.TaxInfo;
import com.thesoftwareguild.flooringmastery.utility.FlooringMasteryUtility;

import java.util.Date;
import java.util.List;

/**
 * Created by paulharding on 8/30/16.
 */

public class OrderController {

    private final Double MAX_AREA = 999999999.00;

    private OrderDao orderDao;
    private ProductDao productDao;
    private TaxDao taxDao;

    private ConsoleIO io = new ConsoleIO();

    public OrderController(ProductDao productDao, TaxDao taxDao, OrderDao orderDao) {
        this.productDao = productDao;
        this.taxDao = taxDao;
        this.orderDao = orderDao;
    }

    public void run() {

        boolean playAgain = true;

        while (playAgain) {

            displayMainMenu();
            int menuChoice = io.getUserInt("What would you like to do? ", 1, 6);

            switch (menuChoice) {

                case 1:
                    viewOrdersByDate();
                    break;

                case 2:
                    addOrder();
                    break;

                case 3:
                    editOrder();
                    break;

                case 4:
                    removeOrder();
                    break;

                case 5:
                    viewOrderDetails();
                    break;

                case 6:
                    playAgain = false;

            }

        }

    }

    private void viewOrdersByDate() {

        displayHeader("VIEW ORDERS BY DATE");

        boolean keepViewing = true;

        while (keepViewing) {

            Date date = io.getUserDate("Input the date: ");
            orderDao.setDate(date);

            List<Order> ordersOnDate = orderDao.listByDate();

            if (ordersOnDate == null) {
                io.println("");
                io.println("There does not appear to be any order saved from " + FlooringMasteryUtility.formatDateForDisplay(date) + ".");
            } else {
                displayListOfOrders(date, ordersOnDate);
            }

            keepViewing = getKeepDoingDecision("view orders from another date");

        }

    }

    private void addOrder() {

        displayHeader("NEW ORDER");

        boolean keepAdding = true;

        while (keepAdding) {

            io.println("Today's Date: " + FlooringMasteryUtility.formatDateForDisplay(FlooringMasteryUtility.getCurrentDate()));
            io.println("");
            Integer whatDate = io.getUserIntAllowBlank("To add new order to today, press \"Enter.\"\nTo add order to another day, input \"0.\"\n", 0, 0);

            Date orderDate;

            if (whatDate == null) {
                orderDate = FlooringMasteryUtility.getCurrentDate();
                orderDao.setDate(orderDate);
            } else {
                orderDate = io.getUserDate("Input the date the order was placed: ");
                orderDao.setDate(orderDate);
            }

            String name = io.getNonEmptyUserString("Customer's name: ");
            Integer state = getState("Input the number that corresponds to the correct state: ", true);
            Integer productType = getProductType("Input the number that corresponds to the correct product: ", true);
            Double area = io.getUserDouble("Total Square Footage: ", 0, MAX_AREA);

            Order customerOrder = createOrder(name, state, productType, area, orderDate);

            displayOrderDetails(customerOrder);

            int reallyAdd = io.getUserInt("Are you ready to add this order?\nFor YES, input \"1\", For NO, input \"2\" ", 1, 2);

            if (reallyAdd == 1) {
                orderDao.add(customerOrder);
            } else {
                io.println("OK, the order has not been added."); // possibly offer to edit here ?
            }

            keepAdding = getKeepDoingDecision("add another order");

        }

    }

    private void editOrder() {

        displayHeader("EDIT ORDER");

        Date date = io.getUserDate("Input the date the order was placed: ");
        orderDao.setDate(date);

        List<Order> ordersOnDate = orderDao.listByDate();

        if (ordersOnDate != null) {

            displayListOfOrders(date, ordersOnDate);

            int orderNumber = io.getUserInt("Input the order number: ");

            Order editOrder = orderDao.read(orderNumber);

            if (editOrder != null) {

                String name = io.getUserString("Enter customer name (" + editOrder.getCustomerName() + "): ");
                Integer state = getState("Enter customer state (" + editOrder.getTaxInfo().getState() + "): ", false);
                Integer productType = getProductType("Enter customer product (" + editOrder.getProduct().getProductType() + "): ", false);
                Double area = io.getUserDoubleAllowBlank("Enter square footage (" + editOrder.getArea() + "): ", 0, MAX_AREA);

                if (!name.trim().equals("")) {
                    editOrder.setCustomerName(name);
                }

                if (state != null) {
                    TaxInfo taxInfo = taxDao.read(state);
                    editOrder.setTaxInfo(taxInfo);
                }

                if (productType != null) {
                    Product product = productDao.read(productType);
                    editOrder.setProduct(product);
                }

                if (area != null) {
                    editOrder.setArea(area);
                }

                editOrder = setOrderTotals(editOrder);
                orderDao.update(editOrder);
                io.println("Order updated successfully!");

            } else {
                io.println("There does not appear to be an order #" + orderNumber + " from " + FlooringMasteryUtility.formatDateForDisplay(date) + "."); // Allow user option to try again?
            }

        } else {
            io.println("");
            io.println("There does not appear to be any order saved from " + FlooringMasteryUtility.formatDateForDisplay(date) + "."); // Allow user option to try again?
        }

    }

    private void removeOrder() {

        displayHeader("REMOVE ORDER");

        boolean keepRemoving = true;

        while (keepRemoving) {

            Date date = io.getUserDate("Input the date the order was placed:");
            orderDao.setDate(date);

            List<Order> ordersOnDate = orderDao.listByDate();

            if (ordersOnDate != null) {

                displayListOfOrders(date, ordersOnDate);

                int orderNumber = io.getUserInt("Input the order number to be removed: ");

                Order orderToRemove = orderDao.read(orderNumber);

                if (orderToRemove != null) {

                    displayOrderDetails(orderToRemove);

                    int reallyDelete = io.getUserInt("Are you sure you want to delete this order?\nFor YES, input \"1\", For NO, input \"2\" ", 1, 2);

                    if (reallyDelete == 1) {
                        orderDao.delete(orderToRemove);

                        io.println("");
                        io.println("Order #" + orderToRemove.getOrderNumber() + " has been deleted.");
                    } else {
                        io.println("");
                        io.println("OK, Order #" + orderToRemove.getOrderNumber() + " has not been deleted.");
                    }

                } else {
                    io.println("There does not appear to be an order #" + orderNumber + " from " + FlooringMasteryUtility.formatDateForDisplay(date) + "."); // Allow user option to try again?
                }

            } else {
                io.println("");
                io.println("There does not appear to be any order saved from " + FlooringMasteryUtility.formatDateForDisplay(date) + "."); // Allow user option to try again?
            }

            keepRemoving = getKeepDoingDecision("remove another order");

        }

    }

    private void viewOrderDetails() {

        displayHeader("VIEW ORDER DETAILS");

        boolean keepViewing = true;

        while (keepViewing) {

            Date date = io.getUserDate("Input the date the order was placed: ");
            orderDao.setDate(date);

            List<Order> listOfOrdersOnDate = orderDao.listByDate();

            if (listOfOrdersOnDate != null) {

                displayListOfOrders(date, listOfOrdersOnDate);

                io.println("");
                int orderNumber = io.getUserInt("Input the order number: ");

                Order orderToView = orderDao.read(orderNumber);

                if (orderToView != null) {
                    displayOrderDetails(orderToView);
                } else {
                    io.println("");
                    io.println("There does not appear to be an order #" + orderNumber + " from " + FlooringMasteryUtility.formatDateForDisplay(date) + "."); // Allow user option to try again?
                }

            } else {
                io.println("");
                io.println("There does not appear to be any order saved from " + FlooringMasteryUtility.formatDateForDisplay(date) + "."); // Allow user option to try again?
            }

            keepViewing = getKeepDoingDecision("view another order");

        }

    }

    private void displayMainMenu() {

        displayHeader("MAIN MENU");

        io.println("\"1\" View Orders By Date");
        io.println("");
        io.println("\"2\" Add an Order");
        io.println("");
        io.println("\"3\" Edit an Order");
        io.println("");
        io.println("\"4\" Remove an Order");
        io.println("");
        io.println("\"5\" View Details of an Order");
        io.println("");
        io.println("\"6\" Exit");
        io.println("");

    }

    private void displayHeader(String title) {

        io.println("");

        for (int i = 0; i < title.length(); i++) {
            io.print("=");
        }

        io.println("");

        io.println(title);

        for (int i = 0; i < title.length(); i++) {
            io.print("=");
        }

        io.println("");
        io.println("");

    }

    private void displayListOfOrders(Date date, List<Order> ordersOnDate) {

        io.println("");
        io.println("\t\t\t\t\t\t\t\t\tOrders for " + FlooringMasteryUtility.formatDateForDisplay(date));
        io.println("\t\t\t\t\t\t\t\t\t---------------------");
        io.println("");
        for (int i = 0; i < ordersOnDate.size(); i++) {

            if ((i + 1) % 2 == 1) {
                io.print("***  ");
            }

            System.out.format("%4s | %14s | %10s", ordersOnDate.get(i).getOrderNumber(), ordersOnDate.get(i).getCustomerName(), "$" + ordersOnDate.get(i).getTotal());
            io.print("\t    |***|\t");

            if ((i + 1) % 2 == 0) {
                io.println("");
            }
        }

        io.println("");

    }

    private void displayOrderDetails(Order orderToPrint) {

        io.println("");
        io.println("            Order Details ");
        io.println("|---------------------------------------|");
        System.out.format("| %20s | %-14s |%n", "Name", orderToPrint.getCustomerName());
        System.out.format("| %20s | %-14s |%n", "State", orderToPrint.getTaxInfo().getState());
        System.out.format("| %20s | %-14s |%n", "Product Ordered", orderToPrint.getProduct().getProductType());
        System.out.format("| %20s | %-14s |%n", "Job Square Footage", orderToPrint.getArea());
        System.out.format("| %20s   %-14s |%n", "", "");
        System.out.format("| %20s | %-14s |%n", "Cost of Materials", "$" + orderToPrint.getMaterialCost());
        System.out.format("| %20s | %-14s |%n", "Cost of Labor", "$" + orderToPrint.getLaborCost());
        System.out.format("| %20s | %-14s |%n", "Subtotal", "$" + (orderToPrint.getMaterialCost() + orderToPrint.getLaborCost()));
        System.out.format("| %20s | %-14s |%n", "Tax", "$" + orderToPrint.getTax());
        System.out.format("| %20s   %-14s |%n", "", "");
        System.out.format("| %20s | %-14s |%n", "Grand Total", "$" + orderToPrint.getTotal());
        io.println("|---------------------------------------|");
        io.println("");

    }

    private Order createOrder(String name, Integer stateId, Integer productId, Double area, Date dateOfOrder) {

        TaxInfo taxInfo = taxDao.read(stateId);
        Product product = productDao.read(productId);

        Order order = new Order();

        order.setCustomerName(name);
        order.setArea(area);
        order.setProduct(product);
        order.setTaxInfo(taxInfo);
        order.setOrderDate(dateOfOrder);

        order = setOrderTotals(order);

        return order;

    }

    private boolean getKeepDoingDecision(String actionToContinue) {

        boolean continueAction = true;

        io.println("");
        io.println("Input \"0\" to return to Main Menu, or");
        int actionMore = io.getUserInt("Input \"1\" to " + actionToContinue + ": ", 0, 1);

        if (actionMore == 0) {
            continueAction = false;
        } else {
            io.println("");
        }
        return continueAction;

    }

    public Order setOrderTotals(Order o) {

        Double unroundedMaterialCost = o.getProduct().getCostPerSqFt() * o.getArea();
        Double materialCost = ((double) Math.round(unroundedMaterialCost * 100)) / 100;
        o.setMaterialCost(materialCost);

        Double unroundedLaborCost = o.getProduct().getLaborCostPerSqFt() * o.getArea();
        Double laborCost = ((double) Math.round(unroundedLaborCost * 100)) / 100;
        o.setLaborCost(laborCost);

        Double subtotal = unroundedMaterialCost + unroundedLaborCost;

        Double taxRateDecimal = o.getTaxInfo().getTaxRate() / 100;

        Double unroundedTax = subtotal * taxRateDecimal;
        double tax = ((double) Math.round(unroundedTax * 100)) / 100; // round the tax to 2 decimals
        o.setTax(tax);

        double unroundedTotal = subtotal + tax;
        double total = ((double) Math.floor(unroundedTotal * 100)) / 100; // round the total to 2 decimals
        o.setTotal(total);

        return o;

    }

    private Integer getState(String prompt, boolean isRequiredField) {

        io.println("");

        if (isRequiredField) {
            io.println("Customer's State: ");
        }

        List<TaxInfo> listOfStates = taxDao.list();

        for (int i = 0; i < listOfStates.size(); i++) {
            io.print("(" + (i + 1) + ") " + listOfStates.get(i).getState());
            io.print("\t\t");

            if ((i + 1) % 4 == 0) {
                io.println("");
            }
        }

        if (isRequiredField) {
            return io.getUserInt(prompt, 1, listOfStates.size());
        } else {
            return io.getUserIntAllowBlank(prompt, 1, listOfStates.size());
        }
    }

    private Integer getProductType(String prompt, boolean isRequiredField) {

        io.println("");

        if (isRequiredField) {
            io.println("Customer's Product: ");
        }

        List<Product> listOfProducts = productDao.list();

        for (int i = 0; i < listOfProducts.size(); i++) {
            io.print("(" + (i + 1) + ") " + listOfProducts.get(i).getProductType());
            io.print("\t\t");

            if ((i + 1) % 4 == 0) {
                io.println("");
            }
        }

        Integer result;
        if (isRequiredField) {
            result = io.getUserInt(prompt, 1, listOfProducts.size());
        } else {
            result = io.getUserIntAllowBlank(prompt, 1, listOfProducts.size());
        }

        io.println("");

        return result;

    }

}
