package service;

import model.Customer;

public interface CustomerService {

	Iterable<Customer> findAll();
}
