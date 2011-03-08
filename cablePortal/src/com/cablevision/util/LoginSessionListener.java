package com.cablevision.util;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.access.DefaultLocatorFactory;

import com.cablevision.service.IUsuarioPortalService;
import com.cablevision.vo.CvUsuarioPortal;

public class LoginSessionListener implements HttpSessionListener{
	private transient IUsuarioPortalService usuarioPortalService;
	
	public void sessionCreated(HttpSessionEvent se){
        ;
    }
	 
    public void sessionDestroyed(HttpSessionEvent se){
        HttpSession session = se.getSession();
        // session has been invalidated and all session data 
        //(except Id)is no longer available
        
        if(StringUtils.isNotEmpty((String)session.getAttribute(Constantes.SESSION_ACCOUNT_ID))){
        	borrarSessionBd(session.getAttribute(Constantes.SESSION_ACCOUNT_ID).toString());
        }
    }
    
    private String getTime(){
        return new Date(System.currentTimeMillis()).toString();
    }
	
    private void borrarSessionBd(String usuario){
    	try{
    		CvUsuarioPortal usuarioPortal = getUsuarioPortalService().findCvUsuarioPortalById(usuario.trim().toUpperCase());
        	usuarioPortal.setCupSessionId("");
        	getUsuarioPortalService().persistCvUsuarioPortal(usuarioPortal);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    public IUsuarioPortalService getUsuarioPortalService(){
    	if(usuarioPortalService == null){
    		BeanFactory factory = DefaultLocatorFactory.getInstance("classpath*:/com/cablevision/conf/cablevisionBeanRefContext.xml")
    		.useBeanFactory("cablevision-context").getFactory();
    		
    		usuarioPortalService = (IUsuarioPortalService)factory.getBean("UsuarioPortalService");
    	}
    	return usuarioPortalService;
    } 
}
