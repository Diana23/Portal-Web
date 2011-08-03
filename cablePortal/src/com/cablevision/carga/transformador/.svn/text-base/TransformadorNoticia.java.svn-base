package com.cablevision.carga.transformador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.cablevision.util.SolrHelper;

/**
 * @author daniela g
 * Trasforma el campo de fecha y fecha corta, de las noticias, en formato de fecha UTC util para solr
 */
public class TransformadorNoticia implements Transformador {

	@Override
	public void transforma(Map<String, String> datos, int index) {
		String strFecha = datos.get("noticia_fecha");
		SimpleDateFormat sdfFechas = new SimpleDateFormat("MM/dd/yyyy");
		Date dFecha = null;
		
		try {
			if(StringUtils.isNotEmpty(strFecha)){
				dFecha = sdfFechas.parse(strFecha);
				datos.put("noticia_fecha", SolrHelper.Date2UTC(dFecha));
				datos.put("noticia_fechaCorta", SolrHelper.Date2UTC(DateUtils.truncate(dFecha, Calendar.MONTH)));
			}
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
	}
}
