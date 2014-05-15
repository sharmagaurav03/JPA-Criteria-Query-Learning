package service;

import java.util.Map;

import javax.annotation.Resource;

import model.NamedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repository.NamedQueryRepository;
import repository.QueryExecutorRepository;

@Service
public class QueryExecutorServiceImpl implements QueryExecutorService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(QueryExecutorServiceImpl.class);
	@Resource
	private QueryExecutorRepository queryExecutorRepository;

	@Resource
	private NamedQueryRepository namedQueryRepository;

	@Transactional
	@Override
	public Page<Object> executeDynamicNativeQuery(Pageable pageable,
			String query) {
		LOGGER.debug("Executing query " + query);
		Page<Object> result = queryExecutorRepository.executeNativeQuery(query,
				null, pageable);
		LOGGER.debug("found result of query " + result);
		return result;
	}

	@Transactional
	@Override
	public Page<Object> executeDynamicJPQLQuery(Pageable pageable, String query) {
		LOGGER.debug("Executing DynamicJPQLQuery " + query);
		Page<Object> result = queryExecutorRepository.executeJPQLQuery(query,
				null, pageable);
		LOGGER.debug("found result of query DynamicJPQLQuery" + result);
		return result;
	}

	@Override
	@Transactional
	public Page<Object> executeNamedQuery(Pageable pageable,
			String namedQueryName, Map<String, String> params) {
		LOGGER.debug("Executing named query " + namedQueryName);

		NamedQuery namedQuery = namedQueryRepository
				.findByQueryName(namedQueryName);
		if (namedQuery.isNative()) {
			LOGGER.debug("Executing named native query " + namedQueryName
					+ " got the query string " + namedQuery.getQueryString()
					+ " params=" + params);
			return queryExecutorRepository.executeNativeQuery(
					namedQuery.getQueryString(), params, pageable);
		} else {
			LOGGER.debug("Executing named JPQL query " + namedQueryName
					+ " got the query string " + namedQuery.getQueryString()
					+ " params=" + params);
			return queryExecutorRepository.executeJPQLQuery(
					namedQuery.getQueryString(), params, pageable);
		}

	}

}
