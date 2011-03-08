package com.cablevision.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.access.DefaultLocatorFactory;

import com.cablevision.service.IUsuarioPortalService;

public class AppContextListener implements ServletContextListener {

	private ServletContext context = null;
	private transient IUsuarioPortalService usuarioPortalService;

	/*This method is invoked when the Web Application has been removed 
  	and is no longer able to accept requests
	 */

	public void contextDestroyed(ServletContextEvent event){
		//Output a simple message to the server's console
		this.context = null;
	}


	//This method is invoked when the Web Application
	//is ready to service requests
	public void contextInitialized(ServletContextEvent event){
		this.context = event.getServletContext();
		
		//Output a simple message to the server's console
		borrarSesionesBd();
	}
	
	private void borrarSesionesBd(){
		try{
			getUsuarioPortalService().removeSessionIds();
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
