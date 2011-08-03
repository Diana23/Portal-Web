package com.cablevision.dao;

import java.util.List;

import com.cablevision.vo.CvLead;
import com.cablevision.vo.CvLeadField;
import com.cablevision.vo.CvLeadStatus;
import com.cablevision.vo.CvLeadType;
import com.cablevision.vo.CvLeadValuefield;

/**
 * The DAO interface for the CvLead entity.
 */
public interface ILeadDao {
	
	public Integer findNumberOfTicketsInDay(String email);
	
	public List<CvLeadField> findLeadFields(CvLeadType leadType);
	
	public CvLeadType findLeadTypeById(CvLeadType leadType);
	
	public CvLeadStatus findLeadStatusById(CvLeadStatus status);
	
	public void persistCvLeadValuefield(CvLeadValuefield value);
	
	public List<CvLeadType> findAllLeadTypes();
	
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
	public CvLead findCvLeadById(Integer id);
	/**
	 * Return all persistent instances of the <code>CvLead</code> entity.
	 */
	public List<CvLead> findAllCvLeads();
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvLead(CvLead cvLead);
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvLead(CvLead cvLead);
}