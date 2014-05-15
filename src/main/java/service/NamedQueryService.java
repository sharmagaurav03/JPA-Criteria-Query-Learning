package service;

import model.NamedQuery;
import exception.NamedQueryFoundException;
import exception.NamedQueryNotFoundException;

/**
 * Declares methods used to obtain and modify namedQuery information.
 */
public interface NamedQueryService {

	/**
	 * Creates a new namedQuery.
	 * 
	 * @param created
	 *            The information of the created namedQuery.
	 * @return The created namedQuery.
	 * @throws NamedQueryFoundException
	 */
	public NamedQuery create(NamedQuery created)
			throws NamedQueryFoundException;

	/**
	 * Deletes a namedQuery.
	 * 
	 * @param queryName
	 *            The queryName of the deleted namedQuery.
	 * @return The deleted namedQuery.
	 * @throws NamedQueryNotFoundException
	 *             if no namedQuery is found with the given id.
	 */
	public NamedQuery deleteByQueryName(String queryName)
			throws NamedQueryNotFoundException;

	/**
	 * Finds all namedQuerys.
	 * 
	 * @return A list of namedQuerys.
	 */
	public Iterable<NamedQuery> findAll();

	/**
	 * Finds namedQuery by id.
	 * 
	 * @param queryName
	 *            The id of the wanted namedQuery.
	 * @return The found namedQuery. If no namedQuery is found, this method
	 *         returns null.
	 */
	public NamedQuery findByQueryName(String queryName)
			throws NamedQueryNotFoundException;

	/**
	 * Updates the information of a namedQuery.
	 * 
	 * @param updated
	 *            The information of the updated namedQuery.
	 * @return The updated namedQuery.
	 * @throws NamedQueryNotFoundException
	 *             if no namedQuery is found with given id.
	 */
	public NamedQuery update(NamedQuery updated)
			throws NamedQueryNotFoundException;

	/**
	 * Retrieve the total count of the namedQuerys in the repository.
	 * 
	 * @param None
	 *            .
	 * @return The count of the namedQuery.
	 */

	public long countAll();

}
