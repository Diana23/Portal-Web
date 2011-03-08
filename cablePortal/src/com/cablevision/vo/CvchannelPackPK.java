package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The primary key class for the CVCHANNEL_PACK database table.
 * 
 * @author BEA Workshop
 */
public class CvchannelPackPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long idchannel;
	private Long idpack;

    public CvchannelPackPK() {
    }

	public Long getIdchannel() {
		return this.idchannel;
	}
	public void setIdchannel(Long idchannel) {
		this.idchannel = idchannel;
	}

	public Long getIdpack() {
		return this.idpack;
	}
	public void setIdpack(Long idpack) {
		this.idpack = idpack;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvchannelPackPK)) {
			return false;
		}
		CvchannelPackPK castOther = (CvchannelPackPK)other;
		return new EqualsBuilder()
			.append(this.getIdchannel(), castOther.getIdchannel())
			.append(this.getIdpack(), castOther.getIdpack())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getIdchannel())
			.append(getIdpack())
			.toHashCode();
    }

	public String toString() {
		return new ToStringBuilder(this)
			.append("idchannel", getIdchannel())
			.append("idpack", getIdpack())
			.toString();
	}
}