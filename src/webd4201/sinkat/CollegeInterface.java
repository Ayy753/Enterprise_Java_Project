package webd4201.sinkat;

/**
 * @author Thomas Sinka
 * @version 1.0
 * @since 01-21-2020
 */

/*
 * Interface which student and faculty objects impliment
 */
interface CollegeInterface {
		
    //ATTRIBUTES
    /**
     * Name of the college
     */
    final String COLLEGE_NAME = "Durham College"; 
    /**
     * Phone number of the college
     */
    final String PHONE_NUMBER = "(905)721-2000"; 
    /**
     * Minimum user ID value
     */
    final long MINIMUM_ID_NUMBER = 100000000; 
    /**
     * Maximum user ID value
     */
    final long MAXIMUM_ID_NUMBER = 999999999; 
    
    /**
     * @return account type
     */
    public String getTypeForDisplay();
}

