package com.cablevision.util;

import org.apache.beehive.netui.util.logging.Logger;
import org.apache.struts.util.MessageResources;

import com.cablevision.portal.ErrorVitriaException;

/**
 * Clase para obtener el mensaje de error dependiendo del coidigo de error en vitria
 * @author Daniela G 
 *
 */
public class ValidateErrors{
	private static final long serialVersionUID = 1L;
	private static final Logger _log = Logger.getInstance( ValidateErrors.class );
	public static void validateErrorResponse(Error error, MessageResources bundle) throws ErrorVitriaException{
		
		if(containsErrors(error) && bundle != null){
			_log.error("Error en Vitria: "+error.getCvErrorMessage());
			String keyError = "error."+error.getCvErrorCode().replaceAll("\\n", "")+".mensaje";
			String mensajeError = bundle.getMessage(keyError);
			
			if(mensajeError== null){
				mensajeError = bundle.getMessage("error.default.mensaje");
			}
			
			throw new ErrorVitriaException(mensajeError);
		}
	}
	
	public static boolean containsErrors(Error error){
		if(error!=null && error.getCvErrorCode()!= null && !error.getCvErrorCode().equals("") 
				&& !error.getCvErrorCode().equals("0")){
			return true;
		}
		return false;
	}
}
