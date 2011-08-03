package com.cablevision.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cablevision.dao.IDisponibilidadDao;
import com.cablevision.vo.CvDisponibilidadnr;

/**
 * The DAO class for the CvDisponibilidadnr entity.
 */
public class DisponibilidadHibernateDao extends HibernateDaoSupport implements IDisponibilidadDao {
	public DisponibilidadHibernateDao() {
		super();
	}
	/**
	 * Return the persistent entities returned from a named query.
	 */
	public List findByNamedQuery(String queryName) {
		return getHibernateTemplate().findByNamedQuery(queryName);
	}
	/**
	 * Return the persistent entities returned from a named query with named parameters.
	 */
	public List findByNamedQuery(String queryName, String[] paramNames, Object[] paramValues) {
		if (paramNames.length != paramValues.length) {
			throw new IllegalArgumentException();
		}
		return getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, paramNames, paramValues);
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List findByExample(Object example) {
		CvDisponibilidadnr disp = (CvDisponibilidadnr)example;
		
		String query = "FROM CvDisponibilidadnr cd WHERE cd.dispCp = ?";
		  		 
		return getHibernateTemplate().find(query.toString(),disp.getDispCp());
	}
	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvDisponibilidadnr findCvDisponibilidadnrById(String id) {
		return (CvDisponibilidadnr)getHibernateTemplate().load(CvDisponibilidadnr.class, id);
	}
	/**
	 * Return all persistent instances of the <code>CvDisponibilidadnr</code> entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvDisponibilidadnr> findAllCvDisponibilidadnrs() {
	 	return getHibernateTemplate().loadAll(CvDisponibilidadnr.class);
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvDisponibilidadnr(CvDisponibilidadnr cvDisponibilidadnr) {
		getHibernateTemplate().saveOrUpdate(cvDisponibilidadnr);
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvDisponibilidadnr(CvDisponibilidadnr cvDisponibilidadnr) {
		getHibernateTemplate().delete(cvDisponibilidadnr);
	}
}