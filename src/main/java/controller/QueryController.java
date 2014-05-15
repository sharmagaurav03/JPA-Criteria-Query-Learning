package controller;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import service.QueryExecutorService;

@Controller
@RequestMapping("/queryExecutor")
public class QueryController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(QueryController.class);

	@Resource
	private QueryExecutorService queryService;

	@RequestMapping(value = "/dynamicQuery/native", method = RequestMethod.POST)
	public @ResponseBody
	Page<Object> executeDymanicNativeQuery(@RequestBody String query,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "1") int size) {
		LOGGER.debug("Executing select query " + query);
		Pageable pageable = new PageRequest(page - 1, size);
		Page<Object> result = queryService.executeDynamicNativeQuery(pageable,
				query);
		LOGGER.debug("got the result " + result);
		return result;
	}

	@RequestMapping(value = "/dynamicQuery/jpql", method = RequestMethod.POST)
	public @ResponseBody
	Page<Object> executedynamicJPQLQuery(@RequestBody String query,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "1") int size) {
		LOGGER.debug("Executing dynamic jpql query " + query);
		Pageable pageable = new PageRequest(page - 1, size);
		Page<Object> result = queryService.executeDynamicJPQLQuery(pageable,
				query);
		LOGGER.debug("got the result of dynamic jpql query " + result);
		return result;
	}

	@RequestMapping(value = "/namedQuery/{queryName}", method = RequestMethod.POST)
	public @ResponseBody
	Page<Object> executeNamedQuery(@RequestBody Map<String, String> params,
			@PathVariable("queryName") String queryName,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "1") int size) {
		LOGGER.debug("Executing named query " + queryName);
		Pageable pageable = new PageRequest(page - 1, size);
		Page<Object> result = queryService.executeNamedQuery(pageable,
				queryName, params);
		LOGGER.debug("got the result of named query" + result);
		return result;
	}

	/**
	 * This setter method should only be used by unit tests
	 * 
	 * @param personService
	 */
	protected void setQueryService(QueryExecutorService queryService) {
		this.queryService = queryService;
	}
}
