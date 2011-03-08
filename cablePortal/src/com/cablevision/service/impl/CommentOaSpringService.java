package com.cablevision.service.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;
import com.cablevision.vo.CvCommentoa;
import com.cablevision.vo.CvTrackoa;
import com.cablevision.service.ICommentOaService;
import com.cablevision.dao.ICommentOaDao;

/**
 * The service class for the entities: CvCommentoa, CvTrackoa.
 */
public class CommentOaSpringService implements ICommentOaService {
	/**
	 * The dao instance injected by Spring.
	 */
	private ICommentOaDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "CommentOaService";
	
	public CommentOaSpringService() {
		super();
	}
	/**
	 * Returns the singleton <code>ICommentOaService</code> instance.
	 */
	public static ICommentOaService getInstance(ApplicationContext context) {
		return (ICommentOaService)context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvCommentoa findCvCommentoaById(Long id) throws Exception {
		try {
			return getDao().findCvCommentoaById(id);
		} catch (RuntimeException e) {
			throw new Exception("findCvCommentoaById failed with the id " + id + ": " + e.getMessage());
		}
	}
	/**
	 * Return all persistent instances of the <code>CvCommentoa</code> entity.
	 */
	public List<CvCommentoa> findAllCvCommentoas() throws Exception {
		try {
			return getDao().findAllCvCommentoas();
		} catch (RuntimeException e) {
			throw new Exception("findAllCvCommentoas failed: " + e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvCommentoa> findCvCommentoasByExample(CvCommentoa cvCommentoa) throws Exception {
		try {
			return getDao().findByExample(cvCommentoa);
		} catch (RuntimeException e) {
			throw new Exception("findCvCommentoasByExample failed: " + e.getMessage());
		}
	}
	
	/**
	 * Find entity list  by its accountId.
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public List<CvCommentoa> findCvCommentoaByNoSolicitud(String noSolicitud) throws Exception {
		try{
			return getDao().findCvCommentoaByNoSolicitud(noSolicitud);
		}catch(RuntimeException e){
			throw new Exception("findCvCommentoaByAccountId failed: "+ e.getMessage());
		}
	}
	
	
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvCommentoa(CvCommentoa cvCommentoa) throws Exception {
		try {
			getDao().persistCvCommentoa(cvCommentoa);
		} catch (RuntimeException e) {
			throw new Exception("persistCvCommentoa failed: " + e.getMessage());
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvCommentoa(CvCommentoa cvCommentoa) throws Exception {
		try {
			getDao().removeCvCommentoa(cvCommentoa);
		} catch (RuntimeException e) {
			throw new Exception("removeCvCommentoa failed: " + e.getMessage());
		}
	}

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvTrackoa findCvTrackoaById(Long id) throws Exception {
		try {
			return getDao().findCvTrackoaById(id);
		} catch (RuntimeException e) {
			throw new Exception("findCvTrackoaById failed with the id " + id + ": " + e.getMessage());
		}
	}
	
	/**
	 * Find an entity by its AccountId.
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public List<CvTrackoa> findCvTrackoaByAccountId(String accountId) throws Exception{
		try {
			return getDao().findCvTrackoaByAccountId(accountId);
		} catch (RuntimeException e) {
			throw new Exception("findCvTrackoaByAccountId failed with the id " + accountId + ": " + e.getMessage());
		}
	}
	/**
	 * Find an entity by its accountId.
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvTrackoa findCvTrackoaByNumberOa(String numberOa) throws Exception{
		try{
			return getDao().findCvTrackoaByNumberOa(numberOa);
		}catch(RuntimeException e){
			throw new Exception("findCvTrackoaByNumberOa filed with the numberoa "+ numberOa +": "+e.getMessage());
		}
	}
	/**
	 * Return all persistent instances of the <code>CvTrackoa</code> entity.
	 */
	public List<CvTrackoa> findAllCvTrackoas() throws Exception {
		try {
			return getDao().findAllCvTrackoas();
		} catch (RuntimeException e) {
			throw new Exception("findAllCvTrackoas failed: " + e.getMessage());
		}
	}
	/**
	 * Return the persistent entities matching the given example entity.
	 */
	@SuppressWarnings("unchecked")
	public List<CvTrackoa> findCvTrackoasByExample(CvTrackoa cvTrackoa) throws Exception {
		try {
			return getDao().findByExample(cvTrackoa);
		} catch (RuntimeException e) {
			throw new Exception("findCvTrackoasByExample failed: " + e.getMessage());
		}
	}
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvTrackoa(CvTrackoa cvTrackoa) throws Exception {
		try {
			getDao().persistCvTrackoa(cvTrackoa);
		} catch (RuntimeException e) {
			throw new Exception("persistCvTrackoa failed: " + e.getMessage());
		}
	}
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvTrackoa(CvTrackoa cvTrackoa) throws Exception {
		try {
			getDao().removeCvTrackoa(cvTrackoa);
		} catch (RuntimeException e) {
			throw new Exception("removeCvTrackoa failed: " + e.getMessage());
		}
	}

	/**
	 * Called by Spring using the injection rules specified in 
	 * the Spring beans file "applicationContext.xml".
	 */
	public void setDao(ICommentOaDao dao) {
		this.dao = dao;
	}
	public ICommentOaDao getDao() {
		return this.dao;
	}
}