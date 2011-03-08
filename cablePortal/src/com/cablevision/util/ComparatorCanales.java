package com.cablevision.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cablevision.vo.Cvchannel;
import com.cablevision.vo.Cvpack;

public class ComparatorCanales implements Comparator<Cvchannel> {
	
	private String[] ordenPaquetes;
	
	public ComparatorCanales(){
		String strOrden = ConfigurationHelper.getPropiedad("canales.orden.default");
		if(strOrden!= null && ordenPaquetes == null){
			ordenPaquetes = strOrden.split(",");
		}
	}
	
	public ComparatorCanales(String[] ordenPaquetes){
		this.ordenPaquetes= ordenPaquetes;
	}
	
	@Override
	public int compare(Cvchannel o1, Cvchannel o2) {
		// TODO Auto-generated method stub
		
		Map<Long,Long> matrizO1 = new HashMap<Long,Long>();
		Map<Long,Long> matrizO2 = new HashMap<Long,Long>();
		
		for(Cvpack cp : o1.getCvchannelPacks()){
			matrizO1.put(cp.getIdpack(), cp.getIdpack());
		}
		
		for(Cvpack cp : o2.getCvchannelPacks()){
			matrizO2.put(cp.getIdpack(), cp.getIdpack());
		}
		
		for(int j=0; j<ordenPaquetes.length; j++){
			if(matrizO1.containsKey(Long.parseLong(ordenPaquetes[j]))){
				if(!matrizO2.containsKey(ordenPaquetes[j]))
					return -1;
			}else if(matrizO2.containsKey(Long.parseLong(ordenPaquetes[j]))){
				return 1;
			}
		}
		
		return 0;
	}
	
	public void cambiarOrden(String inicio){
		List<String> listOrden = new ArrayList<String>();
		listOrden.add(inicio);
		for(int i=0; i<ordenPaquetes.length; i++){
			if(!ordenPaquetes[i].equals(inicio)){
				listOrden.add(ordenPaquetes[i]);
			}
		}
		
		ordenPaquetes = listOrden.toArray(ordenPaquetes);
	}
}
