<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>


<script src="<%=request.getContextPath()%>/framework/skins/cablevision/js/jquery-1.4.2.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/framework/skins/cablevision/css/screenfix.css" type="text/css" media="screen, projection" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/framework/skins/cablevision/css/print.css" type="text/css" media="print"/>	
<link rel="stylesheet" href="<%=request.getContextPath()%>/framework/skins/cablevision/css/layout.css" type="text/css" media="screen, projection"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/framework/skins/cablevision/css/ui.css" type="text/css" media="screen, projection"/>


<jsp:directive.page import="com.bea.portlet.GenericURL"/>
<jsp:directive.page import="org.apache.commons.lang.StringUtils"/>
<jsp:directive.page import="com.bea.portlet.PageURL"/>
<jsp:directive.page import="com.cablevision.util.PageNewUrl"/>

<script type="text/javascript">
	var contextPath = "${pageContext.request.contextPath}";
</script>

<%
	GenericURL urltmp = GenericURL.createGenericURL(request, response);
	urltmp.setPath(request.getContextPath()+"/com/cablevision/controller/email/begin.do");
	urltmp.setTemplate("https");
%>
<script type="text/javascript">
	var urlHttps = "<%=urltmp.toString()%>";
</script>

<script src="${pageContext.request.contextPath}/com/cablevision/controller/email/email.js" type="text/javascript"></script>
<netui-data:declareBundle name="conf" bundlePath="configuracion"/>
<div id="correoCablevision"></div>
<jsp:scriptlet>
	long time = System.currentTimeMillis();
	
	
	com.bea.p13n.cache.Cache cache = com.bea.p13n.cache.CacheFactory.getCache("headerLinks",1000,1800000,true);
	String key = "headerLinks";
	Map urlsMap  = (Map)cache.get("headerLinks");
	if(urlsMap==null){
		urlsMap = new HashMap();
		
		GenericURL cuenta = PageNewUrl.createPageURL(request, response, "cablevision_portal_crearCuenta");
		//cuenta.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false");
		//cuenta.setEncodeSession(false);
		//cuenta.setTemplate("defaultDesktop");
		urlsMap.put("cuenta",cuenta.toString());
		
		GenericURL pass = PageNewUrl.createPageURL(request, response, "cablevision_portal_recuperaPass");
		//pass.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false");
		//pass.setEncodeSession(false);
		//pass.setTemplate("defaultDesktop");
		urlsMap.put("pass",pass.toString());
		
		cache.put("headerLinks",urlsMap);
		
	}
	
	pageContext.setAttribute("urlsMap",urlsMap);
</jsp:scriptlet>


<ul id="side">	
			
	<!-- Correo -->
	<li class="first">
		<h2 class="side2 ico-correo margin-bot15"><b>Correo CABLEVISION&reg; powered by Google&trade; Apps</b></h2>
		<div class="error-msg" id="msgError"></div>
		<div class="text-side"><label for="user-box">Usuario: </label><input type="text" size="25" value="" id="user" name="user"></div>
		
		<div class="error-msg" id="msgErrorPwd"></div>
		<div class="text-side"><label for="pass-box">Contraseña: </label><input type="password" size="21" value="" id="password" name="password"></div>
		
		
		<!-- links -->
		<div class="span-3-fix">
			 - <a title="&iquest;Olvidaste tu clave?" href="${urlsMap.pass}" target="_parent">&iquest;Olvidaste tu contraseña?</a><br>
			 - <netui:anchor href="${urlsMap.cuenta}" title="Crear cuenta" target="_parent"> 
			 	<netui:parameter name="nueva" value="true"/>
			   	Crear cuenta
			   </netui:anchor>
		</div>
		
		<!-- botón de enviar -->
		<a class="btn-small fright" title="Enviar" href="#" onclick="filtrarUsuario(); return false;">Enviar</a>
		<div class="clear"></div>
		
		<!-- form para entrar al correo de gmail-->
		<form target="_blank" onsubmit="return(gaia_onLoginSubmit());" method="get" action="${bundle.conf['email.url.gmail']}" id="gaia_loginform">
		  	<input type="hidden" class="gaia le val" value="" size="18" id="EmailGoogle" name="Email" />
		</form>
		
	</li>
</ul>