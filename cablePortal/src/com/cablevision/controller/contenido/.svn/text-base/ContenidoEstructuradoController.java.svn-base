package com.cablevision.controller.contenido;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.beehive.netui.pageflow.Forward;
import org.apache.beehive.netui.pageflow.PageFlowController;
import org.apache.beehive.netui.pageflow.PageFlowUtils;
import org.apache.beehive.netui.pageflow.annotations.Jpf;
import org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils;
import org.apache.commons.lang.StringUtils;

import com.bea.netuix.servlets.controls.page.PageBackingContext;
import com.bea.netuix.servlets.controls.portlet.backing.PortletBackingContext;
import com.bea.wlp.customization.service.common.ServiceScopeContext;
import com.bea.wlp.customization.service.portlet.PortletCustomizationSvc;
import com.bea.wlp.services.Services;
import com.cablevision.util.Constantes;
import com.cablevision.util.ContenidoHelper;
import com.cablevision.util.UcmUtil;
import com.cablevision.vo.CvContenido;
import com.cablevision.vo.CvTemplate;


/**
 * Clase para renderizar el contenido estructurado
 * @author daniela g
 *
 */

@Jpf.Controller(messageBundles = { @Jpf.MessageBundle(bundlePath="configuracion", bundleName="configuracion")})
public class ContenidoEstructuradoController extends PageFlowController{
	private static final long serialVersionUID = 1L;
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", action = "getContenidoRenderizado")})
	public Forward begin() throws Exception {
		Forward forward = new Forward("success");
		return forward;
	}
	
	/**
	 * Usar en caso de necesitar usar el controller sin autenticarse
	 */
//	@Override
//	public boolean isUserInRole(String roleName) {
//		return true;
//	}
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "pagina.jsp"), 
							 @Jpf.Forward(name = "vacio", action = "datosVacios")})
	public Forward getContenidoRenderizado(PaginaFormBean form) throws Exception {
		Forward forward = new Forward("success");
		
		PageBackingContext context = PageBackingContext.getPageBackingContext(getRequest());
		PortletBackingContext porContext = PortletBackingContext.getPortletBackingContext(getRequest());

		getRequest().getRequestURL();
		String estructuraId = null;
		String contenidoId = null;
		String templateId = null;
		String portlet = porContext!=null?porContext.getInstanceLabel():"";
		
		templateId = (String)getRequest().getAttribute("templateId");
		
		if(StringUtils.isEmpty(templateId)){
			templateId = getRequest().getParameter("templateId");
			
			if(StringUtils.isEmpty(templateId)){
				templateId = porContext.getPortletPreferences(getRequest()).getValue("templateId", null);
				
				if(StringUtils.isEmpty(templateId)){
					templateId = context.getProperty("templateId_"+portlet);
					
					if(StringUtils.isEmpty(templateId)){
						templateId = context.getProperty("templateId");
						estructuraId = context.getProperty("estructuraId");
						contenidoId = context.getProperty("contenidoId");
					}else{
						estructuraId = context.getProperty("estructuraId_"+portlet);
						contenidoId = context.getProperty("contenidoId_"+portlet);
					}
				}else{
					estructuraId = porContext.getPortletPreferences(getRequest()).getValue("estructuraId", null);
					contenidoId = porContext.getPortletPreferences(getRequest()).getValue("contenidoId", null);
				}
			}else{
				estructuraId = (String)getRequest().getParameter("estructuraId");
				contenidoId = (String)getRequest().getParameter("contenidoId");
			}
		}else{
			estructuraId = (String)getRequest().getAttribute("estructuraId");
			contenidoId = (String)getRequest().getAttribute("contenidoId");
		}
		
		if(StringUtils.isEmpty(contenidoId)){
			contenidoId = form.getContenidoId();
		}
		
		//formato al contenidoId, si viene en formato de directorios obtenemos el id
		if(StringUtils.isNotEmpty(contenidoId)){
			String[] tokens = contenidoId.split("/");
			if(tokens.length>0){
				String id = tokens[tokens.length-1];
				id = StringUtils.substringBefore(id, ".");
				form.setContenidoId(id.toUpperCase().trim());
			}else{
				form.setContenidoId(contenidoId.trim());
			}
		}
		
		
		if(StringUtils.isNotEmpty(estructuraId)){
			form.setEstructuraId(estructuraId.trim());
		}
		if(StringUtils.isNotEmpty(templateId)){
			form.setTemplateId(templateId.trim());
		}
		
		if(StringUtils.isEmpty(form.getTemplateId()) || StringUtils.isEmpty(form.getEstructuraId())){
			forward = new Forward("vacio");
			return forward;
		}
		
		
		//poner atributos al portlet 
		if(porContext != null){
			//setPortletPreferences(form.getContenidoId(), form.getTemplateId(), form.getEstructuraId(), porContext);
		}
		
		getContenido(getRequest(), getResponse(), form.getContenidoId(), 
					 form.getTemplateId(), form.getEstructuraId(), form.getFolderId(), form.getNombre());
		return forward;
	}
	
	private void setPortletPreferences(String contenidoId, String templateId, String estructuraId, 
										PortletBackingContext porContext) throws Exception{
		login("weblogic", "weblogic");
		ServiceScopeContext serviceScopeContext = new ServiceScopeContext(getRequest(), "cablePortal");
		//serviceScopeContext.setLocale(locale);
		//serviceScopeContext.setUsername("weblogic");
		serviceScopeContext.setDesktop("cablevision");
		serviceScopeContext.setPortal("cablevision");
		serviceScopeContext.setScope("admin");
		//serviceScopeContext.setUsername(NetuixRestCommandHelper.getUsernameParameter(restContext, serviceScopeContext.getScope()));
		
		PortletCustomizationSvc portletCustomizationSvc = (PortletCustomizationSvc)Services.getService(PortletCustomizationSvc.class);
		if(portletCustomizationSvc!=null){
			portletCustomizationSvc.updatePortletPreferences(serviceScopeContext, porContext.getInstanceLabel(), "contenidioId", new String[]{contenidoId}, false, false, false);
			portletCustomizationSvc.updatePortletPreferences(serviceScopeContext, porContext.getInstanceLabel(), "estructuraId", new String[]{templateId}, false, false, false);
			portletCustomizationSvc.updatePortletPreferences(serviceScopeContext, porContext.getInstanceLabel(), "templateId", new String[]{estructuraId}, false, false, false);
		}	
	}
	
	/**
     * Método principal de renderizar contenido llama
     * */
    public static String getContenido(HttpServletRequest request, HttpServletResponse response, String contenidoId, 
    								  String templateId, String estructuraId, String folderId, String nombre) throws Exception {
		
		//tiene permisos para editar y revisar
		boolean canEdit =  request.isUserInRole("CONTRIBUIDOR")||request.isUserInRole("WEBPORTALADMINISTRATOR");
//		boolean canEdit =  true;
		boolean ultima = "true".equals(ScopedServletUtils.getOuterServletRequest(request).getParameter("ultima"))?true:false;
		
		//esta dentro de un flujo?
		if(canEdit && (UcmUtil.isFileInWorkflow(contenidoId) && UcmUtil.canEditWorkflow(contenidoId, request))){
			request.setAttribute("inWorkFlow", true);
		}else{
			request.setAttribute("inWorkFlow", false);
		}
		
		request.setAttribute("canEdit", canEdit);
		
		if((canEdit||request.isUserInRole("REVIEWER"))){
			request.setAttribute("ultima", ultima);
		}
		
		request.setAttribute("folderId", folderId);
		
		//tiene permisos para borrar
		if(canEdit)
			request.setAttribute("canDelete",ScopedServletUtils.getOuterServletRequest(request).getParameter("borrar"));
		
		//obtener contenido
		CvContenido contenido = ContenidoHelper.renderizeContent(
						request, response, contenidoId, estructuraId, 
						templateId, nombre, ultima,"", canEdit);
		
		request.setAttribute("contenidoId", StringUtils.isNotEmpty(contenido.getDDocName())? contenido.getDDocName():contenidoId);
		request.setAttribute("estructuraId", estructuraId);
		request.setAttribute("templateId", templateId);
		request.setAttribute("strHtml", contenido.getContent());
		
		return  contenido.getContent();
	}
	
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "editarPagina.jsp")},
				rolesAllowed = {"WEBPORTALADMINISTRATOR","CONTRIBUIDOR"})
//	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "editarPagina.jsp")})
	public Forward getContenidoEditable(PaginaFormBean form) throws Exception {
		Forward forward = new Forward("success");
		StringWriter sw = new java.io.StringWriter();
		List editores = new java.util.ArrayList();
		String html = "";
		
		PaginaFormBean contenidoNuevo = ContenidoHelper.getContenidoConEstructura(form.getContenidoId(), form.getEstructuraId(), getRequest());
		form.setContenido(contenidoNuevo.getContenido());
		
		if(StringUtils.isNotEmpty(contenidoNuevo.getContenido())){
			html = ContenidoHelper.getHTMLfromXML(contenidoNuevo.getContenido(), getRequest(), sw, editores);
		}
		
		forward.addActionOutput("strHtml", html);
		forward.addActionOutput("sw", sw.toString());
		forward.addActionOutput("strLista", ContenidoHelper.listToString(editores));
		forward.addActionOutput("contenidoId", form.getContenidoId());
		forward.addActionOutput("templateId", form.getTemplateId());
		forward.addActionOutput("estructuraId", StringUtils.isNotEmpty(form.getEstructuraId())?form.getEstructuraId():contenidoNuevo.getEstructuraId());
		forward.addActionOutput("popup", PageFlowUtils.getActionOutput("popup", getRequest()));
		
		if(StringUtils.isNotEmpty(form.getFolderId())){
			forward.addActionOutput("folderId", form.getFolderId());
		}
		
		return forward;
	}
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path="confirmationUcm.jsp")},
				rolesAllowed = {"WEBPORTALADMINISTRATOR","CONTRIBUIDOR"})
//	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path="confirmationUcm.jsp")})
	public Forward saveContenido(PaginaFormBean form) throws Exception {
		Forward forward = new Forward("success");
		String xml = getRequest().getParameter("__NodeContent__");
		form.setContenido(xml);
		
		CvContenido contenidoGuardado = ContenidoHelper.saveToFile(form, getRequest());
		
		form.setContenidoId(contenidoGuardado.getDDocName());
		forward.addActionOutput("contenidoId", contenidoGuardado.getDDocName());
		forward.addActionOutput("name", contenidoGuardado.getName());
		forward.addActionOutput("id", contenidoGuardado.getDID());
		
		return forward;
	}
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path="respuesta.jsp")})
	public Forward deleteContenido(PaginaFormBean form) throws Exception {
		Forward forward = new Forward("success");
		ContenidoHelper.deleteFile(form, getRequest());
		return forward;
	}
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path="pagina.jsp")})
	public Forward previewContenido(PaginaFormBean form) throws Exception {
		Forward forward = new Forward("success");
		
		String contenido = ContenidoHelper.getContent(ScopedServletUtils.getOuterServletRequest(getRequest()).getParameter("__NodeContent__"), 
							ScopedServletUtils.getOuterRequest(getRequest()));
		String strHtml =  ContenidoHelper.renderizeContent(
							ScopedServletUtils.getOuterRequest(getRequest()),  getResponse(), "", "", 
							ScopedServletUtils.getOuterServletRequest(getRequest()).getParameter("templateId")
							,"",true, contenido, true).getContent();
		
		//obtener contenido
		getRequest().setAttribute("strHtml", strHtml);
		
		return forward;
	}
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path="hmtlFromXml.jsp")})
	public Forward getHtml(PaginaFormBean form) throws Exception {
		Forward forward = new Forward("success");
		
		String xml = getRequest().getParameter("__NodeContent__");
	    java.io.StringWriter sw = new java.io.StringWriter();
	    java.util.List editores = new java.util.ArrayList();
	    
	    String strHtml = ContenidoHelper.getHTMLfromXML(xml, getRequest(), sw, editores);
		forward.addActionOutput("strHtml", strHtml);
		forward.addActionOutput("sw", sw.toString());
		forward.addActionOutput("strLista", ContenidoHelper.listToString(editores));
	    
		return forward;
	}
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path="workflow.jsp")})
	public Forward showPaginaWorkFlow(PaginaFormBean form) throws Exception {
		Forward forward = new Forward("success");
		forward.addActionOutput("contentId", form.getContenidoId());
		return forward;
	}
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path="workflow.jsp")})
	public Forward changeWorkFlow(PaginaFormBean form) throws Exception {
		Forward forward = new Forward("success");
		String contenidoId = getRequest().getParameter("contentId");
		
		if("1".equals(getRequest().getParameter("opcionFlujo"))){
			UcmUtil.workFlowApprove(contenidoId, getRequest());
			//indexar
			ContenidoHelper.indexarContenido(contenidoId, true);
		}else if("2".equals(getRequest().getParameter("opcionFlujo"))){
			String motivo = getRequest().getParameter("motivo");
			UcmUtil.workFlowReject(contenidoId, motivo, getRequest());
		}
		forward.addActionOutput("contentId", contenidoId);
		return forward;
	}
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path="abreUcm.jsp")})
	public Forward autenticar(PaginaFormBean form) throws Exception {
		Forward forward = new Forward("success");
		String type = getRequest().getParameter("type");
		String urlTemplate =  getMessageResources("configuracion").getMessage("ucm.urlTemplate.test");
		if(StringUtils.isNotEmpty(getRequest().getParameter("idArchivo"))){
			forward.addActionOutput("urlArchivo", 
					getMessageResources("configuracion").getMessage("ucm."+type+
					".popup.urlArchivo", urlTemplate,getRequest().getParameter("idArchivo")));
		}
		
		if(StringUtils.isNotEmpty(type) && type.equalsIgnoreCase("folder")){
			forward.addActionOutput("urlArchivo", getMessageResources("configuracion").getMessage("ucm.folder.popup.url", 
					getSession().getAttribute(Constantes.SESSION_ACCOUNT_ID)));
		}
		
		return forward;
	}
	
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path="links.jsp")})
	public Forward showLinks(PaginaFormBean form) throws Exception {
		Forward forward = new Forward("success");
		return forward;
	}
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path="datosVacios.jsp")})
	public Forward datosVacios() throws Exception {
		Forward forward = new Forward("success");
		
		Map<String,CvTemplate> templates = UcmUtil.getTemplates();
		ContenidoHelper.setTemplatesEnCache(templates);
		forward.addActionOutput("templates", templates.values());
		forward.addActionOutput("estructuras", UcmUtil.getEstructuras().values());
		return forward;
	}
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path="contenidoPopup.jsp")})
	public Forward showContenidoPopup() throws Exception {
		Forward forward = new Forward("success");
		forward.addActionOutput("estructuras", UcmUtil.getEstructuras().values());
		return forward;
	}
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", action="getContenidoEditable")})
	public Forward nextContenidoPopup(PaginaFormBean form) throws Exception {
		Forward forward = new Forward("success");
		
		String xml = ContenidoHelper.getContentById(form.getEstructuraId(), "", true, true, getRequest());
		
		if(StringUtils.isEmpty(form.getContenidoId())){
			CvContenido contenidoNuevo = UcmUtil.saveContentFile("", xml, form.getEstructuraId(), form.getFolderId(), 
					form.getNombre(), getRequest());
			form.setContenidoId(contenidoNuevo.getDDocName());
			UcmUtil.workFlowApprove(form.getContenidoId(), getRequest());
		}
		
		
		forward.addOutputForm(form);
		forward.addActionOutput("popup", true);
		return forward;
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

	
	@Jpf.FormBean
	public static class PaginaFormBean implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		private String estructuraId;
		private String contenidoId;
		private String templateId;
		private String contenido;
		private String folderId;
		private String nombre;
		
		public String getEstructuraId() {
			return estructuraId;
		}
		public void setEstructuraId(String estructuraId) {
			this.estructuraId = estructuraId;
		}
		public String getContenidoId() {
			return contenidoId;
		}
		public void setContenidoId(String contenidoId) {
			this.contenidoId = contenidoId;
		}
		public String getTemplateId() {
			return templateId;
		}
		public void setTemplateId(String templateId) {
			this.templateId = templateId;
		}
		public String getContenido() {
			return contenido;
		}
		public void setContenido(String contenido) {
			this.contenido = contenido;
		}
		public String getFolderId() {
			return folderId;
		}
		public void setFolderId(String folderId) {
			this.folderId = folderId;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
	}
}
