package repository;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class QueryExecutorRepositoryImpl implements QueryExecutorRepository {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(QueryExecutorRepositoryImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public Page<Object> executeNativeQuery(String queryStr,
			Map<String, String> params, Pageable pageable) {
		LOGGER.debug("executing query " + queryStr);
		if (null == pageable) {
			Query query = getNativeQuery(queryStr, params);
			List<Object> resultList = query.getResultList();
			PageImpl<Object> result = new PageImpl<Object>(resultList);
			LOGGER.debug("Found results " + resultList);
			return result;
		} else {
			LOGGER.debug("executing query " + queryStr);
			Query query = getNativeQuery(queryStr, params);
			query.setFirstResult(pageable.getOffset());
			query.setMaxResults(pageable.getPageSize());
			List<Object> resultList = query.getResultList();
			LOGGER.debug("Found results " + resultList);
			return new PageImpl<Object>(resultList, pageable, resultList.size());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<Object> executeJPQLQuery(String queryStr,
			Map<String, String> params, Pageable pageable) {
		LOGGER.debug("executing query " + queryStr);
		if (null == pageable) {
			Query query = getHQLQuery(queryStr, params);
			List<Object> resultList = query.getResultList();
			PageImpl<Object> result = new PageImpl<Object>(resultList);
			LOGGER.debug("Found results " + resultList);
			return result;
		} else {
			LOGGER.debug("executing query " + queryStr);
			Query query = getHQLQuery(queryStr, params);
			query.setFirstResult(pageable.getOffset());
			query.setMaxResults(pageable.getPageSize());
			List<Object> resultList = query.getResultList();
			LOGGER.debug("Found results " + resultList);
			return new PageImpl<Object>(resultList, pageable, resultList.size());
		}
	}

	private Query getHQLQuery(String queryStr, Map<String, String> params) {
		Query query = entityManager.createQuery(queryStr);
		if (params != null) {
			setQueryParams(query, params);
		}
		return query;
	}

	private Query getNativeQuery(String queryStr, Map<String, String> params) {
		Query query = entityManager.createNativeQuery(queryStr);
		if (params != null) {
			setQueryParams(query, params);
		}
		return query;
	}

	private void setQueryParams(Query query, Map<String, String> params) {
		if (params != null) {
			for (Object key : params.keySet()) {
				String queryParamName = key.toString();
				Object queryParamValue = params.get(key);
				query.setParameter(queryParamName, queryParamValue);
			}
		}

	}
}
