<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><% request.getParameter("titulo"); %></title>
<style>
	.color-orangePopup { color:#eb6500!important; font-size: 23px!important;}
	.linkk{color:#eb6500!important; font-size: 10px; float:right; margin:18px 0 18px 0;}
	.linkk a{color:#eb6500}
	.grid-content{margin:7px 0 7px 10px;}
	.boxd{display:block; width:600px; height:32px; margin:5px 0 25px 0; }
	.blacker{color:#2a2a2a;}
</style>
</head>
<body>
	<a title="cerrar" onclick="self.parent.tb_remove();" href="#">
		<img border="0" style="float: right;" alt="X" src="<%out.print(request.getContextPath());%>/resources/images/close2.png"/>
	</a>
	<h3 class="color-orangePopup grid-content"><% out.print(request.getParameter("titulo")); %></h3>
	<div align="center">
		<object width="500" height="300" style="margin-left: 5px;">
			<param value="<% out.print(request.getParameter("video")); %>" name="movie"/>
			<param value="true" name="allowFullScreen"/>
			<param value="always" name="allowscriptaccess"/>
			<embed 
				width="500" 
				height="300" 
				allowfullscreen="true" 
				allowscriptaccess="always" 
				type="application/x-shockwave-flash" 
				src="<% out.print(request.getParameter("video")); %>"/>
		</object>
	</div>
</body>
</html>