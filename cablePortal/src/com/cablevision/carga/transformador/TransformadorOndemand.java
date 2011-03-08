package com.cablevision.carga.transformador;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class TransformadorOndemand implements Transformador {

	@Override
	public void transforma(Map<String, String> datos,int index) {
		datos.put("id", datos.get("tipo")+"_"+ Integer.toString(index));
		datos.put("ruletaPrincipal", StringUtils.isNotBlank(datos.get("ruletaPrincipal"))?"true":"false");
		datos.put("ruletaSecundaria", StringUtils.isNotBlank(datos.get("ruletaSecundaria"))?"true":"false");
		
		String categoria = datos.get("categoria");
		
		if(categoria!=null){
			datos.put("categoria", categoria.split("\\\\")[0]);
		}
		
		String rank =  datos.get("rank");
		
		if(rank!=null){
			datos.put("rank", rank.split("\\.")[0]);
		}
	}

}
