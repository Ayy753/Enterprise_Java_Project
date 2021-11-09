package webd4201.sinkat;

/**
 * @author Thomas Sinka
 * @version 1.0
 * @since 01-21-2020
 */
@SuppressWarnings("serial")
public class InvalidPasswordException extends Exception { 
    /**
     * @param errorMessage - Error message
     */
    public InvalidPasswordException(String errorMessage) {
        super(errorMessage);
    }
    
    /**
     * Default exception
     */
    public InvalidPasswordException() {
        super();
    }
}