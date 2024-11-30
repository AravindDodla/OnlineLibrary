<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html>
<%@include file='Index.html' %>
<head>
<meta charset="UTF-8">
<style>
h1{color:lightblue}
a{color:blue}
#ab{
  position:absolute;
  left:90%;
  top:10%;
  background-color:white;
}
</style>
<title>Check</title>
</head>
<body>

<%
    HttpSession sesion = request.getSession(false);
    if (sesion == null || sesion.getAttribute("uname") == null) {
        response.sendRedirect("AdministratorLogin.jsp");
        return;
    }
%>

<h1>Welcome, <%= session.getAttribute("uname") %></h1>
<a href="Staffdetails">Show Staff details</a><br>
<a href="searchbookdetails.jsp">Show Book Details</a><br>
<a href="BorrowedBooks">Show Borrowed Books</a><br>
<div id="ab">
<form action="LogoutServlet" method="post">
    <input type="submit" value="Logout">
</form>
</div>
</body>
</html>

