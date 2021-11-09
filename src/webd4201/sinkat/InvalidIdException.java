package webd4201.sinkat;
	
/**
 * @author Thomas Sinka
 * @version 1.0
 * @since 01-21-2020
 */
@SuppressWarnings("serial")
public class InvalidIdException extends Exception { 
    /**
     * @param errorMessage - Message to be thrown
     */
    public InvalidIdException(String errorMessage) {
        super(errorMessage);
    }
    
    /**
     * Default exception
     */
    public InvalidIdException() {
        super();
    }
}