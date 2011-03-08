<script>
	var contextPath = "<%=request.getContextPath()%>";
</script>
<link href="<%=request.getContextPath()%>/resources/css/explorer.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/resources/css/templates.css" type="text/css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/resources/js/explorer.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/prototype.js"></script>
<script src="<%=request.getContextPath()%>/com/cablevision/controller/contenido/contenido.js"></script>

<html>
<head>
</head>
<body>
	<form action="workflow.jsp" id="formaWf" name="formaWf">
		<table border="0" width="30%" align="center">
			<tr>
				<td width="20%">
					Content Id:
				</td>
				<td width="80%">
					${pageInput.contentId}
					<input type="hidden" id="contentId" name="contentId" value="${pageInput.contentId}">
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td>
					<select id="opcionFlujo" name="opcionFlujo" onchange="showMotivo();">
						<option selected="selected" value="">...</option>
						<option value="1">Aceptar</option>
						<option value="2">Rechazar</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					
				</td>
				<td>
					<table align="center" border="0" width="100%" style="display:none;" id="divMotivo">
						<tr>
							<td>
								Motivo:
							</td>
							<td>
								<input type="text" id="motivo" name="motivo">
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td>
					<input type="button" value="Aceptar" onclick="changeWorkFlow();" id="btnAceptar">
				</td>
			</tr>
		</table>
		<iframe id='_hideSelect' class="hideSelect"></iframe>
    	<div id='_overlay' class="overlay"></div>
    	<div id='_load' class="load"><img src="<%=request.getContextPath()%>/resources/images/saving.gif" ></img></div>
	</form>
</body>
</html>