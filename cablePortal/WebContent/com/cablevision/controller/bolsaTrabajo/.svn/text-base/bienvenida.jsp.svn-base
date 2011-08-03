<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<netui:scriptContainer>
	<br>
	<script>
		var contextPath = "<%=request.getContextPath()%>";
	</script>
	<div class="span-18 last">
	<h2 class="title-bolsa hidden-text">Bolsa de trabajo</h2>
				<div align="right">
					<c:if test="${!empty pageInput.usuario}">
				    	<br><label style="display:block"><netui:anchor styleClass="color-orange padding-top40" action="cerrarSesion">Cerrar Sesion</netui:anchor></label>	
				    </c:if>
				</div>
		<table width="555" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
				<div style="padding-left:20px; display:${pageInput.style}">
					<p>En CABLEVISION<sup>&reg;</sup> nos preocupamos por contar con profesionales que se identifiquen con valores de nuestra empresa y a su vez proporcionen a nuestros clientes la atención y servicios de calidad que nos caracterizan. Para participar en nuestro proceso de selección, sólo necesitas registrarte e ingresar tu CV. ¡Únete a nuestro Equipo!</p>
				</div>
				</td>
			</tr>	
			<tr>
				<td><div id="login_stage" style="display:none">
					<table width="100%">
						<tr>
							<td colspan="2">&nbsp;<input type="hidden" name="login" value="ok"></td>
						</tr>
						<tr>
							<td style="padding-left:20px">Email:<input type="text" name="email" value="danny_gonz@hotmail.com"></td>
							<td>Si ya estas registrado Inicia sesión.</td>
						</tr>
						<tr>
							<td align="right">Contraseña:<input type="password" name="cvpass"></td>
							<td align="center"><input type="image" src="${pageContext.request.contextPath}/contenido/groups/mercadotecnia/documents/imagen_cv/cv000210.gif"></td>				
						</tr>
						<tr>
							<td><div class="error-msg" id="msgError"></div></td>
						</tr>
					</table>
					</div>
				</td>
			  </tr>                          
			  <tr>
				<td>&nbsp;</td>
			  </tr>	  				  
		</table>
		<div align="center" class="span-6"><netui:anchor styleClass="btn-minimized" action="verVacantes">VER VACANTES</netui:anchor></div>
			<div align="center" class="span-6"><netui:anchor styleClass="btn-minimized" action="actualizaCv">ACTUALIZA CV</netui:anchor></div>
	</div>
</netui:scriptContainer>