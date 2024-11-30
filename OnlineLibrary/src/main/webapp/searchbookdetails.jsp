<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@include file='Index.html' %>
<style>
#ar{
width:500px;
margin-left:35%;
margin-top:10%;
font-family:Copperplate;

}
fieldset{
border:3px solid black;
}
legend{color:#58d68d}
legend{font-size:25px}

</style>
<head>
<meta charset="UTF-8">
<title>Search book details</title>
</head>
<body>
<div id="ar">
<form name="adminbooksearch" action="SearchBook">
<fieldset>
<legend><b>Search Book Here:</b></legend>
<table>
<tr><td><b>Search by Book name:</b></td>
<td><input type="text" name="bookname" placeholder="enter book name here"></td></tr>
<tr><td><b>Search by Author:</b></td>
<td><input type="text" name="authorname" placeholder="enter author name here"></td></tr>
<tr><td><input type="submit" name="submit" value="search"></td><td><input type="reset" name="clear" value="clear"></td></tr>
</table>
</fieldset>
</form>
</div>
</body>
</html>