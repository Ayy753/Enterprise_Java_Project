// UpdateStudentServlet.java


package webd4201.sinkat;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.security.NoSuchAlgorithmException;
import java.sql.*;

@SuppressWarnings("serial")
public class UpdateStudentServlet extends HttpServlet
{
//    public void doPost(HttpServletRequest request,
//                            HttpServletResponse response )
//                                throws ServletException, IOException
//   {
//	try 
//	{ /* 	retrieve the Student attribute from the session
//			and cast it to an object of type Student */
//
//            Student aStudent = (Student) request.getSession(false).getAttribute( "Student");
//            // get the name and address values from the HTML page
//            String name = request.getParameter("Name");
//            System.out.println("name:" + request.getParameter("Name"));
//            String addr = request.getParameter("Address");
//            System.out.println("addr:" + request.getParameter("Address"));
//            
//            /* if name or address from HTML page does not match
//                    info in DB, update DB */
//            if (!name.equals(aStudent.getFirstName())) 
//            {
//                aStudent.setFirstName(name);
//                aStudent.setEmailAddress(addr);
//                aStudent.update();
//            }
//            // invoke the StudentOptions.jsp program
//            response.sendRedirect("./StudentOptions.jsp");
//        }catch (NotFoundException | InvalidNameException | NoSuchAlgorithmException e)
//		{	} //do nothing
//    }
}


