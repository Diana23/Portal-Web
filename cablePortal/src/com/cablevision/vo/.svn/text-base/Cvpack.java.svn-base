package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CVPACKS database table.
 * 
 * @author BEA Workshop
 */
public class Cvpack  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long idpack;
	private String name;
	private java.util.Set<CvchannelPack> cvchannelPacks;

    public Cvpack() {
    }

	public Long getIdpack() {
		return this.idpack;
	}
	public void setIdpack(Long idpack) {
		this.idpack = idpack;
	}

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	//bi-directional many-to-one association to CvchannelPack
	public java.util.Set<CvchannelPack> getCvchannelPacks() {
		return this.cvchannelPacks;
	}
	public void setCvchannelPacks(java.util.Set<CvchannelPack> cvchannelPacks) {
		this.cvchannelPacks = cvchannelPacks;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Cvpack)) {
			return false;
		}
		Cvpack castOther = (Cvpack)other;
		return new EqualsBuilder()
			.append(this.getIdpack(), castOther.getIdpack())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getIdpack())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("idpack", getIdpack())
			.toString();
	}
}