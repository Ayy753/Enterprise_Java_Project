package webd4201.sinkat;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * RegisterServlet - This file is used by .jsp files to execute the java code required for a student to register to
 * the website 
 * @author Thomas Sinka
 *
 */
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Runs when the student submits their login and password into form
	 */
	public void doPost(HttpServletRequest request,
                            HttpServletResponse response) 
					throws ServletException, IOException
    {
        try
        { 
            // connect to database
            Connection c = DatabaseConnect.initialize();
            Student.initialize(c);
            User.initialize(c);
            HttpSession session = request.getSession(true);
            
            String login = new String();
            String password  = new String();
            String firstName  = new String();
            String lastName  = new String();
            String confirmPassword = new String();
            String emailAddress = new String();
            String programCode = new String();
            String programDescription = new String();
            String year = new String();
            
    		GregorianCalendar cal = new GregorianCalendar();
    		Date lastAccess = cal.getTime();
    		Date enrol = cal.getTime();

    		StringBuffer errorBuffer = new StringBuffer();
    		
    		Student newStudent = null;
            
            session.setAttribute("errors", "");
            //session.setAttribute("message", "Test Message");
            try 
            {   // retrieve data from DB
            	login = request.getParameter( "Login" ); //this is the name of the text input box on the form
                
                firstName  = request.getParameter("FirstName");
                lastName  = request.getParameter("LastName");
                password  = request.getParameter("Password");                
                confirmPassword  = request.getParameter("ConfirmPassword");
                emailAddress  = request.getParameter("Email");
                programCode  = request.getParameter("ProgramCode");
                programDescription  = request.getParameter("ProgramDescription");
                year  = request.getParameter("Year");
                
                //	User data validation
                if(login.length() == 0){
                	errorBuffer.append("</br>You must enter student ID.");
                }
                else {
                	if(login.length() != User.ID_NUMBER_LENGTH){
                		errorBuffer.append("</br>You must enter a numeric student ID with a legnth of " 
                				+ User.ID_NUMBER_LENGTH + " numbers.");
                    	login = "";
                	}
                    else{
                        try{
                        	Long.parseLong(login);
                        }
                        catch(NumberFormatException nume){
                            errorBuffer.append("</br>The login ID must not contain non-numeric characters.");
                            login = "";
                        } 	
                    }
                }
                
				if(firstName.length() == 0){
					errorBuffer.append("</br>You must enter your first name.");
				}
				
				if(lastName.length() == 0){
					errorBuffer.append("</br>You must enter your last name.");
				}            
				
				if(password.length() == 0){
					errorBuffer.append("</br>You must enter a password.");
				}
				else{
					
			        if(confirmPassword.length() == 0){
						errorBuffer.append("</br>You must confirm your password.");
					}
			        else if(!password.equals(confirmPassword)){
			        	errorBuffer.append("</br>Your passwords did not match.");
			        }
			        
			        if(password.length() < User.MINIMUM_PASSWORD_LENGTH){
			        	errorBuffer.append("</br>The minimum password length is " + User.MINIMUM_PASSWORD_LENGTH + ".");
			        }
			        else if(password.length() > User.MAXIMUM_PASSWORD_LENGTH){
			        	errorBuffer.append("</br>The maximum password length is " + User.MAXIMUM_PASSWORD_LENGTH + ".");
			        }		
				}

                if(emailAddress.length() == 0){
                	errorBuffer.append("</br>You must enter your email address.");
                }     
		        
                //	Insert default student information to blank fields
                if(programCode.length() == 0){
                	programCode = Student.DEFAULT_PROGRAM_CODE;
                }
                
                if(programDescription.length() == 0){
                	programDescription = Student.DEFAULT_PROGRAM_DESCRIPTION;
                }
                
                if(year.length() == 0){
                	year = Integer.toString(Student.DEFAULT_YEAR);
                }
                else{
                    try{
                    	Long.parseLong(year);
                    }
                    catch(NumberFormatException nume){
                        errorBuffer.append("</br>Your program year must not contain non-numeric characters.");
                        year = Integer.toString(Student.DEFAULT_YEAR);
                    }
                }
                
                if(errorBuffer.length() == 0){
                	newStudent = new Student(Long.parseLong(login), password, firstName,
        					lastName, emailAddress, enrol, lastAccess, 's',
        					true, programCode, programDescription, Integer.parseInt(year));
                    StudentDA.create(newStudent);
                }
                
                session.setAttribute("errors", errorBuffer.toString());
                
                if(newStudent != null){
    				//put the found Student onto the session
                    session.setAttribute("Student", newStudent);
                    
                    session.setAttribute("message", "You have successfully registered.");
                    
    				//empty out any errors if there were some
                    session.setAttribute("errors", "");
             
                    // redirect the Student to a JSP
                    response.sendRedirect("./dashboard.jsp");
                }
                else{
                    session.setAttribute("login", login);
                    session.setAttribute("firstName", firstName);
                    session.setAttribute("lastName", lastName);
                    session.setAttribute("emailAddress", emailAddress);
                    session.setAttribute("programCode", programCode);
                    session.setAttribute("programDescription", programDescription);
                    session.setAttribute("year", year);                 	
                	
                	response.sendRedirect("./register.jsp");
                }
                
                //	Clear passwords regardless of if they were valid or not
                password = "";
                confirmPassword = "";
                
                //	Terminate DA
                Student.terminate();
                User.terminate();
            }
            catch(InvalidUserDataException iude)
            {
                errorBuffer.append("<strong>Your information is not valid for an unknown reason.<br/>");
                errorBuffer.append("Please contact admins.</strong>");
                
                session.setAttribute("errors", errorBuffer.toString());
                
                //	Terminate DA
                Student.terminate();
                User.terminate();
                
                response.sendRedirect("./register.jsp");
            }
            catch(DuplicateException de){
                session.setAttribute("errors", "Our records indicate a user with an ID of " + login 
                		+ " already exists. Please log in");
                
                //	Terminate DA
                Student.terminate();
                User.terminate();
                
                response.sendRedirect("./register.jsp");
            }
        }    
   	 catch (Exception e) //not connected
        {
            System.out.println(e);
            String line1="<h2>A network error has occurred!</h2>";
            String line2="<p>Please notify your system " +
                                                    "administrator and check log. "+e.toString()+"</p>";
            formatErrorPage(line1, line2,response);
        }
    }
	
	/**
	 * Redirects get to post
	 */
    public void doGet(HttpServletRequest request,
                            HttpServletResponse response)
                                    throws ServletException, IOException {
    	HttpSession session = request.getSession(true);
    	session.setAttribute("errors", "");
        doPost(request, response);
        
        
    }

    /**
     * Formats a error page 
     * @param first First argument
     * @param second Second argument
     * @param response HTTP response
     * @throws IOException - Thrown when some strage IO error occurs
     */
    public void formatErrorPage( String first, String second,
            HttpServletResponse response) throws IOException
    {
        PrintWriter output = response.getWriter();
        response.setContentType( "text/html" );
        output.println(first);
        output.println(second);
        output.close();
    }
}