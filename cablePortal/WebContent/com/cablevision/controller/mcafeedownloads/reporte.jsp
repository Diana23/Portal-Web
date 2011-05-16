<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%>
<%@ page import="com.cablevision.util.ConfigurationHelper"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>

<netui:scriptContainer>


<link rel="stylesheet"  type="text/css" media="all" href="${pageContext.request.contextPath}/com/cablevision/controller/mcafeedownloads/js/jquery-date-picker/css/ui-lightness/jquery-ui-1.8.1.custom.css" />
<link rel="alternate stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/com/cablevision/controller/mcafeedownloads/js/jquery-date-picker/css/redmond/jquery-ui-1.8.1.custom.css" />

<script type="text/javascript" src="${pageContext.request.contextPath}/com/cablevision/controller/mcafeedownloads/js/jquery-date-picker/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/com/cablevision/controller/mcafeedownloads/js/jquery-date-picker/js/jquery-ui-1.8.1.custom.min.js"></script>

<style type="text/css">
.ctextoAzul {
	color:#221F73;
	font-family:Verdana,Arial,Helvetica,sans-serif;
	font-size:13px;
	font-weight:normal;
	letter-spacing:-1px;
	line-height:19px;
	padding-bottom:5px;
	text-align:left;
}
.ctextoGray{
	color:gray;
	font-family:Verdana,Arial,Helvetica,sans-serif;
	font-size:13px;
	font-weight:bold;
	letter-spacing:-1px;
	line-height:19px;
	padding-bottom:5px;
	text-align:left;
	background-color: #ffd669;
}

.txtRojo {
	color:#EF3E35;
	font-family:Verdana,Arial,Helvetica,sans-serif;
	font-size:10px;
	font-weight:normal;
	letter-spacing:-1px;
	padding-bottom:5px;
}

.internetTitulosRojo  {
	color:#EF4135;
	font-family:Verdana,Arial,Helvetica,sans-serif;
	font-size:13px;
	font-style:normal;
	font-weight:bold;
	letter-spacing:-1px;
}

.ctextoAzulBold{
	color:#221F73;
	font-family:Verdana,Arial,Helvetica,sans-serif;
	font-size:13px;
	font-weight:bold;
	letter-spacing:-1px;
	line-height:19px;
	padding-bottom:5px;
	text-align:left;
}

.txtRojo13{
	color:#EF3E35;
	font-family:Verdana,Arial,Helvetica,sans-serif;
	font-size:13px;
	font-weight:bold;
	letter-spacing:-1px;
	line-height:19px;
	padding-bottom:5px;
	text-align:left;
}

</style>
<div class="span-18">
	<netui:form action="mostrarReporte" tagId="report_form">
		<table cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="5">
					<table cellspacing="0" cellpadding="0">
						<tr>
							<td width="0" border="0"></td>
							<td width="10" border="0" background="${pageContext.request.contextPath }/contenido/groups/mercadotecnia/documents/imagen_cv/cv000071.png"></td>
							<td width="540" align="right" background="${pageContext.request.contextPath }/contenido/groups/mercadotecnia/documents/imagen_cv/cv000072.png" class="bienvenida" background-repeat="repeat-y" height="34"><netui:label value="Reporte de Usuarios Registrados McAfee" styleClass="ctextoAzulBold"/></td>
							<td width="10" border="0" background="${pageContext.request.contextPath }/contenido/groups/mercadotecnia/documents/imagen_cv/cv000073.png"></td>
						</tr>
						<tr>
							<td width="0" border="0"></td>
							<td width="10" border="0" background="${pageContext.request.contextPath }/contenido/groups/mercadotecnia/documents/imagen_cv/cv000074.png" background-repeat="repeat-x"></td>
							<td width="540" background="${pageContext.request.contextPath }/contenido/groups/mercadotecnia/documents/imagen_cv/cv000075.png" background-repeat="repeat-x" >
								<table width="530" cellspacing="0" cellpadding="5">
									<tr class="ctextoAzul">
										<td width="30%">
											<netui:label value="Cuenta:"/>
											<netui:textBox dataSource="actionForm.cuenta" tagId="cuenta" size="14" maxlength="10" styleClass="txtRojo"/>
										</td>
										<td width="35%">
											<netui:label value="Mes:"/>
											<netui:select dataSource="actionForm.mes" styleClass="txtRojo">
												<netui:selectOption value="">
													<netui:label value="..."/>
												</netui:selectOption>
												<c:forEach items="${pageInput.meses}" var="mes">
								                   	<netui:selectOption value="${mes.key}">
														<netui:label value="${mes.value}"/>
													</netui:selectOption>
											    </c:forEach>
											</netui:select>
										</td>
										<td width="35%">
											<netui:label value="Dia:"/>
											<netui:textBox dataSource="actionForm.dia" tagId="dia" maxlength="10" size="14" styleClass="txtRojo"/>
										</td>
									</tr>
									<tr>
										<td colspan="3">&nbsp</td>
									</tr>
									<tr class="ctextoAzul">
										<td>
											<netui:label value="Intervalo:"/>
										</td>
										<td>
											<netui:label value="De: "/>
											<netui:textBox dataSource="actionForm.fechaDe" tagId="diaDe" size="14" styleClass="txtRojo"/>
										</td>
										<td>
											<netui:label value="A: "/>
											<netui:textBox dataSource="actionForm.fechaA" tagId="diaA" size="14" styleClass="txtRojo"/>
										</td>
									</tr>
									<tr>
										<td colspan="3">&nbsp</td>
									</tr>
									<tr class="ctextoAzul">
										<td>
											<netui:label value="Producto:"/>
											<netui:select dataSource="actionForm.tipoProducto" tagId="tipoProducto">
												<netui:selectOption value="ANTERIOR">
													<netui:label value="Anterior"/>
												</netui:selectOption>
												<netui:selectOption value="NUEVO">
													<netui:label value="Nuevo"/>
												</netui:selectOption>
											</netui:select>
										</td>
										<td colspan="2" align="left">
											<netui:label value="Estatus:"/>
						   					<netui:select dataSource="actionForm.estatus" tagId="status">
												<netui:selectOption value="ACTIVO">
													<netui:label value="Activos"/>
												</netui:selectOption>
												<netui:selectOption value="INACTIVO">
													<netui:label value="Inactivos"/>
												</netui:selectOption>
												<netui:selectOption value="CANCELADO">
													<netui:label value="Cancelados"/>
												</netui:selectOption>
												<netui:selectOption value="SUSPENDIDO">
													<netui:label value="Suspendidos"/>
												</netui:selectOption>
												<netui:selectOption value="MIGRADO">
													<netui:label value="Migrados"/>
												</netui:selectOption>
												<netui:selectOption value="REACTIVADO">
													<netui:label value="Reactivados"/>
												</netui:selectOption>
											</netui:select>
										</td>
									</tr>
									<tr>
										<td colspan="3">
											&nbsp;
										</td>
									</tr>
									<tr>
										<td align="left" colspan="1">
											<netui:button action="mostrarResumen" value="Ver Resumen" style="background-color:#FFFFFF;color:#FF0000;font-weight:normal;font-family:arial;font-size:9;border-width:0;cursor:pointer;"/>
										</td>
										<td align="right" colspan="2">
											<netui:imageButton src="${pageContext.request.contextPath}/contenido/groups/mercadotecnia/documents/imagen_cv/cv000783.jpg"/>
										</td>
									</tr>
								</table>
							</td>
							<td width="10" border="0" background="${pageContext.request.contextPath }/contenido/groups/mercadotecnia/documents/imagen_cv/cv000076.png" background-repeat="repeat-x"></td>
						</tr>
						<tr>
							<td width="0" border="0"></td>
							<td width="10" border="0" background="${pageContext.request.contextPath }/contenido/groups/mercadotecnia/documents/imagen_cv/cv000077.png"></td>
							<td width="540" align="right" background="${pageContext.request.contextPath }/contenido/groups/mercadotecnia/documents/imagen_cv/cv000078.png" background-repeat="repeat-y" height="52"> </td>
							<td width="10" border="0" background="${pageContext.request.contextPath }/contenido/groups/mercadotecnia/documents/imagen_cv/cv000079.png"></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<br/>
		<table>
			<tr>
				<td colspan="3">
					<c:choose>
						<c:when  test="${!empty pageInput.lista}">
							<div style="border-color: black white white; overflow: scroll; width: 530px; border-style: ridge; height: 150px;">
								<table height="100%" width="100%" border="0">
									<tr class="ctextoGray">
										<td>
											<netui:label value="Año-Mes"/>
										</td>
										<c:forEach items="${pageInput.lista}" var="list">
											<td align="center">
												<netui:label value="${list[1]}"><netui:formatDate pattern="yyyy-MM"/></netui:label>
											</td>
							            </c:forEach>
						            </tr>
						            <tr class="ctextoAzul">
										<td>
											<strong><netui:label value="Total por mes"/></strong>
										</td>
										<c:forEach items="${pageInput.lista}" var="list">
											<td align="center">
												<netui:label value="${list[0]}"/>
											</td>
							            </c:forEach>
						            </tr>
						            <tr class="ctextoAzul">
										<td>
											<strong><netui:label value="Acumulado"/></strong>
										</td>
										<c:set var="acumulado" value="0" />
									     <c:forEach items="${pageInput.lista}" var="list" >
										     <td align="center">
										     	<c:set var="acumulado" value="${acumulado + list[0]}" />
										        <netui:label value="${acumulado}"/>
										      </td>
									     </c:forEach>
					             	</tr>
								</table>
							</div>
						</c:when>
						<c:when  test="${!empty pageInput.resumenFechas || !empty pageInput.origen}">
							<div style="display: block;">
								<table width="500px" align="center">
									<tr class="ctextoAzulBold">
										<td width="70%">
											<netui:label value="Total de usuarios registrados acumulados:"/>
										</td>
										<td width="30%" class="txtRojo13">
											<a href="${pageContext.request.contextPath }/com/cablevision/controller/mcafeedownloads/mostrarReporteExcel.do?fechaDe=2007-11-01&fechaA=${pageInput.fechaFin}&estatus=${actionForm.estatus}&tipoProducto=${actionForm.tipoProducto}">
												<netui:label value="${pageInput.resumenFechas[0] + pageInput.resumenFechas[1]}"/>
											</a>
										</td>
									</tr>
									<tr class="ctextoAzul">
							            <td width="70%">
							            	<netui:label value="Total de usuarios registrados en ${pageInput.fechaLetra}:"/>
							            </td>
							            <td width="30%" class="txtRojo">
							            	<a href="${pageContext.request.contextPath }/com/cablevision/controller/mcafeedownloads/mostrarReporteExcel.do?fechaDe=${pageInput.fechaInicio}&fechaA=${pageInput.fechaFin}&estatus=${actionForm.estatus}&tipoProducto=${actionForm.tipoProducto}">
							            		<netui:label value="${pageInput.resumenFechas[1]}"/>
							            	</a>
							            </td>
							        </tr>
							        <tr class="ctextoAzul">
							            <td width="70%">
							            	<netui:label value="Total de usuarios registrados antes de ${pageInput.fechaLetra}:"/>
							            </td>
							            <td width="30%" class="txtRojo">
							            	<a href="${pageContext.request.contextPath }/com/cablevision/controller/mcafeedownloads/mostrarReporteExcel.do?fechaDe=2007-11-01&fechaA=${pageInput.fechaFinTotalAntes}&estatus=${actionForm.estatus}&tipoProducto=${actionForm.tipoProducto}">
							            		<netui:label value="${pageInput.resumenFechas[0]}"/>
							            	</a>
							            </td>
						            </tr>
						            <tr>
						            	<td colspan="2">
						            		<hr>
						            	</td>
						            </tr>
						            <!-- 
						            <tr class="ctextoAzulBold">
						            	<td width="75%">
											<netui:label value="Origen de Descargas en ${pageInput.fechaLetra}:"/>
										</td>
										<td width="25%" class="txtRojo13">
										</td>
									</tr>
									<c:forEach items="${pageInput.origen}" var="origen">
										<tr class="ctextoAzul">
											<td width="75%">
												<c:if test="${empty origen[1]}">
													DIRECTO
												</c:if>
												<c:if test="${!empty origen[1]}">
													<netui:label value="${origen[1]}"/>
												</c:if>
											</td>
											<td width="25%" class="txtRojo">
												<netui:label value="${origen[0]}"/>
											</td>
										</tr>
						            </c:forEach>
						             -->
								</table>
							</div>
						</c:when>
						<c:otherwise>
							<c:if test="${!empty pageInput.mcafeeUser}">
								<div style="display: block;">
									<table width="70%" align="center">
										<tr>
											<td width="75%" class="ctextoAzulBold">
												<netui:label value="Status:"/>
											</td>
											<td width="25%" class="txtRojo13">
												<netui:label value="${pageInput.mcafeeUser.musCvstatus}"/>
											</td>
										</tr>
										<tr>
											<td width="75%" class="ctextoAzulBold">
												<netui:label value="#Cuenta:"/>
											</td>
											<td width="25%" class="txtRojo13">
												<netui:label value="${pageInput.mcafeeUser.musAccount}"/>
											</td>
										</tr>
										<tr>
											<td width="75%" class="ctextoAzulBold">
												<netui:label value="Email:"/>
											</td>
											<td width="25%" class="txtRojo13">
												<netui:label value="${pageInput.mcafeeUser.musEmailaddress}"/>
											</td>
										</tr>
										<tr>
											<td width="75%" class="ctextoAzulBold">
												<netui:label value="Fecha Registro en McAfee:"/>
											</td>
											<td width="25%" class="txtRojo13">
												<netui:label value="${pageInput.mcafeeUser.musDatesuscribe}"/>
											</td>
										</tr>
							            <tr>
							            	<td colspan="2">
							            		<hr>
							            	</td>
							            </tr>
							            <!-- 
							            <tr>
											<td width="75%" class="ctextoAzulBold">
												<netui:label value="Detalle de descargas:"/>
											</td>
											<td width="25%" class="txtRojo13">
											</td>
										</tr>
										<tr>
											<td width="75%" class="txtRojo13">
												<netui:label value="Fecha"/>
											</td>
											<td width="25%" class="txtRojo13">
												<netui:label value="Origen"/>
											</td>
										</tr>
										<c:forEach items="${pageInput.listDownloads}" var="download">
											<tr class="ctextoAzul">
												<td width="75%">
													<netui:label value="${download.mdlDate}"/>
												</td>
												<td width="25%" class="txtRojo13">
													<netui:label value="${download.mdlRefer}"/>
												</td>
											</tr>
							            </c:forEach>
							             -->
									</table>
								</div>
							</c:if>
							<c:if test="${!empty pageInput.mcafeeUserNuevo}">
								<div style="display: block;">
									<table width="70%" align="center">
										<tr>
											<td width="75%" class="ctextoAzulBold">
												<netui:label value="Status:"/>
											</td>
											<td width="25%" class="txtRojo13">
												<netui:label value="${pageInput.mcafeeUserNuevo.mcaCvstatus}"/>
											</td>
										</tr>
										<tr>
											<td width="75%" class="ctextoAzulBold">
												<netui:label value="#Cuenta:"/>
											</td>
											<td width="25%" class="txtRojo13">
												<netui:label value="${pageInput.mcafeeUserNuevo.mcaAccount}"/>
											</td>
										</tr>
										<tr>
											<td width="75%" class="ctextoAzulBold">
												<netui:label value="Email:"/>
											</td>
											<td width="25%" class="txtRojo13">
												<netui:label value="${pageInput.mcafeeUserNuevo.mcaEmailaddress}"/>
											</td>
										</tr>
										<tr>
											<td width="75%" class="ctextoAzulBold">
												<netui:label value="Fecha Registro en McAfee:"/>
											</td>
											<td width="25%" class="txtRojo13">
												<netui:label value="${pageInput.mcafeeUserNuevo.mcaDatesuscribe}"/>
											</td>
										</tr>
							            <tr>
							            	<td colspan="2">
							            		<hr>
							            	</td>
							            </tr>
									</table>
								</div>
							</c:if>
							<c:if test="${!empty pageInput.errors}">
								<c:forEach items="${pageInput.errors}" var="errors">
									<tr class="txtRojo13">
										<td width="100%" colspan="2">
											<netui:label value="${errors}"/>
										</td>
									</tr>
								</c:forEach>
			        		</c:if>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</table>
	</netui:form>
</div>
<script type="text/javascript">

	$("#"+getJqId('<netui:rewriteName name="dia" forTagId="true" resultId="dia"/>')).datepicker({dateFormat: 'yy-mm-dd',changeMonth: true,changeYear: true,showOn:'both'});
	$("#"+getJqId('<netui:rewriteName name="diaDe" forTagId="true" resultId="diaDe"/>')).datepicker({dateFormat: 'yy-mm-dd',changeMonth: true,changeYear: true,showOn:'both'});
	$("#"+getJqId('<netui:rewriteName name="diaA" forTagId="true" resultId="diaA"/>')).datepicker({dateFormat: 'yy-mm-dd',changeMonth: true,changeYear: true,showOn:'both'});

	function getJqId(myid){ return myid.replace(/:/g,"\\:").replace(/\./g,"\\."); }
</script>

</netui:scriptContainer>