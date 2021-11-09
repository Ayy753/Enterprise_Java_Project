package webd4201.sinkat;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

/**
 * LogoutServlet - Logs the student out by removing their session and redirect them to the login page.
 * @author Thomas
 *
 */
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Remove the student session and redirect to login page
	 */
	public void doPost(HttpServletRequest request,
                            HttpServletResponse response) 
					throws ServletException, IOException
    {
        Connection c = DatabaseConnect.initialize();
        Student.initialize(c);
        HttpSession session = request.getSession(true);
		session.removeAttribute("Student");
		session.setAttribute("message", "You have successfully logged out.");
		
		
		response.sendRedirect("./login.jsp");
    }
	
	/**
	 * Redirects to post
	 */
    public void doGet(HttpServletRequest request,
                            HttpServletResponse response)
                                    throws ServletException, IOException {
        doPost(request, response);
    }


}