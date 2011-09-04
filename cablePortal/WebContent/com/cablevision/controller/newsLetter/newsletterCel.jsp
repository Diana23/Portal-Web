<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="jstl-c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@page import="com.cablevision.util.Constantes"%>
<%@page import="com.cablevision.util.RespuestaToMyAccount"%>

<jsp:scriptlet>
	RespuestaToMyAccount account = (RespuestaToMyAccount)request.getSession().getAttribute(Constantes.SESSION_MI_CUENTA);
	String nombreContacto = account.getNombreContacto() != null ? account.getNombreContacto() : "";
</jsp:scriptlet>




<div class="color-orange link-user-servonline bord-toped bord-boted" title="BIENVENID@ <jsp:expression>account.getNombreContacto()</jsp:expression>" >
	BIENVENID@ <jsp:expression>nombreContacto</jsp:expression>
</div>		

<div class="content-pago">
	<div class="content-form margin-top10 marg-rig40">
		<span class="title-factura">Subscripción a Newsletter</span>
		
		<table width="100%">
			<tr>
				<td colspan="2">
					<font class="error-msg">
						<netui:errors bundleName="newsletter"/>
					</font>
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
						<br/>
						<font class="error-msg">
		        			${pageInput.msg}
		        		</font>
		        	</c:if>
				</td>
			</tr>
		</table> 
		<netui:form action="newsletterUpdateEmail" tagId="formemail" styleClass="formulario">
			<ul class="pago-form">
				<li style="width:250px; line-height: 40px;">
					Desea Suscribirse al newsletter?
				</li>
				<netui:radioButtonGroup dataSource="actionForm.suscribir" style="background: none repeat scroll 0% 0% transparent; border: 0pt none;">
					<li style="width:50px; line-height: 40px; vertical-align: middle;">
						<netui:radioButtonOption value="SI" />
					</li>
					<li style="width:60px; line-height: 40px; vertical-align: middle;">
						<netui:radioButtonOption value="NO"/>
					</li>
				</netui:radioButtonGroup>
			</ul>
			<div class="clear"></div><br /><br />
			<div class="bord-toped"></div><br /><br />
			<ul class="pago-form">
				<li style="width:70px; line-height: 40px;">

					<label for="email">Email</label>
				</li>
				<li style="width:350px; line-height: 40px;">
					<netui:textBox dataSource="actionForm.email" styleClass="big" size="50" maxlength="50"/>
				</li>
				<li style="width:100px; line-height: 40px;">
					<div style="display:none;">
					 <netui:rewriteName  name="formemail" forTagId="true" resultId="formemail"/>
					</div>
					
					<netui:anchor formSubmit="true" onClick="submitForma('${formemail}',this);return false;" action="newsletterUpdateEmail" styleClass="btn-small margin-bot15 fright" title="Cambiar" value="Cambiar"/>
					
				</li>

			</ul>
			<div class="clear"></div>
		</netui:form>
		<c:if test="${!empty pageInput.categorias}">
			<netui:form action="newsletterUpdateCateg" tagId="formcateg" styleClass="formulario">
				<div class="clear"></div><br /><br />
				<div class="bord-toped"></div><br />
				<ul class="items-lista-newsletter">
					<li>
						Desea recibir información destacada sobre?
						<c:if test="${!empty pageInput.msgCat}">
							<div class="error_form_cv">Seleccione al menos una categoría.</div>
						</c:if>
						<div class="clear"></div><br /><br />
					</li>
					<li>
						<ul class="items-lista-newsletter_2">
							<netui:checkBoxGroup dataSource="actionForm.categoriasForm" >
								<c:forEach items="${pageInput.categorias}" var="cat">
									<li>
										<ul class="items-newsletter">
											<li>
												<strong><netui:label value="" />${cat.padreName}</strong>
											</li>
									  		<c:forEach items="${cat.hijos}" var="h">
									  			<li> 
										    		<netui:checkBoxOption value="${h.cnlId}" style="background:none; border: 0;">
										     			<label class="style9" style="vertical-align: middle">
										      				<c:out value="${h.cnlNombre}"/>
										     			</label>
										    		</netui:checkBoxOption>
										    	</li>
									   		</c:forEach>
										</ul>
									</li>
								</c:forEach>
							</netui:checkBoxGroup>
						</ul>
					</li>
					<li >
						<div class="clear"></div><br /><br />
						<div style="display:none;">
						 <netui:rewriteName  name="formcateg" forTagId="true" resultId="formcateg"/>
						</div>
						
						<netui:anchor formSubmit="true" onClick="submitForma('${formcateg}',this);return false;" action="newsletterUpdateCateg" styleClass="btn-small margin-bot15 fright" title="Cambiar" value="Cambiar"/>
					</li>
				</ul>
				<div class="clear"></div>
			</netui:form>
		</c:if>
	</div>
	<div class="clear"></div><br /><br />
</div>
