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
	</div>
	
  <netui:form action="guardarComentario" tagId="form_Comentario" styleClass="formulario">
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
  	
  	<table width="100%" align="center" border="0" cellpadding="2" cellspacing="0">
  		<tr align="center">
  			<td>
		  		<label>Comentario (*)</label><span class="caracteres"> (100 caracteres como m&aacute;ximo)</span><br>
			</td>
  		</tr>
		<tr align="center">
		  	<td>
		  		<netui:textArea dataSource="actionForm.comentario" styleClass="txt" cols="35" rows="4" onkeyup="return textLimit(this, 100);" onKeyPress="return isAlphaNumeric(event);"/>
		  	</td>
		</tr>
	   	<tr>
		  	<td height="10"></td>
		</tr>
		<tr align="center">
		 	<td>
		 		<strong>Tipo de actividad: </strong>Actualización de web
		 	</td>
	   	</tr>
	   	<tr>
		  	<td height="10"></td>
		</tr>
		<tr align="center">
		  	<td><span>(*) Campos obligatorios</span></td>
		</tr>
	</table>
  	
  	<div style="display:none;">
		<netui:rewriteName  name="form_Comentario" forTagId="true" resultId="form_Comentario"/>
	</div>
	
	<div class="margin-top15 marg-rig40">
		<netui:anchor  onClick="submitForma('${form_Comentario}',this);return false;" formSubmit="true" action="guardarComentario" styleClass="btn-small margin-bot15 fright" title="Guardar" value="Guardar">
			<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
		</netui:anchor>
		<netui:anchor action="mostrarDetalle" title="Cancelar" value="Cancelar" styleClass="btn-small  margin-bot15">
  			<netui:parameter name="_pageLabel" value='<%= org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils.getOuterServletRequest(request).getParameter("_pageLabel") %>'/>
  		</netui:anchor>
	</div>
	
  </netui:form>
</netui:scriptContainer>