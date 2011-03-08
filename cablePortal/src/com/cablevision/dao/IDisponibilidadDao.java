package com.cablevision.dao;

import java.util.List;

import com.cablevision.vo.CvDisponibilidadnr;

/**
 * The DAO interface for the CvDisponibilidadnr entity.
 */
public interface IDisponibilidadDao {
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
	public CvDisponibilidadnr findCvDisponibilidadnrById(String id);
	/**
	 * Return all persistent instances of the <code>CvDisponibilidadnr</code> entity.
	 */
	public List<CvDisponibilidadnr> findAllCvDisponibilidadnrs();
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvDisponibilidadnr(CvDisponibilidadnr cvDisponibilidadnr);
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvDisponibilidadnr(CvDisponibilidadnr cvDisponibilidadnr);
}