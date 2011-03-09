package com.cablevision.controller.entretenimiento;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.rpc.ServiceException;

import org.apache.beehive.netui.pageflow.Forward;
import org.apache.beehive.netui.pageflow.PageFlowUtils;
import org.apache.beehive.netui.pageflow.annotations.Jpf;
import org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.struts.upload.FormFile;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.bea.portlet.GenericURL;
import com.bea.portlet.PageURL;
import com.cablevision.ToInterfase;
import com.cablevision.carga.CargaExcel;
import com.cablevision.controller.base.ControllerBase;
import com.cablevision.util.ConfigurationHelper;
import com.cablevision.util.Constantes;
import com.cablevision.util.MailUtil;
import com.cablevision.util.ResponseToGetPPV;
import com.cablevision.util.ResponseToPurchasePPV;
import com.cablevision.util.RespuestaToMyAccount;
import com.cablevision.util.SolrHelper;

//Substitute with this annotation if nested pageflow
//@Jpf.Controller( nested=true )

@Jpf.Controller(messageBundles = {
		@Jpf.MessageBundle(bundlePath = "com.cablevision.controller.ondemand.ondemand", bundleName = "ondemand"),
		@Jpf.MessageBundle(bundlePath = "com.cablevision.carga.carga", bundleName = "carga"),
		@Jpf.MessageBundle(bundlePath = "com.cablevision.controller.entretenimiento.entretenimiento", bundleName = "entretenimiento")}, simpleActions = {
		@Jpf.SimpleAction(name = "mostrarOnDemandEntretenimiento", path = "ondemand/ruletasOndemand.jsp"),
		@Jpf.SimpleAction(name = "ruletaSimpleOnDemand", path = "ondemand/ruletaSimpleOndemand.jsp"),
		@Jpf.SimpleAction(name = "mostrarHDEntretenimiento", path = "../cablevisionhd/ruletasHD.jsp"),
		@Jpf.SimpleAction(name = "ruletaSimpleHD", path = "../cablevisionhd/ruletaSimpleHD.jsp"),
		@Jpf.SimpleAction(name = "mostrarPPVEntretenimiento", path = "ppv/ruletasPPV.jsp"),
		@Jpf.SimpleAction(name = "ruletaSimplePPV", path = "ppv/ruletaSimplePPV.jsp"),
		@Jpf.SimpleAction(name = "masVistosOndemand", path = "sidebar.jsp"),
		@Jpf.SimpleAction(name = "masVistosPPV", path = "ppv/sidebar.jsp"),
		@Jpf.SimpleAction(name = "masVistosHD", path = "../cablevisionhd/sidebar.jsp"),
		@Jpf.SimpleAction(name = "ruletaSimpleTodos", path = "ruletaSimpleTodos.jsp") }, multipartHandler = Jpf.MultipartHandler.memory)
public class EntretenimientoController extends ControllerBase {
	private static final long serialVersionUID = 1L;
	transient ToInterfase vitriaClient;

	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "ruleta.jsp") })
	protected Forward begin(EntretenimientoFormBean form) throws Exception {
		if (StringUtils.isBlank(form.getTipo())
				|| StringUtils.isNotEmpty((String) getRequest().getAttribute(
						"todos"))) {
			llenarDeRequest(form);
		}

		Forward forward = new Forward("success");
		forward.addActionOutput("ruleta", SolrHelper.query(
				"tipo:" + form.getTipo() + " AND " + form.getTipoRuleta()
						+ ":true").getResults());
		setCategorias(forward, form.getTipo(),form.getTipoRuleta());

		// si el parametro todos viene significa que es el portlet de
		// progrmacion, por lo cual se amnda un parametro
		// para que no se muestre el link de -ver programacion completa-
		forward.addActionOutput("todos", (String) getRequest().getAttribute(
				"todos"));

		forward.addOutputForm(form);
		
		String pageLabel = getMessageResources("entretenimiento").getMessage("entretenimiento."+form.getTipo()+".verprogramacion.pageLabel");
		
		if(StringUtils.isNotBlank(pageLabel)){
			PageURL url = PageURL.createPageURL(getRequest(), getResponse(), pageLabel);
			url.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false");
			url.setTemplate("defaultDesktop");
			forward.addActionOutput("url", url.toString());
		}

		return forward;
	}

	private void llenarDeRequest(EntretenimientoFormBean form) {
		form.setCategoria((String[]) getRequest().getAttribute("categoria"));
		form.setId((String) getRequest().getAttribute("id"));
		form.setTipo((String) getRequest().getAttribute("tipo"));
		form
				.setTipoMasVisto((String) getRequest().getAttribute(
						"tipoMasVisto"));
		form.setTipoRuleta((String) getRequest().getAttribute("tipoRuleta"));
	}

	/**
	 * Pone las categorias en en actionOutput
	 * 
	 * @param forward
	 *            El forward donde se pondran las categorias
	 * @param tipo
	 *            Tipo del cual se quieren las categorias, ondemand,ppv,hd
	 */
	private void setCategorias(Forward forward, String tipo, String tipoRuleta) {
		String query = null;
		if(tipoRuleta!=null){
			query="tipo:"+tipo+" AND " + tipoRuleta +":true";
		}else{
			query="tipo:"+tipo;
		}
		
		
		QueryResponse res = SolrHelper.query(query, null, null, true,
				new String[] { "categoria" }, null);

		FacetField facetField = res.getFacetField("categoria");

		Map<String, String> categoriasMap = new HashMap<String, String>();

		if(facetField.getValues()!=null){
			int cont=0;
			for (FacetField.Count element : facetField.getValues()) {
				categoriasMap.put(element.getName(), element.getName());
				cont++;
				if(cont==4){
					break;
				}
			}
		}

		getRequest().setAttribute("categorias", categoriasMap.keySet());
		forward.addActionOutput("categoriasMap", categoriasMap);

	}

	/**
	 * Action que filtra los elementos a mostrar en la ruleta segun las
	 * categorias que se hayan escogido
	 * 
	 * @param form
	 *            El form con los datos del formulario
	 * @return Un forward hacia el jsp de la ruleta
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "ruletaInclude.jsp"), })
	public Forward filtrarRuleta(EntretenimientoFormBean form) {
		Forward forward = new Forward("success");

		if (form.getCategoria() == null || form.getCategoria().length == 0
				|| "Todos".equals(form.getCategoria()[0])) {
			forward.addActionOutput("ruleta", SolrHelper.query(
					"tipo:" + form.getTipo() + " AND " + form.getTipoRuleta()
							+ ":true").getResults());
		} else {
			StringBuilder builder = new StringBuilder("+tipo:" + form.getTipo()
					+ " +" + form.getTipoRuleta() + ":true  +(");
			for (int i = 0; i < form.getCategoria().length; i++) {
				builder.append("categoria:\"" + form.getCategoria()[i]+"\"");
				if (i + 1 < form.getCategoria().length) {

					builder.append(" OR ");
				}
			}
			builder.append(")");

			SolrDocumentList list = SolrHelper.query(builder.toString())
					.getResults();

			// La ruleta no funciona con menos de 4 elementos,
			// Repetir los elementos hasta que se tengan por lo menos 4
			// Solo cuando tenga por lo menos un elemento para evitar que se
			// cicle
			if (list.size() > 0) {
				while (list.size() < 4) {
					list.addAll(list);
				}
			}

			forward.addActionOutput("ruleta", list);
		}
		getRequest().setAttribute("tipo", form.getTipo());
		getRequest().setAttribute("tipoRuleta", form.getTipoRuleta());
		setCategorias(forward, form.getTipo(),form.getTipoRuleta());

		return forward;
	}

	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "losmasvistos.jsp") })
	protected Forward lmvAction(EntretenimientoFormBean form) throws Exception {
		Forward forward = new Forward("success");
		SolrDocumentList listaMasVistos = new SolrDocumentList();
		SolrDocumentList lmv = new SolrDocumentList();

		String isRandom = (String) getRequest().getAttribute("random");
		if (StringUtils.isEmpty(form.getTipo())) {
			form.setTipo((String) getRequest().getAttribute("tipo"));
		}

		QueryResponse res = SolrHelper.queryProgramacion("tipo:"
				+ form.getTipo(), "masVistos", ORDER.asc, true,
				new String[] { "masVistos" }, null);

		FacetField facetField = res.getFacetField("masVistos");

		Map<String, String> masVistosMap = new HashMap<String, String>();

		if (StringUtils.isNotEmpty(isRandom)
				&& isRandom.equalsIgnoreCase("true")) {
			Random r = new Random();
			int i = r.nextInt(facetField.getValueCount());
			FacetField.Count element = facetField.getValues().get(i);

			masVistosMap.put(element.getName(), element.getName());
			lmv = SolrHelper.queryProgramacion(
					"tipo:" + form.getTipo() + " AND masVistos:"
							+ element.getName(), "masVistos", ORDER.asc, false,
					null, null).getResults();
			if (lmv != null)
				listaMasVistos.addAll(lmv);
		} else {
			for (FacetField.Count element : facetField.getValues()) {
				masVistosMap.put(element.getName(), element.getName());
				lmv = SolrHelper.queryProgramacion(
						"tipo:" + form.getTipo() + " AND masVistos:"
								+ element.getName(), "masVistos", ORDER.asc,
						false, null, null).getResults();
				if (lmv != null)
					listaMasVistos.addAll(lmv);
			}
		}

		getRequest().setAttribute("masvistoscats", masVistosMap.keySet());
		forward.addActionOutput("masVistosCatsMap", masVistosMap);

		forward.addActionOutput("masvistos", listaMasVistos);
		return forward;
	}

	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "sidebarProg.jsp") })
	public Forward showSidebarProgramacion() {
		Forward forward = new Forward("success");

		Map<Integer, String> tipos = new HashMap<Integer, String>();

		Random r = new Random();
		int i = r.nextInt(3);
		i = 0;
		tipos.put(0, "ondemand");
		tipos.put(1, "hd");
		tipos.put(2, "ppv");

		forward.addActionOutput("tipo", tipos.get(i));
		return forward;
	}

	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "programacion.jsp") })
	protected Forward programacion(EntretenimientoFormBean form)
			throws Exception {

		Forward forward = new Forward("success");
		forward.addActionOutput("programacion", SolrHelper.queryProgramacion(
				"tipo:ondemand", "masVistos", ORDER.asc, false, null, null)
				.getResults());
		setCategorias(forward, "ondemand",null);
		return forward;
	}

	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "programacionPPV.jsp") })
	protected Forward programacionPPV(EntretenimientoFormBean form)
			throws Exception {
		Forward forward = new Forward("success");
		Date fechaPPVI = null;
		Date fechaPPVF = null;
		Calendar cal = Calendar.getInstance();
		Date hoy = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (form.getFechaProgramacionPPV() == null) {
			cal.setTime(hoy);
		} else {
			StringBuffer strFecha = new StringBuffer(form.getFechaProgramacionPPV());
			cal.setTime(sdf.parse(strFecha.toString()));
		}
		
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 0, 0, 0);
		fechaPPVI = cal.getTime();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 23, 59, 0);
		fechaPPVF = cal.getTime();
		
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(fechaPPVI);
		cal2.set(Calendar.DATE, cal2.get(Calendar.DATE)-1);
		forward.addActionOutput("title_left_arrow",sdf.format(cal2.getTime()));
		cal2.set(Calendar.DATE, cal2.get(Calendar.DATE)+2);
		forward.addActionOutput("title_right_arrow",sdf.format(cal2.getTime()));

		forward.addActionOutput("left_arrow",
				fechaPPVI.compareTo(hoy) <= 0 ? false : true);

//		forward.addActionOutput("lang", getLocale().getDisplayLanguage());
//		forward.addActionOutput("country", getLocale().getCountry());
		forward.addActionOutput("lang", "es");
		forward.addActionOutput("country", "MX");

		forward.addActionOutput("fhoy", fechaPPVI);
		
		SolrDocumentList listaProgramacion = SolrHelper.queryProgramacion(
				"tipo:programacion.ppv AND fechaini:[" + SolrHelper.Date2UTC(fechaPPVI)
				+ " TO " + SolrHelper.Date2UTC(fechaPPVF) + "]", null, null, false,
		null, null).getResults();

		forward.addActionOutput("programacion", listaProgramacion);
		getDays(forward);
		getHours(forward, fechaPPVI, fechaPPVF);
		getCategoriesPPV(forward, fechaPPVI, fechaPPVF);
		
		return forward;
	}
	
	private void getCategoriesPPV(Forward forward, Date fechai, Date fechaf){
		QueryResponse res = SolrHelper.queryProgramacion(
				"tipo:programacion.ppv AND fechaini:[" + SolrHelper.Date2UTC(fechai)
				+ " TO " + SolrHelper.Date2UTC(fechaf) + "]", "categoria", ORDER.asc, true, new String[] {"categoria"}, "categoria");
		FacetField facetField = res.getFacetField("categoria");
		Map<String, String> categoriasMap = new HashMap<String, String>();
		if(facetField.getValues()!=null){
			for (FacetField.Count element : facetField.getValues()) {
				if(!element.getName().equals(""))
					categoriasMap.put(element.getName(), element.getName());
			}
		}
		
		getRequest().setAttribute("categorias", categoriasMap.keySet());
		forward.addActionOutput("categoriasMap", categoriasMap);
	}

	private void getDays(Forward forward){
		QueryResponse res = SolrHelper.queryProgramacion(
				"tipo:programacion.ppv", "fechainiCorta", ORDER.asc, true, new String[] {"fechainiCorta"}, "fechainiCorta");

		FacetField facetField = res.getFacetField("fechainiCorta");
		SimpleDateFormat sdfin = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		sdfin.setTimeZone(TimeZone.getTimeZone("UTC"));
		SimpleDateFormat sdfvalue = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfout = new SimpleDateFormat("dd/MM/yyyy");
		sdfout.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));
		Date f = null;

		Map<String, String> diasMap = new LinkedHashMap<String, String>();

		try {
			if(facetField.getValues()!=null){
				for (FacetField.Count element : facetField.getValues()) {
					f = sdfin.parse(element.getName());
					if(!diasMap.containsKey(sdfvalue.format(f)))
						diasMap.put(sdfvalue.format(f), sdfout.format(f));
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		getRequest().setAttribute("dias", diasMap.keySet());
		forward.addActionOutput("diasMap", diasMap);
	}
	
	private void getHours(Forward forward, Date fechai, Date fechaf){
		QueryResponse res = SolrHelper.queryProgramacion(
				"tipo:programacion.ppv AND fechaini:[" + SolrHelper.Date2UTC(fechai)
				+ " TO " + SolrHelper.Date2UTC(fechaf) + "]", "fechaini", ORDER.asc, true, new String[] {"fechaini"}, "fechaini");

		FacetField facetField = res.getFacetField("fechaini");
		SimpleDateFormat sdfin = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		sdfin.setTimeZone(TimeZone.getTimeZone("UTC"));
		SimpleDateFormat sdfout = new SimpleDateFormat("HH:mm");
		sdfout.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));
		Date f = null;

		Map<String, String> horasMap = new LinkedHashMap<String, String>();

		try {
			if(facetField.getValues()!=null){
				for (FacetField.Count element : facetField.getValues()) {
					f = sdfin.parse(element.getName());
					if(!horasMap.containsKey(sdfout.format(f)))
						horasMap.put(sdfout.format(f), sdfout.format(f));
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		getRequest().setAttribute("horas", horasMap.keySet());
		forward.addActionOutput("horasMap", horasMap);
	}

	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "setCarga.jsp") })
	protected Forward setCarga(EntretenimientoFormBean forma) throws Exception {
		String tipos[] = getMessageResources("carga").getMessage("carga.tipos")
				.split(",");
		Map<String, String> tiposMap = new HashMap<String, String>();
		for (String tipo : tipos) {
			String[] element = tipo.split(":");
			tiposMap.put(element[1], element[0]);
		}
		Forward forward = new Forward("success");
		getRequest().setAttribute("tipos", tiposMap.keySet());
		forward.addActionOutput("tiposMap", tiposMap);
		if (forma.archivo != null) {
			InputStream is = forma.archivo.getInputStream();
			forward.addActionOutput("ufmsg", "Archivo cargado exitosamente.");
			// writeToFile(forma.archivo.getFileName(), is, false, forward);
			CargaExcel carga = new CargaExcel();
			carga.generarXML(forma.tipo, is);
		}
		return forward;
	}

	@Jpf.Action(forwards = { @Jpf.Forward(name = "detalle", path = "detalle.jsp")})
	public Forward mostrarDetalle(EntretenimientoFormBean form) {
		Forward forward = new Forward("detalle");

		SolrDocumentList result = SolrHelper.query(
				"id:\"" + form.getId() + "\"").getResults();
		
		if (result != null && result.size() > 0) {
			SolrDocument detalle = result.get(0);
			
			//si viene de programación de ppv, necesita ir por el detalle agregado en la ruleta
			if ( "programacion.ppv".equals(form.getTipo()) ) {
				SolrDocumentList resultPpv = SolrHelper.query(
						"titulo:\"" + detalle.getFieldValue("titulo") + "\"" +
						"AND tipo:ppv AND genero: \"" +detalle.getFieldValue("genero")+ "\" ").getResults();
				if(resultPpv != null && resultPpv.size() > 0)
					detalle = resultPpv.get(0);
			}
			
			
			forward.addActionOutput("detalle", detalle);
			getRequest().setAttribute("id", form.getId());
			getRequest().setAttribute("tipo", form.getTipo());
			
			if ( ( "ppv".equals(form.getTipo()) || "programacion.ppv".equals(form.getTipo()) ) ){
				SolrDocumentList horariosPpv = SolrHelper.query("tipo:programacion.ppv" +
												" AND titulo:\"" + detalle.getFieldValue("titulo") + "\"" +
												" AND (fechainiVenta:[* TO NOW] AND fechafinVenta:[NOW TO *])",
												"fechainiVenta").getResults();

				getRequest().setAttribute("ppv", horariosPpv);
				forward.addActionOutput("msg", PageFlowUtils.getActionOutput("ppvDuplicado", getRequest()));
			}
		}
		
		return forward;
	}

	/**
	 * Metodos para verificar si un ppv se puede contratar
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "ppv/informacionVenta.jsp"),
							 @Jpf.Forward(name = "error", action="mostrarDetalle"),
							 @Jpf.Forward(name = "login", action="../login/showLogin")})
	public Forward verificarContratar(PpvFormBean form) throws Exception{
		Forward forward = new Forward("error"); //
		
		ServletRequest request = ScopedServletUtils.getOuterServletRequest(getRequest());
		
		String idSolr = request.getParameter("idSolr");
		String idIni = request.getParameter("id");
		String tipoIni = request.getParameter("tipo");
		
		String userAccount = getSession().getAttribute(Constantes.SESSION_ACCOUNT_ID)!=null?
							 getSession().getAttribute(Constantes.SESSION_ACCOUNT_ID).toString():"";
		if(StringUtils.isNotBlank(userAccount)){
			RespuestaToMyAccount response = getVitriaClient().getProjects_CVNPW_Initial_ToInterfase().toMyAccount(userAccount.trim());
			if(response != null){
				if ( response.getEquipos()!=null && response.getEquipos().length > 0 ) {
					
					
					SolrDocumentList result = SolrHelper.query(
							"id:\"" + idSolr + "\"").getResults();
					
					if (result != null && result.size() > 0) {
						SolrDocument detalle = result.get(0);
						
						SimpleDateFormat sdfFecha = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
						
						form.setFechaFin(detalle.getFieldValue("fechafin")!=null ? 
										sdfFecha.format((Date)detalle.getFieldValue("fechafin")):"");
						form.setFechaIni(detalle.getFieldValue("fechafin")!=null ? 
										sdfFecha.format((Date)detalle.getFieldValue("fechaini")):"");
						form.setIdEvento((String)detalle.getFieldValue("eventoid"));
						form.setTitulo((String)detalle.getFieldValue("titulo"));
						form.setCanal((String)detalle.getFieldValue("canal"));
						form.setPrecio(detalle.getFieldValue("precio")!=null?(detalle.getFieldValue("precio")).toString():"");
						
						ResponseToGetPPV ppv = getVitriaClient().getProjects_CVNPW_Initial_ToInterfase().toGetPPV(
								   response.getCvNumberAccount(), form.getFechaIni(), 
								   form.getFechaFin(), form.getIdEvento());
							if(ppv.getPpvEvent().equalsIgnoreCase("N")){
								//forward.addActionOutput("ppvDuplicado", ppv.getError().getCvErrorMessage());
								forward.addActionOutput("ppvDuplicado", "Ya tiene contratado este evento, seleccione otro");
								EntretenimientoFormBean formIni = new EntretenimientoFormBean();
								formIni.setId(idIni);
								formIni.setTipo(tipoIni);
								forward.addOutputForm(formIni);
								return forward;
							}
							
							
							forward = new Forward("success");
							getRequest().setAttribute("usuario", response);
							getRequest().setAttribute("fechaini", sdfFecha.parse(form.getFechaIni()));
							getRequest().setAttribute("fechafin", sdfFecha.parse(form.getFechaFin()));
							getRequest().setAttribute("equipos", response.getEquipos()[0]!=null?response.getEquipos()[0].getEquipo():"");
							getRequest().setAttribute("ppv",form);
							getRequest().setAttribute("idSolr",idSolr);
						}else{
							forward.addActionOutput("ppvDuplicado", "No tiene el servicio de Internet contratado.");
							EntretenimientoFormBean formIni = new EntretenimientoFormBean();
							formIni.setId(idIni);
							formIni.setTipo(tipoIni);
							forward.addOutputForm(formIni);
							return forward;
						}
					}
			}
		}else{
			forward = new Forward("login");
			getSession().setAttribute(Constantes.CABLEVISION_URL, getRequest().getContextPath()+
					"/com/cablevision/controller/entretenimiento/verificarContratar.do?modal=true&height=430&width=520"+
					"&idSolr="+idSolr+"&id="+idIni+"&tipo="+tipoIni);
		}
		return forward;
	}
	
	/**
	 * Metodos para mostrar los datos de confirmación del ppv a comprarse
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "ppv/confirmacionCompra.jsp")})
	public Forward showConfirmacion(PpvFormBean form) throws Exception{
		Forward forward = new Forward("success");
		
		ServletRequest request = ScopedServletUtils.getOuterServletRequest(getRequest());
		
		SimpleDateFormat sdfFecha = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		getRequest().setAttribute("fechaini", sdfFecha.parse(form.getFechaIni()));
		getRequest().setAttribute("fechafin", sdfFecha.parse(form.getFechaFin()));
		getRequest().setAttribute("ppv",form);
		getRequest().setAttribute("nombre",request.getParameter("nombre"));
		getRequest().setAttribute("cuenta",request.getParameter("cuenta"));
		getRequest().setAttribute("idSolr",request.getParameter("idSolr"));
		
		return forward;
	}
	
	/**
	 * Metodos para contratar el ppv elegido
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "ppv/confirmacion.jsp"),
						     @Jpf.Forward(name = "error", path = "ppv/mensaje.jsp")
						   })
	public Forward contratar(PpvFormBean form) throws Exception{
		Forward forward = new Forward("success");
		ServletRequest request = ScopedServletUtils.getOuterServletRequest(getRequest());
		String cuenta = request.getParameter("cuenta");
		
		ResponseToPurchasePPV response = getVitriaClient().getProjects_CVNPW_Initial_ToInterfase().toPurchasePPV(cuenta, form.getUbicacion(), form.getIdEquipo(),
				form.getEquipoSerie(), form.getIdEvento(), form.getFechaIni());
		
		if ( response!=null && response.getError()!=null && !"0".equals(response.getError().getCvErrorCode()) ) {
			forward = new Forward("error");
			forward.addActionOutput("msg", response.getError().getCvErrorMessage());
			
			//Mandar Error a la hora de contratar
			String userAccount = getSession().getAttribute(Constantes.SESSION_ACCOUNT_ID)!=null?
					 getSession().getAttribute(Constantes.SESSION_ACCOUNT_ID).toString():"";
			RespuestaToMyAccount responseAcount = getVitriaClient().getProjects_CVNPW_Initial_ToInterfase().toMyAccount(userAccount.trim());
			responseAcount.getCvMailAddres();		
			
			Map<String, String> values = new HashMap<String, String>();
			values.put("nombre", responseAcount.getNombreContacto()+" "+responseAcount.getApellidoPaterno()+" "+responseAcount.getApellidoMaterno());
			values.put("email", responseAcount.getCorreoContacto());
			values.put("noCuenta", responseAcount.getCvNumberAccount());
			values.put("errorCode", response.getError().getCvErrorCode());
			
			
			MailUtil.sendMail(ConfigurationHelper.getPropiedad("correo.ppv.subject",null), ConfigurationHelper.getPropiedad("correo.ppv.to",null), 
					ConfigurationHelper.getPropiedad("correo.ppv.from",null), 
					ConfigurationHelper.getPropiedad("correo.ppv.templateId",null), values);
			
		}
		return forward;
	}
	
	/**
	 * Guarda un archivo en el servidor a partir de un inputstream
	 * 
	 * @param nArchivo
	 *            El nombre del archivo
	 * @param is
	 *            El inputstream
	 * @param crearDir
	 *            Si se desea crear un directorio para guardar el archivo
	 * @throws IOException
	 */
	public static void writeToFile(String nArchivo, InputStream is,
			boolean crearDir, Forward forward) throws IOException {
		if (nArchivo == null) {
			forward
					.addActionOutput("errormsg",
							"El nombre del archivo es nulo");
		}
		if (is == null) {
			forward.addActionOutput("errormsg", "InputStream es nulo");
		}

		File elArchivo = new File(nArchivo);

		if (elArchivo.exists()) {
			String msg = elArchivo.isDirectory() ? "directorio" : (!elArchivo
					.canWrite() ? "no editable" : null);
			if (msg != null) {
				forward.addActionOutput("errormsg", "El archivo '" + nArchivo
						+ "' es " + msg);
			}
		}

		if (crearDir && elArchivo.getParentFile() != null) {
			elArchivo.getParentFile().mkdirs();
		}

		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(elArchivo));
			byte[] buffer = new byte[32 * 1024];
			int bytesRead = 0;
			while ((bytesRead = is.read(buffer)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
		} catch (Exception e) {
			forward.addActionOutput("errormsg", "Fallo: " + e.toString());
		} finally {
			close(is, bos);
		}
	}
	
	/**
	 * Va a vitria por la cuenta del usuario y la pone en sesion
	 * @throws RemoteException
	 * @throws ServiceException
	 */
	public ToInterfase getVitriaClient() {
		if(vitriaClient==null){
			ApplicationContext context = (ApplicationContext)getRequest().getSession().getServletContext().getAttribute(
					WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			vitriaClient = (ToInterfase)context.getBean("VitriaClient");

		}
		return vitriaClient;
	}

	
	/**
	 * Metodo que cierra todos los streams usados por el guardado del archivo
	 * 
	 * @param is
	 *            InputStream
	 * @param bos
	 *            OutputStream
	 * @throws IOException
	 */
	protected static void close(InputStream is, OutputStream bos)
			throws IOException {
		try {
			if (is != null) {
				is.close();
			}
		} finally {
			if (bos != null) {
				bos.close();
			}
		}
	}

	// Uncomment this block if nested pageflow
	/**
	 * @Jpf.Action( forwards={
	 * @Jpf.Forward(name="done", returnAction="NestedTemplateDone") } )
	 *                           protected Forward done() { return new
	 *                           Forward("done"); }
	 */

	/**
	 * Callback that is invoked when this controller instance is created.
	 */
	protected void onCreate() {
	}

	/**
	 * Callback that is invoked when this controller instance is destroyed.
	 */
	protected void onDestroy(HttpSession session) {
	}

	/**
	 * @author luishpm
	 * 
	 */
	@Jpf.FormBean
	public static class EntretenimientoFormBean implements java.io.Serializable {
		private static final long serialVersionUID = 1L;

		private String[] categoria;
		private String id;
		private String tipo;
		private String tipoMasVisto;
		private String tipoRuleta;
		private String fechaProgramacionPPV;

		private transient FormFile archivo;

		public String[] getCategoria() {
			return categoria;
		}

		public void setCategoria(String[] categoria) {
			this.categoria = categoria;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getTipo() {
			return tipo;
		}

		public void setTipo(String tipo) {
			this.tipo = tipo;
		}

		public String getTipoMasVisto() {
			return tipoMasVisto;
		}

		public void setTipoMasVisto(String tipoMasVisto) {
			this.tipoMasVisto = tipoMasVisto;
		}

		public String getTipoRuleta() {
			return tipoRuleta;
		}

		public void setTipoRuleta(String tipoRuleta) {
			this.tipoRuleta = tipoRuleta;
		}

		public FormFile getArchivo() {
			return archivo;
		}

		public void setArchivo(FormFile archivo) {
			this.archivo = archivo;
		}

		public String getFechaProgramacionPPV() {
			return fechaProgramacionPPV;
		}

		public void setFechaProgramacionPPV(String fechaProgramacionPPV) {
			this.fechaProgramacionPPV = fechaProgramacionPPV;
		}		
	}
	
	
	@Jpf.FormBean
	public static class PpvFormBean implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		
		private String idEvento;
		private String equipoSerie;
		private String ubicacion;
		private String idEquipo;
		private String titulo;
		private String precio;
		private String canal;
		private String fechaIni;
		private String fechaFin;
		public String getIdEvento() {
			return idEvento;
		}
		public void setIdEvento(String idEvento) {
			this.idEvento = idEvento;
		}
		public String getEquipoSerie() {
			return equipoSerie;
		}
		public void setEquipoSerie(String equipoSerie) {
			this.equipoSerie = equipoSerie;
		}
		public String getUbicacion() {
			return ubicacion;
		}
		public void setUbicacion(String ubicacion) {
			this.ubicacion = ubicacion;
		}
		public String getIdEquipo() {
			return idEquipo;
		}
		public void setIdEquipo(String idEquipo) {
			this.idEquipo = idEquipo;
		}
		public String getTitulo() {
			return titulo;
		}
		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}
		public String getPrecio() {
			return precio;
		}
		public void setPrecio(String precio) {
			this.precio = precio;
		}
		public String getFechaIni() {
			return fechaIni;
		}
		public void setFechaIni(String fechaIni) {
			this.fechaIni = fechaIni;
		}
		public String getFechaFin() {
			return fechaFin;
		}
		public void setFechaFin(String fechaFin) {
			this.fechaFin = fechaFin;
		}
		public String getCanal() {
			return canal;
		}
		public void setCanal(String canal) {
			this.canal = canal;
		}
	}
	
}