package controller;

import javax.annotation.Resource;

import model.Person;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.PersonService;
import exception.PersonNotFoundException;

@Controller
@RequestMapping("/persons")
public class PersonController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonController.class);

	@Resource
	private PersonService personService;

	/**
	 * Processes requests to return lists all available persons.
	 * 
	 * @param model
	 * @return The name of the person list view.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody
	Iterable<Person> findAll() {
		LOGGER.debug("Rendering persons list");
		Iterable<Person> persons = personService.findAll();
		return persons;
	}

	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public @ResponseBody
	Long countAll() {
		LOGGER.debug("counting persons");
		Long count = personService.countAll();
		return count;
	}

	@RequestMapping(value = "/person/{id}", method = RequestMethod.GET)
	public @ResponseBody
	Person findPerson(@PathVariable("id") Long id) throws PersonNotFoundException {
		LOGGER.debug("Getting person with id: " + id);
		Person person = personService.findById(id);
		LOGGER.debug("Person details with id: " + person);
		return person;
	}

	@RequestMapping(value = "/person/{id}", method = RequestMethod.DELETE)
	public @ResponseBody
	boolean delete(@PathVariable("id") Long id) throws PersonNotFoundException {
		LOGGER.debug("Deleting person with id: " + id);
		Person deleted = personService.delete(id);
		return deleted != null;
	}

	@RequestMapping(value = "/person/", method = RequestMethod.PUT)
	public @ResponseBody
	Person editPerson(@RequestBody Person person) throws PersonNotFoundException {
		LOGGER.debug("Editing person with id: " + person.getId());
		person = personService.update(person);
		LOGGER.debug("Person details with id: " + person);
		return person;
	}

	@RequestMapping(value = "/person/", method = RequestMethod.POST)
	public @ResponseBody void createPerson(@RequestBody Person person) {
		LOGGER.debug("Create person with information: " + person);
		person = personService.create(person);
		LOGGER.debug("Created person with information: " + person);
	}

	@ExceptionHandler(PersonNotFoundException.class)
	ResponseEntity<String> handlePersonNotFoundException(Exception e) {
		return new ResponseEntity<String>(String.format("{\"Error\":\"%s\"}",
				e.getMessage()), HttpStatus.NOT_FOUND);
	}

	/**
	 * This setter method should only be used by unit tests
	 * 
	 * @param personService
	 */
	protected void setPersonService(PersonService personService) {
		this.personService = personService;
	}
}
