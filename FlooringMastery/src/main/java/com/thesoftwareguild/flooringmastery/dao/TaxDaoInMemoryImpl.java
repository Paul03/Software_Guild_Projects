package com.thesoftwareguild.flooringmastery.dao;

import com.thesoftwareguild.flooringmastery.dto.TaxInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paulharding on 8/30/16.
 */
public class TaxDaoInMemoryImpl implements TaxDao {

    private List<TaxInfo> taxInfoList = new ArrayList();
    private Integer nextId = 1;

    @Override
    public TaxInfo add(TaxInfo taxInfo) {

        taxInfo.setId(nextId);

        nextId++;

        taxInfoList.add(taxInfo);

        return new TaxInfo(taxInfo);
    }

    @Override
    public TaxInfo read(Integer id) {

        for (TaxInfo t : taxInfoList) {
            if (t.getId().equals(id)) {

                return new TaxInfo(t);

            }
        }

        return null;
    }

    @Override
    public void update(TaxInfo taxInfo) {

        for (int i = 0; i < taxInfoList.size(); i++) {
            if (taxInfoList.get(i).getId().equals(taxInfo.getId())) {
                taxInfoList.set(i, taxInfo);
            }
        }

    }

    @Override
    public void delete(TaxInfo taxInfo) {

        for (TaxInfo t : taxInfoList) {
            if (t.getId().equals(taxInfo.getId())) {
                taxInfoList.remove(t);
                break;
            }
        }

    }

    @Override
    public List<TaxInfo> list() {
        return new ArrayList(taxInfoList);
    }

}
