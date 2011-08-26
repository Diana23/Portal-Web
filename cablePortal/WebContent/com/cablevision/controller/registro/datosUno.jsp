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
<a title="Paso 1" class="paso1acti hidden-text" style="cursor: default;" href="#">Paso 1</a>
<a title="Paso 2" class="reg-pas2 hidden-text" style="cursor: default;" href="#">Paso 2</a>
<a title="Paso 3" class="reg-pas3 hidden-text" style="cursor: default;" href="#">Paso 3</a>
</div>
<div class="span-18 last limitheight">
<div class="wrap-cont-exp">


	<img style="margin-top:15px;" border="0" alt="NoPagos" src="/contentserver/groups/mercadotecnia/documents/imagen_cv/cv006248.jpg">
	<h3 class="registrotitle hidden-text">Datos del suscriptor</h3>
	<div class="error-msg" id="msgError">
		<c:if test="${!empty pageInput.errores}">
		${pageInput.errores}
		</c:if>
		<c:if test="${empty pageInput.errores}">
		     <netui:errors bundleName="registroBundle" />
		</c:if>
	</div>
	
	
	<netui:form action="pasoUno" styleClass="formulario" tagId="formSiguiente1">
		<table width="50%" cellspacing="0" cellpadding="0" border="0" >
			<tbody><tr>
				<td colspan="2">
					<netui:label for="actionForm_nombre" value="* Nombre" /><br>
					<netui:textBox dataSource="actionForm.nombre" tagId="actionForm_nombre" styleClass="bigR" onKeyPress="return isAlpha(event);"/>
				</td>
				<td width="50%" colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td width="50%" colspan="2">
					<netui:label for="actionForm_apellidoPaterno" value="* Apellido Paterno" /><br>
					<netui:textBox dataSource="actionForm.apellidoPaterno" tagId="actionForm_apellidoPaterno" styleClass="bigR" onKeyPress="return isAlpha(event);"/>	
				</td>
				<td width="50%" colspan="2">
					<netui:label for="actionForm_apellidoMaterno" value="* Apellido Materno" /><br>
					<netui:textBox dataSource="actionForm.apellidoMaterno" tagId="actionForm_apellidoMaterno" styleClass="bigR" onKeyPress="return isAlpha(event);"/>
				</td>
			</tr>
			<tr>
				<td colspan="2">      
					<netui:label for="actionForm_email" value="* Correo electrónico" /><br>
					<netui:textBox dataSource="actionForm.email" tagId="actionForm_email" styleClass="bigR"/>
				</td>
				<td width="50%" colspan="2">      
					<netui:label for="actionForm_emailConfirmacion" value="* Confirma tú correo electrónico" /><br>
					<netui:textBox dataSource="actionForm.emailConfirmacion" tagId="actionForm_emailConfirmacion" styleClass="bigR" />
				</td>
			</tr>
			<tr>
				<td colspan="2">      
					<netui:label for="actionForm_idUsuario" value="* Usuario" /><br>
					<p class="estilizado">Nombre con el que ingresaras a Mi cuenta CABLEVISION<sup>&reg;</sup></p>
					<netui:textBox dataSource="actionForm.idUsuario" tagId="actionForm_idUsuario" styleClass="bigR" onKeyPress="return isAlphaNumericDash(event);"/>
				</td>
				<td width="50%" colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td valign="top" colspan="2">
					<label for="${actionForm_noContrato}">* Número de contrato CABLEVISION<sup>&reg;</sup></label><br>
					<p class="estilizado">Primeros 8 números del contrato </p>
					<netui:textBox dataSource="actionForm.noContrato" styleClass="bigR" tagId="actionForm_noContrato" onKeyPress="return isNumeric(event);"/><br>
					<div style="display:none;">
						<netui:rewriteName  name="actionForm_noContrato" forTagId="true" resultId="actionForm_noContrato"/>
					</div>
					<a title="¿Cuál es mi número de contrato?" class="color-orange smaller" href="#" onclick="window.open('/contentserver/groups/mercadotecnia/documents/imagen_cv/cv000571.jpg','','width=570,height=420,titlebar =0,toolbar=0,status=0,scrollbars=0,menubar=0,location=0,directories=0' ); return false;">¿Cuál es mi número de contrato?</a>
				</td>
				<td width="50%" valign="top" colspan="2">     
					<netui:label for="actionForm_telefono" value="* Teléfono de contacto" /><br>
					<p class="estilizado">Sin clave LADA, 8 digitos solamente.</p> 
					<netui:textBox dataSource="actionForm.telefono" tagId="actionForm_telefono" styleClass="bigR" onKeyPress="return isNumeric(event);"/>								
				</td>
			</tr>
		</tbody>
		</table>		
		
		<div style="display:none;">
			<netui:rewriteName  name="formSiguiente1" forTagId="true" resultId="formSiguiente1"/>
		</div>
		<netui:anchor onClick="submitForma('${formSiguiente1}',this);return false;" formSubmit="true" action="pasoUno" styleClass="btn-small margin-bot15" title="Siguiente">

			<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
			Siguiente
		</netui:anchor>
		
  </netui:form>
  
	
  <br><br><br>
  	</div>
  	<div class="beneficios"></div>
	</div>
  
</netui:scriptContainer>

