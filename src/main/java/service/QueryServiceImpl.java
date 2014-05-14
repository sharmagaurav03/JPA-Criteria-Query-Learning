package service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repository.QueryRepository;

@Service
public class QueryServiceImpl implements QueryService{
	private static final Logger LOGGER = LoggerFactory
			.getLogger(QueryServiceImpl.class);
	@Resource
    private QueryRepository queryRepository;

	@Transactional
	@Override
	public Page<Object> executeNativeQuery(Pageable pageable,String query) {
		LOGGER.debug("Executing query "+query);
		Page<Object> result=queryRepository.executeNativeQuery(query, pageable);
		LOGGER.debug("found result of query "+result);
		return result;
	}

}
