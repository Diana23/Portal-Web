<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript" src="registro.js"></script>
<netui:scriptContainer>
<script type="text/javascript">
	var contextPath = "<%=request.getContextPath()%>";
</script>

<div class="span-6"> 
<a title="Paso 1" class="reg-pas1 hidden-text" style="cursor: default;" href="#">Paso 1</a>
<a title="Paso 2" class="paso2act hidden-text" style="cursor: default;" href="#">Paso 2</a>
<a title="Paso 3" class="reg-pas3 hidden-text" style="cursor: default;" href="#">Paso 3</a>
</div>
<div class="span-18 last limitheight">
<div class="wrap-cont-exp">


<br><br><br>
<h3 class="registrotitle hidden-text">Datos del suscriptor</h3>

<c:if test="${!empty pageInput.errores}">
	${pageInput.errores}
</c:if>
<c:if test="${empty pageInput.errores}">
	<netui:errors bundleName="registroBundle" />
</c:if>	
<netui:form action="pasoDos" styleClass="formulario">
	<table width="50%" cellspacing="0" cellpadding="0" border="0" align="center">
		<tbody><tr>
			<td width="50%" colspan="2">
				<netui:label for="actionForm_password" value="* Contraseña" /><br>
				<netui:textBox dataSource="actionForm.password" tagId="actionForm_password" styleClass="bigR" size="25" password="true"/>						
				<p class="estilizado">25 Caracteres alfanuméricos máximo </p>
			</td>
			<td width="50%" colspan="2">
				<netui:label for="actionForm_passwordConfirmacion" value="* Confirmar contraseña"/><br>
				<netui:textBox dataSource="actionForm.passwordConfirmacion" tagId="actionForm_passwordConfirmacion" styleClass="bigR" size="25" password="true"/>
				<p class="estilizado">&nbsp;</p>
			</td>
		</tr>
		<tr>
			<td colspan="4">      
				<netui:label for="actionForm_preguntaConfirmacion" value="* Pregunta de confirmación"/><br>
				<netui:select dataSource="actionForm.preguntaConfirmacion" tagId="actionForm_preguntaConfirmacion" styleClass="bigest" >
					<netui:selectOption value="">...</netui:selectOption>
					<netui:selectOption value="¿COMO SE LLAMA TU MASCOTA?">¿C&oacute;mo se llama tu mascota?</netui:selectOption> 
					<netui:selectOption value="¿CUAL ES TU EQUIPO FAVORITO?">¿Cu&aacute;l es tu equipo favorito?</netui:selectOption>
					<netui:selectOption value="¿CUAL ES EL NOMBRE DE TU MEJOR AMIGO?">¿Cu&aacute;l es el nombre de tu mejor amigo?</netui:selectOption>
					<netui:selectOption value="¿DONDE CONOCISTE A TU PAREJA?">¿D&oacute;nde conociste a tu pareja?</netui:selectOption>
				</netui:select>
				<p class="estilizado">(que se debe usar cuando se olvida la contraseña) </p>
			</td>
		</tr>
		<tr>
			<td colspan="4">      
				<netui:label for="actionForm_respuestaConfirmacion" value="* Respuesta a la pregunta"/><br>
				<netui:textBox dataSource="actionForm.respuestaConfirmacion" tagId="actionForm_respuestaConfirmacion" styleClass="bigfix" onKeyPress="return isAlpha(event);"/>
			</td>
		</tr>
	</tbody></table>
	<div style="display:none;">
		<netui:rewriteName  name="formSiguiente2" forTagId="true" resultId="formSiguiente2"/>
	</div>		
	<netui:anchor onClick="submitForma('${formSiguiente2}',this);return false;" action="pasoDos" formSubmit="true" title="Siguiente" styleClass="btn-small marg-rig40 margin-bot15 fleft">
		<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
		Siguiente
	</netui:anchor>
	<netui:anchor action="begin" title="Atrás" styleClass="btn-small marg-rig40 margin-bot15 fleft">
		<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
		Atrás
	</netui:anchor>
	<br><br><br>
  </netui:form>
  
  
  
  </div>
  <div class="beneficios2"></div>
  </div>
</netui:scriptContainer>