<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%>

<%@page import="com.cablevision.util.Constantes"%>
<%@page import="com.cablevision.util.RespuestaToMyAccount"%>

<jsp:scriptlet>
	RespuestaToMyAccount account = (RespuestaToMyAccount)request.getSession().getAttribute(Constantes.SESSION_MI_CUENTA);
	String nombreContacto = account.getNombreContacto() != null ? account.getNombreContacto() : "";
</jsp:scriptlet>

 
<netui:scriptContainer>
	<div>
		<div class="color-orange link-user-servonline bord-toped bord-boted" title="BIENVENID@ <jsp:expression>account.getNombreContacto()</jsp:expression>" >
	BIENVENID@ <jsp:expression>nombreContacto</jsp:expression>
</div>	
		<h3 class="side title-paperless bord-boted">Centro de Atenci&oacute;n al Cliente</h3>
		
		<p class="padding-top10">Bienvenido(a) al Centro de Atenci&oacute;n al Clientes en línea de CABLEVISION&reg;. A continuación te presentamos los servicios que tenemos actualmente habilitados en este centro. Para levantar una Solicitud de Servicio solamente haz click en la liga que le corresponda.</p>
		<p>No dejes de visitar este centro de servicios a lo largo del tiempo. Todo con el fin de servirte mejor.</p>
		
		<h4 class="title-factura title-at bord-toped padding-top15">Dudas acerca de los pagos aplicados</h4>
		<ul class="paperless-list">
			<li class="ico-ofrece margin-bot10">
				<netui:anchor action="mostrarPagoNoAplicado" styleClass="title-paperless at-cliente" title="Pagos no aplicados o aplicados err&oacute;neamente">
					<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
					<strong>Pagos no aplicados o aplicados err&oacute;neamente</strong>
				</netui:anchor>
			</li>
			<li class="ico-ofrece margin-bot10">
				<netui:anchor action="mostrarErrorFactura" styleClass="title-paperless at-cliente" title="Error en factura">
					<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
					<strong>Error en factura</strong>
				</netui:anchor>
			</li>                              
		</ul>
		
		<h4 class="title-factura title-at margin-top15 bord-toped padding-top15">Quejas</h4>
		<ul class="paperless-list">
			<li class="ico-ofrece margin-bot10">
				<netui:anchor action="mostrarLevantarQueja" styleClass="title-paperless at-cliente" title="Levantar una queja">
					<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
					<strong>Levantar una queja</strong>
				</netui:anchor>
			</li>                             
		</ul>
		
	   <h4 class="title-factura title-at margin-top15 bord-toped padding-top15">Cambio de instrucci&oacute;n en el Cambio Recurrente</h4>
		<ul class="paperless-list">
			<li class="ico-ofrece margin-bot10">
				<netui:anchor action="mostrarPagoRecurrente" styleClass="title-paperless at-cliente" title="Cambio de la tarjeta de cr&eacute;dito que uso para el cargo recurrente"> 
					<netui:parameter name="tipoPago" value="cambio"/>
					<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
					<strong>Cambio de la tarjeta de cr&eacute;dito que uso para el cargo recurrente</strong>
				</netui:anchor>
			</li>
			<li class="ico-ofrece margin-bot30">
				<netui:anchor action="mostrarPagoRecurrente" styleClass="title-paperless at-cliente" title="Dar de alta mi tarjeta de cr&eacute;dito para pago recurrente"> 
					<netui:parameter name="tipoPago" value="alta"/>
					<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
					<strong>Dar de alta mi tarjeta de cr&eacute;dito para pago recurrente</strong>
				</netui:anchor>
			</li>
		</ul>
	</div>
	
</netui:scriptContainer>
