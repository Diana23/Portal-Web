package com.cablevision.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The primary key class for the CV_NEWSLETTER_CATEGORIAUSUARIO database table.
 * 
 * @author BEA Workshop
 */
public class CvNewsletterCategoriausuarioPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer ncuIdcategoria;
	private Long ncuIdusuario;

    public CvNewsletterCategoriausuarioPK() {
    }

	public Integer getNcuIdcategoria() {
		return this.ncuIdcategoria;
	}
	public void setNcuIdcategoria(Integer ncuIdcategoria) {
		this.ncuIdcategoria = ncuIdcategoria;
	}

	public Long getNcuIdusuario() {
		return this.ncuIdusuario;
	}
	public void setNcuIdusuario(Long ncuIdusuario) {
		this.ncuIdusuario = ncuIdusuario;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvNewsletterCategoriausuarioPK)) {
			return false;
		}
		CvNewsletterCategoriausuarioPK castOther = (CvNewsletterCategoriausuarioPK)other;
		return new EqualsBuilder()
			.append(this.getNcuIdcategoria(), castOther.getNcuIdcategoria())
			.append(this.getNcuIdusuario(), castOther.getNcuIdusuario())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getNcuIdcategoria())
			.append(getNcuIdusuario())
			.toHashCode();
    }

	public String toString() {
		return new ToStringBuilder(this)
			.append("ncuIdcategoria", getNcuIdcategoria())
			.append("ncuIdusuario", getNcuIdusuario())
			.toString();
	}
}