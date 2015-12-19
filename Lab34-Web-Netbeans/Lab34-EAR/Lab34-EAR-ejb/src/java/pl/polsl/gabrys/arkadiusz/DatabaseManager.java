package pl.polsl.gabrys.arkadiusz;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.LockTimeoutException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;
import pl.polsl.gabrys.arkadiusz.model.*;

/**
 *
 * @author arkad_000
 */
@Stateless(mappedName = "DatabaseManager")
public class DatabaseManager implements DatabaseManagerLocal {

    /**
     * Entity manager instance
     */
    @PersistenceContext(name="Lab34-EJB")
    private EntityManager entityManager;

    /**
     * Class constructor
     */
    public DatabaseManager() { }

    /**
     * Finds Author entity by its id
     * @param id the entity id
     * @return the entity with given id
     * @throws IllegalArgumentException if the id is null
     */
    @Override
    public Author findAuthorById(Long id) throws IllegalArgumentException {
        return find(Author.class, id);
    }

    /**
     * Returns all authors
     * @return the list with all authors
     * @throws QueryTimeoutException if the query execution exceeds the query timeout value set and only the statement is rolled back
     * @throws TransactionRequiredException if a lock mode has been set and there is no transaction
     * @throws PessimisticLockException if pessimistic locking fails and the transaction is rolled back
     * @throws LockTimeoutException if pessimistic locking fails and only the statement is rolled back
     * @throws PersistenceException if the query execution exceeds the query timeout value set and the transaction is rolled back
     */
    @Override
    public List<Author> findAllAuthors() throws QueryTimeoutException, TransactionRequiredException, PessimisticLockException, LockTimeoutException, PersistenceException {
      return findAll(Author.class);
    }

    /**
     * Returns all authors with given name
     * @param name the author name
     * @return the list of authors with given name
     * @throws IllegalArgumentException if given class is not valid entity class for this operation
     * @throws QueryTimeoutException if the query execution exceeds the query timeout value set and only the statement is rolled back
     * @throws TransactionRequiredException if a lock mode has been set and there is no transaction
     * @throws PessimisticLockException if pessimistic locking fails and the transaction is rolled back
     * @throws LockTimeoutException if pessimistic locking fails and only the statement is rolled back
     * @throws PersistenceException if the query execution exceeds the query timeout value set and the transaction is rolled back
     */
    @Override
    public List<Author> findAuthorsByName(String name) throws IllegalArgumentException, QueryTimeoutException, TransactionRequiredException, PessimisticLockException, LockTimeoutException, PersistenceException {
        name = name.replace("\"", "").trim();
        Query query = entityManager.createNamedQuery("Author.findByName");
        query.setParameter("name", name);
        return query.getResultList();
    }

    /**
     * Finds Book entity by its id
     * @param id the entity id
     * @return the entity with given id
     * @throws IllegalArgumentException if the id is null
     */
    @Override
    public Book findBookById(Long id) throws IllegalArgumentException {
        return find(Book.class, id);
    }

    /**
     * Returns all books
     * @return the list with all books
     * @throws QueryTimeoutException if the query execution exceeds the query timeout value set and only the statement is rolled back
     * @throws TransactionRequiredException if a lock mode has been set and there is no transaction
     * @throws PessimisticLockException if pessimistic locking fails and the transaction is rolled back
     * @throws LockTimeoutException if pessimistic locking fails and only the statement is rolled back
     * @throws PersistenceException if the query execution exceeds the query timeout value set and the transaction is rolled back
     */
    @Override
    public List<Book> findAllBooks() throws QueryTimeoutException, TransactionRequiredException, PessimisticLockException, LockTimeoutException, PersistenceException {
      return findAll(Book.class);
    }

    /**
     * Returns al books with given title
     * @param title the book title
     * @return the list of books with given title
     * @throws IllegalArgumentException if given class is not valid entity class for this operation
     * @throws QueryTimeoutException if the query execution exceeds the query timeout value set and only the statement is rolled back
     * @throws TransactionRequiredException if a lock mode has been set and there is no transaction
     * @throws PessimisticLockException if pessimistic locking fails and the transaction is rolled back
     * @throws LockTimeoutException if pessimistic locking fails and only the statement is rolled back
     * @throws PersistenceException if the query execution exceeds the query timeout value set and the transaction is rolled back
     */
    @Override
    public List<Book> findBooksByTitle(String title) throws IllegalArgumentException, QueryTimeoutException, TransactionRequiredException, PessimisticLockException, LockTimeoutException, PersistenceException {
        title = title.replace("\"", "").trim();
        Query query = entityManager.createNamedQuery("Book.findByTitle");
        query.setParameter("title", title);
        return query.getResultList();
    }

    /**
     * Adds new Author entity
     * @param name the author name
     * @param lastName the author last name
     * @throws TransactionRequiredException if invoked on a container-managed entity manager of type PersistenceContextType.TRANSACTION and there is no transaction
     */
    @Override
    public void persistAuthor(String name, String lastName) throws TransactionRequiredException {
        Author entity = new Author(name, lastName);
        persist(entity);
    }

    /**
     * Adds new Book entity
     * @param title the book title
     * @param pages the number of book pages
     * @param releaseDate the book release date
     * @param authorId the book author id
     * @throws IllegalArgumentException if the author id is null or if the author doesn't exists
     * @throws TransactionRequiredException if invoked on a container-managed entity manager of type PersistenceContextType.TRANSACTION and there is no transaction
     */
    @Override
    public void persistBook(String title, Long pages, Date releaseDate, Long authorId) throws IllegalArgumentException, TransactionRequiredException {
        Author author = findAuthorById(authorId);
        
        if (author == null) {
            throw new IllegalArgumentException("Author with id: " + authorId + " doesn't exists");
        }
        
        Book entity = new Book(title, pages, releaseDate, author);
        persist(entity);
    }

    /**
     * Updates given Author entity
     * @param id the author id
     * @param name the new author name
     * @param lastName the new author last name
     * @throws IllegalArgumentException if the author is a removed entity or if the id is null or the given author doesn't exists
     * @throws TransactionRequiredException if invoked on a container-managed entity manager of type PersistenceContextType.TRANSACTION and there is no transactio
     */
    @Override
    public void mergeAuthor(Long id, String name, String lastName) throws IllegalArgumentException, TransactionRequiredException {
      Author entity = findAuthorById(id);
      entity.setName(name);
      entity.setLastName(lastName);
      merge(entity);
    }

    /**
     * Updates given Book entity
     * @param id the book id
     * @param title the new book title
     * @param pages the new number of pages
     * @param releaseDate the new release date
     * @param authorId the id of the new author
     * @throws IllegalArgumentException if the book is a removed entity or if the id is null
     * @throws TransactionRequiredException if invoked on a container-managed entity manager of type PersistenceContextType.TRANSACTION and there is no transactio
     */
    @Override
    public void mergeBook(Long id, String title, Long pages, Date releaseDate, Long authorId) throws IllegalArgumentException, TransactionRequiredException {
      Book entity = findBookById(id);
      Author author = findAuthorById(authorId);
      entity.setTitle(title);
      entity.setReleaseDate(releaseDate);
      entity.setAuthor(author);
      merge(entity);
    }

    /**
     * Removes Author entity
     * @param id the author id
     * @throws IllegalArgumentException if the instance is a detached entity
     * @throws TransactionRequiredException if invoked on a container-managed entity manager of type PersistenceContextType.TRANSACTION and there is no transaction
     */
    @Override
    public void removeAuthor(Long id) throws IllegalArgumentException, TransactionRequiredException {
      Author entity = findAuthorById(id);
      remove(entity);
    }

    /**
     * Removes Book entity
     * @param id the book id
     * @throws IllegalArgumentException if the instance is a detached entity
     * @throws TransactionRequiredException if invoked on a container-managed entity manager of type PersistenceContextType.TRANSACTION and there is no transaction
     */
    @Override
    public void removeBook(Long id) throws IllegalArgumentException, TransactionRequiredException {
      Book entity = findBookById(id);
      remove(entity);
    }

    /**
     * Finds entity by its id
     * @param <T> the entity type
     * @param objectClass the entity class
     * @param tId the entity id
     * @return the entity with given id
     * @throws IllegalArgumentException if the first argument does not denote an entity type or the second argument is is null
     */
    private <T> T find(Class<T> objectClass, Long tId) throws IllegalArgumentException {
        return entityManager.find(objectClass, tId);
    }

    /**
     * Adds new entity
     * @param <T> the entity type
     * @param t the eintity instance
     * @throws EntityExistsException if the entity already exists. (If the entity already exists, the EntityExistsException may be thrown when the persist operation is invoked, or the EntityExistsException or another PersistenceException may be thrown at flush or commit time.)
     * @throws IllegalArgumentException if the instance is not an entity
     * @throws TransactionRequiredException if invoked on a container-managed entity manager of type PersistenceContextType.TRANSACTION and there is no transaction
     */
    private <T> void persist(T t) throws EntityExistsException, IllegalArgumentException, TransactionRequiredException {
        entityManager.persist(t);
    }

    /**
     * Updates given entity
     * @param <T> the entity type
     * @param t the entity instance
     * @return the chenged entity instance
     * @throws IllegalArgumentException if instance is not an entity or is a removed entity
     * @throws TransactionRequiredException if invoked on a container-managed entity manager of type PersistenceContextType.TRANSACTION and there is no transactio
     */
    private <T> T merge(T t) throws IllegalArgumentException, TransactionRequiredException {
        return entityManager.merge(t);
    }

    /**
     * Removes given entity
     * @param <T> the entity type
     * @param t the entity instance
     * @throws IllegalArgumentException if the instance is not an entity or is a detached entity
     * @throws TransactionRequiredException if invoked on a container-managed entity manager of type PersistenceContextType.TRANSACTION and there is no transaction
     */
    private <T> void remove(T t) throws IllegalArgumentException, TransactionRequiredException {
        entityManager.remove(t);
    }

    /**
     * Returns all entities of given entity class
     * @param <T> the entity type
     * @param objectClass the entity class
     * @return the list of with all entities
     * @throws IllegalArgumentException if given class is not valid entity class for this operation
     * @throws QueryTimeoutException if the query execution exceeds the query timeout value set and only the statement is rolled back
     * @throws TransactionRequiredException if a lock mode has been set and there is no transaction
     * @throws PessimisticLockException if pessimistic locking fails and the transaction is rolled back
     * @throws LockTimeoutException if pessimistic locking fails and only the statement is rolled back
     * @throws PersistenceException if the query execution exceeds the query timeout value set and the transaction is rolled back
     */
    private <T> List<T> findAll(Class<T> objectClass) throws IllegalArgumentException, QueryTimeoutException, TransactionRequiredException, PessimisticLockException, LockTimeoutException, PersistenceException {
        String namedQuery = objectClass.getName() + ".findAll";
        String[] split = namedQuery.split("\\.");
        namedQuery = split[split.length - 2] + "." + split[split.length -1];
        Query query = entityManager.createNamedQuery(namedQuery);
        return query.getResultList();
    }
}
