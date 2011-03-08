package com.cablevision.dao;

import java.util.List;

import com.cablevision.vo.CvPaperless;
import com.cablevision.vo.CvPaperlessHistorial;

/**
 * The DAO interface for the entities: CvPaperless, CvPaperlessHistorial.
 */
public interface IPaperlessDao {
	/**
	 * Return the persistent entities returned from a named query.
	 */
	public List findByNamedQuery(String queryName);
	/**
	 * Return the persistent entities returned from a named query with named parameters.
	 */
	public List findByNamedQuery(String queryName, String[] paramNames, Object[] paramValues);
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List findByExample(Object example);

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvPaperless findCvPaperlessById(String id);
	/**
	 * Return all persistent instances of the <code>CvPaperless</code> entity.
	 */
	public List<CvPaperless> findAllCvPaperlesses();
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvPaperless(CvPaperless cvPaperless);
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvPaperless(CvPaperless cvPaperless);

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvPaperlessHistorial findCvPaperlessHistorialById(Long id);
	/**
	 * Return all persistent instances of the <code>CvPaperlessHistorial</code> entity.
	 */
	public List<CvPaperlessHistorial> findAllCvPaperlessHistorials();
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvPaperlessHistorial(CvPaperlessHistorial cvPaperlessHistorial);
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvPaperlessHistorial(CvPaperlessHistorial cvPaperlessHistorial);
	
	public String getCvPaperlessHistoryNextSeqValue();
}