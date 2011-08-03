<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<netui:scriptContainer>
<script type="text/javascript">
	var contextPath = "<%=request.getContextPath()%>";
</script>
	<div class="span-18 last">
		<div class="span-11 padding-rl20 ">


	<!-- form para que funcione bolsaTrabajo.js -->
		<netui:form action="actualizaCv" tagId="formTest"></netui:form>

       			<!-- InstanceBeginEditable name="titulo_seccion" -->       			
				<h2 class="title-bolsa hidden-text">Bolsa de trabajo</h2>
				<div align="right">
					<c:if test="${!empty pageInput.usuario}">
				    	<br><label style="display:block"><netui:anchor styleClass="color-orange padding-top40" action="cerrarSesion">Cerrar Sesion</netui:anchor></label>	
				    </c:if>
				</div>
       			<!-- InstanceEndEditable -->
       			<div class="clear"><netui:anchor styleClass="btn-minimized" action="actualizaCv">INGRESAR CV</netui:anchor></div><br>
       			<div>
				 	<c:if test="${!empty pageInput.userRH}">					 			
						<netui:anchor action="verAgregarVacante" styleClass="color-orange padding-top40" >Agregar Vacante</netui:anchor>												
					</c:if>
				</div>
       				<div>
       					<div class="error-msg" id="msgError">
						<c:if test="${!empty pageInput.errors}">
								<br/>
								<c:out value="${pageInput.errors}"/>
				        	</c:if>
				        	<netui:errors bundleName="bolsaTrabajoBundle" />
					</div>
       				</div>       			
       			
       			<ul id="cotiz-content" class="cotiz-content">
					<c:forEach var="vacante" items="${requestScope.vacantes}">
		           		<tr>
		           		 <td width="10%" valign="top" align="center">
		           		 	<c:if test="${!empty pageInput.userRH}">
			                   	<netui:anchor action="verAgregarVacante"><img border="0" src="${contextPath }/contentserver/groups/mercadotecnia/documents/imagen_cv/cv002352.png"/><netui:parameter name="idVacante" value="${vacante.idVacante}" /></netui:anchor>
			                   	<netui:anchor action="borrarVacante"><img border="0" src="${contextPath}/contentserver/groups/mercadotecnia/documents/imagen_cv/cv002349.png"/><netui:parameter name="idVacante" value="${vacante.idVacante}" /></netui:anchor>
		                 	</c:if>
		                 </td>
		                 <li class="wrap-paq-cot">
								<div class="height-fix">
									<h3 class="job-title">	<c:out value="${vacante.titulo}"/></h3>
									
									<p class="job-desc">
										<c:out value="${vacante.descripcion}"/><br>
									</p>
		                 		</div>
		                 		<div class="precio-bordered">
		                 		<input type="hidden" value="${vacante.idVacante}" id="<netui:rewriteName name="idVacante" forTagId="true"/>" />		                
			                 	<div id="vac_post" style="display:{vac_post}">
			                 		<netui:anchor styleClass="sre-job-mail" title="Enviar por mail" action="enviarVacantePorMail">			                 		                   			
			                   			<netui:parameter name="idVacante" value="${vacante.idVacante}" />
			                   		</netui:anchor><span class="legend-mail">Enviar por mail</span> 
			                   		
			                 		<netui:anchor styleClass="btn-minimized fright" title="Postularme" value= "Postularme" action="postularse">			                 		                   			
			                   			<netui:parameter name="idVacante" value="${vacante.idVacante}" />
			                   		</netui:anchor>
			                   		<c:if test="${ pageInput.mensajeOk eq vacante.idVacante }">
			                   			<font color="green" size="2">Postulado</font>
			                   		</c:if>
			                   		<c:if test="${ pageInput.mailOk eq vacante.idVacante }">
			                   			<font color="green" size="2">Vacante Enviada</font>
			                   		</c:if>
			                   		<div id="<netui:rewriteName name="respuesta" forTagId="true"/>"> </div>
			                   	</div>	
			                   	<div class="clear"></div>
			                   	</div>						
						</li>
						
		        	</c:forEach>							
				</ul>   
				<a class="color-orange padding-top40 fright" title="subir" href="#cotiz-content">^ Subir</a>		
				<div class="span-6"><netui:anchor styleClass="btn-minimized" action="actualizaCv">INGRESAR CV</netui:anchor></div>		             
		</div>
	</div>
	
</netui:scriptContainer>