package com.cg.service;

import com.cg.model.Customer;
import com.cg.model.CustomerType;

import java.util.List;

public interface ICustomerTypeService {
    List<CustomerType> findAll();

    void save(CustomerType customerType);

    CustomerType findById(int id);

    void update(int id, CustomerType customerType);

    void remove(int id);
}
