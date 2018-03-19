package com.thesoftwareguild.flooringmastery.dao;

import com.thesoftwareguild.flooringmastery.dto.Audit;

import java.util.List;

/**
 * Created by paulharding on 9/8/16.
 */

public interface AuditDao {

    public Audit add(Audit audit);

    public Audit read(Integer id);

    public void update(Audit audit);

    public void delete(Audit audit);

    public List<Audit> list();

}
