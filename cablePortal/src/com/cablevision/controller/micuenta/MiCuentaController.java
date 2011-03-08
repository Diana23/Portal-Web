package com.cablevision.controller.micuenta;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpSession;
import javax.xml.rpc.ServiceException;

import org.apache.beehive.netui.pageflow.Forward;
import org.apache.beehive.netui.pageflow.annotations.Jpf;
import org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.upload.FormFile;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.bea.portlet.GenericURL;
import com.bea.portlet.PageURL;
import com.cablevision.ToInterfase;
import com.cablevision.controller.base.ControllerBase;
import com.cablevision.forms.RegistroBean;
import com.cablevision.portal.ErrorVitriaException;
import com.cablevision.service.IUsuarioPortalService;
import com.cablevision.util.Blowfish;
import com.cablevision.util.Constantes;
import com.cablevision.util.CustomEntry;
import com.cablevision.util.ImageUtil;
import com.cablevision.util.LogFilter;
import com.cablevision.util.PasswordUtil;
import com.cablevision.util.Respuesta;
import com.cablevision.util.RespuestaToMyAccount;
import com.cablevision.util.RespuestaToUpdateAccount;
import com.cablevision.util.ValidarPasswordUtil;
import com.cablevision.util.ValidateErrors;
import com.cablevision.vo.CvContrasenaHistorial;
import com.cablevision.vo.CvUsuarioPortal;
import com.cablevision.ws.WSPortType;
import com.cablevision.ws.WS_Impl;
import com.pb.e2.vault.beans.xsd.Attachment;
import com.pb.e2.vault.service.VaultService;
import com.pb.e2.vault.service.VaultServicePortType;
import com.pb.e2.vault.service.VaultService_Impl;

/**
 * Page Flow para usar con mi cuenta
 * 
 * @author Luis Perez - JWMSolutions 24/09/2009
 *
 */
@Jpf.Controller(
		multipartHandler = Jpf.MultipartHandler.memory,
		catches={
	        @Jpf.Catch(type=RemoteException.class, path="/com/cablevision/controller/error.jsp"),
	        @Jpf.Catch(type=ServiceException.class, path="/com/cablevision/controller/error.jsp")
	    },
		messageBundles={ 
				@Jpf.MessageBundle(bundlePath = "configuracion", bundleName="configuracion"),
			@Jpf.MessageBundle(bundlePath = "com.cablevision.controller.micuenta.micuenta", bundleName="micuentaBundle"),
			@Jpf.MessageBundle(bundlePath = "com.cablevision.controller.micuenta.portabilidad", bundleName="portabilidadBundle"),
			@Jpf.MessageBundle(bundlePath = "mensajeError", bundleName="mensajeError" )
		},
		loginRequired=true
)
public class MiCuentaController extends ControllerBase {
	private static final long serialVersionUID = 1L;
	transient ToInterfase vitriaClient;
	private transient IUsuarioPortalService usuarioPortalService;
	private static final org.apache.log4j.Logger log = LogFilter.getLoggerInstance();
	
	
	
	/**
	 * Metodo para refrescar mi cuenta
	 * 
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 * @throws ServiceException 
	 * @throws RemoteException 
	 * @throws URISyntaxException 
	 */
	@Jpf.Action(forwards = { 
			@Jpf.Forward(name = "success", path = "index.jsp"),
			@Jpf.Forward(name = "logout", action = "begin")  
		}
	)
	public Forward begin() throws RemoteException, ServiceException, URISyntaxException {
		
		if("true".equals(ScopedServletUtils.getOuterServletRequest(getRequest()).getParameter( "logout" ))){
			getSession().invalidate();
			return new Forward("logout");
		}
		
		if("false".equals(getSession().getAttribute(Constantes.USUARIO_EN_VITRIA))){
			final PageURL url = PageURL.createPageURL(getRequest(), getResponse(), "cablevision_portal_page_home");
			url.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false");
			url.setForcedAmpForm(false);
			return new Forward(new URI(url.toString()));
		}
		
		if(getSession().getAttribute(Constantes.SESSION_MI_CUENTA)==null){
			setCuentaEnSesion();
		}
		
		Forward forward = new Forward("success");
		return forward;
	}
	
	/**
	 * Metodo para refrescar mi cuenta
	 * 
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 * @throws ServiceException 
	 * @throws RemoteException 
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "resumen.jsp") })
	public Forward resumen() throws RemoteException, ServiceException {
		setCuentaEnSesion();
		
		Forward forward = new Forward("success");
		Calendar fecha = Calendar.getInstance();
		DecimalFormat formato = new DecimalFormat("00");
		fecha.setTime(new Date());
		
		forward.addActionOutput("anio", fecha.get(Calendar.YEAR));
		
		String mes = "";
		if(fecha.get(Calendar.DAY_OF_MONTH)<=9){
			mes = formato.format(fecha.get(Calendar.MONTH));
		}else{
			mes = formato.format((fecha.get(Calendar.MONTH) + 1));
		}
		
		if(mes.trim().equals("00"))mes="12";
		forward.addActionOutput("mes", mes);
		
		return forward;
	}
	
	/**
	 * Metodo para refrescar mi cuenta y mostrar el resumn
	 * 
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 * @throws ServiceException 
	 * @throws RemoteException 
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "resumen.jsp") })
	public Forward refrescarResumen() throws RemoteException, ServiceException {
		setCuentaEnSesion();
		
		Forward forward = new Forward("success");
		Calendar fecha = Calendar.getInstance();
		DecimalFormat formato = new DecimalFormat("00");
		fecha.setTime(new Date());
		
		forward.addActionOutput("anio", fecha.get(Calendar.YEAR));
		
		String mes = "";
		if(fecha.get(Calendar.DAY_OF_MONTH)<=9){
			mes = formato.format(fecha.get(Calendar.MONTH));
		}else{
			mes = formato.format((fecha.get(Calendar.MONTH) + 1));
		}
		
		if(mes.trim().equals("00"))mes="12";
		forward.addActionOutput("mes", mes);
		
		return forward;
	}
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "error", path = "sinfactura.jsp") })
	public Forward imprimeFactura() {
		return getFactura("application/pdf");
	}


	@Jpf.Action(forwards = { @Jpf.Forward(name = "error", path = "sinfactura.jsp") })
	public Forward descargaFactura() {
		return getFactura("application/force-download");
	}
	
	private Forward getFactura(String contentType) {
		Forward forward = new Forward("error");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try{
			VaultService service = new VaultService_Impl();
			VaultServicePortType servicePortType = service.getVaultServiceHttpSoap12Endpoint();

			RespuestaToMyAccount account = (RespuestaToMyAccount)getSession().getAttribute(Constantes.SESSION_MI_CUENTA);
			
			Calendar calendar = Calendar.getInstance();
			int month = calendar.get(Calendar.MONTH) + 1;
			int year = calendar.get(Calendar.YEAR);
			
			String statementDate = year+"/"+month;
			String accountNumber = account.getCvNumberAccount();

			String dbName = "Invoices_db";

			Attachment attachment = servicePortType.getDocument(statementDate, accountNumber, dbName);
			String fileNameValue = attachment.getFileName();
			String contentTypeValue = attachment.getContentType();
			byte[] binaryDataValue = attachment.getBinaryData();

			if(StringUtils.isEmpty(fileNameValue)){
				return forward;
			}

			baos.write(binaryDataValue);

			this.getResponse().setHeader("Cache-Control", "max-age=30");
			this.getResponse().setContentType(contentType);

			StringBuffer sbContentDispValue = new StringBuffer();
			sbContentDispValue.append("inline");
			sbContentDispValue.append("; filename=");
			sbContentDispValue.append(fileNameValue + ".pdf");

			this.getResponse().setHeader("Content-disposition",sbContentDispValue.toString());
			this.getResponse().setContentLength(baos.size());

			ServletOutputStream sos;
			sos = this.getResponse().getOutputStream();
			baos.writeTo(sos);
			sos.flush();
		} catch (IOException e) {
			forward.addActionOutput("errores", e.getMessage());
			return forward;
		}catch(Exception e){
			return forward;
		}finally {
			if (baos != null) {
				baos.reset();
			}
		}
		return null;

	}
	
	/**
	 * Va a vitria por la cuenta del usuario y la pone en sesion
	 * @throws RemoteException
	 * @throws ServiceException
	 */
	private void setCuentaEnSesion() throws RemoteException, ServiceException{
		RespuestaToMyAccount response = getVitriaClient().getProjects_CVNPW_Initial_ToInterfase()
			.toMyAccount((String)getSession().getAttribute(Constantes.SESSION_ACCOUNT_ID));
		getSession().setAttribute(Constantes.SESSION_MI_CUENTA, response);
	}
	
	/**
	 * Metodo que muestra forma para modificar cuenta
	 * 
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "modificar.jsp") })
	public Forward muestraModificar() {
		RespuestaToMyAccount cuenta = (RespuestaToMyAccount)getSession().getAttribute(Constantes.SESSION_MI_CUENTA);
		
		
		RegistroBean form = new RegistroBean();
		form.setNombre(cuenta.getNombreContacto());
		form.setEmail(cuenta.getCorreoContacto());
		form.setEmailConfirmacion(cuenta.getCorreoContacto());
		form.setApellidoPaterno(cuenta.getApellidoPaterno());
		form.setApellidoMaterno(cuenta.getApellidoMaterno());
		
		Forward forward = new Forward("success",form);
		return forward;
	}
	
	/**
	 * Metodo que muestra forma para modificar cuenta
	 * 
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "modificarPassword.jsp") })
	public Forward muestraModificarPassword() {
		Forward forward = new Forward("success");
		
		if("true".equals(getSession().getAttribute(Constantes.CONTRASENA_EXPIRADA))){
			forward.addActionOutput("msg", "Para mantener la seguridad de la informaci&oacute;n de tu cuenta, tu contrase\u00F1a debe ser actualizada cada 90 d&iacute;as."+
					"Por favor ingresa una nueva, recuerda que debe ser diferente a las tres  utilizadas  anteriormente.");
		}
		
		return forward;
	}
	
	/**
	 * Metodo modifica los datos de la cuenta de un usuario
	 * 
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 * @throws ServiceException 
	 * @throws RemoteException 
	 * @throws ErrorVitriaException 
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", action = "muestraModificar")},  
			validationErrorForward = @Jpf.Forward(name="fail", navigateTo=Jpf.NavigateTo.currentPage),
			validatableProperties = { 
				@Jpf.ValidatableProperty(displayName="nombre", propertyName = "nombre", 
						validateRequired = @Jpf.ValidateRequired(messageKey = "error.requerido.nombre"),
						validateMask=@Jpf.ValidateMask(regex="^[A-Za-z\u00D1\u00F1 ]*$", messageKey="error.formato.nombre")), 
				@Jpf.ValidatableProperty(displayName="apellido paterno", propertyName="apellidoPaterno", 
						validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.apellido"),
						validateMask=@Jpf.ValidateMask(regex="^[A-Za-z\u00D1\u00F1 ]*$", messageKey="error.formato.apellidoPaterno")),
				@Jpf.ValidatableProperty(displayName="apellido materno", propertyName = "apellidoMaterno", 
						validateMask=@Jpf.ValidateMask(regex="^[A-Za-z\u00D1\u00F1 ]*$", messageKey="error.formato.apellidoMaterno")), 
				@Jpf.ValidatableProperty(propertyName="email", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.email"), 
						validateEmail=@Jpf.ValidateEmail(messageKey="error.formato.email"),
						validateValidWhen=@Jpf.ValidateValidWhen(messageKey="error.confirmacion.email", condition="${actionForm.email == actionForm.emailConfirmacion}")) 
			}
	)
	public Forward guardarCuenta(RegistroBean form) throws RemoteException, ServiceException, ErrorVitriaException {
		String accountId = (String)getSession().getAttribute(Constantes.SESSION_ACCOUNT_ID);
		
		String newFileName = "";
		FormFile foto = form.getFoto();
		CustomEntry ce;
		if(foto != null && StringUtils.isNotBlank(foto.getFileName())) {
			ce = saveFile(foto, accountId);
			if(!ce.getKey().equals("success")) {
				Forward forward = new Forward("fail");
				forward.addActionOutput("errors", ce.getValue());
				return forward;
			} else {
				newFileName = ce.getValue();
				getUsuarioPortalService().updateCvUsuarioPortal(accountId.toUpperCase(), newFileName);
			}
		}
		RespuestaToUpdateAccount respuesta = getVitriaClient().getProjects_CVNPW_Initial_ToInterfase().toUpdateAccount(
				accountId, form.getNombre().toUpperCase(Locale.ENGLISH), 
				form.getApellidoPaterno().toUpperCase(Locale.ENGLISH),form.getApellidoMaterno().toUpperCase(Locale.ENGLISH), form.getEmail(), null);
		
		ValidateErrors.validateErrorResponse(respuesta.getCvErrorCode(), getMessageResources("mensajeError"));

		setCuentaEnSesion();
		if(StringUtils.isNotBlank(newFileName)){
			getSession().setAttribute("fotoUsuario", newFileName);
		}
		
		Forward forward = new Forward("success");
		forward.addActionOutput("exito", getMessageResources("micuentaBundle").getMessage("modificar.datos.exito"));
		return forward;
	}
	
	private CustomEntry saveFile(FormFile formFile, String id) {
		// mostramos los parametros del fichero
		try {
			String contentType = formFile.getContentType();
			Integer maxImageSize = new Integer(getMessageResources("configuracion").getMessage("miCuenta.foto.maxfilesize"));
			Long maxSize = maxImageSize.longValue();
			String path = getMessageResources("configuracion").getMessage("virtual.cablevision.archivosPerfil");
			path += "fotos/";
			if(!StringUtils.contains(contentType, "image")) {
				return new CustomEntry("error", "El archivo debe ser de tipo de imagen valido");
			}
			int fileSize = formFile.getFileSize();
			if(fileSize > maxSize.longValue() * 1024) {
				return new CustomEntry("error", "El archivo de imagen debe tener un tamaño maximo de "+ maxImageSize +"KB");
			}
			File f = new File(path);
			f.mkdirs();
			f.createNewFile();

			String extension = formFile.getFileName();
			int dotPos = extension.lastIndexOf(".");
			extension = extension.substring(dotPos);
			String newName = id + "-Source" + extension;
			String sourceImagePath = path + newName;
			newName = id + extension;
			String resultImagePath = path + newName;
			//guarda los datos del fichero
			OutputStream os = new FileOutputStream(sourceImagePath);
			InputStream is = new BufferedInputStream(formFile.getInputStream());

			int count;  
			byte buf[] = new byte[4096];  

			while ((count = is.read(buf)) > -1) {  
				os.write(buf, 0, count);    
			}  

			is.close();   
			os.close();
			
			ImageUtil.scale(sourceImagePath, resultImagePath, 48, 49);
			return new CustomEntry("success", newName);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return new CustomEntry("error", "Ha ocurrido un error!");
	}
	
	/**
	 * Metodo que modifica el password de un usuario
	 * 
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 * @throws Exception 
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", action = "muestraModificarPassword")},  
			validationErrorForward = @Jpf.Forward(name="fail", navigateTo=Jpf.NavigateTo.currentPage),
			validatableProperties = { 
				@Jpf.ValidatableProperty(propertyName = "password", 
						validateRequired = @Jpf.ValidateRequired(messageKey = "error.password")),
				@Jpf.ValidatableProperty(propertyName = "nuevoPassword", 
						validateRequired = @Jpf.ValidateRequired(messageKey = "error.nuevoPassword"),
						validateMinLength = @Jpf.ValidateMinLength(messageKey="error.size.nuevoPass", chars=8),
						validateValidWhen = @Jpf.ValidateValidWhen(messageKey = "error.confirmacion.password", condition = "${actionForm.nuevoPassword == actionForm.passwordConfirmacion}"))
			}
	)
	public Forward modificarPassword(RegistroBean form) throws Exception {
		RespuestaToMyAccount cuenta = (RespuestaToMyAccount)getSession().getAttribute(Constantes.SESSION_MI_CUENTA);

		String passwordActual = Blowfish.desEncriptar((byte[])getSession().getAttribute(Constantes.SESSION_MI_PASSWD),Constantes.ENCRIPT_PASSWD);

		if(!form.getPassword().equals(passwordActual)){
			addActionError("password", "error.password.actual", null);
			return new Forward("fail");
		}else if(!ValidarPasswordUtil.validatePassword(form.getNuevoPassword())){
			Forward forward = new Forward("fail");
			forward.addActionOutput("errores", "La contrase&ntilde;a debe de contener de 8 a 25 caracteres " +
			"incluyendo may&uacute;sculas, min&uacute;sculas, n&uacute;meros y caracteres especiales como @#$%!&/()=?");
			return forward;
		}
		
		List<CvContrasenaHistorial> listHistorial = getUsuarioPortalService().findCvContrasenaHistorialByIdUser(
				((String)getSession().getAttribute(Constantes.SESSION_ACCOUNT_ID)).toUpperCase());
		
		if(listHistorial != null && listHistorial.size()>0){
			int sizeList = listHistorial.size();
			if(sizeList >= 3){
				for(int i=0; i<3; i++){
					if(listHistorial.get(i).getCchContrasena().equals(PasswordUtil.getEncodedPassword(form.getNuevoPassword()))){
						Forward forward = new Forward("fail");
						forward.addActionOutput("errores", "La contrase\u00F1aa que ingresaste ya hab&iacute;a sido registrada con anterioridad, por favor ingresa una nueva");
						forward.addActionOutput("msg", "");
						return forward;
					}
				}
			}else{
				for(CvContrasenaHistorial cvContrasena : listHistorial){
					if(cvContrasena.getCchContrasena().equals(PasswordUtil.getEncodedPassword(form.getNuevoPassword()))){
						Forward forward = new Forward("fail");
						forward.addActionOutput("errores", "La contrase\u00F1aa que ingresaste ya hab&iacute;a sido registrada con anterioridad, por favor ingresa una nueva");
						forward.addActionOutput("msg", "");
						return forward;
					}
				}
			}
		}
		
		RespuestaToUpdateAccount respuesta = getVitriaClient().getProjects_CVNPW_Initial_ToInterfase().toUpdateAccount(
				(String)getSession().getAttribute(Constantes.SESSION_ACCOUNT_ID), cuenta.getNombreContacto(), 
				cuenta.getApellidoPaterno(),cuenta.getApellidoMaterno(), 
				cuenta.getCorreoContacto(), form.getNuevoPassword());

		ValidateErrors.validateErrorResponse(respuesta.getCvErrorCode(), getMessageResources("mensajeError"));
		
		//lleno el pojo para guardarlo en el historial
		CvContrasenaHistorial cvContrasenaHistorial = new CvContrasenaHistorial();
		CvUsuarioPortal cvUsuarioPortal = getUsuarioPortalService().findCvUsuarioPortalById(((String)getSession().getAttribute(Constantes.SESSION_ACCOUNT_ID)).toUpperCase());
		Date date = new Date();
		Timestamp time = new Timestamp(date.getTime());
		
		cvUsuarioPortal.setCupFechaUltimaContrasena(time);//al cambiar la contraseña la fecha de la ultima contraseña sera igual a la fecha actual
		cvContrasenaHistorial.setCvUsuarioPortal(cvUsuarioPortal);
		cvContrasenaHistorial.setCchContrasena(PasswordUtil.getEncodedPassword(form.getNuevoPassword()));
		
		//guardo la nueva contraseña en el historial
		getUsuarioPortalService().persistCvContrasenaHistorial(cvContrasenaHistorial);
		//guardo la fecha de la ultima contraseña igual a la fecha actual
		getUsuarioPortalService().persistCvUsuarioPortal(cvUsuarioPortal);
		
		
		getRequest().getSession().setAttribute(Constantes.SESSION_MI_PASSWD, Blowfish.encriptar(form.getNuevoPassword(), Constantes.ENCRIPT_PASSWD));
		setCuentaEnSesion();
		Forward forward = new Forward("success");
		
		
		if("true".equals(getSession().getAttribute(Constantes.CONTRASENA_EXPIRADA))){
			forward.addActionOutput("exito", getMessageResources("micuentaBundle").getMessage("modificar.passwordExpirado.exito"));
		}else{
			forward.addActionOutput("exito", getMessageResources("micuentaBundle").getMessage("modificar.password.exito"));
		}
		
		//una vez cambiada la contraseña remuevo de session la var de que expiro la contraseña
		getSession().removeAttribute(Constantes.CONTRASENA_EXPIRADA);
		
		
		LogFilter.log(log, form.getIdUsuario(), Constantes.LOG_ACCION_ENTRADA, 0, Constantes.LOG_ESTADO_CAMBIO_CONTRASENIA);
		form.setPassword(null);
		form.setPasswordConfirmacion(null);
		form.setNuevoPassword(null);

		return forward;
	}

	
	/**
	 * Metodo que muestra forma de portabilidad
	 * 
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "portabilidad.jsp") })
	public Forward muestraPortabilidad() {
		Forward forward = new Forward("success");
		
		return forward;
	}
	
	
	/**
	 * Metodo que muestra el estatus de portabilidad del telefono proporcionado
	 * 
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "estatusPortabilidad.jsp") })
	public Forward obtenerEstatusPortabilidad()throws RemoteException, ServiceException{
		Forward forward = new Forward("success");
		String telefono = getRequest().getParameter("telefono");
		
		if(telefono != null && telefono.trim() != ""){
			WS_Impl wsImpl = new WS_Impl();
			WSPortType portType = wsImpl.getWSHttpPort();
			Respuesta estatus = portType.consultaPortabilidad(telefono);
			
			//cadena que formara el key del mensaje del properties para que contenga el codigo de error
			StringBuilder msg = new StringBuilder();
			msg.append("portabilidad.estatus.");
			msg.append(estatus.getCodigo());
			
			if(estatus.getCodigo() == 1005){
				String [] argsMsg = {estatus.getTelefono(), estatus.getFechaPortacion()};
				forward.addActionOutput("msg", getMessageResources("portabilidadBundle").getMessage(msg.toString(), argsMsg));
			}
			else{
				forward.addActionOutput("msg", getMessageResources("portabilidadBundle").getMessage(msg.toString()));
			}
		}
		else{
			forward.addActionOutput("error", "Proporcione un número de teléfono");
		}
		
		return forward;
	}
	
	public ToInterfase getVitriaClient() {
		if(vitriaClient==null){
			ApplicationContext context = (ApplicationContext)getRequest().getSession().getServletContext().getAttribute(
					WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			vitriaClient = (ToInterfase)context.getBean("VitriaClient");
			
		}
		return vitriaClient;
	}
	
	private IUsuarioPortalService getUsuarioPortalService() {
		if (usuarioPortalService == null) {
			ApplicationContext context = (ApplicationContext) getRequest().getSession().getServletContext().getAttribute(
					WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			usuarioPortalService = (IUsuarioPortalService) context.getBean("UsuarioPortalService");
		}
		return usuarioPortalService;
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