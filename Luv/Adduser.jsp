<%@page import="com.Training.Library.LibraryDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<center>
<h1>Add User</h1>
<form action="Adduser.jsp" method="post">
	Enter User Name:
	<input type="text" name="Username" /><br/><br/>
	Password:
	<input type="password" name="Password" /><br/><br/>
	<input type="submit" value="Submit" />
</form>
</center>
<%
if(request.getParameter("Password")!=null){
	String Username = request.getParameter("Username");
	String Password = request.getParameter("Password");
	LibraryDAO dao = new LibraryDAO();
	dao.AddUser(Username,Password);
}

%>

</body>
</html>