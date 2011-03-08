<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@page import="com.cablevision.util.Constantes"%>
<%@page import="com.cablevision.util.RespuestaToMyAccount"%>

<jsp:scriptlet>
	RespuestaToMyAccount account = (RespuestaToMyAccount)request.getSession().getAttribute(Constantes.SESSION_MI_CUENTA);
	String nombreContacto = account.getNombreContacto() != null ? account.getNombreContacto() : "";
	String nombreCompleto = nombreContacto + " " + account.getApellidoPaterno();
</jsp:scriptlet>

<netui:scriptContainer>
<div class="color-orange link-user-servonline bord-toped bord-boted" title="BIENVENID@ <jsp:expression>account.getNombreContacto()</jsp:expression>" >
	BIENVENID@ <jsp:expression>nombreContacto</jsp:expression>
</div>	
<netui:form action="mostrarConfirmacion" tagId="form" styleClass="formulario">
	<div style="display:none;">
		<netui:rewriteName  name="form" forTagId="true" resultId="form"/>
	</div>
	<netui-data:declareBundle bundlePath="com.cablevision.controller.paperless.paperless" name="paperlessBundle"/>
		<br>
		<h3>${bundle.paperlessBundle['texto.condiciones.titulo']}</h3>
		
		<div class="error-msg" id="msgError">
			<c:if test="${!empty pageInput.errores}">
				<br/>
				${pageInput.errores}
			</c:if>
		</div>
		<c:if test="${empty pageInput.errores}">
			<br/>
			<netui:errors bundleName="paperlessBundle" />
		</c:if>	
		
		<p class="padding-top10">
			
		</p>
		<h4 class="title-factura">Convenio de aceptaci&oacute;n de condiciones para el servicio Paperless</h4>
		<textarea rows="10" cols="55" readonly="readonly" class="big">${bundle.paperlessBundle['texto.condiciones.parrafo1']}</textarea>
		
		<ul class="mail">
	       	<li>
				<label class="suscribe">
					<netui:checkBox dataSource="actionForm.check" styleClass="check"/>
					Acepto las condiciones
				</label>
			</li>
		</ul>
		<br/><br/><br/>
		<div class="clear"></div>
		
		<p>	
			<netui:anchor action="begin" title="Cancelar" styleClass="btn btn-small margin-bot40 fright">Cancelar</netui:anchor>
			<netui:anchor action="mostrarConfirmacion" onClick="submitForma('${form}',this);return false;" styleClass="btn-small fleft" title="Continuar">Continuar</netui:anchor>
		</p>
</netui:form>
</netui:scriptContainer>
