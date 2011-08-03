package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CV_CRECEPROD database table.
 * 
 * @author BEA Workshop
 */
public class CreceProd  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private CreceProdPK compId;
	private String cprGroup;
	private Long cprIdUpgradesprod;
	private Integer cprPriority;

    public CreceProd() {
    }

	public CreceProdPK getCompId() {
		return this.compId;
	}
	public void setCompId(CreceProdPK compId) {
		this.compId = compId;
	}

	public String getCprGroup() {
		return this.cprGroup;
	}
	public void setCprGroup(String cprGroup) {
		this.cprGroup = cprGroup;
	}

	public Long getCprIdUpgradesprod() {
		return this.cprIdUpgradesprod;
	}
	public void setCprIdUpgradesprod(Long cprIdUpgradesprod) {
		this.cprIdUpgradesprod = cprIdUpgradesprod;
	}

	public Integer getCprPriority() {
		return this.cprPriority;
	}
	public void setCprPriority(Integer cprPriority) {
		this.cprPriority = cprPriority;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CreceProd)) {
			return false;
		}
		CreceProd castOther = (CreceProd)other;
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