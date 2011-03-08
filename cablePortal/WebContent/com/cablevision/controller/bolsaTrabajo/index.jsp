<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<netui:scriptContainer>
<script type="text/javascript">
	var contextPath = "<%=request.getContextPath()%>";
</script>
	<div class="span-18 last">

				<h2 class="title-bolsa hidden-text">Bolsa de trabajo</h2>
				
				<h1 class="cable-no-img">¡Únete a nuestro equipo!</h1>
				
				<p>En CABLEVISION<sup>&reg;</sup> nos preocupamos por contar con profesionales que se identifiquen con valores de nuestra empresa y a su vez proporcionen a nuestros clientes la atención y servicios de calidad que nos caracterizan. Para participar en nuestro proceso de selección, sólo necesitas registrarte e ingresar tu CV. ¡Únete a nuestro Equipo!</p>
				<div class="span-9"><h3 class="side title-paperless">Si ya estas registrado Inicia sesión.</h3></div>
				<div align="right" style="line-height: 30px;" class="span-9">
					<c:if test="${!empty pageInput.errors}">
						<div class="error-msg" id="msgError"><br/><c:out value="${pageInput.errors}"/></div>
				        </c:if>
				        <netui:errors bundleName="bolsaTrabajoBundle" />
				</div>
				<div class="span-7 padding-full60" id="<netui:rewriteName name="login_stage" forTagId="true"  />" style="display:block">
					<netui:form action="iniciarSesion" tagId="formGuardar">
					
						<div class="text-side"><netui:label for="actionForm_email" value="Email:" /><netui:textBox dataSource="actionForm.email" tagId="actionForm_email" maxlength="250"></netui:textBox></div>
						<div class="text-side"><netui:label for="actionForm_password" value="Contraseña:" /><netui:textBox dataSource="actionForm.password" tagId="actionForm_password" password="true" maxlength="250"></netui:textBox></div>
					</netui:form>
				</div>
				
				<div style="display:none;">
					<netui:rewriteName  name="formGuardar" forTagId="true" resultId="formGuardar"/>
				</div>
				<div align="center" class="span-5 padding-full60 last">	
					<netui:anchor  onClick="submitForma('${formGuardar}',this);return false;" formSubmit="true" action="iniciarSesion" styleClass="btn-small margin-bot15 fright" title="ENTRAR" value="ENTRAR"/>					
				</div>
				<div class="clear"></div>
				<br>
				<br>
				<hr>
				<div align="center" class="span-6"><netui:anchor styleClass="btn-minimized" href="formularioRegistro.jsp">REGISTRO</netui:anchor></div>
				<div align="center" class="span-6"><netui:anchor styleClass="btn-minimized" action="verVacantes">VER VACANTES</netui:anchor></div>
				<div align="center" class="span-6"><netui:anchor styleClass="btn-minimized" action="actualizaCv">INGRESAR CV</netui:anchor></div>				
				<br><br><br>
				</div>				
				
				
				<div class="clear"></div>

			</div>		
</netui:scriptContainer>