package webd4201.sinkat;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserDA {
	
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
	 * @param id - unique Id of user
	 * @return A newly created user object based on the data retrieved from database
	 * @throws NotFoundException - user was not found in database
	 */
	public static User retrieve(long id) throws NotFoundException {
		aUser = null;
		
		try {
			
			// define the SQL query statement for user table using the UserId key
			PreparedStatement psRetrieve = aConnection.prepareStatement(
					"SELECT UserId, Password, FirstName, LastName, EmailAddress, LastAccess, EnrollDate, Enabled, Type "
					+ " FROM Users WHERE UserId = ?"
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

				// create User
				try {
					
					aUser = new User(UserId, Password, FirstName,
							LastName, EmailAddress, LastAccess, EnrolDate,
							Type, Enabled);

				} catch (InvalidUserDataException e) {
					System.out
							.println("Record for "
									+ FirstName
									+ " "
									+ LastName
									+ " contains invalid user data.  Verify and correct.");
				}
			} else { // nothing was retrieved

				throw (new NotFoundException("User with id of " + id
						+ " not found in database."));
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return aUser;
	}

	/**
	 * 
	 * @param aUser - user object
	 * @return If record was successfully created or not
	 * @throws DuplicateException - A duplicate record was founds
	 * @throws SQLException - A general SQL exception
	 * @throws NoSuchAlgorithmException - Thrown when there is a problem with the hashing algorithm 
	 */
	public static boolean create(User aUser) throws DuplicateException, SQLException, NoSuchAlgorithmException {
		boolean inserted = false; // insertion success flag

		// retrieve the user attribute values
		UserId = aUser.getId();
		Password = aUser.getPassword();
		FirstName = aUser.getFirstName();
		LastName = aUser.getLastName();
		EmailAddress = aUser.getEmailAddress();
		LastAccess = aUser.getLastAccess();
		EnrolDate = aUser.getEnrollDate();
		Enabled = aUser.isEnabled();
		Type = aUser.getType();
		
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
		
		// see if this user already exists in the database
		try {
			retrieve(aUser.getId());
			throw (new DuplicateException(
					"Problem with creating user record, user ID  "
							+ UserId + " already exists in the system."));
		}
		// if NotFoundException, add user to database
		catch (NotFoundException e) {
			try { // execute the SQL update statement
				psUserInsert.executeUpdate();
				//psuserInsert.executeUpdate();
				inserted = true;
			} catch (SQLException ee) {
				System.out.println(ee);
			}
		}
		return inserted;
	}

	/**
	 * 
	 * @param aUser - user object
	 * @return Number of records deleted
	 * @throws NotFoundException - Record not found
	 */
	public static int delete(User aUser) throws NotFoundException {
		int records = 0;
		// retrieve the UserId (key)
		UserId = aUser.getId();

		// see if this user already exists in the database
		try {
			// create the SQL delete statement
			
			PreparedStatement psUserDelete = aConnection.prepareStatement(
					"DELETE FROM Users WHERE UserId = ?");
						
			//psuserDelete.setLong(1,UserId);
			psUserDelete.setLong(1,UserId);
							
			User.retrieve(UserId); // used to determine if record exists for the passed user

			// if found, execute the SQL update statement
			//records += psuserDelete.executeUpdate();
			records += psUserDelete.executeUpdate();
			
		} catch (NotFoundException e) {
			throw new NotFoundException("user with UserId " + UserId
					+ " cannot be deleted, does not exist.");
		} catch (SQLException e)

		{
			System.out.println(e);
		}
		return records;
	}

	/**
	 * 
	 * @param aUser - user object
	 * @return Number of records updated
	 * @throws NotFoundException - Thrown when user does exist in database
	 * @throws NoSuchAlgorithmException - Thrown when cryptographic algorithm does not exist
	 * @throws SQLException - A general SQL exception
	 */
	public static int update(User aUser) throws NotFoundException, NoSuchAlgorithmException, SQLException {
		int records = 0;

		// retrieve the user attribute values
		UserId = aUser.getId();
		Password = aUser.getPassword();
		FirstName = aUser.getFirstName();
		LastName = aUser.getFirstName();
		EmailAddress = aUser.getEmailAddress();
		LastAccess = aUser.getLastAccess();
		EnrolDate = aUser.getEnrollDate();
		Enabled = aUser.isEnabled();
		Type = aUser.getType();

		MessageDigest md = MessageDigest.getInstance("SHA1"); 
		md.update(Password.getBytes()); 
		byte[] bytesOfHashedString = md.digest();

		// see if this user exists in the database
		// NotFoundException is thrown by find method
		try {

			// create the SQL insert statement for user table using attribute values
			PreparedStatement psUserUpdate = aConnection.prepareStatement(
					"Update Users SET "
					+ "Password = ?, FirstName = ?, LastName = ?, EmailAddress = ?,"
					+ " LastAccess = ?, EnrollDate = ?, Enabled = ?, Type = ? "
					+ "where UserId = ?");
			
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
			
			User.retrieve(Long.valueOf(UserId)); // determine if there is a
													// user record to be
													// updated

			// if found, execute the SQL update statement
			records += psUserUpdate.executeUpdate();
			//records += psuserUpdate.executeUpdate();


		} catch (NotFoundException e) {
			throw new NotFoundException("user with UserId " + UserId
					+ " cannot be updated, does not exist in the system.");
		} catch (SQLException e) {
			System.out.println(e);
			
			throw new SQLException(e);
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
	 * @param id - Unique id of user
	 * @param password - Non-hashed password
	 * @return user object
	 * @throws NotFoundException - Thrown when user does exist in database
	 * @throws NoSuchAlgorithmException - Thrown when cryptographic algorithm does not exist
	 */
	public static User authenticate(long id, String password) throws NotFoundException, NoSuchAlgorithmException {
		//user user = null;
		User aUser = null;
		
		try{
			//user = retrieve(id);
			aUser = retrieve(id);
			
			//System.out.println("password: " + password + ", db hashed: " + user.getPassword() + ", hashed input: " + hashPassword(password));
		}
		catch(NotFoundException e){
			throw (new NotFoundException("user with id of " + id
					+ " not found in database."));
		}
		
		if(!aUser.getPassword().equals(hashPassword(password))){
			throw new NotFoundException("user with UserId " + UserId
					+ " and hashed password " + hashPassword(password) 
					+ " does not exist in the system.");
		}

		return aUser;
	}
	
	/**
	 * Checks if a user exists in the database
	 * @param id user's Id
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

