package com.thesoftwareguild.flooringmastery.dao;

import com.thesoftwareguild.flooringmastery.dto.TaxInfo;

import java.util.List;

/**
 * Created by paulharding on 8/30/16.
 */
public interface TaxDao {

    public TaxInfo add(TaxInfo taxInfo);

    public TaxInfo read(Integer id);

    public void update(TaxInfo taxInfo);

    public void delete(TaxInfo taxInfo);

    public List<TaxInfo> list();

}
