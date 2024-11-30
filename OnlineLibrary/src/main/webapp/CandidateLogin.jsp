<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@include file='Index.html' %>

<style>
form{
  
   width:400px;
  margin-left:500px;
  margin-top:100px;

}
legend{font-size:40px;}
</style>
<head>
<meta charset="UTF-8">
<title>Candidate Login</title>
<script>
function validateForm() {
    var username = document.forms["loginForm"]["uname"].value;
    var password = document.forms["loginForm"]["password"].value;
    if (username == "" || password == "") {
        alert("Both fields must be filled out");
        return false;
    }
    return true;
}
</script>
</head>
<body>


<form name="loginForm" action="CLoginvalidate" onsubmit="return validateForm()">
<fieldset>
<legend>Candidate Login</legend>
<table>
<tr><td>USERNAME:</td><td><input type="text" name="uname" id="uname"></td></tr>
<tr><td>PASSWORD:</td><td><input type="password" name="password" id="password"></td></tr>
<tr><td><input type="submit" value="Login"></td><td><input type="reset" value="Clear"></td></tr>

<tr><td><input type="button" value="Register" onclick="window.location.href='Register.jsp'"></td></tr>
</table>
</fieldset>
</form>


</body>
</html>