package repository;

import model.NamedQuery;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Specifies methods used to obtain and modify person related information which
 * is stored in the database.
 */
public interface NamedQueryRepository extends
		PagingAndSortingRepository<NamedQuery, Long> {

	NamedQuery findByQueryName(String queryName);

}
