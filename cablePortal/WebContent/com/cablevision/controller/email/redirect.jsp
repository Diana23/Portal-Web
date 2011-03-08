<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
<script type="text/javascript">
	var contextPath = "${pageContext.request.contextPath}";
</script>
	<jsp:scriptlet>
		if( request.getSession().getAttribute("_acs_url") != null  &&  request.getSession().getAttribute("_saml_response").toString() != null ){
			pageContext.setAttribute("urlSSO", request.getSession().getAttribute("_acs_url").toString()); 
			String strSML = request.getSession().getAttribute("_saml_response").toString().replaceAll("\"", "\'");
			pageContext.setAttribute("saml", strSML);
			System.out.println(strSML);
	</jsp:scriptlet>
		<script type="text/javascript">
			$(function(){
				document.getElementById("googleSSOForm").submit();
			});
		</script>
	<jsp:scriptlet>
		}
	</jsp:scriptlet>
	
	<!-- Form del redirect de GoogleSSO-->
	<form method="post" action="${urlSSO}" id="googleSSOForm" name="googleSSOForm">
		<input type="hidden"  id="SAMLResponse" name="SAMLResponse" value="${saml}" />
		<input type="hidden" name="RelayState" value="token" />
	</form>