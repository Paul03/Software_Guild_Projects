package com.thesoftwareguild.flooringmastery.dao;

import com.thesoftwareguild.flooringmastery.dto.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by paulharding on 8/30/16.
 */
public class ProductDaoImpl implements ProductDao {

    private final String TOKEN = ",";
    private List<Product> productList = new ArrayList();
    private Integer nextId = 1;

    public ProductDaoImpl() {

        productList = decode();

        for (Product p : productList) {
            if (p.getId() >= nextId) {
                nextId = p.getId() + 1;
            }
        }

    }

    @Override
    public Product add(Product product) {

        product.setId(nextId);

        nextId++;

        productList.add(product);

        encode();

        return new Product(product);
    }

    @Override
    public Product read(Integer id) {

        for (Product p : productList) {
            if (p.getId().equals(id)) {
                return new Product(p);
            }
        }

        return null;
    }

    @Override
    public void update(Product product) {

        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getId().equals(product.getId())) {
                productList.set(i, product);
            }
        }

        encode();

    }

    @Override
    public void delete(Product product) {

        for (Product p : productList) {
            if (p.getId().equals(product.getId())) {
                productList.remove(p);
                break;
            }
        }

        encode();

    }

    @Override
    public List<Product> list() {
        return new ArrayList(productList);
    }

    private void encode() {

        PrintWriter out = null;

        try {

            out = new PrintWriter(new FileWriter("Data-Products.txt"));

            for (Product p : productList) {

                out.print(p.getId());
                out.print(TOKEN);

                out.print(p.getProductType());
                out.print(TOKEN);

                out.print(p.getCostPerSqFt());
                out.print(TOKEN);

                out.print(p.getLaborCostPerSqFt());
                out.print(TOKEN);

                out.println();

            }


        } catch (IOException ex) {

        } finally {
            out.close();
        }

    }

    private List<Product> decode() {

        List<Product> tempProductList = new ArrayList();

        try {

            Scanner sc = new Scanner(new BufferedReader(new FileReader("Data-Products.txt")));

            while (sc.hasNextLine()) {

                String currentLine = sc.nextLine();

                String[] stringParts = currentLine.split(TOKEN);

                Integer id = Integer.parseInt(stringParts[0]);
                Double cost = Double.parseDouble(stringParts[2]);
                Double laborCost = Double.parseDouble(stringParts[3]);

                Product product = new Product();

                product.setId(id);
                product.setProductType(stringParts[1]);
                product.setCostPerSqFt(cost);
                product.setLaborCostPerSqFt(laborCost);

                tempProductList.add(product);

            }

        } catch (FileNotFoundException ex) {

        }

        return tempProductList;
    }

}
