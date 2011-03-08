/**
 * 
 */
package com.cablevision.util;

import java.util.Calendar;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author <a href="mailto:jorge.ruiz@jwmsolutions.com">Jorge Ruiz Aquino</a>
 * 30/11/2009
 */
public class SchedulerJob implements Job {
	/**
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		String[] txtFiles = {
				ConfigurationHelper.getPropiedad("programacion.archivo.programrecord", null),
				ConfigurationHelper.getPropiedad("programacion.archivo.stationrecord", null),
				ConfigurationHelper.getPropiedad("programacion.archivo.schedulerecord", null),
				ConfigurationHelper.getPropiedad("programacion.archivo.translationrecord", null)
		};
		ProgramScheduleReader psr = new ProgramScheduleReader();
		try {
//			new ProgramScheduleReader().loadFile(Constantes.SCHEDULE_SCHEDULE_RECORD);
			System.out.println(" *** Download files form FTP starting at " + Calendar.getInstance().getTime().toString());
			psr.getFilesFromFTP();
			System.out.println(" *** Download files form FTP finished at " + Calendar.getInstance().getTime().toString());
			
			System.out.println(" *** Deleting all records from all tables of programming started at " + Calendar.getInstance().getTime().toString());
			psr.deleteAllTableRecords();
			System.out.println(" *** Deleting all records from all tables of programming finished at " + Calendar.getInstance().getTime().toString());
			
			System.out.println(" *** Deleting all records from from Solr started at " + Calendar.getInstance().getTime().toString());
			SolrHelper.borrarByQuery("tipo:programacion");
			SolrHelper.getSolrServer().commit();
			System.out.println(" *** Deleting all records from Solr finished at " + Calendar.getInstance().getTime().toString());
			
			psr.populateChannelMap();
			
			System.out.println(" *** Populate of DB from files starting at " + Calendar.getInstance().getTime().toString());
			for(int i=0; i<txtFiles.length; i++) {
				System.out.println(" *** Populate from '" + txtFiles[i] + "' starting at " + Calendar.getInstance().getTime().toString());
				psr.loadFile(txtFiles[i]);
				System.out.println(" *** Populate from '" + txtFiles[i] + "' finished at " + Calendar.getInstance().getTime().toString());
			}
			System.out.println(" *** Populate of DB from files finished at " + Calendar.getInstance().getTime().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
}
