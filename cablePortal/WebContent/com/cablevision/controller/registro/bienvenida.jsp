<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>Bienvenida</title>
</head>
<body>
<script type="text/javascript">
	var contextPath = "<%=request.getContextPath()%>";
</script>
	<c:if test="${empty pageInput.usuario}">
    <%
	    String redirectURL = "pasoUno.jsp";
	    response.sendRedirect(redirectURL);
	%>
    </c:if>
	<table width="533" border="1" cellpadding="0" cellspacing="0" align="center">
	  <form name="form1" method="post" action="">
	    <tr>
	      <td width="5">&nbsp;</td>
	      <td>&nbsp;</td>
	      <td width="10">&nbsp;</td>
	    </tr>
	    <tr>
	      <td>&nbsp;</td>
	      <td>Bienvenido(a) 
	      <c:if test="${!empty pageInput.usuario}">
	      		<b><label>${pageInput.usuario}</label></b>	
       	  </c:if>	    
	     </td>
	      <td>&nbsp;</td>
	    </tr>
	    <tr>
	      <td valign="top">&nbsp;</td>
	      <td valign="top">&nbsp;</td>
	      <td valign="top">&nbsp;</td>
	    </tr>
	    <tr>
	      <td valign="top">&nbsp;</td>
	      <td valign="top"><table width="519" border="0" cellspacing="5" cellpadding="0">
	          <tr>
	            <td>
	              <p>Centro de Atenci&oacute;n a Clientes </p>
	            </td>
	          </tr>
	          <tr>
	            <td><div align="left"></div></td>
	          </tr>
	          <tr>
	            <td><div align="left">
	              <p>Bienvenido (a) al Centro de Atenci&oacute;n a Clientes en L&iacute;nea de CABLEVISI&Oacute;N. A continuaci&oacute;n te presentamos los servicios que tenemos actualmente habilitados en este centro. Para levantar una Solicitud de Servicio solamente haz clic en la liga que le corresponda.</p>
	              <p>No dejes de visitar este centro de servicios a lo largo del tiempo. Todo con el fin de servirte mejor. </p>
	            </div></td>
	          </tr>
	      </table></td>
	      <td valign="top">&nbsp;</td>
	    </tr>
	    <tr>
	      <td valign="top">&nbsp;</td>
	      <td valign="top">&nbsp;</td>
	      <td valign="top">&nbsp;</td>
	    </tr>
	  </form>
	</table>
	<br>
	<table width="533" border="1" cellpadding="0" cellspacing="0" align="center">
	  <form name="form1" method="post" action="">
	    <tr>
	      <td width="5">&nbsp;</td>
	      <td width="519">&nbsp;</td>
	      <td width="10">&nbsp;</td>
	    </tr>
	    <tr>
	      <td valign="top">&nbsp;</td>
	      <td valign="top">&nbsp;</td>
	      <td valign="top">&nbsp;</td>
	    </tr>
	    <tr>
	      <td valign="top">&nbsp;</td>
	      <td valign="top"><table width="519" border="0" cellspacing="5" cellpadding="0">
	          <tr>
	            <td>Dudas acerca de los pagos aplicados</td>
	          </tr>
	          <tr>
	            <td><div align="left">* <a href="http://www.google.com">Pagos no aplicados o aplicados err&oacute;neamente </a></div></td>
	          </tr>
	          <tr>
	            <td><div align="left">* Error en factura </div></td>
	          </tr>
	          <tr>
	            <td>Tel&eacute;fono de contacto: </td>
	          </tr>
	          <tr>
	            <td>&nbsp;</td>
	          </tr>
	          <tr>
	            <td>Quejas</td>
	          </tr>
	          <tr>
	            <td>*Levantar una queja </td>
	          </tr>
	          <tr>
	            <td>&nbsp;</td>
	          </tr>
	          <tr>
	            <td>Cambio de Instrucci&oacute;n en el Cambio Recurrente </td>
	          </tr>
	          <tr>
	            <td>*Cambio de la tarjeta de cr&eacute;dito que uso para el cargo recurrente </td>
	          </tr>
	          <tr>
	            <td>* Dar de alta mi tarjeta de cr&eacute;dito para pago recurrente </td>
	          </tr>
	          <tr>
	            <td>&nbsp;</td>
	          </tr>
	          <tr>
	            <td><div align="right">
	            </div></td>
	          </tr>
	      </table></td>
	      <td valign="top">&nbsp;</td>
	    </tr>
	    <tr>
	      <td valign="top">&nbsp;</td>
	      <td valign="top">&nbsp;</td>
	      <td valign="top">&nbsp;</td>
	    </tr>
	  </form>
	</table>
</body>
</html>