package com.cablevision.controller.email;

import org.apache.beehive.netui.pageflow.Forward;
import org.apache.beehive.netui.pageflow.annotations.Jpf;
import org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.cablevision.controller.base.ControllerBase;
import com.cablevision.service.IEmailService;
import com.cablevision.util.sso.AuteticaGoogle;
import com.cablevision.util.sso.GoogleSSO;
import com.cablevision.vo.Email;

/**
 * Page Flow para usar con encuestas
 * 
 * @author daniela g 24/06/10
 *
 */
@Jpf.Controller(messageBundles = {@Jpf.MessageBundle(bundlePath = "configuracion", bundleName="configuracion")})
public class EmailController extends ControllerBase {
	private static final long serialVersionUID = 1L;
	private transient IEmailService emailService;
	/**
	 * Metodo que muestra la encuesta
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 * @throws Exception 
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "respuesta.jsp") })
	public Forward begin() throws Exception {
		Forward forward = new Forward("success");
		String usuario = getRequest().getParameter("user");
		String password = getRequest().getParameter("password");
		String usuarioDom = usuario+getMessageResources("configuracion").getMessage("email.dominio.user"); 
		
		Email email = new Email();
		email.setEmail(usuario);
		email.setStatus(2);
		
		//USUARIO MIGRADO
		forward.addActionOutput("success", true);
		
		
		//me falta autenticacion
		String respuesta = AuteticaGoogle.AUTENTICA_VALIDO;//AuteticaGoogle.autentica(usuarioDom, password);
		
		if(respuesta.equals(AuteticaGoogle.AUTENTICA_VALIDO)){
			getRequest().getSession().setAttribute("_email_login", usuarioDom);
			
			String[] samlRequestAttributes = (String[])getRequest().getSession().getAttribute("samlRequestAttributes");
			if(samlRequestAttributes != null){
				GoogleSSO.redirectToGoogle(getRequest(), samlRequestAttributes , getResponse());
			}else{
				//forward.addActionOutput("urlGmail", getMessageResources("configuracion").getMessage("email.url.gmail"));
				//blank
			}
		}else{
			forward.addActionOutput("success", false);
			forward.addActionOutput("msg", "No es un usuario registrado.");
		}
		
		return forward;
	}

	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "emailFrame.jsp") })
	public Forward showLoginEmail() {
		Forward forward = new Forward("success");
		return forward;
	}
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "email.jsp") })
	public Forward showEmail() {
		Forward forward = new Forward("success");
		return forward;
	}
	
	/*
	 * Metodo que manda a la pagina de crear cuneta o a la de recuperar password dependiendo si 
	 * el request trae el parametro de nueva
	 * */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "crearCuenta.jsp") })
	public Forward showCrearCuenta() {
		Forward forward = new Forward("success");
		String url = "";
		String cuentaNueva = ScopedServletUtils.getOuterServletRequest(getRequest()).getParameter("nueva");
		
		if("true".equals(cuentaNueva)){
			url = getMessageResources("configuracion").getMessage("email.url.crearCuenta");
		}else{
			url = getMessageResources("configuracion").getMessage("email.url.recuperarPssw");
		}
		
		forward.addActionOutput("url", url);
		return forward;
	}
	
	public IEmailService getEmailService() {
		if(emailService==null){
			ApplicationContext context = (ApplicationContext) getRequest().getSession().getServletContext().getAttribute(
					WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			emailService = (IEmailService)context.getBean("EmailService");
		}
		
		return emailService;
	}
	
	public void setEmailService(IEmailService emailService) {
		this.emailService = emailService;
	}
	
}
