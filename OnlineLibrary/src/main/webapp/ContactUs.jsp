<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<style>
footer
 {
 display:flex;
 justify-content:space-around;
  margin-top:50px;
  padding:15px;
   height:150px;
   font-size:20px;
   background-color:rgba(236, 240, 241 ,0.5);
   color:black;
   border-radius:7px;
   text-align:center;
    font-family:Poppins,sans-serif;
 }
 
 .address
 {
   margin-right:50px;
   margin-left:50px;
   color:black;
 }
 
 #email
 {
   margin-left:50px;
 }
</style>
<head>
<meta charset="UTF-8">
<%@include file='Index.html' %>
<title>About Us</title>
</head>
<body>
<footer>
          <div class="social">
  
           <h3><i class="fa-brands fa-instagram"> Instagram</i></h3>
           <h3><i class="fa-brands fa-facebook"> Facebook</i></h3>
           <h3><i class="fa-brands fa-twitter"> Twitter</i></h3>
  
         </div>
         
         <div class="address">
             <h3>Address: </h3> <p> Online Library ,Road No:1, <br> KPHB Colony,Kukatpally,Hyderabad <br> Pincode:500072</p>
         </div>
         
         <div>
               <h3>contact :</h3>
               <p> +91 7989400640 </p> 
               
         </div>
         
         <div id="email">
              <h3> Email : </h3>  <p> onlinelibrary060@gmail.com </p>
               
         </div>
  </footer>


</body>
</html>