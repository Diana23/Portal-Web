<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-html-1.0" prefix="netui"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-databinding-1.0" prefix="netui-data"%>
<%@taglib uri="http://beehive.apache.org/netui/tags-template-1.0" prefix="netui-template"%>

<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="org.apache.commons.lang.LocaleUtils"%>
<netui:scriptContainer>
<jsp:directive.page import="org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils"/>
<jsp:directive.page import="org.apache.commons.lang.StringUtils"/>
<script type="text/javascript">
	var contextPath = "<%=request.getContextPath()%>";
</script>
<script type="text/javascript">
	var programacionId = '<netui:rewriteName name="programacion" forTagId="true" resultId="programacion"/>';
	var categoriaId = '<netui:rewriteName name="categoriaId" forTagId="true" resultId="categoriaId"/>';
</script>
<input type="hidden" value="${pageInput.cnlCategory}" id="<netui:rewriteName name="categoriaId" forTagId="true" resultId="categoriaId"/>" />
<div class="programacion" id="<netui:rewriteName name="programacion" forTagId="true" resultId="programacion"/>">
	<div class="programacion-title">
		<div style="text-align: left;">
		<h2 class="side ico-paq-prog padding-left45 margin-bot8">TV Programaci&oacute;n</h2>
		</div>
		<ul class="programacion-date-nav">
			<jsp:scriptlet>
				SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMMM 'de' yyyy", LocaleUtils.toLocale("es_MX"));
				Calendar cdate = Calendar.getInstance();
			</jsp:scriptlet>
			<li><a title="anteriores" id="atrasarFecha" class="programacion-arrow-ante-date hidden-text" href="#">Anteriores</a></li>
			<li><div id="muestraFecha"><a title="20 de Julio de 2010" class="programacion-middle-navdate color-bluedark " href="#"><strong><jsp:scriptlet>out.println(sdf.format(cdate.getTime()));</jsp:scriptlet></strong></a></div></li>
			<li><a title="siguientes" id="avanzarFecha" class="programacion-arrow-sigu-date hidden-text" href="#">siguientes</a></li>
		</ul>
		<div class="clear"></div>
	</div> 
	
	<div class="programacion-info tab-entre-head">
		<div class="filters padd-tb-small">
			<strong class="fix-2paddleft"> Buscar: </strong>
			<select class="date" style="width:100px;"></select>
			<select class="time" style="width:80px;"></select>
			<select class="categorias" style="width:140px;">
				<option value="" selected="selected">Todos los canales</option>
				<option value="Adults">Adultos</option>
				<option value="Comedy">Comedia</option>
				<option value="Variety">Cultural</option>
				<option value="Soccer">Deportes - Fútbol</option>
				<option value="Sports">Deportes - General</option>
				<option value="Documentary">Documental</option>
				<option value="Music">Música</option>
				<option value="News">Noticias</option>
				<option value="Romance">Romántico</option>
				<option value="Soap">Telenovela</option>
				<option value="Travel">Viajes</option>
			</select>
			<input type="button" class="boton" value="Obtener programaci&oacute;n" />
		</div>
		<div class="programacion-tags padd-tb-small">
			<p>
				<strong class="fix-1paddleft"> Filtrar por: </strong>
				<label>
				<input type="checkbox" value="Sports" /> <span class="deportes">Deportes</span> 
				</label>
				<label> 
				<input type="checkbox" value="Soap" /> <span class="telenovelas">Telenovelas</span>
				</label>
				<label> 
				<input type="checkbox" value="Spanish" /> <span class="peliculas">Peliculas</span>
				</label>
				<label> 
				<input type="checkbox" value="News" /> <span class="noticias">Noticias</span>
				</label>
			</p>
		</div>
	</div>
	
	<div class="programacion-content">
		
		<div class="programacion-nav clearfix">
			<div class="floatL canal">&nbsp;</div>
			<div class="floatL back"><netui:image src="/contentserver/groups/mercadotecnia/documents/imagen_cv/cv000757.jpg" styleClass="Anterior"/></div>
			<div class="floatL horas">
				<div class="floatL hora"><p>2:00 PM</p></div>
				<div class="floatL hora"><p>2:30 PM</p></div>
				<div class="floatL hora"><p>3:00 PM</p></div>
				<div class="floatL hora"><p>3:30 PM</p></div>
			</div>
			<div class="floatL next"><netui:image src="/contentserver/groups/mercadotecnia/documents/imagen_cv/cv000758.jpg" styleClass="Siguiente"/></div>
		</div>
		<div class="manto-blanco"></div>
		<div class="programacion-programas">
			<ul>
			</ul>
		</div>
	</div>
</div>
<script type="text/javascript">
	var that = this;
	var id= '#'+this.getJqId(programacionId);
	var categoria = $('#'+this.getJqId(categoriaId)).val();
	this.isOpen = false;
	this.startDate = new Date();
	
	var availableDays = 15;
		
	var min = this.startDate.getMinutes();
	if(min < 20) this.startDate.setMinutes(0);
	else if(min < 50)this.startDate.setMinutes(30);
	else{
		this.startDate.setMinutes(0);
		this.startDate.addHours(1);
	}
	
	this.manto = $(id+' .programacion-content .manto-blanco');
		
	$('#avanzarFecha').click(function(){
			var url = contextPath+'/com/cablevision/controller/programacion/findProgramacionByCategory.do';
			var time = $(id+' .programacion-info .filters .time');
			var selectedIndex = $(id+' .programacion-info .filters .date').attr("selectedIndex");
			if(selectedIndex < availableDays-1)
				$(id+' .programacion-info .filters .date').attr("selectedIndex", selectedIndex+1);
			else
				return false;
			var dates = $(id+' .programacion-info .filters .date');
			var categorias = $(id+' .programacion-info .filters .categorias');
			that.startDate = Date.parseExact(dates.val()+' '+time.val(),'MM-dd-yyyy HH:mm');
			if(categorias.val() == ''){
				url = contextPath+'/com/cablevision/controller/programacion/findProgramacion.do';
			}
			
			that.manto.removeClass('hidden');
			$.getJSON(url,{date:that.startDate.toString('dd-MM-yyyy'),time:that.startDate.toString('HH:mm'),category:categorias.val(),cnlCategory:categoria},function(data){
				that.createTime();
				that.data = data;//that.getTestData();
				that.createGrid();
				that.manto.addClass('hidden');
				var nuevaFecha = data.date;
				$('#muestraFecha').html('<a title="'+ nuevaFecha +'" class="programacion-middle-navdate color-bluedark " href="#"><strong>'+ nuevaFecha +'</strong></a>');
			});
			return false;
			
		});
		
		$('#atrasarFecha').click(function(){
			var url = contextPath+'/com/cablevision/controller/programacion/findProgramacionByCategory.do';
			var time = $(id+' .programacion-info .filters .time');
			var selectedIndex = $(id+' .programacion-info .filters .date').attr("selectedIndex");
			if(selectedIndex > 0)
				$(id+' .programacion-info .filters .date').attr("selectedIndex", selectedIndex-1);
			else
				return false;
			var dates = $(id+' .programacion-info .filters .date');
			var categorias = $(id+' .programacion-info .filters .categorias');
			that.startDate = Date.parseExact(dates.val()+' '+time.val(),'MM-dd-yyyy HH:mm');
			if(categorias.val() == ''){
				url = contextPath+'/com/cablevision/controller/programacion/findProgramacion.do';
			}
			
			that.manto.removeClass('hidden');
			$.getJSON(url,{date:that.startDate.toString('dd-MM-yyyy'),time:that.startDate.toString('HH:mm'),category:categorias.val(),cnlCategory:categoria},function(data){
				that.createTime();
				that.data = data;//that.getTestData();
				that.createGrid();
				that.manto.addClass('hidden');
				var nuevaFecha = data.date;
				$('#muestraFecha').html('<a title="'+ nuevaFecha +'" class="programacion-middle-navdate color-bluedark " href="#"><strong>'+ nuevaFecha +'</strong></a>');
			});
			return false;
			
		});
		
</script>
</netui:scriptContainer>