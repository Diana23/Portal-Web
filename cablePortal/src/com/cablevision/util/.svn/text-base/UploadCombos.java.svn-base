package com.cablevision.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.access.DefaultLocatorFactory;

import com.bea.p13n.cache.Cache;
import com.bea.p13n.cache.CacheFactory;
import com.cablevision.service.ICombosService;
import com.cablevision.vo.CvBanco;
import com.cablevision.vo.CvEserviceN2;
import com.cablevision.vo.CvEserviceN3;
import com.cablevision.vo.CvTipoTarjeta;


/**
 * Clase para llenar los combos de la aplicacion, si no vienen en cache los toma de la base de datos
 * @author Daniela G 
 *
 */

public class UploadCombos {
	private static ICombosService combosService;
	
	@SuppressWarnings("unchecked")
	public static Map getCombo(HttpServletRequest request, Long idCombo, String nivel) throws Exception{
		Map results = (Map)getComboFromCache(request, idCombo.toString()+"."+nivel);
		if(results == null){
			Map<String, String> comboMap = new LinkedHashMap();
			
			if(nivel.equals("1")){
				List<CvEserviceN2> comboListN2 = getCombosService().findCvEserviceN2ByEn1Id(idCombo);
				for(CvEserviceN2 serviceN3 : comboListN2){
					comboMap.put(serviceN3.getEn2Name(), serviceN3.getEn2Name());
				}
			}else{
				List<CvEserviceN3> comboListN3 = getCombosService().findCvEserviceN3ByEn2Id(idCombo);
				for(CvEserviceN3 serviceN3 : comboListN3){
					comboMap.put(serviceN3.getEn3Name(), serviceN3.getEn3Name());
				}
			}
			
			setComboInCache(request, comboMap, idCombo.toString()+"."+nivel);
			return comboMap;
		}
		return results;
	}
	
	@SuppressWarnings("unchecked")
	public static Map getComboBancos(HttpServletRequest request, String idCombo) throws Exception{
		Map results = (Map)getComboFromCache(request, idCombo);
		if(results == null){
			Map<String, String> comboMap = new LinkedHashMap();
			
			List<CvBanco> comboBancos = getCombosService().findAllCvBancos();
			for(CvBanco banco : comboBancos){
				comboMap.put(banco.getBanNombre(), banco.getBanNombre());
			}
			
			setComboInCache(request, comboMap, idCombo);
			return comboMap;
		}
		return results;
	}
	
	@SuppressWarnings("unchecked")
	public static Map getComboTarjetas(HttpServletRequest request, String idCombo) throws Exception{
		Map results = (Map)getComboFromCache(request, idCombo);
		if(results == null){
			Map<String, String> comboMap = new LinkedHashMap();
			
			List<CvTipoTarjeta> comboBancos = getCombosService().findAllCvTipoTarjetas();
			for(CvTipoTarjeta tarjeta : comboBancos){
				comboMap.put(tarjeta.getTtaNombre(), tarjeta.getTtaNombre());
			}
			
			setComboInCache(request, comboMap, idCombo);
			return comboMap;
		}
		return results;
	}
	
	@SuppressWarnings("unchecked")
	public static Map getComboFromCache(HttpServletRequest request, String idKey) throws Exception{
		Object results = null;
		Cache cache = CacheFactory.getCache(Constantes.CABLEVISION_COMBOS);
		String key = Constantes.COMBO_CACHE_KEY+idKey;
		results = cache.get(key);
		if(results instanceof Map){
			return (Map)results;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static void setComboInCache(HttpServletRequest request, Map comboMap, String idKey){
		Cache cache = CacheFactory.getCache(Constantes.CABLEVISION_COMBOS);
		String key = Constantes.COMBO_CACHE_KEY+idKey;
		cache.put(key, comboMap);
	}

	
	@SuppressWarnings( { "unused", "unchecked" })
	public static void flushCache(HttpServletRequest request)
			throws Exception {
		Object result = null;
		CacheFactory.flush(Constantes.CABLEVISION_COMBOS);
	}
	
	public List<String> getMotivos(String area){
		List<String> motivos = null;
		try{
			CvEserviceN2 service = getCombosService().findCvEserviceN2IdByName(area);
			motivos = getCombosService().getListaMotivos(service.getEn2Id());
		}catch(Exception e){
			e.printStackTrace();
		}
		return motivos;
	}
	
	public static ICombosService getCombosService() {
		if(combosService == null){
			BeanFactory factory = DefaultLocatorFactory.getInstance("classpath*:/com/cablevision/conf/cablevisionBeanRefContext.xml")
			.useBeanFactory("cablevision-context")
			.getFactory();
			
			combosService = (ICombosService) factory.getBean("CombosService");
		}
		
		return combosService;
	}
}
