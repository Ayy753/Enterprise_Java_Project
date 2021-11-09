<%String title = "Register";%>

<%@include file="./header.jsp" %>

<%
	//	Redirect to dashboard if user is already logged in
	if(aStudent != null){
		session.setAttribute("message", "You are already registered!");
		response.sendRedirect("./dashboard.jsp");
	}

	String login = (String)session.getAttribute("login");
	String firstName = (String)session.getAttribute("firstName");
	String lastName = (String)session.getAttribute("lastName");
	String emailAddress = (String)session.getAttribute("emailAddress");
	String programCode = (String)session.getAttribute("programCode");
	String programDescription = (String)session.getAttribute("programDescription");
	String year = (String)session.getAttribute("year");

	if(login == null)
		login = "";
	if(firstName == null)
		firstName = "";
	if(lastName == null)
		lastName = "";
	if(emailAddress == null)
		emailAddress = "";
	if(programCode == null)
		programCode = "";
	if(programDescription == null)
		programDescription = "";
	if(year == null)
		year = "";
%>

	<script type="text/javascript">
		//	A function to clear the fields in the form
		function clearForm(){
			document.getElementsByName("Login")[0].value = "";
			document.getElementsByName("FirstName")[0].value = "";
			document.getElementsByName("LastName")[0].value = "";
			document.getElementsByName("Email")[0].value = "";
			document.getElementsByName("ProgramCode")[0].value = "";
			document.getElementsByName("ProgramDescription")[0].value = "";
			document.getElementsByName("Year")[0].value = "";
		}
	</script>

	<center>
		<hr/><img src="images/dc_logo.svg" width="200" height="100" alt="Durham College Logo" /><hr/>
		<br/><h1><strong><%=message%></strong></h1>
		<br/><h2>Please enter your information</h2><br/>
		<p>Enter your login information below.<br/>
		   If you are not a Student, please return to the
		   <a href="index.jsp">Durham College</a> home page.</p>
		<p>
		   If you are already registered on our system, please <a href="login.jsp">login</a>.</p>
		<form name="Input" method="post" action="./Register" >
			
			<table border="0" bgcolor="lightgoldenrodyellow" cellpadding="10" >

			<tr><td colspan="2" style="margin-left:auto;margin-right:auto"><%= errorMessage %></td>
			</tr>
			<tr>
				<td><strong>Login Id</strong></td>
				<td><input type="text" name="Login" value="<%= login %>" size="20"/></td>
			</tr>

			<tr>
				<td><strong>First Name</strong></td>
				<td><input type="text" name="FirstName" value="<%= firstName %>" size="20"/></td>
			</tr>
			<tr>
				<td><strong>Last Name</strong></td>
				<td><input type="text" name="LastName" value="<%= lastName %>" size="20"/></td>
			</tr>

			<tr>
				<td><strong>Password</strong></td>
				<td><input type="password" name="Password" size="20"/></td>
			</tr>		
			<tr>
				<td><strong>Confirm Password</strong></td>
				<td><input type="password" name="ConfirmPassword" size="20"/></td>
			</tr>		
			<tr>
				<td><strong>Email Address</strong></td>
				<td><input type="text" name="Email" value="<%= emailAddress %>" size="20"/></td>
			</tr>			
			<tr>
				<td><strong>Program Code</strong></td>
				<td><input type="text" name="ProgramCode" value="<%= programCode %>" size="20"/></td>
			</tr>	
			<tr>
				<td><strong>Program Description</strong></td>
				<td><input type="text" name="ProgramDescription" value="<%= programDescription %>" size="20"/></td>
			</tr>				
			<tr>
				<td><strong>Year</strong></td>
				<td><input type="text" name="Year" value="<%= year %>" size="20"/></td>
			</tr>				

			</table>
			<table border="0" cellspacing="15" >
			<tr>
				<td><input type="submit" value = "Register"/></td>
				<td><button type="button" onclick="clearForm()">Clear</button></td>
			</tr>
			</table>
		</form>
		Please wait after pressing <strong>Register</strong>
		while we process your information to our database.<br/>
		<em>(This may take a few moments)</em>
	</center>
<%@include file="./footer.jsp" %>