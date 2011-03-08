package com.cablevision.vo;

import java.io.Serializable;

public class CvContenido implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dDocName;
	private String name;
	private String content;
	private String dID;
	
	public CvContenido(){
		this.dDocName = "";
		this.name = "";
		this.content = "";
		this.dID = "";
	}
	
	public CvContenido(String dDocName, String name, String content){
		this.dDocName = dDocName;
		this.name = name;
		this.content = content;
	}
	
	public String getDDocName() {
		return dDocName;
	}
	public void setDDocName(String docName) {
		dDocName = docName;
	}
	public String getDID() {
		return dID;
	}
	public void setDID(String did) {
		dID = did;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
