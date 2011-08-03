package com.cablevision.vo;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the SERVICES database table.
 * 
 * @author BEA Workshop
 */
public class Service  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long idService;
	private String description;
	private String image;
	private String name;
	private Long orden;
	private Boolean activo;
	
	
	private Set<ProductService> products = new HashSet<ProductService>();
	private Set<Extra> extras = new HashSet<Extra>();

    public Service() {
    }

	public Long getIdService() {
		return this.idService;
	}
	public void setIdService(Long idservice) {
		this.idService = idservice;
	}

	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Service)) {
			return false;
		}
		Service castOther = (Service)other;
		return new EqualsBuilder()
			.append(this.getIdService(), castOther.getIdService())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getIdService())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("idService", getIdService())
			.toString();
	}

	public Set<ProductService> getProducts() {
		return products;
	}

	public void setProducts(Set<ProductService> products) {
		this.products = products;
	}

	public Set<Extra> getExtras() {
		return extras;
	}

	public void setExtras(Set<Extra> extras) {
		this.extras = extras;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
}