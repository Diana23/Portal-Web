package com.cablevision.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;

import com.cablevision.vo.SolrQueryVO;

public class SolrHelper {
	private static SolrServer serverInstance;
	private static SolrServer serverInstanceProd;
	
	
	/**
	 * Indexa un mapa en solr
	 * @param mapToIndex Mapa a indexar, la llave del mapa se utiliza como nombre del campo  en solr
	 * y el valor del mapa es el valor del campo en solr
	 * @throws Exception
	 */
	public static void indexarContenido(Map<String, String> mapToIndex, boolean isApproved) throws Exception {
		//Obtener instancia de Solr
		SolrServer server = getSolrServer();
		try{
			indexarContenido(mapToIndex,null, null);
			server.commit();
		}
		catch(Exception e){
			if(server!=null){
				server.rollback();
			}
			throw e;
		}
		
		//obtener instancia de Produccion de Solr
		String urlProd = ConfigurationHelper.getPropiedad("solr.server.url.prod");
		if(isApproved && StringUtils.isNotEmpty(urlProd)){
			SolrServer serverProd = getSolrServer(urlProd);
			try{
				indexarContenido(mapToIndex,null, urlProd);
				serverProd.commit();
			}
			catch(Exception e){
				if(serverProd!=null){
					serverProd.rollback();
				}
				throw e;
			}
		}
		
		
	}
	
	
	/**
	 *  Metodo que indexa un documento en Solr, toma los valores del parametro mapToIndex, si el parametro xml
	 *  no es vacío también indexa el documento. Este metodo no hace commit, quien genere la llamada deberá 
	 *  hacer el commit. 
	 *  
	 * @param mapToIndex mapToIndex Mapa a indexar, la llave del mapa se utiliza como nombre del campo  en solr
	 * y el valor del mapa es el valor del campo en solr
	 * @param xml El documento original que contiene los datos
	 * @throws Exception
	 */
	public static void indexarContenido(List<Map<String, String>> aIndexar) throws Exception {
		if(aIndexar!=null&&aIndexar.size()>0){
			//Obtener instancia de Solr
			SolrServer server = getSolrServer();
			Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
			try{


				for (Map<String, String> mapToIndex : aIndexar) {
					//Crear documentos a indexar
					SolrInputDocument doc = new SolrInputDocument();
					Iterator <Map.Entry<String,String>> it = mapToIndex.entrySet().iterator();
					while(it.hasNext()){
						Map.Entry<String, String> e = (Map.Entry<String, String>)it.next();
						doc.addField(e.getKey(), e.getValue());
					}

					if(doc.getFieldValue("id")!=null &&  StringUtils.isNotEmpty(doc.getFieldValue("id").toString())){
						//Agregar documentos a Solr
						docs.add(doc);

					}
				}



				server.add(docs);
			}
			catch(Exception e){
				if(server!=null){
					server.rollback();
				}
				throw e;
			}
		}
	}
	
	/**
	 *  Metodo que indexa un documento en Solr, toma los valores del parametro mapToIndex, si el parametro xml
	 *  no es vacío también indexa el documento. Este metodo no hace commit, quien genere la llamada deberá 
	 *  hacer el commit. 
	 *  
	 * @param mapToIndex mapToIndex Mapa a indexar, la llave del mapa se utiliza como nombre del campo  en solr
	 * y el valor del mapa es el valor del campo en solr
	 * @param xml El documento original que contiene los datos
	 * @throws Exception
	 */
	public static void indexarContenido(Map<String, String> mapToIndex,String xml, String url) throws Exception {
		//Obtener instancia de Solr
		SolrServer server = getSolrServer(url);
		try{
			

			//Crear documentos a indexar
			SolrInputDocument doc1 = new SolrInputDocument();
			Iterator <Map.Entry<String,String>> it = mapToIndex.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry<String, String> e = (Map.Entry<String, String>)it.next();
				doc1.addField(e.getKey(), e.getValue());
			}

			if(doc1.getFieldValue("id")!=null &&  StringUtils.isNotEmpty(doc1.getFieldValue("id").toString())){
				//Agregar documentos a Solr
				Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
				docs.add(doc1);
				server.add(docs);
			}
		}
		catch(Exception e){
			if(server!=null){
				server.rollback();
			}
			throw e;
		}
	}
	
	public static void borrarByQuery(String query) throws MalformedURLException, SolrServerException, IOException{
		getSolrServer().deleteByQuery(query);
	}
	
    
    /**
     * Regresa una instancia
     * @return
     * @throws MalformedURLException
     */
    public static SolrServer getSolrServer(String url) throws MalformedURLException{
        
    	//the instance can be reused
        if(StringUtils.isNotEmpty(url)){
        	if(serverInstanceProd==null){
        		serverInstanceProd = new CommonsHttpSolrServer(url);
        	}
        	return serverInstanceProd;
        }else{
        	if(serverInstance==null){
        		serverInstance = new CommonsHttpSolrServer(ConfigurationHelper.getPropiedad("solr.server.url"));
        	}
        	return serverInstance;
        }
    }
    
    /**
     * Regresa una instancia
     * @return
     * @throws MalformedURLException
     */
    public static SolrServer getSolrServer() throws MalformedURLException{
        return getSolrServer(null);
    }
    
    /**
     * Manda llamar a {@link #query(String,String)} enviando null en campoOrden
     * 
     * @param queryStr El query
     * @return El resultado del qeury
     */
    public static QueryResponse query(String queryStr){
    	return query(queryStr,null);
    }
    
    
    /**
     * Manda llamar a  {@link #query(String, String, ORDER)} enviando en orden {@link ORDER#asc}
     * @param queryStr El query
     * @param campoOrden campo por el que se va ordenar, si es null no hace ordenamiento
     * @return El resultado del qeury
     */
    public static QueryResponse query(String queryStr,String campoOrden){
    	return query(queryStr,campoOrden,ORDER.asc,false,null,null);
    }
    
    /**
     * 
     * @param queryStr El query
     * @param noRows campo de no de renglones a regresar
     * @return El resultado del qeury
     */
    public static QueryResponse query(String queryStr, int noRows){
    	return query(queryStr,null,ORDER.asc,false,null,noRows);
    }
    
    /**
     * Hace un query hacia Solr
     * @param queryStr El query
     * @param campoOrden campo por el que se va ordenar, si es null no hace ordenamiento
     * @param orden Tipo de orden (ascendente, descendente)
     * @return El resultado del qeury
     */
    public static QueryResponse query(String queryStr,String campoOrden, ORDER orden, boolean facet, String[] facetFields, Integer noRows){
    	SolrQuery query = new SolrQuery();
	    query.setQuery(queryStr);
	    
	    if(campoOrden!=null){
	    	query.setSortField(campoOrden, orden);
	    }
	    
	    if(facet){
	    	query.setFacet(true);
	    	query.addFacetField(facetFields);
	    	query.setFacetMinCount(1);
	    }
	    
	    if(noRows!=null){
	    	query.setRows(noRows);
	    }
	    
	    try {
			return getSolrServer().query(query);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new QueryResponse();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			return new QueryResponse();
		}
    }
    
    
    public static QueryResponse queryConHighlight(String queryStr,String[] camposHighlight,int registroInicio,Map<String, String> parametros){
    	SolrQuery query = new SolrQuery();
	    query.setQuery(queryStr);
	    query.setStart(registroInicio);
	    
	   if(camposHighlight!=null&&camposHighlight.length>0){
		   query.setHighlight(true);
		   
		   StringBuilder builder = new StringBuilder();
		   
		   for(int i=0;i<camposHighlight.length;i++){
			   builder.append(camposHighlight[i]+" ");
		   }
		   query.setParam("hl.fl", builder.toString());
		   query.setHighlightFragsize(200);
	   }
	   
	   if(parametros!=null){
		   for (String string : parametros.keySet()) {
			   query.setParam(string, parametros.get(string));
		   }
	   }
	    
	    try {
			return getSolrServer().query(query);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new QueryResponse();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			return new QueryResponse();
		}
    }
    
    /**
     * Transforma una fecha del timezone actual a UTC, en el formato que Solr lo requiere
     * @param date java.util.Date La fecha que se desea transformar
     * @return La fecha como cadena en el formato que requiere Solr
     */
    public static String Date2UTC(Date date) {
		String timePattern = "yyyy-MM-dd'T'HH:mm:ssz";
		SimpleDateFormat formatter = new SimpleDateFormat(timePattern);
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		String f = formatter.format(date);
		return f.replaceAll("UTC", "Z");
	}
    
    /**
     * Hacemos un query para obtener todos los registros de la programacion de ondemand
     * @param queryStr El query
     * @return El resultado del qeury
     */
    public static QueryResponse queryProgramacion(String queryStr, String campoOrden, ORDER orden, boolean facet, String[] facetFields, String facetsort){
    	SolrQuery query = new SolrQuery();
	    query.setQuery(queryStr);
	    try {
			QueryResponse res1 = getSolrServer().query(query);
			query.setStart(0);
			
			Long l = res1.getResults().getNumFound();
			
			query.setRows(l.intValue());
			
			if(facet){
				query.setFacet(facet);
				query.addFacetField(facetFields);
			}
			
			if(facetsort!=null){
				query.setFacetSort(facetsort);
			}
			
			if(campoOrden!=null){
		    	query.setSortField(campoOrden, orden);
		    }
			//query.setSortField("masVistos", ORDER.asc);
			
			query.setFacetMinCount(1);
			
			return getSolrServer().query(query);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return new QueryResponse();
		} catch (SolrServerException e) {
			return new QueryResponse();
		}
    }
    
    public static void queryByVO(SolrQueryVO queryVO){
    	SolrQuery query = new SolrQuery();
	    query.setQuery(queryVO.getQueryStr());
	    try {
			QueryResponse res = getSolrServer().query(query);
			query.setStart(queryVO.getStart());
			
			Long l = res.getResults().getNumFound();
			if(queryVO.getRows()==0)
				query.setRows(l.intValue());
			else
				query.setRows(queryVO.getRows());
			
			if(queryVO.isFacet()){
				query.setFacet(true);
				query.addFacetField(queryVO.getFacetFields());
			}
			
			if(queryVO.getFacetSort()!=null){
				query.setFacetSort(queryVO.getFacetSort());
			}
			
			if(queryVO.getOrderField()!=null){
		    	query.setSortField(queryVO.getOrderField(), queryVO.getOrder());
		    }
			
			query.setFacetMinCount(1);
			queryVO.setNumResults(l.intValue());
			
			queryVO.setQueryResponse(getSolrServer().query(query));
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
			queryVO.setQueryResponse(new QueryResponse());
		} catch (SolrServerException e) {
			queryVO.setQueryResponse(new QueryResponse());
		}
    }

    /**
     * Hacemos un query para obtener todos los registros de newsletter
     * @param queryStr El query
     * @return El resultado del qeury
     */
    public static QueryResponse queryNewsletter(String queryStr, String campoOrden, ORDER orden, boolean facet, String[] facetFields, String facetsort, Integer noFacets){
    	SolrQuery query = new SolrQuery();
	    query.setQuery(queryStr);
	    try {
			query.setStart(0);
			
			if(facet){
				query.setFacet(facet);
				query.addFacetField(facetFields);
			}
			
			if(facetsort!=null){
				query.setFacetSort(facetsort);
			}
			
			if(campoOrden!=null){
		    	query.setSortField(campoOrden, orden);
		    }
			//query.setSortField("masVistos", ORDER.asc);
			if(noFacets!=null){
				query.setFacetLimit(noFacets);
		    }
			
			query.setFacetMinCount(1);
			
			return getSolrServer().query(query);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return new QueryResponse();
		} catch (SolrServerException e) {
			return new QueryResponse();
		}
    }
}