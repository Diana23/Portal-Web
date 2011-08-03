package com.cablevision.vo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CV_CATEGORIANEWSLETTER database table.
 * 
 * @author BEA Workshop
 */
public class CvCategorianewsletter  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Long cnlId;
	private String cnlNombre;
	private Long cnlPadre;
    private List<CvCategorianewsletter> hijos = new ArrayList<CvCategorianewsletter>();
    private Long padreId;
    private String padreName;
    
    public CvCategorianewsletter() {
    }

	public Long getCnlId() {
		return this.cnlId;
	}
	public void setCnlId(Long cnlId) {
		this.cnlId = cnlId;
	}

	public String getCnlNombre() {
		return this.cnlNombre;
	}
	public void setCnlNombre(String cnlNombre) {
		this.cnlNombre = cnlNombre;
	}

	
	
	public Long getCnlPadre() {
		return cnlPadre;
	}

	public void setCnlPadre(Long cnlPadre) {
		this.cnlPadre = cnlPadre;
	}


	
	public List<CvCategorianewsletter> getHijos() {
		return hijos;
	}

	public void setHijos(List<CvCategorianewsletter> hijos) {
		this.hijos = hijos;
	}
	
	public Long getPadreId() {
		return padreId;
	}

	public void setPadreId(Long padreId) {
		this.padreId = padreId;
	}

	public String getPadreName() {
		return padreName;
	}

	public void setPadreName(String padreName) {
		this.padreName = padreName;
	}
	
	

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvCategorianewsletter)) {
			return false;
		}
		CvCategorianewsletter castOther = (CvCategorianewsletter)other;
		return new EqualsBuilder()
			.append(this.getCnlId(), castOther.getCnlId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getCnlId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("cnlId", getCnlId())
			.toString();
	}
}