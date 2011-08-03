package com.cablevision.controller.ordenadministrativa;

import java.io.File;
import java.io.FileOutputStream;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpSession;
import javax.xml.rpc.ServiceException;

import org.apache.beehive.netui.pageflow.Forward;
import org.apache.beehive.netui.pageflow.annotations.Jpf;
import org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils;
import org.apache.beehive.netui.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.upload.FormFile;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.bea.portlet.GenericURL;
import com.cablevision.ToInterfase;
import com.cablevision.controller.base.ControllerBase;
import com.cablevision.portal.ErrorVitriaException;
import com.cablevision.service.ICommentOaService;
import com.cablevision.util.Constantes;
import com.cablevision.util.ResponseToQueryDetailServiceRequest;
import com.cablevision.util.ResponseToServiceRequest;
import com.cablevision.util.RespuestaToMyAccount;
import com.cablevision.util.UploadCombos;
import com.cablevision.util.ValidateErrors;
import com.cablevision.vo.Archivo;
import com.cablevision.vo.CvCommentoa;
import com.cablevision.vo.CvTrackoa;

/**
 * Page Flow de las ordenes administrativas
 * 
 * @author Luis Perez - JWMSolutions 24/09/2009
 *
 */
@Jpf.Controller(simpleActions = { @Jpf.SimpleAction(name = "begin", action = "mostrarBienvenida")},
				multipartHandler=Jpf.MultipartHandler.memory, 
				messageBundles = { @Jpf.MessageBundle(bundlePath = "com.cablevision.controller.ordenadministrativa.ordenAdministrativa", bundleName="ordenAdminBundle"), 
								   @Jpf.MessageBundle(bundlePath = "configuracion", bundleName="configuracion"),
								   @Jpf.MessageBundle(bundlePath = "mensajeError", bundleName="mensajeError")},
				loginRequired=true )
@SuppressWarnings("unchecked")
public class OrdenAdministrativaController extends ControllerBase {
	private static final Logger _log = Logger.getInstance( OrdenAdministrativaController.class );
	private static final long serialVersionUID = 1L;
	private static final String TIPO_PROBLEMA_PAGOS = "Problemas de Facturacion";
	private static final String TIPO_PROBLEMA_TARJETA = "Modificar";
	private static final String TIPO_PROBLEMA_QUEJAS = "Quejas";
	private static final String SUBTIPO_PROBLEMA_PAGOS = "Pagos";
	private static final String SUBTIPO_PROBLEMA_ERROR_FACTURA = "Facturacion Erronea";
	private static final String SUBTIPO_PROBLEMA_TARJETA = "Datos Pago";
	private static final String MOTIVO_ALTA_TARJETA = "Alta Tarjeta de Credito";
	private static final String MOTIVO_CAMBIO_TARJETA = "Modificacion Tarjeta Credito";
	
	private static SimpleDateFormat formatDDMMYYY = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat formatMMDDYYY = new SimpleDateFormat("MM/dd/yyyy");
	
	private transient ICommentOaService commentOaService;
	private DetalleSolicitudForm detalleSolicitud;
	transient ToInterfase vitriaClient;
	
	/**
	 * Metodo que muestra la pagina de bienvenida 
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 * @throws ServiceException 
	 * @throws RemoteException 
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "index.jsp") })
	public Forward mostrarBienvenida() throws RemoteException, ServiceException {
		RespuestaToMyAccount cuenta = (RespuestaToMyAccount)getSession().getAttribute(Constantes.SESSION_MI_CUENTA);
		if(cuenta==null){
			//mandar a iniciar sesion
		}
		Forward forward = new Forward("success");
		return forward;
	}
	
	/**
	 * Metodo que muestra la forma de pago no aplicado
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "pagoNoAplicado.jsp") })
	public Forward mostrarPagoNoAplicado() {
		Forward forward = new Forward("success");
		try{
			// el 7 es el id para pagos en la BD
			Map mapCombo = UploadCombos.getCombo(getRequest(), 7l, "2");
			forward.addActionOutput("combo", mapCombo);
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}
	
	/**
	 * Metodo que graba una queja de pago no aplicado
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", action = "mostrarDetalle") },
			validationErrorForward = @Jpf.Forward(name="fail", action= "mostrarPagoNoAplicado"), 
			validatableProperties = { @Jpf.ValidatableProperty(displayName="descripcion", propertyName="descripcionProblema", 
					                  	validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.descripcion"), 
					                  	validateMaxLength=@Jpf.ValidateMaxLength(messageKey="error.longitud.descipcionProblema", chars=250),
					                  	validateMask=@Jpf.ValidateMask(regex="^[A-Za-z0-9\u00D1\u00F1 ]*$", messageKey="error.formato.descipcionProblema")), 
					                  @Jpf.ValidatableProperty(propertyName="tipoProblema", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.tipoProblema")) })
	public Forward guardarPagoNoAplicado(PagoNoAplicadoBean form) throws Exception {
		Forward forward = new Forward("success");
		DetalleSolicitudForm formDetalle = new DetalleSolicitudForm();
		RespuestaToMyAccount cuenta = (RespuestaToMyAccount)getSession().getAttribute(Constantes.SESSION_MI_CUENTA);
		FormFile archivoComprobante = form.getArchivoDigital();
		String noSolicitud = "";
		
		if(form.getTipoProblema().equals("...")){
			forward = new Forward("fail");
			forward.addActionOutput("msg", "Favor de Elegir el tipo del Problema que reporta.");
			return forward;
		}
		
		if(form.getComprobante()!= null && !form.getComprobante().equals("")){
			if(form.getComprobante().equals("archivo")){
				if(archivoComprobante==null || archivoComprobante.getFileName().equals("")){
					forward = new Forward("fail");
					forward.addActionOutput("errores", "El nombre de archivo (Comprobante) es requerido.");
					return forward;
				}else if(archivoComprobante.getFileSize()==0){
					forward = new Forward("fail");
					forward.addActionOutput("errores", "El archivo (Comprobante) no existe.");
					return forward;
				}else if(archivoComprobante.getFileSize()>1048576){
					forward = new Forward("fail");
					forward.addActionOutput("errores", "El archivo (Comprobante) excede el l\u00EDmite permitido.");
					return forward;
				}
				formDetalle.setBackward("opcion_archivo");
			}else{
				formDetalle.setBackward("opcion_fax");
			}
		}else{
			formDetalle.setBackward("pagoNoAplicado");
		}
		
		//guardar Datos
		CvTrackoa cvTrackoa = new CvTrackoa();
		cvTrackoa.setToaDate(new Timestamp(new Date().getTime()));
		cvTrackoa.setToaType(TIPO_PROBLEMA_PAGOS);
		cvTrackoa.setToaSubtype(SUBTIPO_PROBLEMA_PAGOS);
		cvTrackoa.setToaMotive(form.getTipoProblema());
		cvTrackoa.setToaDescripcion(form.getDescripcionProblema());
		cvTrackoa.setToaAccountid(cuenta.getCvNumberAccount());
		
		//guarda el archivo digital en disco
		List<FormFile> archivos = null;
		if(archivoComprobante!= null && form.getComprobante()!=null && form.getComprobante().equals("archivo")){
			archivos = new ArrayList<FormFile>();
			archivos.add(archivoComprobante);
		}
		noSolicitud = this.guardarDatos(cvTrackoa, form, null, null, archivos);
		
		formDetalle.setNoSolicitud(noSolicitud);
		if(StringUtils.isNotEmpty(noSolicitud)){
			forward.addActionOutput("successMsgSolicitud", getMessageResources("ordenAdminBundle").getMessage("success.solicitudservicio"));
		}
		forward.addOutputForm(formDetalle);
		return forward;
	}
	
	/**
	 * Metodo que muestra la forma de error en factura
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "errorFactura.jsp") })
	public Forward mostrarErrorFactura() {
		Forward forward = new Forward("success");
		try{
			// el 6 es el id para facturacion erronea en la BD
			Map mapCombo = UploadCombos.getCombo(getRequest(), 6l, "2");
			forward.addActionOutput("combo", mapCombo);
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}
	
	/**
	 * Metodo que graba una queja de error en factura
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", action = "mostrarDetalle") }, 
				validationErrorForward = @Jpf.Forward(name="fail", action= "mostrarErrorFactura"),
				validatableProperties = { @Jpf.ValidatableProperty(displayName="descripcion problema", propertyName = "descripcion", 
												validateRequired = @Jpf.ValidateRequired(messageKey = "error.requerido.descripcion"),
												validateMask=@Jpf.ValidateMask(regex="^[A-Za-z0-9\u00D1\u00F1 ]*$", messageKey="error.formato.descipcionProblema")), 
										  @Jpf.ValidatableProperty(propertyName="tipoProblema", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.tipoProblema")) })
	public Forward guardarErrorFactura(QuejaBean form) throws Exception {
		Forward forward = new Forward("success");
		String noSolicitud = "";
		
		if(form.getTipoProblema().equals("...")){
			forward = new Forward("fail");
			forward.addActionOutput("msg", "Favor de Elegir el tipo del Problema que reporta.");
			return forward;
		}
			
		CvTrackoa cvTrackoa = new CvTrackoa();
		cvTrackoa.setToaDate(new Timestamp(new Date().getTime()));
		cvTrackoa.setToaType(TIPO_PROBLEMA_PAGOS);
		cvTrackoa.setToaSubtype(SUBTIPO_PROBLEMA_ERROR_FACTURA);
		cvTrackoa.setToaMotive(form.getTipoProblema());
		cvTrackoa.setToaDescripcion(form.getDescripcion().toUpperCase(Locale.ENGLISH));
		
		noSolicitud = this.guardarDatos(cvTrackoa, null, form, null, null);
		
		DetalleSolicitudForm formDetalle = new DetalleSolicitudForm();
		formDetalle.setNoSolicitud(noSolicitud);
		formDetalle.setBackward("errorFactura");
		forward.addOutputForm(formDetalle);
		
		if(StringUtils.isNotEmpty(noSolicitud)){
			forward.addActionOutput("successMsgSolicitud", getMessageResources("ordenAdminBundle").getMessage("success.solicitudservicio"));
		}
		
		return forward;
	}
	
	/**
	 * Metodo que muestra la forma de levantar queja
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "levantarQueja.jsp") })
	public Forward mostrarLevantarQueja() {
		Forward forward = new Forward("success");
		try{
			// el 1 es el id para quejas en la BD
			Map mapCombo = UploadCombos.getCombo(getRequest(), 1l, "1");
			forward.addActionOutput("combo", mapCombo);
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}
	
	/**
	 * Metodo que carga el subcomnbo de quejas
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "") })
	public Forward cargarSubCombo() {
		Forward forward = new Forward("success");
		try{
			Map mapCombo = UploadCombos.getCombo(getRequest(), 1l, "1");
			forward.addActionOutput("subcombo", mapCombo);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Metodo que graba una queja
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", action = "mostrarDetalle") },
				validationErrorForward = @Jpf.Forward(name="fail", action= "mostrarLevantarQueja"),
				validatableProperties = { 
					@Jpf.ValidatableProperty(propertyName = "area", validateRequired = @Jpf.ValidateRequired(messageKey = "errors.requerido.area")), 
					@Jpf.ValidatableProperty(displayName="descripcion problema", propertyName="descripcion", 
							validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.descripcion"),
							validateMaxLength=@Jpf.ValidateMaxLength(messageKey="error.maxlength.descripcion",chars=200),
							validateMask=@Jpf.ValidateMask(regex="^[A-Za-z0-9\u00D1\u00F1 ]*$", messageKey="error.formato.descipcionProblema")), 
					@Jpf.ValidatableProperty(propertyName="motivo", validateRequired=@Jpf.ValidateRequired(messageKey="errors.rquerido.motivo")), 
					@Jpf.ValidatableProperty(displayName="fecha hora", propertyName="fechaHora", 
							validateMask=@Jpf.ValidateMask(regex="^[A-Za-z0-9\u00D1\u00F1 ]*$", messageKey="error.formato.fechaHora")), 
					@Jpf.ValidatableProperty(displayName="comentario", propertyName="comentario", 
							//validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.comentario"),
							validateMaxLength=@Jpf.ValidateMaxLength(messageKey="error.maxlength.comentario",chars=100),
							validateMask=@Jpf.ValidateMask(regex="^[A-Za-z0-9\u00D1\u00F1 ]*$", messageKey="error.formato.comentario")), 
					@Jpf.ValidatableProperty(displayName="quien atendio", propertyName="quienAtendio", 
							validateMask=@Jpf.ValidateMask(regex="^[A-Za-z\u00D1\u00F1 ]*$", messageKey="error.formato.quienAtendio")) })
	public Forward guardarLevantarQueja(QuejaBean form) throws Exception {
		Forward forward = new Forward("success");
		String noSolicitud = "";
		
		if(form.getArea().equals("...")){
			forward = new Forward("fail");
			forward.addActionOutput("msg", "Favor de Elegir el Área Relacionada con su Inconformidad.");
			return forward;
		}
		
		if(form.getMotivo().equals("...")){
			forward = new Forward("fail");
			forward.addActionOutput("msg", "Favor de Elegir el Motivo de su Inconformidad.");
			return forward;
		}
		
		CvTrackoa cvTrackoa = new CvTrackoa();
		cvTrackoa.setToaDate(new Timestamp(new Date().getTime()));
		cvTrackoa.setToaType(TIPO_PROBLEMA_QUEJAS);
		cvTrackoa.setToaSubtype(form.getArea());
		cvTrackoa.setToaMotive(form.getMotivo());
		cvTrackoa.setToaDescripcion(form.getDescripcion().toUpperCase(Locale.ENGLISH));
		
		//update 06 abril 2010
		if(StringUtils.isNotEmpty(form.getComentario())){
			CvCommentoa comments =  new CvCommentoa();
			comments.setCoaComment(form.getComentario());
			cvTrackoa.setCvCommentoa(comments);
		}
		
		noSolicitud = this.guardarDatos(cvTrackoa, null, form, null, null);
		
		DetalleSolicitudForm formDetalle = new DetalleSolicitudForm();
		formDetalle.setNoSolicitud(noSolicitud);
		formDetalle.setBackward("queja");
		forward.addOutputForm(formDetalle);
		
		if(StringUtils.isNotEmpty(noSolicitud)){
			forward.addActionOutput("successMsgSolicitud", getMessageResources("ordenAdminBundle").getMessage("success.solicitudservicio"));
		}
		
		return forward;
	}
	
	/**
	 * Metodo que muestra la forma de pago recurrente
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "pagoRecurrente.jsp") })
	public Forward mostrarPagoRecurrente() {
		Forward forward = new Forward("success");
		try{
			if(getRequest().getParameter("tipoPago")!= null && getRequest().getParameter("tipoPago").equals("alta")){
				forward.addActionOutput("tipoPago", "alta");
			}
			Map comboBancos = UploadCombos.getComboBancos(getRequest(), "bancos");
			Map comboTarjetas = UploadCombos.getComboTarjetas(getRequest(), "tarjetas");
			
			forward.addActionOutput("comboBancos", comboBancos);
			forward.addActionOutput("comboTarjetas", comboTarjetas);
		}catch(Exception e){
			e.printStackTrace();
		}
		return forward;
	}
	
	/**
	 * Metodo que graba una queja
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", 
				action = "mostrarDetalle") },
				validationErrorForward = @Jpf.Forward(name="fail", action= "mostrarPagoRecurrente"), 
				validatableProperties = { @Jpf.ValidatableProperty(propertyName = "anioVencimiento", validateRequired = @Jpf.ValidateRequired(messageKey = "error.requerido.anioVencimiento")), 
										@Jpf.ValidatableProperty(propertyName="banco", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.banco")), 
										@Jpf.ValidatableProperty(propertyName="mesVencimiento", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.mesVencimiento")), 
										@Jpf.ValidatableProperty(propertyName="noTarjeta", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.noTarjeta"), 
												validateMaxLength=@Jpf.ValidateMaxLength(messageKey="error.numerico.noTarjeta", chars=16), 
												validateType=@Jpf.ValidateType(messageKey="error.numerico.noTarjeta", type=long.class)), 
										@Jpf.ValidatableProperty(propertyName="tipoTarjeta", validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.tarjeta")), 
										@Jpf.ValidatableProperty(displayName="titular tarjeta", propertyName="titularTarjeta", 
												validateRequired=@Jpf.ValidateRequired(messageKey="error.requerido.titularTarjeta"),
												validateMask=@Jpf.ValidateMask(regex="^[A-Za-z\u00D1\u00F1 ]*$", messageKey="error.formato.titularTarjeta")) })
	public Forward guardarPagoRecurrente(PagoRecurrenteBean form) throws Exception{
		Forward forward = new Forward("success");
		String noSolicitud = "";
		DetalleSolicitudForm formDetalle = new DetalleSolicitudForm();
		List<String> errorsList = new ArrayList<String>();
		
		if(form.getTipoTarjeta().equals("...")){
			forward = new Forward("fail");
			forward.addActionOutput("msg", "Favor de Elegir el Tipo de Tarjeta.");
			return forward;
		}else{
			if(form.getBanco().equals("...")){
				forward = new Forward("fail");
				forward.addActionOutput("msg", "Favor de Elegir el Banco.");
				return forward;
			}else{
				if(form.getMesVencimiento().equals("Mes")){
					forward = new Forward("fail");
					forward.addActionOutput("msg", "Favor de Elegir el Mes de la Fecha de Vencimiento.");
					return forward;
				}else{
					if(form.getAnioVencimiento().equals("Año")){
						forward = new Forward("fail");
						forward.addActionOutput("msg", "Favor de Elegir el Año de la Fecha de Vencimiento.");
						return forward;
					}
				}
			}
		}
		
		FormFile identificacion =  form.getIdentificacion();
		FormFile cartaAutorizacion = form.getCartaAutorizacion();
		
		if(form.getTipoArchivo()!= null && !form.getTipoArchivo().equals("")){
			if(form.getTipoArchivo().equals("archivo")){
				if(identificacion==null || identificacion.getFileName().equals("")){
					errorsList.add("El nombre de archivo (Identificaci\u00F3n) es requerido.");
				}else if(identificacion.getFileSize()==0){
					errorsList.add("El archivo (Identificaci\u00F3n) no existe.");
				}else if(identificacion.getFileSize()>1048576){
					errorsList.add("El archivo (Identificaci\u00F3n) excede el l\u00EDmite permitido.");
				}
				
				if(cartaAutorizacion==null || cartaAutorizacion.getFileName().equals("")){
					errorsList.add("El nombre de archivo (Carta Autorizaci\u00F3n) es requerido.");
				}else if(cartaAutorizacion.getFileSize()==0){
					errorsList.add("El archivo (Carta Autorizaci\u00F3n) no existe.");
				}else if(cartaAutorizacion.getFileSize()>1048576){
					errorsList.add("El archivo (Carta Autorizaci\u00F3n) excede el l\u00EDmite permitido.");
				}
				
				if(!errorsList.isEmpty()){
					forward = new Forward("fail");
					forward.addActionOutput("errores", errorsList);
					return forward;
				}
				formDetalle.setBackward("opcion_archivo");
			}else{
				formDetalle.setBackward("opcion_fax");
			}
		}else{
			formDetalle.setBackward("pagoRecurrente");
		}
		
		//guardar en Datos
		CvTrackoa cvTrackoa = new CvTrackoa();
		cvTrackoa.setToaDate(new Timestamp(new Date().getTime()));
		
		if(getRequest().getParameter("tipoPago")!= null && getRequest().getParameter("tipoPago").equals("alta")){
			cvTrackoa.setToaType(TIPO_PROBLEMA_TARJETA);
			cvTrackoa.setToaSubtype(SUBTIPO_PROBLEMA_TARJETA);
			cvTrackoa.setToaMotive(MOTIVO_ALTA_TARJETA);
			cvTrackoa.setToaDescripcion(MOTIVO_ALTA_TARJETA);
			form.setTipoPago("alta");
		}else{
			cvTrackoa.setToaType(TIPO_PROBLEMA_TARJETA);
			cvTrackoa.setToaSubtype(SUBTIPO_PROBLEMA_TARJETA);
			cvTrackoa.setToaMotive(MOTIVO_CAMBIO_TARJETA);
			cvTrackoa.setToaDescripcion(MOTIVO_CAMBIO_TARJETA);
			form.setTipoPago("cambio");
		}
		
		//guardando el archivo
		List<FormFile> archivos = null;
		if(identificacion!=null && cartaAutorizacion!= null && form.getTipoArchivo() !=null && form.getTipoArchivo().equals("archivo")){
			archivos = new ArrayList<FormFile>();
			archivos.add(identificacion);
			archivos.add(cartaAutorizacion);
		}
		
		noSolicitud = this.guardarDatos(cvTrackoa, null, null, form, archivos);
		
		formDetalle.setNoSolicitud(noSolicitud);
		forward.addOutputForm(formDetalle);
		if(StringUtils.isNotEmpty(noSolicitud)){
			forward.addActionOutput("successMsgSolicitud", getMessageResources("ordenAdminBundle").getMessage("success.solicitudservicio"));
		}
		
		return forward;
	}
	
	/**
	* guarda un archivo digital en disco 
	* @param FormFile --> El archivo que viene del form y se quiere guardar en disco
	* @return String --> nombre nuevo del archivo ya almacenado en el disco
	*/
	private String guardarArchivoDigital(FormFile archivoTmp, String idCarpeta) throws Exception{
		StringTokenizer nombre = new StringTokenizer(archivoTmp.getFileName(),".");
		String linkVitria ="";
		if(nombre.countTokens()==2){
			
			if(idCarpeta != null && !"".equals(idCarpeta)){
				//Controlamos las condiciones para subirlo
				try {
					//nombre nuevo del archivo
					String fileNewName = "";
					//ubicaci\u00F3n y nombre del archivo a guardar
					String path = getMessageResources("configuracion").getMessage("ruta.guardar.archivos")+idCarpeta+"/";

					// se comprueba que la ruta exista
					File f = new File(path);
					if (makeSureDirectoryExists(f)) {
						String[] archivos = f.list();
						//nombre nuevo del archivo
						fileNewName = (archivos.length+1)+"_"+nombre.nextElement()+ "."+nombre.nextElement(); //El nombre con el que queremos guardar el archivo
						
						// Se graba en la ruta el archivo;
						path = getMessageResources("configuracion").getMessage("ruta.guardar.archivos")+idCarpeta+"/"+fileNewName;
						f = new File(path);
						FileOutputStream out = new FileOutputStream(f);
						out.write(archivoTmp.getFileData());
						out.flush();
						out.close();
						
						//se guarda el link para vitiria
						GenericURL urltmp = GenericURL.createGenericURL(getRequest(), getResponse());
						urltmp.setTemplate("urlApp");
						urltmp.setContextualPath("/com/cablevision/controller/archivos/begin.do");
						urltmp.addParameter("id", idCarpeta);
						linkVitria = urltmp.toString();
					} 
				} catch (Exception ex) {
					throw new Exception("Lanzada excepcion en guardarArchivoDigital:"+ex.getMessage());
				}
				//Destruimos el archivo temporal
				archivoTmp.destroy();
			}else{
				throw new Exception("El archivo no pertenece a ninguna solicitud");
			}
		}
		return linkVitria;
	}
	
	/**
	* obtiene los archivos almacenados
	* @param noSolicitud --> El no de solicitud equivale al nombre del a carpeta a buscar
	* @return List --> lista de archivos dentro de una carpeta
	*/
	private List<Archivo> getArchivos(String noSolicitud) throws Exception{
		List<Archivo> archivosList = new ArrayList<Archivo>();
		if(noSolicitud != null && !"".equals(noSolicitud)){
			//Controlamos las condiciones para subirlo
			try {
				//obtenemos el registro de la BD correspondiente a ese noSolicitud
				CvTrackoa track = this.getCommentOaService().findCvTrackoaByNumberOa(noSolicitud);
				
				//ubicaci\u00F3n y nombre del directorio
				String path = getMessageResources("configuracion").getMessage("ruta.guardar.archivos")+track.getToaId().toString()+"/";

				// se comprueba que la ruta exista
				File f = new File(path);
				if (f.exists()) {
					File[] archivos = f.listFiles();
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
					
					for(int i =0; i < archivos.length; i++){
						Date fecha = new Date(archivos[i].lastModified());
						archivosList.add(new Archivo(archivos[i].getName(), String.valueOf(archivos[i].length()), sdf.format(fecha)));
					}
				} 
			} catch (Exception ex) {
				throw new Exception("Lanzada excepcion en getArchivos: "+ex.getMessage());
			}
		}
		return archivosList;
	}
	
	/**
	* Devuelve la ruta padre del subdirectorio actual
	* @param File --> El archivo del cual se quiere sacar su directorio o directorio padre
	* @return File --> Crea un archivo con la ruta del directorio padre
	*/
	private File parent(File f) {
		String dirname = f.getParent();
		
		if (dirname == null ) {
			return new File(File.separator);
		}
		return new File(dirname);
	}
		
	/**
	* Crear un subdirectorio si este no existe
	* @param dir --> El path del archivo (direcci\u00F3n + nombre)
	* @return True -> Existe o se ha creado False --> No existe y no se ha podido crear
	*/
	private boolean makeSureDirectoryExists(File dir) {
		if (!dir.exists()) {
			if (makeSureDirectoryExists(parent(dir)))
				dir.mkdir();
			else
				return false ;
		}
		return true ;
	}
	
	/**
	 * Metodo que muestra la forma de agregar un comentario
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "comentarios.jsp")} , useFormBean = "detalleSolicitud" )
	public Forward mostrarAgregaComentario(DetalleSolicitudForm form) {
		Forward forward = new Forward("success");
		if(form!=null){
			forward.addOutputForm(form);
		}
		return forward;
	}
	
	/**
	 * Metodo que graba un comentario
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", action = "mostrarDetalle")},
				useFormBean = "detalleSolicitud",
				validationErrorForward = @Jpf.Forward(name="fail", navigateTo=Jpf.NavigateTo.currentPage), validatableProperties = { @Jpf.ValidatableProperty(propertyName = "comentario", validateRequired = @Jpf.ValidateRequired(messageKey = "error.requerido.comentario"), validateMask = @Jpf.ValidateMask(messageKey = "error.formato.comentario", regex = "^[A-Za-z0-9\\u00D1\\u00F1 ]*$")) })
	public Forward guardarComentario(DetalleSolicitudForm form) {
		Forward forward = new Forward("success");
		this.getDetalleSolicitud().setBackward("");
		
		try{
			//guardar en DB
			CvTrackoa track = this.getCommentOaService().findCvTrackoaByNumberOa(form.getNoSolicitud());
			CvCommentoa cvCommentoa = new CvCommentoa();
			cvCommentoa.setCoaComment(form.getComentario().toUpperCase(Locale.ENGLISH));
			cvCommentoa.setCoaToaId(track.getToaId());
			cvCommentoa.setCoaDate(new Timestamp(new Date().getTime()));
			cvCommentoa.setCvTrackoa(track);
			this.getCommentOaService().persistCvCommentoa(cvCommentoa);
		}catch(Exception e){
			e.printStackTrace();
			forward = new Forward("fail");
			forward.addActionOutput("errores", "Error intente m\u00E1s tarde");
			return forward;
		}
		forward.addActionOutput("successMsgComentario", getMessageResources("ordenAdminBundle").getMessage("success.agregarcomentario"));
		return forward;
	}
	
	/**
	 * Metodo que muestra la forma para agregar un archivo
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "archivos.jsp") }, useFormBean = "detalleSolicitud")
	public Forward mostrarAgregaArchivo(DetalleSolicitudForm form){
		Forward forward = new Forward("success");
		if(form!=null){
			forward.addOutputForm(form);
		}
		return forward;
	}
	
	/**
	 * Metodo que graba un archivo
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", action = "mostrarDetalle") }, useFormBean = "detalleSolicitud",
				validationErrorForward = @Jpf.Forward(name="fail", navigateTo=Jpf.NavigateTo.currentPage),
				validatableProperties = { @Jpf.ValidatableProperty(propertyName = "archivo", validateRequired = @Jpf.ValidateRequired(messageKey = "error.requerido.archivoAdjunto"))})
	public Forward guardarArchivo(DetalleSolicitudForm form) {
		Forward forward = new Forward("success");
		this.getDetalleSolicitud().setBackward("");
		FormFile file = form.getArchivo();
		
		if(file.getFileSize()==0){
			forward = new Forward("fail");
			forward.addActionOutput("errores", "El archivo no existe.");
			return forward;
		}else if(file.getFileSize()>1048576){
			forward = new Forward("fail");
			forward.addActionOutput("errores", "El archivo excede el l\u00EDmite permitido. (1MB)");
			return forward;
		}
		
		try{
			//guarda el archivo digital en disco
			//obtenemos el registro de la BD correspondiente a ese noSolicitud
			CvTrackoa track = this.getCommentOaService().findCvTrackoaByNumberOa(form.getNoSolicitud());
			
			String linkVitria = guardarArchivoDigital(file, track.getToaId().toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		forward.addActionOutput("successMsgArchivo", getMessageResources("ordenAdminBundle").getMessage("success.agregararchivo"));
		return forward;
	}
	
	/**
	 * Metodo que muestra una lista de solicitudes del cliente
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "listaSolicitudes.jsp")},
				validationErrorForward = @Jpf.Forward(name="fail", path= "listaSolicitudes.jsp"))
	public Forward muestraLista(PagoRecurrenteBean form) throws Exception {
		RespuestaToMyAccount cuenta = (RespuestaToMyAccount)getSession().getAttribute(Constantes.SESSION_MI_CUENTA);
		Forward forward = new Forward("success");
		List<CvTrackoa> listaTracks = getCommentOaService().findCvTrackoaByAccountId(cuenta.getCvNumberAccount());
		if(listaTracks!= null && !listaTracks.isEmpty()){
			forward.addActionOutput("listaSolicitudes", listaTracks);
			
			int solporpag = Integer.parseInt(getMessageResources("configuracion").getMessage("atencionclientes.solicitudesporpagina"));
			int numSolicitudes = listaTracks.size();
			forward.addActionOutput("numSolicitudes", numSolicitudes);
			forward.addActionOutput("solporpag", numSolicitudes<solporpag?numSolicitudes:solporpag);
			
			
			double fracPages = (double)numSolicitudes/(double)solporpag;
			long decPart = Math.round((fracPages-Math.floor(fracPages))*100);
			long numPages =  (long)fracPages;
			numPages = decPart>0?numPages+1:numPages;
			forward.addActionOutput("numPaginas", numPages);
		}
		
		return forward;
	}
	
	/**
	 * Metodo que muestra el detalle de una solicitud, comentarios y archivos
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "detalleSolicitud.jsp")}, 
				useFormBean = "detalleSolicitud")
	public Forward mostrarDetalle(DetalleSolicitudForm form) throws Exception { 
		Forward forward = new Forward("success");
		RespuestaToMyAccount cuenta = (RespuestaToMyAccount)getSession().getAttribute(Constantes.SESSION_MI_CUENTA);
		String noSolicitud="";
		
		if(ScopedServletUtils.getOuterServletRequest(getRequest()).getParameter("noSolicitud")== null){
			noSolicitud = form.getNoSolicitud();
		}else{
			noSolicitud = ScopedServletUtils.getOuterServletRequest(getRequest()).getParameter("noSolicitud");
		}
		
		//obtener los detalles de la solicitud de vitria y de la BD
		try{
			_log.info(cuenta.getCvNumberAccount()+" - "+noSolicitud);
			ResponseToQueryDetailServiceRequest  res = getVitriaClient().getProjects_CVNPW_Initial_ToInterfase().toQueryDetailServiceRequest(cuenta.getCvNumberAccount(),noSolicitud); // para llenar el res
			ValidateErrors.validateErrorResponse(res.getCvError(), getMessageResources("mensajeError"));
			_log.info("getCvAccountName: "+res.getCvAccountName());
			_log.info("getCvCreationDate: "+res.getCvCreationDate());
			_log.info("getCvDescription: "+res.getCvDescription());
			_log.info("getCvProblemType: "+res.getCvProblemType());
			_log.info("getCvRequestNumber: "+res.getCvRequestNumber());
			_log.info("getCvStatus: "+res.getCvStatus());
			_log.info("getCvSubProblemType: "+res.getCvSubProblemType());
			_log.info("getCvSubReasonType: "+res.getCvSubReasonType());
			
			CvTrackoa track = this.getCommentOaService().findCvTrackoaByNumberOa(noSolicitud);
			DetalleSolicitudForm formDetalle = new DetalleSolicitudForm();
			formDetalle.setContrato(cuenta.getCvNumberAccount());
			formDetalle.setNoSolicitud(noSolicitud);
			formDetalle.setBackward(form.getBackward());
			formDetalle.setNombreCuenta(cuenta.getCvNameAccount());
			
			Date creado = formatMMDDYYY.parse(res.getCvCreationDate().trim());
			formDetalle.setCreado(formatDDMMYYY.format(creado));
			formDetalle.setDescripcion(track.getToaDescripcion());
			
			String estatus = getMessageResources("ordenAdminBundle").getMessage("estatus."+res.getCvStatus());
			
			formDetalle.setEstado(StringUtils.isNotEmpty(estatus)?estatus:res.getCvStatus());
			formDetalle.setTipoProblema(track.getToaType());
			formDetalle.setSubtipoProblema(track.getToaSubtype());
			formDetalle.setMotivoProblema(track.getToaMotive());
			
			this.setDetalleSolicitud(formDetalle);
			forward.addOutputForm(formDetalle);
			
			//obtiene los comentarios de la base de datos
			forward.addActionOutput("comentariosList", this.getCommentOaService().findCvCommentoaByNoSolicitud(noSolicitud));
			
			//obtener lista de archivos adjuntos 
			forward.addActionOutput("archivosList", getArchivos(noSolicitud));	
			
			//si el status es Cancelado o Finalizado bandera para no agregar archivos ni comentarios
			if(res.getCvStatus().equals("Finalizada") || res.getCvStatus().equals("Cancelada")){
				forward.addActionOutput("finalizada", true);
			}
		}catch(Exception e){
			_log.error("Error al obtener el detalle de una OA - ",e);
			forward.addOutputForm(new DetalleSolicitudForm());
			forward.addActionOutput("errores", "No existe el registro en el sistema.");
		}
		
		if(form.getBackward()!= null && form.getBackward().equals("opcion_fax")){
			forward.addActionOutput("fax", true);
		}
		
		return forward;
	}
	

	/**
	 * Metodo que muestra la pagina portada de fax a imprimir
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "portadaFax.jsp") }, useFormBean = "detalleSolicitud")
	public Forward mostrarPortadaFax(DetalleSolicitudForm form) {
		Forward forward = new Forward("success");
		if(form!=null){
			forward.addOutputForm(form);
		}
		return forward;
	}
	
	/**
	 * Metodo que muestra la carta de autorizacion a imprimir
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "cartaAutorizacion.jsp") }, useFormBean = "detalleSolicitud")
	public Forward mostrarCartaAutorizacion(DetalleSolicitudForm form) {
		Forward forward = new Forward("success");
		
		SimpleDateFormat formato = new SimpleDateFormat("dd 'de' MMMMM 'de' yyyy", new Locale("es"));
		String fecha = "M\u00E9xico, D.F., a "+formato.format(new Date());
		if(form!=null){
			forward.addOutputForm(form);
			forward.addActionOutput("fechaHoy", fecha);
		}
		return forward;
	}
	
	/**
	 * Metodo que guarda en vitria y en base de datos la orden administrativa
	 * @return el no de solicitud
	 */
	public String guardarDatos(CvTrackoa cvTrackoa, PagoNoAplicadoBean formPagoNoApliacado, QuejaBean quejaForm, PagoRecurrenteBean pagoRecForm, List<FormFile> archivos) throws Exception{
		RespuestaToMyAccount cuenta = (RespuestaToMyAccount)getSession().getAttribute(Constantes.SESSION_MI_CUENTA);
		String linkArchivo = "";
		
		//guardar en DB para obtener el id para crear la carpeta que contendra archivos
		try{
			cvTrackoa.setToaAccountid(cuenta.getCvNumberAccount());
			getCommentOaService().persistCvTrackoa(cvTrackoa);
		}catch(Exception e){
			throw new Exception("Error al guardar los Datos: "+e.getMessage());
		}
		
		if(archivos != null){
			for(FormFile archivo : archivos){
				linkArchivo = this.guardarArchivoDigital(archivo, cvTrackoa.getToaId().toString());
			}
		}
		
		//guardardado en vitria dependiendo del tipo de orden adminitrativa y  el form recibido
		ResponseToServiceRequest response = new ResponseToServiceRequest();
		
		try{
			if(formPagoNoApliacado!=null){
				response = this.getVitriaClient().getProjects_CVNPW_Initial_ToInterfase().
							toServiceRequest(formPagoNoApliacado.getTipoProblema(), "PAGINA WEB", 
											cuenta.getCvNumberAccount(), formPagoNoApliacado.getTipoProblema(), 
											formPagoNoApliacado.getDescripcionProblema().toUpperCase(Locale.ENGLISH), 
											"", TIPO_PROBLEMA_PAGOS, "", SUBTIPO_PROBLEMA_PAGOS, "", "", "", "", "", "", 
											"", "", "", linkArchivo, "");
			}else if(pagoRecForm != null){
				if(pagoRecForm.getTipoPago()!= null && pagoRecForm.getTipoPago().equals("alta")){
					response = this.getVitriaClient().getProjects_CVNPW_Initial_ToInterfase().
					toServiceRequest(MOTIVO_ALTA_TARJETA, "PAGINA WEB", cuenta.getCvNumberAccount(), 
									TIPO_PROBLEMA_TARJETA, MOTIVO_ALTA_TARJETA, "", TIPO_PROBLEMA_TARJETA, "", 
									SUBTIPO_PROBLEMA_TARJETA, "", "", "", 
									pagoRecForm.getTipoTarjeta(), 
									pagoRecForm.getBanco(), 
									pagoRecForm.getNoTarjeta(), 
									pagoRecForm.getTitularTarjeta().toUpperCase(Locale.ENGLISH), 
									pagoRecForm.getMesVencimiento(), 
									pagoRecForm.getAnioVencimiento(), linkArchivo, "");
				}else{
					response = this.getVitriaClient().getProjects_CVNPW_Initial_ToInterfase().
					toServiceRequest(MOTIVO_CAMBIO_TARJETA, "PAGINA WEB", cuenta.getCvNumberAccount(), 
									TIPO_PROBLEMA_TARJETA, MOTIVO_CAMBIO_TARJETA, "", TIPO_PROBLEMA_TARJETA, "", 
									SUBTIPO_PROBLEMA_TARJETA, "", "", "", 
									pagoRecForm.getTipoTarjeta(), 
									pagoRecForm.getBanco(), 
									pagoRecForm.getNoTarjeta(), 
									pagoRecForm.getTitularTarjeta().toUpperCase(Locale.ENGLISH), 
									pagoRecForm.getMesVencimiento(), 
									pagoRecForm.getAnioVencimiento(), linkArchivo, "");
				}
			}else if(quejaForm != null){
				if(cvTrackoa.getToaType().equals(TIPO_PROBLEMA_QUEJAS)){
					String descripcion = (quejaForm.getDescripcion()+" atendio:"+quejaForm.getQuienAtendio()).toUpperCase(Locale.ENGLISH);
					response = this.getVitriaClient().getProjects_CVNPW_Initial_ToInterfase().
					toServiceRequest(quejaForm.getMotivo(), "PAGINA WEB", cuenta.getCvNumberAccount(), 
									cvTrackoa.getToaSubtype(),descripcion, "", TIPO_PROBLEMA_QUEJAS,
									"Siebel Administration", quejaForm.getArea(), 
									quejaForm.getFechaHora(), 
									"SADMIN", quejaForm.getComentario().toUpperCase(Locale.ENGLISH), "", "", 
									"", "", "", "", linkArchivo, "");
				}else{
					response = this.getVitriaClient().getProjects_CVNPW_Initial_ToInterfase().
					toServiceRequest(quejaForm.getTipoProblema(), "PAGINA WEB", cuenta.getCvNumberAccount(), 
									quejaForm.getTipoProblema(),
									quejaForm.getDescripcion().toUpperCase(Locale.ENGLISH), "", 
									TIPO_PROBLEMA_PAGOS, "", SUBTIPO_PROBLEMA_ERROR_FACTURA, "", "", "", 
									"", "", "","", "", "" , linkArchivo, "");
				}
			}
		}catch(Exception e){
			getCommentOaService().removeCvTrackoa(cvTrackoa);
			throw new ErrorVitriaException("Error inesperado, favor de intentar m\u00E1s tarde");
		}
		
		//se actualiza el track con el noSolicitud que regreso vitir o se borra el registro si no se inserto en vitria
		try{
			if(   (response.getCvError()==null || response.getCvError().getCvErrorCode()== null || 
				  "".equals(response.getCvError().getCvErrorCode())) 
			   && (response.getCvSRId()!= null && !response.getCvSRId().equals("")) ){
					cvTrackoa.setToaNumberOa(response.getCvSRId());
					getCommentOaService().persistCvTrackoa(cvTrackoa);
					
					//update 06 abril 2010
					if(cvTrackoa.getCvCommentoa()!=null && cvTrackoa.getCvCommentoa().getCoaComment()!=null){
						//guardar comentario en DB
						CvCommentoa cvCommentoa = new CvCommentoa();
						cvCommentoa.setCoaComment(cvTrackoa.getCvCommentoa().getCoaComment().toUpperCase(Locale.ENGLISH));
						cvCommentoa.setCoaToaId(cvTrackoa.getToaId());
						cvCommentoa.setCoaDate(new Timestamp(new Date().getTime()));
						cvCommentoa.setCvTrackoa(cvTrackoa);
						this.getCommentOaService().persistCvCommentoa(cvCommentoa);
					}
			}else{
				getCommentOaService().removeCvTrackoa(cvTrackoa);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new ErrorVitriaException("Error inesperado, favor de intentar m\u00E1s tarde");
		}
		
		ValidateErrors.validateErrorResponse(response.getCvError(), getMessageResources("mensajeError"));
		return response.getCvSRId();
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
	 * llamada a el servicio CommentOaService para acceder a la BD
	 */
	public ICommentOaService getCommentOaService() {
		if(commentOaService==null){
			ApplicationContext context = (ApplicationContext)getRequest().getSession().getServletContext().getAttribute(
					WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			commentOaService = (ICommentOaService)context.getBean("CommentOaService");
			
		}
		return commentOaService;
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
	 * Bean Class para usar en quejas
	 * 
	 * @author Luis Perez - JWMSolutions 24/09/2009
	 *
	 */
	@Jpf.FormBean
	public static class QuejaBean implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		
		private String noSolicitud;
		private String tipoProblema;
		private String descripcion;
		private transient FormFile comprobante;
		private String tipoComprobante;
		
		private String area;
		private String motivo;
		private String fechaHora;
		private String quienAtendio;
		private String comentario;
		
		public String getTipoProblema() {
			return tipoProblema;
		}
		public void setTipoProblema(String tipoProblema) {
			this.tipoProblema = tipoProblema;
		}
		public String getDescripcion() {
			return descripcion;
		}
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		
		public String getTipoComprobante() {
			return tipoComprobante;
		}
		public void setTipoComprobante(String tipoComprobante) {
			this.tipoComprobante = tipoComprobante;
		}
		public FormFile getComprobante() {
			return comprobante;
		}
		public void setComprobante(FormFile comprobante) {
			this.comprobante = comprobante;
		}
		public String getArea() {
			return area;
		}
		public void setArea(String area) {
			this.area = area;
		}
		public String getMotivo() {
			return motivo;
		}
		public void setMotivo(String motivo) {
			this.motivo = motivo;
		}
		public String getFechaHora() {
			return fechaHora;
		}
		public void setFechaHora(String fechaHora) {
			this.fechaHora = fechaHora;
		}
		public String getQuienAtendio() {
			return quienAtendio;
		}
		public void setQuienAtendio(String quienAtendio) {
			this.quienAtendio = quienAtendio;
		}
		public String getComentario() {
			return comentario;
		}
		public void setComentario(String comentario) {
			this.comentario = comentario;
		}
		public String getNoSolicitud() {
			return noSolicitud;
		}
		public void setNoSolicitud(String noSolicitud) {
			this.noSolicitud = noSolicitud;
		}
	
	}
	
	@Jpf.FormBean
	public static class PagoRecurrenteBean implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		
		private String tipoTarjeta;
		private String banco;
		private String noTarjeta;
		private String titularTarjeta;
		private String mesVencimiento;
		private String anioVencimiento;
		private String tipoArchivo;
		private String tipoPago;
		private transient FormFile cartaAutorizacion;
		private transient FormFile identificacion;
		
		public String getTipoTarjeta() {
			return tipoTarjeta;
		}
		public void setTipoTarjeta(String tipoTarjeta) {
			this.tipoTarjeta = tipoTarjeta;
		}
		public String getBanco() {
			return banco;
		}
		public void setBanco(String banco) {
			this.banco = banco;
		}
		public String getNoTarjeta() {
			return noTarjeta;
		}
		public void setNoTarjeta(String noTarjeta) {
			this.noTarjeta = noTarjeta;
		}
		public String getTitularTarjeta() {
			return titularTarjeta;
		}
		public void setTitularTarjeta(String titularTarjeta) {
			this.titularTarjeta = titularTarjeta;
		}
		public String getMesVencimiento() {
			return mesVencimiento;
		}
		public void setMesVencimiento(String mesVencimiento) {
			this.mesVencimiento = mesVencimiento;
		}
		public String getAnioVencimiento() {
			return anioVencimiento;
		}
		public void setAnioVencimiento(String anioVencimiento) {
			this.anioVencimiento = anioVencimiento;
		}
		public String getTipoArchivo() {
			return tipoArchivo;
		}
		public void setTipoArchivo(String tipoArchivo) {
			this.tipoArchivo = tipoArchivo;
		}
		public FormFile getCartaAutorizacion() {
			return cartaAutorizacion;
		}
		public void setCartaAutorizacion(FormFile cartaAutorizacion) {
			this.cartaAutorizacion = cartaAutorizacion;
		}
		public FormFile getIdentificacion() {
			return identificacion;
		}
		public void setIdentificacion(FormFile identificacion) {
			this.identificacion = identificacion;
		}
		public String getTipoPago() {
			return tipoPago;
		}
		public void setTipoPago(String tipoPago) {
			this.tipoPago = tipoPago;
		}
	}

	@Jpf.FormBean
	public static class DetalleSolicitudForm implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		private String contrato; // viene en la sesion
		private String nombreCuenta; //viene en la sesion
		private String tipoProblema;
		private String subtipoProblema;
		private String motivoProblema;
		private String descripcion;
		private String creado;
		private String noSolicitud;
		private String backward;
		private String comentario;
		private String tipoActividad; //
		private String estado; 
		private transient FormFile archivo;
		private String comentariosArchivo;
		
		
		public String getContrato() {
			return contrato;
		}
		public void setContrato(String contrato) {
			this.contrato = contrato;
		}
		public String getNombreCuenta() {
			return nombreCuenta;
		}
		public void setNombreCuenta(String nombreCuenta) {
			this.nombreCuenta = nombreCuenta;
		}
		public String getTipoProblema() {
			return tipoProblema;
		}
		public void setTipoProblema(String tipoProblema) {
			this.tipoProblema = tipoProblema;
		}
		public String getSubtipoProblema() {
			return subtipoProblema;
		}
		public void setSubtipoProblema(String subtipoProblema) {
			this.subtipoProblema = subtipoProblema;
		}
		public String getMotivoProblema() {
			return motivoProblema;
		}
		public void setMotivoProblema(String motivoProblema) {
			this.motivoProblema = motivoProblema;
		}
		public String getDescripcion() {
			return descripcion;
		}
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		public String getCreado() {
			return creado;
		}
		public void setCreado(String creado) {
			this.creado = creado;
		}
		public String getNoSolicitud() {
			return noSolicitud;
		}
		public void setNoSolicitud(String noSolicitud) {
			this.noSolicitud = noSolicitud;
		}
		public String getBackward() {
			return backward;
		}
		public void setBackward(String backward) {
			this.backward = backward;
		}
		public String getComentario() {
			return comentario;
		}
		public void setComentario(String comentario) {
			this.comentario = comentario;
		}
		public String getTipoActividad() {
			return tipoActividad;
		}
		public void setTipoActividad(String tipoActividad) {
			this.tipoActividad = tipoActividad;
		}
		public String getEstado() {
			return estado;
		}
		public void setEstado(String estado) {
			this.estado = estado;
		}
		public FormFile getArchivo() {
			return archivo;
		}
		public void setArchivo(FormFile archivo) {
			this.archivo = archivo;
		}
		public String getComentariosArchivo() {
			return comentariosArchivo;
		}
		public void setComentariosArchivo(String comentariosArchivo) {
			this.comentariosArchivo = comentariosArchivo;
		}
	}

	@Jpf.FormBean
	public static class PagoNoAplicadoBean implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		private String noSerie;
		private String tipoProblema;
		private String descripcionProblema;
		private transient FormFile archivoDigital;
		private String comprobante;
		
		public String getNoSerie() {
			return noSerie;
		}
		public void setNoSerie(String noSerie) {
			this.noSerie = noSerie;
		}
		public String getTipoProblema() {
			return tipoProblema;
		}
		public void setTipoProblema(String tipoProblema) {
			this.tipoProblema = tipoProblema;
		}
		public String getDescripcionProblema() {
			return descripcionProblema;
		}
		public void setDescripcionProblema(String descripcionProblema) {
			this.descripcionProblema = descripcionProblema;
		}
		public FormFile getArchivoDigital() {
			return archivoDigital;
		}
		public void setArchivoDigital(FormFile archivoDigital) {
			this.archivoDigital = archivoDigital;
		}
		public String getComprobante() {
			return comprobante;
		}
		public void setComprobante(String comprobante) {
			this.comprobante = comprobante;
		}
	}

	public DetalleSolicitudForm getDetalleSolicitud() {
		return detalleSolicitud;
	}

	public void setDetalleSolicitud(DetalleSolicitudForm detalleSolicitud) {
		this.detalleSolicitud = detalleSolicitud;
	}
}