<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<font color = "red">${msg}</font>
<form action="sendSmsByJson" method="post">
Mobile Number : <input type="text" name="mobile"/> </br>
Text : <textarea rows="10" cols="20" name="text"></textarea></br>
<input type="submit" value="Send Sms"/>
</form>
</body>
</html>