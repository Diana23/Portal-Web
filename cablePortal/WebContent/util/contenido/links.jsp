<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<title>Explorer</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jsTree/source/jquery.tree.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/ckeditor/ckeditor.js"></script>



<script type="text/javascript">
	
	function setLink(label){
		<%
			if(request.getParameter("CKEditorFuncNum")!= null){
				%>
					window.opener.setVariableEditor(<%=request.getParameter("CKEditorFuncNum")%>);
				<%
			}
		%>
	
		window.opener.setLinkParent(label);
        window.close();
	}
	
	$(function () { 
		$("#basic_html").tree();
	});
</script>
<link href="${pageContext.request.contextPath}/resources/css/texto.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<%
	com.cablevision.portal.sitemap.SiteMapFactory factory = (com.cablevision.portal.sitemap.SiteMapFactory) Class.forName("com.cablevision.portal.sitemap.impl.ContextSiteMapFactory").newInstance();

	try {
		request.setAttribute("sites",com.cablevision.portal.sitemap.SiteMapManager.getSiteMapFromCache(request,factory,true).getSites());
	} catch(NullPointerException e) {
		e.printStackTrace();
	}
%>


<table cellspacing="0" cellpadding="0" border="0">
	<tr>
		<td width="10"></td>
		<td colspan="3"><br></td>
	</tr>
	<tr>
		<td width="10"></td>
		<td colspan="3"><p class="arial_titulo2">Escoge la p&aacute;gina a la cual se quiere el link</p></td>
	</tr>
	<tr>
		<td width="10"></td>
		<td colspan="3"><br></td>
	</tr>
	<tr>
		<td width="10" border="0"></td>
		<td width="30" border="0"></td>
		<td width="540">
			<div class="demo source" id="basic_html">

			    <jsp:directive.page isELIgnored="false" />
				<jsp:include page="sites2.jsp"/>
				
			</div>
		</td>
		<td width="10" border="0"></td>
	</tr>
</table>
  	
</body>
</html>