package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CVCHANNEL_PACK database table.
 * 
 * @author BEA Workshop
 */
public class CvchannelPack  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private CvchannelPackPK compId;
	private Cvchannel cvchannel;
	private Cvpack cvpack;

    public CvchannelPack() {
    }

	public CvchannelPackPK getCompId() {
		return this.compId;
	}
	public void setCompId(CvchannelPackPK compId) {
		this.compId = compId;
	}

	//bi-directional many-to-one association to Cvchannel
	public Cvchannel getCvchannel() {
		return this.cvchannel;
	}
	public void setCvchannel(Cvchannel cvchannel) {
		this.cvchannel = cvchannel;
	}

	//bi-directional many-to-one association to Cvpack
	public Cvpack getCvpack() {
		return this.cvpack;
	}
	public void setCvpack(Cvpack cvpack) {
		this.cvpack = cvpack;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvchannelPack)) {
			return false;
		}
		CvchannelPack castOther = (CvchannelPack)other;
		return new EqualsBuilder()
			.append(this.getCompId(), castOther.getCompId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getCompId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("compId", getCompId())
			.toString();
	}
}