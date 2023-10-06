package seng202.team2.exceptions;

/**
 * Custom exception to be thrown when illegal access of a singleton instance is requested
 * In this case when we are trying to create an instance when one already exists
 *
 * @author Morgan English
 * @see <a href="https://docs.google.com/document/d/1OzJJYrHxHRYVzx_MKjC2XPGS8_arDKSxYD4NhDN37_E/edit">
 * SENG202 Advanced Applications with JavaFX</a>
 */
public class InstanceAlreadyExistsException extends Exception {
    /**
     * Simple constructor that passes to parent Exception class
     *
     * @param message error message
     */
    public InstanceAlreadyExistsException(String message) {
        super(message);
    }
}
