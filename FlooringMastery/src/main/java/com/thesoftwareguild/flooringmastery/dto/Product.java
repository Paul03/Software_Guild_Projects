package com.thesoftwareguild.flooringmastery.dto;

/**
 * Created by paulharding on 8/30/16.
 */
public class Product {

    private Integer id;
    private String productType;
    private Double costPerSqFt;
    private Double laborCostPerSqFt;

    public Product() {

    }

    public Product(Product p) {
        this.id = p.getId();
        this.productType = p.getProductType();
        this.costPerSqFt = p.getCostPerSqFt();
        this.laborCostPerSqFt = p.getLaborCostPerSqFt();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Double getCostPerSqFt() {
        return costPerSqFt;
    }

    public void setCostPerSqFt(Double costPerSqFt) {
        this.costPerSqFt = costPerSqFt;
    }

    public Double getLaborCostPerSqFt() {
        return laborCostPerSqFt;
    }

    public void setLaborCostPerSqFt(Double laborCostPerSqFt) {
        this.laborCostPerSqFt = laborCostPerSqFt;
    }
}
