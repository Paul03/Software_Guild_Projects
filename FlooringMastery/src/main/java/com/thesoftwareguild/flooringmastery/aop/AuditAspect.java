package com.thesoftwareguild.flooringmastery.aop;

import com.thesoftwareguild.flooringmastery.dao.AuditDao;
import com.thesoftwareguild.flooringmastery.dto.Audit;
import com.thesoftwareguild.flooringmastery.dto.Order;
import com.thesoftwareguild.flooringmastery.utility.FlooringMasteryUtility;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;

/**
 * Created by paulharding on 9/8/16.
 */

public class AuditAspect {

    private AuditDao auditDao;

    public AuditAspect(AuditDao auditDao) {
        this.auditDao = auditDao;
    }

    public void logAudit(JoinPoint jp) {

        Order order = (Order) jp.getArgs()[0];

        Signature methodSignature = jp.getSignature();
        String signatureString = methodSignature.getName();

        Audit audit = new Audit();

        if ( signatureString.equals("add") ) {
            audit.setOperation("OrderAdded");
        } else if (signatureString.equals("update")) {
            audit.setOperation("OrderUpdated");
        } else if (signatureString.equals("delete")) {
            audit.setOperation("OrderDeleted");
        }

        audit.setOrder(order);
        audit.setDate(FlooringMasteryUtility.getCurrentDate());

        auditDao.add(audit);

    }

}
