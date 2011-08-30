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
<!-- 
<netui:scriptContainer>
<div class="color-orange link-user-servonline bord-toped bord-boted" title="BIENVENID@ <jsp:expression>account.getNombreContacto()</jsp:expression>" >
	BIENVENID@ <jsp:expression>nombreContacto</jsp:expression>
</div>	
<netui:form action="mostrarRegistro">
	<netui-data:declareBundle bundlePath="com.cablevision.controller.paperless.paperless" name="paperlessBundle"/>
		<h3>Suscr&iacute;bete a Paperless</h3>
		<p class="padding-top10">
			<c:if test="${pageInput.paperlessBean.paperless eq false}">
				${bundle.paperlessBundle['texto.index.parrafo1.alta']}
			</c:if>
			<c:if test="${pageInput.paperlessBean.paperless eq true}">
				${bundle.paperlessBundle['texto.index.parrafo1.modificacion']}
			</c:if> 
		</p>
		<h4 class="title-factura">"Paperless" te ofrece</h4>
		<ul class="paperless-list">
		   	<li class="ico-ofrece">
		       	<span class="title-paperless"><strong>Mayor Privacidad</strong></span>
		           <p>Cada Estado de Cuenta podr&aacute; ser revisado las veces que deseas con la seguridad de tu cuenta de Servicios en L&iacute;nea de CABLEVISION&reg;</p>
			</li>
		       <li class="ico-ofrece">
		       	<span class="title-paperless"><strong>Mayor eficiencia y rapidez</strong></span>
		           <p>Recibe puntualmente un recordatorio para consultar tu Estado de Cuenta en l&iacute;nea, arch&iacute;valos electr&oacute;nicamente y ahorra papel, espacio y tiempo para buscar la informaci&oacute;n</p>
			</li>
		</ul>
		
		<c:if test="${pageInput.paperlessBean.paperless eq false}">
			<p>	
				<netui:anchor action="mostrarRegistro" styleClass="btn-small bs-2-lines fright" title="Reg&iacute;strese">
					Reg&iacute;strate
					<netui:parameter name="estado" value="alta"/>
				</netui:anchor>
				<a class="btn-small bs-2-lines" title="Modificar<br> E-mail" href="#" style="visibility: hidden;"></a>
			</p>
		</c:if>
		
		<c:if test="${pageInput.paperlessBean.paperless eq true}">
			<p class="bord-toped padding-top10">
				<span class="fright"><strong>Eres miembro desde:</strong> ${pageInput.paperlessBean.fechaInscripcion}</span>
				<span class="title-paperless"><strong>El correo electr&oacute;nico que tenemos registrado es:</strong> </span><span>${pageInput.paperlessBean.email}</span>
			</p>
			<p>	
				<netui:anchor action="mostrarRegistro" styleClass="btn-small bs-2-lines fright" title="Cancelar Paperles">
					Cancelar Paperles
					<netui:parameter name="estado" value="baja"/>
				</netui:anchor>
				<netui:anchor action="mostrarRegistro" styleClass="btn-small bs-2-lines" title="Modificar<br> E-mail">
					Modificar<br> E-mail
					<netui:parameter name="estado" value="cambio"/>
				</netui:anchor>
			</p>
		</c:if>
		<p class="bord-toped padding-top10">* Una vez inscrito, si desearas volver a recibir tu Estado de Cuenta en papel, puedes actualizar tus preferencias en nuestro sitio web o comunic&aacute;ndote al Centro de Servicio al Cliente.</p>
</netui:form>
</netui:scriptContainer>
 -->