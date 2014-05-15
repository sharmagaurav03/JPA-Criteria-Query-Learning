package repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryExecutorRepository {

	Page<Object> executeNativeQuery(String queryStr,
			Map<String, String> params, Pageable pageable);

	Page<Object> executeJPQLQuery(String queryStr, Map<String, String> params,
			Pageable pageable);

}
