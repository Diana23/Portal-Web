<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- 
<netui:form action="modificarPassword" tagId="formreg" styleClass="formulario">
	<div class="content-form margin-top10 marg-rig40">
		<span class="title-factura">
			Modificaci&oacute;n de mi Contrase&ntilde;a
		</span>
		<table width="100%">
			<tr>
				<td colspan="2">
					<netui:errors bundleName="micuentaBundle" />
					<c:if test="${!empty pageInput.errores}">
						<br/>
						<font class="error-msg">
		        			${pageInput.errores}
		        		</font>
		        	</c:if>
		        	<c:if test="${!empty pageInput.exito}">
						<br/>
		        		<font class="msg-success">
		        			${pageInput.exito}
		        		</font>
		        	</c:if>
		        	<c:if test="${!empty pageInput.msg}">
						<br/><br/>
						<font class="error-msg">
		        			${pageInput.msg}
		        		</font>
		        	</c:if>
				</td>
			</tr>
		</table>
		<ul class="pago-form">
			<li class="right-li">
				<span class="category">
					<label>Contrase&ntilde;a Actual (*)</label>
					<netui:textBox password="true" dataSource="actionForm.password" styleClass="big" size="50" maxlength="25"/>
				</span>
			</li>
			<li>
				<span class="category">
					<label>Nueva Contrase&ntilde;a (*)</label>
					<netui:textBox password="true" dataSource="actionForm.nuevoPassword" styleClass="big" size="50" maxlength="25"/>
				</span>
			</li>
			<li class="right-li">
				<span class="category">
					<label>Confirma Nueva Contrase&ntilde;a (*)</label>
					<netui:textBox password="true" dataSource="actionForm.passwordConfirmacion" styleClass="big" size="50" maxlength="25"/>
				</span>
			</li>
		</ul>
		<div class="clear"></div>
	</div>
	<div style="display:none;">
     <netui:rewriteName  name="formreg" forTagId="true" resultId="formreg"/>
    </div>
	<div class="margin-top15 marg-rig40">
		<netui:anchor  onClick="submitForma('${formreg}',this);return false;" formSubmit="true" action="modificarPassword" styleClass="btn-small margin-bot15 fright" title="Guardar" value="Guardar"/>
		<span>(*) Campos obligatorios</span>
		<div class="clear"></div>
	</div>
	
</netui:form> 