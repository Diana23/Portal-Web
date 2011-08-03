package com.cablevision.controller.cotizador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.beehive.netui.pageflow.Forward;
import org.apache.beehive.netui.pageflow.annotations.Jpf;
import org.apache.beehive.netui.pageflow.scoping.ScopedServletUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.cablevision.controller.base.ControllerBase;
import com.cablevision.service.ICotizadorService;
import com.cablevision.service.ILeadService;
import com.cablevision.util.ReCaptchaUtil;
import com.cablevision.vo.CreceProd;
import com.cablevision.vo.ErrorField;
import com.cablevision.vo.Extra;
import com.cablevision.vo.ProductService;
import com.cablevision.vo.Service;
import com.cablevision.vo.UpgradesExtra;
import com.cablevision.vo.UpgradesProd;

/**
 * 
 * @author Crysfel - JWMSolutions 29/09/2009
 *
 */

@Jpf.Controller()
public class CotizadorController extends ControllerBase{
	
	private static final long serialVersionUID = 1L;
	private transient ICotizadorService cotizadorService;
	private transient ILeadService leadService;
	private transient ProductService seleccionado;
	/**
	 * Este m\u00E9todo nos proporciona los servicios que se muestran al inicio
	 * Del cotizador
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "index.jsp") })
	public Forward begin(CotizadorForm form) throws Exception {
		Forward forward = new Forward("success");
		String idService = ScopedServletUtils.getOuterRequest(getRequest()).getParameter("idService");
		String idProduct = ScopedServletUtils.getOuterRequest(getRequest()).getParameter("idProduct");
		seleccionado = new ProductService();
		List<Service> services = getCotizadorService().findAllServices();
		
		/*
		 * Se recibe el idProducto y el idServicio, si vienen son pasados que servicio se mostrara al principio y el orden en el que esta
		 * para poder marcar en el menu la opción seleccionada
		 */
		if(StringUtils.isNotEmpty(idProduct) || StringUtils.isNotEmpty(idService)){
			if(StringUtils.isNotEmpty(idProduct)){
				ProductService product = getCotizadorService().findProductServiceById(Long.parseLong(idProduct));
				getRequest().setAttribute("primerServicio", product.getService().getIdService().toString());
				getRequest().setAttribute("ordenSelect", product.getService().getOrden().toString());
				getRequest().setAttribute("productSelect", idProduct);
				forward.addActionOutput("product", product);
			}else{
				getRequest().setAttribute("primerServicio", idService);
				for(Service service: services){
					if(service.getIdService().longValue() == Long.parseLong(idService) ){
						getRequest().setAttribute("ordenSelect", service.getOrden().toString());
						break;
					}
				}
			}
		}else{
			if(services!=null&&services.size()>0){
				getRequest().setAttribute("primerServicio", services.get(0).getIdService());
				getRequest().setAttribute("ordenSelect", "1");
			}
		}
		
		forward.addActionOutput("services",services);
		//getRequest().setAttribute("productSelect", idProduct);
		return forward;
	}
	
	/**
	 * Este m\u00E9todo nos proporciona los servicios que se muestran al inicio
	 * del cotizador atravez de Ajax, se usa cuando el usuario regresa al paso 1
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "services.jsp") })
	public Forward getServices(CotizadorForm form) throws Exception {
		Forward forward = new Forward("success");
		
		List<Service> services = getCotizadorService().findAllServices();
		for(Service service: services){
			if(service.getIdService().longValue() == form.getIdService().longValue()){
				forward.addActionOutput("ordenSelect", service.getOrden());
				break;
			}
		}
		forward.addActionOutput("services",services);
		return forward;
	}
	
	/**
	 * Este m\u00E9todo es ejecutado por Ajax y nos muestra los productos del servicio
	 * Solicitado
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "products.jsp") })
	public Forward searchProducts(CotizadorForm form) throws Exception{
		Forward forward = new Forward("success");
			
		forward.addActionOutput("products", getCotizadorService().findProductsByService(form.getIdService()));
		forward.addActionOutput("productSelect", form.getIdProduct()!=null?form.getIdProduct():"");
		return forward;
	}
	
	/**
	 * Este m\u00E9todo regresa los upgrades de un producto
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "products.jsp") })
	public Forward findUpgradesProduct(CotizadorForm form) throws Exception{
		Forward forward = new Forward("success");
		
		List<UpgradesProd> upgrades = getCotizadorService().findUpgradesProductByIdProduct(form.getIdProduct());
		List<ProductService> products = new ArrayList<ProductService>();
		
		if(upgrades!= null && upgrades.size()>0){
			for(UpgradesProd up : upgrades){
				ProductService product = getCotizadorService().findProductServiceById(up.getId().getIdUpgradesProd());
				products.add(product);
			}
			seleccionado = getCotizadorService().findProductServiceById(form.getIdProduct());
			forward.addActionOutput("product", seleccionado);
			forward.addActionOutput("products", products);
			
		}else{
			forward = null;
		}
		
		return forward;
	}
	
	/**
	 * Este m\u00E9todo regresa las opciones para crecer un combo 
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "mejoras.jsp") })
	public Forward findCreceProducts(CotizadorForm form) throws Exception{
		Forward forward = new Forward("success");
				
		List<CreceProd> creceProducts = getCotizadorService().findCreceProductByIdProduct(form.getIdProduct());
		Map<String, List<ProductService>> mejoras = new HashMap<String, List<ProductService>>();
		
		for(CreceProd cr : creceProducts){
			String grupo = cr.getCprGroup();
			ProductService product = getCotizadorService().findProductServiceById(cr.getCprIdUpgradesprod());
			product.setGrupo(grupo);
			
			if(StringUtils.isNotEmpty(grupo)){
				if(mejoras.containsKey(grupo)){
					if(!mejoras.get(grupo).contains(product)){
						mejoras.get(grupo).add(product);
					}
				}else{
					List<ProductService> prodMejora = new ArrayList<ProductService>();
					prodMejora.add(product);
					mejoras.put(grupo, prodMejora);
				}
			}
		}
		
		
		seleccionado = getCotizadorService().findProductServiceById(form.getIdProduct());
		forward.addActionOutput("product", seleccionado);
		getRequest().setAttribute("mejoras", mejoras.values());
		
		if(mejoras.values().isEmpty()){
			return null;
		}
		
		return forward;
	}
	
	
	/**
	 * Este m\u00E9todo busca un producto por su Id
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "product.jsp") })
	public Forward findProduct(CotizadorForm form) throws Exception{
		Forward forward = new Forward("success");
		
		seleccionado = getCotizadorService().findProductServiceById(form.getIdProduct());
		forward.addActionOutput("product", seleccionado);
		return forward;
	}
	
	
	/**
	 * Este m\u00E9todo buscar todos los productos seleccionados 
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "productosSeleccionados.jsp") })
	public Forward showProductsSelected(CotizadorForm form) throws Exception{
		Forward forward = new Forward("success");
		forward.addActionOutput("products", findProductsSelected(form.getIdProduct()));
		return forward;
	}
	
	/**
	 * Este m\u00E9todo regresa los extras de un producto
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "extras.jsp") })
	public Forward findExtrasProduct(CotizadorForm form) throws Exception{
		Forward forward = new Forward("success");
		
		List<UpgradesExtra> upgrades = getCotizadorService().findUpgradesExtraByIdProduct(form.getIdProduct());
		List<Extra> extras = new ArrayList<Extra>();
		for(UpgradesExtra ex : upgrades){
			Extra e = getCotizadorService().findExtraById(ex.getId().getIdProdExtra());
			if(e.getNumber()>1){
				List<Integer> numbers = new ArrayList<Integer>();
				for(Integer i=0;i<e.getNumber();i++){
					numbers.add(i+1);
				}
				e.setNumbers(numbers);
			}else{
				e.setNumbers(null);
			}
			
			if(ex.getDepende()!= null && !ex.getDepende().isEmpty()){
				e.setDepende(ex.getDepende());
			}
			extras.add(e);
		}
		
		forward.addActionOutput("productsSelected", findProductsSelected(form.getIdProduct()));
		forward.addActionOutput("extras", extras);
		return forward;
	}
	
	/**
	 * Este m\u00E9todo agrega a la sesion un extra para el producto seleccionado
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "productJson.jsp") })
	public Forward addExtra(CotizadorForm form) throws Exception{
		Forward forward = new Forward("success");
		Extra extra = getCotizadorService().findExtraById(form.getIdExtra());
		if(seleccionado != null){
			extra.setSelectedNumber(form.getNumber());
			seleccionado.addExtra(extra);
		}
		
		/*String descripcion="";
		StringTokenizer st = new StringTokenizer(seleccionado.getDescription(),"|");
		while(st.hasMoreTokens()){
			descripcion = descripcion.concat("<span class=\"color-orange\"> > </span>"+st.nextToken()+"<br/>");
		}
		
		seleccionado.setDescriptionFormato(descripcion);*/
		forward.addActionOutput("product", seleccionado);
		return forward;
	}
	
	/**
	 * Este m\u00E9todo remueve de la sesion un extra para el producto seleccionado
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "productJson.jsp") })
	public Forward removeExtra(CotizadorForm form) throws Exception{
		Forward forward = new Forward("success");
		Extra extra = new Extra(form.getIdExtra());
		if(seleccionado != null){
			seleccionado.removeExtra(extra);
		}
		forward.addActionOutput("product", seleccionado);
		return forward;
	}
	
	/**
	 * Este m\u00E9todo muestra el formulario del solicitante
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "solicitud.jsp") })
	public Forward showSolicitud(CotizadorForm form) throws Exception{
		Forward forward = new Forward("success");
			
		forward.addActionOutput("form", new CotizadorForm(0l,0l,0l));
		
		return forward;
		
	}
	
	/**
	 * Este m\u00E9todo agrega a la sesion un extra para el producto seleccionado
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "productJson.jsp") })
	public Forward addMejora(CotizadorForm form) throws Exception{
		Forward forward = new Forward("success");
		
		ProductService mejora = getCotizadorService().findProductServiceById(form.getIdProduct());
		
		if(seleccionado != null){
			mejora.setGrupo(form.getGroup().toString());
			seleccionado.addMejora(mejora);
		}
		
		forward.addActionOutput("product", seleccionado);
		return forward;
	}
	
	private List<ProductService> findProductsSelected(Long idProduct) throws Exception{
		
		if(seleccionado == null){
			seleccionado = getCotizadorService().findProductServiceById(idProduct);
		}
		
		List<ProductService> seleccionados = new ArrayList<ProductService>();
		seleccionados.add(seleccionado);
		if(seleccionado.getMejoras()!=null && seleccionado.getMejoras().size()>0){
			for(ProductService product : seleccionado.getMejoras().values()){
				seleccionados.add(product);
			}
		}
		
		return seleccionados;
	}

	/**
	 * Este m\u00E9todo guarda la informaci\u00F3n del solicitante
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@Jpf.Action(forwards = { @Jpf.Forward(name = "success", path = "saved.jsp") })
   	public Forward saveInformation(CotizadorForm form) throws Exception{
		Forward forward = new Forward("success");
		Boolean isValid = true;
		
		//Valida que se teclee correcto lo del reCaptcha
		if(!ReCaptchaUtil.isValido(getRequest())){
		   forward.addActionOutput("success", false);
		   forward.addActionOutput("errors", new ErrorField("recaptcha","El Texto no coincide con la imagen"));
		   isValid = false;
		} else {	
			Integer idLeadType = 4;
			//validaciones
			Map<String,String> map = form.getFields();
			map.put("leadType", idLeadType.toString());
			List<ErrorField> errors = getLeadService().validateField(map);
			if (isValid){
				if(errors.size()==0){
					map.put("idStatus", "0");
					
					StringBuffer sb = new StringBuffer();
					sb.append("El usuario quiere contratar : "+seleccionado.getName()+", "+seleccionado.getDescription());
					for(Extra extra :seleccionado.getExtras().values()){
						sb.append(", "+extra.getName());
					}
					sb.append(" con un costo de $"+seleccionado.getTotal());
					map.put("Fees", sb.toString());
					
					getLeadService().saveLead(map);
						
					//Enviar correo
					
					//guardar correo y cuenta de usuario
					
				}
				  
				if(errors.size()>0){
					forward.addActionOutput("success", false);
					forward.addActionOutput("errors", errors);
				} else {
					forward.addActionOutput("success", true);
					forward.addActionOutput("msg", "Guardado con \u00E9xito");
				}
			} 
		}
		return forward;
		  
	}
	
	
	@Jpf.FormBean
	public static class CotizadorForm implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		private Integer number;
		private Long idService;
		private Long idProduct;
		private Long idExtra;
		private Long group;
		private String nombre;
		private String apellidoPaterno;
		private String apellidoMaterno;
		private String rfc;
		private String identificacion;
		private String email;
		private String telCasa;
		private String telOficina;
		private String calle;
		private String numeroExterior;
		private String numeroInterior;
		private String entreCalles;
		private String colonia;
		private String cp;
		private String delegacion;
		private String estado;
		private String calleFactura;
		private String numeroExteriorFactura;
		private String numeroInteriorFactura;
		private String entreCallesFactura;
		private String coloniaFactura;
		private String cpFactura;
		private String delegacionFactura;
		private String estadoFactura;
		private String leadType;
		private String challenge;
		private String uresponse;
		
		public CotizadorForm(){
			
		}
		public CotizadorForm(Long idService,Long idProduct,Long idExtra){
			this.idService = idService;
			this.idProduct = idProduct;
			this.idExtra = idExtra;
		}
		public Long getIdService() {
			return idService;
		}
		public void setIdService(Long idService) {
			this.idService = idService;
		}
		public Long getIdProduct() {
			return idProduct;
		}
		public void setIdProduct(Long idProduct) {
			this.idProduct = idProduct;
		}
		public Long getIdExtra() {
			return idExtra;
		}
		public void setIdExtra(Long idExtra) {
			this.idExtra = idExtra;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public String getApellidoPaterno() {
			return apellidoPaterno;
		}
		public void setApellidoPaterno(String apellidoPaterno) {
			this.apellidoPaterno = apellidoPaterno;
		}
		public String getApellidoMaterno() {
			return apellidoMaterno;
		}
		public void setApellidoMaterno(String apellidoMaterno) {
			this.apellidoMaterno = apellidoMaterno;
		}
		public String getRfc() {
			return rfc;
		}
		public void setRfc(String rfc) {
			this.rfc = rfc;
		}
		public String getIdentificacion() {
			return identificacion;
		}
		public void setIdentificacion(String identificacion) {
			this.identificacion = identificacion;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getCalle() {
			return calle;
		}
		public void setCalle(String calle) {
			this.calle = calle;
		}
		public String getNumeroExterior() {
			return numeroExterior;
		}
		public void setNumeroExterior(String numeroExterior) {
			this.numeroExterior = numeroExterior;
		}
		public String getNumeroInterior() {
			return numeroInterior;
		}
		public void setNumeroInterior(String numeroInterior) {
			this.numeroInterior = numeroInterior;
		}
		public String getEntreCalles() {
			return entreCalles;
		}
		public void setEntreCalles(String entreCalles) {
			this.entreCalles = entreCalles;
		}
		public String getColonia() {
			return colonia;
		}
		public void setColonia(String colonia) {
			this.colonia = colonia;
		}
		public String getCp() {
			return cp;
		}
		public void setCp(String cp) {
			this.cp = cp;
		}
		public String getDelegacion() {
			return delegacion;
		}
		public void setDelegacion(String delegacion) {
			this.delegacion = delegacion;
		}
		public String getEstado() {
			return estado;
		}
		public void setEstado(String estado) {
			this.estado = estado;
		}
		public String getCalleFactura() {
			return calleFactura;
		}
		public void setCalleFactura(String calleFactura) {
			this.calleFactura = calleFactura;
		}
		public String getNumeroExteriorFactura() {
			return numeroExteriorFactura;
		}
		public void setNumeroExteriorFactura(String numeroExteriorFactura) {
			this.numeroExteriorFactura = numeroExteriorFactura;
		}
		public String getNumeroInteriorFactura() {
			return numeroInteriorFactura;
		}
		public void setNumeroInteriorFactura(String numeroInteriorFactura) {
			this.numeroInteriorFactura = numeroInteriorFactura;
		}
		public String getEntreCallesFactura() {
			return entreCallesFactura;
		}
		public void setEntreCallesFactura(String entreCallesFactura) {
			this.entreCallesFactura = entreCallesFactura;
		}
		public String getColoniaFactura() {
			return coloniaFactura;
		}
		public void setColoniaFactura(String coloniaFactura) {
			this.coloniaFactura = coloniaFactura;
		}
		public String getCpFactura() {
			return cpFactura;
		}
		public void setCpFactura(String cpFactura) {
			this.cpFactura = cpFactura;
		}
		public String getDelegacionFactura() {
			return delegacionFactura;
		}
		public void setDelegacionFactura(String delegacionFactura) {
			this.delegacionFactura = delegacionFactura;
		}
		public String getEstadoFactura() {
			return estadoFactura;
		}
		public void setEstadoFactura(String estadoFactura) {
			this.estadoFactura = estadoFactura;
		}
		public String getLeadType() {
			return leadType;
		}
		public void setLeadType(String leadType) {
			this.leadType = leadType;
		}
		
		@SuppressWarnings("unchecked")
		public Map<String,String> getFields()throws Exception{
			Map<String,String> map = new HashMap<String,String>();
			
			map.put("Nombre",this.nombre!=null?this.nombre.toUpperCase(Locale.ENGLISH):this.nombre);
			map.put("PrimerApellido",this.apellidoPaterno!=null?this.apellidoPaterno.toUpperCase(Locale.ENGLISH):this.apellidoPaterno);
			map.put("SegundoApellido",this.apellidoMaterno!=null?this.apellidoMaterno.toUpperCase(Locale.ENGLISH):this.apellidoMaterno);
			map.put("RFC",this.rfc!=null?this.rfc.toUpperCase(Locale.ENGLISH):this.rfc);
			map.put("Identificacion",this.identificacion!=null?this.identificacion.toUpperCase(Locale.ENGLISH):this.identificacion);
			map.put("Email",this.email);
			map.put("TelCasa",this.telCasa);
			map.put("TelOfna",this.telOficina);
			map.put("Calle",this.calle!=null?this.calle.toUpperCase(Locale.ENGLISH):this.calle);
			map.put("NoExt",this.numeroExterior);
			map.put("NoInt",this.numeroInterior);
			map.put("EntreCalles",this.entreCalles!=null?this.entreCalles.toUpperCase(Locale.ENGLISH):this.entreCalles);
			map.put("Colonia",this.colonia!=null?this.colonia.toUpperCase(Locale.ENGLISH):this.colonia);
			map.put("CP",this.cp);
			map.put("Delegacion",this.delegacion!=null?this.delegacion.toUpperCase(Locale.ENGLISH):this.delegacion);
			map.put("Estado",this.estado!=null?this.estado.toUpperCase(Locale.ENGLISH):this.estado);
			map.put("CalleFact",this.calleFactura!=null?this.calleFactura.toUpperCase(Locale.ENGLISH):this.calleFactura);
			map.put("NoExtFact",this.numeroExteriorFactura);
			map.put("NoIntFact",this.numeroInteriorFactura);
			map.put("EntreCallesFact",this.entreCallesFactura!=null?this.entreCallesFactura.toUpperCase(Locale.ENGLISH):this.entreCallesFactura);
			map.put("ColoniaFact",this.coloniaFactura!=null?this.coloniaFactura.toUpperCase(Locale.ENGLISH):this.coloniaFactura);
			map.put("CPFact",this.cpFactura);
			map.put("DelegacionFact",this.delegacionFactura!=null?this.delegacionFactura.toUpperCase(Locale.ENGLISH):this.delegacionFactura);
			map.put("EstadoFact",this.estadoFactura!=null?this.estadoFactura.toUpperCase(Locale.ENGLISH):this.estadoFactura);
			map.put("leadType",this. leadType);
			return map;
		}
		public String getTelCasa() {
			return telCasa;
		}
		public void setTelCasa(String telCasa) {
			this.telCasa = telCasa;
		}
		public String getTelOficina() {
			return telOficina;
		}
		public void setTelOficina(String telOficina) {
			this.telOficina = telOficina;
		}
		public Integer getNumber() {
			return number;
		}
		public void setNumber(Integer number) {
			this.number = number;
		}
		public String getChallenge() {
			return challenge;
		}
		public void setChallenge(String challenge) {
			this.challenge = challenge;
		}
		public String getUresponse() {
			return uresponse;
		}
		public void setUresponse(String uresponse) {
			this.uresponse = uresponse;
		}
		public Long getGroup() {
			return group;
		}
		public void setGroup(Long group) {
			this.group = group;
		}
	}
	
	public ICotizadorService getCotizadorService() {
		if(cotizadorService==null){
			ApplicationContext context = (ApplicationContext)getRequest().getSession().getServletContext().getAttribute(
					WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			cotizadorService = (ICotizadorService)context.getBean("CotizadorService");
			
		}
		return cotizadorService;
	}

	public ILeadService getLeadService() {
		if(leadService==null){
			ApplicationContext context = (ApplicationContext)getRequest().getSession().getServletContext().getAttribute(
					WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			leadService = (ILeadService)context.getBean("LeadService");
			
		}
		return leadService;
	}
	
	public void setCotizadorService(ICotizadorService cotizadorService) {
		this.cotizadorService = cotizadorService;
	}
	
}
