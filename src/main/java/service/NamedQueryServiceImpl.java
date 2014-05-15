package service;

import javax.annotation.Resource;

import model.NamedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repository.NamedQueryRepository;
import exception.NamedQueryFoundException;
import exception.NamedQueryNotFoundException;

/**
 * This implementation of the NamedQueryService interface communicates with the
 * database by using a Spring Data JPA repository.
 */
@Service
public class NamedQueryServiceImpl implements NamedQueryService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(NamedQueryServiceImpl.class);

	@Resource
	private NamedQueryRepository namedQueryRepository;

	@Transactional
	@Override
	public NamedQuery create(NamedQuery NamedQuery)
			throws NamedQueryFoundException {
		LOGGER.debug("Creating a new NamedQuery with information: "
				+ NamedQuery);

		NamedQuery namedQuery = namedQueryRepository.findByQueryName(NamedQuery
				.getQueryName());
		if (namedQuery != null) {
			LOGGER.debug("NamedQuery found with queryName: "
					+ NamedQuery.getQueryName());
			throw new NamedQueryFoundException();
		}
		return namedQueryRepository.save(NamedQuery);
	}

	@Transactional(readOnly = true)
	@Override
	public Iterable<NamedQuery> findAll() {
		LOGGER.debug("Finding all NamedQuerys");
		return namedQueryRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public NamedQuery findByQueryName(String queryName)
			throws NamedQueryNotFoundException {
		LOGGER.debug("Finding NamedQuery by queryName: " + queryName);
		NamedQuery NamedQuery = namedQueryRepository.findByQueryName(queryName);
		if (NamedQuery == null) {
			LOGGER.debug("No NamedQuery found with queryName: " + queryName);
			throw new NamedQueryNotFoundException();
		}
		return NamedQuery;
	}

	@Transactional(readOnly = true)
	@Override
	public long countAll() {
		return namedQueryRepository.count();
	}

	@Override
	@Transactional(rollbackFor = NamedQueryNotFoundException.class)
	public NamedQuery deleteByQueryName(String queryName)
			throws NamedQueryNotFoundException {
		NamedQuery deleted = namedQueryRepository.findByQueryName(queryName);
		if (deleted == null) {
			LOGGER.debug("No NamedQuery found with queryName: " + queryName);
			throw new NamedQueryNotFoundException();
		}
		namedQueryRepository.delete(deleted);
		return deleted;
	}

	@Transactional(rollbackFor = NamedQueryNotFoundException.class)
	@Override
	public NamedQuery update(NamedQuery updated)
			throws NamedQueryNotFoundException {
		LOGGER.debug("Updating NamedQuery with information: " + updated);

		NamedQuery namedQueryFound = namedQueryRepository
				.findByQueryName(updated.getQueryName());
		LOGGER.debug("Found NamedQuery: " + namedQueryFound);

		if (namedQueryFound == null) {
			LOGGER.debug("No NamedQuery found with queryName: "
					+ updated.getQueryName());
			throw new NamedQueryNotFoundException();
		}
		copyQuery(updated, namedQueryFound);

		namedQueryRepository.save(namedQueryFound);

		return namedQueryFound;
	}

	private void copyQuery(NamedQuery updated, NamedQuery namedQueryFound) {
		namedQueryFound.setNative(updated.isNative());
		namedQueryFound.setQueryName(updated.getQueryName());
		namedQueryFound.setQueryString(updated.getQueryString());
	}

	/**
	 * This setter method should be used only by unit tests.
	 * 
	 * @param NamedQueryRepository
	 */
	protected void setNamedQueryRepository(
			NamedQueryRepository NamedQueryRepository) {
		this.namedQueryRepository = NamedQueryRepository;
	}

}
