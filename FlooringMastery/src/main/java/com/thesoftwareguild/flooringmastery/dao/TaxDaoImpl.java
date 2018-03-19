package com.thesoftwareguild.flooringmastery.dao;

import com.thesoftwareguild.flooringmastery.dto.TaxInfo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by paulharding on 8/30/16.
 */
public class TaxDaoImpl implements TaxDao {

    public static final String TOKEN = ",";
    private List<TaxInfo> taxInfoList = new ArrayList();
    private Integer nextId = 1;

    public TaxDaoImpl() {

        taxInfoList = decode();

    }

    @Override
    public TaxInfo add(TaxInfo taxInfo) {

        taxInfo.setId(nextId);

        nextId++;

        taxInfoList.add(taxInfo);

        encode();

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

        encode();

    }

    @Override
    public void delete(TaxInfo taxInfo) {

        for (TaxInfo t : taxInfoList) {
            if (t.getId().equals(taxInfo.getId())) {
                taxInfoList.remove(t);
                break;
            }
        }

        encode();

    }

    @Override
    public List<TaxInfo> list() {
        return new ArrayList(taxInfoList);
    }

    private void encode() {

        PrintWriter out = null;

        try {

            out = new PrintWriter(new FileWriter("Data-Taxes.txt"));

            for (TaxInfo t : taxInfoList) {

                out.print(t.getId());
                out.print(TOKEN);

                out.print(t.getState());
                out.print(TOKEN);

                out.print(t.getTaxRate());
                out.print(TOKEN);

                out.println();

            }

        } catch(IOException ex) {

        } finally {
            out.close();
        }

    }

    private List<TaxInfo> decode() {

        List<TaxInfo> tempTaxInfoList = new ArrayList();



        try {

            Scanner sc = new Scanner(new BufferedReader(new FileReader("Data-Taxes.txt")));

            while (sc.hasNextLine()) {

                String currentLine = sc.nextLine();

                String[] stringParts = currentLine.split(TOKEN);

                Integer id = Integer.parseInt(stringParts[0]);
                Double taxRate = Double.parseDouble(stringParts[2]);

                TaxInfo taxInfo = new TaxInfo();
                taxInfo.setId(id);
                taxInfo.setState(stringParts[1]);
                taxInfo.setTaxRate(taxRate);

                tempTaxInfoList.add(taxInfo);

            }

        }catch (FileNotFoundException ex) {

        }


        return tempTaxInfoList;
    }

}
