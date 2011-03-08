package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CVCHANNELS database table.
 * 
 * @author BEA Workshop
 */
public class Cvchannel  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long idchannel;
	private String description;
	private boolean hd = false;
	private boolean international = false;
	private String logo;
	private String name;
	private java.util.Set<Cvpack> cvchannelPacks;
	private Cvcategory cvcategory;

    public Cvchannel() {
    }

	public Long getIdchannel() {
		return this.idchannel;
	}
	public void setIdchannel(Long idchannel) {
		this.idchannel = idchannel;
	}

	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public boolean getHd() {
		return this.hd;
	}
	public void setHd(boolean hd) {
		this.hd = hd;
	}

	public boolean getInternational() {
		return this.international;
	}
	public void setInternational(boolean international) {
		this.international = international;
	}

	public String getLogo() {
		return this.logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	//bi-directional many-to-one association to CvchannelPack
	public java.util.Set<Cvpack> getCvchannelPacks() {
		return this.cvchannelPacks;
	}
	public void setCvchannelPacks(java.util.Set<Cvpack> cvchannelPacks) {
		this.cvchannelPacks = cvchannelPacks;
	}

	//bi-directional many-to-one association to Cvcategory
	public Cvcategory getCvcategory() {
		return this.cvcategory;
	}
	public void setCvcategory(Cvcategory cvcategory) {
		this.cvcategory = cvcategory;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Cvchannel)) {
			return false;
		}
		Cvchannel castOther = (Cvchannel)other;
		return new EqualsBuilder()
			.append(this.getIdchannel(), castOther.getIdchannel())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getIdchannel())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("idchannel", getIdchannel())
			.toString();
	}
}