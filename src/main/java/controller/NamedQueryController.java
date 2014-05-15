package controller;

import javax.annotation.Resource;

import model.NamedQuery;

import org.apache.commons.lang.exception.ExceptionUtils;
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

import service.NamedQueryService;
import exception.NamedQueryFoundException;
import exception.NamedQueryNotFoundException;

@Controller
@RequestMapping("/namedQuery")
public class NamedQueryController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(NamedQueryController.class);

	@Resource
	private NamedQueryService namedQueryService;

	/**
	 * Processes requests to return lists all available namedQuerys.
	 * 
	 * @param model
	 * @return The name of the namedQuery list view.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody
	Iterable<NamedQuery> findAll() {
		LOGGER.debug("Rendering namedQuerys list");
		Iterable<NamedQuery> namedQuerys = namedQueryService.findAll();
		return namedQuerys;
	}

	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public @ResponseBody
	Long countAll() {
		LOGGER.debug("counting namedQuerys");
		Long count = namedQueryService.countAll();
		return count;
	}

	@RequestMapping(value = "/findOne/{queryName}", method = RequestMethod.GET)
	public @ResponseBody
	NamedQuery findNamedQuery(@PathVariable("queryName") String queryName)
			throws NamedQueryNotFoundException {
		LOGGER.debug("Getting namedQuery with queryName: " + queryName);
		NamedQuery namedQuery = namedQueryService.findByQueryName(queryName);
		LOGGER.debug("NamedQuery details with queryName: " + namedQuery);
		return namedQuery;
	}

	@RequestMapping(value = "/delete/{queryName}", method = RequestMethod.DELETE)
	public @ResponseBody
	boolean delete(@PathVariable("queryName") String queryName)
			throws NamedQueryNotFoundException {
		LOGGER.debug("Deleting namedQuery with queryName: " + queryName);
		NamedQuery deleted = namedQueryService.deleteByQueryName(queryName);
		return deleted != null;
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public @ResponseBody
	NamedQuery editNamedQuery(@RequestBody NamedQuery namedQuery)
			throws NamedQueryNotFoundException {
		LOGGER.debug("Editing namedQuery with queryName: "
				+ namedQuery.getQueryName());
		namedQuery = namedQueryService.update(namedQuery);
		LOGGER.debug("NamedQuery details with queryName: " + namedQuery);
		return namedQuery;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	void createNamedQuery(@RequestBody NamedQuery namedQuery)
			throws NamedQueryFoundException {
		LOGGER.debug("Create namedQuery with information: " + namedQuery);
		namedQuery = namedQueryService.create(namedQuery);
		LOGGER.debug("Created namedQuery with information: " + namedQuery);
	}

	@ExceptionHandler(NamedQueryNotFoundException.class)
	ResponseEntity<String> handleNamedQueryNotFoundException(Exception e) {
		return new ResponseEntity<String>(String.format("{\"Error\":\"%s\"}",
				ExceptionUtils.getRootCauseMessage(e)), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NamedQueryFoundException.class)
	ResponseEntity<String> handleNamedQueryFoundException(Exception e) {
		return new ResponseEntity<String>(String.format("{\"Error\":\"%s\"}",
				ExceptionUtils.getRootCauseMessage(e)), HttpStatus.BAD_REQUEST);
	}

	/**
	 * This setter method should only be used by unit tests
	 * 
	 * @param namedQueryService
	 */
	protected void setNamedQueryService(NamedQueryService namedQueryService) {
		this.namedQueryService = namedQueryService;
	}
}
