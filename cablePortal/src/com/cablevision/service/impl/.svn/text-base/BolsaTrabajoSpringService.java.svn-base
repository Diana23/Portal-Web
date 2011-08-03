package com.cablevision.service.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;
import com.cablevision.vo.CvCurriculum;
import com.cablevision.vo.CvUser;
import com.cablevision.service.IBolsaTrabajoService;
import com.cablevision.dao.IBolsaTrabajoDao;

/**
 * The service class for the entities: CvCurriculum, CvProspectus, CvUser, CvVacancy, CvVacancyItem.
 */
public class BolsaTrabajoSpringService implements IBolsaTrabajoService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IBolsaTrabajoDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "BolsaTrabajoService";
	
	public BolsaTrabajoSpringService() {
		super();
	}
	/**
	 * Returns the singleton <code>IBolsaTrabajoService</code> instance.
	 */
	public static IBolsaTrabajoService getInstance(ApplicationContext context) {
		return (IBolsaTrabajoService)context.getBean(SERVICE_BEAN_ID);
	}

	public Boolean isCvUserRegistered(String id) throws Exception{
		CvUser user = getDao().findUserById(id);
		return user!=null;
	}
	
	
	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvCurriculum findCvCurriculumById(String id) throws Exception {
		try {
			return getDao().findCvCurriculumById(id);
		} catch (RuntimeException e) {
			throw new Exception("findCvCurriculumById failed with the id " + id + ": " + e.getMessage());
		}
	}
	/**
	 * Return all persistent instances of the <code>CvCurriculum</code> entity.
	 */
	public List<CvCurriculum> findAllCvCurriculums() throws Exception {
		try {
			return getDao().findAllCvCurriculums();
		} catch (RuntimeException e) {
			throw new Exception("findAllCvCurriculums failed: " + e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvCurriculum> findCvCurriculumsByExample(CvCurriculum cvCurriculum) throws Exception {
		try {
			return getDao().findByExample(cvCurriculum);
		} catch (RuntimeException e) {
			throw new Exception("findCvCurriculumsByExample failed: " + e.getMessage());
		}
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvCurriculum(CvCurriculum cvCurriculum) throws Exception {
		try {
			getDao().persistCvCurriculum(cvCurriculum);
		} catch (RuntimeException e) {
			throw new Exception("persistCvCurriculum failed: " + e.getMessage());
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvCurriculum(CvCurriculum cvCurriculum) throws Exception {
		try {
			getDao().removeCvCurriculum(cvCurriculum);
		} catch (RuntimeException e) {
			throw new Exception("removeCvCurriculum failed: " + e.getMessage());
		}
	}


	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvUser findCvUserById(String id) throws Exception {
		try {
			return getDao().findUserById(id); //CvUserById(id);
		} catch (RuntimeException e) {
			throw new Exception("findCvUserById failed with the id " + id + ": " + e.getMessage());
		}
	}
	/**
	 * Return all persistent instances of the <code>CvUser</code> entity.
	 */
	public List<CvUser> findAllCvUsers() throws Exception {
		try {
			return getDao().findAllCvUsers();
		} catch (RuntimeException e) {
			throw new Exception("findAllCvUsers failed: " + e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvUser> findCvUsersByExample(CvUser cvUser) throws Exception {
		try {
			return getDao().findByExample(cvUser);
		} catch (RuntimeException e) {
			throw new Exception("findCvUsersByExample failed: " + e.getMessage());
		}
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvUser(CvUser cvUser) throws Exception {
		try {
			getDao().persistCvUser(cvUser);
		} catch (RuntimeException e) {
			throw new Exception("persistCvUser failed: " + e.getMessage());
		}
	}
	
	public String getCvUserNextSeqValue() {
		return getDao().getCvUserNextSeqValue();
	}
	
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvUser(CvUser cvUser) throws Exception {
		try {
			getDao().removeCvUser(cvUser);
		} catch (RuntimeException e) {
			throw new Exception("removeCvUser failed: " + e.getMessage());
		}
	}

	/**
	 * Called by Spring using the injection rules specified in 
	 * the Spring beans file "applicationContext.xml".
	 */
	public void setDao(IBolsaTrabajoDao dao) {
		this.dao = dao;
	}
	public IBolsaTrabajoDao getDao() {
		return this.dao;
	}
}