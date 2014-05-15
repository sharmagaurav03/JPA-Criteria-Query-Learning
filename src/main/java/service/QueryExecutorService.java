package service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryExecutorService {

	Page<Object> executeDynamicNativeQuery(Pageable pageable, String query);

	Page<Object> executeNamedQuery(Pageable pageable, String namedQueryName,
			Map<String, String> params);

	Page<Object> executeDynamicJPQLQuery(Pageable pageable, String query);

}
