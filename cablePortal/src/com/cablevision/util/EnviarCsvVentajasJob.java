package com.cablevision.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.cablevision.controller.base.ControllerBase;

/**
 * @author Jorge Ruiz Aquino
 *
 */
public class EnviarCsvVentajasJob implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("Job: EnviarCsvVentajasJob... START ...");
		
//		String path = getMessageResources("configuracion").getMessage("virtual.cablevision.testimonios");
		Properties properties = new Properties();
		InputStream is = ControllerBase.class.getResourceAsStream("/configuracion.properties");
//		Configuration config = new PropertiesConfiguration/("configuracion.properties");

		try {
			System.out.println("Cargando archivo de propiedades configuracion.properties...");
			properties.load(is);

			System.out.println("Obteniendo la ruta de los archivos .csv...");
			String path = properties.getProperty("virtual.cablevision.testimonios");
			String emailFrom = properties.getProperty("correo.vacantes.from");

			System.out.println("Generando el nombre del archivo .csv del mes pasado...");
			Calendar calendar = Calendar.getInstance();
			String[] months = new DateFormatSymbols().getShortMonths();
			int month = calendar.get(Calendar.MONTH);
			int year = calendar.get(Calendar.YEAR);
			if(month == 0){
				month = 12;
				year--;
			}
			String folderName = year + "-" + months[month-1];
			String csvFileName =  folderName + ".csv";

			System.out.println("Obteniendo el archivo con id:LISTA_EMAILS.TXT con los emails desde UCM...");
			String emails = UcmUtil.getContentByName("LISTA_EMAILS.TXT", true);
			
			System.out.println("Enviando los correos con el archivo adjunto...");
			String titulo = "Lo que dicen nuestros clientes mes " + folderName;
			MailUtil.sendMail(titulo, titulo, emails, emailFrom, true, path, csvFileName);

		} catch (IOException e) {
			System.out.println("Ocurrió un error: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Ocurrió un error: " + e.getMessage());
			e.printStackTrace();
		}
		System.out.println("Job: EnviarCsvVentajasJob... END ...");
	}

}
