package repository;

import java.util.List;

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
public class QueryRepositoryImpl implements QueryRepository {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(QueryRepositoryImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public Page<Object> executeNativeQuery(String queryStr, Pageable pageable) {
		LOGGER.debug("executing query " + queryStr);
		if (null == pageable) {
			List<Object> resultList = entityManager.createNativeQuery(queryStr)
					.getResultList();
			PageImpl<Object> result = new PageImpl<Object>(resultList);
			LOGGER.debug("Found results " + resultList);
			return result;
		} else {
			Query query = entityManager.createNativeQuery(queryStr);
			query.setFirstResult(pageable.getOffset());
			query.setMaxResults(pageable.getPageSize());
			List<Object> resultList = query.getResultList();
			return new PageImpl<Object>(resultList, pageable, resultList.size());
		}
	}
}
