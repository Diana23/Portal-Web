package com.cablevision.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;

import com.cablevision.util.UcmUtil;
import com.stellent.getfile.GetFileByNameResult;

/**
 * Servlet implementation class for Servlet: LastVersionSevlet
 *
 */
public class LastVersionSevlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	static final long serialVersionUID = 1L;

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public LastVersionSevlet() {
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
		GetFileByNameResult result;
		try {
			String[] splitedPath = request.getPathInfo().split("/");
			
			if((request.isUserInRole("WEBPORTALADMINISTRATOR")||request.isUserInRole("CONTRIBUIDOR")||request.isUserInRole("REVIEWER"))){
				if(request.getPathInfo().contains("notlast")){
					result = UcmUtil.getFileNameResult(splitedPath[splitedPath.length-1].split("\\.")[0].toUpperCase(),false,request);
				}else{
					result = UcmUtil.getFileNameResult(splitedPath[splitedPath.length-1].split("\\.")[0].toUpperCase(),true,request);
				}
			}else{
				result = UcmUtil.getFileNameResult(splitedPath[splitedPath.length-1].split("\\.")[0].toUpperCase(),false,request);
			}
			
			
			if(result!=null&&result.getFileInfo()[0]!=null&&result.getFileInfo()[0].getDFormat()!=null){
				if(result.getFileInfo()[0].getDFormat().endsWith("/css")){
					response.setContentType("text/css; charset=UTF-8");
				}else{
					response.setContentType(result.getFileInfo()[0].getDFormat());
				}
			}
			
			ServletOutputStream sos;
			sos = response.getOutputStream();
			sos.write(result.getDownloadFile().getFileContent());
			sos.flush();
		} catch (ServiceException e) {
			e.printStackTrace();
		}	
	}
}