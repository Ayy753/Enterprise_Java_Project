package webd4201.sinkat;

/**
 * @author Thomas Sinka
 * @version 1.0
 * @since 01-21-2020
 */
@SuppressWarnings("serial")
public class InvalidNameException  extends Exception { 
    /**
     * @param errorMessage - Message to be thrown
     */
    public InvalidNameException(String errorMessage) {
        super(errorMessage);
    }
    
    /**
     * Default exception
     */
    public InvalidNameException() {
        super();
    }
}