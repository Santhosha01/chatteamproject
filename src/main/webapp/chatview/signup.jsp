<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="s" uri="/struts-tags"%><%@ taglib prefix="tiles"
	uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>chatApp</title>
<style type="text/css">
body {
	display: flex;
	justify-content: center;
	margin: 73px;
}

div {
	align-content: space-around;
	background-color: #F2F2F3;
	width: 550px;
	height: 500px;
}

form {
	margin: 75px;
	margin-top: 45px;
}

label {
	display: block;
	margin-bottom: 8px;
	font-weight: bold;
}

/* Style input fields */
input[type="text"], input[type="password"],input[type="email"] {
	width: 100%;
	padding: 8px;
	margin-bottom: 12px;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
}

/* Style the submit button */
input[type="submit"] {
	width: 100%;
	margin-top: 13px;
	padding: 10px;
	background-color: #007bff;
	color: #fff;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	padding: 10px;
}

/* Optional: Add hover effect to the submit button */
input[type="submit"]:hover {
	background-color: #0056b3;
}

/* Style the link for sign-up */
h4 a {
	margin-left: 25px;
	text-decoration: none;
	color: #007bff;
}

/* Optional: Add hover effect to the sign-up link */
h4 a:hover {
	text-decoration: underline;
}

h3 {
	text-align: center;
	padding-top: 23px;
	color: red;
}
</style>
</head>
<body>
<div>
		<h3>
			<s:property value="error" />
		</h3>
		<form action="signup" method="post">
			<label for="name">Name</label> <input type="text" name="name"
				required> <label for="username">UserName</label> <input
				type="text" name="username" required> <label for="email">Email</label>
			<input type="email" name="email" required> <label
				for="password">Password</label> <input type="text" name="password"
				required> <label for="repassword">Re-enter password</label>
			<input type="text" name="repassword" required> <input
				type="submit" name="signup" value="signup"
				style="width: 100%; margin: 8px 0px;">
		</form>
	</div>
</body>
</html>