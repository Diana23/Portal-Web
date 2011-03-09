package com.cablevision.util;


import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.MessageResources;

import com.bea.portlet.PageURL;
import com.cablevision.vo.CvContenido;
import com.cablevision.vo.CvCurriculum;
import com.cablevision.vo.CvEstructura;
import com.cablevision.vo.CvTemplate;
import com.cablevision.vo.Vacante;
import com.stellent.checkin.CheckIn;
import com.stellent.checkin.CheckInSoap;
import com.stellent.checkin.CheckInUniversalResult;
import com.stellent.checkin.CheckIn_Impl;
import com.stellent.checkin.IdcFile;
import com.stellent.checkin.IdcProperty;
import com.stellent.checkin.IdcPropertyList;
import com.stellent.checkin.UpdateDocInfoResult;
import com.stellent.docinfo.DocInfo;
import com.stellent.docinfo.DocInfoByNameResult;
import com.stellent.docinfo.DocInfoSoap;
import com.stellent.docinfo.DocInfo_Impl;
import com.stellent.getfile.FileInfo;
import com.stellent.getfile.GetFile;
import com.stellent.getfile.GetFileByNameResult;
import com.stellent.getfile.GetFile_Impl;
import com.stellent.search.AdvancedSearchResult;
import com.stellent.search.QuickSearchResult;
import com.stellent.search.Search;
import com.stellent.search.SearchResults;
import com.stellent.search.Search_Impl;
import com.stellent.workflow.GetWorkflowQueueResult;
import com.stellent.workflow.Workflow;
import com.stellent.workflow.WorkflowCheckInResult;
import com.stellent.workflow.WorkflowQueue;
import com.stellent.workflow.WorkflowSoap;
import com.stellent.workflow.Workflow_Impl;

/**
 * Clase para accesar WS de UCM
 * @author Luis Perez - JWMSolutions 10/11/2009
 *
 */
public class UcmUtil {
	
	/**
	 * Patron para obtener las urls a paginas en el contenido
	 */
	private static Pattern p = Pattern.compile("\\\"_pageLabel\\=[^\\\"]+\\\"");

	
	public static String getContentByName(String dDocName,boolean ultima) throws Exception{
		GetFileByNameResult result = getFileNameResult(dDocName, ultima);
		String contenido = new String(
				result.getDownloadFile().getFileContent(),"UTF-8"
		);
		return contenido;
	}
	
	public static GetFileByNameResult getFileByName(String dDocName,boolean ultima,HttpServletRequest request) throws Exception{
		GetFileByNameResult result = getFileNameResult(dDocName, ultima);
		
		return result;
	}

	public static GetFileByNameResult getFileNameResult(String dDocName,
			boolean ultima) throws RemoteException,
			ServiceException {
		GetFileByNameResult result = getFileClient().getGetFileSoap(
				ConfigurationHelper.getPropiedad("ucm.usuario").getBytes(),
				ConfigurationHelper.getPropiedad("ucm.password").getBytes()
			).getFileByName(
				dDocName, ultima?"latest":"latestReleased", "primary", null
			);
		return result;
	}
	
	public static CvContenido saveContentFile(String dDocName, String contenido, String estructuraId, 
										 String folderId, String contentName, HttpServletRequest request) throws Exception{
		
		CvContenido contenidoGuardado = new CvContenido();
		CheckInSoap soap = getCheckInSoap(request);
		
		//asociarle al contenido el id de la estructura al cual esta ligado
		if(StringUtils.isNotEmpty(estructuraId)){
			contenido = ContenidoHelper.setEstructuraIdAlContenido(contenido, estructuraId);
		}
		
		GetFileByNameResult result = null;
		
		//El archivo ya existe, hacer checkout
		if(StringUtils.isNotEmpty(dDocName)){
			soap.checkOutByName(dDocName, null);
			result = getFileNameResult(dDocName, true);
		}
		
		DocInfoByNameResult resultWF = getDocInfoSoap().docInfoByName(dDocName, null);
		
		if(resultWF.getWorkflowInfo()!=null && resultWF.getWorkflowInfo()[0]!=null  && canEditWorkflow(dDocName, request)){
			FileInfo[] fileInfo = result.getFileInfo();
			if(fileInfo!= null && fileInfo.length > 0){
				FileInfo info = fileInfo[0];
				//Crear el archivo
				com.stellent.workflow.IdcFile file = new com.stellent.workflow.IdcFile();
				file.setFileName(info.getDDocTitle());
				
				file.setFileContent(contenido.getBytes("UTF-8"));
				WorkflowCheckInResult wfCheckIn = null;
				
				if(resultWF.getWorkflowInfo()[0].getDWfCurrentStepID()==11){
					wfCheckIn = getWfInfoSoap(request).workflowCheckIn(
							dDocName, info.getDDocTitle(), info.getDDocType(), info.getDDocAuthor(), 
							info.getDSecurityGroup(), info.getDDocAccount(), null, file,
							null, new Boolean("true"), null);
				}else{
					wfCheckIn = getWfInfoSoap(request).workflowCheckIn(
							dDocName, info.getDDocTitle(), info.getDDocType(), info.getDDocAuthor(), 
							info.getDSecurityGroup(), info.getDDocAccount(), null, file,
							null, new Boolean("false"), null);
				}
				contenidoGuardado.setName(info.getDDocTitle());
				contenidoGuardado.setDID(wfCheckIn!=null && wfCheckIn.getDID()!=null?wfCheckIn.getDID().toString():"");
				 
			}
			contenidoGuardado.setDDocName(dDocName);
			
		}else{
			//update
			if(result != null && result.getStatusInfo().getStatusCode()==0){
				FileInfo[] fileInfo = result.getFileInfo();
				if(fileInfo!= null && fileInfo.length > 0){
					FileInfo info = fileInfo[0];
					
					//Crear el archivo
					IdcFile file = new IdcFile();
					file.setFileName(info.getDDocTitle());
					
					file.setFileContent(contenido.getBytes("UTF-8"));
					
					CheckInUniversalResult chekIn = soap.checkInUniversal(
							dDocName, info.getDDocTitle(), info.getDDocType(), null, 
							"Mercadotecnia", null, null, file,
							null, null);
					contenidoGuardado.setName(info.getDDocTitle());
					contenidoGuardado.setDID(chekIn.getDID()!=null?
							getDocInfoSoap().docInfoByID(chekIn.getDID(), null).getContentInfo()[0].getDID().toString():"");
				}
				contenidoGuardado.setDDocName(dDocName);
			//guardar nuevo contenido
			}else{
				//Crear el archivo
				IdcFile file = new IdcFile();
				if(StringUtils.isEmpty(contentName))
					contentName = getNewTitle(estructuraId);
				file.setFileName(contentName);
				
				file.setFileContent(contenido.getBytes("UTF-8"));
				
				IdcPropertyList properties = new IdcPropertyList();
				
				IdcProperty folder = new IdcProperty();
				folder.setName("xCollectionID");
				folder.setValue(folderId);

				IdcProperty isFinished = new IdcProperty();
				isFinished.setName("isFinished");
				isFinished.setValue("true");
				properties.setProperty(new IdcProperty[]{isFinished,folder});
				
				CheckInUniversalResult chekIn = soap.checkInUniversal(
						null, contentName, "CONTENIDO_ESTRUCTURADO_CV ", null, 
						"Mercadotecnia", null, null, file,
						null, properties);
				
				contenidoGuardado.setDDocName(chekIn.getDID()!=null?
						getDocInfoSoap().docInfoByID(chekIn.getDID(), null).getContentInfo()[0].getDDocName():"");
				contenidoGuardado.setName(contentName);
				contenidoGuardado.setDID(chekIn.getDID()!=null?
						getDocInfoSoap().docInfoByID(chekIn.getDID(), null).getContentInfo()[0].getDID().toString():"");
			}
		}
		ContenidoHelper.indexarContenido(contenidoGuardado.getDDocName(), false);
		
		return contenidoGuardado;
	}

	/**
	 * Borrar contenido de UCM
	 * @param contentId El id del contenido a borrar
	 */
	public static void removeContentFile(String contentId, HttpServletRequest request) throws Exception{
		CheckInSoap soap = getCheckInSoap(request);
		
		//obtenemos el contenido estructurado de ucm
		GetFileByNameResult result = getFileByName(contentId,true,request);
		
		
		if(isFileInWorkflow(contentId)){
			workFlowApprove(contentId, request);
		}
		
		// Crear el archivo
		IdcFile file = new IdcFile();
		file.setFileName(result.getDownloadFile().getFileName());
		file.setFileContent(result.getDownloadFile().getFileContent());

		result.getFileInfo()[0].getDReleaseDate();
		
		IdcPropertyList properties = new IdcPropertyList();

		com.stellent.checkin.IdcProperty folderFecha = new com.stellent.checkin.IdcProperty();
		if(StringUtils.isNotEmpty(result.getFileInfo()[0].getDCreateDate())){
			folderFecha.setName("dOutDate");
			SimpleDateFormat formato = new SimpleDateFormat("MM/dd/yy hh:mm aa");
			Calendar fecha = Calendar.getInstance();
			fecha.setTime(formato.parse(result.getFileInfo()[0].getDCreateDate()));
			fecha.add(Calendar.MINUTE,1 );
			
			folderFecha.setValue(formato.format(fecha.getTime()));
		}
		
		properties.setProperty(new com.stellent.checkin.IdcProperty[] { folderFecha });

		
		soap.updateDocInfo(result.getFileInfo()[0].getDID(), result.getFileInfo()[0].getDRevLabel(), 
				contentId, result.getFileInfo()[0].getDDocTitle(), "CONTENIDO_ESTRUCTURADO_CV", result.getFileInfo()[0].getDDocAuthor(), "Mercadotecnia", result.getFileInfo()[0].getDDocAccount(), properties, properties);

	}
	
	
	public static boolean isFileInWorkflow(String docName) throws RemoteException, ServiceException{
		
		if(StringUtils.isNotEmpty(docName)){
			DocInfoByNameResult result = getDocInfoSoap().docInfoByName(docName, null);
			
			if (result.getWorkflowInfo()!= null && result.getWorkflowInfo()[0].getDWfCurrentStepID()!=11)
				return true;
		}
		return false;
	}
	
	
	public static boolean canEditWorkflow(String dDocName, HttpServletRequest request) throws RemoteException, ServiceException, Exception{
		GetWorkflowQueueResult result = getWfInfoSoap(request).getWorkflowQueue(null);
		
		if(result!= null && result.getWorkflowQueue()!= null){
			for(WorkflowQueue wq: result.getWorkflowQueue()){
				if (wq.getDDocName().equalsIgnoreCase(dDocName))
					return true;
			}
		}
		
		return false;
	}
	
	public static GetFileByNameResult getFileNameResult(String dDocName,
			boolean ultima, HttpServletRequest request) throws RemoteException,
			ServiceException {
		GetFileByNameResult result = getFileClient().getGetFileSoap(
				ConfigurationHelper.getPropiedad("ucm.usuario").getBytes(),
				ConfigurationHelper.getPropiedad("ucm.password").getBytes()
			).getFileByName(
				dDocName, ultima?"latest":"latestReleased", "primary", null
			);
		return result;
	}
	
	public static void workFlowApprove(String dDocName, HttpServletRequest request) throws ServiceException, RemoteException, Exception{
		while(isFileInWorkflow(dDocName)){
			//aprobar los archivos con referencia
			String idsArchivos = getArchivosConReferencia(dDocName);
			for(String idArchivo : idsArchivos.split("/")){
				if(StringUtils.isNotEmpty(idArchivo) && isFileInWorkflow(idArchivo) && canEditWorkflow(idArchivo, request)){
					DocInfoByNameResult resultArchivo = getDocInfoSoap().docInfoByName(dDocName, null);
					
					if(resultArchivo != null && resultArchivo.getContentInfo()!= null && resultArchivo.getContentInfo().length>0){
						getWfInfoSoap(request).workflowApprove(resultArchivo.getContentInfo()[0].getDID(), idArchivo, "", null);
					}
				}
			}
			
			//aprobar el archivo principal
			DocInfoByNameResult result = getDocInfoSoap().docInfoByName(dDocName, null);
			if(result != null && result.getContentInfo()!= null && result.getContentInfo().length>0){
				getWfInfoSoap(request).workflowApprove(result.getContentInfo()[0].getDID(), dDocName, "", null);
			}
		}
	}
	
	public static void workFlowReject(String dDocName, String wfRejectMessage, HttpServletRequest request) throws ServiceException, RemoteException, Exception{
		//rechazar archivos que hacen referencia al archivo principal
		String idsArchivos = getArchivosConReferencia(dDocName);
		for(String idArchivo : idsArchivos.split("/")){
			if(StringUtils.isNotEmpty(idArchivo) && isFileInWorkflow(idArchivo) && canEditWorkflow(idArchivo, request)){
				DocInfoByNameResult resultArchivo = getDocInfoSoap().docInfoByName(dDocName, null);
				
				if(resultArchivo != null && resultArchivo.getContentInfo()!= null && resultArchivo.getContentInfo().length>0){
					getWfInfoSoap(request).workflowReject(resultArchivo.getContentInfo()[0].getDID(), idArchivo, resultArchivo.getWorkflowInfo()[0].getDWfName(), wfRejectMessage, null);
				}
			}
		}
		
		//rechazar el archivo principal
		DocInfoByNameResult result = getDocInfoSoap().docInfoByName(dDocName, null);
		if(result != null && result.getContentInfo()!= null && result.getContentInfo().length>0){
			getWfInfoSoap(request).workflowReject(result.getContentInfo()[0].getDID(), dDocName, result.getWorkflowInfo()[0].getDWfName(), wfRejectMessage, null);
		}
	}
	
	
	public static Map<String,CvTemplate> getTemplates() throws Exception {
		AdvancedSearchResult result = getSearch().getSearchSoap(
				ConfigurationHelper.getPropiedad("ucm.usuario").getBytes(),
				ConfigurationHelper.getPropiedad("ucm.password").getBytes()).
				advancedSearch("dDocType <MATCHES> `TEMPLATE_CV`", null, null, 100, null);
		SearchResults[] search = result.getSearchResults();
		
		Map<String,CvTemplate> templates = new LinkedHashMap<String,CvTemplate>();
		
		if(search!=null){
			for(int i=0; i<search.length;i++){
				CvTemplate template = new CvTemplate(search[i].getDDocName().trim(), search[i].getDDocTitle(), getContentByName(search[i].getDDocName(), true));
				templates.put(search[i].getDDocName().trim(), template);
			}
		}
		
		return templates;
	}
	
	public static Map<String,CvEstructura> getEstructuras() throws Exception {
		QuickSearchResult result = getSearch().getSearchSoap(
				ConfigurationHelper.getPropiedad("ucm.usuario").getBytes(),
				ConfigurationHelper.getPropiedad("ucm.password").getBytes()).
				quickSearch("dDocType <MATCHES> `ESTRUCTURA_CV`", null);
		SearchResults[] search = result.getSearchResults();
		
		Map<String,CvEstructura> templates = new LinkedHashMap<String,CvEstructura>();
		
		if(search!=null){
			for(int i=0; i<search.length;i++){
				CvEstructura template = new CvEstructura(search[i].getDDocName().trim(), search[i].getDDocTitle(), getContentByName(search[i].getDDocName(), true));
				templates.put(search[i].getDDocName().trim(), template);
			}
		}
		
		return templates;
	}
	
	public static List<String> getIdsContenido() throws Exception{
		AdvancedSearchResult result = getSearch().getSearchSoap(
				ConfigurationHelper.getPropiedad("ucm.usuario").getBytes(),
				ConfigurationHelper.getPropiedad("ucm.password").getBytes()).
				advancedSearch("dDocType <MATCHES> `CONTENIDO_ESTRUCTURADO_CV`", null, null, 500, null);
		SearchResults[] search = result.getSearchResults();
		
		List<String> listContenido = new ArrayList<String>();
		
		if(search != null){
			for(int i=0; i<search.length; i++){
				//CvContenido contenido = new CvContenido(search[i].getDDocName().trim(), search[i].getDDocTitle(), getContentByName(search[i].getDDocName(), true));
				listContenido.add(search[i].getDDocName().trim());
			}
		}
		return listContenido;
	}
	
	private static String getArchivosConReferencia(String dDocName){
		String archivos = "";
		try{
			String content = getContentByName(dDocName, true);
			
			while(content.length()>0){
				String archivo = "";
				
				if(content.contains("/cv") && content.length()>8){
					archivo = content.substring(content.indexOf("/cv"), content.indexOf("/cv")+9);
					archivos += archivo;
					content = content.replace(archivo, "");
				}else{
					break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		archivos = StringUtils.capitalize(archivos);
		return archivos;
	}
	
	/**
	 * Busca en el contenido todo las urls a paginas con formato {@link #p} y los sustituye por el link de 
	 * la pagina utilizando {@link PageURL#createPageURL(HttpServletRequest, HttpServletResponse, String)}
	 * para generarlo
	 * @param contenido El contenido html
	 * @param request El request
	 * @param response El response
	 * @return El contenido con los links de paginas con urls reales
	 */
	public static String sustituyePageUrl(String contenido,HttpServletRequest request, HttpServletResponse response){
		StringBuilder builder = new StringBuilder();

		int lastEnd = 0;
		
		contenido = contenido.replaceAll("http://_pageLabel", "_pageLabel");
		contenido = contenido.replaceAll("_contextPath", request.getContextPath());
		
		Matcher m = p.matcher(contenido); // get a matcher object
		while(m.find()) {
			String[] link = m.group().substring(0, m.group().length()-1).split("\\?");
			String pageLabel = StringUtils.substringAfter(link[0],"=");
			PageURL url = null;
			
			if("cotizador".equals(pageLabel)){
				url = PageURL.createPageURL(request, response);
				url.setTemplate("cotizador");
			}else if("home".equals(pageLabel)){
				url = PageURL.createPageURL(request, response);
				url.setTemplate("cotizador");
			}else{
				url = PageURL.createPageURL(request, response, pageLabel);
				url.setTemplate("default");
			}
			
			url.addParameter(com.bea.portlet.GenericURL.TREE_OPTIMIZATION_PARAM, "false");
			url.setForcedAmpForm(false);

			//parametros que vienen en la url
			if(link.length>=2){
				String[] params = link[1].split("&");
	        	for(int j=0; j<params.length; j++){
	        		if( StringUtils.isNotEmpty(StringUtils.substringBefore(params[j], "=")) &&
	        			StringUtils.isNotEmpty(StringUtils.substringAfter(params[j], "=")) ){
	        			url.addParameter(StringUtils.substringBefore(params[j], "="), StringUtils.substringAfter(params[j], "="));
	        		}
	        	}
			}
        	
			builder.append(contenido.substring(lastEnd,m.start()));
			builder.append("\""+url.toString()+"\"");
			
			lastEnd = m.end();

		}
		builder.append(contenido.substring(lastEnd));

		return builder.toString();

	}
	
	/**
	 * Busca en el contenido todo las urls a paginas con formato {@link #p} y los sustituye por el link en https de 
	 * la pagina utilizando {@link PageURL#createPageURL(HttpServletRequest, HttpServletResponse, String)}
	 * para generarlo
	 * @param contenido El contenido html
	 * @param request El request
	 * @param response El response
	 * @return El contenido con los links de paginas con urls reales
	 */
	public static String sustituyePageUrlHttps(String contenido,HttpServletRequest request, HttpServletResponse response){
		StringBuilder builder = new StringBuilder();

		int lastEnd = 0;
		
		contenido = contenido.replaceAll("https://_pageLabel", "_pageLabel");
		contenido = contenido.replaceAll("_contextPath", request.getContextPath());
		
		Matcher m = p.matcher(contenido); // get a matcher object
		while(m.find()) {
			String[] link = m.group().substring(0, m.group().length()-1).split("\\?");
			String pageLabel = StringUtils.substringAfter(link[0],"=");
			PageURL url = null;
			
			url = PageURL.createPageURL(request, response, pageLabel);
			url.setTemplate("https");
			url.addParameter(com.bea.portlet.GenericURL.TREE_OPTIMIZATION_PARAM, "false");
			url.setForcedAmpForm(false);

			//parametros que vienen en la url
			if(link.length>=2){
				String[] params = link[1].split("&");
	        	for(int j=0; j<params.length; j++){
	        		if( StringUtils.isNotEmpty(StringUtils.substringBefore(params[j], "=")) &&
	        			StringUtils.isNotEmpty(StringUtils.substringAfter(params[j], "=")) ){
	        			url.addParameter(StringUtils.substringBefore(params[j], "="), StringUtils.substringAfter(params[j], "="));
	        		}
	        	}
			}
        	
			builder.append(contenido.substring(lastEnd,m.start()));
			builder.append("\""+url.toString()+"\"");
			
			lastEnd = m.end();

		}
		builder.append(contenido.substring(lastEnd));

		return builder.toString();

	}
	
	/*static private String getNewDocName(String dDocName){
		String newDocName=dDocName;
		for(int i = dDocName.length(); i<6;i++){
			newDocName = "0"+newDocName;
		}
		return "CV"+newDocName;
	}*/
	
	static private String getNewTitle(String dDocName){
		SimpleDateFormat sfd = new SimpleDateFormat("ddMMyymm");
		String newTitle=dDocName+sfd.format(new Date());
		
		return newTitle;
	}
	
	static private DocInfoSoap getDocInfoSoap() throws ServiceException {
		DocInfoSoap docInfoSoap = getDocInfoClient().getDocInfoSoap(
				ConfigurationHelper.getPropiedad("ucm.usuario").getBytes(),
				ConfigurationHelper.getPropiedad("ucm.password").getBytes());
		return docInfoSoap;
	}
	
	static private WorkflowSoap getWfInfoSoap(HttpServletRequest request) throws ServiceException, Exception {
		String passw = ConfigurationHelper.getPropiedad("ucm.password");
		String userId = ConfigurationHelper.getPropiedad("ucm.usuario");
		if(request.getSession().getAttribute(Constantes.SESSION_MI_PASSWD)!=null)
			passw = Blowfish.desEncriptar((byte[])request.getSession().getAttribute(Constantes.SESSION_MI_PASSWD),Constantes.ENCRIPT_PASSWD);
		if(request.getSession().getAttribute(Constantes.SESSION_ACCOUNT_ID)!=null)
			userId = ((String)request.getSession().getAttribute(com.cablevision.util.Constantes.SESSION_ACCOUNT_ID));

		
		WorkflowSoap wfInfoSoap = getWorkflow().getWorkflowSoap(
				userId.getBytes(),
				passw.getBytes());
		return wfInfoSoap;
	}
	
	static private CheckInSoap getCheckInSoap(HttpServletRequest request) throws Exception{
		String passw = ConfigurationHelper.getPropiedad("ucm.password");
		String userId = ConfigurationHelper.getPropiedad("ucm.usuario");
		if(request.getSession().getAttribute(Constantes.SESSION_MI_PASSWD)!=null)
			passw = Blowfish.desEncriptar((byte[])request.getSession().getAttribute(Constantes.SESSION_MI_PASSWD),Constantes.ENCRIPT_PASSWD);
		if(request.getSession().getAttribute(Constantes.SESSION_ACCOUNT_ID)!=null)
			userId = ((String)request.getSession().getAttribute(com.cablevision.util.Constantes.SESSION_ACCOUNT_ID));

		return getCheckInClient().getCheckInSoap(userId.getBytes(), passw.getBytes());
	}
	
	/** 
	 * Obtine el cliente para el WS de UCM para obtener un archivo 
	 * @param request El request
	 * @return El cliente de UCM para obtener un archivo
	 */
	public static GetFile getFileClient() throws ServiceException{
		return new GetFile_Impl();
	}
	
	public static CheckIn getCheckInClient() throws ServiceException{
		return new CheckIn_Impl();
	}
	
	public static DocInfo getDocInfoClient() throws ServiceException{
		return new DocInfo_Impl();
	}
	
	public static Workflow getWorkflow() throws ServiceException{
		return new Workflow_Impl();
	}
	
	public static Search getSearch() throws ServiceException{
		return new Search_Impl();
	}
	
	
	/**
	 * Obtiene todas las vacantes de ucm
	 * @param request El request
	 * @return Una lista de {@link Vacante}
	 */
	public static Collection<Vacante> getVacantes(HttpServletRequest request) throws Exception{
		Map<String, Vacante> vacantes = new LinkedHashMap<String,Vacante>();
		QuickSearchResult result = getSearch().getSearchSoap(
				ConfigurationHelper.getPropiedad("ucm.usuario").getBytes(),
				ConfigurationHelper.getPropiedad("ucm.password").getBytes()
			).quickSearch("dDocType <MATCHES> `VACANTE_CV`", null);
		SearchResults[] search = result.getSearchResults();
		
		if(search!=null){
			for(int i=0; i<search.length; i++){
				Vacante vacante = new Vacante();
				vacante.setIdVacante(search[i].getDDocName());
				for(com.stellent.search.IdcProperty property : search[i].getCustomDocMetaData().getProperty()){
					if(property.getName().equals("xTituloVacante")){
						vacante.setTitulo(property.getValue());
					}
				}
				vacante.setDescripcion(getContentByName(search[i].getDDocName(),true));
				vacantes.put(String.valueOf(i), vacante);
			}
		}
		return vacantes.values();
	}
	
	/**
	 * Borra una vacante de UCM
	 * @param idVacante El id de la vancate a borrar
	 */
	public static void postularVacante(String nombreCompleto, String email, String idVacante, CvCurriculum curriculum, HttpServletRequest request) throws Exception{
		GetFileByNameResult result = getFileByName(idVacante,true,request);
		
		Map<String, String> values = new HashMap<String, String>();
		values.put("nombre", nombreCompleto);
		values.put("email", email);
		values.put("tituloVacante", result.getDownloadFile().getFileName());
		
		
		
		String contenido = new String(
				result.getDownloadFile().getFileContent(),"UTF-8"
		);
		
		values.put("descripcionVacante", contenido);
		if(curriculum!=null){
			values.put("escolaridad", curriculum.getStatusSchool()+ " "+curriculum.getSchool());
			values.put("edoCivil", curriculum.getStatusCiv());
			values.put("direccion", curriculum.getAddress());
			values.put("fechaNac", curriculum.getDateBorn());
			values.put("ultimoTrabajo", curriculum.getNameEmp1()+" ("+curriculum.getStartEmp1()+
						"-"+curriculum.getEndEmp1()+")  "+curriculum.getDescriptionEmp1());
			
			if(StringUtils.isNotEmpty(curriculum.getNameEmp2())){
				values.put("antTrabajo",  curriculum.getNameEmp2()+" ("+curriculum.getStartEmp2()+
						"-"+curriculum.getEndEmp2()+")  "+curriculum.getDescriptionEmp2());
			}
			
			String idiomas = curriculum.getIdiom1()+": "+curriculum.getIdiom1Level();
			if(StringUtils.isNotEmpty(curriculum.getIdiom2())){
				idiomas = idiomas.concat(", "+curriculum.getIdiom2()+": "+curriculum.getIdiom2Level());
			}
			if(StringUtils.isNotEmpty(curriculum.getIdiom3())){
				idiomas = idiomas.concat(", "+curriculum.getIdiom3()+": "+curriculum.getIdiom3Level());
			}
			values.put("idiomas", idiomas);
			
			String paqueteria = curriculum.getPack1()+": "+curriculum.getPack1Level();
			if(StringUtils.isNotEmpty(curriculum.getPack2())){
				paqueteria = paqueteria.concat(", "+curriculum.getPack2()+": "+curriculum.getPack2Level());
			}
			if(StringUtils.isNotEmpty(curriculum.getPack3())){
				paqueteria = paqueteria.concat(", "+curriculum.getPack3()+": "+curriculum.getPack3Level());
			}
			values.put("paqueteria", paqueteria);
			values.put("dinero", curriculum.getMoney());
			values.put("telefono",curriculum.getPhone());
		}

		com.stellent.getfile.IdcProperty[] properties = result.getFileInfo()[0].getCustomDocMetaData().getProperty();
		
		String to = ConfigurationHelper.getPropiedad("correo.postularvacante.to",null);
		
		if(properties!=null){
			for (int i = 0; i < properties.length; i++) {
				if("xComments".equals(properties[i].getName())&&StringUtils.isNotBlank(properties[i].getValue())){
					to = properties[i].getValue();
					break;
				}
			}
		}
		
		MailUtil.sendMail(ConfigurationHelper.getPropiedad("correo.postularvacante.subject",null), 
							to, 
							ConfigurationHelper.getPropiedad("correo.postularvacante.from",null), 
							ConfigurationHelper.getPropiedad("correo.postularvacante.templateId",null), 
							values);
		
	}
	
	
	/**
	 * Guarda una vacante en UCM, si trae un id hace un update,
	 * si no lo trae hace un insert
	 * @param vacante La vacante a guardar
	 * @param request El request
	 */
	public static void saveVacante(Vacante vacante, MessageResources config, HttpServletRequest request) throws Exception{
		CheckInSoap soap = getCheckInClient().getCheckInSoap(
							config.getMessage("ucm.usuario").getBytes(),
							config.getMessage("ucm.password").getBytes());
		
		//El archivo ya existe, hacer checkout
		if(StringUtils.isNotEmpty(vacante.getIdVacante())){
			soap.checkOutByName(vacante.getIdVacante(), null);
		}
		
		//Crear el archivo
		IdcFile file = new IdcFile();
		file.setFileName(vacante.getTitulo());
		String contenido = new String(
				vacante.getDescripcion().getBytes()
		);
		file.setFileContent(contenido.getBytes());
		
		IdcPropertyList properties = new IdcPropertyList();

		com.stellent.checkin.IdcProperty folder = new com.stellent.checkin.IdcProperty();
		folder.setName("xCollectionID");
		folder.setValue(config.getMessage("ucm.folder.vacantes.id"));
		com.stellent.checkin.IdcProperty folderTitulo = new com.stellent.checkin.IdcProperty();
		folderTitulo.setName("xTituloVacante");
		folderTitulo.setValue(vacante.getTitulo());
		
		
		com.stellent.checkin.IdcProperty isFinished = new com.stellent.checkin.IdcProperty();
		isFinished.setName("isFinished");
		isFinished.setValue("true");
		
		com.stellent.checkin.IdcProperty xComments = new com.stellent.checkin.IdcProperty();
		xComments.setName("xComments");
		xComments.setValue(LdapUtil.getMail((String)request.getSession().getAttribute(Constantes.SESSION_ACCOUNT_ID)));
		
		properties.setProperty(new com.stellent.checkin.IdcProperty[]{isFinished,folder, folderTitulo,xComments});
		
		if(StringUtils.isEmpty(vacante.getIdVacante())){
			soap.checkInUniversal(null, vacante.getTitulo(), "VACANTE_CV", null, 
			"Mercadotecnia", null, null, file,
			null, properties);
		}else{
			soap.checkInUniversal(vacante.getIdVacante(), vacante.getTitulo(), "VACANTE_CV", null, 
			"Mercadotecnia", null, null, file,
			null, properties);
		}
	}
	
	/**
	 * Borra una vacante de UCM
	 * @param idVacante El id de la vancate a borrar
	 */
	public static void removeVacante(String idVacante, MessageResources config, HttpServletRequest request) throws Exception{
		CheckInSoap soap = getCheckInClient().getCheckInSoap(
				config.getMessage("ucm.usuario").getBytes(),
				config.getMessage("ucm.password").getBytes());
		
		//obtenemos la vacante
		GetFileByNameResult result = getFileByName(idVacante,true,request);
		
		
		// El archivo ya existe, hacer checkout
		if (StringUtils.isNotEmpty(idVacante)) {
//			soap.checkOutByName(idVacante, null);
		}

		// Crear el archivo
		IdcFile file = new IdcFile();
		file.setFileName(result.getDownloadFile().getFileName());
		file.setFileContent(result.getDownloadFile().getFileContent());

		result.getFileInfo()[0].getDReleaseDate();
		
		IdcPropertyList properties = new IdcPropertyList();

		com.stellent.checkin.IdcProperty folder = new com.stellent.checkin.IdcProperty();
		folder.setName("xCollectionID");
		folder.setValue(config.getMessage("ucm.folder.vacantes.id"));
		com.stellent.checkin.IdcProperty folderTitulo = new com.stellent.checkin.IdcProperty();
		folder.setName("xTituloVacante");
		folder.setValue(result.getDownloadFile().getFileName());
		com.stellent.checkin.IdcProperty folderFecha = new com.stellent.checkin.IdcProperty();
		folderFecha.setName("dOutDate");
		SimpleDateFormat formato = new SimpleDateFormat("MM/dd/yy hh:mm aa");
		Calendar fecha = Calendar.getInstance();
		fecha.setTime(formato.parse(result.getFileInfo()[0].getDReleaseDate()));
		fecha.add(Calendar.MINUTE,1 );
		
		folderFecha.setValue(formato.format(fecha.getTime()));
		
//		com.stellent.checkin.IdcProperty isFinished = new com.stellent.checkin.IdcProperty();
//		isFinished.setName("isFinished");
//		isFinished.setValue("true");
		
		properties.setProperty(new com.stellent.checkin.IdcProperty[] { folder,
				folderTitulo, folderFecha });

		
		UpdateDocInfoResult update = soap.updateDocInfo(result.getFileInfo()[0].getDID(), result.getFileInfo()[0].getDRevLabel(), 
				idVacante, result.getFileInfo()[0].getDDocTitle(), "VACANTE_CV", result.getFileInfo()[0].getDDocAuthor(), "Mercadotecnia", result.getFileInfo()[0].getDDocAccount(), properties, properties);
		
//		CheckInUniversalResult resultCI = soap.checkInUniversal(idVacante, result.getDownloadFile().getFileName(),
//				"VACANTE_CV", null, "Mercadotecnia", null, null, file,
//				null, properties);
		
		
	}
	
	
	/**
	 * Borra una vacante de UCM
	 * @param idVacante El id de la vancate a borrar
	 */
	public static void sendMailVacante(String nombre, String email, String idVacante,HttpServletRequest request) throws Exception{
	
		GetFileByNameResult result = getFileByName(idVacante,true,request);
		
		Map<String, String> values = new HashMap<String, String>();
		values.put("nombre", nombre);
		values.put("titulo", result.getDownloadFile().getFileName());
		
		String contenido = new String(
				result.getDownloadFile().getFileContent(),"UTF-8"
		);
		
		values.put("descripcion", contenido);

		MailUtil.sendMail(ConfigurationHelper.getPropiedad("correo.mailvacante.subject",null), 
							email, 
							ConfigurationHelper.getPropiedad("correo.postularvacante.from",null), 
							ConfigurationHelper.getPropiedad("correo.mailvacante.templateId",null), 
							values);
	}	
	
}
