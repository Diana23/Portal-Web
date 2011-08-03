package com.cablevision.backingfile;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.bea.netuix.servlets.controls.application.backing.DesktopBackingContext;
import com.bea.netuix.servlets.controls.content.backing.AbstractJspBacking;
import com.bea.netuix.servlets.controls.page.BookBackingContext;
import com.bea.netuix.servlets.controls.page.PageBackingContext;
import com.bea.portlet.GenericURL;
import com.cablevision.util.PageNewUrl;
import com.cablevision.util.sso.GoogleSSO;

/**
 * Backing file para el libro principal.
 * 
 * Pone bien el titulo de la pagina y redirecciona al home cuando se entra 
 * directo al portal (sin parametros)
 * 
 * @author luishpm
 *
 */
public class MainBookBackingFile extends AbstractJspBacking {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public boolean handlePostbackData(HttpServletRequest request,HttpServletResponse response) {
		return false;
	}
	

	
	public boolean preRender(HttpServletRequest request, HttpServletResponse response){
		DesktopBackingContext desktop = DesktopBackingContext.getDesktopBackingContext(request);
		BookBackingContext book = desktop.getBookBackingContext();
		PageBackingContext activePage = book.getFirstDisplayedPageBackingContext();
		if("Home Page".equalsIgnoreCase(activePage.getTitle()))
			desktop.setTitle("Cablevision - Mexico | Internet, Cable y Telefonía");
		else
			desktop.setTitle(activePage.getTitle()+" - "+desktop.getTitle());
		
		if(!response.isCommitted()){
			if(StringUtils.isEmpty(request.getQueryString())){
				GenericURL url = PageNewUrl.createPageURL(request, response,"cablevision_portal_page_home");
				url.setTemplate("defaultDesktop");
//				url.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false");
				try {
					response.sendRedirect(url.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}

				return false;
			}else{
				try{
					GoogleSSO.sso(request, response);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
		}else{
			return false;
		}
		return true;
    }
	
	protected boolean isRequestTargeted(HttpServletRequest request)
    {
		return super.isRequestTargeted(request);
    }

}
