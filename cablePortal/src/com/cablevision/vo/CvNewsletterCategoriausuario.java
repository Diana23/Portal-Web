package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the CV_NEWSLETTER_CATEGORIAUSUARIO database table.
 * 
 * @author BEA Workshop
 */
public class CvNewsletterCategoriausuario  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private CvNewsletterCategoriausuarioPK compId;
	private CvCategorianewsletter cvCategorianewsletter;

    public CvNewsletterCategoriausuario() {
    }

	public CvNewsletterCategoriausuarioPK getCompId() {
		return this.compId;
	}
	public void setCompId(CvNewsletterCategoriausuarioPK compId) {
		this.compId = compId;
	}

	//bi-directional many-to-one association to CvCategorianewsletter
	public CvCategorianewsletter getCvCategorianewsletter() {
		return this.cvCategorianewsletter;
	}
	public void setCvCategorianewsletter(CvCategorianewsletter cvCategorianewsletter) {
		this.cvCategorianewsletter = cvCategorianewsletter;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvNewsletterCategoriausuario)) {
			return false;
		}
		CvNewsletterCategoriausuario castOther = (CvNewsletterCategoriausuario)other;
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