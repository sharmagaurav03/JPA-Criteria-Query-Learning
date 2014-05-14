package controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import service.QueryService;

@Controller
@RequestMapping("/query")
public class QueryController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(QueryController.class);

	@Resource
	private QueryService queryService;

	@RequestMapping(value = "/nativeQueryExecutor", method = RequestMethod.POST)
	public @ResponseBody
	Page<Object> executeSelectQuery(@RequestBody String query,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "0") int size) {
		LOGGER.debug("Executing select query " + query);
		Pageable pageable = new PageRequest(page, size);
		Page<Object> result = queryService.executeNativeQuery(pageable, query);
		LOGGER.debug("got the result " + result);
		return result;
	}


	/**
	 * This setter method should only be used by unit tests
	 * 
	 * @param personService
	 */
	protected void setQueryService(QueryService queryService) {
		this.queryService = queryService;
	}
}
