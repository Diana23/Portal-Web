package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The primary key class for the UPGRADESPROD database table.
 * 
 * @author BEA Workshop
 */
public class UpgradesProdPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private ProductService productService;
	private Long idUpgradesProd;

    public UpgradesProdPK() {
    }

	public ProductService getProductService() {
		return this.productService;
	}
	public void setProductService(ProductService idprodservice) {
		this.productService = idprodservice;
	}

	public Long getIdUpgradesProd() {
		return this.idUpgradesProd;
	}
	public void setIdUpgradesProd(Long idupgradesprod) {
		this.idUpgradesProd = idupgradesprod;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UpgradesProdPK)) {
			return false;
		}
		UpgradesProdPK castOther = (UpgradesProdPK)other;
		return new EqualsBuilder()
			.append(this.getProductService(), castOther.getProductService())
			.append(this.getIdUpgradesProd(), castOther.getIdUpgradesProd())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getProductService())
			.append(getIdUpgradesProd())
			.toHashCode();
    }

	public String toString() {
		return new ToStringBuilder(this)
			.append("idprodservice", getProductService())
			.append("idupgradesprod", getIdUpgradesProd())
			.toString();
	}
}