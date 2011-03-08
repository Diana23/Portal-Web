<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<netui:scriptContainer>
<script type="text/javascript">
	var contextPath = "<%=request.getContextPath()%>";
</script>
   
<div class="span-18 last">	
  
  	
   	<h2 class="title-bolsa hidden-text">Bolsa de trabajo</h2>
   	   <div align="right">
	    	<br><label style="display:block"><netui:anchor styleClass="color-orange padding-top40" action="cerrarSesion">Cerrar Sesion</netui:anchor></label>	
		</div>
	<netui:form action="guardarVacante" tagId="actionForm_vacantes" styleClass="formulario"> 			
    <table width="556" border="0" cellspacing="0" cellpadding="0">		
		<tr>
			<td align="center">
				<table>
					<tr>
						<td width="50%">&nbsp;</td>
						<td align="right">
							<div class="error-msg" id="msgError">
								<c:if test="${!empty pageInput.errors}">
									<c:out value="${pageInput.errors}"/>
				        			</c:if>
								<netui:errors bundleName="bolsaTrabajoBundle" />
							</div>
						</td>
						<td width="10%">&nbsp;</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td >&nbsp;</td>
		</tr> 
		<tr>
        	<td align="center">
        	        
        	        <div class="span-17">
        	        <table width="700" cellspacing="0" cellpadding="0" border="0" align="center">
								<tbody>
								<tr>
									<td width="20%" height="25"><netui:label for="actionForm_idVacante" value="Id:" /><netui:label value="${actionForm.idVacante}" tagId="actionForm_idVacante" />
										<netui:hidden dataSource="actionForm.idVacante"/></td>									
							  	</tr>
								<tr>
									<td><netui:label for="actionForm_titulo" value="Titulo:" /></td>									
								</tr>
								<tr>
									<td><netui:textBox styleClass="big" dataSource="actionForm.titulo" tagId="actionForm_titulo" maxlength="250" onKeyPress="return isAlpha(event);" size="30" ></netui:textBox></td>
								</tr>
								<tr>
									<td height="25"><netui:label for="actionForm_descripcion" value="Descripcion:" />
								</tr>
								<tr>
									<td><netui:textArea dataSource="actionForm.descripcion" tagId="actionForm_descripcion" cols="50" rows="5" onKeyPress="return isAlphaNumeric(event);"  ></netui:textArea></td>
								</tr>
								<td width="55%" align="left">
									<div style="display:none;"><netui:rewriteName  name="actionForm_vacantes" forTagId="true" resultId="actionForm_vacantes"/></div>
						  			<netui:anchor onClick="submitForma('${actionForm_vacantes}',this);return false;" formSubmit="true" styleClass="btn-small margin-bot15" action="guardarVacante">GUARDAR</netui:anchor>						  			
						  		</td>
						  		<tr>
									<td align="center">																		
										<table border="0" width="100%" cellpadding="5">										  										
											<tr>
												<td align="center">										
													<netui:anchor styleClass="btn-minimized" action="verVacantes">VER VACANTES</netui:anchor>
												</td>											
												<td align="left">
													<netui:anchor styleClass="btn-minimized" action="actualizaCv">INGRESAR CV</netui:anchor>
												</td>
											</tr>                          		
										</table>
									</td>
								</tr>  
				</tbody>
			</table>  
			</div>
		</td>
		</tr>
	</table>      	                	        			
  </netui:form>
  </div>
</netui:scriptContainer>