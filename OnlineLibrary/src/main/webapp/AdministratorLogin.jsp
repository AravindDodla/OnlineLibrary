<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<!DOCTYPE html>
<html>
<style>
form{
  
   width:400px;
  margin-left:500px;
  margin-top:100px;

}

input{

    margin-left:10px;
    width:300px;
    height:30px;

}
legend{font-size:40px;}

.ar{
margin-left:90px;
width:100px;
}
fieldset{
border:3px solid black;
}

h3{color:#C7EE06}
</style>
<%@include file='Index.html' %>
<head>
<meta charset="UTF-8">
<title>Administrator</title>
</head>
<body>

<form  action= "AdminValidate" onsubmit="return validateForm()">
<fieldset>

<legend>Admin Login</legend>

<table>
<tr><td>User name:</td><td><input type="text" name="uname" id="uname"></td></tr>
<tr><td>Password:</td><td><input type="password" name="password" id="password"></td></tr>

</table>

<div>
<input type="submit" value="Login" class="ar">
<input type="reset" value="Clear" class="ar">
<input type="button" value="Register" class="ar" onclick="window.location.href='Register.jsp'">

</div>
</fieldset>

</form>


<script>
function validateForm() {
    var uname = document.getElementById('uname').value;
    var apassword = document.getElementById('apassword').value;
    
    if (uname === '' || apassword === '') {
        alert('Username and Password cannot be empty');
        return false;
    }
    
 
    }
    
    // If all checks pass, allow form submission
    return true;
}
</script>


</body>
</html>