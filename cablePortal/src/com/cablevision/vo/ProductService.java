package com.cablevision.vo;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the PRODUCT_SERVICE database table.
 * 
 * @author BEA Workshop
 */
public class ProductService  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long idProductService;
	private String description;
	private String descriptionFormato;
	private Boolean hidden;
	private String image;
	private String name;
	private Double normalPrice;
	private Long priority;
	private Double tcPrice;
	private Service service;
	private Double total;
	private Double totalnormal;
	private String grupo;
	private Map<Long,Extra> extras = new HashMap<Long,Extra>();
	private Map<Long,ProductService> mejoras = new HashMap<Long,ProductService>();

    public ProductService() {
    }

    /**
     * Agrega un Extra al producto seleccionado y calcula el nuevo precio
     * @param extra
     */
    public void addExtra(Extra extra){
    	if(extras==null)extras = new HashMap<Long,Extra>();
    	if(extras.containsKey(extra.getIdExtra())){
    		extras.remove(extra.getIdExtra());
    	}
    	extras.put(extra.getIdExtra(), extra);
    	this.calculateTotal();
    }
    
    public void removeExtra(Extra extra){
    	if(extras!=null && extra!=null)
    	if(extras.containsKey(extra.getIdExtra())){
    		extras.remove(extra.getIdExtra());
    		this.calculateTotal();
    	}
    }
    
    /**
     * Agrega un Mejoras al producto seleccionado y calcula el nuevo precio, solo puede tener una mejora por grupo
     * @param extra
     */
    public void addMejora(ProductService mejora){
    	if(mejoras==null)mejoras = new HashMap<Long,ProductService>();
    	if(mejoras.containsKey(Long.parseLong(mejora.getGrupo()))){
    		mejoras.remove(Long.parseLong(mejora.getGrupo()));
    	}
    	mejoras.put(Long.parseLong(mejora.getGrupo()), mejora);
    	
    	this.calculateTotalMejoras();
    }
    
    public void removeMejora(ProductService mejora){
    	if(mejoras!=null && mejora!=null)
    	if(mejoras.containsKey(Long.parseLong(mejora.getGrupo()))){
    		mejoras.remove(Long.parseLong(mejora.getGrupo()));
    		this.calculateTotalMejoras();
    	}
    }
    
    /**
     * Calcula el precio total del producto seleccionado tomando en cuenta
     * los extras que seleccion\u00F3 el usuario
     * @param actual Es el extra que se esta agregando o removiendo 
     */
    private void calculateTotal(){
    	total = this.getTcPrice();
    	totalnormal = this.getNormalPrice();
    	
    	for(Iterator<Long> i = extras.keySet().iterator();i.hasNext();){
    		Extra extra = extras.get(i.next());
    		
    		if(extra.getSelectedNumber()>0){
	    		Double adicionalTc = 0.0;	    	
	    		Double adicionalNormal = 0.0;	   
	    		if(extra.getSelectedNumber()>=4){
	    			adicionalTc = (extra.getTcPriceDv())*((int)(extra.getSelectedNumber()/4));
	    			adicionalNormal = (extra.getNormalPriceDv())*((int)(extra.getSelectedNumber()/4));
	    		}
		    	total += extra.getTcPrice()*extra.getSelectedNumber()+adicionalTc;
		    	totalnormal += extra.getNormalPrice()*extra.getSelectedNumber()+adicionalNormal;
    		}else{
    			total += extra.getTcPrice();
    			totalnormal += extra.getNormalPrice();
    		}
    	}
    }
    
    /**
     * Calcula el precio total del producto seleccionado tomando en cuenta
     * las mejoras que seleccion\u00F3 el usuario
     */
    private void calculateTotalMejoras(){
    	total = this.getTcPrice();
    	totalnormal= this.getNormalPrice();
    	
    	for(Iterator<Long> i = mejoras.keySet().iterator();i.hasNext();){
    		ProductService mejora = mejoras.get(i.next());
    		
    		total += mejora.getTcPrice();
    		totalnormal += mejora.getNormalPrice();
    	}
    }
    
    public Map<Long,Extra> getExtras(){
    	return this.extras;
    }
	public Long getIdProductService() {
		return this.idProductService;
	}
	public void setIdProductService(Long idproductservice) {
		this.idProductService = idproductservice;
	}

	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getHidden() {
		return this.hidden;
	}
	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}
	public String getImage() {
		return this.image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Double getNormalPrice() {
		return this.normalPrice;
	}
	public void setNormalPrice(Double normalPrice) {
		this.normalPrice = normalPrice;
	}

	public Long getPriority() {
		return this.priority;
	}
	public void setPriority(Long priority) {
		this.priority = priority;
	}

	public Double getTcPrice() {
		return this.tcPrice;
	}
	public void setTcPrice(Double tcPrice) {
		this.tcPrice = tcPrice;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ProductService)) {
			return false;
		}
		ProductService castOther = (ProductService)other;
		return new EqualsBuilder()
			.append(this.getIdProductService(), castOther.getIdProductService())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getIdProductService())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("idproductservice", getIdProductService())
			.toString();
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
	public Double getTotalnormal() {
		return totalnormal;
	}

	public void setTotalnormal(Double totalnormal) {
		this.totalnormal = totalnormal;
	}

	public String getDescriptionFormato() {
		return descriptionFormato;
	}

	public void setDescriptionFormato(String descriptionFormato) {
		this.descriptionFormato = descriptionFormato;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public Map<Long, ProductService> getMejoras() {
		return mejoras;
	}

	public void setMejoras(Map<Long, ProductService> mejoras) {
		this.mejoras = mejoras;
	}
	
}