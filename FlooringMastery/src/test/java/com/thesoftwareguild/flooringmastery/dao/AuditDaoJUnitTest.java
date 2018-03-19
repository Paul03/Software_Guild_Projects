package com.thesoftwareguild.flooringmastery.dao;

import com.thesoftwareguild.flooringmastery.dto.Audit;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

/**
 * Created by paulharding on 9/8/16.
 */

public class AuditDaoJUnitTest {

    private AuditDao auditDao;

    private Audit testAudit;

    public AuditDaoJUnitTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        auditDao = ctx.getBean("auditDao", AuditDao.class);
    }

    @Before
    public void setUp() {

        testAudit = new Audit();
        testAudit.setAuditId(10);
        testAudit.setOperation("Edit");
        testAudit.setDate(new Date(116, 9, 8));

    }

    @After
    public void tearDown() {

    }

    @Test
    public void testAdd() {

        Audit addedAudit = auditDao.add(testAudit);

        Assert.assertNotNull(addedAudit);
        Assert.assertNotNull(addedAudit.getAuditId());

    }

    @Test
    public void testRead() {

        Audit addedAudit = auditDao.add(testAudit);

        Audit readAudit = auditDao.read(addedAudit.getAuditId());

        Assert.assertNotNull(readAudit);

        Assert.assertEquals("Edit", readAudit.getOperation());

    }

    @Test
    public void testUpdate() {

        Audit addedAudit = auditDao.add(testAudit);

        Audit readAudit = auditDao.read(addedAudit.getAuditId());
        readAudit.setOperation("Add");

        auditDao.update(readAudit);

        Audit updatedAudit = auditDao.read(readAudit.getAuditId());

        Assert.assertNotSame(addedAudit, updatedAudit);
        Assert.assertEquals("Add", updatedAudit.getOperation());

    }

    @Test
    public void testDelete() {

        Audit addedAudit = auditDao.add(testAudit);

        auditDao.delete(addedAudit);

        Audit deletedAudit = auditDao.read(addedAudit.getAuditId());

        Assert.assertNull(deletedAudit);

    }

}