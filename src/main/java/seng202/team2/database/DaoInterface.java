package seng202.team2.database;

import java.util.List;

/**
 * Interface for Database Access Objects (DAOs) that provides common functionality for database access
 *
 * @see <a href="https://docs.google.com/document/d/1OzJJYrHxHRYVzx_MKjC2XPGS8_arDKSxYD4NhDN37_E/edit">
 *     SENG202 Advanced Applications with JavaFX</a>
 * @author Morgan English
 */
public interface DaoInterface<T> {
    /**
     * Gets all of T from the database
     * @return List of all objects type T from the database
     */
    List<T> getAll();

    /**
     * Gets a single object of type T from the database by id
     * @param id id of object to get
     * @return Object of type T that has id given
     */
    T getOne(int id);

    /**
     * Adds a single object of type T to database
     * @param toAdd object of type T to add
     * @return object insert id if inserted correctly
     */
    int add(T toAdd);

    /**
     * Deletes and object from database that matches id given
     * @param id id of object to delete
     */
    void delete(int id);

    /**
     * Updates an object in the database
     * @param toUpdate Object that needs to be updated (this object must be able to identify itself and its previous self)
     */
    void update(T toUpdate);

}
