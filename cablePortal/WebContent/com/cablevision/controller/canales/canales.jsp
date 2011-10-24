<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>

<%@page import="com.cablevision.util.Constantes"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/framework/skins/cablevision/js/jquery.tooltip.min.js"></script>
<link href="<%=request.getContextPath()%>/framework/skins/cablevision/css/jquery.tooltip.css" type="text/css" rel="stylesheet">
<script type="text/javascript">
	var tb_pathToImage = "<%=request.getContextPath()%>/images/loadingAnimation.gif";
</script>


<netui:scriptContainer idScope="canales_${fn:escapeXml(param._windowLabel)}">


			<div class="span-18 last">
				<netui:form action="ordenar" tagId="canalesForm">
					
					<div class="wrap-cont-simple">
						<span class="bajada-canales">M&aacute;s de 280 opciones para que disfrutes como m&aacute;s te gusta.</span>
						<p>En CABLEVISION&reg; te ofrecemos m&aacute;s canales que ninguna otra compa&ntilde;&iacute;a, porque estamos convencidos de que eres t&uacute; el que debe elegir.<br /> As&iacute; de sencillo, sin pagar de m&aacute;s.</p>
						<br /><br />
					
						<div class="wrap-grey-bar">
							<div class="wgb-left">
								<strong>ORDENAR POR</strong>
								<netui:select dataSource="actionForm.ordenarPor" onChange="document.getElementById(lookupIdByTagId('canalesForm',this)).submit();" defaultValue="2">
									<netui:selectOption value="1">Nombre del Canal</netui:selectOption>
									<netui:selectOption value="2">Numero de Canal</netui:selectOption>
									<netui:selectOption value="3">Categor&iacute;as.</netui:selectOption>
									<netui:selectOption value="4">Paquetes.</netui:selectOption>
								</netui:select>
							</div>
							
							<div class="wgb-right">
								<netui:textBox dataSource="actionForm.buscar" styleClass="search-box-cha sb-canales" size="23" onKeyPress="return isAlphaNumeric(event);">Busca</netui:textBox>
								<div style="display:none;">
									<netui:rewriteName  name="canalesForm" forTagId="true" resultId="canalesFormName"/>
								</div>
								<netui:anchor onClick="submitForma('${canalesFormName}',this);return false;" formSubmit="true" action="buscar" styleClass="buscar-canales fright hidden-text" title="buscar" />
							</div>
							
							<div class="clear"></div>
						</div>
						
						<table id="table-canales" border="0" cellpadding="0" cellspacing="0">
							<!-- CABECERA de la tabla - Nombres de paquetes -->
							  <tr>
								<td class="erasetd" width="30" height="45" align="center" valign="middle" class="bord-boted" bgcolor="#f28600">
									<div style="display:none;">
										<netui:rewriteName  name="canalesForm" forTagId="true" resultId="canalesFormName"/>
									</div>
									<netui:anchor  onClick="submitForma('${canalesFormName}',this);return false;" formSubmit="true" action="ordenar" styleClass="style5 mayusculas" title="Nº">
										<span class="color-white">N&ordm;</span>
										<netui:parameter name="ordenarPor" value="2"/>
									</netui:anchor>
									
								</td>
								<td width="75" height="45" align="center" valign="middle" class="bord-boted" >
									<netui:anchor  onClick="submitForma('${canalesFormName}',this);return false;" formSubmit="true" action="ordenar" styleClass="wtd" title="Canales" value="Canales">
										<netui:parameter name="ordenarPor" value="1"/>
									</netui:anchor>
								</td>
								<c:set var="color" value="#fce5c9"/>
								<c:forEach var="pack" items="${pageInput.paquetes}">
									<td width="75" height="45" align="center" valign="middle" class="bord-boted" bgcolor="${ color }">
										<div style="display:none;">
											<netui:rewriteName  name="canalesForm" forTagId="true" resultId="canalesFormName"/>
										</div>
										<netui:anchor  onClick="submitForma('${canalesFormName}',this);return false;" formSubmit="true" action="ordenar" styleClass="wtd" title="${pack.name}" value="${pack.name}">
											<netui:parameter name="primerOrd" value="${pack.idpack}"/>
										</netui:anchor>
									</td>
									<c:choose>
								  	<c:when test='${color eq "#fce5c9"}'>
								  		<c:set var="color" value=""/>
								  	</c:when>
								  	<c:otherwise>
								  		<c:set var="color" value="#fce5c9"/>
								  	</c:otherwise>
								  	</c:choose>
									</c:forEach>
								<c:choose>
								  	<c:when test='${color eq "#fce5c9"}'>
								  		<c:set var="color" value=""/>
								  	</c:when>
								  	<c:otherwise>
								  		<c:set var="color" value="#fce5c9"/>
								  	</c:otherwise>
								  </c:choose>
								<td width="14" height="45" align="center" valign="middle" class="bord-boted" bgcolor="${ color }">&nbsp;</td>
								<c:choose>
								  	<c:when test='${color eq "#fce5c9"}'>
								  		<c:set var="color" value=""/>
								  	</c:when>
								  	<c:otherwise>
								  		<c:set var="color" value="#fce5c9"/>
								  	</c:otherwise>
								  </c:choose>
								<td class="columncheck" width="141" height="45" align="center" valign="middle" class="bord-boted" bgcolor="${ color }"><span class="style5">Filtrar por:</span></td>
							  </tr>
							  
							  <!-- CANALES encontrados -->
							  <tr>
								<td colspan="${fn:length(pageInput.paquetes)+3 }" rowspan="20" align="left" valign="top">
									<div class="wrap-canales-table">
									  <table width="100%" border="0" cellpadding="0" cellspacing="0">
									  	<c:set var="channelCount" value="1"/>							  		
											<c:if test="${!empty pageInput.popup}">
										    	<input type="hidden" value="${pageInput.popup}" id="txtPopUp">
											</c:if>
																					  		
									  		<c:forEach var="canalKV" items="${pageInput.canales}">
									  		<tr>
											  <td style="width:28px;*width:26px" height="30" align="center" valign="middle" class="bord-boted" bgcolor="#f28600"><span class="color-white">${ canalKV.key.idchannel }</span></td>
											  <td width="83" height="30" align="center" valign="middle" class="bord-boted" >
											  	<a href="#" title="<strong>${ canalKV.key.name }</strong><br /> ${ canalKV.key.description }" class="bk-logos-canales" id="canal${channelCount}" rel="${channelCount}">
											  		<img src="${ canalKV.key.logo }" width="25" height="25" alt="${ canalKV.key.name }"/>
											  	</a>
											  </td>
											  <c:set var="channelCount" value="${channelCount + 1}"/>
											  <c:set var="size" value="${fn:length(pageInput.paquetes)}"/>
											  <c:set var="countPack" value="1"/>
											  <c:set var="width" value="83"/>
											  <c:forEach var="pack" items="${ pageInput.paquetes }">
											  	<c:if test="${countPack == size}">
											  		<c:set var="width" value="77"/>
											  	</c:if>
											  	<td width="${ width }" height="30" align="center" valign="middle" class="bord-boted">
											  	<c:set var="contains" value="false"/>
											  	<c:forEach var="packEl" items="${canalKV.value}">
											  		<c:if test="${packEl == pack.idpack}"><c:set var="contains" value="true"/></c:if>
											  	</c:forEach>
											  	<c:choose>
											  		<c:when test="${contains}">
											  			<img src="${pageContext.request.contextPath }/images/visto.png" alt="visto" />
											  		</c:when>
											  		<c:otherwise>
											  			<c:choose>
											  				<c:when test="${ pack.idpack==4 || pack.idpack==5 || pack.idpack==7 }">
											  					-
											  				</c:when>
											  				<c:otherwise>
																<img src="${pageContext.request.contextPath }/images/cruz.png" alt="cruz" />											  				
											  				</c:otherwise>
											  			</c:choose>
											  		</c:otherwise>
											  	</c:choose>
											  	</td>
											  	<c:set var="countPack" value="${countPack + 1}"/>
											  </c:forEach>
											  
											</tr>
									  	</c:forEach>
									  </table>
									</div>    
								</td>
							</tr>
							
							<!-- CATEGORIAS -->
							<c:set var="color" value="#e6e4e4"/>
							<c:set var="count" value="1"/>
							<netui:checkBoxGroup dataSource="actionForm.filtros">
							<c:forEach items="${pageInput.categorias}" var="cat">
								<tr>						
									<td class="columncheck" width="140" height="30" align="left" valign="middle" bgcolor="<c:out value="${color}"/>">
										<netui:checkBoxOption value="${cat.key}" >
											<label class="style8">
												<c:out value="${cat.value}"/>
											</label>
										</netui:checkBoxOption>
									</td>
							  	</tr>
							  	<c:choose>
							  	<c:when test='${color eq "#e6e4e4"}'>
							  		<c:set var="color" value=""/>
							  	</c:when>
							  	<c:otherwise>
							  		<c:set var="color" value="#e6e4e4"/>
							  	</c:otherwise>
							  	</c:choose>
							  	<c:set var="count" value="${count + 1}"/>
							</c:forEach>
							</netui:checkBoxGroup>
							  <tr>
								<td width="140" align="center" valign="bottom" height="200" bgcolor="#e6e4e4">
									<div style="display:none;">
										<netui:rewriteName  name="canalesForm" forTagId="true" resultId="canalesFormName"/>
									</div>
									<netui:anchor  onClick="submitForma('${canalesFormName}',this);return false;" formSubmit="true" action="filtrar" styleClass="btn-ctrt" title="Filtrar" value="Filtrar"/>
									<br /><br /><br /><br /><br /><br /><br /><br /><br />
<!-- 
									<a id="imprimir" title="Imprimir" value="Imprimir" href="${pageContext.request.contextPath }/com/cablevision/controller/canales/imprimir.do?modal=true&amp;keepThis=true&amp;height=500&amp;width=550" class="btn-small thickbox">Imprimir</a>
 -->
 
 <netui:anchor  onClick="submitForma('${canalesFormName}',this,'_blank');return false;" formSubmit="true" popup="true" action="imprimir" styleClass="btn-small" title="Imprimir" value="Imprimir"/>
								</td>
							  </tr> 
							</table>
					</div>
					
					<div style="visibility: hidden">
						<a href="${pageContext.request.contextPath }/images/close.png#TB_inline?height=300&width=430&inlineId=popupContact&modal=true" class="thickbox" id="linkErrorBusqueda"></a>
					</div>
					
					<!-- div del popup -->
					<div id="popupContact" style="visibility: hidden;">
						<div class="login-in-thbox">
							<div align="center" class="wrap-img-thbk5">
								<a class="close-cross3" title="cerrar" onclick="self.parent.tb_remove();$('#popupContact').css('visibility','hidden');return false;" href="#" style="text-decoration: none;">
									<img border="0" alt="X" src="${pageContext.request.contextPath }/images/close.png">
								</a>
									Su b&uacute;squeda no produjo resultado<br>
									intente de nuevo.
								<div class="clear"></div>
							</div>
						</div>
				     </div>
					<!-- fin del div del popup -->
					
					<script type="text/javascript">
					
						function submitForma(formaName,link,target){
							href = link.href.replace("javascript:bea.wlp.disc.xie._Service.update('","");
							href = href.substring(0,href.length-3);
							if(bea&&bea.wlp&&bea.wlp.disc&&bea.wlp.disc.xie&&bea.wlp.disc.xie._Service&&!target){
								bea.wlp.disc.xie._Service.update(href,formaName);
							}else{
								for (var i=0; i<document.forms.length; i++) {
							     if (document.forms[i].id == formaName) {
							        document.forms[i].method = "POST";
							        document.forms[i].action = href;
							        
							        if(target){
							       		document.forms[i].target = target;
							        }else{
							        	document.forms[i].target = "_self";
							        }
							        
							        document.forms[i].submit();
							     }
							   }
							}
						}
						
					</script>
				</netui:form>
				
			</div>
			
			<script type="text/javascript">
				$(document).ready(function(){
					$(".bk-logos-canales").tooltip({ 
						track: true, 
						delay: 0, 
						showURL: false, 
						opacity: 1, 
						//fixPNG: true, 
						showBody: "soy el contenido?!!!!", 
						extraClass: "pretty", 
						top: -15, 
						left: 5
					}); 
		
			        var txtPop = jQuery('#txtPopUp').val();
			        if(txtPop == "mostrar"){
						$("#popupContact").removeAttr("style");
					}
				});
				var txtPop = jQuery('#txtPopUp').val();
		        if(txtPop == "mostrar"){
					tb_init('a.thickbox, area.thickbox, input.thickbox');
					$('#linkErrorBusqueda').trigger('click');
					
				}
				
			</script>

	</netui:scriptContainer>