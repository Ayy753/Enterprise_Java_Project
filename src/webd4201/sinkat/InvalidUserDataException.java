package webd4201.sinkat;

/**
 * @author Thomas Sinka
 * @version 1.0
 * @since 01-21-2020
 */
@SuppressWarnings("serial")
public class InvalidUserDataException extends Exception { 
    /**
     * @param errorMessage - Error Message
     */
    public InvalidUserDataException(String errorMessage) {
        super(errorMessage);
    }
    
    /**
     * Default exception
     */
    public InvalidUserDataException() {
        super();
    }    
}