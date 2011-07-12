
<%
	String username = ((String)session.getAttribute(com.cablevision.util.Constantes.SESSION_ACCOUNT_ID)).trim();
	//Logger logger = LogFilter.getLoggerInstance();
	//LogFilter.log(log, username, Constantes.LOG_ACCION_SALIDA, 0, Constantes.LOG_ESTADO_SALIDA_EXISTOSO);
	session.invalidate();
	com.bea.portlet.GenericURL url = com.cablevision.util.PageNewUrl.createPageURL(request, response, "servicios_enlinea_inicio_serviciosEnLinea");
	//url.addParameter(com.bea.portlet.GenericURL.TREE_OPTIMIZATION_PARAM, "false");
	response.sendRedirect(url.toString());
%>