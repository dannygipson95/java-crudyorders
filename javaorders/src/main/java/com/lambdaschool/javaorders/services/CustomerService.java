package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Customer;
import com.lambdaschool.javaorders.models.Order;

import java.util.List;

public interface CustomerService {
    List<Customer> findAllOrders();

    Customer findCustomerById(long id);

    List<Customer> findByNameLike(String custname);

    Customer save(Customer customer);

    Customer update(Customer customer, long id);
}
