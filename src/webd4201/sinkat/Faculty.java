package webd4201.sinkat;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

/**
 * @author Thomas Sinka
 * @version 1.0
 * @since 01-21-2020
 */
public class Faculty extends User {
	
	//	CLASS ATTRIBUTES
	/**
	 * Default school code
	 */
	protected final static String DEFAULT_SCHOOL_CODE = "SET";
	/**
	 * Default school description
	 */
	protected final static String DEFAULT_SCHOOL_DESCRIPTION = "School of Engineering & Technology";
	/**
	 * Default office location
	 */
	protected final static String DEFAULT_OFFICE = "H-140";
	/**
	 * Default phone extension
	 */
	protected final static int DEFAULT_PHONE_EXTENSION = 1234;
	
	//	INSTANCE ATTRIBUTES
	/**
	 * School code attribute
	 */
	private String schoolCode;
	/**
	 * School description attribute
	 */
	private String schoolDescription;
	/**
	 * School description attribute
	 */
	private String office;
	/**
	 * School office location
	 */
	private int extension;
	
	//	Getters and setters
	
	/**
	 * @return School code
	 */
	public String getSchoolCode() {
		return schoolCode;
	}
	/**
	 * Set school code
	 * @param schoolCode
	 * 		School's code
	 */
	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}
	/**
	 * @return School Description
	 */
	public String getSchoolDescription() {
		return schoolDescription;
	}
	/**
	 * @param schoolDescription
	 * Description of school
	 */
	public void setSchoolDescription(String schoolDescription) {
		this.schoolDescription = schoolDescription;
	}
	/**
	 * @return Get Office location
	 */
	public String getOffice() {
		return office;
	}
	/**
	 * @param office
	 * 		Office location
	 */
	public void setOffice(String office) {
		this.office = office;
	}
	/**
	 * @return Returns phone extension
	 */
	public int getExtension() {
		return extension;
	}
	/**
	 * @param extension
	 * Telephone extension
	 */
	public void setExtension(int extension) {
		this.extension = extension;
	}
		
	//	PARAMETERIZED CONSTRUCTOR
	/**
	 * Parameterized constructor for User class
	 * @param id - ID of user
	 * @param password - User's password
	 * @param firstName - User's first name
	 * @param lastName - User's last name
	 * @param emailAddress - User's email address
	 * @param lastAccess - User's last access
	 * @param enrollDate - User's enroll date
	 * @param enabled - User account enabled flag
	 * @param type - User account type
	 * @param schoolCode - School Code
	 * @param schoolDescription - School's description
	 * @param office - Office location
	 * @param extension	- Phone Extension
	 * @throws InvalidUserDataException - thrown if any of the data is missing or invalid type
	 */	
	public Faculty(long id, String password, String firstName, String lastName, String emailAddress, 
			Date lastAccess, Date enrollDate, boolean enabled, char type, String schoolCode,
			String schoolDescription, String office, int extension) throws InvalidUserDataException{
			
		super(id, password, firstName, lastName, emailAddress, enrollDate, lastAccess, type, enabled);
		
		setSchoolCode(schoolCode);
		setSchoolDescription(schoolDescription);
		setOffice(office);
		setExtension(extension);
	}
	
	//	DEFAULT CONSTRUCTOR
    /**
     * Default constructor for Faculty class, uses default values
     * @throws InvalidUserDataException - Should never be thrown
     */
	public Faculty() throws InvalidUserDataException{
		/*super(DEFAULT_ID, DEFAULT_PASSWORD, DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, 
    			DEFAULT_EMAIL_ADDRESS, new Date(), new Date(), DEFAULT_ENABLED_ACCESS, DEFAULT_TYPE);*/
		super();
		setSchoolCode(DEFAULT_SCHOOL_CODE);
		setSchoolDescription(DEFAULT_SCHOOL_DESCRIPTION);
		setOffice(DEFAULT_OFFICE);
		setExtension(DEFAULT_PHONE_EXTENSION);
	}
	

	/* (non-Javadoc)
	 * @see webd4201.sinkat.User#getTypeForDisplay()
	 */
	@Override
	public String getTypeForDisplay(){
		return "Faculty";
	}	
	

	/* (non-Javadoc)
	 * @see webd4201.sinkat.User#toString()
	 */
	@Override
	public String toString(){
		String output = super.toString();
		output = output.replaceAll("User", getTypeForDisplay());
		output += String.format("\n\t%s\n\tOffice: %s\n\t%s x%d", schoolDescription, office, PHONE_NUMBER, 
				extension);
		
		return output;
	}

	
	//	Class methods
	
	/**
	 * Initialize connection to database
	 * @param c - Connection Object
	 */
	public static void initialize(Connection c){
		FacultyDA.initialize(c);
	}
	
	/**
	 * Terminate connection to database
	 */
	public static void terminate(){
		FacultyDA.terminate();
	}
	
	//	Instance methods	
	
	
	/**
	 * 
	 * @param id - Unique ID of Faculty
	 * @return Faculty object
	 * @throws NotFoundException - Thrown when record is not found in database
	 */
	public static Faculty retrieve(Long id) throws NotFoundException {
		Faculty result = null;
		
		try{
			result =  FacultyDA.retrieve(id);
		}
		catch(NotFoundException e){
			throw new NotFoundException(e.getMessage());
		}
		return 	result;
	}
	
	/**
	 * 	
	 * @return Boolean result of the operation being successful or not
	 * @throws DuplicateException - Thrown when record is not found in database
	 * @throws SQLException - Thrown when a generic SQL exception occurs 
	 * @throws NoSuchAlgorithmException - Thrown when there is a problem with the hashing algorithm
	 */
	public boolean create() throws DuplicateException, SQLException, NoSuchAlgorithmException{
		boolean result = false;
		
		try{
			result = FacultyDA.create(this);
		}
		catch(DuplicateException e){
			throw new DuplicateException(e.getMessage());
		}
		return 	result;
	}
	
	/**
	 * 
	 * @return Number of records updated
	 * @throws NotFoundException - Thrown when record is not found in database
	 * @throws NoSuchAlgorithmException - Thrown when there is a problem with the hashing algorithm 
	 */
	public int update() throws NotFoundException, NoSuchAlgorithmException{
		int result = 0;
		
		try{
			result = FacultyDA.update(this);
		}
		catch(NotFoundException e){
			throw new NotFoundException(e.getMessage());
		}
		return 	result;
	}
	
	/**
	 * 
	 * @return Number of records deleted
	 * @throws NotFoundException - Thrown when record is not found in database
	 */
	public int delete() throws NotFoundException{
		int result = 0;
		
		try{
			result = FacultyDA.delete(this);
		}
		catch(NotFoundException e){
			throw new NotFoundException(e.getMessage());
		}
		return 	result;
	}
}
