package repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import model.Person;

/**
 * Specifies methods used to obtain and modify person related information
 * which is stored in the database.
 */
public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {
	
}
