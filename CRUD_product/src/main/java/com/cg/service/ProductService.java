package com.cg.service;

import com.cg.model.ECategory;
import com.cg.model.Product;


import java.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductService implements IProductService{

    private static  Map<Long, Product> products = null;

    static {
        products = new HashMap<>();
        products.put(1L, new Product(1L,"ip 11",10,LocalDate.of(2023,2,1), ECategory.PHONE));
        products.put(2L, new Product(2L,"asus",10,LocalDate.of(2023,2,1), ECategory.LAPTOP));
        products.put(3L, new Product(3L,"sony",10,LocalDate.of(2023,2,1), ECategory.DESKTOP));
    }
    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public void save(Product product) {
        products.put(product.getId(), product);
    }

    @Override
    public Product findById(Long id) {
        return products.get(id);
    }

    @Override
    public void update(Long id, Product product) {
        products.put(id, product);

    }

    @Override
    public void remove(Long id) {
        products.remove(id);
    }
}
