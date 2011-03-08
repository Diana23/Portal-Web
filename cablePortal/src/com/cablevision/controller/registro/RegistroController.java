package com.cablevision.controller.registro;

import java.net.URI;
import java.rmi.RemoteException;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.security.auth.login.FailedLoginException;
import javax.servlet.http.HttpSession;
import javax.xml.rpc.ServiceException;

import org.apache.beehive.netui.pageflow.Forward;
import org.apache.beehive.netui.pageflow.annotations.Jpf;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.bea.portlet.GenericURL;
import com.bea.portlet.PageURL;
import com.cablevision.ToInterfase;
import com.cablevision.controller.base.ControllerBase;
import com.cablevision.forms.RegistroBean;
import com.cablevision.portal.ErrorVitriaException;
import com.cablevision.util.Blowfish;
import com.cablevision.util.Constantes;
import com.cablevision.util.LdapUtil;
import com.cablevision.util.ResponseToValidateAccount;
import com.cablevision.util.RespuestaToMyAccount;
import com.cablevision.util.RespuestaToRegister;
import com.cablevision.util.ValidarPasswordUtil;
import com.cablevision.util.ValidateErrors;

/**
 * Page Flow para usar en el registro de clientes de cablevision
 * para usar los servicios en linea
 * 
 * @author Luis Perez - JWMSolutions 24/09/2009
 *
 */
@Jpf.Controller( 
				messageBundles = { @Jpf.MessageBundle(bundlePath = "com.cablevision.controller.registro.registro", bundleName="registroBundle"),
								   @Jpf.MessageBundle(bundlePath = "mensajeError", bundleName="mensajeError" )})

public class RegistroController extends ControllerBase {
	private static final long serialVersionUID = 1L;
	private RegistroBean registroBean;
	transient ToInterfase vitriaClient;

	/**
	 * Metodo para mostrar el registro
	 * @param form 
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "datosUno.jsp") })
	public Forward begin(RegistroBean form) {
		registroBean = new RegistroBean();
		Forward forward = new Forward("success");
		return forward;
	}
	
	/**
	 * Metodo para registrar a un cliente de cablevision en los servicios en linea
	 * @param form La forma llena con los datos a guardar
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", action = "begin") })
	public Forward buscarCanal(RegistroBean form) {
		Forward forward = new Forward("success");
		return forward;
	}

	/**
	 * Metodo que muestra el formulario del primer paso para registrarse y hace las primeras 
	 * validaciones
	 * @param form La forma llena con los datos a guardar
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "next", path="datosDos.jsp") }, 
				useFormBean = "registroBean",
				validationErrorForward = @Jpf.Forward(name="fail", navigateTo=Jpf.NavigateTo.currentPage), 
				validatableProperties = { 
					@Jpf.ValidatableProperty(displayName="apellido paterno", propertyName = "apellidoPaterno", validateRequired = @Jpf.ValidateRequired(messageKey = "error.requerido.paterno"),
							validateMask=@Jpf.ValidateMask(regex="^[A-Za-z\u00D1\u00F1 ]*$", messageKey="error.formato.paterno")), 
					@Jpf.ValidatableProperty(displayName="apellido materno", propertyName = "apellidoMaterno", validateRequired = @Jpf.ValidateRequired(messageKey = "error.requerido.materno"),
							validateMask=@Jpf.ValidateMask(regex="^[A-Za-z\u00D1\u00F1 ]*$", messageKey="error.formato.materno")), 
					@Jpf.ValidatableProperty(propertyName="email", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.email"), 
							validateEmail=@Jpf.ValidateEmail(messageKey="error.formato.email"), 
							validateValidWhen=@Jpf.ValidateValidWhen(messageKey="error.confirmacion.email", condition="${actionForm.email == actionForm.emailConfirmacion}")), 
					@Jpf.ValidatableProperty(propertyName="noContrato", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.noContrato"),
							validateType=@Jpf.ValidateType(messageKey="error.numero.contrato",type=int.class),
							validateMinLength=@Jpf.ValidateMinLength(messageKey="error.numero.contrato", chars=8),
							validateMaxLength=@Jpf.ValidateMaxLength(messageKey="error.numero.contrato", chars=8)), 
					@Jpf.ValidatableProperty(displayName="el nombre", propertyName="nombre", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.name"),
							validateMask=@Jpf.ValidateMask(regex="^[A-Za-z\u00D1\u00F1 ]*$", messageKey="error.formato.nombre")), 
					@Jpf.ValidatableProperty(propertyName="telefono", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.telefono"),
							validateType=@Jpf.ValidateType(messageKey="error.numero.telefono",type=int.class),
							validateMinLength=@Jpf.ValidateMinLength(messageKey="error.numero.telefono", chars=8),
							validateMaxLength=@Jpf.ValidateMaxLength(messageKey="error.numero.telefono", chars=8)),
					@Jpf.ValidatableProperty(propertyName="idUsuario", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.idUsuario")) } )
	public Forward pasoUno(com.cablevision.forms.RegistroBean form) throws Exception{
		Forward forward = new Forward("next");
		
		//consulta a vitria para ver si es un usuario de cablevision
		ResponseToValidateAccount response = this.getVitriaClient().getProjects_CVNPW_Initial_ToInterfase().toValidateAccount(form.getNoContrato(), "55"+form.getTelefono());
		
		ValidateErrors.validateErrorResponse(response.getError(), getMessageResources("mensajeError"));
		
		if(response.getCvPrincipalPhoneNumber() == null || "".equals(response.getCvPrincipalPhoneNumber()) ){
			forward = new Forward("fail");
			forward.addActionOutput("errores", "Necesita ser usuario de cablevision para poder registrarse.");
			return forward;
		}
		
		//consulta a ldap para verificar que el usuario id no exista
		if(LdapUtil.existUserInLdap(form.getIdUsuario()) == true){
			forward = new Forward("fail");
			forward.addActionOutput("errores", "El ID de usuario ya existe, elige otro.");
			return forward;
		}

		return forward;
	}

	/**
	 * Metodo que muestra el formulario del segundo paso para registrarse
	 * @param form La forma llena con los datos a guardar
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "next", path="datosTres.jsp"), 
							 @Jpf.Forward(name = "back", action="pasoUno") }, 
				useFormBean = "registroBean", 
				validationErrorForward = @Jpf.Forward(name="fail", navigateTo=Jpf.NavigateTo.currentPage), 
				validatableProperties = { @Jpf.ValidatableProperty(propertyName = "password", 
												validateRequired = @Jpf.ValidateRequired(messageKey = "error.requerido.pass"), 
												validateValidWhen = @Jpf.ValidateValidWhen(messageKey = "error.confirmacion.pass", condition = "${actionForm.password == actionForm.passwordConfirmacion}"),
												validateMinLength = @Jpf.ValidateMinLength(messageKey="error.size.pass", chars=8)), 
										  @Jpf.ValidatableProperty(propertyName="preguntaConfirmacion", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.pregunta")), 
										  @Jpf.ValidatableProperty(displayName="respuesta confirmacion", propertyName="respuestaConfirmacion", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.respuesta"),
												  validateMask=@Jpf.ValidateMask(regex="^[A-Za-z\u00D1\u00F1 ]*$", messageKey="error.formato.respuestaConfirmacion")) } )
	public Forward pasoDos(com.cablevision.forms.RegistroBean form) {
		Forward forward = new Forward("next");
		String pass = form.getPassword();
		if(!ValidarPasswordUtil.validatePassword(pass)) {
			forward = new Forward("fail");
			forward.addActionOutput("errores", "La contrase&ntilde;a debe de contener de 8 a 25 caracteres " +
					"incluyendo por lo menos una may&uacute;scula, una min&uacute;scula, un n&uacute;mero y un caracteres especial como @#$%!&/()=?");
		}
		return forward;
	}
	
	/**
	 * Metodo que muestra el formulario del tercer paso para registrarse
	 * @param form La forma llena con los datos a guardar
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", action="registrar"),
							 @Jpf.Forward(name = "back", path="datosDos.jsp")}, 
				useFormBean = "registroBean", 
				validationErrorForward = @Jpf.Forward(name="fail", navigateTo=Jpf.NavigateTo.currentPage),
				validatableProperties = { @Jpf.ValidatableProperty(propertyName = "entiendo", validateValidWhen = @Jpf.ValidateValidWhen(messageKey = "error.requerido.entiendo", condition = "${actionForm.entiendo == true}")) } )
	public Forward pasoTres(com.cablevision.forms.RegistroBean form) {
		Forward forward = new Forward("success");
		return forward;
	}
	
	/**
	 * Metodo que hace el registro
	 * @param form La forma llena con los datos a guardar
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "../ordenadministrativa/index.jsp") }, useFormBean = "registroBean" )
	public Forward registrar(com.cablevision.forms.RegistroBean form) throws Exception {
		Forward forward = new Forward("success");
		RespuestaToRegister response;
		
		String nombre= "";
		String segundoNombre = "";
		StringTokenizer stNombre = new StringTokenizer(form.getNombre()," ");
		if(stNombre.countTokens()>1){
			nombre = stNombre.nextToken();
			segundoNombre = stNombre.nextToken();
		}else if(stNombre.countTokens()==1){
			nombre = stNombre.nextToken();
		}
		
		
		response = getVitriaClient().getProjects_CVNPW_Initial_ToInterfase().toRegister(nombre.toUpperCase(Locale.ENGLISH), 
					segundoNombre.toUpperCase(Locale.ENGLISH), form.getApellidoPaterno().toUpperCase(Locale.ENGLISH), 
				   form.getApellidoMaterno().toUpperCase(Locale.ENGLISH), form.getEmail(), form.getIdUsuario(), 
				   form.getPassword(), form.getPreguntaConfirmacion().toUpperCase(Locale.ENGLISH), 
				   form.getRespuestaConfirmacion().toUpperCase(Locale.ENGLISH), form.getNoContrato(), form.getTelefono());
		
		//cambio de mensaje de error, ya no llama al properties si es el error SBL-001
		PageURL url = PageURL.createPageURL(getRequest(), getResponse(), "servicios_enlinea_login");
		url.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false");
		url.setForcedAmpForm(false);
		
		PageURL urlContact = PageURL.createPageURL(getRequest(), getResponse(), "atencion_contactanos");
		urlContact.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false");
		urlContact.setForcedAmpForm(false);
		
		if(response.getError()!= null && (response.getError().getCvErrorCode().equals("SBL-001"))){
			String mensajeError = "LO SENTIMOS<br/><br/> "+
									"El n\u00FAmero de contrato que estas registrando ya fue dado de alta, si lo compartiste, olvidaste la contrase\u00F1a <br/>" +
									"o est\u00E1n haciendo mal uso de ello te recomendamos que cambies ID de usuario y contrase\u00F1a." +
									"<a href=\""+url+"\">Hazlo aqu\u00ED</a><br/>" +
									"Si necesitas ayuda personalizada.<br/>Ll\u00E1manos a Call Center 51 599 699<br/>Env\u00EDanos un <a href=\""+urlContact+"\">mensaje</a>";
			throw new ErrorVitriaException(mensajeError);
		}
		
		
		String nombreCompleto = (form.getNombre()+" "+form.getApellidoPaterno()+" "+form.getApellidoMaterno()).toUpperCase(Locale.ENGLISH);
		forward.addActionOutput("usuario", nombreCompleto+" "+response.getNumeroFactura());
		ValidateErrors.validateErrorResponse(response.getError(), getMessageResources("mensajeError"));
		
		try{
			login(form.getIdUsuario(), form.getPassword());
		}catch(FailedLoginException e){
			String mensajeError = "LO SENTIMOS<br/><br/> "+
					"El ID de usuario que estas registrando ya fue dado de alta, si lo compartiste, olvidaste la contrase\u00F1a <br/>" +
					"o est\u00E1n haciendo mal uso de ello te recomendamos que cambies ID de usuario y contrase\u00F1a." +
					"<a href=\""+url+"\">Hazlo aqu\u00ED</a><br/>" +
					"Si necesitas ayuda personalizada.<br/>Ll\u00E1manos a Call Center 51 599 699<br/>Env\u00EDanos un <a href=\""+urlContact+"\">mensaje</a>";
			throw new ErrorVitriaException(mensajeError);
		}
		getRequest().getSession().setAttribute(Constantes.SESSION_MI_PASSWD, Blowfish.encriptar(form.getPassword(), Constantes.ENCRIPT_PASSWD));
		setCuentaEnSesion(form.getIdUsuario());
		
		final PageURL urlSeL = PageURL.createPageURL(getRequest(), getResponse(), "servicios_enlinea_inicio");
		urlSeL.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false");
		urlSeL.setForcedAmpForm(false);
		forward = new Forward(new URI(urlSeL.toString()));
		
		return forward;
	}

	/**
	 * Va a vitria por la cuenta del usuario y la pone en sesion
	 * @return true si se encontr\u00F3 cuenta de usuario cablevision, de lo contrario false
	 * @throws RemoteException
	 * @throws ServiceException
	 */
	private boolean setCuentaEnSesion(final String userAccount) throws RemoteException, ServiceException{
		final RespuestaToMyAccount response = getVitriaClient().getProjects_CVNPW_Initial_ToInterfase().toMyAccount(userAccount);
		getSession().setAttribute(Constantes.SESSION_ACCOUNT_ID, userAccount);
		
		if(response==null){
			return false;
		}else{
			getSession().setAttribute(Constantes.SESSION_MI_CUENTA, response);
			return true;
		}
		
		
	}
	
	public RegistroBean getRegistroBean() {
		return registroBean;
	}

	public void setRegistroBean(RegistroBean registroBean) {
		this.registroBean = registroBean;
	}

	public ToInterfase getVitriaClient() {
		if(vitriaClient==null){
			ApplicationContext context = (ApplicationContext)getRequest().getSession().getServletContext().getAttribute(
					WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			vitriaClient = (ToInterfase)context.getBean("VitriaClient");
			
		}
		return vitriaClient;
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

}