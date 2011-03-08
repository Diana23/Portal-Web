package com.cablevision.controller.noticias;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import org.apache.beehive.netui.pageflow.Forward;
import org.apache.beehive.netui.pageflow.PageFlowUtils;
import org.apache.beehive.netui.pageflow.annotations.Jpf;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;

import com.cablevision.controller.base.ControllerBase;
import com.cablevision.util.SolrHelper;
import com.cablevision.vo.SolrQueryVO;
import com.cablevision.util.ContenidoHelper;

/**
 * Page Flow para usar con Noticias y Prensa
 * 
 * @author Daniela G - JWMSolutions 07/12/2101
 *
 */
@Jpf.Controller(
		messageBundles={ 
		@Jpf.MessageBundle(bundlePath = "mensajeError", bundleName="mensajeError" )
	})

public class NoticiasController extends ControllerBase {
	private static final long serialVersionUID = 1L;
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", action = "getNoticias") })
	public Forward begin() throws Exception{
		Forward forward = new Forward("success");	
		forward.addActionOutput("fromBegin", true);
		return forward;

	}
	
	private List<String> getNoticiasPorMes(String query) throws Exception{
		QueryResponse res = SolrHelper.queryNewsletter(
				query, "noticia_fecha", ORDER.desc , true, new String[] {"noticia_fechaCorta"}, "noticia_fechaCorta", null);
		FacetField facetField = res.getFacetField("noticia_fechaCorta");
		List<String> fechasMap = new ArrayList<String>();
		
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		sdfDate.setTimeZone(TimeZone.getTimeZone("UTC"));
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date fecha = null;
		
		if(facetField!=null&&facetField.getValues()!=null){
			for (FacetField.Count element : facetField.getValues()) {
				fecha = sdfDate.parse(element.getName());
				if(fecha != null)
					fechasMap.add(sdf.format(fecha));
			}

			Collections.reverse(fechasMap);
			if(fechasMap.size()>6){
				fechasMap = fechasMap.subList(0, 6);
			}
		}
		return fechasMap;
	}
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "noticias.jsp"),
							 @Jpf.Forward(name = "begin", path = "index.jsp")
				})
	public Forward getNoticias()throws Exception {
		Forward forward = new Forward("success");
		
		String fecha = getRequest().getParameter("fecha");
		String orderField = StringUtils.isNotEmpty(getRequest().getParameter("orden"))?getRequest().getParameter("orden"):"noticia_fecha";
		String id = getRequest().getParameter("id");
		String strStart = getRequest().getParameter("start");
		String strRows = getRequest().getParameter("rows");
		Map<String, String> mapaPaginas = new TreeMap<String, String>();
		String query ="tipo:noticia";
		ORDER order = ORDER.asc;
	
		//ver si la pagina viene del begin para mandar al index y agregar el menu derecho de noticias por mes
		if(PageFlowUtils.getActionOutput("fromBegin", getRequest())!= null){
			forward = new Forward("begin");
			forward.addActionOutput("fechaNoticias", getNoticiasPorMes("tipo:noticia"));
		}
		
		if(orderField.equals("noticia_fecha")){
			order = ORDER.desc;
		}
		
		//tiene permisos para editar y revisar
		boolean canAdd =  getRequest().isUserInRole("CONTRIBUIDOR")||getRequest().isUserInRole("WEBPORTALADMINISTRATOR");
		forward.addActionOutput("canAdd", canAdd);
		
		//paginacion
		int minRows = 5;
		int start = (strStart==null || strStart.equalsIgnoreCase(""))?0:Integer.parseInt((String)strStart);
		int rows = (strRows==null || strRows.equalsIgnoreCase(""))?minRows:Integer.parseInt((String)strRows);
		
		
		if(StringUtils.isNotEmpty(fecha) && !"undefined".equalsIgnoreCase(fecha)){
			SimpleDateFormat sdfFechaCorta = new SimpleDateFormat("MM/dd/yyyy");
			Date fechaCorta = sdfFechaCorta.parse(fecha);
			query = query.concat(" AND noticia_fechaCorta:\""+SolrHelper.Date2UTC(fechaCorta)+"\"");
			
		}
		if(StringUtils.isNotEmpty(id) && !"undefined".equalsIgnoreCase(id)){
			query = query.concat(" AND id:"+id);
		}
		
		SolrQueryVO queryVO = new SolrQueryVO(query.toString(), orderField, order , false, null, null, start, rows, null, 0);
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
		
		forward.addActionOutput("noticias", queryVO.getQueryResponse().getResults());
		
		return forward;
	}
}
