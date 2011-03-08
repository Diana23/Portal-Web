package com.cablevision.controller.canales;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.beehive.netui.pageflow.Forward;
import org.apache.beehive.netui.pageflow.PageFlowController;
import org.apache.beehive.netui.pageflow.annotations.Jpf;
import org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.bea.p13n.cache.Cache;
import com.bea.p13n.cache.CacheFactory;
import com.cablevision.service.IChannelService;
import com.cablevision.util.ComparatorCanales;
import com.cablevision.util.Constantes;
import com.cablevision.vo.Cvcategory;
import com.cablevision.vo.Cvchannel;
import com.cablevision.vo.Cvpack;

//Substitute with this annotation if nested pageflow
//@Jpf.Controller( nested=true )

@Jpf.Controller()
public class CanalesController extends PageFlowController {
	private static final long serialVersionUID = 1L;
	private transient IChannelService channelService;
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "canales.jsp") })
	protected Forward begin(final CanalesForm form) throws Exception {
		Forward forward = new Forward("success");
		String categoria = ScopedServletUtils.getOuterServletRequest(getRequest()).getParameter("cnlCategory");
		String paquete = ScopedServletUtils.getOuterServletRequest(getRequest()).getParameter("paquete");
		
		List<Cvchannel> canales = (List<Cvchannel>)getChannelsFromCache();
		
		if(StringUtils.isBlank(categoria)){
			if(canales == null){
				canales = getChannelService().findAllCvchannels();
				setChannelsInCache(canales);
			}
		}else{
			form.setFiltros(new String[]{categoria});
			canales = getChannelService().findCvChannel(null, null, form.getFiltros());
			forward.addOutputForm(form);
			forward.addActionOutput("categoriaSelec", categoria);
		}
		
		
		//ordenar por paquete 
		if(StringUtils.isNotBlank(paquete)){
			ComparatorCanales comparador = new ComparatorCanales();
			comparador.cambiarOrden(paquete);
			List<Cvchannel> canalesTemp = new ArrayList<Cvchannel>(canales);
			canales = canalesTemp;
			Collections.sort(canales,comparador);
		}
		
		getTablaCanales(forward, canales, form!=null?form.getBuscar():"");
		
		return forward;
	}
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "canales.jsp") })
	public Forward ordenar(final CanalesForm form) throws Exception {
		Forward forward = new Forward("success");
		Integer ordenarPor = form.getOrdenarPor();
		
		String buscar = form.getBuscar();
		if(StringUtils.equalsIgnoreCase(buscar, "Busca"))
			buscar = "";
		
		String[] filtros = form.getFiltros();
		
		List<Cvchannel> canales = getChannelService().findCvChannel(ordenarPor, buscar.trim(), filtros);
		
		//ordenar por paquete
		String primerPaq = getRequest().getParameter("primerOrd");
		ComparatorCanales comparador = new ComparatorCanales();
		if(StringUtils.isNotEmpty(primerPaq)){
			comparador.cambiarOrden(primerPaq);
			Collections.sort(canales,comparador);
		}else{
			if(ordenarPor == 4){
				Collections.sort(canales,comparador);
			}
		}
		
		getTablaCanales(forward, canales, form!=null?form.getBuscar():"");
		return forward;
	}
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "canales.jsp") })
	public Forward buscar(final CanalesForm form) throws Exception {
		Forward forward = new Forward("success");
		String buscar = form.getBuscar();
		if(StringUtils.equalsIgnoreCase(buscar, "Busca"))
			buscar = "";
		
		Integer ordenarPor = form.getOrdenarPor();
		String[] filtros = form.getFiltros();
		
		
		List<Cvchannel> canales = getChannelService().findCvChannel(ordenarPor, buscar.trim(), filtros);

		
		//ordenar por paquete
		if(ordenarPor == 4){
			Collections.sort(canales,new ComparatorCanales());
		} 
		
		getTablaCanales(forward, canales, buscar);
		return forward;
	}
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "canales.jsp") })
	public Forward filtrar(final CanalesForm form) throws Exception {
		String buscar = null;
		Forward forward = new Forward("success");
		
		buscar = form.getBuscar();
		if(StringUtils.equalsIgnoreCase(buscar, "Busca"))
			buscar = "";
		
		Integer ordenarPor = form.getOrdenarPor();
		
		List<Cvchannel> canales = getChannelService().findCvChannel(ordenarPor, buscar.trim(), form.getFiltros());
		
		//ordenar por paquete
		if(ordenarPor == 4){
			Collections.sort(canales,new ComparatorCanales());
		} 
		
		getTablaCanales(forward, canales, buscar);
		return forward;
	}
	
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "imprimir.jsp") })
	public Forward imprimir(final CanalesForm form) throws Exception {
		return filtrar(form);
	}
	
	private void getTablaCanales(Forward forward, List<Cvchannel> canales, String buscar) throws Exception {
		List<Cvcategory> categorias = getChannelService().findAllCvcategories();
		List<Cvpack> paquetes = getChannelService().findAllCvpacks();
		Map<Cvchannel, List<Long>> canalesMap = new LinkedHashMap<Cvchannel, List<Long>>();
		Map<Long, String> categoriasMap = new LinkedHashMap<Long, String>();

		for(Cvchannel c: canales) {
			Set<Cvpack> channelPacks = c.getCvchannelPacks();
			List<Long> pIds = new ArrayList<Long>();
			for(Cvpack cp : channelPacks) {
				pIds.add(cp.getIdpack());
			}
			canalesMap.put(c, pIds);
		}
		for(Cvcategory c: categorias) categoriasMap.put(c.getIdcategory(), c.getName());

		forward.addActionOutput("paquetes", paquetes);
		forward.addActionOutput("canales", canalesMap);
		forward.addActionOutput("categorias", categoriasMap);
		forward.addActionOutput("popup", "");
		
		if(StringUtils.isNotBlank(buscar) && canalesMap.isEmpty()){
			forward.addActionOutput("popup", "mostrar");
		}

	}
	
	@SuppressWarnings("unchecked")
	private List<Cvchannel> getChannelsFromCache(){
		Object results = null;
		Cache cache = CacheFactory.getCache(Constantes.CABLEVISION_CANALES);
		results = cache.get(Constantes.CANALES_CACHE_KEY);
		if(results instanceof List){
			return (List<Cvchannel>)results;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private void setChannelsInCache(List<Cvchannel> canales){
		Cache cache = CacheFactory.getCache(Constantes.CABLEVISION_CANALES);
		cache.put(Constantes.CANALES_CACHE_KEY, canales);
	}
	
	// Uncomment this block if nested pageflow
	/**
	@Jpf.Action(
	    forwards={
	       @Jpf.Forward(name="done", returnAction="NestedTemplateDone")
	    }
	)
	protected Forward done()
	{
		return new Forward("done");
	}
	 **/

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

	public IChannelService getChannelService() {
		if(channelService==null){
			ApplicationContext context = (ApplicationContext)getRequest().getSession().getServletContext().getAttribute(
					WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			channelService = (IChannelService)context.getBean("ChannelService");
		}
		return channelService;
	}



	public void setChannelService(IChannelService channelService) {
		this.channelService = channelService;
	}
	
	@Jpf.FormBean
	public static class CanalesForm implements java.io.Serializable {
		private Integer ordenarPor;
		private String buscar;
		private String[] filtros;

		public Integer getOrdenarPor() {
			return ordenarPor;
		}

		public void setOrdenarPor(Integer ordenarPor) {
			this.ordenarPor = ordenarPor;
		}
		
		public String getBuscar() {
			return buscar;
		}

		public void setBuscar(String buscar) {
			this.buscar = buscar;
		}

		public String[] getFiltros() {
			return filtros;
		}

		public void setFiltros(String[] filtros) {
			this.filtros = filtros;
		}
	}
}