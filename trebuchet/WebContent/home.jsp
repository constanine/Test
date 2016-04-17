<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="common.Player" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Trebuchet</title>
<% Player player = (Player)session.getAttribute("player");%>
</head>
<body>
	<p>This is Trebuchet</p>
	<p>用户:<%=player.getName() %></p>
	<p>用户ID:<%=player.getID() %></p>
	<p>用户Planet:<%=player.getMainPlanet() %></p>
	
</body>
</html>