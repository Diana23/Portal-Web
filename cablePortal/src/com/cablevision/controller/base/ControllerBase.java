package com.cablevision.controller.base;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.beehive.netui.pageflow.Forward;
import org.apache.beehive.netui.pageflow.NotLoggedInException;
import org.apache.beehive.netui.pageflow.PageFlowController;
import org.apache.beehive.netui.pageflow.annotations.Jpf;
import org.apache.beehive.netui.pageflow.config.PageFlowActionMapping;
import org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils;
import org.apache.beehive.netui.util.logging.Logger;

import com.bea.portlet.GenericURL;
import com.cablevision.exception.ContrasenaExpiradaException;
import com.cablevision.portal.ErrorVitriaException;
import com.cablevision.util.Constantes;
import com.cablevision.util.PageNewUrl;
@Jpf.Controller(
		catches={
		        @Jpf.Catch(type=NotLoggedInException.class, method="handleLoginException"),
		        @Jpf.Catch(type=ErrorVitriaException.class, method="handleVitriaException"),
		        @Jpf.Catch(type=RuntimeException.class, method="handleExceptionDefault"),
		        @Jpf.Catch(type=ContrasenaExpiradaException.class, method="handleContrasenaExpiradaException")
		    }
			
		
)
public abstract class ControllerBase extends PageFlowController {
	private static final Logger _log = Logger.getInstance( ControllerBase.class );
	@Jpf.ExceptionHandler(
	        forwards={
	            @Jpf.Forward(name="loginPage", path="../login/index.jsp",redirect=true)
	        }
	    )

	public Forward handleLoginException(NotLoggedInException e, String actionName, String message, Object formBean) throws URISyntaxException, IOException{
		GenericURL url = GenericURL.createGenericURL(getRequest(), getResponse());
		url.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false");
		url.setForcedAmpForm(false);
		url.setTemplate("https");
		
		getSession().setAttribute(Constantes.SESSION_LOGIN_NEXT_URI, url.toString());
		
		url = PageNewUrl.createPageURL(getRequest(), getResponse(), "servicios_enlinea_login");
//		url.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false");
		url.setTemplate("https");
		
		if(!getResponse().isCommitted()){
			getResponse().sendRedirect(url.toString());
		}
		return null;
    }
	
	
	@Jpf.ExceptionHandler(
	        forwards={
	            @Jpf.Forward(name="fallo", navigateTo=Jpf.NavigateTo.currentPage )
	        }
	    )
	public Forward handleVitriaException(ErrorVitriaException e, String actionName, String message, Object formBean){
		Forward forward = new Forward("fallo"); 
		forward.addActionOutput("errores", e.getMessage());
		return forward;
    }
	@Jpf.ExceptionHandler(
	        forwards={
	            @Jpf.Forward(name="loginPage", path="../login/index.jsp",redirect=true)
	        }
	    )
	public Forward handleExceptionDefault(RuntimeException e, String actionName, String message, Object formBean) throws URISyntaxException{
		_log.error("Error en la aplicacion: ", e);
		GenericURL url = PageNewUrl.createPageURL(getRequest(), getResponse(), "cablevision_portal_error");
//		url.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false");
//		url.setTemplate("defaultDesktop");
		
		return new Forward(new URI(url.toString()),true);
    }
	
	@Override
	 protected synchronized void beforeAction() throws Exception {
	  
		PageFlowActionMapping pfActionMapping =
	            getActionMapping() instanceof PageFlowActionMapping ? ( PageFlowActionMapping ) getActionMapping() : null;
	  
	    if(pfActionMapping != null){
	    	if(!pfActionMapping.getPath().equals("/muestraModificarPassword")&& !pfActionMapping.getPath().equals("/modificarPassword")){
		        if(pfActionMapping.isLoginRequired() && "true".equals(getSession().getAttribute(Constantes.CONTRASENA_EXPIRADA))){
		        	if(!"true".equals(ScopedServletUtils.getOuterServletRequest(getRequest()).getParameter( "logout" ))){
		        		throw new ContrasenaExpiradaException();
		        	}
		        }
	    	}
	    }
		
		super.beforeAction();
	 }
	
	
	@Jpf.ExceptionHandler(
        forwards={
            @Jpf.Forward(name="loginPage", path="../login/index.jsp",redirect=true)
        }
	)
	public Forward handleContrasenaExpiradaException(ContrasenaExpiradaException e, String actionName, String message, Object formBean) throws URISyntaxException{
		//_log.error("Error en la aplicacion handleContrasenaExpiradaException: ", e);
		GenericURL url = PageNewUrl.createPageURL(getRequest(), getResponse(), "servicios_enlinea_expira");
//		url.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false");
		url.setTemplate("https");
		
		return new Forward(new URI(url.toString()),true);
    }
	
}
