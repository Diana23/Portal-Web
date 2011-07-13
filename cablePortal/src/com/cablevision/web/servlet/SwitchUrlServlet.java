package com.cablevision.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.bea.portlet.GenericURL;
import com.bea.portlet.PageURL;
import com.cablevision.util.PageNewUrl;

public class SwitchUrlServlet extends HttpServlet implements javax.servlet.Servlet {
	static final long serialVersionUID = 1L;

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public SwitchUrlServlet() {
		super();
	}   	

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doBoth(request, response);
	}  	

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doBoth(request, response);
	}   

	private void doBoth(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			Map<String,String> urlsMap = PageNewUrl.getMapUrls();
			
			String relativeURI = request.getRequestURI();
			
			if(relativeURI.contains(request.getContextPath())){
				relativeURI = StringUtils.substringAfter(relativeURI, request.getContextPath());
			}
			
			String pageLabel = urlsMap.get(relativeURI);
			if( StringUtils.isNotEmpty(pageLabel) ){
				PageURL urlAnterior = PageURL.createPageURL(request, response, pageLabel);
				urlAnterior.setTemplate("desktopContextPath");
				urlAnterior.addParameter(GenericURL.TREE_OPTIMIZATION_PARAM, "false"); 
				urlAnterior.setForcedAmpForm(false);
				urlAnterior.setEncodeSession(false);
				
				//((HttpServletRequest)request).getRequestDispatcher("/cablevision.portal?_nfpb=true&_nfxr=false&_pageLabel=soluciones_cable_cableDigital&_nfto=false").forward(request, response);
				((HttpServletRequest)request).getRequestDispatcher(urlAnterior.toString()).forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
}
