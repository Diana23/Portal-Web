/**
 * 
 */
package com.cablevision.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

/**
 * @author <a href="mailto:jorge.ruiz@jwmsolutions.com">Jorge Ruiz Aquino</a>
 *	Clase usada para realizar el filtro de los logs
 */
public class LogFilter extends Filter{
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");


	/**
	 * @see org.apache.log4j.spi.Filter#decide(org.apache.log4j.spi.LoggingEvent)
	 */
	@Override
	public int decide(LoggingEvent event) {
		if (StringUtils.equals(event.getLoggerName(), Constantes.SEGURIDAD_LOG_NAME)) {
			return NEUTRAL;
		}
		return DENY;
	}
	
	public static Logger getLoggerInstance() {
		return Logger.getLogger(Constantes.SEGURIDAD_LOG_NAME);
	}

	public static void log(Logger log, String user, String accion, int intento, String status) {
		String message = user + " " + sdf.format(new Date()) + " " + accion + " " + intento + " " + status;
		log.info(message);
	}
}
