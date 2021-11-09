package webd4201.sinkat;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

/**
 * @author Thomas Sinka
 * @version 2.0
 * @since 02-03-2020
 */
public class Student extends User{
	/**
	 * Default program code
	 */
	protected static String DEFAULT_PROGRAM_CODE = "UNDC";
	/**
	 * Default program description
	 */
	protected static String DEFAULT_PROGRAM_DESCRIPTION = "Undeclared";
	/**
	 * Default program year
	 */
	protected static int DEFAULT_YEAR = 1;
	/**
	 * Instance program code
	 */
	private String programCode;
	/**
	 * Instance program description
	 */
	private String ProgramDescription;
	/**
	 * Instance program year
	 */
	private int year;
	/**
	 * Instance marks vector
	 */
	private Vector<Mark> marks;
	
	
	/**
	 * @return Returns program code
	 */
	public String getProgramCode() {
		return programCode;
	}
	/**
	 * Set program code
	 * @param programCode 
	 * Code of program
	 */
	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}
	/**
	 * @return returns program description
	 */
	public String getProgramDescription() {
		return ProgramDescription;
	}
	/**
	 * Get Description for program
	 * @param programDescription
	 * Description of program
	 */
	public void setProgramDescription(String programDescription) {
		ProgramDescription = programDescription;
	}
	/**
	 * @return returns program year
	 */
	public int getYear() {
		return year;
	}
	/**
	 * Set program year
	 * @param year
	 * Year of program student is currently in
	 */
	public void setYear(int year) {
		this.year = year;
	}
	/**
	 * @return returns marks vector
	 */
	public Vector<Mark> getMarks() {
		return marks;
	}
	/**
	 * Set Marks vector
	 * @param marks
	 * An vector of Mark objects
	 */
	public void setMarks(Vector<Mark> marks) {
		this.marks = marks;
	}
	
	/**
	 * Constructor for student class
	 * @param programCode - Program student is enrolled in
	 * @param ProgramDescription - Description of program student is enrolled in
	 * @param year - The current program year student is in
	 * @param marks - A vector of student marks
	 * @param id - Student ID
	 * @param password - Student Password
	 * @param firstName - Student First Name
	 * @param lastName - Student Last Name
	 * @param emailAddress - Student Email Address
	 * @param lastAccess - Student's last access
	 * @param enrollDate - Date in which student enrolled
	 * @param enabled - Is user enabled?
	 * @param type - What user type? Spoiler: Its probably student
	 * @throws InvalidUserDataException - Throws exception if any parameters are invalid
	 */
	public Student(long id,
			String password, String firstName, String lastName, String emailAddress, 
			Date lastAccess, Date enrollDate, boolean enabled, char type, String programCode, 
			String ProgramDescription, int year, Vector<Mark> marks) 
					throws InvalidUserDataException {
		super(id, password, firstName, lastName, emailAddress, lastAccess, enrollDate, 
				type, enabled);

		try{
			setProgramCode(programCode);
			setProgramDescription(ProgramDescription);
			setYear(year);
			setMarks(marks);	
		}
		catch(Exception e){
			throw new InvalidUserDataException(e.getMessage());
		}
	}
	
	/**
	 * Constructor that accepts a User object as a base
	 * @param user - Predefined User object
	 * @param programCode - Program student is enrolled in
	 * @param ProgramDescription - Description of program student is enrolled in
	 * @param year - The current program year student is in
	 * @throws InvalidUserDataException - Throws exception if any parameters are invalid
	 */
	public Student(User user, String programCode, String ProgramDescription, int year) throws InvalidUserDataException{
		super(user.getId(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getEmailAddress(), user.getLastAccess(), 
				user.getEnrollDate(), user.getType(), user.isEnabled());
		try{
			setProgramCode(programCode);
			setProgramDescription(ProgramDescription);
			setYear(year);
			setMarks(marks);				
		}
		catch(Exception e){
			throw new InvalidUserDataException(e.getMessage());
		}
	}
	
	/**
	 * Parameterized Student constructor with no marks vector
	 * @param programCode - Program student is enrolled in
	 * @param ProgramDescription - Description of program student is enrolled in
	 * @param year - The current program year student is in
	 * @param id - Student ID
	 * @param password - Student Password
	 * @param firstName - Student First Name
	 * @param lastName - Student Last Name
	 * @param emailAddress - Student Email Address
	 * @param lastAccess - Student's last access
	 * @param enrollDate - Date in which student enrolled
	 * @param type - What user type? Spoiler: Its probably student
	 * @param enabled - Is user enabled?
	 * @throws InvalidUserDataException 
	 * Throws exception if any parameters are invalid
	 */
	public Student(long id, String password, String firstName, String lastName,
			String emailAddress, Date lastAccess, Date enrollDate,
			char type, boolean enabled, String programCode, String ProgramDescription, int year) 
					throws InvalidUserDataException {
		this(id, password,  firstName,  lastName,
				emailAddress, lastAccess, enrollDate,
				enabled, type, programCode, ProgramDescription, year, new Vector<Mark>());
	}
	
	/**
	 * Default Student class constructor
	 * @throws InvalidUserDataException
	 * This should never throw an exception since all the parameters are default
	 */
	public Student() throws InvalidUserDataException{
    	this(DEFAULT_ID, DEFAULT_PASSWORD, 
    			DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_EMAIL_ADDRESS, new Date(), new Date(), 
    			DEFAULT_TYPE, DEFAULT_ENABLED_ACCESS, DEFAULT_PROGRAM_CODE, DEFAULT_PROGRAM_DESCRIPTION, DEFAULT_YEAR);
	}
		
	/* (non-Javadoc)
	 * @see webd4201.sinkat.User#toString()
	 */
	@Override
	public String toString(){

		String strYear = "";
		switch(this.year) {
			case 1:
				strYear = "1st";
				break;
			case 2:
				strYear = "2nd";
				break;
			case 3:
				strYear = "3rd";
				break;
			case 4:
				strYear = "4th";
		}

		String output = String.format("Student info for: \n\tName: %s %s (%s)\n\tCurrently in %s year of %s(%s)\n\tEnrolled: %s",
				getFirstName(), getLastName(), getId(), strYear, getProgramDescription(),getProgramCode() ,DF.format(getEnrollDate()));
		
		return output;
	}
	
	//	Class methods
	
	/**
	 * Initialize connection to database
	 * @param c - Connection Object
	 */
	public static void initialize(Connection c){
		StudentDA.initialize(c);
	}
	
	/**
	 * Terminate connection to database
	 */
	public static void terminate(){
		StudentDA.terminate();
	}
	
	//	Instance methods
	
	/**
	 * 
	 * @param id - Unique ID of student
	 * @return Student object
	 * @throws NotFoundException - Thrown when record is not found in database
	 */
	public static Student retrieve(Long id) throws NotFoundException {
		Student result = null;
		
		try{
			result =  StudentDA.retrieve(id);
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
	 * @throws InvalidUserDataException - thrown if any of the data is missing or invalid type
	 * @throws NoSuchAlgorithmException - Thrown when there is a problem with the hashing algorithm 
	 */
	public boolean create() throws DuplicateException, SQLException, InvalidUserDataException, NoSuchAlgorithmException{
		boolean result = false;
		
		try{
			result = StudentDA.create(this);
		}
		catch(DuplicateException e){
			throw new DuplicateException(e.getMessage());
		}
		catch(InvalidUserDataException ee){
			System.out.println(ee.getMessage());
			throw new InvalidUserDataException(ee.getMessage());
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
			result = StudentDA.update(this);
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
			result = StudentDA.delete(this);
		}
		catch(NotFoundException e){
			throw new NotFoundException(e.getMessage());
		}
		return 	result;
	}
	
	/**
	 * 
	 * @param bytes - Decimal number in the form of binary
	 * @return A string representation of the converted hex code
	 */
	public static String decToHex(byte[] bytes)
	{
	    String hex = "";
	    StringBuilder sb = new StringBuilder();
	    for(int i = 0; i < bytes.length ;i++)
	    {
		sb.append(String.format("%02x", bytes[i]));
	    }
	    hex = sb.toString();
	    return hex;
	}
	
	/**
	 * 
	 * @param id - Student Id
	 * @param password - Student password
	 * @return Student object
	 * @throws NoSuchAlgorithmException - Thrown when there is a problem with the hashing algorithm 
	 * @throws NotFoundException - Thrown when student is not found in database
	 */
	public static Student authenticate(long id, String password) throws NoSuchAlgorithmException, NotFoundException {
		Student result ;
		
		try{
			result = StudentDA.authenticate(id, password);
		}
		catch(NotFoundException e){
			throw new NotFoundException(e.getMessage());
		}
		return 	result;
	}
	
	/**
	 * Check if student exists in database
	 * @param id Student's ID
	 * @return True or false
	 */
	public static boolean exists(long id){
		return StudentDA.exists(id);
	}
}
