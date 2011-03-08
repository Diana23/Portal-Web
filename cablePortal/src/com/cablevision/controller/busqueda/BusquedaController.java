package com.cablevision.controller.busqueda;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.beehive.netui.pageflow.Forward;
import org.apache.beehive.netui.pageflow.PageFlowController;
import org.apache.beehive.netui.pageflow.annotations.Jpf;
import org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.solr.client.solrj.response.QueryResponse;

import com.cablevision.util.SolrHelper;

@Jpf.Controller()
public class BusquedaController extends PageFlowController {
	private static final long serialVersionUID = 1L;

	/**
	 * Metodo que hace la busqueda
	 * @return El forward con la informaci\u00F3n del siguiente paso
	 */
	@Jpf.Action(forwards = { 
			@Jpf.Forward(name = "general", path = "general.jsp"),
			@Jpf.Forward(name = "programacion", path = "programacion.jsp")
		}
	)
	public Forward begin(BusquedaBean form) {
		Forward forward = new Forward("general");
		QueryResponse response = null;
		int registroInicial = (form.getPaginaInicio()-1)*10;
		
		if(StringUtils.isBlank(form.getBusqueda())){
			llenarDeRequest(form);
		}
		
		/*
		 * Variable para saber si se cambio el texto de busqueda,
		 * de venir falso el texto no se cambió y se interpreta como vacío
		 */
		ServletRequest request = ScopedServletUtils.getOuterServletRequest(getRequest());
		if (StringUtils.isNotBlank(request.getParameter("changeSearch")) && request.getParameter("changeSearch").equals("false")){
			form.setBusqueda("");
		}
		
		String queryUsuario = QueryParser.escape(form.getBusqueda());
		
		if(StringUtils.isNotEmpty(queryUsuario.trim())){
			if("programacion".equals(form.getTipoBusqueda())){
				Map<String, String> parametros = new HashMap<String, String>();
				parametros.put("sort", "score desc,fechaini desc");
				
				response = SolrHelper.queryConHighlight(
						"+(+(descripcion:("+ queryUsuario + ") OR descripcion:(\""+queryUsuario+"\")^3 OR titulo:(\""+queryUsuario+"\")^4 OR titulo:("+queryUsuario+")^2) AND fechaini:[NOW *]) NOT tipo:"+QueryParser.escape("programacion.ppv"),
						new String[]{"descripcion","titulo"},registroInicial,parametros);
				
				forward = new Forward("programacion");
			}else if("productos".equals(form.getTipoBusqueda())){
				response = SolrHelper.queryConHighlight("url:(soluciones_)  " +
						"AND -url:(cnlCategory) AND -url:(&paquete=) " +
						"AND  (content:("+ queryUsuario + ") OR content:(\""+queryUsuario+"\")^3 OR " +
								"title:("+queryUsuario+")^2 OR title:(\""+queryUsuario+"\"))^4",
						new String[]{"content","title"},
						registroInicial,
						null
				);
			}else{
				response = SolrHelper.queryConHighlight("content:("+ queryUsuario + ")^1 OR content:(\""+queryUsuario+"\")^3 OR "+
							"title:("+queryUsuario+")^2 OR title:(\""+queryUsuario+"\")^4",new String[]{"content","title"},registroInicial,null);
			}
		}
		
		if(response != null && response.getResults() != null){
			forward.addActionOutput("numeroPaginas", (int)Math.ceil((double)response.getResults().getNumFound() / 10));
			forward.addActionOutput("esPaginaFinal", registroInicial+11>=response.getResults().getNumFound());
			forward.addActionOutput("total", response.getResults().getNumFound());
			forward.addActionOutput("listSize", response.getResults().size());
		}else{
			forward.addActionOutput("numeroPaginas", 0);
			forward.addActionOutput("esPaginaFinal", true);
			forward.addActionOutput("total", 0);
			forward.addActionOutput("listSize", 0);
		}
		
		forward.addActionOutput("resultadosBusqueda", response);
		forward.addActionOutput("paginaInicio", form.getPaginaInicio());
		forward.addActionOutput("resultadosXPagina", 10);
		forward.addActionOutput("registroInicial", registroInicial);
		
		return forward;
	}
	
	private void llenarDeRequest(BusquedaBean form){
		ServletRequest request = ScopedServletUtils.getOuterServletRequest(getRequest());
		
		form.setBusqueda(request.getParameter("busqueda"));
		
		if(StringUtils.isNotBlank(request.getParameter("paginaInicio"))){
			try{
				form.setPaginaInicio(Integer.parseInt(request.getParameter("paginaInicio")));
			}catch (NumberFormatException e) {
				form.setPaginaInicio(1);
			}
		}
		
		form.setTipoBusqueda(request.getParameter("tipoBusqueda"));
		
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
	
	@Jpf.FormBean
	public static class BusquedaBean implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		private String tipoBusqueda;
		private String busqueda;
		private Integer paginaInicio = 1;
		
		public String getTipoBusqueda() {
			return tipoBusqueda;
		}
		public void setTipoBusqueda(String tipoBusqueda) {
			this.tipoBusqueda = tipoBusqueda;
		}
		public String getBusqueda() {
			return busqueda;
		}
		public void setBusqueda(String busqueda) {
			this.busqueda = busqueda;
		}
		public Integer getPaginaInicio() {
			return paginaInicio;
		}
		public void setPaginaInicio(Integer paginaInicio) {
			this.paginaInicio = paginaInicio;
		}
		
		
	}
}