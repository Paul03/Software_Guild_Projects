package com.thesoftwareguild.flooringmastery.dto;

import java.util.Date;

/**
 * Created by paulharding on 8/30/16.
 */

public class Order {

    private Integer orderNumber;
    private Date orderDate;
    private String customerName;
    private Double area;
    private Product product;
    private TaxInfo taxInfo;
    private Double materialCost;
    private Double laborCost;
    private Double tax;
    private Double total;

    public Order() {

    }

    public Order(Order o) {
        this.orderNumber = o.getOrderNumber();
        this.orderDate = o.getOrderDate();
        this.customerName = o.getCustomerName();
        this.area = o.getArea();
        this.product = o.getProduct();
        this.taxInfo = o.getTaxInfo();
        this.materialCost = o.getMaterialCost();
        this.laborCost = o.getLaborCost();
        this.tax = o.getTax();
        this.total = o.getTotal();
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public TaxInfo getTaxInfo() {
        return taxInfo;
    }

    public void setTaxInfo(TaxInfo taxInfo) {
        this.taxInfo = taxInfo;
    }

    public Double getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(Double materialCost) {
        this.materialCost = materialCost;
    }

    public Double getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(Double laborCost) {
        this.laborCost = laborCost;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
