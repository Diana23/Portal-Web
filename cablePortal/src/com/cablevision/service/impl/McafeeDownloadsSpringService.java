package com.cablevision.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.text.DateFormatter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.ServiceException;

import org.apache.beehive.netui.pageflow.Forward;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xerces.dom.DocumentImpl;
import org.apache.xerces.jaxp.DocumentBuilderFactoryImpl;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.access.DefaultLocatorFactory;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import weblogic.net.http.SOAPHttpsURLConnection;

import com.cablevision.ResponsetoSecretQuestionByAccount;
import com.cablevision.ToInterfase;
import com.cablevision.util.ConfigurationHelper;
import com.cablevision.util.Constantes;
import com.cablevision.util.MailUtil;
import com.cablevision.util.ResponseToSecretQuestion;
import com.cablevision.util.ResponseToServiceRequest;
import com.cablevision.util.RespuestaToMyAccount;
import com.cablevision.util.ValidateErrors;
import com.cablevision.vo.CvMcafeeDownload;
import com.cablevision.vo.CvMcafeeReset;
import com.cablevision.vo.CvMcafeeUser;
import com.cablevision.vo.CvMcafeesuscribed;
import com.cablevision.vo.McaffeeVO;
import com.cablevision.portal.ErrorVitriaException;
import com.cablevision.service.IMcafeeDownloadsService;
import com.cablevision.dao.IMcafeeDownloadsDao;
import com.cablevision.vo.CvMcafee;

/**
 * The service class for the entities: CvMcafeeDownload, CvMcafeeReset, CvMcafeeUser.
 *  
 */
public class McafeeDownloadsSpringService implements IMcafeeDownloadsService,ApplicationContextAware{
	/**
	 * The dao instance injected by Spring.
	 */
	private IMcafeeDownloadsDao dao;
	private String MCAFEE_PARTNERID ;
	private String MCAFEE_SKU ;
	private String MCAFEE_NEW_SKU ;
	private String MCAFEE_QTY ;
	private String MCAFEE_NEW_ACTION ;
	private String MCAFEE_CANCEL_ACTION ;
	private String MCAFEE_URLSUSCRIPTION ;
	private String MCAFEE_URLDOWNLOAD ;
	private String MCAFEE_RETURNURL ;
	private String MCAFEE_HOME;
	private String CORREO_FROM;
	private String CORREO_SUBJECT;
	private String CORREO_TEMPLATEID;
	public static final String STS_ACTIVO = "ACTIVO" ;
	public static final String STS_CANCELADO = "CANCELADO";
	public static final String STS_SUSPENDIDO = "SUSPENDIDO";
	public static final String STS_MIGRADO = "MIGRADO";
	public static final String STS_REACTIVADO = "REACTIVADO";
	public static final String STS_INACTIVO = "INACTIVO";
	public static final String RES_OK = "OK";
	public static final String RES_ERROR = "ERROR";
	private ApplicationContext applicationContext;//inyectado por spring
	private Logger log = Logger.getLogger(McafeeDownloadsSpringService.class);
	transient ToInterfase vitriaClient;
	
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "McafeeDownloadsService";
	
	public McafeeDownloadsSpringService() {
		super();
	}
	public String getMCAFEE_PARTNERID() {
		return MCAFEE_PARTNERID;
	}
	public void setMCAFEE_PARTNERID(String mcafee_partnerid) {
		MCAFEE_PARTNERID = mcafee_partnerid;
	}
	public String getMCAFEE_SKU() {
		return MCAFEE_SKU;
	}
	public void setMCAFEE_SKU(String mcafee_sku) {
		MCAFEE_SKU = mcafee_sku;
	}
	public String getMCAFEE_NEW_SKU() {
		return MCAFEE_NEW_SKU;
	}
	public void setMCAFEE_NEW_SKU(String mcafee_new_sku) {
		MCAFEE_NEW_SKU = mcafee_new_sku;
	}
	public String getMCAFEE_QTY() {
		return MCAFEE_QTY;
	}
	public void setMCAFEE_QTY(String mcafee_qty) {
		MCAFEE_QTY = mcafee_qty;
	}
	public String getMCAFEE_NEW_ACTION() {
		return MCAFEE_NEW_ACTION;
	}
	public void setMCAFEE_NEW_ACTION(String mcafee_new_action) {
		MCAFEE_NEW_ACTION = mcafee_new_action;
	}
	public String getMCAFEE_CANCEL_ACTION() {
		return MCAFEE_CANCEL_ACTION;
	}
	public void setMCAFEE_CANCEL_ACTION(String mcafee_cancel_action) {
		MCAFEE_CANCEL_ACTION = mcafee_cancel_action;
	}
	public String getMCAFEE_URLSUSCRIPTION() {
		return MCAFEE_URLSUSCRIPTION;
	}
	public void setMCAFEE_URLSUSCRIPTION(String mcafee_urlsuscription) {
		MCAFEE_URLSUSCRIPTION = mcafee_urlsuscription;
	}
	public String getMCAFEE_URLDOWNLOAD() {
		return MCAFEE_URLDOWNLOAD;
	}
	public void setMCAFEE_URLDOWNLOAD(String mcafee_urldownload) {
		MCAFEE_URLDOWNLOAD = mcafee_urldownload;
	}
	public String getMCAFEE_RETURNURL() {
		return MCAFEE_RETURNURL;
	}
	public void setMCAFEE_RETURNURL(String mcafee_returnurl) {
		MCAFEE_RETURNURL = mcafee_returnurl;
	}
	public ToInterfase getVitriaClient() {
		
			if(vitriaClient == null){
				log.info("vitriClient es nulo");
				vitriaClient = (ToInterfase)applicationContext.getBean("VitriaClient");
			}
			log.info("vitriClient no es nulo");
			return vitriaClient;
	}
	public void setVitriaClient(ToInterfase vitriaClient) {
		this.vitriaClient = vitriaClient;
	}
	/**
	 * Returns the singleton <code>IMcafeeDownloadsService</code> instance.
	 */
	public static IMcafeeDownloadsService getInstance(ApplicationContext context) {
		return (IMcafeeDownloadsService)context.getBean(SERVICE_BEAN_ID);
	}
	@Override
	public List getResumen(String tipoProducto)throws Exception{
		try {
			return getDao().getResumenDownloads(tipoProducto);
		} catch (RuntimeException e) {
			throw new Exception("getResumen failed: " + e.getMessage());
		}
	}
	
	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvMcafeeDownload findCvMcafeeDownloadById(Long id) throws Exception {
		try {
			return getDao().findCvMcafeeDownloadById(id);
		} catch (RuntimeException e) {
			throw new Exception("findCvMcafeeDownloadById failed with the id " + id + ": " + e.getMessage());
		}
	}
	/**
	 * Return all persistent instances of the <code>CvMcafeeDownload</code> entity.
	 */
	public List<CvMcafeeDownload> findAllCvMcafeeDownloads() throws Exception {
		try {
			return getDao().findAllCvMcafeeDownloads();
		} catch (RuntimeException e) {
			throw new Exception("findAllCvMcafeeDownloads failed: " + e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvMcafeeDownload> findCvMcafeeDownloadsByExample(CvMcafeeDownload cvMcafeeDownload) throws Exception {
		try {
			return getDao().findByExample(cvMcafeeDownload);
		} catch (RuntimeException e) {
			throw new Exception("findCvMcafeeDownloadsByExample failed: " + e.getMessage());
		}
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvMcafeeDownload(CvMcafeeDownload cvMcafeeDownload) throws Exception {
		try {
			getDao().persistCvMcafeeDownload(cvMcafeeDownload);
		} catch (RuntimeException e) {
			throw new Exception("persistCvMcafeeDownload failed: " + e.getMessage());
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvMcafeeDownload(CvMcafeeDownload cvMcafeeDownload) throws Exception {
		try {
			getDao().removeCvMcafeeDownload(cvMcafeeDownload);
		} catch (RuntimeException e) {
			throw new Exception("removeCvMcafeeDownload failed: " + e.getMessage());
		}
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvMcafeeReset findCvMcafeeResetById(Long id) throws Exception {
		try {
			return getDao().findCvMcafeeResetById(id);
		} catch (RuntimeException e) {
			throw new Exception("findCvMcafeeResetById failed with the id " + id + ": " + e.getMessage());
		}
	}
	/**
	 * Return all persistent instances of the <code>CvMcafeeReset</code> entity.
	 */
	public List<CvMcafeeReset> findAllCvMcafeeResets() throws Exception {
		try {
			return getDao().findAllCvMcafeeResets();
		} catch (RuntimeException e) {
			throw new Exception("findAllCvMcafeeResets failed: " + e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvMcafeeReset> findCvMcafeeResetsByExample(CvMcafeeReset cvMcafeeReset) throws Exception {
		try {
			return getDao().findByExample(cvMcafeeReset);
		} catch (RuntimeException e) {
			throw new Exception("findCvMcafeeResetsByExample failed: " + e.getMessage());
		}
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvMcafeeReset(CvMcafeeReset cvMcafeeReset) throws Exception {
		try {
			getDao().persistCvMcafeeReset(cvMcafeeReset);
		} catch (RuntimeException e) {
			throw new Exception("persistCvMcafeeReset failed: " + e.getMessage());
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvMcafeeReset(CvMcafeeReset cvMcafeeReset) throws Exception {
		try {
			getDao().removeCvMcafeeReset(cvMcafeeReset);
		} catch (RuntimeException e) {
			throw new Exception("removeCvMcafeeReset failed: " + e.getMessage());
		}
	}

	/**
	 * Return all persistent instances of the <code>CvMcafeeUser</code> entity.
	 */
	public List<CvMcafeeUser> findAllCvMcafeeUsers() throws Exception {
		try {
			return getDao().findAllCvMcafeeUsers();
		} catch (RuntimeException e) {
			throw new Exception("findAllCvMcafeeUsers failed: " + e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvMcafeeUser> findCvMcafeeUsersByExample(CvMcafeeUser cvMcafeeuser) throws Exception {
		try {
			return getDao().findByExample(cvMcafeeuser);
		} catch (RuntimeException e) {
			throw new Exception("findCvMcafeeUsersByExample failed: " + e.getMessage());
		}
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvMcafeeUser(CvMcafeeUser cvMcafeeuser) throws Exception {
		try {
			getDao().persistCvMcafeeUser(cvMcafeeuser);
		} catch (RuntimeException e) {
			throw new Exception("persistCvMcafeeUser failed: " + e.getMessage());
		}
	}
	
	
	public void updateCvMcafeeUserStatus(Long id, String status,String motivo,Timestamp fechaStatus) throws Exception {
		try {
			getDao().updateCvMcafeeUserStatus(id, status,motivo,fechaStatus);
		} catch (RuntimeException e) {
			throw new Exception("updateCvMcafeeUserStatus failed: " + e.getMessage());
		}
	}
	
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvMcafeeUser(CvMcafeeUser cvMcafeeuser) throws Exception {
		try {
			getDao().removeCvMcafeeUser(cvMcafeeuser);
		} catch (RuntimeException e) {
			throw new Exception("removeCvMcafeeUser failed: " + e.getMessage());
		}
	}

	/**
	 * Called by Spring using the injection rules specified in 
	 * the Spring beans file "applicationContext.xml".
	 */
	public void setDao(IMcafeeDownloadsDao dao) {
		this.dao = dao;
	}
	public IMcafeeDownloadsDao getDao() {
		return this.dao;
	}
	
	@Override
	public CvMcafeeUser getMcafeeUserByAccount(Long account) throws Exception {
		return this.dao.getMcafeeUserByAccount(account);
	}
	@Override
	public CvMcafeeReset getMcafeeReset(Long account) throws Exception {
		return this.dao.getMcafeeReset(account);
	}
	@Override
	public List<CvMcafeeDownload> getMcafeeDownloadsByUserAccount(Long account)
			throws Exception {
		return this.dao.getMcafeeDownloadsByUserAccount(account);
	}
	@Override
	public List getOrigen(Date fechaInicio, Date fechaFinal, String status) {
		return getDao().getOrigen(fechaInicio, fechaFinal, status);
	}
	@Override
	public List getResumenPorFecha(Date fechaInicio, Date fechaFinal,
			String status, String tipoproducto) {
		return getDao().getResumenPorFecha(fechaInicio, fechaFinal, status, tipoproducto);
	}
	
	public String generaResumenExcel(String tipoProducto){
	if(tipoProducto.trim().length() == 0)
		tipoProducto = "ANTERIOR";
		List datos = getDao().getResumenDownloads(tipoProducto);
		Iterator iter = datos.iterator();
		StringBuffer archivo = new StringBuffer();
		ArrayList<Long> listaCantidades = new ArrayList<Long>();
		ArrayList<String> listaFechas =new ArrayList<String>();
		ArrayList<Long> listaAcumulado = new ArrayList<Long>(); 
		Long cuenta = new Long(0);
		try{
			while (iter.hasNext()) {
				Object[] arr= (Object[])iter.next();  
				Long cantidad = Long.parseLong(arr[0].toString());
				listaCantidades.add(cantidad);
				System.out.println("cantidad::"+cantidad);
				
				cuenta = cuenta.longValue() + cantidad.longValue();
				listaAcumulado.add(cuenta);
				
				String _fecha = arr[1].toString();
				String _anio = "";
				String _mes = "";
				
				if(_fecha.length() >= 10){
					_anio = _fecha.substring(0, 4);
					System.out.println("ANIO:"+_anio);
					_mes  = _fecha.substring(5, 7);
					System.out.println("ANIO:"+_mes);
					_fecha = _anio+"-"+_mes;
					}
					
			
				listaFechas.add(_fecha);
				System.out.println("fecha::"+_fecha);
				

			}
			
			
		archivo.append("<table>");
		archivo.append("<tr>");
			archivo.append("<td>CABLEVISION - MCAFEE RESUMEN</td>");
		archivo.append("</tr>");
		archivo.append("<tr>");
			archivo.append("<td>Producto:"+tipoProducto+"</td>");
		archivo.append("</tr>");
		archivo.append("<tr>");
			archivo.append("<td>Año - Mes</td>");
			
			for (String dato : listaFechas) {
				archivo.append("<td>"+dato+"</td>");
			}
		
		archivo.append("</tr>");
		archivo.append("<tr>");
			archivo.append("<td>Total por mes</td>");
			
			for (Long dato : listaCantidades) {
				archivo.append("<td>"+dato.longValue()+"</td>");
			}
		archivo.append("</tr>");
			archivo.append("<td>Acumulado</td>");
			for (Long dato : listaAcumulado) {
				archivo.append("<td>"+dato.longValue()+"</td>");
			}
		archivo.append("<tr>");
			
		archivo.append("<tr>");
	archivo.append("</table>");
	
		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return archivo.toString();
	}
	
	@Override
	public String generaReporte(Date fechaInicio, Date fechaFinal,String status, String tipoProducto, String fileName) throws Exception{
		log.info("PARAMETROS fechaInicio:"+fechaInicio+" fechaFinal:"+fechaFinal+" status:"+status+" tipoProducto:"+tipoProducto+" fileName:"+fileName);
		
		Iterator iterator = getDao().getSuscripciones(fechaInicio, fechaFinal, status,0,4000,tipoProducto).iterator();
		
		List descargas = getDao().getResumenPorFecha(fechaInicio, fechaFinal, status,tipoProducto);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat formatLargo = new SimpleDateFormat("dd - MMMM - yyyy");
		
		/*File file = new File(ConfigurationHelper.getPropiedad("ruta.guardar.archivos")+"/mcafee/tmp/");
		if(!file.exists()){
			file.mkdirs();
		}
		FileWriter out = new FileWriter(ConfigurationHelper.getPropiedad("ruta.guardar.archivos")+"/mcafee/tmp/"+fileName);
		PrintWriter writer = new PrintWriter(out);
		*/
		log.debug("obj descargas:"+descargas);
		BigDecimal total = (BigDecimal)descargas.get(1);
		
		StringBuffer archivo = new StringBuffer();
		
		archivo.append("<table>");
		archivo.append("<tr><td colspan='6'>CABLEVISION - MCAFEE detalle</td></tr>");
		archivo.append("<tr><td colspan='6'>&nbsp;</td></tr>");
		archivo.append("<tr><td colspan='6'><b>"+formatLargo.format(fechaInicio));
		
		if(fechaFinal!=null){
			archivo.append(" al "+formatLargo.format(fechaFinal));
		}
		archivo.append("</b></td></tr>");
		archivo.append("<tr><td colspan='4'>&nbsp;</td></tr>");
		archivo.append("<tr><td colspan='4'><b># Usuarios Registrados</b></td><td><b>"+total+"</b></td></tr>");
		archivo.append("<tr><td colspan='4'>&nbsp;</td></tr>");
		
		archivo.append("<tr>");
		archivo.append("<td><b># Cuenta</b></td>");
		archivo.append("<td><b>Fecha de descarga</b></td>");
		//archivo.append("<td><b>Origen</b></td>");
		archivo.append("<td><b>Email</b></td>");
		//archivo.append("<td><b>Descargas</b></td>");
		archivo.append("<td><b>Estado</b></td>");
		archivo.append("</tr>");
		
		long inicio = System.currentTimeMillis();
		int cont = 1;
		
		if(tipoProducto.equalsIgnoreCase("ANTERIOR")){
			for(;iterator.hasNext();cont++){
				/*CvMcafeesuscribed suscribed = (CvMcafeesuscribed)iterator.next();
	
				archivo.append("<tr>");
				archivo.append("<td>"+suscribed.getMteAccount()+"</td>");
				archivo.append("<td>"+format.format(suscribed.getMteSuscribedate())+"</td>");
				//archivo.append("<td>"+suscribed.getMteSource()+"</td>");
				archivo.append("<td>"+suscribed.getMteEmail()+"</td>");
				//archivo.append("<td>"+suscribed.getMteAttemps()+"</td>");
				archivo.append("<td>"+suscribed.getMteStatus()+"</td>");
				archivo.append("</tr>");
				//writer.flush();
	*/
				
				CvMcafeeUser suscribed = (CvMcafeeUser)iterator.next();
				
				archivo.append("<tr>");
				archivo.append("<td>"+suscribed.getMusAccount()+"</td>");
				archivo.append("<td>"+format.format(suscribed.getMusDatesuscribe())+"</td>");
				//archivo.append("<td>"+suscribed.getMteSource()+"</td>");
				archivo.append("<td>"+suscribed.getMusEmailaddress()+"</td>");
				//archivo.append("<td>"+suscribed.getMteAttemps()+"</td>");
				archivo.append("<td>"+suscribed.getMusCvstatus()+"</td>");
				archivo.append("</tr>");
				if(!iterator.hasNext()&&cont<total.intValue()){
					System.out.println(""+cont+" - "+(System.currentTimeMillis()-inicio));
					inicio = System.currentTimeMillis();
					iterator = getDao().getSuscripciones(fechaInicio, fechaFinal, status,cont,4000,tipoProducto).iterator();
				}
			}
		}else {
			for(;iterator.hasNext();cont++){
				CvMcafee suscribed = (CvMcafee)iterator.next();
	
				archivo.append("<tr>");
				archivo.append("<td>"+suscribed.getMcaAccount()+"</td>");
				archivo.append("<td>"+format.format(suscribed.getMcaDatesuscribe())+"</td>");
				//archivo.append("<td>"+suscribed.getMteSource()+"</td>");
				archivo.append("<td>"+suscribed.getMcaEmailaddress()+"</td>");
				//archivo.append("<td>"+suscribed.getMteAttemps()+"</td>");
				archivo.append("<td>"+suscribed.getMcaCvstatus()+"</td>");
				archivo.append("</tr>");
				//writer.flush();
	
				if(!iterator.hasNext()&&cont<total.intValue()){
					System.out.println(""+cont+" - "+(System.currentTimeMillis()-inicio));
					inicio = System.currentTimeMillis();
					iterator = getDao().getSuscripciones(fechaInicio, fechaFinal, status,cont,4000,tipoProducto).iterator();
				}
			}
		}
		
		archivo.append("</table>");

		return archivo.toString();
	}
	@Override
	public CvMcafeesuscribed getSuscribdByAccount(Integer account){
		return getDao().getSuscribdByAccount(account);
	}
	@Override
	public void aumentarDownload(String account){
		getDao().aumentarDownload(account);
	}
	@Override
	public void persistCvMcafeesuscribed(CvMcafeesuscribed suscribed){
		getDao().persistCvMcafeesuscribed(suscribed);
	}
	/**
	 * Find an entity by its id (primary key).
	 * @return  The found entity instance or null if the entity does not exist.
	 */
	public CvMcafee findCvMcafeeById(Long id) throws Exception {
		try {
			return getDao().findCvMcafeeById(id);
		} catch (RuntimeException e) {
			throw new Exception("findCvMcafeeById failed with the id " + id
					+ ": " + e.getMessage());
		}
	}
	/**
	 * Return all persistent instances of the <code>CvMcafee</code> entity.
	 */
	public List<CvMcafee> findAllCvMcafees() throws Exception {
		try {
			return getDao().findAllCvMcafees();
		} catch (RuntimeException e) {
			throw new Exception("findAllCvMcafees failed: " + e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvMcafee> findCvMcafeesByExample(CvMcafee cvMcafee)
			throws Exception {
		try {
			return getDao().findByExample(cvMcafee);
		} catch (RuntimeException e) {
			throw new Exception("findCvMcafeesByExample failed: "
					+ e.getMessage());
		}
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvMcafee(CvMcafee cvMcafee) throws Exception {
		try {
			getDao().persistCvMcafee(cvMcafee);
		} catch (RuntimeException e) {
			throw new Exception("persistCvMcafee failed: " + e.getMessage());
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvMcafee(CvMcafee cvMcafee) throws Exception {
		try {
			getDao().removeCvMcafee(cvMcafee);
		} catch (RuntimeException e) {
			throw new Exception("removeCvMcafee failed: " + e.getMessage());
		}
	}
	
	public void updateCvMcafeeStatus(Long id, String status, String motivo, Timestamp fechaStatus) throws Exception {
		try {
			getDao().updateCvMcafeeStatus(id, status, motivo, fechaStatus);
		} catch (RuntimeException e) {
			throw new Exception("updateCvMcafeeStatus failed: " + e.getMessage());
		}
	}
	
	public CvMcafee getMcafeeByAccount(Long account) throws Exception {
		try {
			return getDao().getMcafeeByAccount(account);
		} catch (RuntimeException e) {
			throw new Exception("getMcafeeByAccount failed: " + e.getMessage());
		}
	}
	
	public String getCodeFromXML(String xml) throws IOException, ParserConfigurationException, SAXException {
		String code = "";
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Document xmlResponse = null;

		builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xml));
		xmlResponse = builder.parse(is);
		NodeList nodes = xmlResponse.getElementsByTagName("RETURNCODE");
		code = nodes.getLength()>0 ? nodes.item (0).getTextContent():"";
		
		return code;
	}
	/**
	 * Genera un string a partir de un documento XML
	 * @param documentXML El documento XML
	 * @return un string con el texto del XML
	 */
	@SuppressWarnings("deprecation")
	public String xmlToString(Document documentXML) {
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
			log.error("Error : " + ioEx);
		}
		return strWriter.toString();
	}

	/**
	 * Encripta un texto usando el algoritmo MD5
	 * @param data El texto a encriptar
	 * @return un String con el texto encriptado
	 */
	public String encryptMD5(String code){
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
	public String toHexadecimal(byte[] datos) { 
		String resultado=""; 
		ByteArrayInputStream input = new ByteArrayInputStream(datos); 
		String cadAux; 
		boolean ult0=false;
		int leido = input.read(); 
		while(leido != -1) { 
			cadAux = Integer.toHexString(leido); 
			if(cadAux.length() < 2){ //Hay que aï¿½adir un 0 
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
	 * Genera el XML para enviar a Mcafee
	 * @param cvCuenta El objeto de cuenta de Cablevision del usuario
	 * @return un Documento XML
	 */
	public Document generateXML(RespuestaToMyAccount cvCuenta) {
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
						customerContext.setAttribute(REQUESTTYPE, "NEW");
						{
							Element account = documentXML.createElement(ACCOUNT);
							{
								Element emailAddress = documentXML.createElement(EMAILADDRESS);
								emailAddress.setTextContent(cvCuenta.getCorreoContacto());
								Element firstName = documentXML.createElement(FIRSTNAME);

								String name = cvCuenta.getNombreContacto() == null? "" : cvCuenta.getNombreContacto();								

								if(name!=null&&name.length()>20){
									name = cvCuenta.getNombreContacto().substring(0, 20);
								}
								
								String strlastName = (cvCuenta.getApellidoPaterno() == null? "" :cvCuenta.getApellidoPaterno()) +
													" "+
												(cvCuenta.getApellidoMaterno() == null? "" :cvCuenta.getApellidoMaterno());
								if(strlastName != null){
									if(strlastName.length() > 20)
										strlastName = strlastName.substring(0, 20);
								}

								CDATASection cdataFName = documentXML.createCDATASection(name);
								firstName.appendChild(cdataFName);
								Element lastName = documentXML.createElement(LASTNAME);
								CDATASection cdataLName = documentXML.createCDATASection(strlastName);
								lastName.appendChild(cdataLName);
								Element password = documentXML.createElement(PASSWORD);
								password.setTextContent("");//la documentacion indica que enviando el campo en blanco, se genera un password aleatorio por mcafee
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
									item.setAttribute(SKU, MCAFEE_NEW_SKU);
									//item.setAttribute(SKU, MCAFEE_SKU);
									item.setAttribute(QTY, MCAFEE_QTY);
									item.setAttribute(ACTION, MCAFEE_NEW_ACTION);
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
		
		log.debug("XML ::\n"+xmlToString(documentXML)); 
		
		return documentXML;
	}
	
	/**
	 * Genera el XML para enviar a una cancelacion a Mcafee
	 * @param cvCuenta El objeto de cuenta de Cablevision del usuario
	 * @return un Documento XML
	 * Se modifica el primer parametro de tipo CvMcafeeUser a tipo Long, 
	 * ya que el metodo solo utiliza el numero de cuenta y asi permite ser reutilizado para funciones del Webservice
	 */
	public Document generateCancelXML(Long cvCuenta, String reference,Boolean nuevoProducto) {
		final String PARTNERCONTEXT = "PARTNERCONTEXT";
		final String HEADER = "HEADER";
		final String PARTNER = "PARTNER";
		final String PARTNER_ID = "PARTNER_ID";
		final String DATA = "DATA";
		final String CUSTOMERCONTEXT = "CUSTOMERCONTEXT";
		final String ACCOUNT = "ACCOUNT";
		final String ID = "ID";
		final String REQUESTTYPE = "REQUESTTYPE";
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
						customerContext.setAttribute(ID, cvCuenta.toString());
						customerContext.setAttribute(REQUESTTYPE, "UPDATE");
						{
							Element account = documentXML.createElement(ACCOUNT);
							customerContext.appendChild(account);
							
							DateFormat dfm = new SimpleDateFormat("ddMMyyyyHHmmss"+Calendar.getInstance().getTimeInMillis());
							String partnerRef = cvCuenta.toString() + "-" + encryptMD5(dfm.toString());
							Element order = documentXML.createElement(ORDER);
							order.setAttribute(PARTNERREF, partnerRef);
							order.setAttribute(REF, reference);
							{
								Element items = documentXML.createElement(ITEMS);
								{
									Element item = documentXML.createElement(ITEM);
									if(nuevoProducto)
										item.setAttribute(SKU, MCAFEE_NEW_SKU);
									else
										item.setAttribute(SKU, MCAFEE_SKU);
									item.setAttribute(QTY, MCAFEE_QTY);
									item.setAttribute(ACTION, MCAFEE_CANCEL_ACTION);
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
		log.info("CANCEL: "+xmlToString(documentXML)); 
		return documentXML;
	}
	
	/**
	 * Método para realizar la baja del servicio de Mcafee
	 * @author Moises Galvan
	 * @param parametrosVO bean que contiene la informacion de parametros del web service
	 * @return OK si la operacion se realiza correctamente, ERROR si se genera alguna excepcion
	 */
	 public String bajaServicioMcAfee(McaffeeVO parametrosVO){
		log.debug("BAJA MCAFEE : PARAMETROS RECIBIDOS::"+parametrosVO);
		CvMcafeeUser mcafeeUserAnt = null;
		CvMcafee mcafeeUserNuevo  = null;
		Long account = parametrosVO.getNoCuenta();
		String respuesta = "";
		
		try{
		//usuario en la vieja tabla
		mcafeeUserAnt = getMcafeeUserByAccount(account);
		//usuario en la nueva tabla
		mcafeeUserNuevo = getMcafeeByAccount(account);
		}catch(Exception e){
			log.error("ERROR AL INTENTAR OBTENER LA INFORMACION DE LA CUENTA");
			e.printStackTrace();
		}
		
		//valida si el usuario existe en la tabla vieja
		if ( mcafeeUserAnt != null  
				&& !(STS_MIGRADO.equalsIgnoreCase(mcafeeUserAnt.getMusCvstatus().trim()))
				&& !(STS_REACTIVADO.equalsIgnoreCase(mcafeeUserAnt.getMusCvstatus().trim()))){
			
			log.info("BAJA MCAFEE::USUARIO ENCONTRADO EN TABLA ANTERIOR");
			
			if(mcafeeUserAnt.getRegistroMultiple()){
				log.error("CUENTA "+ account + " REGISTRADA MULTIPLES VECES EN BASE DE DATOS CV_MCAFEEUSERS");
				respuesta = RES_ERROR + "::CUENTA "+ account + "REGISTRADA MULTIPLES VECES EN BASE DE DATOS: CV_MCAFEEUSERS";
			}else{
				if(parametrosVO.getTipoSuspension().equalsIgnoreCase("SUSPENDER") ||
						parametrosVO.getTipoSuspension().equalsIgnoreCase("CANCELAR")){
					try{
						log.debug("ESTATUS DE LA CUENTA::"+mcafeeUserAnt.getMusCvstatus().trim());
						if(STS_ACTIVO.equalsIgnoreCase(mcafeeUserAnt.getMusCvstatus().trim())){
							//enviar xml de baja
							Document xmlCancel = generateCancelXML(account, mcafeeUserAnt.getMusReference(),false);
							
							String xmlStringCancelResponse = getXMLResponse(xmlCancel);
							if ( !StringUtils.isEmpty(xmlStringCancelResponse) ){
								//VALIDAR RESPUESTA DE MCAFEE
								String returnCode = getCodeFromXML(xmlStringCancelResponse);
								log.info("CODIGO RESPUESTA MCAFEE::"+returnCode);
								if ( !"1000".equals(returnCode) && !"5001".equals(returnCode) && !"5002".equals(returnCode) ){
									log.error("EXCEPCION AL INTENTAR ENVIAR LA OPERACION DE CANCELACION A MCAFEE");
									log.error(RES_ERROR+"::"+returnCode);
									respuesta = RES_ERROR+"::"+returnCode;
								}else{
									if(parametrosVO.getTipoSuspension().equalsIgnoreCase("SUSPENDER"))
										updateCvMcafeeUserStatus(mcafeeUserAnt.getMusId(),STS_SUSPENDIDO,parametrosVO.getMotivo(),new Timestamp(System.currentTimeMillis()));
									else if (parametrosVO.getTipoSuspension().equalsIgnoreCase("CANCELAR"))
										updateCvMcafeeUserStatus(mcafeeUserAnt.getMusId(),STS_CANCELADO,parametrosVO.getMotivo(),new Timestamp(System.currentTimeMillis()));
										
									log.info("BAJA MCAFEE EXITOSA DE CUENTA::"+account+" CON CODIGO::"+returnCode);
									respuesta = RES_OK;
								}
							}else{
								log.error("NO SE RECIBIO RESPUESTA DE MCAFEE PARA LA CUENTA::"+account);
								respuesta = RES_ERROR+"::NO SE RECIBIO RESPUESTA DE MCAFEE PARA LA CUENTA::"+account;
							}
						}else if(STS_SUSPENDIDO.equalsIgnoreCase(mcafeeUserAnt.getMusCvstatus().trim()) &&
									parametrosVO.getTipoSuspension().equalsIgnoreCase("CANCELAR")){
							updateCvMcafeeUserStatus(mcafeeUserAnt.getMusId(),STS_CANCELADO,parametrosVO.getMotivo(),new Timestamp(System.currentTimeMillis()));
							log.info("BAJA MCAFEE: CUENTA "+account+" ACTUALIZADA DE "+STS_SUSPENDIDO+" A ESTATUS "+STS_CANCELADO);
							respuesta = RES_OK;
						}else if(STS_CANCELADO.equalsIgnoreCase(mcafeeUserAnt.getMusCvstatus().trim())){
							log.info("BAJA MCAFEE: CUENTA "+account+" YA SE ENCUENTRA EN ESTATUS "+STS_CANCELADO);
							respuesta = RES_ERROR+"::CUENTA "+account+" YA SE ENCUENTRA EN ESTATUS "+STS_CANCELADO;
						}
						else{
							log.error("LA CUENTA NO FUE ACTUALIZADA YA QUE SE ENCUENTRA EN ESTATUS"+mcafeeUserAnt.getMusCvstatus().trim());
							respuesta = RES_ERROR+"::LA CUENTA NO FUE ACTUALIZADA YA QUE SE ENCUENTRA EN ESTATUS"+mcafeeUserAnt.getMusCvstatus().trim();
						}
					}catch(Exception e){
						log.error("EXCEPCION AL INTENTAR ENVIAR LA OPERACION DE CANCELACION A MCAFEE");
						e.printStackTrace();
						respuesta = RES_ERROR+"EXCEPCION "+e.getMessage();
					}
				}
			}
			
		}else if (mcafeeUserNuevo != null ){
			log.info("BAJA MCAFEE::USUARIO ENCONTRADO EN TABLA NUEVA");
			if(mcafeeUserNuevo.getRegistroMultiple()){
				log.error("CUENTA "+ account + " REGISTRADA MULTIPLES VECES EN BASE DE DATOS: CV_MCAFEE");
				respuesta = RES_ERROR + "::CUENTA "+ account + " REGISTRADA MULTIPLES VECES EN BASE DE DATOS: CV_MCAFEE";
			}else{
				if(parametrosVO.getTipoSuspension().equalsIgnoreCase("SUSPENDER") ||
						parametrosVO.getTipoSuspension().equalsIgnoreCase("CANCELAR")){
					try{
						log.debug("ESTATUS DE LA CUENTA::"+mcafeeUserNuevo.getMcaCvstatus().trim());
						if(STS_ACTIVO.equalsIgnoreCase(mcafeeUserNuevo.getMcaCvstatus().trim())){
							//enviar xml de baja
							Document xmlCancel = generateCancelXML(account, mcafeeUserNuevo.getMcaReference(),true);
							String xmlStringCancelResponse = getXMLResponse(xmlCancel);
							if ( !StringUtils.isEmpty(xmlStringCancelResponse) ){
								//VALIDAR RESPUESTA DE MCAFEE
								String returnCode = getCodeFromXML(xmlStringCancelResponse);
								if ( !"1000".equals(returnCode) && !"5001".equals(returnCode) && !"5002".equals(returnCode) ){
									log.error("EXCEPCION AL INTENTAR ENVIAR LA OPERACION DE CANCELACION A MCAFEE CON EL CODIGO::"+returnCode);
									respuesta = RES_ERROR+"::"+returnCode;
								}else{
									if(parametrosVO.getTipoSuspension().equalsIgnoreCase("SUSPENDER"))
										updateCvMcafeeStatus(mcafeeUserNuevo.getMcaId(),STS_SUSPENDIDO,parametrosVO.getMotivo(),new Timestamp(System.currentTimeMillis()));
									else if(parametrosVO.getTipoSuspension().equalsIgnoreCase("CANCELAR"))
										updateCvMcafeeStatus(mcafeeUserNuevo.getMcaId(),STS_CANCELADO,parametrosVO.getMotivo(),new Timestamp(System.currentTimeMillis()));
									log.info("BAJA MCAFEE EXITOSA DE CUENTA::"+account+" CON CODIGO::"+returnCode);
									respuesta = RES_OK;
								}
							}else{
								log.error("NO SE RECIBIO RESPUESTA DE MCAFEE PARA LA CUENTA::"+account);
								respuesta = RES_ERROR+"::NO SE RECIBIO RESPUESTA DE MCAFEE PARA LA CUENTA::"+account;
							}
						}else if(STS_SUSPENDIDO.equalsIgnoreCase(mcafeeUserNuevo.getMcaCvstatus().trim())&&
								parametrosVO.getTipoSuspension().equalsIgnoreCase("CANCELAR")){
							//actualizar estatus a CANCELADO
							updateCvMcafeeStatus(mcafeeUserNuevo.getMcaId(),STS_CANCELADO,parametrosVO.getMotivo(),new Timestamp(System.currentTimeMillis()));
							log.info("BAJA MCAFEE: CUENTA "+account+" ACTUALIZADA DE SUSPENDIDO A ESTATUS CANCELADO");
							respuesta = RES_OK;
						}else if(STS_CANCELADO.equalsIgnoreCase(mcafeeUserNuevo.getMcaCvstatus().trim())){
							log.info("BAJA MCAFEE: CUENTA "+account+" YA SE ENCUENTRA EN ESTATUS "+STS_CANCELADO);
							respuesta = RES_ERROR+"::LA CUENTA::"+account+" YA SE ENCUENTRA EN ESTATUS "+STS_CANCELADO;
						}else if(STS_SUSPENDIDO.equalsIgnoreCase(mcafeeUserNuevo.getMcaCvstatus().trim())){
							log.error("LA CUENTA::"+account+" YA SE ENCUENTRA EN ESTATUS "+STS_SUSPENDIDO);
							respuesta = RES_ERROR+";;LA CUENTA::"+account+" YA SE ENCUENTRA EN ESTATUS "+STS_SUSPENDIDO;
						}
					}catch(Exception e){
						e.printStackTrace();
						respuesta = RES_ERROR+"::EXCEPCION "+ e.getMessage();
					}
				}
			}
		}else{
			if(mcafeeUserAnt == null && mcafeeUserNuevo == null){
				log.error("EL USUARIO DE LA CUENTA "+account+" NO FUE ENCONTRADO EN LA BASE DE DATOS");
				respuesta = RES_ERROR+"::CUENTA "+account+" NO FUE ENCONTRADA EN LA BASE DE DATOS";
			}
			else if(mcafeeUserAnt != null){
				log.error("NO ES POSIBLE REACTIVAR. LA CUENTA "+account+" SE ENCUENTRA EN ESTATUS: "+mcafeeUserAnt.getMusCvstatus().trim());
				respuesta = RES_ERROR+"::LA CUENTA "+account+" SE ENCUENTRA EN ESTATUS: "+mcafeeUserAnt.getMusCvstatus().trim();
			}
		}
		log.debug("RESP::"+respuesta);
		 return respuesta;
	 }
	 /**
	  * Funcion que envia un documento XML a los servidores de Mcafee
	  * @param xml Documento a enviar
	  * @return xml de respuesta de la operacion enviada a Mcafee
	  */
	 @SuppressWarnings("deprecation")
		public String getXMLResponse(Document xml) {
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
				log.info("Enviando xml: "+xmlToString(xml));
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
			
			String xmlStringResponse = sb.toString();

			log.debug("XML RECIBIDO:: "+xmlStringResponse);
			return xmlStringResponse;
		}
	 
	 /**
	  * Método para realizar la reactivación del servicio de mcafee 
	  * @author Moises Galvan
	  * @param parametrosVO bean que contiene la informacion de parametros del web service
	  * @return OK si la operacion se realiza correctamente, ERROR si se genera alguna excepcion 
	  * 
	  */
	 public String reactivaServicioMcafee(McaffeeVO parametrosVO){
		 log.debug("REACTIVA MCAFEE : PARAMETROS RECIBIDOS::"+parametrosVO);
		 
		 Long account = parametrosVO.getNoCuenta();
		 if(parametrosVO.getMotivo() == null)
			 parametrosVO.setMotivo("");
		 CvMcafeeUser mcafeeUserAnt = null;
		 CvMcafee mcafeeUserNuevo  = null;
		 String respuesta = "";
		 
		 try{
			//usuario en la vieja tabla
			mcafeeUserAnt = getMcafeeUserByAccount(account);
			//usuario en la nueva tabla
			mcafeeUserNuevo = getMcafeeByAccount(account);
			}catch(Exception e){
				log.error("ERROR AL INTENTAR OBTENER LA INFORMACION DE LA CUENTA");
				e.printStackTrace();
				respuesta = RES_ERROR;
			}

			
			//valida si el usuario existe en la tabla vieja
			if ( mcafeeUserAnt != null 
					&& !(STS_MIGRADO.equalsIgnoreCase(mcafeeUserAnt.getMusCvstatus().trim()))
					&& !(STS_REACTIVADO.equalsIgnoreCase(mcafeeUserAnt.getMusCvstatus().trim()))){
				
				log.debug("REACTIVA MCAFEE::USUARIO ENCONTRADO EN TABLA ANTERIOR");
				
				try{
					log.debug("ESTATUS DE LA CUENTA::"+mcafeeUserAnt.getMusCvstatus().trim());
					
					if(STS_SUSPENDIDO.equalsIgnoreCase(mcafeeUserAnt.getMusCvstatus().trim())){
						updateCvMcafeeUserStatus(mcafeeUserAnt.getMusId(),STS_REACTIVADO,parametrosVO.getMotivo(),new Timestamp(System.currentTimeMillis()));
						log.info("REACTIVA MCAFEE: CUENTA "+account+" ACTUALIZADA DE "+STS_SUSPENDIDO+" A ESTATUS "+STS_REACTIVADO);
						respuesta = RES_OK;
					}else if(STS_ACTIVO.equalsIgnoreCase(mcafeeUserAnt.getMusCvstatus().trim()) ||
								STS_CANCELADO.equalsIgnoreCase(mcafeeUserAnt.getMusCvstatus().trim())){
						log.error("NO ES POSIBLE REACTIVAR. LA CUENTA "+account+" SE ENCUENTRA EN ESTATUS: "+mcafeeUserAnt.getMusCvstatus().trim());
						respuesta = RES_ERROR+"::NO ES POSIBLE REACTIVAR. LA CUENTA "+account+" SE ENCUENTRA EN ESTATUS: "+mcafeeUserAnt.getMusCvstatus().trim();
					}
					else{
						log.error("EL USUARIO DE LA CUENTA "+account+" NO CUENTA CON UN ESTATUS VALIDO");
						respuesta = RES_ERROR+"::EL USUARIO DE LA CUENTA "+account+" NO CUENTA CON UN ESTATUS VALIDO";
					}
				}catch(Exception e){
					log.error("EXCEPCION AL INTENTAR REACTIVAR MCAFEE EN TABLA ANTERIOR CON LA CUENTA::"+account);
					e.printStackTrace();
					respuesta = RES_ERROR;
				}
				
				
			}else if (mcafeeUserNuevo != null ){
				log.debug("REACTIVA MCAFEE::USUARIO ENCONTRADO EN TABLA NUEVA");
				
				try{
					log.debug("ESTATUS DE LA CUENTA::"+mcafeeUserNuevo.getMcaCvstatus().trim());
					if(STS_SUSPENDIDO.equalsIgnoreCase(mcafeeUserNuevo.getMcaCvstatus().trim())){
						
						//obtiene los datos del cliente usando la interfaz de Vitria
						RespuestaToMyAccount cvCuenta = obtenInfoCuenta(account);
						
						cvCuenta.setCvEmail(mcafeeUserNuevo.getMcaEmailaddress());
						
						Document xml = generateXML(cvCuenta);
						
						String xmlStringResponse = getXMLResponse(xml);
						
						if(!StringUtils.isEmpty(xmlStringResponse)){
							String code = getCodeFromXML(xmlStringResponse);
							if ( xmlStringResponse != null && ("1000".equals(code) || "5001".equals(code) || "5002".equals(code)) ) {
								if("5002".equals(code)){
									log.info("MCAFEE HA SOLICITADO LA ACTUALIZACION DE DATOS DE LA CUENTA::"+account);
									Document xmlUpdateProfile = generateUpdatingProfileXML(cvCuenta);
									String xmlUpdateProfileResponse = getXMLResponse(xmlUpdateProfile);
									String codeUpdate = getCodeFromXML(xmlUpdateProfileResponse);
									if ( !("1000".equals(codeUpdate) || "5001".equals(codeUpdate) || "5002".equals(codeUpdate)) ) {
										log.error("LA ACTUALIZACION DE DATOS DE LA CUENTA::"+account+ " HA GENERADO EL CODIGO DE ERROR::"+codeUpdate);
										respuesta = RES_ERROR+"::LA ACTUALIZACION DE DATOS DE LA CUENTA::"+account+ " HA GENERADO EL CODIGO DE ERROR::"+code;
									}else{
										log.info("ACTUALIZACION MCAFEE EXITOSA DE CUENTA::"+account);
										
									}
								}else if("5001".equals(code) || "1000".equals(code)){
									log.info("REACTIVACION MCAFEE EXITOSA DE CUENTA::"+account+" CON CODIGO::"+code);
								}
								updateCvMcafeeStatus(mcafeeUserNuevo.getMcaId(),STS_ACTIVO,parametrosVO.getMotivo(),new Timestamp(System.currentTimeMillis()));
								respuesta = RES_OK;
							}else{
								log.error("SE GENERO EL ERROR::"+code+" AL INTENTAR LA REACTIVACIÓN DEL SERVICIO MCAFEE CON LA CUENTA::"+account);
								respuesta = RES_ERROR+"::SE GENERO EL ERROR::"+code+" AL INTENTAR LA REACTIVACIÓN DEL SERVICIO MCAFEE CON LA CUENTA::"+account;
							}
						}else{
							log.error("NO SE RECIBIO RESPUESTA DE MCAFEE PARA LA CUENTA::"+account);
							respuesta = RES_ERROR;
						}
					}else if(STS_ACTIVO.equalsIgnoreCase(mcafeeUserNuevo.getMcaCvstatus().trim()) ||
								STS_CANCELADO.equalsIgnoreCase(mcafeeUserNuevo.getMcaCvstatus().trim())){
						log.error("NO ES POSIBLE REACTIVAR. LA CUENTA::"+account+" SE ENCUENTRA EN ESTATUS: "+mcafeeUserNuevo.getMcaCvstatus().trim());
						respuesta = RES_ERROR+"::NO ES POSIBLE REACTIVAR. LA CUENTA::"+account+" SE ENCUENTRA EN ESTATUS: "+mcafeeUserNuevo.getMcaCvstatus().trim();
					}
				}catch(Exception e){
					log.error("ERROR AL INTENTAR REACTIVAR LA CUENTA::"+account);
					e.printStackTrace();
					respuesta = RES_ERROR;
				}
				
			}else{
				if(mcafeeUserAnt == null && mcafeeUserNuevo == null){
					log.error("EL USUARIO DE LA CUENTA "+account+" NO FUE ENCONTRADO EN LA BASE DE DATOS");
					respuesta = RES_ERROR;
				}
				else if(mcafeeUserAnt != null){
					log.error("NO ES POSIBLE REACTIVAR. LA CUENTA "+account+" SE ENCUENTRA EN ESTATUS: "+mcafeeUserAnt.getMusCvstatus().trim());
					respuesta = RES_ERROR;
				}
			}
		 
		log.debug("RESP::"+respuesta);
		 return respuesta;
	 }
	 
	 public CvMcafee getMcafeeUserFromResponse(Document response, RespuestaToMyAccount cuenta, String sessionAccountId) {
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
				CvMcafee mcafeeUser = new CvMcafee();
				NodeList nodes = null;
				mcafeeUser.setMcaAccount(Long.parseLong(cuenta.getCvNumberAccount()));
				
				//mcafeeUser.setMcaAccountid(sessionAccountId);
				mcafeeUser.setMcaContactid(sessionAccountId);
				mcafeeUser.setMcaCvstatus("ACTIVO");
				mcafeeUser.setMcaDatesuscribe(new java.sql.Timestamp(new Date().getTime()));
				
				nodes = response.getElementsByTagName(EMAILADDRESS);
				mcafeeUser.setMcaEmailaddress(nodes.getLength()>0 ? nodes.item (0).getTextContent():null);
				
				nodes = response.getElementsByTagName(PASSWORD);
				mcafeeUser.setMcaPassword(nodes.getLength()>0 ? nodes.item(0).getTextContent():null);
				
				nodes = response.getElementsByTagName(ORDER);
				mcafeeUser.setMcaReference(nodes.getLength()>0 ? nodes.item(0).getAttributes().getNamedItem(REF).getNodeValue():null);
				
				return mcafeeUser;
			} else
				return null;
		}
	 
	 /**
		 * Genera el XML para enviar update del profile de un usuario a Mcafee
		 * @param cvCuenta El objeto de cuenta de Cablevision del usuario
		 * @return un Documento XML
		 */
		public Document generateUpdatingProfileXML(RespuestaToMyAccount cvCuenta) {
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
							customerContext.setAttribute(REQUESTTYPE, "UPDPROFILE");
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
			log.info("update profile: "+xmlToString(documentXML)); 
			return documentXML;
		}
	
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		this.applicationContext = arg0;
		
	}
	/**
	 * @param metodo de envio de correo electronico de confirmación de descarga de productos Mcafee
	 */
	public String enviaCorreoAlta(RespuestaToMyAccount cvCuenta,CvMcafee newMcafeeUser,String urldescarga, String contexto){
		/*1. E-mail address for McAfee login
		2. Password for McAfee login
		3. URL to login, download, and install the subscription
		4. Subscription term, type, and product
		*/
		
		String para =cvCuenta.getCorreoContacto();
		
		String de = "";
		Map<String, String> paramsCorreo = new HashMap<String, String>();
		log.debug("CONTEXTO::"+contexto);
		paramsCorreo.put("tag_nombrecliente", cvCuenta.getNombreContacto() == null? "" : cvCuenta.getNombreContacto() );
		paramsCorreo.put("tag_email", para);
		paramsCorreo.put("tag_password", newMcafeeUser.getMcaPassword());
		paramsCorreo.put("tag_link", urldescarga);
		paramsCorreo.put("tag_contexto", contexto);
		log.debug("URLDESCARGA::"+urldescarga);
		
				try {
					log.debug("ENVIANDO CORREO DE NOTIFICACION MCAFEE A CUENTA "+cvCuenta.getCvNumberAccount());
					//MailUtil.sendMail(texto, CORREO_SUBJECT, para, CORREO_FROM);
					
					MailUtil.sendMail(CORREO_SUBJECT, 
							para, 
							CORREO_FROM, 
							CORREO_TEMPLATEID, 
							paramsCorreo);
				}catch(Exception e){
					log.error("SE GENERO UNA EXCEPCION AL INTENTAR ENVIAR EL CORREO DE CONFIRMACION DE DESCARGA DE MCAFEE");
					e.printStackTrace();
				}
				
		return "";
	}
	/**
	 * Metodo que regresa la informacion correspondiente a un numero de cuenta utilizando la interfaz con vitria
	 * @param account
	 * @return Objeto RespuestaToMyAccount con la informacion correspondiente a la cuenta
	 * @throws RemoteException
	 * @throws ServiceException
	 */
	public RespuestaToMyAccount obtenInfoCuenta(Long account) throws RemoteException,ServiceException{
		
		ResponsetoSecretQuestionByAccount usuario2 = getVitriaClient().getProjects_CVNPW_Initial_ToInterfase().toSecretQuestionByAccount(account.toString());
		String usrid = usuario2.getUserId();
		log.debug("USRID para la cuenta::"+account+"::"+usrid);
		RespuestaToMyAccount cvCuenta = getVitriaClient().getProjects_CVNPW_Initial_ToInterfase().toMyAccount(usrid);
		 
		return cvCuenta;
	}
	
	public String getCORREO_FROM() {
		return CORREO_FROM;
	}
	public void setCORREO_FROM(String correo_from) {
		CORREO_FROM = correo_from;
	}
	public String getCORREO_SUBJECT() {
		return CORREO_SUBJECT;
	}
	public void setCORREO_SUBJECT(String correo_subject) {
		CORREO_SUBJECT = correo_subject;
	}
	public String getMCAFEE_HOME() {
		return MCAFEE_HOME;
	}
	public void setMCAFEE_HOME(String mcafee_home) {
		MCAFEE_HOME = mcafee_home;
	}
	public String getCORREO_TEMPLATEID() {
		return CORREO_TEMPLATEID;
	}
	public void setCORREO_TEMPLATEID(String correo_templateid) {
		CORREO_TEMPLATEID = correo_templateid;
	}
	
		
}