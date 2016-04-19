<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="common.Player" %>    
<%@ page import="common.Planet" %>   
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
	<p>用户Planet:<%=player.getMainPlanet().getID() %></p>
	<p>用户Planet名 :<%=player.getMainPlanet().getName() %></p>
	<p>经度:<%=player.getMainPlanet().getPosition().getLatitudes() %></p>
	<p>纬度:<%=player.getMainPlanet().getPosition().getLongitudes() %></p>
	<p>galsxy:<%=player.getMainPlanet().getPosition().getGalaxy() %></p>
	<p>Planet-Metal:<%=player.getMainPlanet().getResources().getMetal() %></p>
	<p>Planet-Fule:<%=player.getMainPlanet().getResources().getFule() %></p>
	<p>Planet-Feed:<%=player.getMainPlanet().getResources().getFeed() %></p>
	<p>Planet-Crystal:<%=player.getMainPlanet().getResources().getCrystal() %></p>
	<p>Planet-Power :<%=player.getMainPlanet().getResources().getPower() %></p>
	
</body>
</html>