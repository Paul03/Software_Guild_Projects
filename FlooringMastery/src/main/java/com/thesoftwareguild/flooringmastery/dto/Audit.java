package com.thesoftwareguild.flooringmastery.dto;

import java.util.Date;

/**
 * Created by paulharding on 9/8/16.
 */

public class Audit {

    private Integer auditId;
    private Order order;
    private String operation;
    private Date date;

    public Audit() {

    }

    public Audit(Audit a) {
        this.auditId = a.getAuditId();
        this.order = a.getOrder();
        this.operation = a.getOperation();
        this.date = a.getDate();
    }

    public Integer getAuditId() {
        return auditId;
    }

    public void setAuditId(Integer auditId) {
        this.auditId = auditId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
