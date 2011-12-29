package com.cablevision.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class CvMcafeeHistorico implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5368104290452636828L;
	private Long mcaIdHistorico;
	private Long mcaAccountNumber; 
	private String mcaCvStatus; 
	private String mcaMotivo; 
	private Timestamp mcaFechaInicio; 
	private Timestamp mcaFechaFin;
	private String mcaReference;
	private String mcaTipoProducto;
	
	public Long getMcaIdHistorico() {
		return mcaIdHistorico;
	}
	public void setMcaIdHistorico(Long mcaIdHistorico) {
		this.mcaIdHistorico = mcaIdHistorico;
	}
	public Long getMcaAccountNumber() {
		return mcaAccountNumber;
	}
	public void setMcaAccountNumber(Long mcaAccountNumber) {
		this.mcaAccountNumber = mcaAccountNumber;
	}
	public String getMcaCvStatus() {
		return mcaCvStatus;
	}
	public void setMcaCvStatus(String mcaCvStatus) {
		this.mcaCvStatus = mcaCvStatus;
	}
	public String getMcaMotivo() {
		return mcaMotivo;
	}
	public void setMcaMotivo(String mcaMotivo) {
		this.mcaMotivo = mcaMotivo;
	}
	public Timestamp getMcaFechaInicio() {
		return mcaFechaInicio;
	}
	public void setMcaFechaInicio(Timestamp mcaFechaInicio) {
		this.mcaFechaInicio = mcaFechaInicio;
	}
	public Timestamp getMcaFechaFin() {
		return mcaFechaFin;
	}
	public void setMcaFechaFin(Timestamp mcaFechaFin) {
		this.mcaFechaFin = mcaFechaFin;
	}
	
	public String getMcaReference() {
		return mcaReference;
	}
	public void setMcaReference(String mcaReference) {
		this.mcaReference = mcaReference;
	}
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CvMcafeeHistorico)) {
			return false;
		}
		CvMcafeeHistorico castOther = (CvMcafeeHistorico)other;
		return new EqualsBuilder()
			.append(this.getMcaIdHistorico(), castOther.getMcaIdHistorico())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getMcaIdHistorico())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("mcaIdHistorico", getMcaIdHistorico())
			.toString();
	}
	public String getMcaTipoProducto() {
		return mcaTipoProducto;
	}
	public void setMcaTipoProducto(String mcaTipoProducto) {
		this.mcaTipoProducto = mcaTipoProducto;
	}

}
