/**
 * 
 */
package webd4201.sinkat;

import java.sql.Connection;
import java.util.Vector;

/**
 * A tester program
 *
 */
public class Deliverable3Tester {

	public static void main(String[] args) {
		  
		Connection c = null;
		try{  
	        // initialize the databases (i.e. connect)
	        c = DatabaseConnect.initialize();
	        Student.initialize(c);
	        Student aStudent;
	        try // get a Student
	        {
	                aStudent = Student.authenticate(100111111, "Password");
	        	//aStudent = Student.retrieve(100543802L);
	                System.out.println(aStudent.toString());
	        }
	        catch(NotFoundException e)
	                {	System.out.println(e);}
		  }catch(Exception e)
		  {
			  System.out.println(e);
		  }

	}

}
