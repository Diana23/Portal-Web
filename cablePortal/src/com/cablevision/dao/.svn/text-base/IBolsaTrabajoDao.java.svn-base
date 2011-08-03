package com.cablevision.dao;

import java.util.List;
import com.cablevision.vo.CvCurriculum;
import com.cablevision.vo.CvUser;

/**
 * The DAO interface for the entities: CvCurriculum, CvProspectus, CvUser, CvVacancy, CvVacancyItem.
 */
@SuppressWarnings("unchecked")
public interface IBolsaTrabajoDao {
	
	public CvUser findUserById(String id);	
	
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
	public CvCurriculum findCvCurriculumById(String id);
	/**
	 * Return all persistent instances of the <code>CvCurriculum</code> entity.
	 */
	public List<CvCurriculum> findAllCvCurriculums();
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvCurriculum(CvCurriculum cvCurriculum);
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvCurriculum(CvCurriculum cvCurriculum);

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvUser findCvUserById(String id);
	/**
	 * Return all persistent instances of the <code>CvUser</code> entity.
	 */
	public List<CvUser> findAllCvUsers();
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvUser(CvUser cvUser);
	
	public String getCvUserNextSeqValue();
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvUser(CvUser cvUser);	
}