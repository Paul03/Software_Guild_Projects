package com.thesoftwareguild.flooringmastery.dao;

import com.thesoftwareguild.flooringmastery.dto.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paulharding on 8/30/16.
 */
public class ProductDaoInMemoryImpl implements ProductDao {

    private List<Product> productList = new ArrayList();
    private Integer nextId = 1;

    @Override
    public Product add(Product product) {

        product.setId(nextId);

        nextId++;

        productList.add(product);

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

    }

    @Override
    public void delete(Product product) {

        for (Product p : productList) {
            if (p.getId().equals(product.getId())) {
                productList.remove(p);
                break;
            }
        }

    }

    @Override
    public List<Product> list() {
        return new ArrayList(productList);
    }

}
