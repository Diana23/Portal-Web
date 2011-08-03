<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@page import="com.cablevision.util.Constantes"%>
<%@page import="com.cablevision.util.RespuestaToMyAccount"%>

<script src="${pageContext.request.contextPath}/com/cablevision/controller/ordenadministrativa/ordenAdmin.js" type="text/javascript"></script>

<jsp:scriptlet>
	RespuestaToMyAccount account = (RespuestaToMyAccount)request.getSession().getAttribute(Constantes.SESSION_MI_CUENTA);
	String nombreContacto = account.getNombreContacto() != null ? account.getNombreContacto() : "";
</jsp:scriptlet>


<netui:scriptContainer>
	<div class="color-orange link-user-servonline bord-toped bord-boted" title="BIENVENID@ <jsp:expression>account.getNombreContacto()</jsp:expression>" >
	BIENVENID@ <jsp:expression>nombreContacto</jsp:expression>
</div>	
	
	<div class="content-pago">
		<h3 class="side">Solicitud de Servicio</h3>
		
		<table width="530" cellspacing="0" cellpadding="5">
			<tr>
				<td align="right" width="40%">
					<c:if test="${!empty pageInput.errores}">
						<c:forEach var="error" items="${pageInput.errores}">
							<br/>
							<font class="error-msg">
								${pageInput.errores}
							</font>
						</c:forEach>
					</c:if>
					<c:if test="${empty pageInput.errores}">
						<br/>
						<font class="error-msg">
							<netui:errors bundleName="ordenAdminBundle" />
						</font>
					</c:if>
					<c:if test="${!empty pageInput.successMsgSolicitud}">
						<br/>
		        		<font class="msg-success">
		        			${pageInput.successMsgSolicitud}
		        		</font>
		        	</c:if>
		        	<c:if test="${!empty pageInput.msg}">
						<br/>
						<font class="error-msg">
		        			${pageInput.msg}
		        		</font>
		        	</c:if>
				</td>
			</tr>
		</table>
	
  		<netui:form action="guardarArchivo" tagId="form_guardarArchivo" styleClass="formulario" enctype="multipart/form-data">
	  		<table cellspacing="0" cellpadding="0" align="center">
	          	<tr>
	              	<td align="right" width="50%"><strong>Contrato: </strong></td>
	               	<td width="50%"><netui:label value="${actionForm.contrato}"/></td>
	            </tr>
	            <tr>
	             	<td colspan="2"></td>
	            </tr>
	            <tr>
	              	<td align="right"><strong>Nombre de la Cuenta: </strong></td>
	              	<td><netui:label value="${actionForm.nombreCuenta}"/></td>
	            </tr>
	            <tr>
	              	<td colspan="2"></td>
	            </tr>
	            <tr>
	              	<td align="right"><strong>Tipo de Problema: </strong></td>
	              	<td><netui:label value="${actionForm.tipoProblema}"/></td>
	            </tr>
	            <tr>
	              	<td colspan="2"></td>
	            </tr>
	            <tr>
	              	<td align="right"><strong>Subtipo de Problema: </strong></td>
	              	<td><netui:label value="${actionForm.subtipoProblema}"/></td>
	            </tr>
	            <tr>
	              	<td colspan="2"></td>
	            </tr>
	            <tr>
	              	<td align="right"><strong>Motivo de Problema: </strong></td>
	              	<td><netui:label value="${actionForm.motivoProblema}"/></td>
	            </tr>
	            <tr>
	              	<td colspan="2"></td>
	            </tr>
	            <tr>
	              	<td valign="top" align="right"><strong>Descripci&oacute;n: </strong></td>
	              	<td>
	              		<textarea class="ta-atencion-clientes" readonly="readonly"><c:out value="${actionForm.descripcion}"/></textarea>
		              	<!--<netui:label value="${actionForm.descripcion}"/>-->
			</td>
	            </tr>
	            <tr>
	              	<td colspan="2"></td>
	            </tr>
	            <tr>
	              	<td align="right"><strong>N&uacute;mero de Solicitud: </strong></td>
	              	<td><netui:label value="${actionForm.noSolicitud}"/></td>
	            </tr>
	            <tr>
	              	<td colspan="2"></td>
	            </tr>
	            <tr>
	              	<td align="right"><strong>Creado el: </strong></td>
	              	<td><netui:label value="${actionForm.creado}"/></td>
	            </tr>
	            <tr>
	              	<td colspan="2"></td>
	            </tr>
	            <tr>
	              	<td align="right"><strong>Estado: </strong></td>
	              	<td><netui:label value="${actionForm.estado}"/></td>
	            </tr>
	            <tr>
	              	<td colspan="2"></td>
	            </tr>
	        </table>
		
		<div align="center" style="width: 100%">
			<ul>
		  		<li>
		  			<label>Nombre del archivo adjunto (*)</label>
		  		</li>
		  		<li>
		  			<netui:fileUpload dataSource="actionForm.archivo" tagId="actionForm_archivo" styleClass="txt"/>
		  		</li>
		  		<li><span>(*) Campos obligatorios</span></li>
		  	</ul>
		</div>
	  	
		
		<div style="display:none;">
			<netui:rewriteName  name="form_guardarArchivo" forTagId="true" resultId="form_guardarArchivo"/>
		</div>
		
		<div class="margin-top15 marg-rig40">
			<netui:anchor formSubmit="true" action="guardarArchivo" styleClass="btn-small margin-bot15 fright" title="Guardar" value="Guardar">
	        	<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
	        	<netui:parameter name="_nfto" value="false"/>
	        </netui:anchor>
	        <netui:anchor action="mostrarDetalle" title="Cancelar" value="Cancelar" styleClass="btn-small  margin-bot15">
	  			<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
	  		</netui:anchor>
		</div>
  </netui:form>
  
 </div>
</netui:scriptContainer>




