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
<a title="Paso 2" class="reg-pas2 hidden-text" style="cursor: default;" href="#">Paso 2</a>
<a title="Paso 3" class="paso3acti hidden-text" style="cursor: default;" href="#">Paso 3</a>
</div>
<div class="span-18 last limitheight">
<div class="wrap-cont-exp">

	
		<br><br><br>
		<h3 class="registrotitle hidden-text">Datos del suscriptor</h3>
		
		<netui:form action="pasoTres">
			<div class="error-msg" id="msgError">
				<c:if test="${!empty pageInput.errores}">
					<br/>
					${pageInput.errores}
				</c:if>
			</div>
			<c:if test="${empty pageInput.errores}">
				<netui:errors bundleName="registroBundle" />
			</c:if>	
			<div class="line-height-fix">
				<strong>Nombre: <span class="color-orange"><netui:label value="${actionForm.nombre} ${actionForm.apellidoPaterno} ${actionForm.apellidoMaterno}"/></span><br></strong>
				<strong>Correo electrónico: <span class="color-orange"><netui:label value="${actionForm.email}"/></span><br></strong>
				<strong>Número de contrato en CABLEVISION&reg;: <span class="color-orange"><netui:label value="${actionForm.noContrato}"/></span><br></strong>
				<strong>Teléfono de contacto: <span class="color-orange"><netui:label value="${actionForm.telefono}"/></span><br></strong>
				<netui:checkBox dataSource="actionForm.entiendo"/><span class="color-orange">Entiendo y acepto los </span>
				<a class="color-orange"
					onclick="window.open('${pageContext.request.contextPath}/contenido/groups/mercadotecnia/documents/html_cv/cv000574.html','','width=800,height=500,titlebar =0,toolbar=0,status=0,scrollbars=1,menubar=0,location=0,directories=0' ); return false;">
					términos y condiciones
				</a><br><br>
				<netui:anchor styleClass="btn-small marg-rig40 margin-bot15 fleft" formSubmit="true" title="Continuar">
					<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
					Continuar
				</netui:anchor> 
	        	<netui:anchor styleClass="btn-small marg-rig40 margin-bot15 fleft" action="pasoUno" title="Cancelar">
	        		<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
	        		Cancelar
	        	</netui:anchor> 
			</div>
			<br><br><br>
		
		
	</netui:form>
	
	
	</div>
  	<div class="beneficios2"></div>
  	</div>
</netui:scriptContainer>