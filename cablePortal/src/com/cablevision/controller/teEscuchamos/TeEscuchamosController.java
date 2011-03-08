package com.cablevision.controller.teEscuchamos;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.beehive.netui.pageflow.Forward;
import org.apache.beehive.netui.pageflow.PageFlowController;
import org.apache.beehive.netui.pageflow.annotations.Jpf;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.upload.FormFile;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.cablevision.service.ILeadService;
import com.cablevision.util.CustomEntry;
import com.cablevision.util.ReCaptchaUtil;
import com.cablevision.vo.CvLead;
import com.cablevision.vo.CvLeadStatus;
import com.cablevision.vo.CvLeadType;

//Substitute with this annotation if nested pageflow
//@Jpf.Controller( nested=true )

@Jpf.Controller(
		multipartHandler = Jpf.MultipartHandler.memory,
		messageBundles = { @Jpf.MessageBundle(bundlePath="configuracion", bundleName="configuracion")}
)
public class TeEscuchamosController extends PageFlowController {
	private static final long serialVersionUID = 1L;
	private transient ILeadService leadService;

	@Jpf.Action(forwards = { @Jpf.Forward(name = "index", path = "index.jsp") })
	protected Forward begin() {
		return new Forward("index");
	}

	@SuppressWarnings("unchecked")
	@Jpf.Action(forwards = { 
			@Jpf.Forward(name = "success", path = "success.jsp"),
			@Jpf.Forward(name = "errors", path = "index.jsp")
	})
	public Forward saveLead(TeEscuchamosForm form) throws NumberFormatException, Exception {
		Forward forward = new Forward("errors"); 

		if(form != null && form.getAsunto()!= null){
			CvLead cvLead = new CvLead();
			CvLeadType leadType = getLeadService().findLeadTypeById(new Integer(form.getAsunto()));
			CvLeadStatus status = getLeadService().findLeadStatusById(0);
			cvLead.setLeadType(leadType);
			cvLead.setCleDatecreation(new Timestamp(new Date().getTime()));
			cvLead.setCleDatelead(new Timestamp(new Date().getTime()));
			cvLead.setLeadStatus(status);
			cvLead.setCleValid(Boolean.TRUE);
		}
		
		Map<String,String> map = form.getFields();
		
		if(map!= null && map.size()>0){
			Map<String,String> errors = getLeadService().getValidatedFields(map);
			Map<String,String> errors2 = form.getFields();

			//Valida que escribas correcto lo del captcha
			if(!ReCaptchaUtil.isValido(getRequest())){
				forward = new Forward("errors");
				getRequest().setAttribute("errors", "El Texto no coincide con la imagen");
			} else {
				for(Iterator<Entry<String, String>> it = map.entrySet().iterator(); it.hasNext();) {
					Entry<String, String> e = (Entry<String, String>)it.next();
					if(errors.containsKey(e.getKey()))
						errors2.put(e.getKey(), errors.get(e.getKey()));
					else
						errors2.remove(e.getKey());
				}

				forward = new Forward("success");
				if(errors2.size()==0){
					String id = (String)getLeadService().saveLead(map);
					FormFile foto = form.getFoto();
					FormFile video = form.getVideo();
					if(foto != null){
						InputStream fotoIS = foto.getInputStream();
						
						CustomEntry ce;
						if(StringUtils.isNotBlank(foto.getFileName())) {
							ce = saveFile(foto, id);
							if(!ce.getKey().equals("success")) {
								forward = new Forward("errors");
								forward.addActionOutput("errors", ce.getValue());
							}
						}
						
						if(video != null ){
							InputStream videoIS = video.getInputStream();
							if(StringUtils.isNotBlank(video.getFileName())) {
								ce = saveFile(video, id);
								if(!ce.getKey().equals("success")) {
									forward = new Forward("errors");
									forward.addActionOutput("errors", ce.getValue());
								}
							}
						}
					}
				}else{
					forward = new Forward("errors");
					getRequest().setAttribute("errors", errors2.values());
				}
			}
		}
		
		return forward;
	}

	private CustomEntry saveFile(FormFile formFile, String id) {
		// mostramos los parametros del fichero
		try {
			String contentType = formFile.getContentType();
			String path = "";
			Long maxSize = 0L;
			Integer maxImageSize = new Integer(getMessageResources("configuracion").getMessage("teEscuchamos.image.maxfilesize"));
			Integer maxVideoSize = new Integer(getMessageResources("configuracion").getMessage("teEscuchamos.video.maxfilesize"));
			if(StringUtils.contains(contentType, "image")) {
				path = getMessageResources("configuracion").getMessage("virtual.cablevision.teEscuchamos")+"fotos/";
				maxSize = maxImageSize.longValue();
			} else if(StringUtils.contains(contentType, "video")) {
				path = getMessageResources("configuracion").getMessage("virtual.cablevision.teEscuchamos")+"videos/";
				maxSize = maxVideoSize.longValue();
			} else {
				return new CustomEntry("error", "Los archivos deben ser de tipo imagen o de video");
			}
			int fileSize = formFile.getFileSize();
			if(fileSize > maxSize.longValue() * 1024 * 1024) {
				return new CustomEntry("error", "El archivo de imagen debe tener un tama\u00F1o maximo de "+ maxImageSize +" MB y el de video de " + maxVideoSize + " MB");
			}
			File f = new File(path);
			f.mkdirs();
			f.createNewFile();

			String extension = formFile.getFileName();
			int dotPos = extension.lastIndexOf(".");
			extension = extension.substring(dotPos);
			String newName = id + extension;
			//guarda los datos del fichero
			OutputStream os = new FileOutputStream(path + newName);
			InputStream is = new BufferedInputStream(formFile.getInputStream());

			int count;  
			byte buf[] = new byte[4096];  

			while ((count = is.read(buf)) > -1) {  
				os.write(buf, 0, count);    
			}  

			is.close();   
			os.close();
			return new CustomEntry("success", "");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return new CustomEntry("error", "Ha ocurrido un error!");
	}

	@Jpf.FormBean
	public static class TeEscuchamosForm implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		private String asunto;
		private String nombre;
		private String apellidos;
		private String email;
		private String comentario;
		private String challenge;
		private String uresponse;
		
		private String esCliente;
		private String numContrato;
		private String telefono;
		private String domicilio;
		private String colonia;
		private String cp;
		private String ciudad;
		private String estado;
		
		private transient FormFile foto;
		private transient FormFile video;

		public TeEscuchamosForm(){}

		public String getAsunto() {
			return asunto;
		}
		public void setAsunto(String asunto) {
			this.asunto = asunto;
		}

		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getApellidos() {
			return apellidos;
		}
		public void setApellidos(String apellidos) {
			this.apellidos = apellidos;
		}

		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getComentario() {
			return comentario;
		}
		public void setComentario(String comentario) {
			this.comentario = comentario;
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

		public FormFile getFoto() {
			return foto;
		}
		public void setFoto(FormFile foto) {
			this.foto = foto;
		}

		public FormFile getVideo() {
			return video;
		}
		public void setVideo(FormFile video) {
			this.video = video;
		}

		public String getEsCliente() {
			return esCliente;
		}

		public void setEsCliente(String esCliente) {
			this.esCliente = esCliente;
		}

		public String getNumContrato() {
			return numContrato;
		}

		public void setNumContrato(String numContrato) {
			this.numContrato = numContrato;
		}

		public String getTelefono() {
			return telefono;
		}

		public void setTelefono(String telefono) {
			this.telefono = telefono;
		}

		public String getDomicilio() {
			return domicilio;
		}

		public void setDomicilio(String domicilio) {
			this.domicilio = domicilio;
		}

		public String getColonia() {
			return colonia;
		}

		public void setColonia(String colonia) {
			this.colonia = colonia;
		}

		public String getCp() {
			return cp;
		}

		public void setCp(String cp) {
			this.cp = cp;
		}

		public String getCiudad() {
			return ciudad;
		}

		public void setCiudad(String ciudad) {
			this.ciudad = ciudad;
		}

		public String getEstado() {
			return estado;
		}

		public void setEstado(String estado) {
			this.estado = estado;
		}

		@SuppressWarnings("unchecked")
		public Map<String,String> getFields(){
			Map<String,String> map = new LinkedHashMap<String,String>();
			map.put("Nombre",this.nombre!=null?this.nombre.toUpperCase(Locale.ENGLISH):this.nombre);
			map.put("Apellido",this.apellidos!=null?this.apellidos.toUpperCase(Locale.ENGLISH):this.apellidos);
			map.put("Cliente",this.esCliente);
			map.put("No_contrato",this.numContrato);
			map.put("Email",this.email);
			map.put("Tel_cable",this.telefono);
			map.put("Domicilio",this.domicilio);
			map.put("Colonia",this.colonia);
			map.put("Ciudad",this.ciudad);
			map.put("CP",this.cp);
			map.put("Estado",this.estado);
			map.put("Comentarios",this.comentario!=null?this.comentario.toUpperCase(Locale.ENGLISH):this.comentario);
			map.put("leadType", this.asunto);
			map.put("idStatus","0");
			return map;
		}

	}

	public ILeadService getLeadService() {
		if(leadService==null){
			ApplicationContext context = (ApplicationContext)getRequest().getSession().getServletContext().getAttribute(
					WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			leadService = (ILeadService)context.getBean("LeadService");

		}
		return leadService;
	}

	public void setLeadService(ILeadService leadService) {
		this.leadService = leadService;
	}
}