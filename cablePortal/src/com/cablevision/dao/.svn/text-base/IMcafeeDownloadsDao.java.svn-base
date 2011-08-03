package com.cablevision.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.cablevision.vo.CvMcafeeDownload;
import com.cablevision.vo.CvMcafeeReset;
import com.cablevision.vo.CvMcafeeUser;
import com.cablevision.vo.CvMcafeesuscribed;

/**
 * The DAO interface for the entities: CvMcafeeDownload, CvMcafeeReset, CvMcafeeUser.
 */
public interface IMcafeeDownloadsDao {
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
	public CvMcafeeDownload findCvMcafeeDownloadById(Long id);
	/**
	 * Return all persistent instances of the <code>CvMcafeeDownload</code> entity.
	 */
	public List<CvMcafeeDownload> findAllCvMcafeeDownloads();
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvMcafeeDownload(CvMcafeeDownload cvMcafeeDownload);
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvMcafeeDownload(CvMcafeeDownload cvMcafeeDownload);

	/**
	 * Find an entity by its id (primary key).
	 * @return The found entity instance or null if the entity does not exist.
	 */
	public CvMcafeeReset findCvMcafeeResetById(Long id);
	/**
	 * Return all persistent instances of the <code>CvMcafeeReset</code> entity.
	 */
	public List<CvMcafeeReset> findAllCvMcafeeResets();
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvMcafeeReset(CvMcafeeReset cvMcafeeReset);
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvMcafeeReset(CvMcafeeReset cvMcafeeReset);

	/**
	 * Return all persistent instances of the <code>CvMcafeeUser</code> entity.
	 */
	public List<CvMcafeeUser> findAllCvMcafeeUsers();
	/**
	 * Make the given instance managed and persistent.
	 */
	public void persistCvMcafeeUser(CvMcafeeUser cvMcafeeuser);
	/**
	 * Remove the given persistent instance.
	 */
	public void removeCvMcafeeUser(CvMcafeeUser cvMcafeeuser);
	
	public CvMcafeeUser getMcafeeUserByAccount(Long account);
	
	public List<CvMcafeeDownload> getMcafeeDownloadsByUserAccount(Long account);
	
	public CvMcafeeReset getMcafeeReset(Long account);
	
	public List getResumenDownloads();
	public List getOrigen(Date fechaInicio, Date fechaFinal, String status);
	public List getResumenPorFecha(Date fechaInicio, Date fechaFinal, String status);
	public CvMcafeesuscribed getSuscribdByAccount(Integer account);
	public List<CvMcafeesuscribed> getSuscripciones(Date fechaInicio,Date fechaFinal, String status,int inicio,int maxResults);
	public void aumentarDownload(String account);
	public void persistCvMcafeesuscribed(CvMcafeesuscribed suscribed);
}