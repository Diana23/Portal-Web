package com.cablevision.vo;
import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * The persistent class for the cvleadtypes database table.
 * 
 * @author BEA Workshop
 */
public class CvLeadType  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long cltIdLeadtype;
	private Long cltCloseaccount;
	private String cltNameType;
	private long cltPpeHdDeType;
	private Long cltSendemail;
	private String cltTemplatelead;
	private String cltTemplateleadopen;
	private Long cltValidate;
	private String cltGrupo;
	
    public CvLeadType() {
    }

    public CvLeadType(Long cltIdLeadtype) {
    	this.cltIdLeadtype = cltIdLeadtype;
    }
    
    public Long getCltIdLeadtype() {
		return this.cltIdLeadtype;
	}
	public void setCltIdLeadtype(Long cltIdLeadtype) {
		this.cltIdLeadtype = cltIdLeadtype;
	}

	public Long getCltCloseaccount() {
		return this.cltCloseaccount;
	}
	public void setCltCloseaccount(Long cltCloseaccount) {
		this.cltCloseaccount = cltCloseaccount;
	}

	public String getCltNameType() {
		return this.cltNameType;
	}
	public void setCltNameType(String cltNameType) {
		this.cltNameType = cltNameType;
	}

	public long getCltPpeHdDeType() {
		return this.cltPpeHdDeType;
	}
	public void setCltPpeHdDeType(long cltPpeHdDeType) {
		this.cltPpeHdDeType = cltPpeHdDeType;
	}

	public Long getCltSendemail() {
		return this.cltSendemail;
	}
	public void setCltSendemail(Long cltSendemail) {
		this.cltSendemail = cltSendemail;
	}

	public String getCltTemplatelead() {
		return this.cltTemplatelead;
	}
	public void setCltTemplatelead(String cltTemplatelead) {
		this.cltTemplatelead = cltTemplatelead;
	}

	public String getCltTemplateleadopen() {
		return this.cltTemplateleadopen;
	}
	public void setCltTemplateleadopen(String cltTemplateleadopen) {
		this.cltTemplateleadopen = cltTemplateleadopen;
	}

	public Long getCltValidate() {
		return this.cltValidate;
	}
	public void setCltValidate(Long cltValidate) {
		this.cltValidate = cltValidate;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvLeadType)) {
			return false;
		}
		CvLeadType castOther = (CvLeadType)other;
		return new EqualsBuilder()
			.append(this.getCltIdLeadtype(), castOther.getCltIdLeadtype())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getCltIdLeadtype())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("cltIdLeadtype", getCltIdLeadtype())
			.toString();
	}

	public String getCltGrupo() {
		return cltGrupo;
	}

	public void setCltGrupo(String cltGrupo) {
		this.cltGrupo = cltGrupo;
	}
}