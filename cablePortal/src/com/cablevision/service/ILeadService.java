package com.cablevision.service;

import java.util.List;
import java.util.Map;

import com.cablevision.vo.CvLead;
import com.cablevision.vo.CvLeadField;
import com.cablevision.vo.CvLeadStatus;
import com.cablevision.vo.CvLeadType;
import com.cablevision.vo.CvLeadValuefield;
import com.cablevision.vo.ErrorField;

/**
 * The service interface for the CvLead entity.
 */
public interface ILeadService {
	
	public Object saveLead(Map<String,String> map)throws Exception;
	
	public List<String> validateFields(Map<String,String> map)throws Exception;
	
	public List<ErrorField> validateField(Map<String,String> map) throws Exception;
	
	public Map<String,String> getValidatedFields(Map<String,String> map)throws Exception;
	
	public String generateId()throws Exception;
	
	public List<CvLeadField> findLeadFields(Map<String,String> form) throws Exception;
	
	public Integer findNumberOfTicketsInDay(String email) throws Exception;
	
	public CvLeadType findLeadTypeById(Integer idLeadType) throws Exception;
	
	public CvLeadStatus findLeadStatusById(Integer idLeadStatus) throws Exception;
	
	public void persistCvLeadValue(CvLeadValuefield value)throws Exception;
	
	public List<CvLeadType> findAllLeadTypes()throws Exception;
	
	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvLead findCvLeadById(Integer id) throws Exception;
	/**
	 * Return all persistent instances of the <code>CvLead</code> entity.
	 */
	public List<CvLead> findAllCvLeads() throws Exception;
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List<CvLead> findCvLeadsByExample(CvLead cvLead) throws Exception;
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvLead(CvLead cvLead) throws Exception;
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvLead(CvLead cvLead) throws Exception;
}