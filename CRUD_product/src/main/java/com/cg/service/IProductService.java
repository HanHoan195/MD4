package com.cg.service;

import com.cg.model.Product;

import java.util.List;

public interface IProductService {
    List<Product> findAll();
    void save(Product product);
    Product findById(Long id);
    void update(Long id,Product product);
    void remove (Long id);
}
