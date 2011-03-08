package com.cablevision.controller.mcafeedownloads;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.ServiceException;

import org.apache.beehive.netui.pageflow.Forward;
import org.apache.beehive.netui.pageflow.annotations.Jpf;
import org.apache.xerces.dom.DocumentImpl;
import org.apache.xerces.jaxp.DocumentBuilderFactoryImpl;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.access.DefaultLocatorFactory;
import org.springframework.web.context.WebApplicationContext;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import weblogic.net.http.SOAPHttpsURLConnection;

import com.bea.portlet.GenericURL;
import com.cablevision.ToInterfase;
import com.cablevision.controller.base.ControllerBase;
import com.cablevision.portal.ErrorVitriaException;
import com.cablevision.service.IMcafeeDownloadsService;
import com.cablevision.util.Constantes;
import com.cablevision.util.ResponseToServiceRequest;
import com.cablevision.util.RespuestaToMyAccount;
import com.cablevision.util.ValidateErrors;
import com.cablevision.vo.CvMcafeeDownload;
import com.cablevision.vo.CvMcafeeUser;
import com.cablevision.vo.CvMcafeesuscribed;

@Jpf.Controller(
		catches={
				@Jpf.Catch(type=RemoteException.class, path="/com/cablevision/controller/error.jsp"),
		        @Jpf.Catch(type=ServiceException.class, path="/com/cablevision/controller/error.jsp"),
		        @Jpf.Catch(type=ParserConfigurationException.class, path="/com/cablevision/controller/error.jsp"),
		        @Jpf.Catch(type=SAXException.class, path="/com/cablevision/controller/error.jsp"),
		        @Jpf.Catch(type=IOException.class, path="/com/cablevision/controller/error.jsp"),
		        @Jpf.Catch(type=UnsupportedEncodingException.class, path="/com/cablevision/controller/error.jsp")
		        },
		simpleActions = { @Jpf.SimpleAction(name = "begin", path = "index.jsp") },
		messageBundles = { 
				@Jpf.MessageBundle(bundlePath = "configuracion", bundleName="configuracion"),
				@Jpf.MessageBundle(bundlePath = "mensajeError", bundleName="error")
				}	,
		loginRequired = true
)
public class McafeeDownloadsController extends ControllerBase {
	private static final long serialVersionUID = 1L;
	private transient IMcafeeDownloadsService mcafeeDownloadsService;
	transient ToInterfase vitriaClient;
	private static String MCAFEE_PARTNERID = "";
	private static String MCAFEE_SKU = "";
	private static String MCAFEE_QTY = "";
	private static String MCAFEE_ACTION = "";
	private static String MCAFEE_URLSUSCRIPTION = "";
	private static String MCAFEE_URLDOWNLOAD = "";
	private static String MCAFEE_RETURNURL = "";
	private int errorCode = 0;
	
	private String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

	
	/**
	 * Metodo que muestra un reporte segun los filtros proporcionados por el usuario
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@Jpf.Action(
			forwards = { 
					@Jpf.Forward(name = "success", path= "reporte.jsp")
			},
			loginRequired = false
	)
	public Forward mostrarReporte(ReportBean form) throws Exception{
		Forward forward = new Forward("success");
		forward.addActionOutput("meses", generaComboMes());
		forward.addActionOutput("form", form);
		
		
		Date fechaInicio = new Date();
		Date fechaFinal = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		
		String mes = form.getMes();
		String txtDia = form.getDia();//"2008-01-01"
		String cuenta = form.getCuenta();
		String txtDe = form.getFechaDe();//"2008-01-01";
		String txtA = form.getFechaA();//"2008-05-01";
		String status = form.getEstatus();//"ACTIVO";
		String fechaLetra = "";
		boolean banderaFiltros = false;
		List<String> errors = new ArrayList<String>();
		
		String mesLetra = "";
		int mesNumero = 0;
		
		//filtro 1 por mes
		if(mes != null && !mes.equals("") && !mes.equals("...")){
			//"2007/11"
			mesLetra = mes.substring(5);
			mesNumero = Integer.parseInt(mesLetra);
			
			mes = mes.concat("/01");//como solo trae anho y mes le concateno el dia 01 para que la fecha de inicio sea apartir del 1er dia del mes
			mes = mes.replace('/', '-');//le doy formato yyyy-mm-dd
			fechaInicio = sdf.parse(mes);
			
			calendar.setTime(fechaInicio);//apartir de la fecha de inicio obtengo la final
			calendar.add(Calendar.MONTH, 1);//le sumo 1 mes
			calendar.add(Calendar.DATE, -1);//le resto 1 dia para obtener el ultimo dia del mes
			
			fechaFinal = calendar.getTime();
			
			fechaLetra = meses[mesNumero-1].concat(" ").concat(mes.substring(0, 4));//Abril 2008
			banderaFiltros = true;//cuando aplico un filtro sera true
		}
		else{
			//filtro 2 por dia
			if(txtDia != null && !txtDia.isEmpty() && !txtDia.equals("")){
				fechaInicio = sdf.parse(txtDia);
				banderaFiltros = true;//cuando aplico un filtro sera true
				fechaLetra = txtDia;
			}
			else{
				//filtro 3 por Cuenta
				if(cuenta != null && !cuenta.equals("")){
					CvMcafeeUser mcafeeUser = getMcafeeDownloadsService().getMcafeeUserByAccount(Long.parseLong(cuenta));
					List<CvMcafeeDownload> listDownloads = getMcafeeDownloadsService().getMcafeeDownloadsByUserAccount(mcafeeUser.getMusId());
					
					if(mcafeeUser != null){
						forward.addActionOutput("mcafeeUser", mcafeeUser);
					}
					
					if(listDownloads != null && !listDownloads.isEmpty()){
						forward.addActionOutput("listDownloads", listDownloads);
					}

					if(mcafeeUser == null && (listDownloads == null || listDownloads.isEmpty())){
						errors.add("No se Encontraron Datos para la Cuenta Especificada");
						forward.addActionOutput("errors", errors);
					}
					
					return forward;
				}
				else{
					//filtro 4 por Fecha Desde-Hasta
					if((txtDe != null && txtA != null) && (!txtDe.isEmpty() && !txtA.isEmpty()) && (!txtDe.equals("") && !txtA.equals(""))){
						fechaInicio = sdf.parse(txtDe);
						fechaFinal = sdf.parse(txtA);
						fechaLetra = txtDe.concat(" al ").concat(txtA);
						banderaFiltros = true;//cuando aplico un filtro sera true
					}
				}
			}
		}
		
		if(banderaFiltros){
			List origen = getMcafeeDownloadsService().getOrigen(fechaInicio, fechaFinal, status);
			List resumenFechas = getMcafeeDownloadsService().getResumenPorFecha(fechaInicio, fechaFinal, status);
			
			
			Map<String, Integer> agrupado = new HashMap<String, Integer>();
			
			agrupado.put("Google", 0);
			agrupado.put("Yahoo", 0);
			agrupado.put("Directo", 0);
			
			for (Object object : origen) {
				String refer = (String)((Object[])object)[1];
				int cantidad = ((BigDecimal)((Object[])object)[0]).intValue();
				
				if(refer==null){
					agrupado.put("Directo", agrupado.get("Directo")+cantidad);
				}else if(refer.contains("google")){
					agrupado.put("Google", agrupado.get("Google")+cantidad);
				}else if(refer.contains("yahoo")){
					agrupado.put("Yahoo", agrupado.get("Yahoo")+cantidad);
				}else{
					agrupado.put("Directo", agrupado.get("Directo")+cantidad);
				}
			}
			
			origen = new ArrayList();
			for (String grupo : agrupado.keySet()) {
				origen.add(new Object[]{agrupado.get(grupo),grupo});
			}
			
			if(origen.size() > 0 || resumenFechas.size() > 0){
				forward.addActionOutput("origen", origen);
				forward.addActionOutput("resumenFechas", resumenFechas);
				forward.addActionOutput("fechaLetra", fechaLetra);
				
				if(fechaFinal==null){
					fechaFinal = fechaInicio;
				}
				
				calendar = Calendar.getInstance();
				calendar.setTime(fechaInicio);
				calendar.add( Calendar.DATE,-1);
				
				forward.addActionOutput("fechaInicio", sdf.format(fechaInicio));
				forward.addActionOutput("fechaFin", sdf.format(fechaFinal));
				forward.addActionOutput("fechaFinTotalAntes", sdf.format(calendar.getTime()));
				
			}
		}else{
			List resumen = getMcafeeDownloadsService().getResumen();
			forward.addActionOutput("lista", resumen);
		}
		
		return forward;
	}
	
	
	/**
	 * Metodo que muestra la pantalla de reportes
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@Jpf.Action(
			forwards = { 
					@Jpf.Forward(name = "success", path= "reporte.jsp")
			},
			loginRequired = false
	)
	public Forward mostrarPaginaReporte(ReportBean form) throws Exception{
		Forward forward = new Forward("success");
		forward.addActionOutput("meses", generaComboMes());
		forward.addActionOutput("form", form);
		
		return forward;
	}
	
	
	/**
	 * Metodo que muestra el resumen de descargas que hay por mes
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@Jpf.Action(
			forwards = { 
					@Jpf.Forward(name = "success", path= "reporte.jsp")
			},
			loginRequired = false
	)
	public Forward mostrarResumen(ReportBean form) throws Exception{
		Forward forward = new Forward("success");
		
		
		List resumen = getMcafeeDownloadsService().getResumen();
		forward.addActionOutput("lista", resumen);
		forward.addActionOutput("meses", generaComboMes());
		forward.addActionOutput("form", form);
		
		return forward;
	}
	
	
	/**
	 * Metodo que genera el key y value para el combo de mes que se muestra al generar un reporte
	 * Ejemplo: key = 2007/11, value = 2007 - Noviembre
	 * @return
	 */
	public Map<String, String> generaComboMes(){
		Map<String, String> mapMeses = new LinkedHashMap<String, String>();
		
		Calendar calendar = Calendar.getInstance();
		String fecha = calendar.getTime().toString();
		int anioActual = Integer.parseInt(fecha.substring(fecha.length()-4));
		int mesActual = calendar.get(Calendar.MONTH)+1;
		
		String value = "";
		String key = "";
		int desde = 2008;
		int rangoAnios = anioActual-desde;
		int anio = 0;
		
		mapMeses.put("2007/11", "2007 - Noviembre");
		mapMeses.put("2007/12", "2007 - Diciembre");
		
		for(int i=0; i <= rangoAnios; i++){
			if(i == rangoAnios){
				for(int j=1; j <= mesActual; j++){
					value = desde + " - ".concat(meses[j-1]);
					key = String.valueOf(desde);
					if(j>=10){
						key = key.concat("/").concat(String.valueOf(j));
					}
					else{
						key = key.concat("/0").concat(String.valueOf(j));
					}
					mapMeses.put(key, value);
				}
				desde++;
			}
			else{
				for(int j=1; j <= meses.length; j++){
					value = desde + " - ".concat(meses[j-1]);
					key = String.valueOf(desde);
					if(j>=10){
						key = key.concat("/").concat(String.valueOf(j));
					}
					else{
						key = key.concat("/0").concat(String.valueOf(j));
					}
					mapMeses.put(key, value);
				}
				desde++;
			}
		}
		
		return mapMeses;
	}
	
	
	
	@Jpf.Action(
			loginRequired = false
	)
	public Forward mostrarReporteExcel(ReportBean form) throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String nombreArchivo = "reporteMcAfee"+System.currentTimeMillis()+".xls";
		
		getMcafeeDownloadsService().generaReporte(format.parse(form.getFechaDe()), form.getFechaA()!=null?format.parse(form.getFechaA()):null, form.getEstatus(), nombreArchivo);
		
		GenericURL url = GenericURL.createGenericURL(getRequest(),getResponse());
		url.setPath("/archivos/mcafee/tmp/"+nombreArchivo);
		
		
		this.getResponse().setHeader("Cache-Control", "max-age=30");
		this.getResponse().setContentType("application/msexcel");
		
		StringBuffer sbContentDispValue = new StringBuffer();
		sbContentDispValue.append("attachment");
		sbContentDispValue.append("; filename=");
		sbContentDispValue.append(nombreArchivo);
		
		this.getResponse().setHeader("Content-disposition",sbContentDispValue.toString());
		
		return new Forward(new URI(url.toString()));
		
	}
	
	@Jpf.Action(
			forwards = { 
					@Jpf.Forward(name = "success", path= "downloadMcafee.jsp"), 
					@Jpf.Forward(name = "error", action= "catchErrors")
			}
	)
	public Forward downloadMcafee() throws RemoteException, ServiceException, ParserConfigurationException, 
	SAXException, IOException, UnsupportedEncodingException {
		
		
		
		Forward forward = new Forward("error");
		MCAFEE_PARTNERID = getMessageResources("configuracion").getMessage("mcafee.partnerid");
		MCAFEE_SKU = getMessageResources("configuracion").getMessage("mcafee.sku");
		MCAFEE_QTY = getMessageResources("configuracion").getMessage("mcafee.qty");
		MCAFEE_ACTION = getMessageResources("configuracion").getMessage("mcafee.action");
		MCAFEE_URLSUSCRIPTION = getMessageResources("configuracion").getMessage("mcafee.urlsuscription");
		MCAFEE_URLDOWNLOAD = getMessageResources("configuracion").getMessage("mcafee.urldownload");
		MCAFEE_RETURNURL = getMessageResources("configuracion").getMessage("mcafee.returnurl");
		errorCode = 0;
		
		String sessionAccountId = "";

		if(getSession().getAttribute(Constantes.SESSION_ACCOUNT_ID)!=null)
			sessionAccountId = (String)getSession().getAttribute(Constantes.SESSION_ACCOUNT_ID);

		// obtiene el detalle de la cuenta de usuario de cablevision
		RespuestaToMyAccount cvCuenta = (RespuestaToMyAccount)getSession().getAttribute(Constantes.SESSION_MI_CUENTA);
		
		if(cvCuenta != null) {
			if("Y".equals(cvCuenta.getIpCable())){
				// obtiene el objeto usuario de mcafee
				Long account = Long.parseLong(cvCuenta.getCvNumberAccount());
				CvMcafeeUser mcafeeUser = cuentaMcafee(account);

				// genera el XML y luego obtiene el usuario de mcafee, devuelo 0 si no tiene
				Document xml = generateXML(cvCuenta);
				String xmlStringResponse = getXMLResponse(xml);
				if(xmlStringResponse != null) {

					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder builder = null;
					Document xmlResponse = null;

					builder = factory.newDocumentBuilder();
					InputSource is = new InputSource(new StringReader(xmlStringResponse));
					xmlResponse = builder.parse(is);

					CvMcafeeUser newMcafeeUser = getMcafeeUserFromResponse(xmlResponse, cvCuenta, sessionAccountId);
					boolean isNew = true;
					if(newMcafeeUser != null) {
						try {
							if(mcafeeUser!=null){
								newMcafeeUser.setMusId(mcafeeUser.getMusId());
								newMcafeeUser.setMusDatesuscribe(mcafeeUser.getMusDatesuscribe());
								isNew =  false;
							}
							if(newMcafeeUser != null) {
								getMcafeeDownloadsService().persistCvMcafeeUser(newMcafeeUser);
								mcafeeUser = newMcafeeUser;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

						if(mcafeeUser != null) {
							// comprueba que no sea un usuario de prueba
							if(mcafeeUser.getMusId() != 10000748 && mcafeeUser.getMusId() != 17000137) {
								// obtiene la cantidad de descargas hechas por el usuario
								if(downzStillAvailable(mcafeeUser.getMusId())) {
									CvMcafeeDownload md = new CvMcafeeDownload();
									md.setMdlDate(new java.sql.Timestamp(new Date().getTime()));
									md.setMdlIduser(mcafeeUser.getMusId());
									try {
										getMcafeeDownloadsService().persistCvMcafeeDownload(md);
									} catch (Exception e) {
										e.printStackTrace();
									}
									
									CvMcafeesuscribed suscribed = null;
									
									if(isNew){
										suscribed = new CvMcafeesuscribed();
										suscribed.setMteAccount(Long.parseLong(cvCuenta.getCvNumberAccount()));
										suscribed.setMteAttemps(Long.valueOf(1));
										suscribed.setMteEmail(cvCuenta.getCorreoContacto());
										suscribed.setMteSource("directo");
										suscribed.setMteStatus("ACTIVO");
										suscribed.setMteSuscribedate(new Timestamp(System.currentTimeMillis()));
										suscribed.setMteIduser(mcafeeUser.getMusId());
										getMcafeeDownloadsService().persistCvMcafeesuscribed(suscribed);
									}else{
										getMcafeeDownloadsService().aumentarDownload(cvCuenta.getCvNumberAccount());
									}
									
									//nueva llamada vitria
							         if(suscribed == null){
							          suscribed = getMcafeeDownloadsService().getSuscribdByAccount(Integer.parseInt(cvCuenta.getCvNumberAccount()));
							         }
							         
							         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
							         String descripcion = "email:"+mcafeeUser.getMusEmailaddress()+" pwd:"+mcafeeUser.getMusPassword()+
							               " ref:"+mcafeeUser.getMusReference()+" fecha de suscripción:"+sdf.format(suscribed.getMteSuscribedate())+
							               " fecha de descarga "+sdf.format(new Date())+" origen de descarga:Pagina Web id descarga:"+suscribed.getMteId();
							         
							         ResponseToServiceRequest response = getVitriaClient().getProjects_CVNPW_Initial_ToInterfase().
							                  toServiceRequest("McAfee", "PAGINA WEB", 
							                      cvCuenta.getCvNumberAccount(), descripcion, 
							                      "", 
							                      "", "Descarga", "", "Internet Protected", "", "", "", "", "", "", 
							                      "", "", "", "", "");
							         
							        try {
										ValidateErrors.validateErrorResponse(response.getCvError(), getMessageResources("mensajeError"));
									} catch (ErrorVitriaException e) {
										errorCode = 0;
										return forward;
									}
									
									String urlDownload = MCAFEE_URLDOWNLOAD + "?" +
									URLEncoder.encode("EMAIL_ADDRESS","UTF-8") + "=" + URLEncoder.encode(mcafeeUser.getMusEmailaddress(),"UTF-8") + "&" +
									URLEncoder.encode("PASSWORD","UTF-8") + "=" + URLEncoder.encode(mcafeeUser.getMusPassword(),"UTF-8") + "&" +
									URLEncoder.encode("CCID","UTF-8") + "=" + URLEncoder.encode(Long.toString(mcafeeUser.getMusAccount()),"UTF-8") + "&" +
									URLEncoder.encode("AFF_ID","UTF-8") + "=" + URLEncoder.encode(MCAFEE_PARTNERID,"UTF-8") + "&" +
									URLEncoder.encode("RETURN_URL","UTF-8")	+ "=" + URLEncoder.encode(MCAFEE_RETURNURL,"UTF-8") + "&" +
									URLEncoder.encode("REMEMBER_ME","UTF-8") + "=" + URLEncoder.encode("0","UTF-8") + "&" +
									URLEncoder.encode("APP_CODE","UTF-8") + "=" + URLEncoder.encode("MVS","UTF-8");
									getResponse().sendRedirect(urlDownload);

									forward = new Forward("success");
								} else {
									errorCode = 3;
								}
							}
						} else {
							errorCode = 0;
						}
					} else {
						errorCode = 2;
					}
				} else {
					errorCode = 1;
				}
			}else {
				errorCode = 4;
			}
		} else {
			errorCode = -1;
			//sendError("Error 2!", getRequest(), getResponse());
		}
		return forward;
	}
	
	 /**
	  * llamada a vitria
	  */
	 public ToInterfase getVitriaClient() {
	  if(vitriaClient==null){
	   ApplicationContext context = (ApplicationContext)getRequest().getSession().getServletContext().getAttribute(
	     WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
	   vitriaClient = (ToInterfase)context.getBean("VitriaClient");
	   
	  }
	  return vitriaClient;
	 }
	
	private CvMcafeeUser getMcafeeUserFromResponse(Document response, RespuestaToMyAccount cuenta, String sessionAccountId) {
		final String PARTNERRESPONSECONTEXT = "PARTNERRESPONSECONTEXT";
		final String HEADER = "HEADER";
		final String PARTNER = "PARTNER";
		final String PARTNER_ID = "PARTNER_ID";
		final String DATA = "DATA";
		final String ID = "ID";
		final String RETURNCODE = "RETURNCODE";
		final String RETURNDESC = "RETURNDESC";
		final String ORDER = "ORDER";
		final String PARTNERREF = "PARTNERREF";
		final String REF = "REF";
		final String ACCOUNT = "ACCOUNT";
		final String EMAILADDRESS = "EMAILADDRESS";
		final String PASSWORD = "PASSWORD";

		if(response != null && response.getElementsByTagName(EMAILADDRESS).getLength() > 0) {
			CvMcafeeUser mcafeeUser = new CvMcafeeUser();
			NodeList nodes = null;
			mcafeeUser.setMusAccount(Long.parseLong(cuenta.getCvNumberAccount()));
			
			mcafeeUser.setMusAccountid(sessionAccountId);
			mcafeeUser.setMusContactid(sessionAccountId);
			mcafeeUser.setMusCvstatus("ACTIVO");
			mcafeeUser.setMusDatesuscribe(new java.sql.Timestamp(new Date().getTime()));
			
			nodes = response.getElementsByTagName(EMAILADDRESS);
			mcafeeUser.setMusEmailaddress(nodes.getLength()>0 ? nodes.item (0).getTextContent():null);
			
			nodes = response.getElementsByTagName(PASSWORD);
			mcafeeUser.setMusPassword(nodes.getLength()>0 ? nodes.item(0).getTextContent():null);
			
			nodes = response.getElementsByTagName(ORDER);
			mcafeeUser.setMusReference(nodes.getLength()>0 ? nodes.item(0).getAttributes().getNamedItem(REF).getNodeValue():null);
			
			return mcafeeUser;
		} else
			return null;
	}
	
	
	@SuppressWarnings("deprecation")
	private String getXMLResponse(Document xml) {
		URLConnection uc =null;
		SOAPHttpsURLConnection connection=null;
		OutputStream out=null;
		InputStream in=null;
		Reader reader=null;
		Writer wout =null; 
		StringBuffer sb = new StringBuffer();

		try{
			URL u = new URL(MCAFEE_URLSUSCRIPTION);
			uc = u.openConnection();
			connection = (SOAPHttpsURLConnection) uc;
			connection.setDoOutput(true);
			connection.setDoInput(true); 
			connection.setRequestProperty("Content-Type", "application/octet-stream");
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			out = connection.getOutputStream();
			wout = new OutputStreamWriter(out,"UTF-8");
			// Write the request
			System.out.println("Enviando xml: "+xmlToString(xml));
			wout.write(xmlToString(xml));  
			connection.connect();
			wout.flush();
			wout.close();

			// Read the response
			in = connection.getInputStream();
			reader = new InputStreamReader(in, "UTF-8");
			int c;
			while ((c = in.read()) != -1) 
				sb.append((char) c);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally{
			try{in.close(); }catch(Exception e){}
			try{reader.close(); }catch(Exception e){}
			try{connection.disconnect(); }catch(Exception e){}
		}

//		String xmlStringResponse = new String("<?xml version=\"1.0\"?> " + 
//				"<PARTNERRESPONSECONTEXT>" +
//				"	<HEADER>" +
//				"		<PARTNER PARTNER_ID=\"625\"/>" +
//				"	</HEADER>" +
//				"	<DATA>" +
//				"		<RESPONSECONTEXT ID=\"17435360\">" +
//				"			<RETURNCODE>5001</RETURNCODE>" +
//				"			<RETURNDESC>Transaction success: Warning, Email Address Exists.</RETURNDESC>" +
//				"			<ORDER PARTNERREF=\"17435360-45d77bff05df903ac8c5e13ad1ffaab6\" REF=\"NCS602843872\"/>" +
//				"			<ACCOUNT>" +
//				"				<EMAILADDRESS>me_vale@hotmail.com</EMAILADDRESS>" +
//				"				<PASSWORD><![CDATA[h5zt1kpq]]></PASSWORD>" +
//				"			</ACCOUNT></RESPONSECONTEXT>" +
//				"	</DATA>" +
//		"</PARTNERRESPONSECONTEXT>");
		System.out.println("Xml recibido "+sb.toString());
		return sb.toString();
	}
	
	/**
	 * Genera el XML para enviar a Mcafee
	 * @param cvCuenta El objeto de cuenta de Cablevision del usuario
	 * @return un Documento XML
	 */
	private Document generateXML(RespuestaToMyAccount cvCuenta) {
		final String PARTNERCONTEXT = "PARTNERCONTEXT";
		final String HEADER = "HEADER";
		final String PARTNER = "PARTNER";
		final String PARTNER_ID = "PARTNER_ID";
		final String DATA = "DATA";
		final String CUSTOMERCONTEXT = "CUSTOMERCONTEXT";
		final String ACCOUNT = "ACCOUNT";
		final String ID = "ID";
		final String REQUESTTYPE = "REQUESTTYPE";
		final String EMAILADDRESS = "EMAILADDRESS";
		final String FIRSTNAME = "FIRSTNAME";
		final String LASTNAME = "LASTNAME";
		final String PASSWORD = "PASSWORD";
		final String PREFERENCES = "PREFERENCES";
		final String PREFERENCE = "PREFERENCE";
		final String TYPE = "TYPE";
		final String ORDER = "ORDER";
		final String PARTNERREF = "PARTNERREF";
		final String REF = "REF";
		final String ITEMS = "ITEMS";
		final String ITEM = "ITEM";
		final String SKU = "SKU";
		final String QTY = "QTY";
		final String ACTION = "ACTION";

		Document documentXML = new DocumentImpl();
		DocumentBuilderFactory dbFactory = DocumentBuilderFactoryImpl.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = dbFactory.newDocumentBuilder();
			documentXML = docBuilder.newDocument(); 
			{
				Element partnerContext = documentXML.createElement(PARTNERCONTEXT);
				{
					Element header = documentXML.createElement(HEADER);
					{
						Element partner = documentXML.createElement(PARTNER);
						partner.setAttribute(PARTNER_ID, MCAFEE_PARTNERID);
						header.appendChild(partner);
					}
					partnerContext.appendChild(header);
					Element data = documentXML.createElement(DATA);
					{
						Element customerContext = documentXML.createElement(CUSTOMERCONTEXT);
						customerContext.setAttribute(ID, cvCuenta.getCvNumberAccount());
//						customerContext.setAttribute(ID, "17000138");
						customerContext.setAttribute(REQUESTTYPE, "NEW");
						{
							Element account = documentXML.createElement(ACCOUNT);
							{
								Element emailAddress = documentXML.createElement(EMAILADDRESS);
								emailAddress.setTextContent(cvCuenta.getCorreoContacto());
								Element firstName = documentXML.createElement(FIRSTNAME);

								String name = cvCuenta.getCvNameAccount();

								if(name!=null&&name.length()>20){
									name = cvCuenta.getCvNameAccount().substring(0, 20);
								}

								CDATASection cdataFName = documentXML.createCDATASection(name);
								firstName.appendChild(cdataFName);
								Element lastName = documentXML.createElement(LASTNAME);
								CDATASection cdataLName = documentXML.createCDATASection(name);
								lastName.appendChild(cdataLName);
								Element password = documentXML.createElement(PASSWORD);
								password.setTextContent("");
								Element preferences = documentXML.createElement(PREFERENCES);
								{
									Element preference = documentXML.createElement(PREFERENCE);
									preference.setAttribute(TYPE, "LANG");
									preference.setTextContent("es-mx");
									preferences.appendChild(preference);
								}
								account.appendChild(emailAddress);
								account.appendChild(firstName);
								account.appendChild(lastName);
								account.appendChild(password);
								account.appendChild(preferences);
							}
							customerContext.appendChild(account);
							
							DateFormat dfm = new SimpleDateFormat("ddMMyyyyHHmmss"+Calendar.getInstance().getTimeInMillis());
							String partnerRef = cvCuenta.getCvNumberAccount() + "-" + encryptMD5(dfm.toString());
							Element order = documentXML.createElement(ORDER);
							order.setAttribute(PARTNERREF, partnerRef);
							order.setAttribute(REF, "");
							{
								Element items = documentXML.createElement(ITEMS);
								{
									Element item = documentXML.createElement(ITEM);
									item.setAttribute(SKU, MCAFEE_SKU);
									item.setAttribute(QTY, MCAFEE_QTY);
									item.setAttribute(ACTION, MCAFEE_ACTION);
									items.appendChild(item);
								}
								order.appendChild(items);
							}
							customerContext.appendChild(order);
						}
						data.appendChild(customerContext);
					}
					partnerContext.appendChild(data);
				}
				documentXML.appendChild(partnerContext);
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return documentXML;
	}
	
	
	/**
	 * Genera un string a partir de un documento XML
	 * @param documentXML El documento XML
	 * @return un string con el texto del XML
	 */
	private String xmlToString(Document documentXML) {
		StringWriter strWriter = null;
		XMLSerializer seliarizadorXML = null;
		OutputFormat formatoSalida = null;
		try {
			seliarizadorXML = new XMLSerializer();
			strWriter = new StringWriter();
			formatoSalida = new OutputFormat();
			// 1. Establecer el formato
			formatoSalida.setEncoding("ISO-8859-1");
			formatoSalida.setVersion("1.0");
			formatoSalida.setIndenting(true);
			formatoSalida.setIndent(4);
			// 2. Definir un objeto donde se generara el codigo
			seliarizadorXML.setOutputCharStream(strWriter);
			// 3. Aplicar el formato
			seliarizadorXML.setOutputFormat(formatoSalida);
			// 4. Serializar documento XML
			seliarizadorXML.serialize(documentXML);
			strWriter.close();
		} catch (IOException ioEx) {
			System.out.println("Error : " + ioEx);
		}
		return strWriter.toString();
	}

	/**
	 * Encripta un texto usando el algoritmo MD5
	 * @param data El texto a encriptar
	 * @return un String con el texto encriptado
	 */
	private String encryptMD5(String code){
		try{			
			MessageDigest md = MessageDigest.getInstance("MD5");			
			byte[] input = code.getBytes();	
			input=md.digest(input);			
			code = toHexadecimal(input);
			return code;
		}catch(Exception e){
			return code;
		}
	}	
	
	/**
	 * Genera la representaci\u00F3n XML del dato encriptado previamente
	 * @param datos Los datos encriptados 
	 * @return un String con el dato encriptado en hexadecimal 
	 */
	private static String toHexadecimal(byte[] datos) { 
		String resultado=""; 
		ByteArrayInputStream input = new ByteArrayInputStream(datos); 
		String cadAux; 
		boolean ult0=false;
		int leido = input.read(); 
		while(leido != -1) { 
			cadAux = Integer.toHexString(leido); 
			if(cadAux.length() < 2){ //Hay que a�adir un 0 
				resultado += "0";
			if(cadAux.length() == 0)
				ult0=true;
			}else{ ult0=false;}                    
			resultado += cadAux; 
			leido = input.read(); 
		} 
		if(ult0)//quitamos el 0 si es un caracter aislado
			resultado=
				resultado.substring(0, resultado.length()-2)+resultado.charAt(resultado.length()-1);
		return resultado; 
	}
	
	/**
	 * Obtiene la cuenta de Mcafee del usuario
	 * @param cvAccount El numero de cuenta del usuario de Cablevision
	 * @return un McafeeUser
	 */
	private CvMcafeeUser cuentaMcafee(Long cvAccount) {
		CvMcafeeUser mcafeeUser = null;
		try {
			mcafeeUser = getMcafeeDownloadsService().getMcafeeUserByAccount(cvAccount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mcafeeUser;
	}
	
	/**
	 * Regresa un boolean que indica si aun tiene descargas disponibles
	 * @param account
	 * @return 	true: tiene descargas disponibles
	 * 			false: ya no tiene descargas disponibles
	 */
	private boolean downzStillAvailable(Long userId) {
		try {
//			CvMcafeeReset reset = getMcafeeDownloadsService().getMcafeeReset(account);
//			
//			if(reset != null) {
//				if(reset.getMreIntentos()*1 <= 4 ) {
			List<CvMcafeeDownload> mcafeeDownloads = getMcafeeDownloadsService().getMcafeeDownloadsByUserAccount(userId);
			if(mcafeeDownloads.size() < 5) {
				return true;
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Si ha excedido, mandar mensaje de error de que ha excedido el l\u00EDmite de descargas
		return false;
	}
	

	/**
	 * Callback that is invoked when this controller instance is created.
	 */
	@Override
	protected void onCreate() {
	}

	/**
	 * Callback that is invoked when this controller instance is destroyed.
	 */
	@Override
	protected void onDestroy(HttpSession session) {
	}

	private IMcafeeDownloadsService getMcafeeDownloadsService() {
		if(mcafeeDownloadsService==null){
			BeanFactory factory = DefaultLocatorFactory.getInstance("classpath*:/com/cablevision/conf/cablevisionBeanRefContext.xml")
			.useBeanFactory("cablevision-context")
			.getFactory();
			mcafeeDownloadsService = (IMcafeeDownloadsService)factory.getBean("McafeeDownloadsService");
			
		}
		return mcafeeDownloadsService;
	}

	public void setMcafeeDownloadsService(
			IMcafeeDownloadsService mcafeeDownloadsService) {
		this.mcafeeDownloadsService = mcafeeDownloadsService;
	}
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path= "showErrors.jsp") })
	public Forward catchErrors() {
		String errorMessage = getMessageResources("error").getMessage("mcafee.error.unknown");
		switch(errorCode) {
		case 1: errorMessage = getMessageResources("error").getMessage("mcafee.error.emptyResponse"); break;
		case 2: errorMessage = getMessageResources("error").getMessage("mcafee.error.invalidXml"); break;
		case 3: errorMessage = getMessageResources("error").getMessage("mcafee.error.limit"); break;
		case 4: errorMessage = getMessageResources("error").getMessage("mcafee.error.package"); break;
		case 5: errorMessage = getMessageResources("error").getMessage("mcafee.error.service"); break;
		}
		getSession().setAttribute("errorMessage", errorMessage);
		return new Forward("success");
	}
	
	@Jpf.FormBean
	public static class ReportBean implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		
		private String mes;
		private String dia;
		private String fechaDe;
		private String fechaA;
		private String cuenta;
		private String estatus;
		
		public String getMes() {
			return mes;
		}
		public void setMes(String mes) {
			this.mes = mes;
		}
		public String getDia() {
			return dia;
		}
		public void setDia(String dia) {
			this.dia = dia;
		}
		public String getFechaDe() {
			return fechaDe;
		}
		public void setFechaDe(String fechaDe) {
			this.fechaDe = fechaDe;
		}
		public String getFechaA() {
			return fechaA;
		}
		public void setFechaA(String fechaA) {
			this.fechaA = fechaA;
		}
		public String getCuenta() {
			return cuenta;
		}
		public void setCuenta(String cuenta) {
			this.cuenta = cuenta;
		}
		public String getEstatus() {
			return estatus;
		}
		public void setEstatus(String estatus) {
			this.estatus = estatus;
		}
		
	}
	
}
