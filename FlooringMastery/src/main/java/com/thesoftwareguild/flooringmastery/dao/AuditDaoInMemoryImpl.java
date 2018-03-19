package com.thesoftwareguild.flooringmastery.dao;

import com.thesoftwareguild.flooringmastery.dto.Audit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paulharding on 9/8/16.
 */

public class AuditDaoInMemoryImpl implements AuditDao {

    private List<Audit> auditList = new ArrayList();
    private Integer nextAuditId = 1;

    @Override
    public Audit add(Audit audit) {

        audit.setAuditId(nextAuditId);

        nextAuditId++;

        auditList.add(audit);

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

    }

    @Override
    public void delete(Audit audit) {

        for (Audit a : auditList) {
            if (a.getAuditId().equals(audit.getAuditId())) {
                auditList.remove(a);
                break;
            }
        }

    }

    @Override
    public List<Audit> list() {
        return new ArrayList(auditList);
    }
}
