package repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryRepository {
	
	Page<Object> executeNativeQuery(String query, Pageable pageable);

}
