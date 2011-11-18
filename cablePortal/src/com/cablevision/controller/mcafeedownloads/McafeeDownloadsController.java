package com.cablevision.controller.mcafeedownloads;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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

import jwm.jdom.JDOMException;
import jwm.jdom.input.SAXBuilder;

import org.apache.beehive.netui.pageflow.Forward;
import org.apache.beehive.netui.pageflow.annotations.Jpf;
import org.apache.xerces.dom.DocumentImpl;
import org.apache.xerces.jaxp.DocumentBuilderFactoryImpl;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import com.cablevision.service.impl.McafeeDownloadsSpringService;
import com.cablevision.util.Constantes;
import com.cablevision.util.MailUtil;
import com.cablevision.util.ResponseToServiceRequest;
import com.cablevision.util.RespuestaToMyAccount;
import com.cablevision.util.ValidateErrors;
import com.cablevision.vo.CvMcafee;
import com.cablevision.vo.CvMcafeeDownload;
import com.cablevision.vo.CvMcafeeUser;
import com.cablevision.vo.CvMcafeesuscribed;
import com.cablevision.vo.McaffeeVO;

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
	private static String MCAFEE_NEW_SKU = "";
	private static String MCAFEE_QTY = "";
	private static String MCAFEE_NEW_ACTION = "";
	private static String MCAFEE_CANCEL_ACTION = "";
	private static String MCAFEE_URLSUSCRIPTION = "";
	private static String MCAFEE_URLDOWNLOAD = "";
	private static String MCAFEE_RETURNURL = "";
	private int errorCode = 0;
	private Logger log = Logger.getLogger(McafeeDownloadsController.class); 
	
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
		String tipoProducto = form.getTipoProducto();
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
					if(tipoProducto.equalsIgnoreCase("ANTERIOR")){
						CvMcafeeUser mcafeeUser = getMcafeeDownloadsService().getMcafeeUserByAccount(Long.parseLong(cuenta));
						
						//List<CvMcafeeDownload> listDownloads = getMcafeeDownloadsService().getMcafeeDownloadsByUserAccount(mcafeeUser.getMusId());
						
						if(mcafeeUser != null){
							forward.addActionOutput("mcafeeUser", mcafeeUser);
						}
						
						/*if(listDownloads != null && !listDownloads.isEmpty()){
							forward.addActionOutput("listDownloads", listDownloads);
						}*/
	
						if(mcafeeUser == null ){//&& (listDownloads == null || listDownloads.isEmpty())){
							errors.add("No se Encontraron Datos para la Cuenta Especificada");
							forward.addActionOutput("errors", errors);
						}
					}else if(tipoProducto.equalsIgnoreCase("NUEVO")){
						CvMcafee mcafee = getMcafeeDownloadsService().getMcafeeByAccount(Long.parseLong(cuenta));
						
						if(mcafee != null){
							forward.addActionOutput("mcafeeUserNuevo", mcafee);
						}else{
							errors.add("No se Encontraron Datos para la Cuenta Especificada");
							forward.addActionOutput("errors", errors);
						}
						
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
				
			List resumenFechas = getMcafeeDownloadsService().getResumenPorFecha(fechaInicio, fechaFinal, status,tipoProducto);
			
			/*List origen = getMcafeeDownloadsService().getOrigen(fechaInicio, fechaFinal, status);
			
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
			*/
			if(resumenFechas.size() > 0){
				//forward.addActionOutput("origen", origen);
				log.debug("pos0"+resumenFechas.get(0));
				log.debug("pos1"+resumenFechas.get(1));
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
			List resumen = getMcafeeDownloadsService().getResumen(tipoProducto);
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
		
		log.debug("LLAMADA A MOSTRAR RESUMEN::"+form.getEstatus());
		
		List resumen = getMcafeeDownloadsService().getResumen(form.getTipoProducto());
		forward.addActionOutput("lista", resumen);
		forward.addActionOutput("meses", generaComboMes());
		forward.addActionOutput("form", form);
		
		return forward;
	}
	
	@Jpf.Action(
			forwards = { 
					@Jpf.Forward(name = "success", path= "reporte.jsp")
			},
			loginRequired = false
	)
	public Forward descargaResumen(ReportBean form) throws Exception{
		Forward forward = new Forward("success");
		
		log.debug("LLAMADA A descarga RESUMEN::"+form.getEstatus());
		
		String datosReporte =  getMcafeeDownloadsService().generaResumenExcel(form.getTipoProducto());
		String nombreArchivo = "resumen_producto_"+form.getTipoProducto();
		
		byte[] arrDatos = datosReporte.getBytes();
		
		ByteArrayOutputStream stream =  new ByteArrayOutputStream(arrDatos.length);
		stream.write(arrDatos);
				
		this.getResponse().setHeader("Cache-Control", "max-age=30");
		this.getResponse().setContentType("application/msexcel");
		this.getResponse().setHeader("Content-length", "" + arrDatos.length);
		this.getResponse().setHeader("Content-disposition", "inline;filename="+nombreArchivo);
		
		stream.writeTo(this.getResponse().getOutputStream());
		
		stream.flush();
		stream.close();	
		
		
		
		return null;
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
		
		String datosReporte = getMcafeeDownloadsService().generaReporte(format.parse(form.getFechaDe()), form.getFechaA()!=null?format.parse(form.getFechaA()):null, form.getEstatus(),form.getTipoProducto(), nombreArchivo);
		
		byte[] arrDatos = datosReporte.getBytes();
		
		ByteArrayOutputStream stream =  new ByteArrayOutputStream(arrDatos.length);
		stream.write(arrDatos);
				
		this.getResponse().setHeader("Cache-Control", "max-age=30");
		this.getResponse().setContentType("application/msexcel");
		this.getResponse().setHeader("Content-length", "" + arrDatos.length);
		this.getResponse().setHeader("Content-disposition", "inline;filename="+nombreArchivo);
		
		stream.writeTo(this.getResponse().getOutputStream());
		
		stream.flush();
		stream.close();	
		
		
		
		/*
		GenericURL url = GenericURL.createGenericURL(getRequest(),getResponse());
		url.setPath("/mcafee/tmp/"+nombreArchivo);
		
		StringBuffer sbContentDispValue = new StringBuffer();
		sbContentDispValue.append("attachment");
		sbContentDispValue.append("; filename=");
		sbContentDispValue.append(nombreArchivo);
		
		this.getResponse().setHeader("Content-disposition",sbContentDispValue.toString());
		*/
		//return new Forward(new URI(url.toString()));
		return null;
		
	}
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path= "infoMcafee.jsp") })
	public Forward infoMcafee() {
		log.info("INFOMCAFEE");
		return new Forward("success");
	}

	@Jpf.Action(
			forwards = { 
					@Jpf.Forward(name = "success", path= "downloadMcafee.jsp"), 
					@Jpf.Forward(name = "error", action= "catchErrors"),
			}
	)
	public Forward downloadMcafee() throws RemoteException, ServiceException, ParserConfigurationException, 
	SAXException, IOException, UnsupportedEncodingException {
		log.info("ENTRA A downloadMcafee");
		Forward forward = new Forward("error");
			
		/*getRequest().getSession(false).setAttribute("urlmcafee", "http://www.google.com");
		//getResponse().sendRedirect(urlDownload);
		String url = getRequest().getScheme()+"://"+getRequest().getServerName()+getRequest().getContextPath();
		log.debug("URL::"+url);
		forward = new Forward("success");
		*/
		
		MCAFEE_PARTNERID = getMessageResources("configuracion").getMessage("mcafee.partnerid");
		MCAFEE_SKU = getMessageResources("configuracion").getMessage("mcafee.sku");
		MCAFEE_NEW_SKU = getMessageResources("configuracion").getMessage("mcafee.new.sku");
		MCAFEE_QTY = getMessageResources("configuracion").getMessage("mcafee.qty");
		MCAFEE_NEW_ACTION = getMessageResources("configuracion").getMessage("mcafee.new.action");
		MCAFEE_CANCEL_ACTION = getMessageResources("configuracion").getMessage("mcafee.cancel.action");
		MCAFEE_URLSUSCRIPTION = getMessageResources("configuracion").getMessage("mcafee.urlsuscription");
		MCAFEE_URLDOWNLOAD = getMessageResources("configuracion").getMessage("mcafee.urldownload");
		MCAFEE_RETURNURL = getMessageResources("configuracion").getMessage("mcafee.returnurl");
		errorCode = 0;
		
		String sessionAccountId = "";

		if(getSession().getAttribute(Constantes.SESSION_ACCOUNT_ID)!=null)
			sessionAccountId = (String)getSession().getAttribute(Constantes.SESSION_ACCOUNT_ID);

		// obtiene el detalle de la cuenta de usuario de cablevision
		RespuestaToMyAccount cvCuenta = (RespuestaToMyAccount)getSession().getAttribute(Constantes.SESSION_MI_CUENTA);
		log.info("OBTIENE CUENTA DE SESION");
		if(cvCuenta != null) {
			if("Y".equalsIgnoreCase(cvCuenta.getIpCable())){
			log.debug("tiene internet::"+cvCuenta.getIpCable());
			
			// obtiene el objeto usuario de mcafee
				Long account = Long.parseLong(cvCuenta.getCvNumberAccount()); 
				CvMcafeeUser mcafeeUser = cuentaMcafeeUser(account);
				
				
				//valida si el usuario exite y si esta activo
				if ( mcafeeUser != null && (McafeeDownloadsSpringService.STS_ACTIVO.equalsIgnoreCase(mcafeeUser.getMusCvstatus().trim()) ||
						McafeeDownloadsSpringService.STS_INACTIVO.equalsIgnoreCase(mcafeeUser.getMusCvstatus().trim()))) {
					log.debug("El usuario con cuenta "+account+"existe en la tabla anterior");
					//se envia que de de baja el viejo producto ya que antes no consideraba que si entra desde el portal solo va a manejar el nuevo producto
					Document xmlCancel = getMcafeeDownloadsService().generateCancelXML(account, mcafeeUser.getMusReference(),false);
					String xmlStringCancelResponse = getMcafeeDownloadsService().getXMLResponse(xmlCancel);
					if ( !StringUtils.isEmpty(xmlStringCancelResponse) ){
						String returnCode = getMcafeeDownloadsService().getCodeFromXML(xmlStringCancelResponse);
						log.debug("CODIGO RESPUESTA DE MCAFEE::"+returnCode);
						if ( !"1000".equals(returnCode) && !"5001".equals(returnCode) && !"5002".equals(returnCode) ){
							errorCode = 1;
							return forward;
						}
					}else{
						errorCode = 1;
						return forward;
					}
				}else if(mcafeeUser != null && (McafeeDownloadsSpringService.STS_CANCELADO.equalsIgnoreCase(mcafeeUser.getMusCvstatus().trim()))){
					errorCode = 6;
					return forward;
				}else if(mcafeeUser != null && (McafeeDownloadsSpringService.STS_SUSPENDIDO.equalsIgnoreCase(mcafeeUser.getMusCvstatus().trim()))){
					errorCode = 7;
					return forward;
				}
				
				CvMcafee mcafee1 = cuentaMcafee(account);
				if(mcafee1 != null){
					if(McafeeDownloadsSpringService.STS_CANCELADO.equalsIgnoreCase(mcafee1.getMcaCvstatus().trim())){
						log.error("AL DESCARGAR DE PORTAL: EL USUARIO DE LA CUENTA "+account+" SE ENCUENTRA EN ESTATUS CANCELADO EN LA TABLA CV_MCAFEE");
						errorCode = 6;
						return forward;
					}else if(McafeeDownloadsSpringService.STS_SUSPENDIDO.equalsIgnoreCase(mcafee1.getMcaCvstatus().trim())){
						log.error("AL DESCARGAR DE PORTAL: EL USUARIO DE LA CUENTA "+account+" SE ENCUENTRA EN ESTATUS SUSPENDIDO EN LA TABLA CV_MCAFEE");
						errorCode = 7;
						return forward;
					}//else if(McafeeDownloadsSpringService.STS_ACTIVO.equalsIgnoreCase(mcafee1.getMcaCvstatus().trim())){
						//log.error("AL DESCARGAR DE PORTAL: EL USUARIO DE LA CUENTA "+account+" SE ENCUENTRA EN ESTATUS ACTIVO EN LA TABLA CV_MCAFEE");
						//errorCode = 8;
						//return forward;
					//}
				}
				
				// genera el XML y luego obtiene el usuario de mcafee, devuelo 0 si no tiene
				Document xml = getMcafeeDownloadsService().generateXML(cvCuenta);
				String xmlStringResponse = getMcafeeDownloadsService().getXMLResponse(xml);
				String code = getMcafeeDownloadsService().getCodeFromXML(xmlStringResponse);
				if ( xmlStringResponse != null && ("1000".equals(code) || "5001".equals(code) || "5002".equals(code)) ) {

					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder builder = null;
					Document xmlResponse = null;

					builder = factory.newDocumentBuilder();
					InputSource is = new InputSource(new StringReader(xmlStringResponse));
					xmlResponse = builder.parse(is);
					Timestamp fechaDescarga = new Timestamp(System.currentTimeMillis());
					CvMcafee newMcafeeUser = getMcafeeDownloadsService().getMcafeeUserFromResponse(xmlResponse, cvCuenta, sessionAccountId);
					if(newMcafeeUser != null) {
						try {
							if( mcafeeUser!=null ){
								if ( McafeeDownloadsSpringService.STS_ACTIVO.equalsIgnoreCase(mcafeeUser.getMusCvstatus().trim()) ) {
									//cambiar el estatus a migrado
									getMcafeeDownloadsService().updateCvMcafeeUserStatus(mcafeeUser.getMusId(), McafeeDownloadsSpringService.STS_MIGRADO,"",fechaDescarga);
									newMcafeeUser.setMcaDatesuscribe(mcafeeUser.getMusDatesuscribe());
									log.info("EL ESTATUS DE LA CUENTA "+account+" FUE ACTUALIZADO DE ACTIVO A MIGRADO");
								}
							}
							
							CvMcafee mcafee = cuentaMcafee(account);
							if(mcafee != null){
									newMcafeeUser.setMcaId(mcafee.getMcaId());
							}
							newMcafeeUser.setMcaCvstatus(McafeeDownloadsSpringService.STS_ACTIVO);
							newMcafeeUser.setMcaFirstName(cvCuenta.getNombreContacto());
							newMcafeeUser.setMcaLastName(cvCuenta.getApellidoPaterno() + " " + cvCuenta.getApellidoMaterno());
							newMcafeeUser.setMcaDatestatus(fechaDescarga);
							getMcafeeDownloadsService().persistCvMcafee(newMcafeeUser);
						} catch (Exception e) {
							e.printStackTrace();
							errorCode = 0;
							return forward;
						}
						
						//si regresÃ³ un code n 5002 se debe mandar a mcafee un update del profile del usuario
						if("5002".equals(code)){
							log.debug("SE ENVIA MENSAJE DE UPDATE A MCAFEE");
							Document xmlUdapteProfile = getMcafeeDownloadsService().generateUpdatingProfileXML(cvCuenta);
							String xmlUdapteProfileResponse = getMcafeeDownloadsService().getXMLResponse(xmlUdapteProfile);
							log.info("LA RESPUESTA DEL MENSAJE DE UPDATE A MCAFEE ES::"+xmlUdapteProfileResponse);
							//TODO se hace algo con el error de update profile?
						}
						if(newMcafeeUser != null) {
									
					         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
					         String descripcion = "email:"+newMcafeeUser.getMcaEmailaddress()+" pwd:"+newMcafeeUser.getMcaPassword()+
					               " ref:"+newMcafeeUser.getMcaReference()+" fecha de suscripción:"+sdf.format(fechaDescarga)+
					               " fecha de descarga "+sdf.format(new Date());
					         log.debug("INFORMACION DE DESCARGA::"+ descripcion);
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
							URLEncoder.encode("EMAIL_ADDRESS","UTF-8") + "=" + URLEncoder.encode(newMcafeeUser.getMcaEmailaddress(),"UTF-8") + "&" +
							URLEncoder.encode("PASSWORD","UTF-8") + "=" + URLEncoder.encode(newMcafeeUser.getMcaPassword(),"UTF-8") + "&" +
							URLEncoder.encode("CCID","UTF-8") + "=" + URLEncoder.encode(Long.toString(newMcafeeUser.getMcaAccount()),"UTF-8") + "&" +
							URLEncoder.encode("AFF_ID","UTF-8") + "=" + URLEncoder.encode(MCAFEE_PARTNERID,"UTF-8") + "&" +
							URLEncoder.encode("RETURN_URL","UTF-8")	+ "=" + URLEncoder.encode(MCAFEE_RETURNURL,"UTF-8") + "&" +
							URLEncoder.encode("REMEMBER_ME","UTF-8") + "=" + URLEncoder.encode("0","UTF-8") + "&" +
							URLEncoder.encode("APP_CODE","UTF-8") + "=" + URLEncoder.encode("MVS","UTF-8");
							
							String url = getRequest().getScheme()+"://"+getRequest().getServerName()+getRequest().getContextPath();
							
							String urlcable = url+"/cablevision.portal?_nfpb=true&_nfxr=false&_pageLabel=soluciones_internet_servicios&_nfto=false";
							
							getMcafeeDownloadsService().enviaCorreoAlta(cvCuenta,newMcafeeUser,urlcable,url+"/");
							
							getRequest().getSession(false).setAttribute("urlmcafee", urlDownload);
							
							//getResponse().sendRedirect(urlDownload);
							
							forward = new Forward("success");

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
	
	
	/**
	 * Obtiene la cuenta de Mcafee del usuario
	 * @param cvAccount El numero de cuenta del usuario de Cablevision
	 * @return un McafeeUser
	 */
	private CvMcafeeUser cuentaMcafeeUser(Long cvAccount) {
		CvMcafeeUser mcafeeUser = null;
		try {
			mcafeeUser = getMcafeeDownloadsService().getMcafeeUserByAccount(cvAccount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mcafeeUser;
	}
	
	/**
	 * Obtiene la cuenta de Mcafee del usuario
	 * @param cvAccount El numero de cuenta del usuario de Cablevision
	 * @return un McafeeUser
	 */
	private CvMcafee cuentaMcafee(Long cvAccount) {
		CvMcafee mcafee = null;
		try {
			mcafee = getMcafeeDownloadsService().getMcafeeByAccount(cvAccount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mcafee;
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
		case 6: errorMessage = getMessageResources("error").getMessage("mcafee.error.cancelled"); break;
		case 7: errorMessage = getMessageResources("error").getMessage("mcafee.error.suspended"); break;
		case 8: errorMessage = getMessageResources("error").getMessage("mcafee.error.active"); break;
		
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
		private String tipoProducto;
		
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
		public String getTipoProducto() {
			return tipoProducto;
		}
		public void setTipoProducto(String tipoProducto) {
			this.tipoProducto = tipoProducto;
		}
		
	}
	
}
