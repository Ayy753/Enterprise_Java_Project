<%@ page import = "java.util.*" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
   	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en"> 
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />	
	<title><%= title %></title>
	<link href="./css/style.css" rel="stylesheet" type="text/css" />
</head>

<%@ page import = "webd4201.sinkat.*" %>

<%  String errorMessage = (String)session.getAttribute("errors"); 
	Student aStudent = (Student)session.getAttribute("Student");
	String message = (String)session.getAttribute("message");
	if(errorMessage == null)
		errorMessage="";
	if(message == null)
		message  = "";

%>

<body>
	<div id="header">
		<div class="clearfix">
			<div class="logo">
				<a href="index.jsp"><img src="images/logo.png" alt="LOGO" height="100" width="300" /></a>
			</div>
			<ul class="navigation">

				<li class="active">
					<a href="index.jsp">Home</a>
				</li>

				<!--
				<li>
					<a href="about.jsp">About</a>
				</li>
				-->


				<%if(aStudent != null){	%>
				<li>
					<a href="./Logout">Logout</a>
				</li>				
				<li>
					<a href="update.jsp">Update</a>
				</li>				
					
				<%}
				else{%>

				<li>
					<a href="login.jsp">Login</a>
				</li>
				<li>
					<a href="register.jsp">Register</a>
				</li>					
				<%}%>



			</ul>
		</div>
	</div>
	<div id="contents">
	<!-- end of header.jsp --> 
