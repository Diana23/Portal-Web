package com.cablevision.util;

import java.text.DateFormat;
import java.util.Comparator;

import com.cablevision.controller.misPagos.MisPagosController.MisPagosBean;

public class ComparatorUtil implements Comparator<MisPagosBean> {

	@Override
	public int compare(MisPagosBean o1, MisPagosBean o2) {
		try{
			
			if(o1.getFechaPago().after(o2.getFechaPago())){
				return 1;
			}
			if(o1.getFechaPago().before(o2.getFechaPago())){
				return -1;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}

}
