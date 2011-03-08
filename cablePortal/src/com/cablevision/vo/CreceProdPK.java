package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The primary key class for the CV_CRECEPROD database table.
 * 
 * @author BEA Workshop
 */
public class CreceProdPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long cprId;
	private ProductService productService;

    public CreceProdPK() {
    }

	public Long getCprId() {
		return this.cprId;
	}
	public void setCprId(Long cprId) {
		this.cprId = cprId;
	}

	public ProductService getProductService() {
		return this.productService;
	}
	public void setProductService(ProductService idprodservice) {
		this.productService = idprodservice;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CreceProdPK)) {
			return false;
		}
		CreceProdPK castOther = (CreceProdPK)other;
		return new EqualsBuilder()
			.append(this.getCprId(), castOther.getCprId())
			.append(this.getProductService(), castOther.getProductService())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getCprId())
			.append(getProductService())
			.toHashCode();
    }

	public String toString() {
		return new ToStringBuilder(this)
			.append("cprId", getCprId())
			.append("productService", getProductService())
			.toString();
	}
}