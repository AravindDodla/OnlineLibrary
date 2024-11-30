<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@include file='Index.html' %>
<head>
<meta charset="UTF-8">
<title>Book Details</title>
<style>
form {
    margin-right: 35%;
    margin-left: 35%;
    margin-top: 10%;
}
fieldset {
    border: 3px solid black;
}
</style>
<script type="text/javascript">
function validateForm() {
    var bookName = document.forms["searchForm"]["bookname"].value;
    if (bookName == null || bookName.trim() == "") {
        alert("Book Name must be filled out.");
        return false;
    }
}
</script>
</head>
<body>
<form name="searchForm" action="SearchBook" target="resultFrame" onsubmit="return validateForm()">
<fieldset>
    <legend><b>Search by Book Name</b></legend>
    <table>
        <tr><td>Book Name:</td><td><input type="text" name="bookname"></td></tr>
        <tr><td><input type="submit" name="submit" value="Search"></td><td><input type="reset" value="Clear"></td></tr>
    </table>
</fieldset>
</form>
</body>
</html>
