package service;

import javax.annotation.Resource;

import model.Person;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repository.PersonRepository;
import exception.PersonNotFoundException;

/**
 * This implementation of the PersonService interface communicates with
 * the database by using a Spring Data JPA repository.
 */
@Service
public class PersonServiceImpl implements PersonService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonServiceImpl.class);
    
    @Resource
    private PersonRepository personRepository;

    @Transactional
    @Override
    public Person create(Person person) {
        LOGGER.debug("Creating a new person with information: " + person);
        return personRepository.save(person);
    }

    @Transactional(rollbackFor = PersonNotFoundException.class)
    @Override
    public Person delete(Long personId) throws PersonNotFoundException {
        LOGGER.debug("Deleting person with id: " + personId);
        Person deleted = personRepository.findOne(personId);
        if (deleted == null) {
            LOGGER.debug("No person found with id: " + personId);
            throw new PersonNotFoundException();
        }
        personRepository.delete(deleted);
        return deleted;
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<Person> findAll() {
        LOGGER.debug("Finding all persons");
        return personRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Person findById(Long id) throws PersonNotFoundException {
        LOGGER.debug("Finding person by id: " + id);
        Person person=personRepository.findOne(id);
        if(person==null){
            LOGGER.debug("No person found with id: " + id);
            throw new PersonNotFoundException();
        }
        return person;
    }

    @Transactional(rollbackFor = PersonNotFoundException.class)
    @Override
    public Person update(Person updated) throws PersonNotFoundException {
        LOGGER.debug("Updating person with information: " + updated);
        
        Person person = personRepository.findOne(updated.getId());
        LOGGER.debug("Found person: " + person);
        
        if (person == null) {
            LOGGER.debug("No person found with id: " + updated.getId());
            throw new PersonNotFoundException();
        }
        personRepository.save(updated);

        return person;
    }

    /**
     * This setter method should be used only by unit tests.
     * @param personRepository
     */
    protected void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public long countAll() {
        return personRepository.count();
    }
    
}
