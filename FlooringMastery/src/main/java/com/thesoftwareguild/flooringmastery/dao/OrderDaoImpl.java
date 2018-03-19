package com.thesoftwareguild.flooringmastery.dao;

import com.thesoftwareguild.flooringmastery.dto.Order;
import com.thesoftwareguild.flooringmastery.dto.Product;
import com.thesoftwareguild.flooringmastery.dto.TaxInfo;
import com.thesoftwareguild.flooringmastery.utility.FlooringMasteryUtility;

import java.io.*;
import java.util.*;

/**
 * Created by paulharding on 8/30/16.
 */

public class OrderDaoImpl implements OrderDao {

    private final String TOKEN = ",";
    private Integer nextOrderNumber = 1;
    private Date date = FlooringMasteryUtility.getCurrentDate();

    private ProductDao productDao;
    private TaxDao taxDao;

    private Map<String, List<Order>> orderMap = new HashMap();

    public OrderDaoImpl(ProductDao productDao, TaxDao taxDao) {
        this.productDao = productDao;
        this.taxDao = taxDao;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public Order add(Order order) {

        // Make sure that there is an ArrayList containing all of the day's orders
        // Otherwise, when encode happens, only the new order (I think) will appear
        // in the txt document (old ones gone).
        List<Order> listOfOrders = decode();
        if (listOfOrders == null) {
            listOfOrders = new ArrayList();
            orderMap.put(FlooringMasteryUtility.formatDateForFiles(date), listOfOrders);
        }

        for (Order o : listOfOrders) { // set the next order number
            if (o.getOrderNumber() >= nextOrderNumber) {
                nextOrderNumber = o.getOrderNumber() + 1;
            }
        }

        order.setOrderNumber(nextOrderNumber);

        nextOrderNumber = 1;

        List<Order> daysOrders = orderMap.get(FlooringMasteryUtility.formatDateForFiles(date));

        daysOrders.add(order);

        encode();

        return new Order(order);
    }

    @Override
    public Order read(Integer orderNumber) {

        List<Order> listOfOrders = decode();
        if (listOfOrders == null) {
            listOfOrders = new ArrayList();
            orderMap.put(FlooringMasteryUtility.formatDateForFiles(date), listOfOrders);
        }

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

        encode();

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

        encode();

    }

    @Override
    public List<Order> listByDate() {

        List<Order> daysOrders;

        daysOrders = orderMap.get(FlooringMasteryUtility.formatDateForFiles(date));

        if (daysOrders == null) {
            daysOrders = decode();

            if (daysOrders == null) {
                return null;
            } else {
                orderMap.put(FlooringMasteryUtility.formatDateForFiles(date), daysOrders);
            }
        }

        return new ArrayList(daysOrders);
    }

    private void encode() {

        String filename = "Orders/ORDERS_" + FlooringMasteryUtility.formatDateForFiles(date) + ".txt";

        PrintWriter out = null;

        try {

            out = new PrintWriter(new FileWriter(filename));

            List<Order> todaysOrders = orderMap.get(FlooringMasteryUtility.formatDateForFiles(date));

            for (Order o : todaysOrders) {

                out.print(o.getOrderNumber());
                out.print(TOKEN);

                String customerName = FlooringMasteryUtility.checkForToken(o.getCustomerName(), TOKEN);
                out.print(customerName);
                out.print(TOKEN);

                String state = FlooringMasteryUtility.checkForToken(o.getTaxInfo().getState(), TOKEN);
                out.print(state);
                out.print(TOKEN);

                out.print(o.getTaxInfo().getTaxRate());
                out.print(TOKEN);

                String productType = FlooringMasteryUtility.checkForToken(o.getProduct().getProductType(), TOKEN);
                out.print(productType);
                out.print(TOKEN);

                out.print(o.getArea());
                out.print(TOKEN);

                out.print(o.getProduct().getCostPerSqFt());
                out.print(TOKEN);

                out.print(o.getProduct().getLaborCostPerSqFt());
                out.print(TOKEN);

                out.print(o.getMaterialCost());
                out.print(TOKEN);

                out.print(o.getLaborCost());
                out.print(TOKEN);

                out.print(o.getTax());
                out.print(TOKEN);

                out.print(o.getTotal());
                out.println();

            }

        } catch (IOException ex) {

        } finally {
            out.close();
        }

    }

    private List<Order> decode() {

        /*
        This method returns an ArrayList containing all the order objects from the date that 'date' is currently set to.
         */

        String formattedDate = FlooringMasteryUtility.formatDateForFiles(date);

        String filename = "Orders/ORDERS_" + formattedDate + ".txt";

        List<Order> daysOrders = new ArrayList();

        try {

            Scanner sc = new Scanner(new BufferedReader(new FileReader(filename)));

            while (sc.hasNextLine()) {

                String currentLine = sc.nextLine();

                List<String> stringParts = FlooringMasteryUtility.generateArrayToDecode(currentLine, TOKEN);


                // Create TaxInfo Object
                String state = stringParts.get(2);
                Double taxRate = Double.parseDouble(stringParts.get(3));

                TaxInfo taxInfo = new TaxInfo();

                taxInfo.setState(state);
                taxInfo.setTaxRate(taxRate);


                // Create Product Object
                String productType = stringParts.get(4); //
                Double costPerSqFt = Double.parseDouble(stringParts.get(6));
                Double laborCostPerSqFt = Double.parseDouble(stringParts.get(7));

                Product product = new Product();

                product.setProductType(productType);
                product.setCostPerSqFt(costPerSqFt);
                product.setLaborCostPerSqFt(laborCostPerSqFt);

                // Create Date Object
                Integer month = Integer.parseInt( formattedDate.substring(0, 2) );
                Integer day = Integer.parseInt( formattedDate.substring(2, 4) );
                Integer year = Integer.parseInt( formattedDate.substring(4, 8) );
                Date date = new Date(year - 1900, month - 1, day);


                // Create Order Object
                Integer orderNumber = Integer.parseInt(stringParts.get(0));
                String customerName = stringParts.get(1);
                Double area = Double.parseDouble(stringParts.get(5));
                Double materialCost = Double.parseDouble(stringParts.get(8));
                Double laborCost = Double.parseDouble(stringParts.get(9));
                Double tax = Double.parseDouble(stringParts.get(10));
                Double total = Double.parseDouble(stringParts.get(11));

                Order order = new Order();

                order.setOrderNumber(orderNumber);
                order.setOrderDate(date);
                order.setCustomerName(customerName);
                order.setArea(area);
                order.setProduct(product);
                order.setTaxInfo(taxInfo);
                order.setMaterialCost(materialCost);
                order.setLaborCost(laborCost);
                order.setTax(tax);
                order.setTotal(total);

                daysOrders.add(order);

            }

            orderMap.put(FlooringMasteryUtility.formatDateForFiles(date), daysOrders);
            return daysOrders;

        } catch (FileNotFoundException ex) {
            return null;
        }


    }

    @Override
    public List<Order> searchByDate() {
        return null;
    }


}
