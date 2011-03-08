package com.cablevision.controller.recuperarpassword;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.xml.rpc.ServiceException;

import org.apache.beehive.netui.pageflow.Forward;
import org.apache.beehive.netui.pageflow.PageFlowController;
import org.apache.beehive.netui.pageflow.annotations.Jpf;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

//import com.cablevision.ResponsetoSecretQuestionByAccount;
import com.cablevision.ResponsetoSecretQuestionByAccount;
import com.cablevision.ToInterfase;
import com.cablevision.portal.ErrorVitriaException;
import com.cablevision.service.IUsuarioPortalService;
import com.cablevision.util.ConfigurationHelper;
import com.cablevision.util.MailUtil;
import com.cablevision.util.PasswordUtil;
import com.cablevision.util.ReCaptchaUtil;
import com.cablevision.util.ResponseToSecretQuestion;
import com.cablevision.util.RespuestaToMyAccount;
import com.cablevision.util.RespuestaToUpdateAccount;
import com.cablevision.util.ValidateErrors;
import com.cablevision.vo.CvContrasenaHistorial;
import com.cablevision.vo.CvUsuarioPortal;

/**
 * Page Flow para usar con recuperar password
 * 
 * @author Luis Perez - JWMSolutions 28/09/2009
 *
 */
@Jpf.Controller(
		messageBundles = { 
				@Jpf.MessageBundle(bundlePath = "com.cablevision.controller.recuperarpassword.recuperarPassword", bundleName="passwordBundle"),
				@Jpf.MessageBundle(bundlePath = "mensajeError", bundleName="mensajeError")
		}
)
public class RecuperarPasswordController extends PageFlowController {
	private static final long serialVersionUID = 1L;
	transient ToInterfase vitriaClient;
	PasswordBean passwordBean;
	private transient IUsuarioPortalService usuarioPortalService;


	/**
	 * Metodo begin
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "olvidoclave.jsp") })
	public Forward begin() {
		Forward forward = new Forward("success");
		passwordBean = new PasswordBean();
		return forward;
	}

	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "olvidoclaveform.jsp") })
	public Forward olvidoClaveForm() {
		Forward forward = new Forward("success");
		passwordBean = new PasswordBean();
		return forward;
	}

	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "olvidouserform.jsp") })
	public Forward olvidoUserForm() {
		Forward forward = new Forward("success");
		passwordBean = new PasswordBean();
		return forward;
	}

	/**
	 * Metodo para enviar datos de mail y no contrato para confirma q sean validos
	 * 
	 * @param form La forma llena con los datos a guardar
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { 
			@Jpf.Forward(name = "success", path = "preguntaclave.jsp"), 
			@Jpf.Forward(name = "fail", path = "olvidoclaveform.jsp"),
			@Jpf.Forward(name = "fatal", path = "errorFatal.jsp")})
			public Forward enviar(PasswordBean form) throws Exception {
		Forward forward = new Forward("success");
		try {
			String idAccount = getRequest().getParameter("idAccount");
			forward.addActionOutput("origen", "clave");
			form.setIdAccount(idAccount);
			
			if(!ReCaptchaUtil.isValido(getRequest())){
				forward = new Forward("fail");
				forward.addActionOutput("errores", "El Texto no coincide con la imagen");
				forward.addActionOutput("idAccount", idAccount);
				return forward;
			}
			
			if(StringUtils.isBlank(idAccount)){
				forward = new Forward("fail");
				forward.addActionOutput("errores", "El ID de usuario es requerido");
				forward.addActionOutput("idAccount", idAccount);
				return forward;
			}
			

			ResponseToSecretQuestion response =  getVitriaClient().getProjects_CVNPW_Initial_ToInterfase().toSecretQuestion(form.getIdAccount());

			form.setPreguntaConfirmacion(response.getQuestion());
			form.setRespuesta(response.getAnswer());


			if(ValidateErrors.containsErrors(response.getError())){
				forward.addActionOutput("errores", getMessageResources("passwordBundle").getMessage("error.datos.novalidos"));
				forward.setName("fail");
			}

			
			
			forward.addActionOutput("pregunta", form.getPreguntaConfirmacion());
			getSession().setAttribute("form", form);
			return forward;
		} catch (Exception e) {
			e.printStackTrace();
		}
		forward = new Forward("fatal");
		getSession().setAttribute("errores", "Lo sentimos por el momento el servicio<br>no est&aacute; disponible.<br><br> Intenta nuevamente m&aacute;s tarde.");
		forward.addActionOutput("errores", "Lo sentimos por el momento el servicio<br>no est&aacute; disponible.<br><br> Intenta nuevamente m&aacute;s tarde.");
		return forward;
	}


	/**
	 * Metodo para enviar datos de mail y no contrato para confirma q sean validos
	 * 
	 * @param form La forma llena con los datos a guardar
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { 
			@Jpf.Forward(name = "success", path = "preguntaclave.jsp"),
			@Jpf.Forward(name = "fail", path = "olvidouserform.jsp"),
			@Jpf.Forward(name = "fatal", path = "errorFatal.jsp")}) 
			public Forward enviarContrato(PasswordBean form) throws Exception {
		Forward forward = new Forward("success");
		try {
			String noContrato = getRequest().getParameter("noContrato");
			form.setNoContrato(noContrato);

			if(!ReCaptchaUtil.isValido(getRequest())){
				forward = new Forward("fail");
				forward.addActionOutput("errores", "El Texto no coincide con la imagen");
				forward.addActionOutput("idAccount", noContrato);
				return forward;
			}
			
			if(StringUtils.isBlank(noContrato)){
				forward = new Forward("fail");
				forward.addActionOutput("errores", "El ID de usuario es requerido");
				forward.addActionOutput("idAccount", noContrato);
				return forward;
			}
			
			ResponsetoSecretQuestionByAccount responseByAccount = getVitriaClient().getProjects_CVNPW_Initial_ToInterfase().toSecretQuestionByAccount(form.getNoContrato());

			form.setPreguntaConfirmacion(responseByAccount.getQuestion());
			form.setRespuesta(responseByAccount.getAnswer());
			form.setRecuperaId(true);
			form.setIdAccount(responseByAccount.getUserId());

			if(ValidateErrors.containsErrors(responseByAccount.getError())){
				forward.addActionOutput("errores", getMessageResources("passwordBundle").getMessage("error.datos.novalidos"));
				forward.setName("fail");
			}

			forward.addActionOutput("pregunta", form.getPreguntaConfirmacion());
			getSession().setAttribute("form", form);
			return forward;
		} catch (Exception e) {
			e.printStackTrace();
		}
		forward = new Forward("fatal");
		getSession().setAttribute("errores", "Lo sentimos por el momento el servicio<br>no est&aacute; disponible.<br><br> Intenta nuevamente m&aacute;s tarde.");
		forward.addActionOutput("errores", "Lo sentimos por el momento el servicio<br>no est&aacute; disponible.<br><br> Intenta nuevamente m&aacute;s tarde.");
		return forward;
	}

	/**
	 * Metodo para enviar una peticion news letter
	 * 
	 * @param form La forma llena con los datos a guardar
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 * @throws ServiceException 
	 * @throws RemoteException 
	 * @throws MessagingException 
	 * @throws NamingException 
	 * @throws UnsupportedEncodingException 
	 * @throws ErrorVitriaException 
	 */
	@Jpf.Action(forwards = { 
			@Jpf.Forward(name = "success", path = "leyenda.jsp"),
			@Jpf.Forward(name = "fail", path = "preguntaclave.jsp"),
			@Jpf.Forward(name = "fatal", path = "errorFatal.jsp")})
			public Forward recuperarPassword(PasswordBean form) throws RemoteException, ServiceException, UnsupportedEncodingException, NamingException, MessagingException, ErrorVitriaException {
		Forward forward = new Forward("fail");
		try {
			if(getSession().getAttribute("form") != null) {
				form = (PasswordBean)getSession().getAttribute("form");
			}
			String valorOrigen = getRequest().getParameter("valorOrigen");
			forward.addActionOutput("valOrigen", valorOrigen);
			String respuestaCliente = getRequest().getParameter("respuestaCliente");
			form.setRespuestaCliente(respuestaCliente);

			if(form.getRespuestaCliente() != null && form.getRespuestaCliente().equalsIgnoreCase(form.getRespuesta())){

				String templateId = "";
				String newPass = "";
				String titulo = "correo.recuperapwd.subject";
				RespuestaToMyAccount response = getVitriaClient().getProjects_CVNPW_Initial_ToInterfase().toMyAccount(form.getIdAccount());

				if(form.isRecuperaId()){
					templateId = ConfigurationHelper.getPropiedad("correo.recuperaid.templateId",null);
					titulo = "correo.recuperaid.subject";
				}else{
					newPass = getPassword(8);
					templateId = ConfigurationHelper.getPropiedad("correo.cambiocontra.templateId",null);
					titulo = "correo.recuperapwd.subject";

					RespuestaToUpdateAccount responseCambia = getVitriaClient()
							.getProjects_CVNPW_Initial_ToInterfase()
							.toUpdateAccount(form.getIdAccount(),
									StringUtils.isNotBlank(response.getNombreContacto())?response.getNombreContacto():".",
									StringUtils.isNotBlank(response.getApellidoPaterno())?response.getApellidoPaterno():".",
									StringUtils.isNotBlank(response.getApellidoMaterno())?response.getApellidoMaterno():".",
									response.getCorreoContacto(), 
									newPass);

					ValidateErrors.validateErrorResponse(responseCambia.getCvErrorCode(), getMessageResources("mensajeError"));

				}

				if(StringUtils.isNotEmpty(response.getCorreoContacto())){
					Map<String, String> values = new HashMap<String, String>();
					values.put("nombre", response.getNombreContacto());
					values.put("apellidoPaterno", response.getApellidoPaterno());
					values.put("apellidoMaterno", response.getApellidoMaterno());
					values.put("nuevoPassword",newPass);
					values.put("idUsuario", form.getIdAccount());

					MailUtil.sendMail(ConfigurationHelper.getPropiedad(titulo,null), 
							response.getCorreoContacto(), /*"jorge.ruiz@jwmsolutions.com",*/ 
							ConfigurationHelper.getPropiedad("correo.cambiocontra.from",null), 
							templateId, 
							values);

					try {
						if(!form.isRecuperaId()) {
							CvUsuarioPortal usuarioPortal = getUsuarioPortalService().findCvUsuarioPortalById(form.getIdAccount());

							if(usuarioPortal != null){
								usuarioPortal.setCupFechaUltimoIntentoLogin(null);

								getUsuarioPortalService().persistCvUsuarioPortal(usuarioPortal);//update o insert con el mismo metodo lo hace

								CvContrasenaHistorial cvContrasenaHistorial = new CvContrasenaHistorial();
								cvContrasenaHistorial.setCvUsuarioPortal(usuarioPortal);
								cvContrasenaHistorial.setCchContrasena(PasswordUtil.getEncodedPassword(newPass));

								getUsuarioPortalService().persistCvContrasenaHistorial(cvContrasenaHistorial);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();						
						forward = new Forward("fail");						
						forward.addActionOutput("critico", "Hubo  un error, intente mas tarde");
						return forward;
					}
				}
				forward = new Forward("success");
				return forward;
			}
			forward.addActionOutput("origen", valorOrigen);
			forward.addActionOutput("pregunta", form.getPreguntaConfirmacion());
			forward.addActionOutput("errores", "La respuesta no es la correcta");
			return forward;
		} catch (Exception e) {
			e.printStackTrace();
		}
		forward = new Forward("fatal");
		getSession().setAttribute("errores", "Lo sentimos por el momento el servicio<br>no est&aacute; disponible.<br><br> Intenta nuevamente m&aacute;s tarde.");
		forward.addActionOutput("errores", "Lo sentimos por el momento el servicio<br>no est&aacute; disponible.<br><br> Intenta nuevamente m&aacute;s tarde.");
		return forward;
	}

	public static String getPassword(int n) {
		char[] pw = new char[n];
		int c  = 'A';
		int  r1 = 0;
		for (int i=0; i < n; i++)
		{
			r1 = (int)(Math.random() * 3);
			switch(r1) {
			case 0: c = '0' +  (int)(Math.random() * 10); break;
			case 1: c = 'a' +  (int)(Math.random() * 26); break;
			case 2: c = 'A' +  (int)(Math.random() * 26); break;
			}
			pw[i] = (char)c;
		}
		return new String(pw);
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
	 * Bean Class para usar con news letter
	 * 
	 * @author Luis Perez - JWMSolutions 24/09/2009
	 *
	 */
	@Jpf.FormBean
	public static class PasswordBean implements java.io.Serializable {
		private static final long serialVersionUID = 1L;

		private String esCliente;
		private String noContrato;
		private String preguntaConfirmacion;
		private String respuesta;
		private String respuestaCliente;
		private String idAccount;
		private boolean recuperaId;


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
		public String getPreguntaConfirmacion() {
			return preguntaConfirmacion;
		}
		public void setPreguntaConfirmacion(String preguntaConfirmacion) {
			this.preguntaConfirmacion = preguntaConfirmacion;
		}
		public String getRespuesta() {
			return respuesta;
		}
		public void setRespuesta(String respuesta) {
			this.respuesta = respuesta;
		}
		public String getRespuestaCliente() {
			return respuestaCliente;
		}
		public void setRespuestaCliente(String respuestaCliente) {
			this.respuestaCliente = respuestaCliente;
		}
		public String getIdAccount() {
			return idAccount;
		}
		public void setIdAccount(String idAccount) {
			this.idAccount = idAccount;
		}
		public boolean isRecuperaId() {
			return recuperaId;
		}
		public void setRecuperaId(boolean recuperaId) {
			this.recuperaId = recuperaId;
		}

	}

	public PasswordBean getPasswordBean() {
		return passwordBean;
	}

	public void setPasswordBean(PasswordBean passwordBean) {
		this.passwordBean = passwordBean;
	}

	private IUsuarioPortalService getUsuarioPortalService() {
		if (usuarioPortalService == null) {
			ApplicationContext context = (ApplicationContext) getRequest().getSession().getServletContext().getAttribute(
					WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			usuarioPortalService = (IUsuarioPortalService) context.getBean("UsuarioPortalService");
		}
		return usuarioPortalService;
	}
}