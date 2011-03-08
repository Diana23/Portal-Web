package com.cablevision.vo;
import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * The persistent class for the cvfields database table.
 * 
 * @author BEA Workshop
 */
public class CvField  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long cfsIdfield;
	private String cfsErrormsgfield;
	private String cfsIsrequiredfield;
	private String cfsMaskfield;
	private String cfsNamealias;
	private String cfsNamefield;
	private String cfsTypefield;
	private String cfsScripts;
	private String cfsAttrvalues;

    public CvField() {
    }

	public Long getCfsIdfield() {
		return this.cfsIdfield;
	}
	public void setCfsIdfield(Long cfsIdfield) {
		this.cfsIdfield = cfsIdfield;
	}

	public String getCfsErrormsgfield() {
		return this.cfsErrormsgfield;
	}
	public void setCfsErrormsgfield(String cfsErrormsgfield) {
		this.cfsErrormsgfield = cfsErrormsgfield;
	}

	public String getCfsIsrequiredfield() {
		return this.cfsIsrequiredfield;
	}
	public void setCfsIsrequiredfield(String cfsIsrequiredfield) {
		this.cfsIsrequiredfield = cfsIsrequiredfield;
	}

	public String getCfsMaskfield() {
		return this.cfsMaskfield;
	}
	public void setCfsMaskfield(String cfsMaskfield) {
		this.cfsMaskfield = cfsMaskfield;
	}

	public String getCfsNamealias() {
		return this.cfsNamealias;
	}
	public void setCfsNamealias(String cfsNamealias) {
		this.cfsNamealias = cfsNamealias;
	}

	public String getCfsNamefield() {
		return this.cfsNamefield;
	}
	public void setCfsNamefield(String cfsNamefield) {
		this.cfsNamefield = cfsNamefield;
	}

	public String getCfsTypefield() {
		return this.cfsTypefield;
	}
	public void setCfsTypefield(String cfsTypefield) {
		this.cfsTypefield = cfsTypefield;
	}

	public String getCfsScripts() {
		return cfsScripts;
	}

	public void setCfsScripts(String cfsScripts) {
		this.cfsScripts = cfsScripts;
	}

	public String getCfsAttrvalues() {
		return cfsAttrvalues;
	}

	public void setCfsAttrvalues(String cfsAttrvalues) {
		this.cfsAttrvalues = cfsAttrvalues;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvField)) {
			return false;
		}
		CvField castOther = (CvField)other;
		return new EqualsBuilder()
			.append(this.getCfsIdfield(), castOther.getCfsIdfield())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getCfsIdfield())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("cfsIdfield", getCfsIdfield())
			.toString();
	}
}