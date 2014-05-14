package service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryService {

	Page<Object> executeNativeQuery(Pageable pageable, String query);

}
