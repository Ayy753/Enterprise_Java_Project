<%String title = "Login Page";%>
<%@include file="./header.jsp" %>

	<%

		//	Redirect to dashboard if user is already logged in
		if(aStudent != null){
			session.setAttribute("message", "You are already logged in!");
			response.sendRedirect("./dashboard.jsp");
		}

		String login = (String)session.getAttribute("login");
		if(login == null)
			login = "";
	%>

	<center>
		<hr/><img src="images/dc_logo.svg" width="200" height="100" alt="Durham College Logo" /><hr/>
		<br/><h1><strong><%=message%></strong></h1>
		<br/><h2>Please log in</h2>
		<p>Enter your login information below.<br/>
		   If you are not a Student, please return to the
		   <a href="index.jsp">Durham College</a> home page.</p>
		<p>
		   If you want to become a Student on our system, please <a href="register.jsp">register</a>.</p>
		<form name="Input" method="post" action="./Login" >
			<!-- action="http://localhost:81/Bradshaw/LoginServlet" > -->
			<table border="0" bgcolor="lightgoldenrodyellow" cellpadding="10" >
			<tr><td colspan="2" style="margin-left:auto;margin-right:auto"><%= errorMessage %></td>
			</tr>
			<tr>
				<td><strong>Login Id</strong></td>
				<td><input type="text" name="Login" value="<%= login %>" size="20"/></td>
			</tr>
			<tr>
				<td><strong>Password</strong></td>
				<td><input type="password" name="Password" size="20"/></td>
			</tr>			
			</table>
			<table border="0" cellspacing="15" >
			<tr>
				<td><input type="submit" value = "Log In"/></td>
				<td><input type="reset" value = "Clear"/></td>



			</tr>
			</table>
		</form>
		Please wait after pressing <strong>Log in</strong>
		while we retrieve your records from our database.<br/>
		<em>(This may take a few moments)</em>
	</center>

<%@include file="./footer.jsp" %>
