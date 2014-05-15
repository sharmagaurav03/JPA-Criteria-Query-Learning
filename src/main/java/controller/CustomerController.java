package controller;

import javax.annotation.Resource;

import model.Customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.CustomerService;

@Controller
@RequestMapping("/customers")
public class CustomerController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CustomerController.class);

	@Resource
	private CustomerService customerService;

	/**
	 * Processes requests to return lists all available persons.
	 * 
	 * @param model
	 * @return The name of the person list view.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody
	Iterable<Customer> findAll() {
		LOGGER.debug("Rendering persons list");
		return customerService.findAll();

	}

}
