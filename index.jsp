<%String title = "DC Mark Tracker";%>
<%@include file="./header.jsp" %>

	<%
	//	Redirect to dashboard if user is already logged in
	if(aStudent != null){
		response.sendRedirect("./dashboard.jsp");
	}
	%>

	<center>
		<h1><em><font color="red">DC Mark Tracker</font></em></h1>
		<hr/>
			<img src = "images/dc_logo.svg" width = "200" height = "100" alt="Durham College Logo"/>
		<hr/>
	</center>

	<center>
		<p>This is a mark tracking website for Durham College students. It uses JavaServer Pages to execute Java code used to create, retrieve, and modify student information on the database. </p>
	</center>

	<%if(aStudent == null){%>
		<center><br/>If you are a Durham College student, please log in.
			<table style="margin-left:auto;margin-right:auto" bgcolor="lightgoldenrodyellow">
				<tr>
					<td width="100" style="text-align:center;">
						<a href="login.jsp">
						<strong><font size="+1">Log In</font></strong></a>
					</td>
				</tr>
			</table>
		</center>
	<%}%>

<%@include file="./footer.jsp" %>