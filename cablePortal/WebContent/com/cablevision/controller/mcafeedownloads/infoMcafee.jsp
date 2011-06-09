<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<link href="<%=request.getContextPath()%>/framework/skins/cablevision/css/ui.css" type="text/css" rel="stylesheet">

		<div class="login-in-thbox">
			<div class="wrap-img-thbk9">
				<a href="#" onclick="self.parent.tb_remove();" title="cerrar" class="close-cross" style="padding-left:0px;padding-top: 0px;width: 40px; height: 40px;" >
					<img src="${pageContext.request.contextPath }/images/close.png" alt="X" border="0">
				</a>
				<br>¡Felicidades! <br><br>
				Haz comenzado el proceso de descarga del<br>
				Centro de Seguridad de CABLEVISION(R)<br>
				powered by McAfee(r),
				en breve recibirás <br>un mail a tu cuenta de correo
				con tu usuario y <br>contraseña para continuar con<br>
				el proceso de descarga e instalación.<br><br><br>				
				<div style="padding-left: 80px;">
				<!-- <input type="button" onclick="window.location.assign('<%=request.getAttribute("urlmcafee") %>')" name="Descarga" value="Descarga" class="btn-small bs-2-lines"/>  -->
				<a title="Desc&aacute;rgalo ahora" href="<%=request.getSession().getAttribute("urlmcafee") %>" class="btn-small bs-2-lines">Descárgalo ahora</a><br><br>
				</div>
			</div>
		</div>
<%
	request.getSession(false).removeAttribute("urlmcafee");
%>