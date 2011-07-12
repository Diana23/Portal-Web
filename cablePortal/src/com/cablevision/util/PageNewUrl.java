package com.cablevision.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.bea.p13n.cache.Cache;
import com.bea.p13n.cache.CacheFactory;
import com.bea.portlet.GenericURL;
import com.bea.portlet.PageURL;

public class PageNewUrl extends GenericURL {
	
	public PageNewUrl(HttpServletRequest httpRequest, HttpServletResponse httpResponse ){
		super(httpRequest, httpResponse);
	}
	
	public static GenericURL createPageURL(javax.servlet.http.HttpServletRequest httpRequest,
            javax.servlet.http.HttpServletResponse httpResponse,
            String pageLabel){
		
		Map<String,String> urlsMap = getMapUrls();
		String newUrl = "";
		GenericURL page = null;
		
		if(StringUtils.isNotEmpty(pageLabel)){
			for(Map.Entry<String, String> entry : urlsMap.entrySet()){
				if(entry.getValue().equals(pageLabel.trim())){
					newUrl = entry.getKey();
				}
			}
			
			if( StringUtils.isNotEmpty(newUrl) ){
				page = new PageNewUrl(httpRequest, httpResponse); 
				page.setTemplate("default");
				page.setContextualPath(newUrl);
			} else {
				page = PageURL.createPageURL(httpRequest, httpResponse, pageLabel);
				page.setTemplate("default");
			}
		}else{
			page = new PageNewUrl(httpRequest, httpResponse); 
			page.setTemplate("default");
		}
		
		return page;
	}
	
	@Override
	public void setTemplate(String templateName) {
		super.setTemplate(templateName+"PrettyUrl");
	}
	
	public static Map<String,String> getMapUrls() {
		Map<String,String> mapUrls = getMapUrlsFromCache();
		if ( mapUrls == null) {
			mapUrls = ContenidoHelper.getUrlMapFromFile(ConfigurationHelper.getPropiedad("urls.archivo.ucm"));
			setMapUrlInCache(mapUrls);
		} 
		
		return mapUrls;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,String> getMapUrlsFromCache() {
		Object results = null;
		Cache cache = CacheFactory.getCache(Constantes.CABLEVISION_URL_NUEVAS);
		results = cache.get(Constantes.URL_CACHE_KEY);
		if ( results instanceof Map ) {
			return (Map<String,String>) results;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static void setMapUrlInCache( Map<String,String> urlMap ){
		Cache cache = CacheFactory.getCache( Constantes.CABLEVISION_URL_NUEVAS );
		cache.put(Constantes.URL_CACHE_KEY, urlMap);
	}
	
	/*private static Map initMap(){
		Map<String,String> urls = new HashMap<String,String>();
		if( urls.isEmpty() ){
			urls.put("cablevision_portal_page_home", "/");
			urls.put("soluciones_soluciones", "/soluciones");
			urls.put("soluciones_cable_cableDigital", "/cable");
			urls.put("soluciones_cable_comparanos", "/cable/descubre");
			urls.put("soluciones_cable_paquetes", "/cable/paquetes");
		}
		
		return urls;
	}*/
}
