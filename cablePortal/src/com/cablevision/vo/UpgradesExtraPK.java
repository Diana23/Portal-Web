package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The primary key class for the UPGRADESEXTRA database table.
 * 
 * @author BEA Workshop
 */
public class UpgradesExtraPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long idProdExtra;
	private ProductService productService;

    public UpgradesExtraPK() {
    }

	public Long getIdProdExtra() {
		return this.idProdExtra;
	}
	public void setIdProdExtra(Long idprodextra) {
		this.idProdExtra = idprodextra;
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
		if (!(other instanceof UpgradesExtraPK)) {
			return false;
		}
		UpgradesExtraPK castOther = (UpgradesExtraPK)other;
		return new EqualsBuilder()
			.append(this.getIdProdExtra(), castOther.getIdProdExtra())
			.append(this.getProductService(), castOther.getProductService())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getIdProdExtra())
			.append(getProductService())
			.toHashCode();
    }

	public String toString() {
		return new ToStringBuilder(this)
			.append("idprodextra", getIdProdExtra())
			.append("idprodservice", getProductService())
			.toString();
	}
}