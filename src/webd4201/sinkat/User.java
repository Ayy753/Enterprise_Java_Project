package webd4201.sinkat;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Thomas Sinka
 * @version 1.0
 * @since 01-21-2020
 */
public class User implements CollegeInterface{
    
	//CLASS ATTRIBUTES
	/**
	 * Default user ID
	 */
	protected final static long DEFAULT_ID = 100123456;
	/**
	 * Default password
	 */
	protected final static String DEFAULT_PASSWORD = "password";
	/**
	 * Minimum allowable password length
	 */
	protected final static byte MINIMUM_PASSWORD_LENGTH = 8;
	/**
	 * Maximum allowable password length
	 */
	protected final static byte MAXIMUM_PASSWORD_LENGTH = 40;
	/**
	 * Default first name
	 */
	protected final static String DEFAULT_FIRST_NAME = "John";
	/**
	 * Default last name
	 */
	protected final static String DEFAULT_LAST_NAME = "Doe";
	/**
	 * Default email address
	 */
	protected final static String DEFAULT_EMAIL_ADDRESS = "john.doe@dcmail.com";
	/**
	 * Default enabled access
	 */
	protected final static boolean DEFAULT_ENABLED_ACCESS = true;
	/**
	 * Default account type
	 */
	protected final static char DEFAULT_TYPE = 's';
	/**
	 * Allowable ID length
	 */
	protected final static byte ID_NUMBER_LENGTH = 9;
	/**
	 * Date format object
	 */
	protected final static DateFormat DF = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CANADA);

    //INSTANCE ATTRIBUTES
	/**
	 * Instance user ID
	 */
	private long id;
	/**
	 * Instance user password
	 */
	private String password;
	/**
	 * Instance user first name
	 */
	private String firstName;
	/**
	 * Instance user last name
	 */
	private String lastName;
	/**
	 * instance user email
	 */
	private String emailAddress;
	/**
	 * User's last access date
	 */
	private Date lastAccess;
	/**
	 * User's enroll date
	 */
	private Date enrollDate;
	/**
	 * User's account enabled status
	 */
	private boolean enabled;
	/**
	 * User account type
	 */
	private char type;
    
    //CONSTRUCTORS
    //PARAMETERIZED CONSTRUCTOR
	
	/**
	 * Parameterized constructor for User class
	 * @param id - ID of user
	 * @param password - User's password
	 * @param firstName - User's first name
	 * @param lastName - User's last name
	 * @param emailAddress - User's email address
	 * @param lastAccess - User's last access
	 * @param enrollDate - User's enroll date
	 * @param type - User account type
	 * @param enabled - User account enabled flag
	 * @throws InvalidUserDataException - thrown if any of the data is missing or invalid type
	 */
    public User(long id, String password, String firstName, 
    		String lastName, String emailAddress, Date enrollDate , 
    		Date lastAccess, char type, boolean enabled) 
    				throws InvalidUserDataException{	

    	try{
    		setId(id);
        	setPassword(password);
        	setFirstName(firstName);
        	setLastName(lastName);
        	setEmailAddress(emailAddress);
        	setLastAccess(lastAccess);
        	setEnrollDate(enrollDate);
        	setEnabled(enabled);
        	setType(type);
    	}
    	catch(Exception e){
    		throw new InvalidUserDataException(e.getMessage());
    	}
    }
    
    /**
     * Default constructor for User class, uses default values
     * @throws InvalidUserDataException - Should never be thrown
     */
    public User() throws InvalidUserDataException{
    	this(DEFAULT_ID, DEFAULT_PASSWORD, DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, 
    			DEFAULT_EMAIL_ADDRESS, new Date(), new Date(), DEFAULT_TYPE, DEFAULT_ENABLED_ACCESS);
    }

    //GETTERS AND SETTERS

    /**
     * 
     * @return returns user id
     */
	public long getId() {
		return id;
	}

	/**
	 * Set user's id
	 * @param id
	 * ID of user
	 * @throws InvalidIdException - If ID is not validated
	 */
	public void setId(long id) throws InvalidIdException {
		if(id < MINIMUM_ID_NUMBER){
			throw new InvalidIdException(id + " is not a valid user id, it must be greater than " +
		MINIMUM_ID_NUMBER);
		}
		else if(id > MAXIMUM_ID_NUMBER){
			throw new InvalidIdException(id + " is not a valid user id, it must be less than " + 
		MAXIMUM_ID_NUMBER);
		}
		else{
			this.id = id;
		}
	}

	/**
	 * @return user's password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set user password
	 * @param password
	 * User's password
	 * @throws InvalidPasswordException - If password did not meet validation rules
	 */
	public void setPassword(String password) throws InvalidPasswordException {
		if(password.length() < MINIMUM_PASSWORD_LENGTH ){
			throw new InvalidPasswordException("The password \"" + password + "\" is too short, it must be at least " +
					MINIMUM_PASSWORD_LENGTH + " characters long.");				
		}
		else if(password.length() > MAXIMUM_PASSWORD_LENGTH){
			throw new InvalidPasswordException("The password \"" + password + ""
					+ "\" is too long, it must be less than " +
					MAXIMUM_PASSWORD_LENGTH + " characters long.");				
		}
		else{
			this.password = password;
		}
	}

	/**
	 * @return user's first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * 	Sets user's first name
	 * @param  firstName
	 * USer's first name
	 * @throws InvalidNameException
	 * Must not be an empty string or a number
	 */
	public void setFirstName(String firstName) throws InvalidNameException {
		if(!firstName.isEmpty()){

			//	Check if name is a number
			try {  
				Double.parseDouble(firstName);  
				throw new InvalidNameException("The user's name cannot be a number.");
			} 
			catch(NumberFormatException e){  
				this.firstName = firstName;	
			}
		}
		else{
			throw new InvalidNameException("The user's first name cannot be empty.");
		}
	}

	/**
	 * @return lastName
	 * user's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Set user's last name
	 * @param lastName
	 * User's last name
	 * @throws InvalidNameException
	 * Must not be an empty string or a number
	 */
	public void setLastName(String lastName) throws InvalidNameException {
		if(!lastName.isEmpty()){

			//	Check if name is a number
			try {  
				Double.parseDouble(lastName);  
				throw new InvalidNameException("The user's last name cannot be a number.");
			} 
			catch(NumberFormatException e){  
				this.lastName = lastName;	
			}
		}
		else{
			throw new InvalidNameException("The user's last name cannot be empty.");
		}
	}

	/**
	 * @return user email
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress
	 * User's email address
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return last access date
	 * Date last accessed
	 */
	public Date getLastAccess() {
		return lastAccess;
	}

	/**
	 * Set last access date
	 * @param date
	 * last access date
	 */
	public void setLastAccess(Date date) {
		this.lastAccess = date;
	}

	/**
	 * Get enroll date
	 * @return enroll date
	 * Date enrolled
	 */
	public Date getEnrollDate() {
		return enrollDate;
	}

	/**
	 * Set enroll date
	 * @param enrollDate
	 * Date of user's enrollment
	 */
	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}

	/**
	 * @return user account enable status
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Is the account enabled?
	 * @param enabled
	 * Account enable flag
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Get user account type
	 * @return account type
	 */
	public char getType() {
		return type;
	}

	/**
	 * Set account type
	 * @param type - Account type
	 */
	public void setType(char type) {
		this.type = type;
	}
    
	//METHODS
	/* (non-Javadoc)
	 * @see webd4201.sinkat.CollegeInterface#getTypeForDisplay()
	 */
	@Override
	public String getTypeForDisplay(){
		return "User";
	}
    
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		String output = String.format("User info for: %d \n\tName: %s %s (%s)\n\tCreated on: "
				+ "%s\n\tLast access: %s", 
				getId(), getFirstName(), getLastName(), getEmailAddress(), DF.format(getEnrollDate()) , 
				DF.format(getLastAccess()) );
		
		return output;
	}
	
	/**
	 * Prints out class tostring override
	 */
	public void dump(){
		System.out.println(this.toString());
	}
	
	/**
	 * @param id
	 * ID to be validated
	 * @return
	 * Success: True or false
	 */
	public static boolean verifyId(long id){
		if(id >= MINIMUM_ID_NUMBER && id <= MAXIMUM_ID_NUMBER){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * 
	 * @param id - Unique ID of User
	 * @return Student object
	 * @throws NotFoundException - Thrown when record is not found in database
	 */
	public static User retrieve(Long id) throws NotFoundException {
		User result = null;
		
		try{
			result =  UserDA.retrieve(id);
		}
		catch(NotFoundException e){
			throw new NotFoundException(e.getMessage());
		}
		return 	result;
	}

	/**
	 * Initialize connection to database
	 * @param c - Connection Object
	 */
	public static void initialize(Connection c){
		UserDA.initialize(c);
	}
	
	/**
	 * Terminate connection to database
	 */
	public static void terminate(){
		UserDA.terminate();
	}

	/**
	 * 	
	 * @return Boolean result of the operation being successful or not
	 * @throws DuplicateException - Thrown when record is not found in database
	 * @throws SQLException - Thrown when a generic SQL exception occurs 
	 * @throws NoSuchAlgorithmException - Thrown when there is a problem with the hashing algorithm 
	 * @throws InvalidUserDataException - thrown if any of the data is missing or invalid type
	 */
	public boolean create() throws DuplicateException, SQLException, NoSuchAlgorithmException, InvalidUserDataException{
		boolean result = false;
		
		try{
			result = UserDA.create(this);
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
	 * @throws SQLException - Thrown when a generic SQL exception occurs
	 */
	public int update() throws NotFoundException, NoSuchAlgorithmException, SQLException{
		int result = 0;
		
		try{
			result = UserDA.update(this);
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
			result = UserDA.delete(this);
		}
		catch(NotFoundException e){
			throw new NotFoundException(e.getMessage());
		}
		return 	result;
	}
}
