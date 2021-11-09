package webd4201.sinkat;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

/**
 * LoginServlet - This file is used by .jsp files to execute the java code required for a student to log into
 * the website 
 * @author Thomas Sinka
 *
 */
public class LoginServlet extends HttpServlet {

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
            session.setAttribute("errors", "");
            session.setAttribute("message", "");
            try 
            {   // retrieve data from DB
            	login = request.getParameter( "Login" ); //this is the name of the text input box on the form
                password  = request.getParameter("Password");
                
                Student aStudent = StudentDA.authenticate(Long.parseLong(login), password); //attempt to find the Student, this could cause a NotFoundException
                // if the Student was found and retrieved from the db
                
                if(aStudent != null){
    				//put the found Student onto the session
                    session.setAttribute("Student", aStudent);
    				//empty out any errors if there were some
                    session.setAttribute("errors", "");
             
                    // redirect the Student to a JSP
                    response.sendRedirect("./dashboard.jsp");
                }

            }catch(NotFoundException nfe)
            {
                //new code == way better, if I do say so myself
                //sending errors to the page thru the session
                StringBuffer errorBuffer = new StringBuffer();
                errorBuffer.append("<strong>Your sign in information is not valid.<br/>");
                errorBuffer.append("Please try again.</strong>");
                if(Student.exists(Long.parseLong(login))){
                	session.setAttribute("login", login);
                	errorBuffer.append("</br>Invalid password.");
                }
                else
                {
                  errorBuffer.append("</br>Invalid login id.");
                  session.setAttribute("login", "");
                }
                session.setAttribute("errors", errorBuffer.toString());
                response.sendRedirect("./login.jsp");
            
            //for the first deliverable you will have to check if there was a problem
            //with just the password, or login id and password
            //this will require a special method e.g. public static boolean isExistingLogin(String arg);
            }catch(NumberFormatException nume){
                StringBuffer errorBuffer = new StringBuffer();
                errorBuffer.append("<strong>Your sign in information is not valid.<br/>");
                errorBuffer.append("Please try again.</strong>");
                errorBuffer.append("</br>The login ID must not contain non-numeric characters.");
            	session.setAttribute("errors", errorBuffer.toString());
            	
            	response.sendRedirect("./login.jsp");
            }
            Student.terminate();
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