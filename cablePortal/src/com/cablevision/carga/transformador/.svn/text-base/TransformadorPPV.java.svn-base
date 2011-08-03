package com.cablevision.carga.transformador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import com.cablevision.util.SolrHelper;

public class TransformadorPPV implements Transformador {

	@Override
	public void transforma(Map<String, String> datos, int index) {
		datos.put("id", datos.get("tipo") + "_" + Integer.toString(index));

		String strFini = datos.get("fechaini");
		String strFfin = datos.get("fechafin");
		SimpleDateFormat sdfFechas = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		SimpleDateFormat sdfFechaCorta = new SimpleDateFormat("MM/dd/yyyy");
		Date dFini = null;
		Date dFfin = null;
		Date dFiniCorta = null;
		try {
			dFini = sdfFechas.parse(strFini);
			dFfin = sdfFechas.parse(strFfin);
			dFiniCorta = sdfFechaCorta.parse(strFini);
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		datos.put("fechaini", SolrHelper.Date2UTC(dFini));
		datos.put("fechafin", SolrHelper.Date2UTC(dFfin));
		datos.put("fechainiCorta", SolrHelper.Date2UTC(dFiniCorta));
		
		SolrDocumentList lst = SolrHelper.query("tipo:ppv AND titulo:\""+datos.get("titulo")+"\" AND genero:\""+datos.get("genero")+"\"").getResults();
		if(lst!=null && lst.size()>0){
			SolrDocument sd = lst.get(0);
			datos.put("categoria", (String)sd.getFieldValue("categoria"));
		}
	}
}