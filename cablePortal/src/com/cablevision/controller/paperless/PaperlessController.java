package com.cablevision.controller.paperless;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import mx.com.cablevision.vitria.EmailRequestData;
import mx.com.cablevision.vitria.ParpelessRequest;
import mx.com.cablevision.vitria.ValEmailRequestData;
import mx.com.cablevision.vitria.VerifyEmailReturnData;

import org.apache.beehive.netui.pageflow.Forward;
import org.apache.beehive.netui.pageflow.annotations.Jpf;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.cablevision.controller.base.ControllerBase;
import com.cablevision.service.IPaperlessService;
import com.cablevision.util.Constantes;
import com.cablevision.util.RespuestaToMyAccount;
import com.cablevision.vo.CvPaperless;

/**
 * Page Flow para el modulo de paperless
 * 
 * @author Daniela G - JWMSolutions 13/04/2010
 *
 */
@Jpf.Controller(
		messageBundles = { 
				@Jpf.MessageBundle(bundlePath = "com.cablevision.controller.paperless.paperless", bundleName="paperlessBundle"),
				@Jpf.MessageBundle(bundlePath = "mensajeError", bundleName="errorBundle")},
		loginRequired = true
)

public class PaperlessController extends ControllerBase {
	private static final long serialVersionUID = 1L;
	private PaperlessFormBean paperlessBean;
	private transient ParpelessRequest paperlessClient;
	private transient IPaperlessService paperlessService;
	
	/**
	 * Metodo para iniciar muestra el index
	 * 
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { 
			@Jpf.Forward(name = "success", path = "index.jsp")
		}
	)
	public Forward begin(){
		Forward forward = new Forward("success");
		//obtenemos datos de la cuenta
		RespuestaToMyAccount cuenta = (RespuestaToMyAccount)getSession().getAttribute(Constantes.SESSION_MI_CUENTA);
		
		//correo electronico y  fecha de registro 
		try{
			CvPaperless cvPaperless = getPaperlessService().findCvPaperlessById(cuenta.getCvNumberAccount());
			if(cvPaperless==null || (cvPaperless!=null && StringUtils.isEmpty(cvPaperless.getPplContrato()))){
				paperlessBean = new PaperlessFormBean(cuenta.getCorreoContacto(),false, "");
			}else{
				//obtener datos del ws
				paperlessBean = new PaperlessFormBean();
				paperlessBean.setEmail(cvPaperless.getPplEmail());
				paperlessBean.setPaperless(true);
				SimpleDateFormat formato = new SimpleDateFormat("MM/yy");
				paperlessBean.setFechaInscripcion(formato.format(cvPaperless.getPplFechaIngreso()));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		forward.addActionOutput("paperlessBean", paperlessBean);
		return forward;
	}
	
	/**
	 * Metodo para mostrar el registro al paperless
	 * 
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = {@Jpf.Forward(name = "success", path = "cambiarEmail.jsp")},
				useFormBean = "paperlessBean"
	)
	public Forward mostrarRegistro(PaperlessFormBean form){
		Forward forward = new Forward("success");
		
		paperlessBean.setEstado(getRequest().getParameter("estado"));
		String leyenda = getMessageResources("paperlessBundle").getMessage("mensaje.checkbox."+paperlessBean.getEstado());
		String leyendaPopup = getMessageResources("paperlessBundle").getMessage("mensaje.popup."+paperlessBean.getEstado());
		RespuestaToMyAccount cuenta = (RespuestaToMyAccount)getSession().getAttribute(Constantes.SESSION_MI_CUENTA);
		
		forward.addActionOutput("titulo", 
				getMessageResources("paperlessBundle").getMessage("texto.registro.titulo."+paperlessBean.getEstado()));
		forward.addActionOutput("mensaje", 
				getMessageResources("paperlessBundle").getMessage(
						"texto.registro.parrafo1."+paperlessBean.getEstado(),
						cuenta.getNombreContacto()+ " " + cuenta.getApellidoPaterno() + " " + cuenta.getApellidoMaterno()));
		
		forward.addActionOutput("textoCheck", leyenda);
		forward.addActionOutput("textoPopup", leyendaPopup);
		forward.addActionOutput("paperlessBean", paperlessBean);
		return forward;
	}
	
	/**
	 * Metodo para mostrar el registro al paperless
	 * 
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = {@Jpf.Forward(name = "success", path = "confirmaCambiarEmail.jsp")},
				useFormBean = "paperlessBean"
	)
	public Forward mostrarConfirmacionCambiarEmail(PaperlessFormBean form){
		Forward forward = new Forward("success");
		
		paperlessBean.setEstado(getRequest().getParameter("estado"));
		String leyendaPopup = getMessageResources("paperlessBundle").getMessage("mensaje.popup."+paperlessBean.getEstado());
		
		forward.addActionOutput("textoPopup", leyendaPopup);
		return forward;
	}
	
	
	/**
	 * realizar el regsitrro del usuario al servicio paperless
	 * 
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(
				forwards = {
						@Jpf.Forward(name = "condiciones", path = "condiciones.jsp"),
						@Jpf.Forward(name="confirmacion", action="mostrarConfirmacion" )
				}, 
				useFormBean = "paperlessBean", 
				validationErrorForward = @Jpf.Forward(name="fail", navigateTo=Jpf.NavigateTo.currentPage), 
				validatableProperties = {@Jpf.ValidatableProperty(
							propertyName = "email", 
							validateEmail = @Jpf.ValidateEmail(messageKey = "error.formato.email"), 
							validateRequired = @Jpf.ValidateRequired(messageKey = "error.req.email")), 
							@Jpf.ValidatableProperty(
									propertyName="paperless", 
									validateValidWhen=@Jpf.ValidateValidWhen(
											messageKey="error.requerido.paperless", 
											condition="${actionForm.check == true}"
									)
							)}
	)
	public Forward registro(PaperlessFormBean form){
		Forward forward = new Forward("condiciones");
		
		try{
			if(StringUtils.isNotEmpty(form.estado) && !form.estado.equals("alta")){
				forward = new Forward("confirmacion");
			}
			
			String respuestaError = validarEmail(form.email);
			if(StringUtils.isNotEmpty(form.estado) && !form.estado.equals("baja") && StringUtils.isNotEmpty(respuestaError)){			
				forward = new Forward("fail");
				forward.addActionOutput("errores", respuestaError);
			}
		}catch(Exception e){
			forward = new Forward("fail");
			forward.addActionOutput("errores", "Hubo  un error, intente mas tarde");
			e.printStackTrace();
		}
		return forward;
	}
	
	/**
	 * Metodo para mostrar la confirmacion del registro
	 * 
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "confirmacion.jsp")},
				validationErrorForward = @Jpf.Forward(name="fail", navigateTo=Jpf.NavigateTo.currentPage), 
				validatableProperties = { @Jpf.ValidatableProperty(propertyName="check", validateValidWhen=@Jpf.ValidateValidWhen(messageKey="error.requerido.check", condition="${actionForm.check == true}")) }
	)
	public Forward mostrarConfirmacion(PaperlessFormBean form){
		Forward forward = new Forward("success");
		if(paperlessBean != null){
			forward.addActionOutput("emailActual", paperlessBean.getEmail());
			forward.addActionOutput("textoBoton", getMessageResources("paperlessBundle").
					getMessage("texto.confirmacion.boton."+paperlessBean.getEstado()));
			forward.addActionOutput("titulo", getMessageResources("paperlessBundle").
					getMessage("texto.confirmacion.titulo."+paperlessBean.getEstado()));
			forward.addActionOutput("parrafo1", getMessageResources("paperlessBundle").
					getMessage("texto.confirmacion.parrafo1."+paperlessBean.getEstado()));
			forward.addActionOutput("parrafo2", getMessageResources("paperlessBundle").
					getMessage("texto.confirmacion.parrafo2."+paperlessBean.getEstado()));
		}
		return forward;
	}
	
	/**
	 * Metodo para mostrar la notificacion
	 * 
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(
			forwards = { @Jpf.Forward(name = "success", path = "notificacion.jsp")},
			validationErrorForward = @Jpf.Forward(name="fail", navigateTo=Jpf.NavigateTo.currentPage)
	)
	public Forward mostrarNotificacion(){
		Forward forward = new Forward("success");
		String respuesta = "";
		RespuestaToMyAccount cuenta = (RespuestaToMyAccount)getSession().getAttribute(Constantes.SESSION_MI_CUENTA);
		String idUsuario = getSession().getAttribute(Constantes.SESSION_ACCOUNT_ID).toString();
		String nombreUsuario = cuenta.getNombreContacto()+" "+cuenta.getApellidoPaterno()+" "+cuenta.getApellidoMaterno();
		
		try{
			if(paperlessBean!= null && paperlessBean.getEstado()!=null){
				if(paperlessBean.getEstado().equals("alta")){
					EmailRequestData emailData = new EmailRequestData();
					emailData.setAccount(cuenta.getCvNumberAccount());
					emailData.setEmail(paperlessBean.getEmail());
					emailData.setName(nombreUsuario);
					emailData.setUser(idUsuario);  
					
					//vitria
					respuesta = getPaperlessClient().getProjects_CVNPW_Initial_ParpelessRequest().newEmail(emailData);
				}else if(paperlessBean.getEstado().equals("baja")){
					EmailRequestData deleteEmail = new EmailRequestData();
					deleteEmail.setAccount(cuenta.getCvNumberAccount());
					deleteEmail.setUser(idUsuario);
					deleteEmail.setName(nombreUsuario);
					paperlessBean.setEmail(getPaperlessService().findCvPaperlessById(cuenta.getCvNumberAccount()).getPplEmail());
					deleteEmail.setEmail(paperlessBean.getEmail());
					//vitria
					respuesta = getPaperlessClient().getProjects_CVNPW_Initial_ParpelessRequest().deleteEmail(deleteEmail);
				}else if(paperlessBean.getEstado().equals("cambio")){
					EmailRequestData emailData = new EmailRequestData();
					emailData.setAccount(cuenta.getCvNumberAccount());
					emailData.setEmail(paperlessBean.getEmail());
					emailData.setName(cuenta.getNombreContacto()+" "+cuenta.getApellidoPaterno()+" "+cuenta.getApellidoMaterno());
					emailData.setUser(idUsuario);
					
					//vitria
					respuesta = getPaperlessClient().getProjects_CVNPW_Initial_ParpelessRequest().changeEmail(emailData);
				}
			}
		}catch(Exception e){
			forward = new Forward("fail");
			forward.addActionOutput("errores", "Hubo  un error, intente mas tarde");
			e.printStackTrace();
		}
		
		String error = getMensajeError(respuesta);
		if(StringUtils.isEmpty(error)){
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			forward.addActionOutput("fecha", format.format(Calendar.getInstance().getTime()));
			forward.addActionOutput("operacion", getMessageResources("paperlessBundle").
					getMessage("mensaje.operacion."+paperlessBean.getEstado()));
			forward.addActionOutput("notificacionTitulo", getMessageResources("paperlessBundle").
					getMessage("texto.notificacion.titulo."+paperlessBean.getEstado()));
			forward.addActionOutput("notificacionFooter", getMessageResources("paperlessBundle").
					getMessage("texto.notificacion.parrafo1."+paperlessBean.getEstado()));
			
			forward.addActionOutput("email", paperlessBean.getEmail());
			forward.addActionOutput("paperlessBean", paperlessBean);
		}else{
			forward = new Forward("fail");
			forward.addActionOutput("errores", error );
		}
		
		return forward;
	}

	
	public String validarEmail(String email) throws Exception{
		RespuestaToMyAccount cuenta = (RespuestaToMyAccount)getSession().getAttribute(Constantes.SESSION_MI_CUENTA);
		ValEmailRequestData emailData = new ValEmailRequestData();
		emailData.setAccount(cuenta.getCvNumberAccount());
		emailData.setEmail(email);
		
		VerifyEmailReturnData response = getPaperlessClient().getProjects_CVNPW_Initial_ParpelessRequest().verifyEmail(emailData);
		return getMensajeError(response.getResponse());
	}
	
	
	/**
	 * Metodo para mostrar el error de mensaje ya sea que no exista o regresar el mensaje de un properties
	 * dependiendo del codigo de error
	 * 
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	public String getMensajeError(String codigoError){
		String mensajeError = "";
		if(StringUtils.isNotEmpty(codigoError) &&(!codigoError.equalsIgnoreCase("ack")&&!codigoError.equalsIgnoreCase("ok"))){
			mensajeError = getMessageResources("errorBundle").getMessage("error."+codigoError.trim()+".mensaje");
			if(StringUtils.isEmpty(mensajeError)){
				mensajeError = getMessageResources("errorBundle").getMessage("error.default.mensaje");
			}
		}
		
		return mensajeError;
	}
	
	public ParpelessRequest getPaperlessClient(){
		if(paperlessClient == null ){
			ApplicationContext context = (ApplicationContext) getRequest().getSession().getServletContext().getAttribute(
					WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		paperlessClient = (ParpelessRequest)context.getBean("PaperlessClient");
		}
		return paperlessClient;
	}
	
	public IPaperlessService getPaperlessService() {
		if(paperlessService == null){
			ApplicationContext context = (ApplicationContext)getRequest().getSession().getServletContext().getAttribute(
					WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			paperlessService = (IPaperlessService)context.getBean("PaperlessService");
		}
		return paperlessService;
	}
	
	@Jpf.FormBean
	public static class PaperlessFormBean implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		private String email;
		private boolean paperless;
		private String estado;
		private boolean check = false;
		private String fechaInscripcion;
		
		public PaperlessFormBean(){
			;
		}

		public PaperlessFormBean(String email, boolean paperless, String estado){
			this.email = email;
			this.paperless = paperless;
			this.estado = estado;
		}
		
		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		
		public boolean getPaperless() {
			return paperless;
		}

		public void setPaperless(boolean paperless) {
			this.paperless = paperless;
		}

		public String getEstado() {
			return estado;
		}

		public void setEstado(String estado) {
			this.estado = estado;
		}

		public boolean getCheck() {
			return check;
		}

		public void setCheck(boolean check) {
			this.check = check;
		}

		public String getFechaInscripcion() {
			return fechaInscripcion;
		}

		public void setFechaInscripcion(String fechaInscripcion) {
			this.fechaInscripcion = fechaInscripcion;
		}
	}
}
