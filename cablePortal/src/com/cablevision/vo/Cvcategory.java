package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CVCATEGORIES database table.
 * 
 * @author BEA Workshop
 */
public class Cvcategory  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long idcategory;
	private String name;
	private java.util.Set<Cvchannel> cvchannels;

    public Cvcategory() {
    }

	public Long getIdcategory() {
		return this.idcategory;
	}
	public void setIdcategory(Long idcategory) {
		this.idcategory = idcategory;
	}

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	//bi-directional many-to-one association to Cvchannel
	public java.util.Set<Cvchannel> getCvchannels() {
		return this.cvchannels;
	}
	public void setCvchannels(java.util.Set<Cvchannel> cvchannels) {
		this.cvchannels = cvchannels;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Cvcategory)) {
			return false;
		}
		Cvcategory castOther = (Cvcategory)other;
		return new EqualsBuilder()
			.append(this.getIdcategory(), castOther.getIdcategory())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getIdcategory())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("idcategory", getIdcategory())
			.toString();
	}
}