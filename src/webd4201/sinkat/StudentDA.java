package webd4201.sinkat;

import java.text.SimpleDateFormat;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Date;

/**
 * StudentDA - this file is contains all of the data access methods, that actually get/set data to the database. 
 * Note: that all the methods are static this is because you do not really create StudentDA objects (does not make sense)
 * @author Thomas Sinka
 * @version 2.0 (2020/2/26) 
 * @since 1.0   
 */
public class StudentDA {
	static Student aStudent;
	static User aUser;

	// declare variables for the database connection
	static Connection aConnection;
	static Statement aStatement;

	// declare static variables for all user instance attribute values
	static long UserId;
	static String Password;
	static String FirstName;
	static String LastName;
	static String EmailAddress;

	static Date LastAccess;
	static Date EnrolDate;
	static boolean Enabled;
	static char Type;

	// declare static variables for all Student instance attribute values
	static String ProgramCode;
	static String ProgramDescription;
	static int Year;

	// Date Formatter
	private static final SimpleDateFormat SQL_DF = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * Establish the database connection
	 * @param c - Connection Object
	 */
	public static void initialize(Connection c) {
		try {
			aConnection = c;
			aStatement = aConnection.createStatement();
			aConnection.setAutoCommit(false);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	/**
	 * Closes the database connection
	 */
	public static void terminate() {
		try { // close the statement
			aStatement.close();
			aConnection.setAutoCommit(true);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	/**
	 * 
	 * @param id - unique Id of student
	 * @return A newly created student object based on the data retrieved from database
	 * @throws NotFoundException - Student was not found in database
	 */
	public static Student retrieve(long id) throws NotFoundException {

		aUser = null;
		
		try {
			// define the SQL query statement for user table using the UserId key
			PreparedStatement psRetrieve = aConnection.prepareStatement(
					"SELECT Users.UserId, Password, FirstName, LastName, EmailAddress, LastAccess, EnrollDate, Enabled, Type, ProgramCode, "
					+ "ProgramDescription, Year FROM Users, Students WHERE Users.UserId = Students.UserId AND Users.UserId = ?"
					);
			
			psRetrieve.setLong(1,id);

			ResultSet rs = psRetrieve.executeQuery();

			// next method sets cursor & returns true if there is data
			boolean gotIt = rs.next();
			if (gotIt) { // extract the data
				// set the user attribute values
				UserId = rs.getLong("UserId");
				Password = rs.getString("Password");
				FirstName = rs.getString("FirstName");
				LastName = rs.getString("LastName");
				EmailAddress = rs.getString("EmailAddress");
				LastAccess = rs.getDate("LastAccess");
				EnrolDate = rs.getDate("EnrollDate");
				Enabled = rs.getBoolean("Enabled");
				Type = rs.getString("Type").charAt(0);

				// set the student attribute values
				ProgramCode = rs.getString("ProgramCode");
				Year = rs.getInt("Year");
				ProgramDescription = rs.getString("ProgramDescription");

				// create User
				try {
					aStudent = new Student(UserId, Password, FirstName,
							LastName, EmailAddress, LastAccess, EnrolDate,
							Type, Enabled, ProgramCode, ProgramDescription,
							Year);
					
				} catch (InvalidUserDataException e) {
					System.out
							.println("Record for "
									+ FirstName
									+ " "
									+ LastName
									+ " contains invalid user data.  Verify and correct.");
				}
				
			} else { // nothing was retrieved

				throw (new NotFoundException("Student with id of " + id
						+ " not found in database."));
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return aStudent;
	}

	/**
	 * 
	 * @param aStudent - Student object
	 * @return If record was successfully created or not
	 * @throws DuplicateException - A duplicate record was founds
	 * @throws SQLException - A general SQL exception
	 * @throws InvalidUserDataException - Thrown when invalid data is passed into User constructor
	 * @throws NoSuchAlgorithmException - Thrown when there is a problem with the hashing algorithm 
	 */
	public static boolean create(Student aStudent) throws DuplicateException, SQLException, InvalidUserDataException, NoSuchAlgorithmException {
		boolean inserted = false; // insertion success flag
		
		// retrieve the user attribute values
		UserId = aStudent.getId();
		Password = aStudent.getPassword();
		FirstName = aStudent.getFirstName();
		LastName = aStudent.getLastName();
		EmailAddress = aStudent.getEmailAddress();
		LastAccess = aStudent.getLastAccess();
		EnrolDate = aStudent.getEnrollDate();
		Enabled = aStudent.isEnabled();
		Type = aStudent.getType();

		// retrieve the student attribute values
		ProgramCode = aStudent.getProgramCode();
		ProgramDescription = aStudent.getProgramDescription();
		Year = aStudent.getYear();

		//	Throw exception if user exists in database 
		if(UserDA.exists(UserId)){    
			throw (new DuplicateException(
					"Problem with creating Student record, student ID  "
							+ UserId + " already exists in the User database."));
		}
		
		//	Add User to user table
		UserDA.create(aStudent);
		
		// create the SQL insert statement for student table using attribute values
		PreparedStatement psStudentInsert = aConnection.prepareStatement(
				"INSERT INTO Students (UserId, ProgramCode, ProgramDescription, Year) "
				+ "VALUES (?,?,?,?)");

		psStudentInsert.setLong(1,UserId);
		psStudentInsert.setString(2, ProgramCode);
		psStudentInsert.setString(3, ProgramDescription);
		psStudentInsert.setLong(4, Year);
		
		try { // attempt to execute the SQL update statement on student table
			psStudentInsert.executeUpdate();
			inserted = true;
			
			//	Commit changes made to database
			aConnection.commit();
		} catch (SQLException ee) {
			System.out.println(ee);
			
			//	Roll back changes made to user table
			aConnection.rollback();
		}
		
		return inserted;
	}

	/**
	 * 
	 * @param aStudent - Student object
	 * @return Number of records deleted
	 * @throws NotFoundException - Record not found
	 */
	public static int delete(Student aStudent) throws NotFoundException {
		int records = 0;
		// retrieve the UserId (key)
		UserId = aStudent.getId();

		// see if this student already exists in the database
		try {
			// create the SQL delete statement
			PreparedStatement psStudentDelete = aConnection.prepareStatement(
					"DELETE FROM Students WHERE UserId = ?");
			
			//	Ensure user exists in user table
			if(!UserDA.exists(UserId)){
				throw new NotFoundException("Student with UserId " + UserId
						+ " cannot be deleted, does not exist.");
			}
			
			// if found, execute the SQL update statement
			psStudentDelete.setLong(1,UserId);
			records += psStudentDelete.executeUpdate();

			try{
				records += UserDA.delete(aStudent);
				
				//	Commit changes made to database
				aConnection.commit();
			}
			catch(SQLException e){
				System.out.println(e);

				//	Roll back changes
				aConnection.rollback();
			}
			
		} catch (SQLException ee){
			System.out.println(ee);
		}
		return records;
	}

	/**
	 * 
	 * @param aStudent - Student object
	 * @return Number of records updated
	 * @throws NotFoundException - Thrown when student does exist in database
	 * @throws NoSuchAlgorithmException - Thrown when cryptographic algorithm does not exist
	 */
	public static int update(Student aStudent) throws NotFoundException, NoSuchAlgorithmException {
		int records = 0;

		// retrieve the user attribute values
		UserId = aStudent.getId();
		Password = aStudent.getPassword();
		FirstName = aStudent.getFirstName();
		LastName = aStudent.getFirstName();
		EmailAddress = aStudent.getEmailAddress();
		LastAccess = aStudent.getLastAccess();
		EnrolDate = aStudent.getEnrollDate();
		Enabled = aStudent.isEnabled();
		Type = aStudent.getType();

		// retrieve the student attribute values
		ProgramCode = aStudent.getProgramCode();
		ProgramDescription = aStudent.getProgramDescription();
		Year = aStudent.getYear();

		// see if this student exists in the database
		try {

			if(!UserDA.exists(UserId)){
				throw new NotFoundException("Student with UserId " + UserId
						+ " cannot be updated, does not exist in the system.");
			}
			
			records += UserDA.update(aStudent);
			
			// define the SQL query statement for Student table using the UserId key
			PreparedStatement psStudentUpdate = aConnection.prepareStatement(
					"Update Students SET "
					+ "ProgramCode = ?, ProgramDescription = ?, Year = ?"
					+ "where UserId = ?");
			
			psStudentUpdate.setString( 1, ProgramCode);
			psStudentUpdate.setString( 2, ProgramDescription);
			psStudentUpdate.setLong(   3, Year);
			psStudentUpdate.setLong(   4, UserId);

			// if found, execute the SQL update statement
			
			try{
				records += psStudentUpdate.executeUpdate();
			}
			catch(SQLException e){
				System.out.println(e);

				//	Roll back changes
				aConnection.rollback();
			}

		} catch (SQLException ee) {
			System.out.println(ee);
		}
		
		return records;	
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
	 * Hashes a password
	 * @param password - Non-hashed password
	 * @return hashed passsword
	 * @throws NoSuchAlgorithmException - Thrown when cryptographic algorithm does not exist
	 */
	public static String hashPassword(String password) throws NoSuchAlgorithmException{

		MessageDigest md = MessageDigest.getInstance("SHA1"); 
		md.update(password.getBytes()); 
		byte[] bytesOfHashedString = md.digest();
		
		return decToHex(bytesOfHashedString);
	}

	/**
	 * 
	 * @param id - Unique id of student
	 * @param password - Non-hashed password
	 * @return Student object
	 * @throws NotFoundException - Thrown when student does exist in database
	 * @throws NoSuchAlgorithmException - Thrown when cryptographic algorithm does not exist
	 */
	public static Student authenticate(long id, String password) throws NotFoundException, NoSuchAlgorithmException {
		Student student = null;
		
		try{
			student = retrieve(id);
			
			//System.out.println("password: " + password + ", db hashed: " + student.getPassword() + ", hashed input: " + hashPassword(password));
		}
		catch(NotFoundException e){
			throw (new NotFoundException("Student with id of " + id
					+ " not found in database."));
		}
		
		if(!student.getPassword().equals(hashPassword(password))){
			throw new NotFoundException("Student with UserId " + UserId
					+ " and hashed password " + hashPassword(password) 
					+ " does not exist in the system.");
		}

		return student;
	}
	
	/**
	 * Checks if a student exists in the database
	 * @param id Student's Id
	 * @return True or false
	 */
	public static boolean exists(long id){
		boolean exists = false;
		
		try{
			retrieve(id);
			exists = true;
		}catch(NotFoundException e){
			exists = false;
		}
		
		return exists;
	}
}
