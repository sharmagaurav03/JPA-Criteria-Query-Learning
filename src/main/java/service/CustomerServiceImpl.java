package service;

import javax.annotation.Resource;

import model.Customer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Resource
	private CustomerRepository customerRepository;

	@Override
	@Transactional
	public Iterable<Customer> findAll() {

		return customerRepository.findAll();

	}
}
