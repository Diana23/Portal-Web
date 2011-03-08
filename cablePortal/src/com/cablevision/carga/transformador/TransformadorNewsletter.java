package com.cablevision.carga.transformador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.cablevision.util.SolrHelper;
import org.apache.commons.lang.time.DateUtils;

public class TransformadorNewsletter implements Transformador{
	@Override
	public void transforma(Map<String, String> datos, int index) {
		//datos.put("id", datos.get("tipo") + "_" + Integer.toString(index));

		String strFecha = datos.get("newsletter_fecha");
		SimpleDateFormat sdfFechas = new SimpleDateFormat("MM/dd/yyyy");
		Date dFecha = null;
		
		try {
			dFecha = sdfFechas.parse(strFecha);
			datos.put("newsletter_fecha", SolrHelper.Date2UTC(dFecha));
			datos.put("newsletter_fechaCorta", SolrHelper.Date2UTC(DateUtils.truncate(dFecha, Calendar.MONTH)));
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
	}
}
