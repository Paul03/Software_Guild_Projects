package com.thesoftwareguild.flooringmastery.dao;

import com.thesoftwareguild.flooringmastery.dto.Product;

import java.util.List;

/**
 * Created by paulharding on 8/30/16.
 */
public interface ProductDao {

    public Product add(Product product);

    public Product read(Integer id);

    public void update(Product product);

    public void delete(Product product);

    public List<Product> list();

}
