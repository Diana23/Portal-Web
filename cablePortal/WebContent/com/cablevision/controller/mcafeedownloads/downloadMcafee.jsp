<script type="text/javascript" src="<%=request.getContextPath()%>/framework/skins/cablevision/js/jquery.tooltip.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/framework/skins/cablevision/js/thickbox.js"></script>
<link href="<%=request.getContextPath()%>/framework/skins/cablevision/css/jquery.tooltip.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/framework/skins/cablevision/css/ui.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/framework/skins/cablevision/css/thickbox.css" type="text/css" rel="stylesheet">

	<a href="${pageContext.request.contextPath}/com/cablevision/controller/mcafeedownloads/infoMcafee.jsp?modal=true&height=450&width=500&inlineId=myOnPageContent" class="thickbox" style="visibility: hidden" id="linkAbrePopup"></a>			
						
<script type="text/javascript">
	$(document).ready(function(){
					$("#linkAbrePopup").removeAttr("style");
					//tb_init('a.thickbox, area.thickbox, input.thickbox'); al descomentar afecta al tamaño del popup, al parecer se llama automaticamente la funcion al declarar el class en el link. 
					$('#linkAbrePopup').trigger('click');
				});
				
				
	
</script>					
