package com.cablevision.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.cablevision.util.ConfigurationHelper;
import com.cablevision.vo.CvMcafeeDownload;
import com.cablevision.vo.CvMcafeeReset;
import com.cablevision.vo.CvMcafeeUser;
import com.cablevision.vo.CvMcafeesuscribed;
import com.cablevision.service.IMcafeeDownloadsService;
import com.cablevision.dao.IMcafeeDownloadsDao;
import com.cablevision.vo.CvMcafee;

/**
 * The service class for the entities: CvMcafeeDownload, CvMcafeeReset, CvMcafeeUser.
 */
public class McafeeDownloadsSpringService implements IMcafeeDownloadsService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IMcafeeDownloadsDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "McafeeDownloadsService";
	
	public McafeeDownloadsSpringService() {
		super();
	}
	/**
	 * Returns the singleton <code>IMcafeeDownloadsService</code> instance.
	 */
	public static IMcafeeDownloadsService getInstance(ApplicationContext context) {
		return (IMcafeeDownloadsService)context.getBean(SERVICE_BEAN_ID);
	}
	@Override
	public List getResumen()throws Exception{
		try {
			return getDao().getResumenDownloads();
		} catch (RuntimeException e) {
			throw new Exception("getResumen failed: " + e.getMessage());
		}
	}
	
	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvMcafeeDownload findCvMcafeeDownloadById(Long id) throws Exception {
		try {
			return getDao().findCvMcafeeDownloadById(id);
		} catch (RuntimeException e) {
			throw new Exception("findCvMcafeeDownloadById failed with the id " + id + ": " + e.getMessage());
		}
	}
	/**
	 * Return all persistent instances of the <code>CvMcafeeDownload</code> entity.
	 */
	public List<CvMcafeeDownload> findAllCvMcafeeDownloads() throws Exception {
		try {
			return getDao().findAllCvMcafeeDownloads();
		} catch (RuntimeException e) {
			throw new Exception("findAllCvMcafeeDownloads failed: " + e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvMcafeeDownload> findCvMcafeeDownloadsByExample(CvMcafeeDownload cvMcafeeDownload) throws Exception {
		try {
			return getDao().findByExample(cvMcafeeDownload);
		} catch (RuntimeException e) {
			throw new Exception("findCvMcafeeDownloadsByExample failed: " + e.getMessage());
		}
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvMcafeeDownload(CvMcafeeDownload cvMcafeeDownload) throws Exception {
		try {
			getDao().persistCvMcafeeDownload(cvMcafeeDownload);
		} catch (RuntimeException e) {
			throw new Exception("persistCvMcafeeDownload failed: " + e.getMessage());
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvMcafeeDownload(CvMcafeeDownload cvMcafeeDownload) throws Exception {
		try {
			getDao().removeCvMcafeeDownload(cvMcafeeDownload);
		} catch (RuntimeException e) {
			throw new Exception("removeCvMcafeeDownload failed: " + e.getMessage());
		}
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvMcafeeReset findCvMcafeeResetById(Long id) throws Exception {
		try {
			return getDao().findCvMcafeeResetById(id);
		} catch (RuntimeException e) {
			throw new Exception("findCvMcafeeResetById failed with the id " + id + ": " + e.getMessage());
		}
	}
	/**
	 * Return all persistent instances of the <code>CvMcafeeReset</code> entity.
	 */
	public List<CvMcafeeReset> findAllCvMcafeeResets() throws Exception {
		try {
			return getDao().findAllCvMcafeeResets();
		} catch (RuntimeException e) {
			throw new Exception("findAllCvMcafeeResets failed: " + e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvMcafeeReset> findCvMcafeeResetsByExample(CvMcafeeReset cvMcafeeReset) throws Exception {
		try {
			return getDao().findByExample(cvMcafeeReset);
		} catch (RuntimeException e) {
			throw new Exception("findCvMcafeeResetsByExample failed: " + e.getMessage());
		}
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvMcafeeReset(CvMcafeeReset cvMcafeeReset) throws Exception {
		try {
			getDao().persistCvMcafeeReset(cvMcafeeReset);
		} catch (RuntimeException e) {
			throw new Exception("persistCvMcafeeReset failed: " + e.getMessage());
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvMcafeeReset(CvMcafeeReset cvMcafeeReset) throws Exception {
		try {
			getDao().removeCvMcafeeReset(cvMcafeeReset);
		} catch (RuntimeException e) {
			throw new Exception("removeCvMcafeeReset failed: " + e.getMessage());
		}
	}

	/**
	 * Return all persistent instances of the <code>CvMcafeeUser</code> entity.
	 */
	public List<CvMcafeeUser> findAllCvMcafeeUsers() throws Exception {
		try {
			return getDao().findAllCvMcafeeUsers();
		} catch (RuntimeException e) {
			throw new Exception("findAllCvMcafeeUsers failed: " + e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvMcafeeUser> findCvMcafeeUsersByExample(CvMcafeeUser cvMcafeeuser) throws Exception {
		try {
			return getDao().findByExample(cvMcafeeuser);
		} catch (RuntimeException e) {
			throw new Exception("findCvMcafeeUsersByExample failed: " + e.getMessage());
		}
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvMcafeeUser(CvMcafeeUser cvMcafeeuser) throws Exception {
		try {
			getDao().persistCvMcafeeUser(cvMcafeeuser);
		} catch (RuntimeException e) {
			throw new Exception("persistCvMcafeeUser failed: " + e.getMessage());
		}
	}
	
	
	public void updateCvMcafeeUserStatus(Long id, String status) throws Exception {
		try {
			getDao().updateCvMcafeeUserStatus(id, status);
		} catch (RuntimeException e) {
			throw new Exception("updateCvMcafeeUserStatus failed: " + e.getMessage());
		}
	}
	
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvMcafeeUser(CvMcafeeUser cvMcafeeuser) throws Exception {
		try {
			getDao().removeCvMcafeeUser(cvMcafeeuser);
		} catch (RuntimeException e) {
			throw new Exception("removeCvMcafeeUser failed: " + e.getMessage());
		}
	}

	/**
	 * Called by Spring using the injection rules specified in 
	 * the Spring beans file "applicationContext.xml".
	 */
	public void setDao(IMcafeeDownloadsDao dao) {
		this.dao = dao;
	}
	public IMcafeeDownloadsDao getDao() {
		return this.dao;
	}
	
	@Override
	public CvMcafeeUser getMcafeeUserByAccount(Long account) throws Exception {
		return this.dao.getMcafeeUserByAccount(account);
	}
	@Override
	public CvMcafeeReset getMcafeeReset(Long account) throws Exception {
		return this.dao.getMcafeeReset(account);
	}
	@Override
	public List<CvMcafeeDownload> getMcafeeDownloadsByUserAccount(Long account)
			throws Exception {
		return this.dao.getMcafeeDownloadsByUserAccount(account);
	}
	@Override
	public List getOrigen(Date fechaInicio, Date fechaFinal, String status) {
		return getDao().getOrigen(fechaInicio, fechaFinal, status);
	}
	@Override
	public List getResumenPorFecha(Date fechaInicio, Date fechaFinal,
			String status) {
		return getDao().getResumenPorFecha(fechaInicio, fechaFinal, status);
	}
	@Override
	public void generaReporte(Date fechaInicio, Date fechaFinal,String status,String fileName) throws Exception{
		Iterator<CvMcafeesuscribed> iterator = getDao().getSuscripciones(fechaInicio, fechaFinal, status,0,4000).iterator();
		List descargas = getDao().getResumenPorFecha(fechaInicio, fechaFinal, status);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat formatLargo = new SimpleDateFormat("dd - MMMM - yyyy");
		File file = new File(ConfigurationHelper.getPropiedad("ruta.guardar.archivos")+"/mcafee/tmp/");
		if(!file.exists()){
			file.mkdirs();
		}
		FileWriter out = new FileWriter(ConfigurationHelper.getPropiedad("ruta.guardar.archivos")+"/mcafee/tmp/"+fileName);
		PrintWriter writer = new PrintWriter(out);
		
		BigDecimal total = (BigDecimal)descargas.get(1);
		
		writer.print("<table>");
		writer.print("<tr><td colspan='6'>CABLEVISION - MCAFEE detalle</td></tr>");
		writer.print("<tr><td colspan='6'>&nbsp;</td></tr>");
		writer.print("<tr><td colspan='6'><b>"+formatLargo.format(fechaInicio));
		
		if(fechaFinal!=null){
			writer.print(" al "+formatLargo.format(fechaFinal));
		}
		writer.print("</b></td></tr>");
		writer.print("<tr><td colspan='6'>&nbsp;</td></tr>");
		writer.print("<tr><td colspan='5'><b># Usuarios Registrados</b></td><td><b>"+total+"</b></td></tr>");
		writer.print("<tr><td colspan='6'>&nbsp;</td></tr>");
		
		writer.print("<tr>");
		writer.print("<td><b># Cuenta</b></td>");
		writer.print("<td><b>Fecha de descarga</b></td>");
		writer.print("<td><b>Origen</b></td>");
		writer.print("<td><b>Email</b></td>");
		writer.print("<td><b>Descargas</b></td>");
		writer.print("<td><b>Estado</b></td>");
		writer.print("</tr>");
		
		long inicio = System.currentTimeMillis();
		int cont = 1;
		for(;iterator.hasNext();cont++){

			CvMcafeesuscribed suscribed = iterator.next();

			writer.print("<tr>");
			writer.print("<td>"+suscribed.getMteAccount()+"</td>");
			writer.print("<td>"+format.format(suscribed.getMteSuscribedate())+"</td>");
			writer.print("<td>"+suscribed.getMteSource()+"</td>");
			writer.print("<td>"+suscribed.getMteEmail()+"</td>");
			writer.print("<td>"+suscribed.getMteAttemps()+"</td>");
			writer.print("<td>"+suscribed.getMteStatus()+"</td>");
			writer.print("</tr>");
			writer.flush();

			
			

			if(!iterator.hasNext()&&cont<total.intValue()){
				System.out.println(""+cont+" - "+(System.currentTimeMillis()-inicio));
				inicio = System.currentTimeMillis();
				iterator = getDao().getSuscripciones(fechaInicio, fechaFinal, status,cont,4000).iterator();
			}

		}
		
		writer.print("</table>");
		writer.close();
		out.close();
		
	}
	@Override
	public CvMcafeesuscribed getSuscribdByAccount(Integer account){
		return getDao().getSuscribdByAccount(account);
	}
	@Override
	public void aumentarDownload(String account){
		getDao().aumentarDownload(account);
	}
	@Override
	public void persistCvMcafeesuscribed(CvMcafeesuscribed suscribed){
		getDao().persistCvMcafeesuscribed(suscribed);
	}
	/**
	 * Find an entity by its id (primary key).
	 * @return  The found entity instance or null if the entity does not exist.
	 */
	public CvMcafee findCvMcafeeById(Long id) throws Exception {
		try {
			return getDao().findCvMcafeeById(id);
		} catch (RuntimeException e) {
			throw new Exception("findCvMcafeeById failed with the id " + id
					+ ": " + e.getMessage());
		}
	}
	/**
	 * Return all persistent instances of the <code>CvMcafee</code> entity.
	 */
	public List<CvMcafee> findAllCvMcafees() throws Exception {
		try {
			return getDao().findAllCvMcafees();
		} catch (RuntimeException e) {
			throw new Exception("findAllCvMcafees failed: " + e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvMcafee> findCvMcafeesByExample(CvMcafee cvMcafee)
			throws Exception {
		try {
			return getDao().findByExample(cvMcafee);
		} catch (RuntimeException e) {
			throw new Exception("findCvMcafeesByExample failed: "
					+ e.getMessage());
		}
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvMcafee(CvMcafee cvMcafee) throws Exception {
		try {
			getDao().persistCvMcafee(cvMcafee);
		} catch (RuntimeException e) {
			throw new Exception("persistCvMcafee failed: " + e.getMessage());
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvMcafee(CvMcafee cvMcafee) throws Exception {
		try {
			getDao().removeCvMcafee(cvMcafee);
		} catch (RuntimeException e) {
			throw new Exception("removeCvMcafee failed: " + e.getMessage());
		}
	}
	
	public void updateCvMcafeeStatus(Long id, String status) throws Exception {
		try {
			getDao().updateCvMcafeeStatus(id, status);
		} catch (RuntimeException e) {
			throw new Exception("updateCvMcafeeStatus failed: " + e.getMessage());
		}
	}
	
	public CvMcafee getMcafeeByAccount(Long account) throws Exception {
		try {
			return getDao().getMcafeeByAccount(account);
		} catch (RuntimeException e) {
			throw new Exception("getMcafeeByAccount failed: " + e.getMessage());
		}
	}
}