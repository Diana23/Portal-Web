
	var init = function(options){
		var that = this;
		var id= '#'+this.getJqId(programacionId);
		var categoria = $('#'+this.getJqId(categoriaId)).val();
		this.isOpen = false;
		this.startDate = new Date();
		
		var min = this.startDate.getMinutes();
		if(min < 20) this.startDate.setMinutes(0);
		else if(min < 50)this.startDate.setMinutes(30);
		else{
			this.startDate.setMinutes(0);
			this.startDate.addHours(1);
		}
		
		this.createTime();
		this.createControls();
		this.manto = $(id+' .programacion-content .manto-blanco');
		
		$.getJSON(contextPath+'/com/cablevision/controller/programacion/findProgramacion.do',
			{date:this.startDate.toString('dd-MM-yyyy'),time:this.startDate.toString('HH:mm'),cnlCategory:categoria},function(data){
			that.data = data;
			that.createGrid();
			that.manto.addClass('hidden');
		});
		
		$(id+' .programacion-nav .next img').click(function(){
			that.startDate.addMinutes(60);
			that.manto.removeClass('hidden');
			$.getJSON(contextPath+'/com/cablevision/controller/programacion/findProgramacion.do',
			{date:that.startDate.toString('dd-MM-yyyy'),time:that.startDate.toString('HH:mm'),cnlCategory:categoria},function(data){
				that.createTime();
				that.data = data;//that.getTestData();
				that.createGrid();
				that.manto.addClass('hidden');
			});
		});
		$(id +' .programacion-nav .back img').click(function(){
			that.startDate.addMinutes(-60);
			that.manto.removeClass('hidden');
			$.getJSON(contextPath+'/com/cablevision/controller/programacion/findProgramacion.do',
			{date:that.startDate.toString('dd-MM-yyyy'),time:that.startDate.toString('HH:mm'),cnlCategory:categoria},function(data){
				that.createTime();
				that.data = data;
				that.createGrid();
				that.manto.addClass('hidden');
			});
		});
		
		$(id +' .programacion-info .filters .boton').click(function(){
			var url = contextPath+'/com/cablevision/controller/programacion/findProgramacionByCategory.do';
			var time = $(id+' .programacion-info .filters .time');
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
			
		});
		
		$(id+' .programacion-info .programacion-tags input').click(function(){
			that.toggleTags($(this));
		});
	};
	
	var toggleTags = function(chk){
		var css = chk.parent().find('span').attr('class');
		var id= '#'+this.getJqId(programacionId);
		var items = $(id+' .programacion-content .programacion-programas ul li .hora input.tags-txt[value*='+chk.val()+']');
		
		if(chk.is(':checked'))
			items.parent().addClass(css);
		else
			items.parent().removeClass(css);
	};
	
	var createTime = function(){
		var that = this;
		var ps = $('#'+this.getJqId(programacionId)+' .programacion-nav .horas .hora p');
		this.endDate = this.startDate.clone();
		ps.each(function(){
			var p = $(this);
			p.text(that.endDate.toString("hh:mm tt"));
			that.endDate.addMinutes(30);
		})
	};
	
	var createGrid = function(){
		var that = this;
		clearTimeout(that.hilo);
		var size = this.data.programas.length;
		var id= '#'+this.getJqId(programacionId);
		
		
		//GROUP BY CANAL
		var canales = new HashMap();
		for(var i=0;i<size;i++){
			var programa = this.data.programas[i];
			if(programa.isLast)break;
			if(canales.containsKey(programa.canal)){
				canales.get(programa.canal).push(programa);
			}else{
				canales.add(programa.canal,[programa]);
			}
			
		}
		
		//TAGS
		var tags = {
			deportes:false,
			telenovelas:false,
			peliculas:false,
			noticias:false,
			
			getSelected: function(tags){
				if(this.deportes && /Sports/.test(tags))return 'deportes';
				if(this.telenovelas && /Soap/.test(tags))return 'telenovelas';
				if(this.peliculas && /Spanish/.test(tags))return 'peliculas';
				if(this.noticias && /News/.test(tags))return 'noticias';
			}
		};
		
		$(id+' .programacion-info .programacion-tags input').each(function(){
			var chk = $(this);
			if(chk.is(':checked'))tags[chk.parent().find('span').attr('class')] = true;
		});

		//PRINT TABLE
		var j = 0;
		var keys = canales.getKeys();
		var programacion = $(id+' .programacion-content .programacion-programas ul');
		programacion.empty();
		this.isOpen = false;
		if(size<=1){
			programacion.append('<br/><strong>Su b&uacute;squeda no produjo resultado intente de nuevo.</strong>');
			return false;
		}
		
		setTimeout(fn,0);
		
		function fn(){
			var key = keys[j]; j++;
			var programas = canales.get(key);
			
			if(programas != undefined){
				
				var canal = programas[0];
			
				if(canal.logo!=''){
					if(canal.logo.charAt(0)!="/"){
						canal.logo = "/bcanales/www/img/canales/"+canal.logo;
					}
				}
				
				var html = '';
				html+= '<li class="clearfix"><div class="floatL canal">'+(canal.logo === ''?'<div class="img"></div>':'<img src="'+canal.logo+'" />')+'<p><strong>';
				html+= canal.canal;
				html+= '</strong><br/>';
				html+= canal.canalTitle;
				html+= '</p></div><div class="floatL"><div class="diagrama clearfix">';
				html+= that.addProgramas(programas,tags);
				html+= '</div><div class="description hidden"><p><Strong class="time-title">';
				html+= '12:00 PM - 14:30 PM'; //hora
				html+= '<br />Hoy</strong></p>'; //titulo
				html+= '<p class="description-text">'+canal.description+'</p>'; //descripcion
				html+= '</div></div></li>';
				
				programacion.append(html);
				
			}
			if(j < keys.length)that.hilo = setTimeout(fn,5);
		}
	};
	
	var addProgramas = function(programas,tags){
		var size = programas.length;
		var html = '',
			back='',
			next='',
			open='';
		for(var i=0;i<size;i++){
			var p = programas[i];
			var start = Date.parseExact(p.start.substring(0,16),'yyyy-MM-dd HH:mm');
			var end = Date.parseExact(p.end.substring(0,16),'yyyy-MM-dd HH:mm');

			if(end.compareTo(this.startDate)===1 && start.compareTo(this.startDate)===-1)back = "open back";
			
			open = end.compareTo(this.endDate)===1?'open':'';
			next = end.compareTo(this.endDate)===1?'next':'';
			
			html += '<div class="floatL hora '+open+' '+tags.getSelected(p.tags)+'" style="width:'+this.getSize(start,end)+';">'+
						'<input class="start-time" type="hidden" value="'+p.start+'" />'+
						'<input class="end-time" type="hidden" value="'+p.end+'" />'+
						'<input class="description-txt" type="hidden" value="'+p.description+'" />'+
						'<input class="tags-txt" type="hidden" value="'+p.tags+'" />'+
						'<p><a href="#" onclick="toggle(this);return false;">'+p.title+'</a>'+
					'</div>';
		}
		return '<div class="floatL empty '+back+'">&nbsp;</div><div class="floatL horas">'+html+'</div><div class="floatL empty '+next+' '+open+'" style="border-right:none;">&nbsp;</div>';
	};
	
	var getSize = function(start,end){
		if(end.compareTo(this.endDate)===1)end = this.endDate;
		if(start.compareTo(this.startDate)===-1)start = this.startDate;
		var diff = this.getMinutes(end) - this.getMinutes(start);
		return (100*diff/120)+'%';
	};
	
	var getMinutes = function(date){
		return date.getHours()*60 + date.getMinutes();
	};
	
	var getJqId = function(myid){ 
		return myid.replace(/:/g,"\\:").replace(/\./g,"\\.");
	};
	
	var createControls = function(){
		var time = $('#'+this.getJqId(programacionId)+' .programacion-info .filters .time');
		var dates = $('#'+this.getJqId(programacionId)+' .programacion-info .filters .date');
		
		for(var i=0;i<24;i++){
			time.append('<option value="'+(i<10?'0'+i:i)+':00">'+(i==0?'12':(i>12?i-12:i))+':00 '+(i<12?'am':'pm')+'</option>');
		}
		time.val(this.startDate.toString('HH')+':00');
		
		var day = this.startDate.clone();
		day.addDays(-2);
		for(i=0;i<15;i++){
			dates.append('<option value="'+day.toString('MM-dd-yyyy')+'">'+day.toString('ddd, MMM d')+'</option>');
			day.addDays(1);
		}
		dates.val(this.startDate.toString('MM-dd-yyyy'));
	};
	
	var toggle = function(element){
		var a = $(element);
		var description = a.parent().parent().parent().parent().parent().find('.description');
		var tab = a.parent().parent();
		var start = tab.find('.start-time').val();
		var end = tab.find('.end-time').val();
		var txt = tab.find('.description-txt').val();

		if(tab.hasClass('selected')){
			var that = this;
			this.actual.slideUp('slow',function(){
				that.tab.removeClass('selected');
				that.tab = null;
				that.description = null;
			});
		}else{
			if(this.actual)this.actual.hide();
			if(this.tab)this.tab.removeClass('selected');
			
			description.find('.time-title').html(start.substring(10,16)+' - '+end.substring(10,16)+'<br />'+a.text());
			description.find('.description-text').text(txt);
			
			tab.addClass('selected');
			description.slideDown('slow');
			this.actual = description;
			this.tab = tab;
		}
	};


var HashMap = function(){
	this.data = {_size:0}
	this.keys = [];
}

HashMap.prototype.add = function(key,obj){
	key = 'item_'+key.replace(/ /g,'');
	
	this.data[key] = obj;
	this.keys.push(key);
	this.data._size++;
};

HashMap.prototype.get = function(key){
	if(!/^item_/.test(key))key = 'item_'+key.replace(/ /g,'');
	return this.data[key];
};

HashMap.prototype.containsKey = function(key){
	if(key === undefined)return false;
	if(!/^item_/.test(key))key = 'item_'+key.replace(/ /g,'');
	return this.data[key];
};

HashMap.prototype.size = function(){
	return this.data._size;
};

HashMap.prototype.getKeys = function(){
	return this.keys;
};

$(function(){
	init();
});