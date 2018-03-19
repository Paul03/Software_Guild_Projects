package com.thesoftwareguild.flooringmastery.dao;

import com.thesoftwareguild.flooringmastery.dto.Audit;
import com.thesoftwareguild.flooringmastery.dto.Order;
import com.thesoftwareguild.flooringmastery.utility.FlooringMasteryUtility;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Created by paulharding on 9/8/16.
 */

public class AuditDaoImpl implements AuditDao {

    private static final String FILENAME = "audits.txt";
    private static final String TOKEN = ",";
    private List<Audit> auditList = new ArrayList();
    private Integer nextAuditId = 1;

    private OrderDao orderDao;

    public AuditDaoImpl(OrderDao orderDao) {

        this.orderDao = orderDao;

        auditList = decode();

        for (Audit a : auditList) {
            if (a.getAuditId() >= nextAuditId) {
                nextAuditId = a.getAuditId() + 1;
            }
        }

    }

    @Override
    public Audit add(Audit audit) {

        audit.setAuditId(nextAuditId);

        nextAuditId++;

        auditList.add(audit);

        encode();

        return new Audit(audit);
    }

    @Override
    public Audit read(Integer id) {

        for (Audit a : auditList) {
            if (a.getAuditId().equals(id)) {
                return new Audit(a);
            }
        }

        return null;
    }

    @Override
    public void update(Audit audit) {

        for (int i = 0; i < auditList.size(); i++) {
            if (auditList.get(i).getAuditId().equals(audit.getAuditId())) {
                auditList.set(i, audit);
            }
        }

        encode();

    }

    @Override
    public void delete(Audit audit) {

        for (Audit a : auditList) {
            if (a.getAuditId().equals(audit.getAuditId())) {
                auditList.remove(a);
                break;
            }
        }

        encode();

    }

    @Override
    public List<Audit> list() {
        return new ArrayList(auditList);
    }

    private void encode() {

        PrintWriter out = null;

        try {

            out = new PrintWriter(FILENAME);

            for (Audit a : auditList) {

                out.print(a.getAuditId());
                out.print(TOKEN);

                out.print(FlooringMasteryUtility.formatDateForAudits(a.getDate()));
                out.print(TOKEN);

                out.print(a.getOperation());
                out.print(TOKEN);

                out.print(FlooringMasteryUtility.formatDateForDisplay(a.getOrder().getOrderDate()));
                out.print(TOKEN);

                out.print(a.getOrder().getOrderNumber());
                out.print(TOKEN);

                out.println();

            }

        } catch (IOException ex) {

        } finally {
            out.close();
        }

    }

    private List<Audit> decode() {

        List<Audit> tempAuditList = new ArrayList();

        try {

            Scanner sc = new Scanner(new BufferedReader(new FileReader(FILENAME)));

            while (sc.hasNextLine()) {

                String currentLine = sc.nextLine();

                String[] stringParts = currentLine.split(TOKEN);

                Integer auditId = Integer.parseInt(stringParts[0]);
                Integer orderId = Integer.parseInt(stringParts[4]);

                // Create Date Object to be Associated with this Audit Object
                String auditDateString = stringParts[1];
                Integer auditMonth = Integer.parseInt(auditDateString.substring(0, 2));
                Integer auditDay = Integer.parseInt(auditDateString.substring(3, 5));
                Integer auditYear = Integer.parseInt(auditDateString.substring(6, 10));
                Integer auditHour = Integer.parseInt(auditDateString.substring(11, 13));
                Integer auditMinute = Integer.parseInt(auditDateString.substring(14, 16));
                Integer auditSecond = Integer.parseInt(auditDateString.substring(17, 19));
                Date auditDate = new Date(auditYear - 1900, auditMonth - 1, auditDay, auditHour, auditMinute, auditSecond);

                // Create Date Object that represents the order's date
                String orderDateString = stringParts[3];
                Integer orderMonth = Integer.parseInt(orderDateString.substring(0, 2));
                Integer orderDay = Integer.parseInt(orderDateString.substring(3, 5));
                Integer orderYear = Integer.parseInt(orderDateString.substring(6, 10));
                Date orderDate = new Date(orderYear - 1900, orderMonth - 1, orderDay);
                orderDao.setDate(orderDate);

                Audit audit = new Audit();

                audit.setAuditId(auditId);
                audit.setOperation(stringParts[2]);

                Order auditOrder = orderDao.read(orderId);
                if (auditOrder != null) {
                    audit.setOrder(auditOrder);
                } else {
                    auditOrder = new Order();

                    auditOrder.setOrderNumber(orderId);
                    auditOrder.setOrderDate(orderDate);
                    audit.setOrder(auditOrder);
                }

                audit.setDate(auditDate);

                tempAuditList.add(audit);

            }

        } catch (FileNotFoundException ex) {

        }

        return tempAuditList;
    }
}
