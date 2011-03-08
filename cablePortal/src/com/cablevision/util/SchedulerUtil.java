/**
 * 
 */
package com.cablevision.util;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author <a href="mailto:jorge.ruiz@jwmsolutions.com">Jorge Ruiz Aquino</a>
 * 30/11/2009
 */
public class SchedulerUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			//  Get a scheduler instance.
			SchedulerFactory schedulerFactory = new StdSchedulerFactory();
			Scheduler scheduler = schedulerFactory.getScheduler();

			long ctime = System.currentTimeMillis();

			//  Create a trigger.
			JobDetail jobDetail = new JobDetail("Job Detail", "jGroup", SchedulerJob.class);
			SimpleTrigger simpleTrigger = new SimpleTrigger("My Trigger", "tGroup");
			simpleTrigger.setStartTime(new Date(ctime));

			//  Set the time interval and number of repeats.
			simpleTrigger.setRepeatInterval(100);
			simpleTrigger.setRepeatCount(10);

			//  Add trigger and job to Scheduler.
			scheduler.scheduleJob(jobDetail, simpleTrigger);

			//  Start the job.
			scheduler.start();
		} catch (SchedulerException ex) {
			ex.printStackTrace();
		}
	}

}
