package com.cablevision.controller.atencionclientes;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.beehive.netui.pageflow.Forward;
import org.apache.beehive.netui.pageflow.PageFlowController;
import org.apache.beehive.netui.pageflow.annotations.Jpf;
import org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;

import com.cablevision.util.SolrHelper;
import com.cablevision.vo.SolrQueryVO;

//@Jpf.Controller( nested=true )
@Jpf.Controller(messageBundles = {
		@Jpf.MessageBundle(bundlePath = "com.cablevision.controller.ondemand.ondemand", bundleName = "ondemand"),
		@Jpf.MessageBundle(bundlePath = "com.cablevision.carga.carga", bundleName = "carga") })
public class AtencionClientesController extends PageFlowController {
	private static final long serialVersionUID = 1L;

	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "preguntas_frecuentes.jsp") })
	protected Forward begin(AtencionClientesFormBean form) throws Exception {
		Forward forward = new Forward("success");
		form.setCategoria(StringEscapeUtils.escapeHtml(ScopedServletUtils.getOuterServletRequest(getRequest()).getParameter("categoria")));
		forward.addOutputForm(form);
		return forward;
	}

	/**
	 * Obtiene las preguntas frecuentes de Solr
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "preguntas_frecuentes.jsp") })
	protected Forward getPreguntasFrecuentes(AtencionClientesFormBean form)  throws Exception{
		Forward forward = new Forward("success");
		Map<String, String> mapaPaginas = new LinkedHashMap<String, String>();
		
		if(StringUtils.isNotBlank(ScopedServletUtils.getOuterServletRequest(getRequest()).getParameter("categoria"))){
			form.setCategoria(StringEscapeUtils.escapeHtml(ScopedServletUtils.getOuterServletRequest(getRequest()).getParameter("categoria")));
		}
		
		int minRows = 5;
		int start = 0;
		try {
			start = (form.getStart()==null || form.getStart().equalsIgnoreCase(""))?0:Integer.parseInt((String)form.getStart());
		} catch (NumberFormatException e) {
			// TODO: handle exception
		}
		
		int rows = (form.getRows()==null || form.getRows().equalsIgnoreCase(""))?minRows:Integer.parseInt((String)form.getRows());
		
		StringBuffer strQuery = new StringBuffer("tipo:preguntaFrecuente");
		if(form.getCategoria()!=null){
			strQuery.append(" AND preguntaFrecuente_categoria:\""+QueryParser.escape(ScopedServletUtils.getOuterServletRequest(getRequest()).getParameter("categoria"))+"\"");
		}else{
			if(form.getSearchStr()!=null){
				if(form.getSearchStr().length()>0){
					String searchStr = QueryParser.escape(form.getSearchStr());
					strQuery.append(" AND (preguntaFrecuente_pregunta:("+searchStr+") OR preguntaFrecuente_respuesta:("+searchStr+"))");
				}
			}
		}
		
		SolrQueryVO queryVO = new SolrQueryVO(strQuery.toString(), "id", ORDER.asc, false, null, null, start, rows, null, 0);
		SolrHelper.queryByVO(queryVO);
		
		int numPreguntas = queryVO.getNumResults();
		
		forward.addActionOutput("numPreguntas", numPreguntas);
		forward.addActionOutput("minRows", minRows);
		forward.addActionOutput("currentPage", (start/minRows)+1);
		
		double fracPages = (double)numPreguntas/(double)minRows;
		
		long decPart = Math.round((fracPages-Math.floor(fracPages))*100);
		
		long numPages =  (long)fracPages;
		
		numPages = decPart>0?numPages+1:numPages;
		
		forward.addActionOutput("numPages", numPages);
		
		long maxRes = ((start+minRows)>numPreguntas)?numPreguntas:(start+minRows);
		forward.addActionOutput("maxRes", maxRes);
		
		for(int i=0; i<numPages; i++){
			mapaPaginas.put(String.valueOf(i+1),String.valueOf(i+1));
		}
		
		getRequest().setAttribute("paginas", mapaPaginas.keySet());
		forward.addActionOutput("mapaPaginas", mapaPaginas);
		
		forward.addActionOutput("preguntas", queryVO.getQueryResponse().getResults());
		
		getFAQCategorias(forward);
		
		return forward;
	}
	
	private void getFAQCategorias(Forward forward) {
		QueryResponse res = SolrHelper.query("tipo:preguntaFrecuente", null, null, true, new String[] { "preguntaFrecuente_categoria" }, null);
		FacetField facetField = res.getFacetField("preguntaFrecuente_categoria");
		Map<String, String> categoriasMap = new HashMap<String, String>();
		if(facetField!=null && facetField.getValues()!=null){
			for (FacetField.Count element : facetField.getValues()) {
				categoriasMap.put(element.getName(), element.getName());
			}
		}
		getRequest().setAttribute("categorias", categoriasMap.keySet());
		forward.addActionOutput("categoriasMap", categoriasMap);
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

	/**
	 * @author Jose Hernandez
	 * 
	 */
	@Jpf.FormBean
	public static class AtencionClientesFormBean implements java.io.Serializable {
		private static final long serialVersionUID = 1L;

		private String id;
		private String tipo;
		private String pregunta;
		private String respuesta;
		private String categoria;
		private String start;
		private String rows;
		private String searchStr;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getTipo() {
			return tipo;
		}

		public void setTipo(String tipo) {
			this.tipo = tipo;
		}
		
		public String getCategoria() {
			return categoria;
		}
		
		public String getPregunta() {
			return pregunta;
		}
		
		public String getRespuesta() {
			return respuesta;
		}
		
		public void setCategoria(String categoria) {
			this.categoria = categoria;
		}
		
		public void setPregunta(String pregunta) {
			this.pregunta = pregunta;
		}
		
		public void setRespuesta(String respuesta) {
			this.respuesta = respuesta;
		}
		
		public String getRows() {
			return rows;
		}
		
		public String getStart() {
			return start;
		}
		
		public void setRows(String rows) {
			this.rows = rows;
		}
		
		public void setStart(String start) {
			this.start = start;
		}
		
		public String getSearchStr() {
			return searchStr;
		}
		
		public void setSearchStr(String searchStr) {
			this.searchStr = searchStr;
		}
	}
}