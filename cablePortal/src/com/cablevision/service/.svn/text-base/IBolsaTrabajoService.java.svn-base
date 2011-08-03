package com.cablevision.service;

import java.util.List;

import com.cablevision.vo.CvCurriculum;
import com.cablevision.vo.CvUser;

/**
 * The service interface for the entities: CvCurriculum, CvProspectus, CvUser, CvVacancy, CvVacancyItem.
 */
public interface IBolsaTrabajoService {

	public Boolean isCvUserRegistered(String id) throws Exception;
	
	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvCurriculum findCvCurriculumById(String id) throws Exception;
	/**
	 * Return all persistent instances of the <code>CvCurriculum</code> entity.
	 */
	public List<CvCurriculum> findAllCvCurriculums() throws Exception;
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List<CvCurriculum> findCvCurriculumsByExample(CvCurriculum cvCurriculum) throws Exception;
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvCurriculum(CvCurriculum cvCurriculum) throws Exception;
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvCurriculum(CvCurriculum cvCurriculum) throws Exception;


	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvUser findCvUserById(String id) throws Exception;
	/**
	 * Return all persistent instances of the <code>CvUser</code> entity.
	 */
	public List<CvUser> findAllCvUsers() throws Exception;
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	public List<CvUser> findCvUsersByExample(CvUser cvUser) throws Exception;
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvUser(CvUser cvUser) throws Exception;
	
	public String getCvUserNextSeqValue();
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvUser(CvUser cvUser) throws Exception;
}