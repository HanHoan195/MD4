package com.cg.service;

import com.cg.model.Customer;
import com.cg.model.CustomerType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerTypeService implements ICustomerTypeService{

    private static final Map<Integer, CustomerType> customerTypes;

    static {

        customerTypes = new HashMap<>();
        customerTypes.put(1, new CustomerType(1, "vip"));
        customerTypes.put(2, new CustomerType(2, "pro"));
        customerTypes.put(3, new CustomerType(3, "normal"));
    }
    @Override
    public List<CustomerType> findAll() {
        return new ArrayList<>(customerTypes.values());
    }



    @Override
    public void save(CustomerType customerType) {
        customerTypes.put(customerType.getId(), customerType);
    }

    @Override
    public CustomerType findById(int id) {
        return customerTypes.get(id);
    }



    @Override
    public void update(int id, CustomerType customerType) {
        customerTypes.put(id, customerType);

    }

    @Override
    public void remove(int id) {
        customerTypes.remove(id);
    }
}
