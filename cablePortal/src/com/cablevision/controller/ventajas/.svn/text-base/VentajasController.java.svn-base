package com.cablevision.controller.ventajas;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DateFormatSymbols;
import java.util.Calendar;

import javax.servlet.http.HttpSession;

import org.apache.beehive.netui.pageflow.Forward;
import org.apache.beehive.netui.pageflow.PageFlowController;
import org.apache.beehive.netui.pageflow.annotations.Jpf;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.upload.FormFile;

import com.cablevision.util.CustomEntry;

//Substitute with this annotation if nested pageflow
//@Jpf.Controller( nested=true )

@Jpf.Controller(
		multipartHandler = Jpf.MultipartHandler.memory,
		messageBundles = { @Jpf.MessageBundle(bundlePath="configuracion", bundleName="configuracion")}
)
public class VentajasController extends PageFlowController {
	private static final long serialVersionUID = 1L;

	@Jpf.Action(forwards = { @Jpf.Forward(name = "index", path = "ventajas.jsp") })
	protected Forward begin() {
		return new Forward("index");
	}

	@Jpf.Action(
			forwards = { 
					@Jpf.Forward(name = "success", path = "ventajas.jsp"),
					@Jpf.Forward(name = "errors", path = "ventajas.jsp") },
					validationErrorForward = @Jpf.Forward(name="fail", navigateTo=Jpf.NavigateTo.currentPage), 
					validatableProperties = { @Jpf.ValidatableProperty(propertyName = "antiguedad", 
													validateRequired = @Jpf.ValidateRequired(message = "El campo Antig\u00FCedad es requerido.<br/>"), 
													validateMask = @Jpf.ValidateMask(message = "Favor de llenar la Antig\u00FCedad sin acentos ni caracteres especiales.<br/>", regex = "^[A-Za-z0-9\\u00D1\\u00F1 ]*$")), 
											@Jpf.ValidatableProperty(propertyName="nombreCompleto", 
													validateRequired=@Jpf.ValidateRequired(message="El campo Nombre es requerido.<br/>"), 
													validateMask=@Jpf.ValidateMask(message="Favor de llenar el Nombre y Apellido sin acentos ni caracteres especiales.<br/>", regex="^[A-Za-z0-9\\u00D1\\u00F1 ]*$")), 
											@Jpf.ValidatableProperty(propertyName="producto", validateRequired=@Jpf.ValidateRequired(message="El campo Producto es requerido.<br/>"), 
													validateMask=@Jpf.ValidateMask(message="Favor de llenar el producto sin acentos ni caracteres especiales.<br/>", regex="^[A-Za-z0-9\\u00D1\\u00F1 ]*$")), 
											@Jpf.ValidatableProperty(propertyName="testimonio", validateRequired=@Jpf.ValidateRequired(message="El campo Testimonio es requerido.<br/>")) }
	)
	public Forward saveComment(VentajasForm form) throws IOException {
		Calendar calendar = Calendar.getInstance();
		String[] months = new DateFormatSymbols().getShortMonths();
		int month = calendar.get(Calendar.MONTH);
		int year = calendar.get(Calendar.YEAR);
		String folderName = year + "-" + months[month];
		String csvFileName = folderName + ".csv";
		String fileName4Files = Long.toString(calendar.getTimeInMillis());
		String path = getMessageResources("configuracion").getMessage("virtual.cablevision.testimonios");

		// Guardar los archivos del usuario
		String fotoFileName = "";
		String videoFileName = "";
		FormFile foto = form.getFoto();
		FormFile video = form.getVideo();
		if(StringUtils.isNotBlank(foto.getFileName())) {
			CustomEntry ce = saveMultipartFile(foto, folderName, fileName4Files);
			if(ce.getKey().equals("success")) {
				fotoFileName = ce.getValue();
			} else {
				Forward forward = new Forward("errors");
				forward.addActionOutput("errors", ce.getValue());
				return forward;
			}
		}
		if(StringUtils.isNotBlank(video.getFileName())) {
			CustomEntry ce = saveMultipartFile(video, folderName, fileName4Files);
			if(ce.getKey().equals("success")) {
				videoFileName = ce.getValue();
			} else {
				Forward forward = new Forward("errors");
				forward.addActionOutput("errors", ce.getValue());
				return forward;
			}
		}

		// Escribe el nuevo registro
		File file = new File(path + csvFileName);
		Writer writer = null;
		if(!file.exists()) {
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file,true);
			writer = new OutputStreamWriter(fos, "UTF8");
			writer.append("Nombre,Antigüedad,Producto,Foto,Video,Testimonio");
		} else {
			FileOutputStream fos = new FileOutputStream(file,true);
			writer = new OutputStreamWriter(fos, "UTF8");
		}
		
		writer.append("\n");
		String val = new String(form.getNombreCompleto().getBytes(), "UTF8");
		writer.append(val);
		writer.append(",");
		val = new String(form.getAntiguedad().getBytes(), "UTF8");
		writer.append(val);
		writer.append(",");
		val = new String(form.getProducto().getBytes(), "UTF8");
		writer.append(val);
		writer.append(",");
		val = new String(fotoFileName.getBytes(), "UTF8");
		writer.append(val);
		writer.append(",");
		val = new String(videoFileName.getBytes(), "UTF8");
		writer.append(val);
		writer.append(",");
		val = new String(form.getTestimonio().getBytes(), "UTF8");
		writer.append(val);


		writer.flush();
		writer.close();		

		Forward forward = new Forward("success");
		forward.addActionOutput("success", "success");
		return forward;
	}

	/**
	 * @param formFile El archivo a guardar
	 * @param pathToAppend si se desea agregar alguna carpeta extra al path predefinido
	 * @param nameToAppend el nombre que se desea poner al archivo  sin su extension
	 * @return Un par key-value con success/nombre del archivo con la extension, si se guardo correctamente.
	 * O error/mensaje de error, si no se pasaron las validaciones u ocurrio algun error
	 */
	private CustomEntry saveMultipartFile(FormFile formFile, String pathToAppend, String nameToAppend) {
		// mostramos los parametros del fichero
		try {
			String contentType = formFile.getContentType();
			Long maxSize = 0L;
			Integer maxImageSize = new Integer(getMessageResources("configuracion").getMessage("ventajas.image.maxfilesize"));
			Integer maxVideoSize = new Integer(getMessageResources("configuracion").getMessage("ventajas.video.maxfilesize"));
			String path = getMessageResources("configuracion").getMessage("virtual.cablevision.testimonios") + pathToAppend  + "/";
			if(StringUtils.contains(contentType, "image")) {
				maxSize = maxImageSize.longValue();
			} else if(StringUtils.contains(contentType, "video")) {
				maxSize = maxVideoSize.longValue();
			} else {
				return new CustomEntry("error", "Los archivos deben ser de tipo imagen o de video");
			}
			int fileSize = formFile.getFileSize();
			if(fileSize > maxSize.longValue() * 1024 * 1024) {
				return new CustomEntry("error", "El archivo de imagen debe tener un tamaño maximo de "+ maxImageSize +" MB y el de video de " + maxVideoSize + " MB");
			}
			File f = new File(path);
			f.mkdirs();
			f.createNewFile();

			String extension = formFile.getFileName();
			int dotPos = extension.lastIndexOf(".");
			extension = extension.substring(dotPos);
			String newName = nameToAppend + extension;
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
			System.out.println("Guardado el archivo: " + path + newName);
			return new CustomEntry("success", newName);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return new CustomEntry("error", "Ha ocurrido un error!");
	}

	@Jpf.FormBean
	public static class VentajasForm implements java.io.Serializable {
		private static final long serialVersionUID = -2604669445027496620L;
		private String nombreCompleto;
		private String antiguedad;
		private String producto;
		private String testimonio;
		private transient FormFile foto;
		private transient FormFile video;

		public String getNombreCompleto() {
			return nombreCompleto;
		}
		public void setNombreCompleto(String nombreCompleto) {
			this.nombreCompleto = nombreCompleto;
		}
		public String getAntiguedad() {
			return antiguedad;
		}
		public void setAntiguedad(String antiguedad) {
			this.antiguedad = antiguedad;
		}
		public String getProducto() {
			return producto;
		}
		public void setProducto(String producto) {
			this.producto = producto;
		}
		public String getTestimonio() {
			return testimonio;
		}
		public void setTestimonio(String testimonio) {
			this.testimonio = testimonio;
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
	}

	/**
	 * Callback that is invoked when this controller instance is created.
	 */
	protected void onCreate() {
	}

	/**
	 * Callback that is invoked when this controller instance is destroyed.
	 */
	protected void onDestroy(HttpSession session) {
	}
}