package com.cablevision.controller.newsLetter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.apache.beehive.netui.pageflow.Forward;
import org.apache.beehive.netui.pageflow.annotations.Jpf;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.cablevision.ToInterfase;
import com.cablevision.controller.base.ControllerBase;
import com.cablevision.service.INewsletterService;
import com.cablevision.util.ConfigurationHelper;
import com.cablevision.util.Constantes;
import com.cablevision.util.MailUtil;
import com.cablevision.util.ReCaptchaUtil;
import com.cablevision.util.RespuestaToMyAccount;
import com.cablevision.util.SolrHelper;
import com.cablevision.vo.CvCategorianewsletter;
import com.cablevision.vo.CvNewsletter;
import com.cablevision.vo.CvNewsletterCategoriausuario;
import com.cablevision.vo.CvNewsletterCategoriausuarioPK;

/**
 * Page Flow para usar con NewsLetter
 * 
 * @author Luis Perez - JWMSolutions 24/09/2009
 *
 */
@Jpf.Controller(simpleActions = { @Jpf.SimpleAction(name = "begin", path = "index.jsp") },
		messageBundles={ 
		@Jpf.MessageBundle(bundlePath = "com.cablevision.controller.newsLetter.newsletter", bundleName="newsletter"),
		@Jpf.MessageBundle(bundlePath = "mensajeError", bundleName="mensajeError" )
	})
public class NewsLetterController extends ControllerBase {
	private static final long serialVersionUID = 1L;
	private transient ToInterfase vitriaClient;
	private transient INewsletterService newsletterService; 

	/**
	 * Metodo para enviar una peticion news letter
	 * 
	 * @param form La forma llena con los datos a guardar
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "index.jsp") })
	public Forward enviar(NewsLetterBean form)throws Exception {
		Forward forward = new Forward("success");
		Boolean isValid = true;
		
		if(!ReCaptchaUtil.isValido(getRequest())){
			forward.addActionOutput("msgCapcha", "El Texto no coincide con la imagen");
			isValid = false;
		}

		String mask = "^[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{2,4})$";
		if (isValid){
			if(form.getNombre() ==null || "".equals(form.getNombre()) || "".equals(form.getNombre().trim())){
				isValid = false;
				forward.addActionOutput("msgNombre", "Ingresa un nombre.");
			}else{
				if(!form.getNombre().matches("[a-zA-Z ]*")){
					isValid = false;
					forward.addActionOutput("msgNombre", "Porfavor introduce el nombre sin acentos ni caracteres especiales.");
				}
			}
			if(form.getEmail() ==null || "".equals(form.getEmail())){
				isValid = false;
				forward.addActionOutput("msgEmail", "Ingresa un email v\u00E1lido.");
			}else{
				Pattern pattern = Pattern.compile(mask);
				Matcher fit = pattern.matcher(form.getEmail());
				if(!fit.matches()){
					isValid = false;
					forward.addActionOutput("msgEmail", "Porfavor introduce un email v\u00E1lido");
				}
			}
			
			if(form.getApellidoPaterno() ==null || "".equals(form.getApellidoPaterno()) || "".equals(form.getApellidoPaterno().trim())){
				isValid = false;
				forward.addActionOutput("msgAP", "Ingresa un apellido paterno.");
			}else if(!form.getApellidoPaterno().matches("[a-zA-Z ]*")){
				isValid = false;
				forward.addActionOutput("msgAP", "Porfavor introduce el Apellido Paterno sin acentos ni caracteres especiales.");
			}
			
			if(form.getApellidoMaterno() ==null || "".equals(form.getApellidoMaterno()) || "".equals(form.getApellidoMaterno().trim())){
				isValid = false;
				forward.addActionOutput("msgAM", "Ingresa un apellido materno.");
			}else if(form.getApellidoMaterno()!= null && !form.getApellidoMaterno().matches("[a-zA-Z ]*")){
				isValid = false;
				forward.addActionOutput("msgAM", "Porfavor introduce Apellido Materno sin acentos ni caracteres especiales.");
			}
			
			CvNewsletter cvNewsletter = new CvNewsletter();
			if("Si".equals(form.getEsCliente())){
				if(form.getNoContrato()!=null && !form.getNoContrato().equals("")){
					try{
						cvNewsletter.setNwlAccount(Long.parseLong(form.getNoContrato()));
						if(!form.getNoContrato().startsWith("1")){
							forward.addActionOutput("msgContrato", "El n\u00FAmero de contrato es inv\u00E1lido");
							isValid = false;
						}else if(form.getNoContrato().length()!=8){
							forward.addActionOutput("msgContrato", "El n\u00FAmero de contrato es inv\u00E1lido");
							isValid = false;
						}
					}catch(NumberFormatException e){
						forward.addActionOutput("msgContrato", "El n\u00FAmero de contrato es inv\u00E1lido");
						isValid = false;
					}
				}else{
					forward.addActionOutput("msgContrato", "El n\u00FAmero de contrato es requerido");
					isValid = false;
				}
			}
		
			
			if(isValid){
				cvNewsletter.setNwlEmail(form.getEmail());
				cvNewsletter.setNwlName(form.getNombre().toUpperCase(Locale.ENGLISH)+" "+form.getApellidoPaterno().toUpperCase(Locale.ENGLISH)+" "+form.getApellidoMaterno().toUpperCase(Locale.ENGLISH));
				cvNewsletter.setNwlIsclient(form.getEsCliente()!=null?("no".equals(form.getEsCliente().toLowerCase())?"N":"Y"):"N");
	
				getNewsletterService().persistCvNewsletter(cvNewsletter);
	
				
				//mandar correo al ususario de confirmacion
				
				if(StringUtils.isNotEmpty(cvNewsletter.getNwlEmail())){
	
					MailUtil.sendMail(ConfigurationHelper.getPropiedad("correo.confirmacion.subject",null), form.getEmail(), 
							ConfigurationHelper.getPropiedad("correo.confirmacion.from",null), 
							ConfigurationHelper.getPropiedad("correo.confirmacion.templateId",null), null);
					
				}
				
				forward.addActionOutput("success", "success");
				getRequest().setAttribute("saved", "true");
			}
		}
		return forward;
	}

	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", action = "mostrarContenido")},
				validationErrorForward = @Jpf.Forward(name="fail", path= "index.jsp"), 
				validatableProperties = { @Jpf.ValidatableProperty(propertyName = "emailRegistrado", validateRequired = @Jpf.ValidateRequired(message = "Ingresa un email."), validateEmail = @Jpf.ValidateEmail(message = "Ingresa un email v\u00E1lido.")), 
										  @Jpf.ValidatableProperty(propertyName="emailRegistrado", validateRequired=@Jpf.ValidateRequired(message="Ingresa un email."), validateEmail=@Jpf.ValidateEmail(message="Ingresa un email v\u00E1lido.")) })
	public Forward verificarMail(NewsLetterBean form)throws Exception {
		Forward forward = new Forward("success");
		
		//validar que el correo este dado de alta en newsletter
		List<CvNewsletter> emailsReg = getNewsletterService().findCvNewsletterByEmail(form.getEmailRegistrado());
		if(emailsReg==null || (emailsReg!=null && emailsReg.size()==0)){
			forward = new Forward("fail");
			forward.addActionOutput("errors", "Email no registrado.");
			return forward;
		}
		
		return forward;
	}
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "newsletters.jsp") })
	public Forward getNewsletters()throws Exception {
		Forward forward = new Forward("success");	
		String fecha = getRequest().getParameter("fecha");
		String orden = StringUtils.isNotEmpty(getRequest().getParameter("orden"))?getRequest().getParameter("orden"):null;
		String busqueda = getRequest().getParameter("busqueda");
		String query ="tipo:newsletter";
	
		//tiene permisos para editar y revisar
		boolean canAdd =  getRequest().isUserInRole("CONTRIBUIDOR")||getRequest().isUserInRole("WEBPORTALADMINISTRATOR");
		
		
		if(StringUtils.isNotEmpty(fecha) && !"undefined".equalsIgnoreCase(fecha)){
			SimpleDateFormat sdfFechaCorta = new SimpleDateFormat("MM/dd/yyyy");
			Date fechaCorta = sdfFechaCorta.parse(fecha);
			query = query.concat(" AND newsletter_fechaCorta:\""+SolrHelper.Date2UTC(fechaCorta)+"\"");
			
		}
		if(StringUtils.isNotEmpty(busqueda) && !"undefined".equalsIgnoreCase(busqueda)){
			query = query.concat(" AND (newsletter_titulo:("+busqueda+") OR newsletter_sinopsis:("+busqueda+"))");
		}
		
		forward.addActionOutput("noticias", SolrHelper.query(query, orden, ORDER.asc, false, null, 5).getResults());
		
		forward.addActionOutput("canAdd", canAdd);
		
		return forward;
	}
	
	
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "contenido.jsp") })
	public Forward mostrarContenido(NewsLetterBean form)throws Exception {
		Forward forward = new Forward("success");	
		//tiene permisos para editar y revisar
		boolean canAdd =  getRequest().isUserInRole("CONTRIBUIDOR")||getRequest().isUserInRole("WEBPORTALADMINISTRATOR");
	
		forward.addActionOutput("noticias", SolrHelper.query("tipo:newsletter",5).getResults());
		forward.addActionOutput("canAdd", canAdd);	
		
		//menu de la derecha de busqueda por meses
		forward.addActionOutput("fechaNoticias", getNewsletterPorMes("tipo:newsletter"));
		
		return forward;
	}

	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "ultimosNewsletters.jsp") })
	public Forward getUltimosNewsletters() throws Exception{
		Forward forward = new Forward("success");
		String busqueda = getRequest().getParameter("busqueda");
		
		//menu de la derecha de busqueda por meses
		if(StringUtils.isEmpty(busqueda)){
			forward.addActionOutput("fechaNoticias", getNewsletterPorMes("tipo:newsletter"));
		}else{
			forward.addActionOutput("fechaNoticias", getNewsletterPorMes("tipo:newsletter AND (newsletter_titulo:("+busqueda+") OR newsletter_sinopsis:("+busqueda+"))"));
		}
		
		return forward;
	}
	/**
	 * Metodo para mostrar newsletterCel
	 * 
	 * @param form La forma llena con los datos a guardar
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "newsletterCel.jsp") },loginRequired=true)
	public Forward mostrarNewsletterCel()throws Exception {
		NewsLetterBean form = new NewsLetterBean();
		Forward forward = new Forward("success",form);
		
		RespuestaToMyAccount account = (RespuestaToMyAccount)getSession().getAttribute(Constantes.SESSION_MI_CUENTA);
		String accountId = account.getCvNumberAccount() != null ? account.getCvNumberAccount() : "";
		CvNewsletter data = new CvNewsletter();
		if(accountId!=null && !accountId.equals(""))
			data = getNewsletterService().findCvNewsletterByIdAndAccount(Integer.valueOf(accountId));
		
		if(data!=null && !data.equals("") && data.getNwlEmail()!=null){
			form.setEmail(data.getNwlEmail());
			 List<CvNewsletterCategoriausuario> listUser = getNewsletterService().findCategoriasByUser(data.getNwlId());
			 String[] arregloCat=null;
			 if(listUser!=null){
				 arregloCat = new String[listUser.size()];
				 int cont = 0;
				 for(CvNewsletterCategoriausuario cat:listUser){
					 arregloCat[cont]=cat.getCompId().getNcuIdcategoria().toString();
					 cont++;
				 }
		    }
			form.setCategoriasForm(arregloCat);
			
			List<CvCategorianewsletter> categorias = getNewsletterService().findCategoriaNewsletter();
			forward.addActionOutput("categorias", categorias);
			forward.addActionOutput("existCategorias", "true");
		}else{
			form.setEmail(account.getCorreoContacto());
			forward.addActionOutput("existCategorias", "false");
		}
		form.setSuscribir("SI");
		return forward;
	}
	
	/**
	 * Metodo para actualizar el email en newsletterCel
	 * 
	 * @param form La forma llena con los datos a guardar
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", action = "mostrarNewsletterCel")},
			validationErrorForward = @Jpf.Forward(name="fail", navigateTo=Jpf.NavigateTo.currentPage),
			validatableProperties = { 
				@Jpf.ValidatableProperty(propertyName="email", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.email"), 
						validateEmail=@Jpf.ValidateEmail(messageKey="error.formato.email")) 
			}
	)
	public Forward newsletterUpdateEmail(NewsLetterBean form)throws Exception {
		Forward forward = new Forward("success");
		RespuestaToMyAccount account = (RespuestaToMyAccount)getSession().getAttribute(Constantes.SESSION_MI_CUENTA);
		String accountId = account.getCvNumberAccount() != null ? account.getCvNumberAccount() : "";
		CvNewsletter data = new CvNewsletter();
		if(accountId!=null && !accountId.equals("")){
			data = getNewsletterService().findCvNewsletterByIdAndAccount(Integer.valueOf(accountId));
			if(data!=null && data.getNwlId()==null){
				if(form.getSuscribir().equals("SI")){
					data.setNwlName(account.getNombreContacto()+" "+account.getApellidoPaterno()+" "+account.getApellidoMaterno());
					data.setNwlEmail(form.getEmail());
					data.setNwlIsclient("si");
					data.setNwlAccount(Long.valueOf(account.getCvNumberAccount()));
					getNewsletterService().persistCvNewsletter(data);
					forward.addActionOutput("exito", getMessageResources("newsletter").getMessage("grabar.datos.exito"));
				}
			}else{
				if(form.getSuscribir().equals("NO")){
					List<CvNewsletterCategoriausuario> listUser = getNewsletterService().findCategoriasByUser(data.getNwlId());
					if(listUser!=null && listUser.size()>0){
						for(CvNewsletterCategoriausuario catUs:listUser){
							getNewsletterService().removeCvNewsletterCategoriausuario(catUs);
						}
					}
					getNewsletterService().removeCvNewsletter(data);
					forward.addActionOutput("exito", getMessageResources("newsletter").getMessage("eliminar.datos.exito"));
				}else{
					data.setNwlEmail(form.getEmail());
					getNewsletterService().persistCvNewsletter(data);
					forward.addActionOutput("exito", getMessageResources("newsletter").getMessage("modificar.datos.exito"));
				}
			}
		}
		return forward;
	}
	
	/**
	 * Metodo para actualizar las categorias en newsletterCel
	 * 
	 * @param form La forma llena con los datos a guardar
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", action = "mostrarNewsletterCel") })
	public Forward newsletterUpdateCateg(NewsLetterBean form)throws Exception {
		Forward forward = new Forward("success");
		RespuestaToMyAccount account = (RespuestaToMyAccount)getSession().getAttribute(Constantes.SESSION_MI_CUENTA);
		String accountId = account.getCvNumberAccount() != null ? account.getCvNumberAccount() : "";
		CvNewsletter data = new CvNewsletter();
		List<CvNewsletterCategoriausuario> list = new ArrayList<CvNewsletterCategoriausuario>();
		if(accountId!=null && !accountId.equals("")){
			data = getNewsletterService().findCvNewsletterByIdAndAccount(Integer.valueOf(accountId));
			if(data!=null && !data.equals("")){
				List<CvNewsletterCategoriausuario> listUser = getNewsletterService().findCategoriasByUser(data.getNwlId());
				if(listUser!=null && listUser.size()>0){
					for(CvNewsletterCategoriausuario catUs:listUser){
						getNewsletterService().removeCvNewsletterCategoriausuario(catUs);
					}
				}
				
				if(form.categoriasForm != null && form.categoriasForm.length > 0){
					for(String cat:form.categoriasForm){
						CvCategorianewsletter catNews = new CvCategorianewsletter();
						CvNewsletterCategoriausuario categoria = new CvNewsletterCategoriausuario();
						CvNewsletterCategoriausuarioPK id = new CvNewsletterCategoriausuarioPK();
						id.setNcuIdcategoria(Integer.valueOf(cat));
						id.setNcuIdusuario(data.getNwlId());
						categoria.setCompId(id);
						catNews.setCnlId(Long.valueOf(id.getNcuIdcategoria()));
						categoria.setCvCategorianewsletter(catNews);
						list.add(categoria);
					}
				}
				else{
					forward.addActionOutput("msgCat", "msgCat");
					return forward;
				}
			}
		}
		getNewsletterService().saveCategorias(list);
		forward.addActionOutput("exito", getMessageResources("newsletter").getMessage("grabar.categorias.exito"));
		return forward;
	}
	
	private List<String> getNewsletterPorMes(String query) throws Exception{
		QueryResponse res = SolrHelper.queryNewsletter(
				query, "newsletter_fecha", ORDER.desc , true, new String[] {"newsletter_fechaCorta"}, "newsletter_fechaCorta", null);
		FacetField facetField = res.getFacetField("newsletter_fechaCorta");
		List<String> fechasMap = new ArrayList<String>();
		
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		sdfDate.setTimeZone(TimeZone.getTimeZone("UTC"));
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date fecha = null;
		
		if (facetField.getValues() != null) {
			for (FacetField.Count element : facetField.getValues()) {
				fecha = sdfDate.parse(element.getName());
				if(fecha != null)
					fechasMap.add(sdf.format(fecha));
			}
		}
		
		Collections.reverse(fechasMap);
		if(fechasMap.size()>5){
			fechasMap = fechasMap.subList(0, 5);
		}
		
		return fechasMap;
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


	/**
	 * Bean Class para usar con news letter
	 * 
	 * @author Luis Perez - JWMSolutions 24/09/2009
	 *
	 */
	@Jpf.FormBean
	public static class NewsLetterBean implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		
		private String nombre;
		private String apellidoPaterno;
		private String apellidoMaterno;
		private String email;
		private String esCliente;
		private String noContrato;
		private String challenge;
		private String uresponse;
		private String emailRegistrado;
		private String suscribir;
		private String[] categoriasForm;
		
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public String getApellidoPaterno() {
			return apellidoPaterno;
		}
		public void setApellidoPaterno(String apellidoPaterno) {
			this.apellidoPaterno = apellidoPaterno;
		}
		public String getApellidoMaterno() {
			return apellidoMaterno;
		}
		public void setApellidoMaterno(String apellidoMaterno) {
			this.apellidoMaterno = apellidoMaterno;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getEsCliente() {
			return esCliente;
		}
		public void setEsCliente(String esCliente) {
			this.esCliente = esCliente;
		}
		public String getNoContrato() {
			return noContrato;
		}
		public void setNoContrato(String noContrato) {
			this.noContrato = noContrato;
		}
		public String getChallenge() {
			return challenge;
		}
		public void setChallenge(String challenge) {
			this.challenge = challenge;
		}
		public String getUresponse() {
			return uresponse;
		}
		public void setUresponse(String uresponse) {
			this.uresponse = uresponse;
		}
		public String getEmailRegistrado() {
			return emailRegistrado;
		}
		public void setEmailRegistrado(String emailRegistrado) {
			this.emailRegistrado = emailRegistrado;
		}
		public String getSuscribir() {
			return suscribir;
		}
		public void setSuscribir(String suscribir) {
			this.suscribir = suscribir;
		}
		public String[] getCategoriasForm() {
			return categoriasForm;
		}
		public void setCategoriasForm(String[] categoriasForm) {
			this.categoriasForm = categoriasForm;
		}
	}
	
	public ToInterfase getVitriaClient() {
		if(vitriaClient==null){
		   ApplicationContext context = (ApplicationContext)getRequest().getSession().getServletContext().getAttribute(
		     WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		   vitriaClient = (ToInterfase)context.getBean("VitriaClient");
		}
		return vitriaClient;
	}

	public INewsletterService getNewsletterService() {
		if(newsletterService==null){
			ApplicationContext context = (ApplicationContext)getRequest().getSession().getServletContext().getAttribute(
					WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			newsletterService = (INewsletterService)context.getBean("NewsletterService");
		}
		
		return newsletterService;
	}

}