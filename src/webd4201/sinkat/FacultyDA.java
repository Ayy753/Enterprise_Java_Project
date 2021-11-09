package webd4201.sinkat;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 * FacultyDA - this file is contains all of the data access methods, that actually get/set data to the database. 
 * Note: that all the methods are static this is because you do not really create FacultyDA objects (does not make sense)
 * @author Thomas Sinka
 * @version 1.0 (2020/3/3) 
 * @since 1.0   
 */
public class FacultyDA {
	static Faculty aFaculty;

	// declare variables for the database connection
	
	/**
	 * Database connection object
	 */
	static Connection aConnection;
	
	/**
	 * Connection statement
	 */
	static Statement aStatement;

	// declare static variables for all user instance attribute values
	/**
	 * Unique Faculty's id
	 */
	static long UserId;
	
	/**
	 * Faculty's password
	 */
	static String Password;
	
	/**
	 * Faculty's first name
	 */
	static String FirstName;
	
	/**
	 * Faculty's last name
	 */
	static String LastName;
	
	/**
	 * Faculty's email address
	 */
	static String EmailAddress;

	/**
	 * Faculty's last access date
	 */
	static Date LastAccess;
	
	/**
	 * Faculty's enroll date
	 */
	static Date EnrolDate;
	
	/**
	 * Account enabled status
	 */
	static boolean Enabled;
	
	/**
	 * User account type
	 */
	static char Type;
	
	// declare static variables for all faculty instance attribute values
	
	/**
	 * Faculty's school code
	 */
	static String SchoolCode;
	
	/**
	 * Faculty's school description
	 */
	static String SchoolDescription;
	
	/**
	 * Faculty's office number
	 */
	static String Office;
	
	/**
	 * Faculty's extension number
	 */
	static int Extension;
	
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
	 * @param id - unique Id of faculty
	 * @return A newly created faculty object based on the data retrieved from database
	 * @throws NotFoundException - faculty was not found in database
	 */
	public static Faculty retrieve(long id) throws NotFoundException {

		aFaculty = null;
		
		try {
			// define the SQL query statement for user table using the UserId key
			PreparedStatement psRetrieve = aConnection.prepareStatement(
					"SELECT Users.UserId, Password, FirstName, LastName, EmailAddress, LastAccess, EnrollDate, Enabled, Type, "
					+ "SchoolCode, SchoolDescription, Office, Extension FROM Users, Faculty WHERE Users.UserId = faculty.UserId AND Users.UserId = ?"
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

				// set the faculty attribute values
				SchoolCode = rs.getString("SchoolCode");
				SchoolDescription = rs.getString("SchoolDescription");
				Office = rs.getString("Office");
				Extension = rs.getInt("Extension");

				// create faculty
				try {
					aFaculty = new Faculty(UserId, Password, FirstName,
							LastName, EmailAddress, LastAccess, EnrolDate,
							Enabled, Type, SchoolCode, SchoolDescription, Office, Extension);

				} catch (InvalidUserDataException e) {
					System.out
							.println("Record for "
									+ FirstName
									+ " "
									+ LastName
									+ " contains invalid user data.  Verify and correct.");
				}
			} else { // nothing was retrieved

				throw (new NotFoundException("faculty with id of " + id
						+ " not found in database."));
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return aFaculty;
	}
	
	
	/**
	 * 
	 * @param aFaculty - Faculty object
	 * @return If record was successfully created or not
	 * @throws DuplicateException - A duplicate record was founds
	 * @throws SQLException - A general SQL exception
	 * @throws NoSuchAlgorithmException  - Thrown when cryptographic algorithm does not exist
	 */
	public static boolean create(Faculty aFaculty) throws DuplicateException, SQLException, NoSuchAlgorithmException {
		boolean inserted = false; // insertion success flag

		// retrieve the user attribute values
		UserId = aFaculty.getId();
		Password = aFaculty.getPassword();
		FirstName = aFaculty.getFirstName();
		LastName = aFaculty.getLastName();
		EmailAddress = aFaculty.getEmailAddress();
		LastAccess = aFaculty.getLastAccess();
		EnrolDate = aFaculty.getEnrollDate();
		Enabled = aFaculty.isEnabled();
		Type = aFaculty.getType();

		// retrieve the Faculty attribute values
		SchoolCode = aFaculty.getSchoolCode();
		SchoolDescription = aFaculty.getSchoolDescription();
		Office = aFaculty.getOffice();
		Extension = aFaculty.getExtension();
		
		//	hashing stuff
		MessageDigest md = MessageDigest.getInstance("SHA1"); 
		md.update(Password.getBytes()); 
		byte[] bytesOfHashedString = md.digest();

		// create the SQL insert statement for user table using attribute values
		PreparedStatement psUserInsert = aConnection.prepareStatement(
				"INSERT INTO USERS "
				+ "(UserId, Password, FirstName, LastName, EmailAddress, LastAccess, EnrollDate, Enabled, Type) "
				+ "VALUES (?,?,?,?,?,?,?,?,?)");
		
		psUserInsert.setLong(1,UserId);
		psUserInsert.setString(2, decToHex(bytesOfHashedString));
		psUserInsert.setString(3, FirstName);
		psUserInsert.setString(4, LastName);
		psUserInsert.setString(5, EmailAddress);
		psUserInsert.setDate(6, new java.sql.Date(LastAccess.getTime()));
		psUserInsert.setDate(7, new java.sql.Date(EnrolDate.getTime()));
		psUserInsert.setBoolean(8, Enabled);
		psUserInsert.setString(9, String.valueOf(Type));

		// create the SQL insert statement for Faculty table using attribute
		// values
		PreparedStatement psFacultyInsert = aConnection.prepareStatement(
				"INSERT INTO Faculty (UserId, SchoolCode, SchoolDescription, Office, Extension) "
				+ "VALUES (?,?,?,?,?)");

		psFacultyInsert.setLong(1,UserId);
		psFacultyInsert.setString(2,SchoolCode);
		psFacultyInsert.setString(3, SchoolDescription);
		psFacultyInsert.setString(4, Office);
		psFacultyInsert.setInt(5, Extension);
		
		// see if this Faculty already exists in the database
		try {
			retrieve(aFaculty.getId());
			throw (new DuplicateException(
					"Problem with creating Faculty record, Faculty ID  "
							+ UserId + " already exists in the system."));
		}
		// if NotFoundException, add Faculty to database
		catch (NotFoundException e) {
			try { // execute the SQL update statement
				psUserInsert.executeUpdate();
				psFacultyInsert.executeUpdate();
				inserted = true;
			} catch (SQLException ee) {
				System.out.println(ee);
			}
		}
		return inserted;
	}
	
	/**
	 * 
	 * @param aFaculty - Faculty object
	 * @return Number of records deleted
	 * @throws NotFoundException - Record not found
	 */
	public static int delete(Faculty aFaculty) throws NotFoundException {
		int records = 0;
		// retrieve the UserId (key)
		UserId = aFaculty.getId();

		// see if this Faculty already exists in the database
		try {
			// create the SQL delete statement
			
			PreparedStatement psFacultyDelete = aConnection.prepareStatement(
					"DELETE FROM Faculty WHERE UserId = ?");
			
			PreparedStatement psUserDelete = aConnection.prepareStatement(
					"DELETE FROM Users WHERE UserId = ?");
						
			psFacultyDelete.setLong(1,UserId);
			psUserDelete.setLong(1,UserId);
							
			Faculty.retrieve(UserId); // used to determine if record exists for the passed Faculty

			// if found, execute the SQL update statement
			records += psFacultyDelete.executeUpdate();
			records += psUserDelete.executeUpdate();
			
			
		} catch (NotFoundException e) {
			throw new NotFoundException("Faculty with UserId " + UserId
					+ " cannot be deleted, does not exist.");
		} catch (SQLException e)

		{
			System.out.println(e);
		}
		return records;
	}
	
	
	/**
	 * 
	 * @param aFaculty - Faculty object
	 * @return Number of records updated
	 * @throws NotFoundException - Thrown when Faculty does exist in database
	 * @throws NoSuchAlgorithmException - Thrown when cryptographic algorithm does not exist
	 */
	public static int update(Faculty aFaculty) throws NotFoundException, NoSuchAlgorithmException {
		int records = 0;

		// retrieve the user attribute values
		UserId = aFaculty.getId();
		Password = aFaculty.getPassword();
		FirstName = aFaculty.getFirstName();
		LastName = aFaculty.getFirstName();
		EmailAddress = aFaculty.getEmailAddress();
		LastAccess = aFaculty.getLastAccess();
		EnrolDate = aFaculty.getEnrollDate();
		Enabled = aFaculty.isEnabled();
		Type = aFaculty.getType();

		// retrieve the Faculty attribute values
		SchoolCode = aFaculty.getSchoolCode();
		SchoolDescription = aFaculty.getSchoolDescription();
		Office = aFaculty.getOffice();
		Extension = aFaculty.getExtension();

		MessageDigest md = MessageDigest.getInstance("SHA1"); 
		md.update(Password.getBytes()); 
		byte[] bytesOfHashedString = md.digest();

		// see if this Faculty exists in the database
		// NotFoundException is thrown by find method
		try {

			// create the SQL insert statement for user table using attribute values
			PreparedStatement psUserUpdate = aConnection.prepareStatement(
					"Update Users SET "
					+ "Password = ?, FirstName = ?, LastName = ?, EmailAddress = ?,"
					+ " LastAccess = ?, EnrollDate = ?, Enabled = ?, Type = ? "
					+ "where UserId = ?");
			
			//	Check if password isn't already hashed
			if(Password.length() != 40){
				psUserUpdate.setString( 1, decToHex(bytesOfHashedString));
				
			}
			else{
				psUserUpdate.setString( 1, Password);
			}
			
			psUserUpdate.setString( 2, FirstName);
			psUserUpdate.setString( 3, LastName);
			psUserUpdate.setString( 4, EmailAddress);
			psUserUpdate.setDate(   5, new java.sql.Date(LastAccess.getTime()));
			psUserUpdate.setDate(   6, new java.sql.Date(EnrolDate.getTime()));
			psUserUpdate.setBoolean(7, Enabled);
			psUserUpdate.setString( 8, String.valueOf(Type));
			psUserUpdate.setLong(   9, UserId);

			
			// define the SQL query statement for Faculty table using the UserId key
			PreparedStatement psFacultyUpdate = aConnection.prepareStatement(
					"Update Faculty SET "
					+ "SchoolCode = ?, SchoolDescription = ?, Office = ?, Extension = ?"
					+ "where UserId = ?");
			
			
			psFacultyUpdate.setString(1,SchoolCode);
			psFacultyUpdate.setString(2, SchoolDescription);
			psFacultyUpdate.setString(3, Office);
			psFacultyUpdate.setInt(4, Extension);
			psFacultyUpdate.setLong(5,UserId);
			
			Faculty.retrieve(Long.valueOf(UserId)); // determine if there is a
													// Faculty record to be
													// updated

			// if found, execute the SQL update statement
			records += psUserUpdate.executeUpdate();
			records += psFacultyUpdate.executeUpdate();


		} catch (NotFoundException e) {
			throw new NotFoundException("Faculty with UserId " + UserId
					+ " cannot be updated, does not exist in the system.");
		} catch (SQLException e) {
			System.out.println(e);
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

}
