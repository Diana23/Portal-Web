package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the UPGRADESPROD database table.
 * 
 * @author BEA Workshop
 */
public class UpgradesProd  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private UpgradesProdPK id;
	private int priority;

    public UpgradesProd() {
    }

	public UpgradesProdPK getId() {
		return this.id;
	}
	public void setId(UpgradesProdPK compId) {
		this.id = compId;
	}

	public int getPriority() {
		return this.priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UpgradesProd)) {
			return false;
		}
		UpgradesProd castOther = (UpgradesProd)other;
		return new EqualsBuilder()
			.append(this.getId(), castOther.getId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("compId", getId())
			.toString();
	}
}