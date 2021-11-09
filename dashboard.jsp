<%String title = "Dashboard";%>
<%@include file="./header.jsp" %>

	<center>
		<h1><em><font color="red">Dashboard</font></em></h1>
		<hr/>
		<img src="images/dc_logo.svg" width="200" height="100" alt="Durham College Logo" /><hr/>
		
	</center>

	<center>
		<h1>Welcome <%out.print(aStudent.getFirstName());%></h1>
		<br/><h1><strong><%=message%></strong></h1>
	</center>
	
<%@include file="./footer.jsp" %>