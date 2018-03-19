package com.thesoftwareguild.flooringmastery.dto;

/**
 * Created by paulharding on 8/30/16.
 */
public class TaxInfo {

    private Integer id;
    private String state;
    private Double taxRate;

    public TaxInfo() {

    }

    public TaxInfo(TaxInfo taxInfo) {
        this.id = taxInfo.getId();
        this.state = taxInfo.getState();
        this.taxRate = taxInfo.getTaxRate();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }
}
