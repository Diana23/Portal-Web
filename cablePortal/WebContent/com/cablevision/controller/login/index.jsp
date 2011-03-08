<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<netui:scriptContainer>

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/com/cablevision/controller/cotizador/popup.js"></script>

<script type="text/javascript">

$(document).ready(function() {
	var txtPop = jQuery('#txtPopUp').val();
	if(txtPop == "mostrar"){
		showPopup();
}

});

</script>

<style>
	.errorRed {
		color: red;
		font-family:Calibri;
		font-size:14px;
		font-weight:normal;
	}
</style>

<%
com.bea.portlet.PageURL url = com.bea.portlet.PageURL.createPageURL(request, response, "aplicaciones_registro");
url.setForcedAmpForm(false);
url.addParameter(com.bea.portlet.GenericURL.TREE_OPTIMIZATION_PARAM, "false");
url.setTemplate("https");
pageContext.setAttribute("url",java.net.URLEncoder.encode(url.toString(),"UTF-8"));

com.bea.portlet.PageURL urlLogin = com.bea.portlet.PageURL.createPageURL(request, response, "cablevision_portal_login");
urlLogin.setForcedAmpForm(false);
urlLogin.addParameter(com.bea.portlet.GenericURL.TREE_OPTIMIZATION_PARAM, "false");
urlLogin.setTemplate("https");
pageContext.setAttribute("urlLogin",java.net.URLEncoder.encode(urlLogin.toString(),"UTF-8"));

com.bea.portlet.PageURL urlOlvidaste = com.bea.portlet.PageURL.createPageURL(request, response, "servicios_enlinea_recuperarPassword");
urlOlvidaste.setForcedAmpForm(false);
urlOlvidaste.addParameter(com.bea.portlet.GenericURL.TREE_OPTIMIZATION_PARAM, "false");
urlOlvidaste.setTemplate("https");
pageContext.setAttribute("urlOlvidaste",urlOlvidaste.toString());

%>

<div style="width:600px;">

<p class="revisaNaranja">
	<c:if test="${!empty pageInput.msg}">
    	${pageInput.msg}
    </c:if>
    
    <c:if test="${!empty pageInput.popup}">
    	<input type="hidden" value="${pageInput.popup}" id="txtPopUp">
	</c:if>
</p>
<div id="<netui:rewriteName name="login_swf" forTagId="true" resultId="login_swf"/>" class="login-swf"></div>

<!-- div del popup -->
	<div id="popupContact">  
         <a href="#" id="popupContactClose" onclick="disablePopup();return false;">x</a>  
         <!--  <a href="${url}" id="popupContactClose" >x</a>   -->
 
         <table cellspacing="0" cellpadding="0">
			<tr>
				<td width="0" border="0"></td>
				<td width="10" border="0" background="${pageContext.request.contextPath }/contenido/groups/mercadotecnia/documents/imagen_cv/cv000071.png"></td>
				<td width="540" align="left" background="${pageContext.request.contextPath }/contenido/groups/mercadotecnia/documents/imagen_cv/cv000072.png" class="bienvenida" background-repeat="repeat-y" height="34">Cuenta Bloqueada</td>
				<td width="10" border="0" background="${pageContext.request.contextPath }/contenido/groups/mercadotecnia/documents/imagen_cv/cv000073.png"></td>
			</tr>
			<tr>
				<td width="0" border="0"></td>
				<td width="10" border="0" background="${pageContext.request.contextPath }/contenido/groups/mercadotecnia/documents/imagen_cv/cv000074.png" background-repeat="repeat-x"></td>
				<td width="540" background="${pageContext.request.contextPath }/contenido/groups/mercadotecnia/documents/imagen_cv/cv000075.png" background-repeat="repeat-x" >
					<table width="530" cellspacing="0" cellpadding="5">
						<tr>
							<td class="revisa" align="center"> 
								<br/>
							</td>
						</tr>
						<tr>
							<td align="center" class="errorRed"> 
								Tu cuenta se ha bloqueado temporalmente, podr&aacute;s volver a intentarlo dentro de una hora
							</td>
						</tr>
					</table>
				</td>
				<td width="10" border="0" background="${pageContext.request.contextPath }/contenido/groups/mercadotecnia/documents/imagen_cv/cv000076.png" background-repeat="repeat-x"></td>
			</tr>
			<tr>
				<td width="0" border="0"></td>
				<td width="10" border="0" background="${pageContext.request.contextPath }/contenido/groups/mercadotecnia/documents/imagen_cv/cv000077.png"></td>
				 
				<td width="540" align="center" background="${pageContext.request.contextPath }/contenido/groups/mercadotecnia/documents/imagen_cv/cv000078.png" background-repeat="repeat-y" height="52">
					<!-- <a href="${url}"> -->
					<a href="#" onclick="disablePopup();return false;">
						<img src="${pageContext.request.contextPath}/contenido/groups/mercadotecnia/documents/imagen_cv/cv001787.png" width="123" height="38" border="0" />
					</a>
					<a href="${urlOlvidaste}">
						<img src="${pageContext.request.contextPath}/contenido/groups/mercadotecnia/documents/imagen_cv/cv001921.png" height="35" border="0" align="top"/>
					</a>
				</td>
				<td width="10" border="0" background="${pageContext.request.contextPath }/contenido/groups/mercadotecnia/documents/imagen_cv/cv000079.png"></td>
			</tr>
		</table> 
     </div>  
     <div id="backgroundPopup"></div>
	<!-- fin del div del popup -->


</div>



<script type="text/javascript">

var flashvars = {
	registro:'${url}',
	form:	 '${urlLogin}',
	url:	 '',
	olvidasteUrl:'${urlOlvidaste}'
}

var params = {
	wmode : "opaque"
}

var params = {
 wmode : "opaque"
}

swfobject.embedSWF(
	'${pageContext.request.contextPath}/com/cablevision/controller/login/servicios.swf', 
	'<netui:rewriteName name="login_swf" forTagId="true" resultId="login_swf"/>', 
	'600', '800', '9.0.0', false, 
	flashvars,params,{}
);

</script>


</netui:scriptContainer>