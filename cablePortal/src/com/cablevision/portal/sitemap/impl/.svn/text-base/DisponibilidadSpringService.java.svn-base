package com.cablevision.portal.sitemap.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;
import com.cablevision.vo.CvDisponibilidadnr;
import com.cablevision.service.IDisponibilidadService;
import com.cablevision.dao.IDisponibilidadDao;

/**
 * The service class for the CvDisponibilidadnr entity.
 */
public class DisponibilidadSpringService implements IDisponibilidadService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IDisponibilidadDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "DisponibilidadService";
	
	public DisponibilidadSpringService() {
		super();
	}
	/**
	 * Returns the singleton <code>IDisponibilidadService</code> instance.
	 */
	public static IDisponibilidadService getInstance(ApplicationContext context) {
		return (IDisponibilidadService)context.getBean(SERVICE_BEAN_ID);
	}
	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvDisponibilidadnr findCvDisponibilidadnrById(String id) throws Exception {
		try {
			return getDao().findCvDisponibilidadnrById(id);
		} catch (RuntimeException e) {
			throw new Exception("findCvDisponibilidadnrById failed with the id " + id + ": " + e.getMessage());
		}
	}
	/**
	 * Return all persistent instances of the <code>CvDisponibilidadnr</code> entity.
	 */
	public List<CvDisponibilidadnr> findAllCvDisponibilidadnrs() throws Exception {
		try {
			return getDao().findAllCvDisponibilidadnrs();
		} catch (RuntimeException e) {
			throw new Exception("findAllCvDisponibilidadnrs failed: " + e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvDisponibilidadnr> findCvDisponibilidadnrsByExample(CvDisponibilidadnr cvDisponibilidadnr) throws Exception {
		try {
			return getDao().findByExample(cvDisponibilidadnr);
		} catch (RuntimeException e) {
			throw new Exception("findCvDisponibilidadnrsByExample failed: " + e.getMessage());
		}
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvDisponibilidadnr(CvDisponibilidadnr cvDisponibilidadnr) throws Exception {
		try {
			getDao().persistCvDisponibilidadnr(cvDisponibilidadnr);
		} catch (RuntimeException e) {
			throw new Exception("persistCvDisponibilidadnr failed: " + e.getMessage());
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvDisponibilidadnr(CvDisponibilidadnr cvDisponibilidadnr) throws Exception {
		try {
			getDao().removeCvDisponibilidadnr(cvDisponibilidadnr);
		} catch (RuntimeException e) {
			throw new Exception("removeCvDisponibilidadnr failed: " + e.getMessage());
		}
	}

	/**
	 * Called by Spring using the injection rules specified in 
	 * the Spring beans file "applicationContext.xml".
	 */
	public void setDao(IDisponibilidadDao dao) {
		this.dao = dao;
	}
	public IDisponibilidadDao getDao() {
		return this.dao;
	}
}