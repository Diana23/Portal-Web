	//  ------------------------------------------------------------------------ //
//                             PopCalendar 1.4                               //
//                    Copyright (c) 2006 alquanto.de                         //
//                       <http://www.alquanto.de/>                           //
//  ------------------------------------------------------------------------ //
//  This program is free software; you can redistribute it and/or modify     //
//  it under the terms of the GNU Lesser General Public License as           //
//  published by the Free Software Foundation; either version 2.1            //
//  of the License, or (at your option) any later version.                   //
//                                                                           //
//  You may not change or alter any portion of this comment or credits       //
//  of supporting developers from this source code or any supporting         //
//  source code which is considered copyrighted (c) material of the          //
//  original comment or credit authors.                                      //
//                                                                           //
//  This program is distributed in the hope that it will be useful,          //
//  but WITHOUT ANY WARRANTY; without even the implied warranty of           //
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the            //
//  GNU Lesser General Public License for more details.                      //
//                                                                           //
//  You should have received a copy of the GNU Lesser General Public License //
//  along with this program; if not, write to the Free Software              //
//  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA //
//  ------------------------------------------------------------------------ //
//
// visit http://www.alquanto.de/goodies/popcalendar for updates
//
// TabSize: 4
//
// HISTORY
// 2007-09-04	ALQUANTO		<info at alquanto dot de>
//				* bug: divs in caption not floating left. fixed.
//				* add: function show now interpretes strings as control-params as document-ids
// 2007-04-30	Murat Purc		<murat at purc dot de>
//				* bug: calendar-table wasn't closed correctly, sometimes missing </tr>
// 2006-12-18	Tom Vonsild <tom at mouseketeers dot dk>
//				* bug: year was falsely calculated in IE6
// 2006-12-12	Tom Vonsild <tom at mouseketeers dot dk>
//				* new language: danish
// 2006-12-07	ALQUANTO		<info at alquanto dot de>
//				* new behaviour: don't hide selectboxes and applets in IE7
//				* new feature: show only one-letter-daynames with "showDayLetter = true"
// 2006-12-06	Davy Belmans
//				* fixed bug with Holiday-Records
// 2006-11-01	Joshua Mills
//				* fixed bug in enablePast-param
// 2006-10-23	Marco Savini
//				* fixed positioning glitches with IE7
// 2006-08-11	ALQUANTO		<info at alquanto dot de>
//				* fixed some language-strings (utf8-sequences)
// 2006-06-28	Marco Lupo Stanghellini <lupostanghellini at gmail dot com>
//				* new Language (italian, it)
// 2006-04-30	ALQUANTO		<info at alquanto dot de>
//				* update: most of the calendar are made of DIVs, all styles are now in stylesheet
//				* var "imgDir" renamed to "theme", because now you are able to have different themes for the calendar
//				* 'gfx/popcalendar.css' renamed to 'default/style.css'
// 2006-04-20	ALQUANTO		<info at alquanto dot de>
//				* update: new language: french
// 2006-04-19	ALQUANTO		<info at alquanto dot de>
//				* release...
//				* update: reimplementation of OO-calendar
//				* update: shorter function-names
//				* update: some global vars are not necessary anymore
//				* update: now you have to call the calendar via popCalendar.show()
//				* update: only one array is necessary for daynames. --> modified Array-Handling
// 2006-04-04	Engelke Eschner	<tekai at gmx dot li>
//				* popCalendar becomes a selfcontaining Object
//				* bugfix: fixed positioning to work with strict modes in IE/Moz (+'px')
//				* init() has a new parameter to optionally install event handlers to hide the calendar
// 2005-07-23	ALQUANTO		<info at alquanto dot de>
//				* release... some fixes, etc.
// 2005-04-30	Dariusz Markowicz	<dmarkowi at o2 dot pl>
//				* new Language (polish, pl)
// 2005-03-10	ALQUANTO		<info at alquanto dot de>
//				* all IDs in html are now prefixed with "pc"
//				* most CSS now is in external stylesheet
//				* optimized CSS
//				* no close-button
//				* new design: very small design
//				* optimized function: constructCalendar()
// 2005-03-08	ALQUANTO		<info at alquanto dot de>
//				* all IDs in HTML are now prefixed with "pcID"
//				* simplier CSS, no Font-Tags anymore
//				* no support for NS4 anymore (what the hell is that? :-) )
// 2004-09-03	sanjaya
//				* new Language (no)
//				* new feature: added time-boxes (just add "hh:xx:ss" to your dateformat in Showcalendar)
// 2003-07-30	ALQUANTO		<info at alquanto dot de>
//				* german language included
//				* modified languageLogic with the ISO-2letter-strings
//				* changes in in showCalendar: defaultLanguage is already set...
//				* js and html corrected... more xhtml-compliant... simplier css
// 2003-07-25	PinoToy			<pinotoy at yahoo dot com>
//				* new logic for multiple languages (English, Spanish and ready for more)
//				* changes in popUpMonth & popDownMonth methods for hiding popup
//				* changes in popDownYear & popDownYear methods for hidding popup
//				* new logic for disabling dates in the past
//				* new method showCalendar, dynamic configuration of language, enabling past & position
//				* changes in the styles
// 2001-12-02	Tan Ling Wee	<fuushikaden at yahoo dot com>
//				* initial version, thanx!

var popCalendar = {
	lg				: 'en',		// Default Language: en - english ; es - spanish; de - german; no - norwegian; pl - polish
	enablePast		: 0,		// 0 - disabled ; 1 - enabled
	fixedX			: -1,		// x position (-1 if to appear below control)
	fixedY			: -1,		// y position (-1 if to appear below control)
	startAt			: 1,		// 0 - sunday ; 1 - monday, ...
	showWeekNumber	: true,		// false - don't show; true - show
	showToday		: true,		// false - don't show; true - show
	showDayLetter	: false,		// false - use 2-3 letter day abbreviations; true - show only one letter for a day
	theme			: typeof theme == 'undefined' ? '../js/calendar/images' : theme,	// directory for images and styles ... e.g. var theme="default"
	hideElements	: false,	// InternetExplorer overlaps selectboxes and applets BEFORE the popCalendar. so hide these temporarily?

	gotoString			: {
		en : "Go To Current Month",
		es : "Ir al Mes Actual",
		da : "Gå til nuværende måned",
		de : "Gehe zu aktuellem Monat",
		no : "G\u00E5 til n\u00E5v\u00E6rende m\u00E5ned",
		nl : "Ga naar de huidige maand",
		pl : "Przejd¼ do aktualnego miesi±ca",
		it : "Vai al mese corrente",
		fr : "Atteindre ..." // argh...
	},

	todayString			: {
		en : "Today is",
		es : "Hoy es",
		da : "Idag er",
		de : "Heute ist",
		no : "Idag er",
		nl : "Vandaag is",
		pl : "Dzisiaj jest",
		it : "Oggi \u00E8",
		fr : "aujourd'hui"
	},

	weekShortString			: {
		en : "Wk",
		es : "Sem",
		da : "Uge",
		de : "KW",
		no : "Uke",
		nl : "wk",
		pl : "Tyg",
		it : "sett",
		fr : "Se"
	},

	weekString			: {
		en : "calendar week",
		es : "Sem",
		da : "Uge",
		de : "Kalenderwoche",
		no : "Uke",
		nl : "wk",
		pl : "Tyg",
		it : "Settimana",
		fr : "Semaine"
	},


	scrollLeftMessage	: {
		en : "Click to scroll to previous month. Hold mouse button to scroll automatically.",
		es : "Presione para pasar al mes anterior. Deje presionado para pasar varios meses.",
		da : "Klik for at hoppe til forrige måned. Hold museknappen nede, for at rulle automatisk.",
		de : "Klicken um zum vorigen Monat zu gelangen. Gedr\u00FCckt halten, um automatisch weiter zu scrollen.",
		no : "Klikk for \u00E5 g\u00E5 til forrige m\u00E5ned. Hold museknappen nede for \u00E5 bla fort tilbake.",
		nl : "Klik om naar de vorige maand te scrollen. Houdt de muis ingedrukt om automatisch te scrollen.",
		pl : "Kliknij aby przej¶æ do poprzedniego miesi±ca. Trzymaj wci¶niêty klawisz myszy aby przewijaæ automatycznie.",
		it : "Premere per passare al mese precedente. Tenere premuto per scorrere vari mesi.",
		fr : "Click to scroll to previous month. Hold mouse button to scroll automatically."
	},

	scrollRightMessage	: {
		en : "Click to scroll to next month. Hold mouse button to scroll automatically.",
		es : "Presione para pasar al siguiente mes. Deje presionado para pasar varios meses.",
		da : "Klik for at hoppe til næste måned. Hold museknappen nede, for at rulle automatisk.",
		de : "Klicken um zum n&aumlchsten Monat zu gelangen. Gedr\u00FCckt halten, um automatisch weiter zu scrollen.",
		no : "Klikk for \u00E5 g\u00E5 til neste m\u00E5ned. Hold museknappen nede for \u00E5 bla fort framover.",
		nl : "Klik om naar de volgende maand te scrollen. Houdt de muis  ingedrukt om automatisch te scrollen.",
		pl : "Kliknij aby przej¶æ do nastêpnego miesi±ca. Trzymaj wci¶niêty klawisz myszy aby przewijaæ automatycznie.",
		it : "Premere per passare al mese successivo. Tenere premuto per scorrere vari mesi.",
		fr : "Click to scroll to next month. Hold mouse button to scroll automatically."
	},

	selectMonthMessage	: {
		en : "Click to select a month.",
		es : "Presione para seleccionar un mes",
		da : "Klik for at vælge måned",
		de : "Klicken um Monat auszuw\u00E4hlen",
		no : "Klikk for \u00E5 velge m\u00E5ned",
		nl : "Klik om een maand te selecteren.",
		pl : "Kliknij aby wybraæ miesi±c.",
		it : "Premere per scegliere un mese.",
		fr : "Cliquez pour choisir le mois."
	},

	selectYearMessage	: {
		en : "Click to select a year.",
		es : "Presione para seleccionar un a\u00F1o",
		da : "Klik for at vælge år",
		de : "Klicken um Jahr auszuw\u00E4hlen",
		no : "Klikk for \u00E5 velge \u00E5r",
		nl : "Klik om een jaar te selecteren.",
		pl : "Kliknij aby wybraæ rok.",
		it : "Premere per scegliere un anno",
		fr : "Cliquez pour choisir l'ann\u00E9e."
	},

	selectDateMessage	: {					// do not replace [date], it will be replaced by date.
		en : "Select [date] as date.",
		es : "Seleccione [date] como fecha",
		da : "Vælg [date] som dato.",
		de : "W\u00E4hle [date] als Datum.",
		no : "Velg [dato] som dato",
		nl : "Selecteer [date] als datum.",
		pl : "Wybierz [date] jako datê.",
		it : "Selezionare [date] come data.",
		fr : "Select [date] as date."
	},

	monthName			: {
		en : new Array("January","February","March","April","May","June","July","August","September","October","November","December"),
		es : new Array("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"),
		da : new Array("Januar","Februar","Marts","April","Maj","Juni","Juli","August","September","Oktober","November","December"),
		de : new Array("Januar","Februar","M\u00E4rz","April","Mai","Juni","Juli","August","September","Oktober","November","Dezember"),
		no : new Array("Januar","Februar","Mars","April","Mai","Juni","Juli","August","September","Oktober","November","Desember"),
		nl : new Array("Januari","Februari","Maart","April","Mei","Juni","Juli","Augustus ","September","Oktober","November","December"),
		pl : new Array("Styczeń","Luty","Marzec","Kwiecień","Maj","Czerwiec","Lipiec","Sierpień","Wrzesień","Październik","Listopad","Grudzień"),
		it : new Array("Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno","Luglio","Agosto ","Settembre","Ottobre","Novembre","Dicembre"),
		fr : new Array("Janvier","F\u00E9vrier","Mars","Avril","Mai","Juin","Juillet","Août","Septembre","Octobre","Novembre","D\u00E9cembre")
	},

	monthNameAbbr		: {
		en : new Array("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"),
		es : new Array("Ene","Feb","Mar","Abr","May","Jun","Jul","Ago","Sep","Oct","Nov","Dic"),
		da : new Array("Jan","Feb","Mar","Apr","Maj","Jun","Jul","Aug","Sep","Okt","Nov","Dec"),
		de : new Array("Jan","Feb","Mrz","Apr","Mai","Jun","Jul","Aug","Sep","Okt","Nov","Dez"),
		no : new Array("Jan","Feb","Mar","Apr","Mai","Jun","Jul","Aug","Sep","Okt","Nov","Des"),
		nl : new Array("Jan","Feb","Mrt","Apr","Mei","Jun","Jul","Aug","Sep","Okt","Nov","Dec"),
		pl : new Array("Sty","Lut","Mar","Kwi","Maj","Cze","Lip","Sie","Wrz","Paź","Lis","Gru"),
		it : new  Array("Gen","Feb","Mar","Apr","Mag","Giu","Lug","Ago","Set","Ott","Nov", "Dic"),
		fr : new Array("Jan","Fev","Mar","Avr","Mai","Juin","Juil","Aout","Sep","Oct","Nov","Dec")
	},

	dayName				: {
		en : new Array("Sun","Mon","Tue","Wed","Thu","Fri","Sat"),
		es : new Array("Dom","Lun","Mar","Mie","Jue","Vie","Sab"),
		da : new Array("Søn","Man","Tirs","Ons","Tors","Fre","Lør"),
		de : new Array("So","Mo","Di","Mi","Do","Fr","Sa"),
		no : new Array("S\u00F8n","Man","Tir","Ons","Tor’","Fre","L\u00F8r"),
		nl : new Array("Zo","Ma","Di","Wo","Do","Vr","Za"),
		pl : new Array("Nie","Pn","Wt","Śr","Cz","Pt","So"),
		it : new Array("Dom","Lun","Mar","Mer","Gio","Ven","Sab"),
		fr : new Array("Dim","Lun","Mar","Mer","Jeu","Ven","Sam")
	},


	o: null, om: null, oy: null, monthSelected: null, yearSelected: null, dateSelected: null, omonthSelected: null, oyearSelected: null, odateSelected: null, yearConstructed: null, intervalID1: null, intervalID2: null, timeoutID1: null, timeoutID2: null, ctlToPlaceValue: null, ctlNow: null, dateFormat: null, nStartingYear: null, curX: 0, curY: 0,
	visYear: 0, visMonth: 0,
	bPageLoaded	: false,
	ie			: (/msie/i.test(navigator.userAgent) && !/opera/i.test(navigator.userAgent)),
	ie7			: (/msie 7/i.test(navigator.userAgent)),
	dom			: document.getElementById,
	today		: new Date(),
	dateNow		: null,
	monthNow	: null,
	yearNow		: null,
	bShow		: false,

	// hides <select> and <applet> objects (for IE only)
	hideElement: function(elmID, overDiv) {
		if(this.ie && !this.ie7) {
			for(var i = 0; i < document.all.tags( elmID ).length; i++) {
				var obj = document.all.tags( elmID )[i];
				if(!obj || !obj.offsetParent) continue;

				// Find the element's offsetTop and offsetLeft relative to the BODY tag.
				objLeft		= obj.offsetLeft;
				objTop		= obj.offsetTop;
				objParent	= obj.offsetParent;

				while(objParent.tagName.toUpperCase() != 'BODY') {
					objLeft	+= objParent.offsetLeft;
					objTop	+= objParent.offsetTop;
					objParent= objParent.offsetParent;
				}

				objHeight = obj.offsetHeight;
				objWidth  = obj.offsetWidth;

				if((overDiv.offsetLeft + overDiv.offsetWidth) <= objLeft);
				else if((overDiv.offsetTop + overDiv.offsetHeight) <= objTop);
				else if(overDiv.offsetTop >= (objTop + objHeight + obj.height));
				else if(overDiv.offsetLeft >= (objLeft + objWidth));
				else {
					obj.style.visibility = 'hidden';
				}
			}
		}
	},

	// unhides <select> and <applet> objects (for IE only)
	showElement: function(elmID) {
		if(this.ie && !this.ie7) {
			for(var i = 0; i < document.all.tags( elmID ).length; i++) {
				var obj = document.all.tags(elmID)[i];
				if(!obj || !obj.offsetParent) continue;
				obj.style.visibility = '';
			}
		}
	},

	// helper-functions:
	getLeft: function(l) {
		if (l.offsetParent) return (l.offsetLeft + this.getLeft(l.offsetParent));
		else return (l.offsetLeft);
	},

	getTop: function(l) {
		if (l.offsetParent) return (l.offsetTop + this.getTop(l.offsetParent));
		else return (l.offsetTop);
	},


	/**
	 * Initialize the calendar. This Function must be called before show()
	 * @param boolean installEventHandlers install event handlers to hide the calendar
	 */
	init: function(installEventHandlers) {
		this.dateNow	= this.today.getDate();
		this.monthNow	= this.today.getMonth();
		this.yearNow	= this.today.getYear();

		if (this.dom) {
			document.write('<div onclick="popCalendar.bShow=true" id="pcIDcalendar" style="visibility:hidden"><div id="pcIDcaption" unselectable="on"></div><div id="pcIDcontent">.</div>');
			if (this.showToday) document.write ('<div id="pcIDfooter"></div>');
			document.write('</div><div id="pcIDselectMonth"></div><div id="pcIDselectYear"></div>');
		}

		if (!this.ie && !this.ie7) this.yearNow += 1900;

		this.o = (this.dom) ? document.getElementById('pcIDcalendar') : this.ie? document.all.pcIDcalendar : document.pcIDcalendar;
		popCalendar.hide();
		this.om = (this.dom) ? document.getElementById('pcIDselectMonth') : this.ie ? document.all.pcIDselectMonth : document.pcIDselectMonth;
		this.oy = (this.dom) ? document.getElementById('pcIDselectYear') : this.ie ? document.all.pcIDselectYear : document.pcIDselectYear;
		this.yearConstructed = false;

		var s = '<div id="pcIDleft"><a href="javascript:void(0)" onclick="popCalendar.decMonth()" onmouseover="window.status=\''+this.scrollLeftMessage[this.lg]+ '\'" onmouseout="clearInterval(popCalendar.intervalID1);window.status=\'\'" onmousedown="clearTimeout(popCalendar.timeoutID1);popCalendar.timeoutID1=setTimeout(\'popCalendar.StartDecMonth()\',500)" onmouseup="clearTimeout(popCalendar.timeoutID1);clearInterval(popCalendar.intervalID1)">&nbsp;&nbsp;&nbsp;</a></div>';
		s    += '<div id="pcIDright"><a href="javascript:void(0)" onclick="popCalendar.incMonth()" onmouseover="window.status=\''+this.scrollRightMessage[this.lg]+'\'" onmouseout="clearInterval(popCalendar.intervalID1);window.status=\'\'" onmousedown="clearTimeout(popCalendar.timeoutID1);popCalendar.timeoutID1=setTimeout(\'popCalendar.StartIncMonth()\',500)" onmouseup="clearTimeout(popCalendar.timeoutID1);clearInterval(popCalendar.intervalID1)">&nbsp;&nbsp;&nbsp;</a></div>';
		s    += '<div id="pcIDMonth" onmouseover="window.status=\''+this.selectMonthMessage[this.lg]+'\'" onmouseout="window.status=\'\'" onclick="popCalendar.popUpMonth()"></div>';
		s    += '<div id="pcIDYear"  onmouseover="window.status=\''+this.selectYearMessage[this.lg]+ '\'" onmouseout="window.status=\'\'" onclick="popCalendar.popUpYear()"></div>&nbsp;';
		document.getElementById('pcIDcaption').innerHTML = s;

		if (!installEventHandlers) {				// hide calendar when enter has been pressed
			document.onkeypress = function (event) { 				
				if (!event) event = window.event;
				if (event.keyCode == 27) popCalendar.hide();
			};
			document.onclick = function () {		// hide calendar when ...
				if (!popCalendar.bShow) popCalendar.hide();
				popCalendar.bShow = false;
			};
		}
		document.write('<link rel="stylesheet" href="'+this.theme+'/style.css" type="text/css" media="screen" />');
		this.bPageLoaded=true;
	},

	hide: function() {
		this.o.style.visibility = 'hidden';
		if (this.om != null) this.om.style.visibility = 'hidden';
		if (this.oy != null) this.oy.style.visibility = 'hidden';
		if (this.hideElements) {
			this.showElement('SELECT');
			this.showElement('APPLET');
		}
	},

	// holidays...
	HolidaysCounter: 0,
	Holidays: new Array(),
	HolidayRec: function(d, m, y, desc) {
		this.d = d; this.m = m; this.y = y; this.desc = desc;
	},
	addHoliday: function(d, m, y, desc) {
		this.Holidays[this.HolidaysCounter++] = new this.HolidayRec (d, m, y, desc);
	},

	padZero: function(num) {
		return (num < 10) ? '0' + num : num;
	},

	constructDate: function(d,m,y) {
		var s = this.dateFormat;
		s = s.replace('dd','<e>');
		s = s.replace('d','<d>');
		s = s.replace('<e>',this.padZero(d));
		s = s.replace('<d>',d);
		s = s.replace('mmmm','<p>');
		s = s.replace('mmm','<o>');
		s = s.replace('mm','<n>');
		s = s.replace('m','<m>');
		s = s.replace('<m>',m+1);
		s = s.replace('<n>',this.padZero(m+1));
		s = s.replace('<o>',this.monthName[this.lg][m]);
		s = s.replace('<p>',this.monthNameAbbr[this.lg][m]);
		s = s.replace('yyyy',y);
		s = s.replace('yy',this.padZero(y%100));
		s = s.replace('hh',this.hour);
		s = s.replace('xx',this.minute);
		s = s.replace('ss',this.second);
		return s.replace ('yy',this.padZero(y%100));
	},


	close: function() {
		this.hide();
		this.ctlToPlaceValue.value = this.constructDate(this.dateSelected, this.monthSelected, this.yearSelected);
		TicketReports();
},


	// Month Pulldown
	StartDecMonth: function() {
		this.intervalID1 = setInterval('popCalendar.decMonth()',80);
	},
	StartIncMonth: function() {
		this.intervalID1 = setInterval('popCalendar.incMonth()',80);
	},
	incMonth: function() {
		this.monthSelected++;
		if (this.monthSelected > 11) {this.monthSelected = 0; this.yearSelected++;}
		this.construct();
	},
	decMonth: function() {
		this.monthSelected--;
		if (this.monthSelected < 0) {this.monthSelected = 11; this.yearSelected--;}
		this.construct();
	},
	constructMonth: function() {
		this.popDownYear();
		var s = '';
		for (i=0; i<12; i++) {
			var sName = this.monthName[this.lg][i];
			if (i == this.monthSelected) sName = '<strong>' + sName + '</strong>';
			s += '<li><a href="javascript:void(0)" onclick="popCalendar.monthSelected=' + i + ';popCalendar.construct();popCalendar.popDownMonth();event.cancelBubble=true">' + sName + '</a></li>';
		}
		this.om.innerHTML = '<ul onmouseover="clearTimeout(popCalendar.timeoutID1)" onmouseout="clearTimeout(popCalendar.timeoutID1);popCalendar.timeoutID1=setTimeout(\'popCalendar.popDownMonth()\',100);event.cancelBubble=true">' + s + '</ul>';
	},
	popUpMonth: function() {
		var leftOffset;
		if (this.visMonth == 1) {
			this.popDownMonth();
			this.visMonth--;
		} else {
			this.constructMonth();
			this.om.style.visibility = (this.dom||this.ie) ? 'visible' : 'show';
			leftOffset = parseInt(this.o.style.left) + document.getElementById('pcIDMonth').offsetLeft;
			if (this.ie) leftOffset += 1;
			this.om.style.left = leftOffset + 'px';
			this.om.style.top = parseInt(this.o.style.top) + 19 + 'px';
			if (this.hideElements) {
				this.hideElement('SELECT', this.om);
				this.hideElement('APPLET', this.om);
			}
			this.visMonth++;
		}
	},
	popDownMonth: function() {
		this.om.style.visibility = 'hidden';
		this.visMonth = 0;
	},


	// Year Pulldown
	incYear: function() {
		for (var i=0; i<7; i++) {
			var newYear = (i + this.nStartingYear) + 1;
			document.getElementById('pcY'+i).innerHTML = (newYear == this.yearSelected) ? '<strong>'+newYear+'</strong>' : newYear;
		}
		this.nStartingYear++; this.bShow=true;
	},
	decYear: function() {
		for (var i=0; i<7; i++) {
			var newYear = (i + this.nStartingYear) - 1;
			document.getElementById('pcY'+i).innerHTML = (newYear == this.yearSelected) ? '<strong>'+newYear+'</strong>' : newYear;
		}
		this.nStartingYear--; this.bShow=true;
	},
	selectYear: function(nYear) {
		this.yearSelected = parseInt(nYear + this.nStartingYear);
		this.yearConstructed = false;
		this.construct();
		this.popDownYear();
	},
	constructYear: function() {
		this.popDownMonth();
		var s = '';
		if (!this.yearConstructed) {
			s = '<li><a href="javascript:void(0)" onmouseout="clearInterval(popCalendar.intervalID1);" onmousedown="clearInterval(popCalendar.intervalID1);popCalendar.intervalID1=setInterval(\'popCalendar.decYear()\',30)" onmouseup="clearInterval(popCalendar.intervalID1)">-</a></li>';
			var j = 0;
			this.nStartingYear = this.yearSelected - 3;
			for ( var i = (this.yearSelected-3); i <= (this.yearSelected+3); i++ ) {
				var sName = i;
				if (i == this.yearSelected) sName = '<strong>' + sName + '</strong>';
				s += '<li><a href="javascript:void(0)" id="pcY' + j + '" onclick="popCalendar.selectYear('+j+');event.cancelBubble=true">' + sName + '</a></li>';
				j++;
			}
			s += '<li><a href="javascript:void(0)" onmouseout="clearInterval(popCalendar.intervalID2);" onmousedown="clearInterval(popCalendar.intervalID2);popCalendar.intervalID2=setInterval(\'popCalendar.incYear()\',30)" onmouseup="clearInterval(popCalendar.intervalID2)">+</a></li>';
			this.oy.innerHTML = '<ul onmouseover="clearTimeout(popCalendar.timeoutID2)" onmouseout="clearTimeout(popCalendar.timeoutID2);popCalendar.timeoutID2=setTimeout(\'popCalendar.popDownYear()\',100)">' + s + '</ul>';

			this.yearConstructed = true;
		}
	},
	popDownYear: function() {
		clearInterval(this.intervalID1);
		clearTimeout(this.timeoutID1);
		clearInterval(this.intervalID2);
		clearTimeout(this.timeoutID2);
		this.oy.style.visibility= 'hidden';
		this.visYear = 0;
	},
	popUpYear: function() {
		var leftOffset;
		if (this.visYear==1) {
			this.popDownYear();
			this.visYear--;
		} else {
			this.constructYear();
			this.oy.style.visibility = (this.dom||this.ie) ? 'visible' : 'show';
			leftOffset = parseInt(this.o.style.left) + document.getElementById('pcIDYear').offsetLeft;
			if (this.ie) leftOffset += 1;
			this.oy.style.left = leftOffset + 'px';
			this.oy.style.top = parseInt(this.o.style.top) + 19 + 'px';
			this.visYear++;
		}
	},

	// calendar
	WeekNbr: function(n) {
		// Algorithm used from Klaus Tondering's Calendar document (The Authority/Guru)
		// http://www.tondering.dk/claus/calendar.html

		if (n == null) n = new Date (this.yearSelected,this.monthSelected,1);
		var year = n.getFullYear();
		var month = n.getMonth() + 1;
		if (this.startAt == 0) {
			var day = n.getDate() + 1;
		} else {
			var day = n.getDate();
		}

		var a = Math.floor((14-month) / 12);
		var y = year + 4800 - a;
		var m = month + 12 * a - 3;
		var b = Math.floor(y/4) - Math.floor(y/100) + Math.floor(y/400);
		var J = day + Math.floor((153 * m + 2) / 5) + 365 * y + b - 32045;
		var d4 = (((J + 31741 - (J % 7)) % 146097) % 36524) % 1461;
		var L = Math.floor(d4 / 1460);
		var d1 = ((d4 - L) % 365) + L;
		var week = Math.floor(d1/7) + 1;
		return week;
	},

	construct : function() {
		var aNumDays = Array (31,0,31,30,31,30,31,31,30,31,30,31);
		var startDate = new Date (this.yearSelected,this.monthSelected,1);
		var endDate;

		if (this.monthSelected==1) {	// get days of February
			endDate = new Date (this.yearSelected,this.monthSelected+1,1);
			endDate = new Date (endDate - (24*60*60*1000));
			var numDaysInMonth = endDate.getDate();
		} else {
			var numDaysInMonth = aNumDays[this.monthSelected];
		}

		var dayPointer = startDate.getDay() - this.startAt;

		if (dayPointer<0) dayPointer = 6;

		var s = '<table><thead><tr>';	// dayheader

		if (this.showWeekNumber) {								// spacer for Weeknumbers
			s += '<th><acronym title="'+this.weekString[this.lg]+'">' + this.weekShortString[this.lg] + '</acronym></th>';
			// s += '<th>&nbsp;</th>';
		}

		for (var i = 0; i<7; i++) {								// render daynames
			if (this.showDayLetter)
				s += '<th>' + this.dayName[this.lg][(i+this.startAt) % 7].charAt(0) + '</th>';
			else
				s += '<th>' + this.dayName[this.lg][(i+this.startAt) % 7] + '</th>';
		}


		s += '</tr></thead><tbody><tr>';

		if (this.showWeekNumber) {
			s += '<td class="pcWeekNumber">' + this.WeekNbr(this.startDate) + '</td>';
		}

		for (var i=1; i<=dayPointer;i++ ) {
			s += '<td>&nbsp;</td>';
		}

		for (var datePointer=1; datePointer <= numDaysInMonth; datePointer++) {
			dayPointer++;
			var sClass = 'pctd ';
			var selDayAction = '';
			var sHint = '';

			for (var k=0; k < this.HolidaysCounter; k++) {	// insert holidays
				if ((parseInt(this.Holidays[k].d) == datePointer)&&(parseInt(this.Holidays[k].m) == (this.monthSelected+1))) {
					if ((parseInt(this.Holidays[k].y)==0)||((parseInt(this.Holidays[k].y)==this.yearSelected)&&(parseInt(this.Holidays[k].y)!=0))) {
						sClass = 'pcDayHoliday ';
						sHint += sHint=="" ? this.Holidays[k].desc : "\n"+this.Holidays[k].desc;
					}
				}
			}
			sHint = sHint.replace('/\"/g', '&quot;');

			if ((datePointer == this.odateSelected) && (this.monthSelected == this.omonthSelected) && (this.yearSelected == this.oyearSelected)) {
				sClass+='pcDaySelected';				// selected day
			} else if ((datePointer == this.dateNow) && (this.monthSelected == this.monthNow) && (this.yearSelected == this.yearNow)) {
				sClass+='pcToday';						// today
			} else if (
				(dayPointer % 7 == (this.startAt * -1)+1)
				|| ((dayPointer % 7 == (this.startAt * -1)+7 && this.startAt==1) || (dayPointer % 7 == this.startAt && this.startAt==0)) )
			{
				sClass+='pcWeekend';					// sunday
			} else {
				sClass+='pcDay';						// every other day
			}

			if (this.enablePast == 0 &&
				((this.yearSelected < this.yearNow) ||
				(this.monthSelected < this.monthNow) &&
				(this.yearSelected == this.yearNow) ||
				(datePointer < this.dateNow) &&
				(this.monthSelected == this.monthNow) &&
				(this.yearSelected == this.yearNow))) {
				sClass+='Past';							// all CSS-classes with past-style are suffixed with "Past":
			} else {
				selDayAction = 'href="javascript:popCalendar.dateSelected='+datePointer+';popCalendar.close();"';
			}

			// create HTML:
			s += '<td class="' + sClass + '"><a onmousemove="window.status=\'' + this.selectDateMessage[this.lg].replace('[date]',this.constructDate(datePointer,this.monthSelected,this.yearSelected)) + '\'" onmouseout="window.status=\'\'" title="'+sHint+'" '+selDayAction+'>'+datePointer+'</a></td>';

			if ((dayPointer+this.startAt) % 7 == this.startAt) {
				s += '</tr>';
				if (datePointer < numDaysInMonth) {
					s += '<tr>';						// open next table row, if we are not at the and of actual month
					if (this.showWeekNumber) {
						s += '<td class="pcWeekNumber">' + (this.WeekNbr(new Date(this.yearSelected,this.monthSelected,datePointer+1))) + '</td>';
					}
				}
			}
		}

		if (dayPointer % 7 != 0) s += '</tr>';			// close last opened table row
		s+='</tbody></table>';

		document.getElementById('pcIDcontent').innerHTML = s;
		document.getElementById('pcIDMonth').innerHTML   = '<a href="javascript:void(0)">' + this.monthName[this.lg][this.monthSelected] + '</a>';
		document.getElementById('pcIDYear').innerHTML    = '<a href="javascript:void(0)">' + this.yearSelected + '</a>';
	},

	/**
	 * Main function, shows the calendar.
	 *
	 * @param ctl1 The "button" which (de-)activates the calendar
	 * @param ctl2 The field to receive the selected date
	 * @param format Format of the date-string, see constructDate()
	 * @param lang Language of the calendar (en, es, de, no, nl)
	 * @param past Are dates in the past allowed? yes=1, no=0
	 * @param fx x-position of the calendar, -1 for directly below ctl1
	 * @param fy y-position of the calendar, -1 for directly below ctl1
	 * @param start Start of the week is on Monday (=1) or Sunday (=0)
	 */
	show: function(ctl, ctl2, format, lang, past, fx, fy, start) {
		if (start != null && (start==0 || start==1)) this.startAt = start;
		if (lang != null && lang != '') this.lg = lang;
		this.enablePast = (past != null) ? past : this.enablePast;
		this.fixedX = (fx != null) ? fx : -1;
		this.fixedY = (fy != null) ? fy : -1;
		if (this.showToday)
			document.getElementById('pcIDfooter').innerHTML = this.todayString[this.lg]+' <a onmousemove="window.status=\''+this.gotoString[this.lg]+'\'" onmouseout="window.status=\'\'" title="'+this.gotoString[this.lg]+'" href="javascript:popCalendar.monthSelected=popCalendar.monthNow;popCalendar.yearSelected=popCalendar.yearNow;popCalendar.construct();">'+ this.dayName[this.lg][(this.today.getDay()) % 7]+', '+this.dateNow+' '+this.monthNameAbbr[this.lg][this.monthNow]+' '+this.yearNow+'</a>';
		this.popUp(ctl, ctl2, format);
	},

	popUp: function(ctl, ctl2, format) {
		var leftpos = 0;
		var toppos	= 0;
		var formatChar = '';
		var aFormat = new Array();
		if (typeof(ctl1)=='string') ctl1 = document.getElementById(ctl1);
		if (typeof(ctl2)=='string') ctl2 = document.getElementById(ctl2);

		if (this.bPageLoaded) {
			if (this.o.style.visibility == 'hidden') {
				this.ctlToPlaceValue = ctl2;
				this.dateFormat = format;
				formatChar = ' ';
				aFormat = this.dateFormat.split(formatChar);
				if (aFormat.length < 3) {
					formatChar = '/';
					aFormat = this.dateFormat.split(formatChar);
					if (aFormat.length < 3) {
						formatChar = '.';
						aFormat = this.dateFormat.split(formatChar);
						if (aFormat.length < 3) {
							formatChar = '-';
							aFormat = this.dateFormat.split(formatChar);
							if (aFormat.length < 3) {
								formatChar = '';					// invalid date format
							}
						}
					}
				}

				var tokensChanged = 0;
				var aData;
				if (formatChar != "") {
					aData = ctl2.value.split(formatChar);			// use user's date

					for (var i=0; i<3; i++) {
						if ((aFormat[i] == "d") || (aFormat[i] == "dd")) {
							this.dateSelected = parseInt(aData[i], 10);
							tokensChanged++;
						} else if ((aFormat[i] == "m") || (aFormat[i] == "mm")) {
							this.monthSelected = parseInt(aData[i], 10) - 1;
							tokensChanged++;
						} else if (aFormat[i] == "yyyy") {
							this.yearSelected = parseInt(aData[i], 10);
							tokensChanged++;
						} else if (aFormat[i] == "mmm") {
							for (j=0; j<12; j++) {
								if (aData[i] == monthName[lg][j]) {
									this.monthSelected=j;
									tokensChanged++;
								}
							}
						} else if (aFormat[i] == "mmmm") {
							for (j=0; j<12; j++) {
								if (aData[i] == monthNameAbbr[lg][j]) {
									this.monthSelected = j;
									tokensChanged++;
								}
							}
						}
					}
				}
				var TimeFormatChar = ':';
				var timeString = ctl2.value.split(" ");
				if (timeString[1] !=null) {
					var timeTokens = timeString[1].split(':');
					if(timeTokens[0].length==2) {
						this.hour = timeTokens[0];
					}
					if (timeTokens[1].length==2) {
						this.minute = timeTokens[1];
					}
					if (timeTokens[2].length==2) {
						this.second= timeTokens[2];
					}
				} else {
					this.hour=00;
					this.minute=00;
					this.second=00;
				}

				if ((tokensChanged != 3) ||
					isNaN(this.dateSelected) ||
					isNaN(this.monthSelected) ||
					this.monthSelected<0 ||
					isNaN(this.yearSelected)) {
					this.dateSelected  = this.dateNow;
					this.monthSelected = this.monthNow;
					this.yearSelected  = this.yearNow;
				}

				this.odateSelected  = this.dateSelected;
				this.omonthSelected = this.monthSelected;
				this.oyearSelected  = this.yearSelected;

				leftpos = this.getLeft(ctl.offsetParent);
				toppos = this.getTop(ctl.offsetParent);

				this.o.style.left = (this.fixedX == -1) ? ctl.offsetLeft + leftpos + 'px' : this.fixedX + 'px';
				this.o.style.top = (this.fixedY == -1) ? ctl.offsetTop + toppos + ctl.offsetHeight + 2 + 'px' : this.fixedY + 'px' ;
				this.construct (1, this.monthSelected, this.yearSelected);
				this.o.style.visibility = (this.dom||this.ie) ? "visible" : "show";
				if (this.hideElements) {
					this.hideElement('SELECT', document.getElementById('pcIDcalendar'));
					this.hideElement('APPLET', document.getElementById('pcIDcalendar'));
				}
				this.bShow = true;
			} else {
				popCalendar.hide();
				if (this.ctlNow!=ctl) this.popUp(ctl, ctl2, format);
			}
			this.ctlNow = ctl;
		}
	}
}

popCalendar.init();
