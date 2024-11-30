<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@include file='Index.html' %>
<head>
    <script>
        function validateForm() {
            var staffId = document.forms["staffForm"]["uid"].value;
            var staffName = document.forms["staffForm"]["uname"].value;
            var password = document.forms["staffForm"]["pass"].value;
            var rePassword = document.forms["staffForm"]["rpass"].value;
            var dob = document.forms["staffForm"]["DOB"].value;
            var email = document.forms["staffForm"]["email"].value;
            var phone = document.forms["staffForm"]["Pnumber"].value;
            var address = document.forms["staffForm"]["Address"].value;
            var designation = document.forms["staffForm"]["designation"].value;

            // Check if any field is empty
            if (staffId == "" || staffName == "" || password == "" || rePassword == "" || dob == "" || email == "" || phone == "" || address == "" || designation == "") {
                alert("All fields must be filled out");
                return false;
            }

            // Check if passwords match
            if (password !== rePassword) {
                alert("Passwords do not match");
                return false;
            }

            // Password complexity validation
            var passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
            if (!passwordRegex.test(password)) {
                alert("Password must contain at least one lowercase letter, one uppercase letter, one numeric digit, one special character, and be at least 8 characters long");
                return false;
            }

            // Email validation
            var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(email) || !email.includes("@gmail.com")) {
                alert("Email must be a valid email address and include '@gmail.com'");
                return false;
            }

            // If all validations pass, return true to allow form submission
            return true;
        }

        function togglePasswordInfo() {
            var info = document.getElementById("passwordInfo");
            if (info.style.display === "none" || info.style.display === "") {
                info.style.display = "block";
            } else {
                info.style.display = "none";
            }
        }
    </script>

    <style>
        form {
            width: 400px;
            margin-left: 500px;
            margin-top: 100px;
            
        }

        .info-icon {
            cursor: pointer;
            margin-left: 10px;
            color: blue;
            font-size: 18px;
        }

        .password-info {
            display: none;
            margin-top: 10px;
            background-color: #f9f9f9;
            border: 1px solid #ccc;
            border-radius: 5px;
            padding: 10px;
        }

        .password-info ul {
            list-style-type: none;
            padding-left: 0;
        }

        .password-info li {
            margin-bottom: 5px;
        }
    </style>
    <meta charset="UTF-8">
    <title>Register New User</title>
</head>
<body>
    <form name="staffForm" onsubmit="return validateForm()" action="CLogin">
        <fieldset>
            <legend>REGISTER NEW USER</legend>
            <table>
                <tr>
                    <td>USER ID:</td>
                    <td><input type="text" name="uid" id="uid" placeholder="Enter User ID"></td>
                </tr>
                <tr>
                    <td>USER NAME:</td>
                    <td><input type="text" name="uname" id="uname" placeholder="Enter User Name"></td>
                </tr>
                <tr>
                    <td>PASSWORD:</td>
                    <td>
                        <input type="password" name="pass" id="pass" placeholder="Enter Your Password">
                        <span class="info-icon" onclick="togglePasswordInfo()">&#9432;</span>
                    </td>
                </tr>
                <tr>
                    <td>RE-ENTER PASSWORD:</td>
                    <td><input type="password" name="rpass" id="rpass" placeholder="Re-enter Your Password"></td>
                </tr>
                <tr>
                    <td>DOB:</td>
                    <td><input type="date" name="DOB" id="DOB" placeholder="Enter Your DOB"></td>
                </tr>
                <tr>
                    <td>EMAIL:</td>
                    <td><input type="text" name="email" id="email" placeholder="Enter Your Email"></td>
                </tr>
                <tr>
                    <td>GENDER:</td>
                    <td>
                        <input type="radio" name="GEN" id="GEN_m" value="m"> M
                        <input type="radio" name="GEN" id="GEN_f" value="f"> F
                    </td>
                </tr>
                <tr>
                    <td>PHONE:</td>
                    <td><input type="tel" name="Pnumber" id="Pnumber" placeholder="Enter Your Phone Number"></td>
                </tr>
                <tr>
                    <td>ADDRESS:</td>
                    <td><textarea name="Address" id="Address" cols="33" rows="2"></textarea></td>
                </tr>
                <tr>
                    <td>SELECT DESIGNATION:</td>
                    <td>
                        <select name="designation" id="designation">
                            <option value="">-- Select Designation --</option>
                            <option value="Librarian">Librarian</option>
                            <option value="Assistant Librarian">Assistant Librarian</option>
                            <option value="Staff">Staff</option>
                            <option value="Lecturer">Lecturer</option>
                            <option value="Student">Student</option>
                        </select>
                    </td>
                </tr>
            </table>
            <input type="reset" value="Clear">
            <input type="submit" value="Submit">
        </fieldset>
        <div id="passwordInfo" class="password-info">
            <ul>
                <li>At least 8 characters</li>
                <li>At least one uppercase letter</li>
                <li>At least one lowercase letter</li>
                <li>At least one number</li>
                <li>At least one special character</li>
            </ul>
        </div>
    </form>
</body>
</html>


