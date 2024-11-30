<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@include file='Index.html' %>
<head>
    <meta charset="UTF-8">
    <title>Login Success</title>
</head>
<body>
    <h1>Welcome, <%= session.getAttribute("uname") %></h1>

    <!-- Hidden input field to store userID -->
    <input type="hidden" name="userID" value="<%= session.getAttribute("userID") %>">

    <!-- Link to display book details (if already implemented) -->
    <a href="DisplayBooks">Click Here to see book details</a><br>

    <!-- Link to display borrowing history in an iframe -->
    <a href="BorrowHistoryUserI" target="historyFrame">Click Here to view your Borrowing History</a>

    <!-- Iframe to display the borrowing history -->
    <iframe name="historyFrame" style="width: 80%; height: 200px; position:relative; border: 0px solid #000;"></iframe>

</body>
</html>
